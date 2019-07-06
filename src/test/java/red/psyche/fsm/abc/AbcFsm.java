package red.psyche.fsm.abc;

import red.psyche.fsm.FsmDefiner;
import red.psyche.fsm.StateTransition;
import red.psyche.fsm.spring.AbstractSpringFsm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbcFsm extends AbstractSpringFsm<AbcState,AbcEvent,AbcRecord, AbcFsmContext> {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public AbcFsm(FsmDefiner definer) {
		super(definer);
	}

	@Override
	protected boolean updateFSMState(StateTransition stateTransition,AbcFsmContext context) {
		log.info("执行更新语句，更新内容={}", context.getAppend());
		return true;
	}



}
