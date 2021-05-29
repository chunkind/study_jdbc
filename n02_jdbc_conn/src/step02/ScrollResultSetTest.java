package step02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ScrollResultSetTest {
	Connection conn;
	Statement st;
	ResultSet rs;

	//oracle
//	static final String PASS = "tiger";
//	static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
//	static final String USER = "scott";

	//mysql
	static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost:3306/mysql?characterEncoding=utf-8";
	static final String USER = "root";
	static final String PASS = "wnstjd88";
	
	ScrollResultSetTest()throws Exception{
		String query = "";
		int row = 0;

		//1. 드라이버 로딩
		Class.forName(DRIVER_NAME);
		System.out.println("Driver Loading.....");
				
		//2. 디비연결
		conn = DriverManager.getConnection(URL, USER, PASS);
		System.out.println("db conn.....");
		
		//3. statement 생성
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		System.out.println("statement 생성");

		//4. 임시로 데이터 넣기.
		query = "DELETE FROM member";
		row = st.executeUpdate(query);
		System.out.println(row + " data clean..");

		query = "INSERT INTO member VALUES('sung', '성유리', 31)";
		row = st.executeUpdate(query);
		System.out.println(row+" row insert ok....");

		query = "INSERT INTO member VALUES('jea', '김제동', 42)";
		row = st.executeUpdate(query);
		System.out.println(row+" row insert ok....");

		query = "INSERT INTO member VALUES('lee', '이동휘', 39)";
		row = st.executeUpdate(query);
		System.out.println(row+" row insert ok....");

		//5. 샐랙트해서 ResultSet에 담기.
		query = "SELECT * FROM member";
		rs = st.executeQuery(query);
		
		//6. 커서의 위치를 EOE로 가도록 로직을 작성
		System.out.println("=====afterLast()======");
		rs.afterLast();

		//eoe이면..true
		System.out.println("isAfterLast :"+rs.isAfterLast());

		//6. rs의 맨 아래 데이타 부터 위쪽으로 데이타가 출력되도록 로직을 작성
		System.out.println("========================회원정보==============================");
		if(rs != null) {
			while (rs.previous()) { //next()의 반대방향..위로 돈다
				System.out.println(
						rs.getString("id") + "\t"
								+ rs.getString("name") + "\t"
								+ rs.getString("age")
				);
			}
		}
		System.out.println("===========================================================");
	}

	public static void main(String[] args) throws Exception{
		new ScrollResultSetTest();
	}
}
















