package step03;

import config.DataBaseInfo;

import java.sql.*;

/**
 * @Auth: chunkind@naver.com
 * @Date: 2021. 05. 29.
 *
 * 1.생성자 사용.
 * 생성자에 DB를 먼저 로드시켜 부하를 줄이자.
 *
 * 2.DB정보의 분리.
 * 이전 예제에서 DB정보를 상수(static final)에 담았다.
 * 이것을 interface로 분리해 하드코딩된 정보를 제거해보자.
 *
 * 3.PreparedStatement 사용.
 * INSERT INTO member VALUES('sung', '성유리', 31) 같은 쿼리문을
 * INSERT INTO member VALUES(?, ?, ?) 와 같이 "?"로 치환하여 사용할수 있다.
 */
public class PreparedStatementTest {

    PreparedStatementTest()throws Exception{
        //1. 드라이버 로딩
        Class.forName(DataBaseInfo.DRIVER_NAME);
        System.out.println("Driver Loading.....");
    }

    public void excute() throws SQLException {
        Connection conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USER, DataBaseInfo.PASS);
        System.out.println("디비 연결 성공....");

        String query = "";
        int row = 0;

        //기존 Statement -> PreparedStatement로 전환.
        PreparedStatement ps = null;

        query = "DELETE FROM member";
        ps = conn.prepareStatement(query);
        row = ps.executeUpdate();
        System.out.println(row + " data clean...");

        //data 정리.
        query = "INSERT INTO member VALUES(?,?,?)";
        ps = conn.prepareStatement(query); //pre compile

        //?값 바인딩
        ps.setString(1,"momo02");
        ps.setString(2,"박부리");
        ps.setInt(3, 29);

        //쿼리문 실행
        row = ps.executeUpdate();
        System.out.println(row+" row INSERT ok!!");

        //?값 바인딩
        ps.setString(1,"chunkind");
        ps.setString(2,"춘부리");
        ps.setInt(3, 34);

        //쿼리문 실행
        row = ps.executeUpdate();
        System.out.println(row+" row INSERT ok!!");

        query = "SELECT id, name, age FROM member";
        ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            System.out.println(
                    rs.getString("id") + "\t"
                            + rs.getString("name") + "\t"
                            + rs.getString("age")
            );
        }

        //자원 닫기.
        rs.close();
        ps.close();
        conn.close();
    }

    public static void main(String[] args) throws Exception {
        PreparedStatementTest test = new PreparedStatementTest();
        test.excute();
    }
}
