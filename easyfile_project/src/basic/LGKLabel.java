package basic;

import java.util.*;

/**
 * 存储标签类型属性和具体标签
 * @author lai
 *
 */
public class LGKLabel 
{
	/**
	 * 标签key值
	 */
	private String key;
	/**
	 * 标签种类，0为任意值，1为可选值。
	 */
	private int type; 
	/**
	 * 标签可选值类表，当标签属性为1是有用
	 */
	private ArrayList<String> valueList = null;
	/**
	 * 标签默认值或者标签值。
	 */
	private String value;
	/**
	 * 对标签的描述
	 */
	private String description="";

	/**
	 * 初始化标签
	 * @param key
	 * @param type
	 * @param valueList if this label belong a file , just set null
	 * @param defaultValue
	 */
	public LGKLabel(String key, int type, String defaultValue)
	{
		this.key = key;
		this.type = type;
		//if (defaultValue == null || defaultValue.length() == 0)
		//	defaultValue = " ";
		this.value = defaultValue;
		this.valueList = new ArrayList<String>();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value == null || value.length() == 0)
			value = " ";
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<String> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<String> valueList) {
		if (valueList != null)
		{
			this.valueList = valueList;
			if (valueList.size()>0)
				value=this.valueList.get(0);
		}
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
