package step01.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DataBaseInfo;

/**
 * @Auth: chunkind@naver.com
 * @Data: 2021. 05. 29.
 *
 * jdbc 프로그램을 구조적(DAO/VO/Test)으로 작성하기에 앞서
 * Simple하게 로직을 일단 작성해보겠다...그런 다음에 구조적으로 변경(step2)
 * 즉 단순하게 비지니스 로직을 작성하겠다는 의미이다.
 * ---------------------------------------------------------------------
 * 테이블 생성

DROP TABLE customer purge;

 * oracle
CREATE TABLE customer(
  name varchar2(20),
  age number(3),
  height number(3),
  weight number(3),
  gender char(5)
);

 * mysql
CREATE TABLE customer(
    name varchar(20),
    age int(3),
    height int(3),
    weight int(3),
    gender char(5)
);
 */
public class SimpleCustDao {

    //생성자
    public SimpleCustDao()throws ClassNotFoundException{
        Class.forName(DataBaseInfo.DRIVER_NAME);
        System.out.println("Driver Loading....");
    }

    // 1. DB연결
    public Connection getConn()throws SQLException{
        Connection conn = DriverManager.getConnection(
            DataBaseInfo.URL,
            DataBaseInfo.USER,
            DataBaseInfo.PASS
        );
        System.out.println("DB Connecting....");
        return conn;
    }

    // 2. close
    public void closeAll(Connection conn, PreparedStatement ps)throws SQLException{
        if(ps != null) ps.close();
        if(conn != null) conn.close();
    }
    public void closeAll(Connection conn, PreparedStatement ps, ResultSet rs)throws SQLException{
        if(rs != null) rs.close();
        closeAll(conn,ps);
    }

    public void insertCustomer(String name, int age, int height, int weight,String gender)throws SQLException {
        /*
         * jdbc 2단계 ~ 5단계 까지의 로직이 작성되어야 한다.
         * 2단계 : 디비연결
         * 3단계 : 쿼리문 작성, PreparedStatement 객체 생성
         * 4단계 : 쿼리문 수행
         * 5단계 : 자원을 닫는다...close()
         */
        Connection conn = null;
        PreparedStatement ps = null;
        conn = getConn();

        String addQuery = "INSERT INTO customer VALUES(?,?,?,?,?)";
        ps = conn.prepareStatement(addQuery);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setInt(3, height);
        ps.setInt(4, weight);
        ps.setString(5, gender);

        int row = ps.executeUpdate();
        System.out.println(row+" 명의 "+ name +" 고객이 추가됨!!");

        closeAll(conn, ps);
    }

    public void deleteCustomer(String name)throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        conn = getConn();

        String delQuery = "DELETE FROM customer WHERE name=?";
        ps = conn.prepareStatement(delQuery);
        ps.setString(1, name);
        int row = ps.executeUpdate();

        System.out.println(row+" 명 " +name+" 고객이 삭제!!");
        closeAll(conn, ps);
    }

    public void updateCustomer(String name, int age, int height, int weight,String gender)throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        conn = getConn();

        String upQuery = "UPDATE customer SET age=?, height=?, weight=?	WHERE name=?";
        ps= conn.prepareStatement(upQuery);
        ps.setInt(1, age);
        ps.setInt(2, height);
        ps.setInt(3, weight);
        ps.setString(4, name);

        int row = ps.executeUpdate();
        System.out.println(row+" 명의 정보가 수정...!!");
        closeAll(conn, ps);
    }

    public void printAllCustomer()throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn = getConn();

        String allQuery = "SELECT * FROM customer";
        ps = conn.prepareStatement(allQuery);
        rs = ps.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString(1)+"\t"+rs.getInt(2));
        }
        closeAll(conn, ps, rs);
    }

    public void printACustomer(String name)throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = getConn();
        String aQuery = "SELECT * FROM customer WHERE name=?";
        ps = conn.prepareStatement(aQuery);
        ps.setString(1, name);
        rs = ps.executeQuery();
        if(rs.next()){
            System.out.println(rs.getString(1)+"\t"+rs.getInt(2));
        }else{
            System.out.println(name + "님의 데이터가 없습니다.");
        }
    }

}