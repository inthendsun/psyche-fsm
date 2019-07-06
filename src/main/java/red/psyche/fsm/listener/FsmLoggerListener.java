package red.psyche.fsm.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author inthendsun created on 2019-6-23
 */
public class FsmLoggerListener implements FsmListener {

    private Logger logger = LoggerFactory.getLogger(FsmLoggerListener.class);


    private Object getAppend(FsmListenerObject listenerObject) {
        return listenerObject.getContext().getAppend();
    }

    @Override
    public void unMatchTransition(FsmListenerObject listenerObject) {
        logger.info("unMatchTransition {}",listenerObject);
    }

    @Override
    public void declineTransition(FsmListenerObject listenerObject) {
        logger.info("declineTransition {}",listenerObject);
    }

    @Override
    public void beforeTransition(FsmListenerObject listenerObject) {
        logger.info("beforeTransition {}",listenerObject);
    }

    @Override
    public void transferFailure(FsmListenerObject listenerObject) {
        logger.info("transferFailure id={},transition=",listenerObject.getRecId(),
                                                        listenerObject.getTransition());
    }

    @Override
    public void transferSuccess(FsmListenerObject listenerObject) {
        logger.info("transferSuccess,id={},transition={},append={}",listenerObject.getRecId(),
                                                                    listenerObject.getTransition(),
                                                                    getAppend(listenerObject));
    }

    @Override
    public void transferException(FsmListenerObject listenerObject, RuntimeException e) {
        logger.error("transferException id=" + listenerObject.getRecId(),e);
    }

    @Override
    public void beforeExecute(FsmListenerObject listenerObject) {
        logger.info("beforeExecute,id={},transition={},execRecord={}",listenerObject.getRecId(),
                                                                      listenerObject.getTransition(),
                                                                      listenerObject.getContext().getExecRecord());
    }

    @Override
    public void afterExecute(FsmListenerObject listenerObject, RuntimeException e) {
        if(e == null) {
            logger.info("afterExecuteSuccess,id={},transition={},execRecord={}",listenerObject.getRecId(),
                    listenerObject.getTransition(),
                    listenerObject.getContext().getExecRecord());
        } else {
            logger.error("afterExecuteException,execRecord=" + listenerObject.getContext().getExecRecord(),e);
        }

    }
}
