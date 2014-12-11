package basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * 此类继承自labelmanager，作为ftp无法使用时，网盘标签系统与前端的adpater
 * 上层可以直接重用，不过注意初始化的时候使用baiduconnection作为初始化函数。
 * @author lai
 *
 */
public class WPLabelManager extends LabelManager{
	private BaiduConnection conn;
	public WPLabelManager(BaiduConnection conn)
	{
		super();
		this.conn=conn;
		init();
	}
	/**
	 * 读取标签文件
	 */
	void init()
	{
		conn.downLoadFile(configFileName, "./wptag" + configFileName);
		conn.downLoadFile(labelFileName, "./wptag" + labelFileName);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("./wptag" + configFileName))));
			while (true)
			{
				String buf = in.readLine();
				if (buf == null)
					break;
				/*String[] words = buf.split("\t");
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
				labelList.add(tmp);*/
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("./wptag" + labelFileName))));
			String filename = null;
			while (true)
			{
				String buf = in.readLine();
				if (buf == null)
					break;
				/*String[] words = buf.split("\t");
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
				}*/
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 上传标签文件
	 */
	public void upload()
	{
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("./wptag" + configFileName))));
			for (LGKLabel label : labelList)
			{
				/*out.write(label.getKey() + "\t" + String.valueOf(label.getType()) + "\t" + label.getValue() + "\n");
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
				}*/
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("./wptag" + labelFileName))));
			if (label.keySet() != null)
				for (String filename : label.keySet())
				{
					/*out.write("file\t" + filename + "\n");
					for (LGKLabel tmp : label.get(filename))
						out.write(tmp.getKey() + "\t" + tmp.getValue() + "\n");*/
				}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		conn.updateFile("./wptag" + configFileName, configFileName);
		conn.updateFile("./wptag" + labelFileName, labelFileName);
	}
}
