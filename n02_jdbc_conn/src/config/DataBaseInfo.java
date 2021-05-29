package config;

/**
 * @Auth: chunkind@naver.com
 * @Date: 2021. 05. 29.
 *
 * DB 서버에 대한 상수값의 정보를 저장하는 틀...
 *
 * interface에서 정의하면
 * String 이라고 선언해도 앞에 public static이 생략되어 있다
 */
public interface DataBaseInfo {

	//oracle
//	String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
//	String USER = "scott";
//	String PASS = "tiger";

	//mysql
	String DRIVER_NAME = "com.mysql.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/mysql?characterEncoding=utf-8";
	String USER = "root";
	String PASS = "wnstjd88";
}
