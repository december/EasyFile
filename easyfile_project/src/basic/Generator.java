package basic;

import java.io.*;

public class Generator {

	/**
	 * 输入文件位置，输出文件位置，字符串分隔符，默认用户，默认密码，默认权限等级
	 */
	final static String originFile = "./rawinput.txt",
			outputFile = "DefaultCredential" + File.separator + "LastUser.cre";
	final static String stringSeparator = "\n";
	static String userName = "melancholy", password = "xushiqiang";
	static String permissionLevel = "3";

	/**
	 * 生成证书
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Type in userName : ");
			userName = in.readLine();
			System.out.print("Type in password : ");
			password = in.readLine();
			System.out.print("Type in permission level : ");
			permissionLevel = in.readLine();
			String ans = userName + stringSeparator + password
					+ stringSeparator + permissionLevel;
			while (true) {
				System.out.println("Type in one server : (-1 to exit)");
				String tmp = in.readLine();
				if (tmp.equals("-1"))
					break;
				ans = ans + stringSeparator + tmp;
			}
			in.close();
			File f = new File("DefaultCredential");
			//if (!f.exists() || f.isFile())
			//	f.mkdirs();
			ans = Coder.encryptBASE64(ans.getBytes());
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(outputFile));
			out.write(ans);
			out.close();
			System.out.println("Encoding process completed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
