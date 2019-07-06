package red.psyche.fsm.listener;


/**
 * @author inthendsun
 */
public interface FsmLogPersister {

	/**
	 * 迁移准备
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
	 * 迁移异常
	 * @param listenerObject
	 * @param e
	 */
	void transferException(FsmListenerObject listenerObject, RuntimeException e);
}
