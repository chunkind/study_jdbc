package step03.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class OracleConnection {
	public OracleConnection() throws Exception{
		 //1. 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("1. Driver Loading ok....");
		//2.DB 연결
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "hr";
		String pass = "hr";
		Connection conn =DriverManager.getConnection(url,user,pass);
		System.out.println("2. 디비 연결 성공...");

		Statement st = conn.createStatement();
		System.out.println("3. Statement 생성......");

		//member라는 테이블 생성...데이터가 없다.. 데이터로부터 insert하겠다
		String query = "";
		int row = 0;
		query = "INSERT INTO member VALUES('momo02','박부리',29)";
		row = st.executeUpdate(query);
		System.out.println("insert ok....."+row);
		query = "INSERT INTO member VALUES('chunkind','춘부리',34)";
		row = st.executeUpdate(query);
		System.out.println("insert ok....."+row);
	}
}
