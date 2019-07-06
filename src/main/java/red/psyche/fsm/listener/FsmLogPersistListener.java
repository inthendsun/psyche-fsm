package red.psyche.fsm.listener;

import lombok.Data;

/**
 *
 * @author inthendsun created on 2019-6-23
 */
@Data
public class FsmLogPersistListener extends FsmMuteListener {

    private FsmLogPersister persister;

    /**
     * 状态机默认实现持久化日志记录监听，需要手工设置FsmLogPersister
     *
     */
    public FsmLogPersistListener() {
    }

    @Override
    public void beforeTransition(FsmListenerObject listenerObject) {
        if(this.persister != null) {
            persister.beforeTransition(listenerObject);
        }
    }

    @Override
    public void transferFailure(FsmListenerObject listenerObject) {
        if(this.persister != null) {
            persister.transferFailure(listenerObject);
        }
    }

    @Override
    public void transferSuccess(FsmListenerObject listenerObject) {
        if(this.persister != null) {
            persister.transferSuccess(listenerObject);
        }
    }

    @Override
    public void transferException(FsmListenerObject listenerObject, RuntimeException e) {
        if(this.persister != null) {
            persister.transferException(listenerObject,e);
        }
    }
}
