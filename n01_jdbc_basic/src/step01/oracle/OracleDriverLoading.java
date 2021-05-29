package step01.oracle;

public class OracleDriverLoading {
	public OracleDriverLoading() {
		// 드라이버 로딩하기...
		// 1. 드라이버 이름.. FQCN
		// 2.~.jar 파일의 classpath가 잡혀져 있지 않다.
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공...");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패...");

		}
	}
}
