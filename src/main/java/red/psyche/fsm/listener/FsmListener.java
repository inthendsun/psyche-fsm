package red.psyche.fsm.listener;

/**
 * 状态机的监听器
 * @author inthendsun
 */
public interface FsmListener {

    /**
     * 未匹配迁移事件
     * @param listenerObject
     */
    void unMatchTransition(FsmListenerObject listenerObject);

    /**
     * 拒绝迁移
     * @param listenerObject
     */
    void declineTransition(FsmListenerObject listenerObject);

    /**
     * 准备迁移
     * @param listenerObject
     */
    void beforeTransition(FsmListenerObject listenerObject);

    /**
     * 迁移失败
     * @param listenerObject
     */
    void transferFailure(FsmListenerObject listenerObject);

    /**
     * 迁移成功
     * @param listenerObject
     */
    void transferSuccess(FsmListenerObject listenerObject);

    /**
     * 迁移发生异常
     * @param listenerObject
     * @param e
     */
    void transferException(FsmListenerObject listenerObject, RuntimeException e);

    /**
     * 开始执行方法
     * @param listenerObject
     */
    void beforeExecute(FsmListenerObject listenerObject);

    /**
     * 执行方法结果
     * @param listenerObject
     * @param e
     */
    void afterExecute(FsmListenerObject listenerObject, RuntimeException e);
}
