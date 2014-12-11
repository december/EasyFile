package basic;

import java.util.*;

public class Authority 
{
	private List<String> userList;
	
	/**
	 * ��ʼ��Ȩ��ϵͳ��Ȩ�������ɸߵ���
	 * @param user1
	 * @param user2
	 * @param user3
	 */
	public Authority(String user1, String user2, String user3)
	{
		userList=new ArrayList<String>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
	}
	/**
	 * ����û�����һ�������Ȩ�ޣ����ӵ��Ȩ���򷵻�true������false��
	 * �ȹ�ӵ��4�ֲ�����download,upload,change,delete��
	 * @param command
	 * @param user
	 * @return
	 */
	public boolean getAuthority(String command, String user)
	{
		switch(command)
		{
			case "download": 
				return true;
			case "upload":
				if (user == userList.get(0) || user == userList.get(1))
					return true;
				else 
					return false;
			case "change":
				if (user == userList.get(0))
					return true;
				else
					return false;
			case "delete":
				if (user == userList.get(0))
					return true;
				else
					return false;				
			default: return false;
		}
	}
}
