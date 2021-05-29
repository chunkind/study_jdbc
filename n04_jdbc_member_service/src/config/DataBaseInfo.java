package config;

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