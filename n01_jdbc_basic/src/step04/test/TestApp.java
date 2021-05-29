package step04.test;

import step03.mysql.MysqlConnection;
import step04.oracle.OracleDBConnection;

/**
 * @Auth: chunkind@naver.com
 * @Date: 2021. 05. 29.
 *
 * JDBC를 이용하여 CRUD를 하는 로직.
 *
 * jdbc 4단계의 모든 코드를 정리
 * 특히 ResultSet의 사용법에 주의
 *
 * 아래 테이블을 생성해야 한다.
CREATE TABLE member(
id varchar(200),
name varchar(200),
age int(5)
);
 */
public class TestApp {
    public static void main(String[] args) throws Exception {
//        new OracleDBConnection();
        new MysqlConnection();
    }
}
