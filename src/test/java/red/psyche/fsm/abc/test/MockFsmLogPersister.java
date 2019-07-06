package red.psyche.fsm.abc.test;

import red.psyche.fsm.listener.FsmListenerObject;
import red.psyche.fsm.listener.FsmLogPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author inthendsun created on 2019-6-24
 */
public class MockFsmLogPersister implements FsmLogPersister {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeTransition(FsmListenerObject listenerObject) {
    }

    @Override
    public void transferFailure(FsmListenerObject listenerObject) {
    }

    @Override
    public void transferSuccess(FsmListenerObject listenerObject) {
        log.info("记录执行日志={}", "xxx");
    }

    @Override
    public void transferException(FsmListenerObject listenerObject, RuntimeException e) {
    }
}
