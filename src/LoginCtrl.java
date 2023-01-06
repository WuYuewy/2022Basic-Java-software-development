
/* 登录系统后台处理 */

import static java.lang.System.err;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LoginCtrl {
	public static ArrayList<User> list = new ArrayList<User>(); //存放用户数据的列表
	/* 由于在VScode环境下，使用的是绝对路径 */
	private static String dir = "E:\\Codefield\\CODE_JAVA\\Project\\App\\src\\user.txt"; // 存放用户数据的文件

	// 载入用户信息
	public static void LoadUser() {
		try {
			// 读文件
			BufferedReader reader = new BufferedReader(new FileReader(new File(dir)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(" ");
				User user = new User(data[0], data[1]);
				user.setBestScore(Integer.parseInt(data[2]));
				list.add(user);
			}
			reader.close();

			// 处理信息 排序并去重
			Collections.sort(list);
			ArrayList<User> newList = new ArrayList<User>();
			for (int i = 0; i < list.size(); i++) {
				boolean isContains = newList.contains(list.get(i));
				if (!isContains)
					newList.add(list.get(i));
			}
			list.clear();
			list.addAll(newList);
		} catch (IOException e) {
			err.println("用户文件数据" + dir + "找不到");
			e.printStackTrace();
		}
	}

	// 判断用户是否存在
	public static boolean findUser(String account, String pwd) {
		return list.contains(new User(account, pwd));
	}

	// 根据用户名找用户
	public static User findAccount(String account) {
		LoadUser();
		for (User u : list)
			if (u.getAccount().equalsIgnoreCase(account))
				return u;
		return null;
	}

	// 添加新的用户
	public static void addNewUser(User user) {
		list.add(user);
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(new File(dir), true));
			writer.write(user.getAccount() + " " + user.getPwd() + " " + user.getBestScore() + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			err.println("无法写入用户文件数据 user.txt");
			e.printStackTrace();
		}
		LoadUser();
	}

	// 更新用户最高分为bestscore
	public static void updateBestScore(String username, int bestscore) {
		StringBuffer bufAll = new StringBuffer();	
		try {		
			BufferedReader reader = new BufferedReader(new FileReader(new File(dir)));	
			String line = null;		
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(" ");
				// 依据用户名 找到对应分数并修改
				if (username.equals(data[0])) {
					bufAll.append(data[0] + " " + data[1] + " " + bestscore);
					bufAll.append(System.getProperty("line.separator"));
				}
				// 其他内容保留
				else {
					bufAll.append(line);
					bufAll.append(System.getProperty("line.separator"));
				}
			}
			reader.close();
			// 写入文件
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir)));
			writer.write(bufAll.toString());
			writer.flush();
			writer.close();
			LoadUser();
		} catch (IOException e) {
			err.println("无法写入用户文件数据 user.txt");
			e.printStackTrace();
		}
	}
}

