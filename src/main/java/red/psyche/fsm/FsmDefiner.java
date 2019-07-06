package red.psyche.fsm;

/**
 * @author inthendsun
 */
@SuppressWarnings("rawtypes")
public interface FsmDefiner {

	/**
	 * 获取对应状态机的名字
	 * @return 返回对应状态机的名字
	 */
	String getName();

	/**
	 * 获取状态机定义内容
	 * @return
	 */
	String[] getTextTransitions();

	/**
	 * 返回状态机状态枚举类，用于校验状态是否正确
	 * @return
	 */
	Class<? extends Enum> getStateEnum();

	/**
	 * 返回状态机事件枚举类，用于校验事件填写是否正确
	 * @return
	 */
	Class<? extends Enum> getEventEnum();

	/**
	 * 获取Bean
	 * @param actionName
	 * @param clazz
	 * @return Action Bean or Interceptor Bean
	 */
	<T> T getBean(String actionName,Class<T> clazz);
}
