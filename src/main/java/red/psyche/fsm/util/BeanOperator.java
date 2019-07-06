package red.psyche.fsm.util;

/**
 * @author inthendsun
 */
public interface BeanOperator<T> {
	/**
	 * two的某个属性值不等于null且与one的属性值不同，则记录
	 * @param one
	 * @param two
	 * @return
	 */
	public T append(T one,T two);
	
	/**
	 * two的某个属性值不等于null且与one的属性值不同，则用two值覆盖one值
	 * @param one
	 * @param two
	 * @return
	 */
	public void override(T one, T two);
	
	/**
	 * two的某个属性值不等于null且one的属性值是null，则用two值赋给one
	 * @param one
	 * @param two
	 */
	public void copyMissing(T one,T two);
}
