package red.psyche.fsm;

import red.psyche.fsm.interceptor.Interceptor;
import red.psyche.fsm.listener.FsmListenerChain;
import red.psyche.fsm.listener.FsmListenerObject;
import red.psyche.fsm.util.BeanOperator;
import red.psyche.fsm.util.BeanOperatorFactory;
import lombok.Data;

import java.util.Map;

/**
 * this class is thread safe 
 * @author inthendsun
 * @param <S> State
 * @param <E> Event
 * @param <R> Record
 * @param <C> Context
 */
@SuppressWarnings({"unchecked","rawtypes"})
public abstract class AbstractFsm<S, E, R extends FsmRecord, C extends AbstractFsmContext<R>> {

	/**
	 * 责任链监听模式
	 */
	private FsmListenerChain listenerChain = new FsmListenerChain();

	/**
	 * 状态机名字，从definer解析
	 */
	private String name;

	/**
	 * 状态迁移表
	 */
	private Map<String, StateTransition> transitionMap;

	/**
	 * 状态机定义
	 * @param definer
	 */
	public AbstractFsm(FsmDefiner definer) {
		this.name = definer.getName();
		transitionMap = FsmTransitionUtil.parse(definer);
	}

	/**
	 * 触发状态机执行。
	 * @param event 预先定义好的事件枚举
	 * @param context 状态机上下文对象，用于在执行状态机过程中记录上下文内容。
	 * @return 可以根据FireResult判断更新是否成功
	 */
	public FireResult fireWithResult(E event, C context) {
		R record = context.getRecord();
		if (record == null) {
			throw new IllegalArgumentException("context.record不能为null");
		}
		if (record.getState() == null) {
			throw new IllegalArgumentException("context.record.getState不能为null");
		}
		BeanOperator<R> beanOperator = BeanOperatorFactory.getBeanOperator(record.getClass());
		R execRecord = context.getExecRecord();
		if (execRecord == null) {
			try {
				execRecord = ((R) record.getClass().newInstance());
			} catch (Exception e) {
				throw new IllegalArgumentException("状态机execRecord 对象创建失败", e);
			}
		}
		beanOperator.copyMissing(execRecord, record);
		R append = beanOperator.append(record, execRecord);
		context.setExecRecord(execRecord);
		context.setAppend(append);

		StateTransition<AbstractFsmAction> stateTransition = transitionMap.get(record.getState()+"/"+ event.toString()) ;
		FsmListenerObject listenerObj = new FsmListenerObject();
		FireResult fireResult = basicCheckFsm(stateTransition, context,listenerObj);
		if(fireResult != null) {
			return fireResult;
		}
		return fireInternal(context,stateTransition,listenerObj);
	}

	/**
	 *  基础校验。
	 *  <br/>1. 迁移内容不能为空
	 *  <br/>2. 状态不能非法
	 * @param stateTransition
	 * @param context
	 * @param listenerObj
	 * @return
	 */
	private FireResult basicCheckFsm(StateTransition<AbstractFsmAction> stateTransition, C context, FsmListenerObject listenerObj) {
		if(stateTransition == null) {
			String message = "unMatch transition relation";
			listenerObj.setMessage(message);
			listenerChain.doUnMatchTransition(listenerObj);
			return new FireResult(false,message);
		}
		R record = context.getRecord();
		R execRecord = context.getExecRecord();

		String currState = record.getState();
		listenerObj.setFsmName(this.name);
		listenerObj.setCurrState(currState);
		listenerObj.setEventName(stateTransition.getEvent());
		listenerObj.setRecId(record.getRecId());
		listenerObj.setContext(context);
		if (!currState.equals(execRecord.getState())) {
			listenerChain.declineTransition(listenerObj);
			String message = "execRecord state != record state,declined!";
			listenerObj.setMessage(message);
			return new FireResult(false,message);
		}
		listenerObj.setTransition(stateTransition.getTransition());
		return null;
	}


	/**
	 * 返回update是否成功
	 * @param context
	 * @param stateTransition
	 * @param listenerObj
	 * @return true 更新成功，false-更新失败
	 */
	protected FireResult fireInternal(C context, StateTransition<AbstractFsmAction> stateTransition, FsmListenerObject listenerObj) {
		R record = context.getRecord();
		R execRecord = context.getExecRecord();
        R append = context.getAppend();
		BeanOperator<R> beanOperator = BeanOperatorFactory.getBeanOperator(record.getClass());
		Interceptor interceptor = stateTransition.getInterceptor();

        if(stateTransition.getNextState() != null) {
        	String nextState = stateTransition.getNextState();
			execRecord.setState(nextState);
			append.setState(nextState);

			//0. action.preTransition
			stateTransition.getAction().preTransition(context);
			//1. listener 2.interceptor
			listenerChain.beforeTransition(listenerObj);
			interceptor.beforeTransition(stateTransition.getTransition(), context);

			boolean updateSuccess;
            try{
				updateSuccess = transactionUpdateState(context, stateTransition);
				if(!updateSuccess) {
					listenerChain.transferFailure(listenerObj);
					return new FireResult(false,"执行update失败",stateTransition);
				} else {
					listenerChain.transferSuccess(listenerObj);
				}
			} catch(RuntimeException e) {
				listenerChain.transferException(listenerObj,e);
				throw new RuntimeException(e);
			}
            beanOperator.override(record, append);
        }

        //1. listener 2. interceptor
		listenerChain.beforeExecute(listenerObj);
		interceptor.beforeExecute(stateTransition.getTransition(), context);
		E nextEvent;
		RuntimeException re = null;
		try{
			nextEvent = (E)stateTransition.getAction().execute(context);
		} catch(RuntimeException e) {
			re = e;
			throw e;
		} finally {
			interceptor.afterExecute(stateTransition.getTransition(), context,re);
			listenerChain.afterExecute(listenerObj,re);
		}

		if(nextEvent != null) {
			return this.fireWithResult(nextEvent, context);
		} else {
			return new FireResult(true, stateTransition);
		}
	}

	/**
	 * 事务同步更新
	 * @param context
	 * @param stateTransition
	 * @return
	 */
	public abstract boolean transactionUpdateState(C context, StateTransition stateTransition);

	public String getName() {
		return name;
	}

	public FsmListenerChain getListenerChain() {
		return listenerChain;
	}

	public void setListenerChain(FsmListenerChain listenerChain) {
		this.listenerChain = listenerChain;
	}

	/**
	 * 上一个迁移记录
	 */
	@Data
	class LastTransition {
		private FsmListenerObject listenerObject;
		private StateTransition transition;

		public LastTransition(FsmListenerObject listenerObject, StateTransition transition) {
			this.listenerObject = listenerObject;
			this.transition = transition;
		}
	}
}