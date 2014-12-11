/**
 * 处理排序操作的初始化类
 */
package display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理排序操作的初始化类
 * 
 * @author luyf
 *
 */
public class JExplorerConstant {
	public static final String TITLE = "JExplorer";
	// About Dialog Parameter
	/*
	 * public static final String ABOUT_INFO = "About JExplorer"; public static
	 * final String ABOUT_AUTHOR = "Marquis Tianzi Hou<Μπ²ΛΊξΎτ>"; public static
	 * final String ABOUT_VERSION = "v0.5"; public static final String
	 * ABOUT_EMAIL = "talent_marquis@163.com"; public static final String
	 * ABOUT_URL = ""; public static final String ABOUT_FILE_PATH = "about.txt";
	 */

	// Setting Dialog Parameter

	// File Copy Cut Paste
	public static final String FILE_COPY_SEPARATOR = "#=sep=#";
	public static final int FILE_IO_BUFFER_SIZE = 4096;

	// FileSortType
	public static final String SORT_TYPE_VALUE = "文件名, 文件大小, 文件类型, 修改时间";
	public static final String SORT_TYPE_KEY = "文件名, 文件大小, 文件类型, 修改时间";

	public static Map<String, Integer> getSortMap() {
		Map<String, Integer> sortMap = new HashMap<String, Integer>();
		Map<String, String> sortTypeMap = getSortTypeMap();
		List<String> sortTypeList = getSortTypeList();

		for (int i = 0; i < sortTypeList.size(); i++) {
			int column = i + 1;
			String sortType = sortTypeMap.get(sortTypeList.get(i));
			sortMap.put(sortType, column);
		}

		return sortMap;
	}

	public static Map<String, String> getSortTypeMap() {
		return getSortTypeMap(SORT_TYPE_KEY, SORT_TYPE_VALUE);
	}

	public static Map<String, String> getSortTypeMap(String keys, String values) {
		String[] keyArray = keys.split(",");
		String[] valueArray = values.split(",");
		Map<String, String> sortTypeMap = new HashMap<String, String>();
		for (int i = 0; i < keyArray.length; i++) {
			sortTypeMap.put(keyArray[i].trim(), valueArray[i].trim());
		}
		return sortTypeMap;
	}

	public static List<String> getSortTypeList() {
		return getSortTypeList(SORT_TYPE_KEY);
	}

	public static List<String> getSortTypeList(String types) {
		String[] typeArray = types.split(",");
		List<String> sortTypeList = new ArrayList<String>();
		for (String s : typeArray) {
			sortTypeList.add(s.trim());
		}
		return sortTypeList;
	}
}
