package step02.test;

import step02.mysql.MysqlConnection;

/**
 * @Auth: chunkind@naver.com
 * @Date: 2021. 05. 29.
 * 1단계 : 드라이버 로딩
 * 2단계 : 디비연결
 */
public class TestApp {
    public static void main(String[] args) {
//        new OracleConnection();
        new MysqlConnection();
    }
}
