package red.psyche.fsm.listener;

/**
 * @author inthendsun created on 2019-6-23
 */
public class FsmMuteListener implements FsmListener{
    @Override
    public void unMatchTransition(FsmListenerObject listenerObject) {
    }

    @Override
    public void declineTransition(FsmListenerObject listenerObject) {
    }

    @Override
    public void beforeTransition(FsmListenerObject listenerObject) {

    }

    @Override
    public void transferFailure(FsmListenerObject listenerObject) {

    }

    @Override
    public void transferSuccess(FsmListenerObject listenerObject) {

    }

    @Override
    public void transferException(FsmListenerObject listenerObject, RuntimeException e) {

    }

    @Override
    public void beforeExecute(FsmListenerObject listenerObject) {

    }

    @Override
    public void afterExecute(FsmListenerObject listenerObject, RuntimeException e) {

    }
}
