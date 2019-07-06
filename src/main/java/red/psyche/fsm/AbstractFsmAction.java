package red.psyche.fsm;


/**
 * @author inthendsun
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractFsmAction<E,C extends AbstractFsmContext> {

	/**
	 * 类似mvc中flashAttribute，get后就删除。
	 */
	private ThreadLocal<Object> flashAttribute = new ThreadLocal<Object>();

	public void setFlashAttribute(Object flashObject) {
		flashAttribute.set(flashObject);
	}

	public Object getFlashAttribute() {
		Object obj = flashAttribute.get();
		flashAttribute.remove();
		return obj;
	}

	/**
	 * 转换前
	 * @param context
	 */
	public void preTransition(C context) {
	}

	/**
	 * 在更新状态机状态时同步执行其他更新语句
	 */
	public boolean txChangeState(C context) {
		return true;
	}

	/**
	 * 执行
	 * @param context
	 */
	public abstract E execute(C context);

}
