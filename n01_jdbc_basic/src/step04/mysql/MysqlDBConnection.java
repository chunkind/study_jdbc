package step04.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MysqlDBConnection {
	public MysqlDBConnection() throws Exception{
		//1. 드라이버 로딩 
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("드라이버 로딩 성공....");
		
		//2. 디비 연결
		String url ="jdbc:mysql://localhost:3306/mysql?characterEncoding=utf-8";
		String user = "root";
		String pass = "wnstjd88";
		Connection conn=DriverManager.getConnection(url,user,pass);
		System.out.println("디비 연결 성공....");
		
		//3. statement 생성
		Statement st = conn.createStatement();
		System.out.println("3. statement 객체 생성 성공...");
		
		//4. id = 'kim', name='김제동', age =40;을 한명 추가
		String query = "";
		int row = 0;

		query = "INSERT INTO member VALUES('momo02','박부리',29)";
		row = st.executeUpdate(query );
		System.out.println("INSERT ...ok.");
		
		//5. 장나라 라는 이름을 장보고로 수정...update 
		query = "UPDATE  member SET name= '빡부리' WHERE name= '박부리'";
		row = st.executeUpdate(query);
		System.out.println("UPDATE...ok");

		//6. 박과장을 삭제
		query = "DELETE FROM member WHERE name ='박과장'";
		st.executeUpdate(query);
		System.out.println("DELETE...OK...");
		
		//7.member 테이블에 있는 모든 데이타를 가져와서 콘솔로 출력 
		query = "SELECT * FROM member";
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			System.out.println(rs.getString(1)
					+"\t"+ rs.getString("name")
					+"\t" + rs.getInt("age"));
		}
	}
}