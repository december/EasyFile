package test;
import org.apache.commons.net.ftp.*;

import java.io.File;
import java.util.*;

import basic.*;
public class Test {
	public static void main(String[] args)
	{
		Connection conn=new Connection();
		System.out.println(conn.connection("127.0.0.1"));
		System.out.println(conn.login("anonymous", ""));
//		System.out.println(conn.changeWorkingDirectory("/MeePo/"));
//		System.out.println(conn.getPath());
//		System.out.println(conn.downLoadFile(conn.getPath()+File.separator+"2.ico", "D:"+File.separator+File.separator+"ico.css"));
//		System.out.println(conn.updateFile("F:\\alu.vhd", "/alu.vhd"));
//		System.out.println(conn.rename("/fuckxj", "/fuckljb"));
//		System.out.println(conn.fileIsExist("/orzljb.vhd"));
//		System.out.println(conn.makeDirectory("/fuckxj"));
//		System.out.println(conn.removeDirectory("/fuckljb"));
//		List<FTPFile>files = conn.showFileList();
//		for (int i = 0;i < files.size(); i++)
//			System.out.println(files.get(i).getName());
		LabelManager label = new LabelManager(conn);
		ArrayList<LGKLabel> labelList = label.getLabelList();
		LGKLabel t;
//		Label t = new Label("high", 0, "180cm");
//		label.addNewKindLabel(t);
//		label.deleteLabel(labelList.get(1));
//		Label t = new Label("high", 1, "180cm");
//		ArrayList<String> s = new ArrayList<String>();
//		s.add("180cm");
//		s.add("170cm");
//		t.setValueList(s);
//		label.addNewKindLabel(t);
/*		HashMap<String, ArrayList<LGKLabel>> map=label.getFileLabel();
		t = new LGKLabel("high", 1, "170cm");
		LGKLabel t2=new LGKLabel("high",1,"180cm");
		label.changeLabel("/test2.txt", t, t2);
		t=map.get("/test2.txt").get(0);
		System.out.println(t.getKey()+" "+t.getValue());
		for (LGKLabel tmp : labelList)
			System.out.println(tmp.getKey()+" "+tmp.getType()+" "+tmp.getValue());
		label.upload();*/
		label.renameFile("/test2.txt", "/test3.txt");
		conn.close();
	}
}
