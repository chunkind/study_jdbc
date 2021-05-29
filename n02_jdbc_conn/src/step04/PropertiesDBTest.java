package step04;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @Auth: chunkind@naver.com
 * @Data: 2021. 05. 29.
 *
 * 인터페이스에 서버의 정보를 상수값으로 저장하는 방식을 넘어서
 * 이번에는 Map방식의 Properties 파일에 스트링값으로 서버의 정보를 저장
 * 이름과 값이 쌍으로 저장되는데....
 * 이런 방식이 프로그램에서 어떻게 발전되어 갈수 있는지를 잘 살펴 보아야 한다.
 * ::
 * 1. 서버정보와 프로그램 로직의 완벽한 분리가 일어난다.
 * 2. sql 쿼리문과 프로그램 로직의 분리도 완벽하게 일어난다.
 */
public class PropertiesDBTest {
	public static void main(String[] args)throws Exception{
		Properties p = new Properties();
		p.load(new FileInputStream("/Users/ck/git/study_jdbc/n02_jdbc_conn/src/config/db.properties"));

		String driverName = p.getProperty("jdbc.oracle.driver");
		String url = p.getProperty("jdbc.oracle.url");
		String user = p.getProperty("jdbc.oracle.user");
		String pass = p.getProperty("jdbc.oracle.pass");

		String insertQuery = p.getProperty("jdbc.sql.insert");
		String deleteQuery = p.getProperty("jdbc.sql.delete");
		String selectQuery = p.getProperty("jdbc.sql.select");
		String selectAllQuery = p.getProperty("jdbc.sql.selectAll");
		String updateQuery = p.getProperty("jdbc.sql.update");
		String cleanQuery = p.getProperty("jdbc.sql.clean");

		Class.forName(driverName);
		System.out.println("driver Loading...");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int row = 0;

		conn = DriverManager.getConnection(url, user, pass);
		System.out.println("db conn....ok..");

		//data clean
		ps = conn.prepareStatement(cleanQuery);
		row = ps.executeUpdate();
		System.out.println("data clean... delete :" + row);
		
		//data insert
		ps = conn.prepareStatement(insertQuery);
		ps.setString(1,"momo02");
		ps.setString(2,"박뿌리");
		ps.setInt(3,19);

		row = ps.executeUpdate();
		System.out.println(row + " row INSERT OK!!");

		ps = conn.prepareStatement(insertQuery);
		ps.setString(1,"chunkind");
		ps.setString(2,"춘뿌리");
		ps.setInt(3,24);

		row = ps.executeUpdate();
		System.out.println(row + " row INSERT OK!!");

		//회원 정보 출력.
		ps = conn.prepareStatement(selectAllQuery);
		rs = ps.executeQuery();
		System.out.println("=정보출력===================================");
		while(rs.next()){
			System.out.println(
				rs.getString("id") + "\t"
						+ rs.getString("name") + "\t"
						+ rs.getString("age")
			);
		}
		System.out.println("==========================================");

		ps = conn.prepareStatement(updateQuery);
		ps.setString(1,"박부리");
		ps.setInt(2,29);
		ps.setString(3,"momo02");

		row = ps.executeUpdate();
		System.out.println(row + " 업데이트!");

		ps = conn.prepareStatement(updateQuery);
		ps.setString(1,"춘부리");
		ps.setInt(2,34);
		ps.setString(3,"chunkind");

		row = ps.executeUpdate();
		System.out.println(row + " 업데이트!");

		//회원 정보 출력.
		ps = conn.prepareStatement(selectAllQuery);
		rs = ps.executeQuery();
		System.out.println("=정보출력===================================");
		while(rs.next()){
			System.out.println(
					rs.getString("id") + "\t"
							+ rs.getString("name") + "\t"
							+ rs.getString("age")
			);
		}
		System.out.println("==========================================");

		ps = conn.prepareStatement(deleteQuery);
		ps.setString(1,"momo02");
		row = ps.executeUpdate();
		System.out.println(row + " 삭제 완료!");

		ps = conn.prepareStatement(selectQuery);
		ps.setString(1, "momo02");
		rs = ps.executeQuery();

		if(rs.next()){
			System.out.println(rs.getString("id") +", " + rs.getString("name") + ", " + rs.getInt("age"));
			System.out.println("삭제가 잘 안됐다!");
		}else{
			System.out.println("삭제완료!");
		}

		ps = conn.prepareStatement(selectQuery);
		ps.setString(1, "chunkind");
		rs = ps.executeQuery();

		if(rs.next()) {
			System.out.println(rs.getString("id") + ", " + rs.getString("name") + ", " + rs.getInt("age"));
		}else{
			System.out.println("삭제되면 안되는데 삭제됏다!");
		}

		rs.close();
		ps.close();
		conn.close();
	}
}


















