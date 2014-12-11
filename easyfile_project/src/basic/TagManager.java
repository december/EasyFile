package basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPFile;

public class TagManager {
	Connection connection;
	ArrayList<ArrayList<String> > tags;
	final String tagFileName = "/tags.easyfile";
	
	public TagManager(Connection con)
	{
		File tmp = new File("./tag");
		//if (!tmp.exists()) tmp.mkdirs();
		this.connection = con;
		tags = new ArrayList<ArrayList<String> >();
		initTag();
	}

	private void initTag() {
		String curPath = connection.getPath();
		connection.changeWorkingDirectory("/");
		FTPFile file = connection.getFile(tagFileName);
		if (file != null)
			if (connection.downLoadFile(tagFileName, "./tag" + tagFileName))
			{
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(
							new FileInputStream(new File("./tag" + tagFileName))));
					ArrayList<String> tmp = new ArrayList<String>();
					while (true)
					{
						String buf = in.readLine();
						if (buf.startsWith("file:"))
						{
							tags.add(tmp);
							tmp = new ArrayList<String>();
							tmp.add(buf.substring(5));
						}
						if (buf.equals("file:"))
							break;
						if (buf.startsWith("tags:"))
							tmp.add(buf.substring(5));
					}
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		connection.changeWorkingDirectory(curPath);
	}
	
	public void addTag(String fileName, ArrayList<String> newtags)
	{
		//if (fileName.charAt(1) == '/')
		//	fileName = fileName.substring(1);
		for (ArrayList<String> tag : tags)
			if (tag.size() > 0 && tag.get(0).equals(fileName))
			{
				/*for (String newtag : newtags)
					if (notDup(tag, newtag))
						tag.add(newtag);
				upload();*/
				return;
			}
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add(fileName);
		for (String newtag : newtags)
			tmp.add(newtag);
		tags.add(tmp);
		upload();
	}

	/*
	private boolean notDup(ArrayList<String> tag, String newtag) {
		for (int i = 1; i < tag.size(); ++i)
			if (tag.get(i).equals(newtag))
				return false;
		return true;
	}
	*/

	private void upload() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("./tag" + tagFileName))));
			for (ArrayList<String> tag : tags)
				if (tag.size() > 0)
				{
					out.write("file:" + tag.get(0) + "\n");
					for (int i = 1; i < tag.size(); ++i)
						out.write("tags:" + tag.get(i) + "\n");
				}
			out.write("file:\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String curPath = connection.getPath();
		connection.changeWorkingDirectory("/");
		connection.updateFile("./tag" + tagFileName, tagFileName);
		connection.changeWorkingDirectory(curPath);
	}
	
	public void delTag(String fileName, String deltag)
	{
		//if (fileName.charAt(1) == '/')
		//	fileName = fileName.substring(1);
		for (ArrayList<String> tag : tags)
			if (tag.size() > 0 && tag.get(0).equals(fileName))
			{
				for (int i = 1; i < tag.size(); ++i)
					if (tag.get(i).equals(deltag))
					{
						tag.remove(i);
						upload();
						return;
					}
				//upload();
				//return;
			}
		upload();
	}
	
	public ArrayList<String> showTag(String fileName)
	{
		//if (fileName.charAt(1) == '/')
		//	fileName = fileName.substring(1);
		ArrayList<String> tmp = new ArrayList<String>();
		for (ArrayList<String> tag : tags)
			if (tag.size() > 0 && tag.get(0).equals(fileName))
			{
				for (int i = 1; i < tag.size(); ++i)
					tmp.add(tag.get(i));
				return tmp;
			}
		return tmp;
	}
	
	public void delFile(String fileName)
	{
		//if (fileName.charAt(1) == '/')
		//	fileName = fileName.substring(1);
		for (ArrayList<String> tag : tags)
			if (tag.size() > 0 && tag.get(0).equals(fileName))
			{
				tags.remove(tag);
				upload();
				return;
			}
	}
}
