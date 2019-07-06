package red.psyche.fsm.abc.test;

import red.psyche.fsm.listener.FsmListenerObject;
import red.psyche.fsm.listener.FsmLogPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author inthendsun created on 2019-6-25
 */
@Component
public class AbcFsmLogPersister implements FsmLogPersister {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeTransition(FsmListenerObject listenerObject) {
    }

    @Override
    public void transferFailure(FsmListenerObject listenerObject) {
    }

    @Override
    public void transferSuccess(FsmListenerObject listenerObject) {
        logger.info("开始持久化...", listenerObject);
    }

    @Override
    public void transferException(FsmListenerObject listenerObject, RuntimeException e) {

    }
}
