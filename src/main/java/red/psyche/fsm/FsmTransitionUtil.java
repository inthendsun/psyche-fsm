package red.psyche.fsm;

import com.google.common.collect.Maps;
import red.psyche.fsm.interceptor.Interceptor;
import red.psyche.fsm.interceptor.NoopInterceptor;

import java.util.*;

/**
 *
 * @author inthendsun
 */
public class FsmTransitionUtil {

    public static String noopAction = "noopAction";
    private static NoopAction noopActionBean = new NoopAction();
    private static NoopInterceptor noopInterceptor = new NoopInterceptor();

    /**
     * 添加fsmDefiner。方法独立出来，支持主动添加
     * @param definer
     */
    public static Map<String, StateTransition> parse(FsmDefiner definer) {
        Map<String, StateTransition> map = Maps.newHashMap();
        List<FsmTransition> transitions = getTextTransitions(definer);
        for (FsmTransition transition : transitions) {
            map.put(transition.getKey(), convertTransition(transition,definer));
        }
        return map;
    }

    protected static List<FsmTransition> getTextTransitions(FsmDefiner definer) {
        List<FsmTransition> list = new ArrayList<FsmTransition>();
        String[] transitions = definer.getTextTransitions();
        for (String item : transitions) {
            String[] array = item.split("/");
            if (array.length != 4) {
                throw new IllegalArgumentException("非法的配置条目,长度必须是4:" + item);
            }
            String currState = array[0];
            String eventName = array[1];
            String nextState = array[2];

            String actionBean = array[3];
            String transitInterceptorBean = null;
            if(actionBean.contains("@")) {
                String[] tmpArray = actionBean.split("@");
                if(tmpArray.length != 2) {
                    throw new IllegalArgumentException("错误的状态机配置" + definer + ","+ item);
                }
                actionBean = tmpArray[0];
                transitInterceptorBean = tmpArray[1];
            }

            Enum.valueOf(definer.getStateEnum(), currState);
            Enum.valueOf(definer.getEventEnum(), eventName);
            Enum.valueOf(definer.getStateEnum(), nextState);

            FsmTransition transition = new FsmTransition(currState, eventName, nextState, actionBean);
            transition.setInterceptBean(transitInterceptorBean);
            list.add(transition);
        }
        return list;
    }

    protected static StateTransition<AbstractFsmAction> convertTransition(FsmTransition transition, FsmDefiner definer) {
        AbstractFsmAction action;
        if (transition.getActionBean().equals(noopAction)) {
            action = noopActionBean;
        } else {
            action = definer.getBean(transition.getActionBean(),AbstractFsmAction.class);
        }
        String currState = transition.getCurrState();
        String eventName = transition.getEventName();
        String nextState = transition.getNextState();

        StateTransition stateTransition = new StateTransition<AbstractFsmAction>(currState,nextState, eventName, action, transition);
        Interceptor interceptor;
        if(transition.getInterceptBean() != null) {
            interceptor = definer.getBean(transition.getInterceptBean(), Interceptor.class);
        } else {
            interceptor = noopInterceptor;
        }
        stateTransition.setInterceptor(interceptor);
        return stateTransition;
    }
}
