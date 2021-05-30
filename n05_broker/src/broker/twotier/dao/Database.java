package broker.twotier.dao;

import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.StockRec;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DB Access 하는 로직을 담고 있는 비지니스 로직...
 * UseCase Diagram을 참조해서 프로그램적인 기능을 뽑아낸다
 */
public class Database {

    public ArrayList<CustomerRec> getAllCustomer() {
        return null;
    }

    public ArrayList<StockRec> getAllStock() {
        return null;
    }

    public void addCustomer(CustomerRec cr) throws DuplicateSSNException{
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