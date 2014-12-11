package basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

import org.apache.commons.net.ftp.FTPFile;

/**
 * 用于管理标签ftp标签系统
 * @author lai
 *
 */
public class LabelManager 
{
	Connection conn;
	ArrayList<LGKLabel> labelList;
	final String labelFileName = "/labels.easyfile";
	final String configFileName = "/config.easyfile";
	HashMap<String, ArrayList<LGKLabel>> label;
	BaiduConnection baiduConn = null;
	
	public BaiduConnection getBaiduConn() {
		return baiduConn;
	}
	
	public void setBaiduConn(BaiduConnection baiduConn) {
		this.baiduConn = baiduConn;
	}

	boolean flag = true;
	
	/**
	 * 初始化标签管理器
	 */
	public LabelManager()
	{
		labelList = new ArrayList<LGKLabel>();
		label = new HashMap<String, ArrayList<LGKLabel>>();
	}
	
	/**
	 * 使用ftp链接初始化标签管理器
	 * @param conn ftp链接
	 */
	public LabelManager(Connection conn)
	{
		labelList = new ArrayList<LGKLabel>();
		label = new HashMap<String, ArrayList<LGKLabel>>();
		this.conn=conn;
		init();
	}
	
	/**
	 * 读入标签文件
	 */
	void init()
	{
		String curPath = conn.getPath();
		conn.changeWorkingDirectory("/");
		FTPFile file = conn.getFile(configFileName);
		if (file != null)
		{
			if (conn.downLoadFile(configFileName, "./tag" + configFileName))
			{
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(
							new FileInputStream(new File("./tag" + configFileName))));
					while (true)
					{
						String buf = in.readLine();
						if (buf == null)
							break;
						String[] words = buf.split("\t");
						LGKLabel tmp = new LGKLabel(words[0], Integer.parseInt(words[1]), words[2]);
						if (tmp.getType() ==1)
						{
							buf = in.readLine();
							String[] tmpLists = buf.split("\t");
							ArrayList<String> valueList = new ArrayList<String>();
							for (int k = 0; k<tmpLists.length ;k++)
								valueList.add(tmpLists[k]);
							tmp.setValueList(valueList);
						}
						labelList.add(tmp);
					}
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else flag = false;
		file = conn.getFile(labelFileName);
		if (file != null)
		{
			if (conn.downLoadFile(labelFileName, "./tag" + labelFileName))
			{
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(
							new FileInputStream(new File("./tag" + labelFileName))));
					String filename = null;
					while (true)
					{
						String buf = in.readLine();
						if (buf == null)
							break;
						String[] words = buf.split("\t");
						if (words[0].equals("file"))
						{
							filename=words[1];
							label.put(filename, new ArrayList<LGKLabel>());
						}
						else
						{
							if (isExist(words[0],words[1]))
							{
								label.get(filename).add(new LGKLabel(words[0], 0, words[1]));
							}
						}
					}
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else flag = false;
		conn.changeWorkingDirectory(curPath);		
	}
	
	/**
	 * 判断这个value对于这个key是否合法
	 * @param key key值
	 * @param value value值
	 * @return 是否合法
	 */
	boolean isExist(String key, String value)
	{
		for (LGKLabel tmp : labelList)
		{
			if (tmp.getKey().equals(key))
			{
				if (tmp.getType() == 0) 
					return true;
				else
				{
					for (String v : tmp.getValueList())
					{
						if (v.equals(value))
							return true;
					}
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * 返回这个ftp系统是否有标签
	 * @return 是否有标签系统
	 */
	public boolean isLabelOrNot()
	{
		return flag;
	}
	
	/**
	 * 返回这个ftp系统所有文件对应的标签
	 * @return 一个hash表表示一个文件所对应的一个标签列表
	 */
	public HashMap<String, ArrayList<LGKLabel>> getFileLabel()
	{
		return label;
	}
	
	/**
	 * 得到这个ftp系统有什么标签，返回一个标签列表。
	 * @return 标签列表包含系统所有标签类型
	 */
	public ArrayList<LGKLabel> getLabelList()
	{
		return labelList;
	}
	
	/**
	 * 删除某个文件的所有标签
	 * @param filename 文件路径
	 */
	public void deleteLabel(String filename)
	{
		filename=trans(filename);
		if (label.get(filename) != null)
			label.remove(filename);
		upload();
	}
	
	/**
	 * 删除文件的一个标签
	 * @param filename 文件路径
	 * @param tmp 要删除的标签
	 */
	public void deleteLabel(String filename, LGKLabel tmp)
	{
		filename=trans(filename);
		if (label.get(filename) != null)
		{
			for (LGKLabel t : label.get(filename))
			{
				if (t.getKey().equals(tmp.getKey()))
				{
					labelList.remove(t);
					break;
				}
			}
		}
		upload();
	}
	
	/**
	 * 对一个文件插入一个标签列表
	 * @param filename 文件路径
	 * @param newLabel 新的标签列表
	 */
	public void addLabel(String filename, ArrayList<LGKLabel> newLabel)
	{
		System.out.println(filename);
		filename=trans(filename);
		if (label.get(filename) == null)
			label.put(filename, newLabel);
		else
		{
			label.remove(filename);
			label.put(filename, newLabel);
		}
		upload();
	}
	
	/**
	 * 对文件插入一个标签，如果标签已存在，那么更新为新的值。
	 * @param filename 文件路径
	 * @param newLabel 新的标签
	 */
	public void addLabel(String filename, LGKLabel newLabel)
	{
		System.out.println("new labels");
		filename=trans(filename);
		if (label.get(filename) == null)
			label.put(filename, new ArrayList<LGKLabel>());
		for (LGKLabel tmp : label.get(filename))
			if (tmp.getKey().equals(newLabel.getKey()))
			{
				System.out.println(newLabel.getValue());
				tmp.setValue(newLabel.getValue());
				upload();
				return;
			}
		label.get(filename).add(newLabel);
		upload();
	}
	
	/**
	 * 改变一个文件的一个标签
	 * @param filename 文件路径
	 * @param oldLabel 旧标签
	 * @param newLabel 新标签
	 */
	public void changeLabel(String filename, LGKLabel oldLabel, LGKLabel newLabel)
	{
		filename=trans(filename);
		deleteLabel(filename, oldLabel);
		addLabel(filename, newLabel);
		upload();
	}
	
	/**
	 * 添加一种标签类型
	 * @param key 标签的key值
	 * @param type 标签的类型
	 * @param defaultValue 标签的默认值
	 * @param valueList 标签可选值列表
	 */
	public void addNewKindLabel(LGKLabel newLabel)
	{
		for (LGKLabel tmp : labelList)
			if (tmp.getKey().equals(newLabel.getKey()))
				return;
		if (newLabel.getType() == 1 && newLabel.getValueList() == null)
			return;
		labelList.add(newLabel);
		upload();
	}
	
	/**
	 * 删除一个标签类型
	 * @param label 标签
	 */
	public void deleteLabel(LGKLabel label)
	{
		for (LGKLabel tmp : labelList)
		{
			if (tmp.getKey().equals(label.getKey()))
			{
				labelList.remove(tmp);
				break;
			}
		}
		upload();
	}
	
	/**
	 * 更改一个文件的标签
	 * @param filename 文件路径
	 * @param newLabel 新的标签
	 */
	public void notifyFileLabel(String filename, LGKLabel newLabel)
	{
		filename=trans(filename);
		if (label.get(filename) == null)
			return;
		for (LGKLabel tmp : label.get(filename))
			if (tmp.getKey().equals(newLabel.getKey()))
			{
				tmp.setValue(newLabel.getValue());
				break;
			}
		upload();
	}
	
	/**
	 * 更改一个标签类型的属性，但是不能给该key值。
	 * @param newLabel 新的标签属性
	 */
	public void notifyLabel(LGKLabel newLabel)
	{
		for (LGKLabel tmp : labelList)
		{
			if (tmp.getKey().equals(newLabel.getKey()))
			{
				//tmp.setType(newLabel.getType());
				//tmp.setValue(newLabel.getValue());
				//tmp.setDescription(newLabel.getDescription());
				//tmp.setValueList(newLabel.getValueList());
				break;
			}
		}
		upload();
	}
	
	/**
	 * 重命名一个文件，对标签系统进行相应操作
	 * @param oldName 旧文件名
	 * @param newName 新文件名
	 */
	public void renameFile(String oldName, String newName)
	{
		oldName=trans(oldName);
		newName=trans(newName);
		if (label.get(oldName) == null)
			return ;
		//if (label.get(newName) != null)
		//	return ;
		ArrayList<LGKLabel> tmp = label.get(oldName);
		label.put(newName, tmp);
		label.remove(oldName);
		upload();
	}
	
	/**
	 * 上传标签系统文件到ftp上。
	 */
	public void upload()
	{
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("./tag" + configFileName))));
			for (LGKLabel label : labelList)
			{
				out.write(label.getKey() + "\t" + String.valueOf(label.getType()) + "\t" + label.getValue() + "\n");
				if (label.getType() == 1)
				{
					ArrayList<String> valueList = label.getValueList();
					for (int i = 0; i < valueList.size(); i++)
					{
						out.write(valueList.get(i));
						if (i != valueList.size())
							out.write('\t');
					}
					out.write("\n");
				}
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("./tag" + labelFileName))));
			if (label.keySet() != null)
				for (String filename : label.keySet())
				{
					out.write("file\t" + filename + "\n");
					for (LGKLabel tmp : label.get(filename))
						out.write(tmp.getKey() + "\t" + tmp.getValue() + "\n");
				}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String curPath = conn.getPath();
		conn.changeWorkingDirectory("/");
		conn.updateFile("./tag" + configFileName, configFileName);
		conn.updateFile("./tag" + labelFileName, labelFileName);
		conn.changeWorkingDirectory(curPath);		
		/*if (baiduConn != null)
		{
			baiduConn.updateFile("./tag" + configFileName, configFileName);
			baiduConn.updateFile("./tag" + labelFileName, labelFileName);
		}*/
	}
	
	String trans(String str)
	{
		if (str.length() >=1 && str.charAt(1) == '/')
			return str.substring(1);
		return str;
	}
}