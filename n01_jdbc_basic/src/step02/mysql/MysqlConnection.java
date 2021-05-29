package step02.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
	public MysqlConnection() {
		try {
			// 1.드라이버 로딩...
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loading ....ok...");
			// 2.디비 연결(특정한 디비서버의 주소로 직접 연결한다)
			String url = "jdbc:mysql://localhost:3306/mysql?characterEncoding=utf-8";
			Connection con = DriverManager.getConnection(url, "root", "wnstjd88");
			System.out.println("DBconnectting.....success....");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Loading ....fail...");
		} catch (SQLException e) {
			System.out.println("DBconnectting.....fail....");
			e.printStackTrace();
		}
	}
}
