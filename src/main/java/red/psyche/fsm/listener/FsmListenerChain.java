package red.psyche.fsm.listener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author inthendsun
 */
public class FsmListenerChain {

    private List<FsmListener> chain = new ArrayList<FsmListener>();


    public FsmListenerChain() {
        chain.add(new FsmLoggerListener());
    }

    /**
     * 清空listener
     */
    public void clearListener() {
        this.chain.clear();
    }

    /**
     * 添加监听器
     * @param listener
     * @return
     */
    public FsmListenerChain addListener(FsmListener listener) {
        chain.add(listener);
        return this;
    }

    /**
     * 循环listener
     * @param method
     */
    private void loop(FsmListenerMethod method) {
        int size = chain.size();
        for (int i = 0; i < size; i++) {
            FsmListener fsmListener = chain.get(i);
            method.doListen(fsmListener);
        }
    }


    /**
     * 未匹配迁移事件
     * @param listenerObject
     */
    public void doUnMatchTransition(final FsmListenerObject listenerObject) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.unMatchTransition(listenerObject);
            }
        });
    }

    /**
     * 拒绝迁移
     * @param listenerObject
     */
    public void declineTransition(final FsmListenerObject listenerObject) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.declineTransition(listenerObject);
            }
        });
    }

    /**
     * 迁移前
     * @param listenerObject
     */
    public void beforeTransition(final FsmListenerObject listenerObject) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.beforeTransition(listenerObject);
            }
        });
    }

    /**
     * 迁移失败
     * @param listenerObject
     */
    public void transferFailure(final FsmListenerObject listenerObject) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.transferFailure(listenerObject);
            }
        });
    }

    /**
     * 迁移成功
     * @param listenerObject
     */
    public void transferSuccess(final FsmListenerObject listenerObject) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.transferSuccess(listenerObject);
            }
        });
    }

    /**
     * 迁移出现异常
     * @param listenerObject
     * @param e
     */
    public void transferException(final FsmListenerObject listenerObject, final RuntimeException e) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.transferException(listenerObject,e);
            }
        });
    }

    /**
     * 开始执行方法
     * @param listenerObject
     */
    public void beforeExecute(final FsmListenerObject listenerObject) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.beforeExecute(listenerObject);
            }
        });
    }

    /**
     * 执行方法后
     * @param listenerObject
     * @param e
     */
    public void afterExecute(final FsmListenerObject listenerObject, final RuntimeException e) {
        loop(new FsmListenerMethod() {
            @Override
            public void doListen(FsmListener fsmListener) {
                fsmListener.afterExecute(listenerObject,e);
            }
        });
    }

    /**
     *
     */
    interface FsmListenerMethod {
        /**
         * 执行Listen
         * @param fsmListener
         */
        void doListen(FsmListener fsmListener);
    }
}

