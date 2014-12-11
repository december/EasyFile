package logic.provider;

/**
 * 提供了某个标签的值的容器这个类
 * 
 * @author Eternal_Answer
 *
 */
public class Sign {
	/**
	 * 标签的序号
	 */
	public int idx;
	/**
	 * 标签的类型
	 */
	public int type;
	/**
	 * 标签的值
	 */
	public String value;

	/**
	 * 构造函数
	 * 
	 * @param _idx
	 *            值序号
	 * @param _type
	 *            标签种类
	 * @param _value
	 *            标签的默认值
	 */
	public Sign(int _idx, int _type, String _value) {
		idx = _idx;
		type = _type;
		value = _value;
	}
}
