package broker.twotier.dao;

import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.StockRec;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import config.DataBaseInfo;

import java.sql.*;
import java.util.ArrayList;

/**
 * DB Access 하는 로직을 담고 있는 비지니스 로직...
 * UseCase Diagram을 참조해서 프로그램적인 기능을 뽑아낸다
 */
public class Database {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public Database() throws ClassNotFoundException {
        Class.forName(DataBaseInfo.DRIVER_NAME);
        System.out.println("driver loading....");
    }

    public Connection getConnection(){
        try {
            conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USER, DataBaseInfo.PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public void closeAll(Connection conn, PreparedStatement ps){
        if(null != conn){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(null != ps){
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void closeAll(Connection conn, PreparedStatement ps, ResultSet rs){
        closeAll(conn, ps);
        if(null != rs){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * add
     */
    public void addCustomer(CustomerRec cr) throws DuplicateSSNException{
        conn = getConnection();
        String sql = "INSERT INTO CUSTOMER(SSN, CUST_NAME, ADDRESS) VALUES(?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cr.getSsn());
            ps.setString(2, cr.getName());
            ps.setString(3, cr.getAddress());
            int result = ps.executeUpdate();
            if(result == 1){
                System.out.println(cr.getName() + "님 회원가입 성공");
            }else{
                System.out.println(cr.getName() + "님 회원가입 실패");
            }
        } catch (MySQLIntegrityConstraintViolationException e){
            throw new DuplicateSSNException("주민번호가 중복 됩니다.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<CustomerRec> getAllCustomer() {
        return null;
    }

    public ArrayList<StockRec> getAllStock() {
        return null;
    }

    public CustomerRec getCustomer(String ssn) {
        return null;
    }

    public void buyShares(String ssn, String symbol, int quantity) throws SQLException{
    }

    public void sellShares(String ssn, String symbol, int quantity) throws SQLException, RecordNotFoundException, InvalidTransactionException{
    }

    public void updateCustomer(CustomerRec cr) throws RecordNotFoundException{
    }

    public void deleteCustomer(String ssn) {
    }
}