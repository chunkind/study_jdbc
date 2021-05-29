package step01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Auth: chunkind@naver.com
 * @Date: 2021. 05. 29.
 *
 * DB정보를 상수(static final)로 정의하여 갖고 있도록 하자.
 * 그리고 CRUD를 작성해보자.
 *
 * ---------------------member table creating------------------------
 * DDL (Data Definition Language)
 * CREATE TABLE member(
 * 	id varchar2(20),
 * 	name varchar2(30),
 * 	age number(3)
 * );
 * 
 * DML (Data Manuplation Language) : SELECT문은 제외
 * INSERT INTO member (id, name, age) VALUES('kim','김제동',40);
 * INSERT INTO member VALUES('lee','이경규',53);
 * DELETE FROM member;
 * UPDATE member set name="", age=0 WHERE id = '';
 *
 * DB 접근하는 로직을 Business Logic이라 한다.
 * 이러한 Business Logic을 가지고 있는 클래스를 DAO라고 부른다.
 * DAO :: Data(Base) Aceess Objetc
 */
public class JDBC4StepTest {
	Connection conn;
	Statement st;
	ResultSet rs;

	//oracle
//	static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
//	static final String USER = "scott";
//	static final String PASS = "tiger";

	//mysql
	static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost:3306/mysql?characterEncoding=utf-8";
	static final String USER = "root";
	static final String PASS = "wnstjd88";
	
	JDBC4StepTest()throws Exception{
		//1. 드라이버 로딩
		Class.forName(DRIVER_NAME);
		System.out.println("Driver Loading.....");
		
		//2. 디비연결
		conn = DriverManager.getConnection(URL, USER, PASS);
		System.out.println("db conn.....");
		
		//3. Statement 생성
		st = conn.createStatement();
		System.out.println("statement creating....");

		String query = ""; //쿼리를 담을 변수
		int row = 0; //수행후 변경된 레코드수

		//5. insert문을 작성 + 실행
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
		
		//6. delete 문을 작성 + 실행
		query = "DELETE FROM member WHERE name = '김제동'";
		row = st.executeUpdate(query);
		System.out.println(row + " row delete ok....");
		
		//7. id가 lee인 사람의 이름을 James'lee로 변경
		query = "UPDATE member SET name='James''lee' WHERE id='lee'";
		row = st.executeUpdate(query);
		System.out.println(row + " row update ok...");
		
		//4. select문을 작성 + 실행 해서 콘솔로 출력
		query = "SELECT id, name, age FROM member";
		rs = st.executeQuery(query);
		
		//김제동 row만을 출력
		rs.absolute(2); //2번째 줄
		System.out.println(rs.getString("id")+"\t"+rs.getString("name"));

		System.out.println("========================회원정보==============================");
		while(rs.next()){
			System.out.println(
				rs.getString("id") + "\t"
			  + rs.getString("name") + "\t"
			  + rs.getString("age")
			);
		}
		System.out.println("===========================================================");
	}

	public static void main(String[] args)throws Exception {
		new JDBC4StepTest();
	}
}