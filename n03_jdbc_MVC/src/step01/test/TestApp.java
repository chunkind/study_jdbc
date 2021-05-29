package step01.test;

import step01.dao.SimpleCustDao;

import java.sql.SQLException;

public class TestApp {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        SimpleCustDao simple = new SimpleCustDao();
        simple.insertCustomer("장동건", 42, 182, 78, "남");
        simple.insertCustomer("원빈", 33, 184, 70, "남");
        simple.deleteCustomer("원빈");
        simple.updateCustomer("장동건", 44, 180, 78, "남");
        simple.printAllCustomer();
        simple.printACustomer("이나영");
    }
}
