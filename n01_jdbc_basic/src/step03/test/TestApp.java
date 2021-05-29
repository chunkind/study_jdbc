package step03.test;

import step03.mysql.MysqlConnection;
import step03.oracle.OracleConnection;

/**
 * 1. Driver Loading
 * 2. DB 연결
 * Connection conn = DriverManager getConnection();
 * ---------------------------------------------------------------------------------------------------
 * DB에 데이타를 넣고(insert) 빼고(delete) 수정(update) 가져올 수(select) 있다
 *  : : CRUD 작업이라 한다
 *  : : CRUD 작업은 sql문으로 한다
 *  ---------------------------------------------------------------------------------------------------
 * eg) create table member(name varchar2(20));
 *
 * 3. Statement 객체 생성
 * Statement st  =  conn. createStatemet();
 *
 * 4. sql문 실행....sql문을 이용해서 데이타를 디비에 CRUD한다
 * ResultSet rs=  st.executeQuery("sql문");
 * int row = st.executeUpdate("sql문");
 *
 * 아래 테이블을 생성해야함.
 * CREATE TABLE member(
 *     id varchar(200),
 *     name varchar(200),
 *     age int(5)
 * );
 */
public class TestApp {
    public static void main(String[] args) throws Exception {
//        new OracleConnection();
        new MysqlConnection();
    }
}