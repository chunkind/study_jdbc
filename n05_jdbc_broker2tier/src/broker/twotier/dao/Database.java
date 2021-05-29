package broker.twotier.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.SharesRec;
import broker.twotier.vo.StockRec;
import config.DataBaseInfo;

/*
 * DB Access 하는 로직을 담고 있는 비지니스 로직...
 * UseCase Diagram을 참조해서 프로그램적인 기능을 뽑아낸다
 */
public class Database {
	public Database(String serverName) throws ClassNotFoundException{
		Class.forName(DataBaseInfo.DRIVER_NAME);
		System.out.println("드라이버 로딩 성공....");
	}
	
	////////////////// 공통적인 로직 ///////////////////////////////
	// getConn() | closeAll() ..2개 
	public Connection getConn() throws SQLException{
		Connection conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USER, DataBaseInfo.PASS);
		System.out.println("디비 연결 성공....");
		return conn;
	}
	
	public void closeAll(PreparedStatement ps, Connection conn)throws SQLException{
		if(ps !=null ) ps.close();
		if(conn != null) conn.close();		
	}
	public void closeAll(ResultSet rs,PreparedStatement ps, Connection conn)throws SQLException{
		if(rs !=null ) rs.close();
		closeAll(ps, conn);			
	}
	
	//////////////////  비지니스 로직 ////////////////////////////
	// isExist() | addCuatomer() | deleteCuastomer() | updateCustomer
	// getPortfolio(String ssn)
	public boolean isExist(Connection conn,String ssn)throws SQLException{
		String query = "SELECT ssn FROM customer WHERE ssn=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, ssn);
		ResultSet rs=  ps.executeQuery();
		
		return rs.next();
	}
	public void addCustomer(CustomerRec cust) throws DuplicateSSNException,SQLException{
		Connection conn = null;
		PreparedStatement ps=  null;
		try{
			conn = getConn();
			if(!isExist(conn, cust.getSsn())){//없다면...추가
				String query = "INSERT INTO customer VALUES(?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, cust.getSsn());
				ps.setString(2, cust.getName());
				ps.setString(3, cust.getAddress());
				System.out.println(ps.executeUpdate()+" 명 insert customer...addCustomer()");
			}else{
				throw new DuplicateSSNException("그런 사람 이미 있어여....addCustomer()..");
			}
		}finally{
			closeAll(ps, conn);
		}
	}
	public void deleteCustomer(String ssn) throws RecordNotFoundException,SQLException{
		Connection conn = null;
		PreparedStatement ps=  null;
		PreparedStatement ps1=  null;
		try{
			conn=  getConn();
			if(isExist(conn, ssn)){ //삭제할 사람이 있다...
				String query = "DELETE FROM customer WHERE ssn=?";			
				ps = conn.prepareStatement(query);				
				ps.setString(1, ssn);			
				System.out.println(ps.executeUpdate()+" 명 delete ...deleteCustomer()");
			}else{
				throw new RecordNotFoundException("삭제할 대상이 없습니다..deleteCustomer()");
			}
		}finally{
			closeAll(ps, conn);
		}
	}
	public void updateCustomer(CustomerRec cust) throws SQLException,RecordNotFoundException{
		Connection conn = null;
		PreparedStatement ps=  null;
		try{
			conn=  getConn();
			if(isExist(conn, cust.getSsn())){ //수정할 사람이 있다...
				String query = "UPDATE customer SET cust_name=?, address=? WHERE ssn=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, cust.getName());
				ps.setString(2, cust.getAddress());
				ps.setString(3, cust.getSsn());
				System.out.println(ps.executeUpdate()+" 명 update ...updateCustomer()");
			}else{
				throw new RecordNotFoundException("수정할 대상이 없습니다..deleteCustomer()");
			}
		}finally{
			closeAll(ps, conn);
		}
	}

	/*
	 * 특정한 고객이 보유하고 있는 주식 보유량을 리턴하는 기능
	 * 주식 보유량 :: SharesRec 객체들...Vector에 담긴다.
	 */
	public Vector<SharesRec> getPortfolio(String ssn)throws SQLException,RecordNotFoundException{
		Connection conn = null;
		PreparedStatement ps=  null;
		ResultSet rs=  null;
		Vector<SharesRec> v = new Vector<SharesRec>();
		try{
			conn = getConn();
			String query = "SELECT * FROM shares WHERE ssn=?";
			ps = conn.prepareStatement(query);
			ps.setString(1,ssn);
			rs = ps.executeQuery();
			while(rs.next()){
				v.add(new SharesRec(ssn, rs.getString("symbol"), rs.getInt("quantity")));
			}
		}finally{
			closeAll(rs, ps, conn);
		}
		return v;
	}
	
	public CustomerRec getCustomer(String ssn)throws Exception{
		Connection conn = null;
		PreparedStatement ps=  null;
		ResultSet rs=  null;
		CustomerRec cr = null;
		try{
			conn = getConn();
			String query = "SELECT * FROM customer WHERE ssn=?";
			ps = conn.prepareStatement(query);
			ps.setString(1,ssn);
			rs = ps.executeQuery();
			if(rs.next()){ //가져올 사람이 있다면
				cr = new CustomerRec(ssn, rs.getString("cust_name"), rs.getString("address"));
			}
			cr.setPortfolio(getPortfolio(ssn));
		}finally{
			closeAll(rs, ps, conn);
		}
		return cr;
	}
	public ArrayList<CustomerRec> getAllCustomer() throws Exception{
		Connection conn = null;
		PreparedStatement ps=  null;
		ResultSet rs=  null;
		ArrayList<CustomerRec> list = new ArrayList<CustomerRec>();
		
		try{
			conn = getConn();
			String query = "SELECT * FROM customer";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(new CustomerRec(rs.getString(1), rs.getString(2), rs.getString(3)));
//				getPortfolio(rs.getString(1))));
			}
			for(int i=0; i<list.size(); i++){
				list.get(i).setPortfolio(getPortfolio(list.get(i).getSsn()));
			}
		}finally{
			closeAll(rs, ps, conn);			
		}
		return list;
	}

	public ArrayList<StockRec> getAllStock() throws SQLException{
		Connection conn = null;
		PreparedStatement ps=  null;
		ResultSet rs=  null;
		ArrayList<StockRec> list = new ArrayList<StockRec>();
		try{
			conn = getConn();
			String query = "SELECT * FROM stock";
			ps = conn.prepareStatement(query);
			rs= ps.executeQuery();
			while(rs.next()){
				list.add(new StockRec(rs.getString(1), 
									  rs.getFloat(2)));
			}
		}finally{
			closeAll(rs, ps, conn);
		}
		return list;
	}

	public float getStckPrice(String symbol) throws SQLException,RecordNotFoundException{
		Connection conn = null;
		PreparedStatement ps=  null;
		ResultSet rs=  null;
		float price = 0.0f;
		try{
			conn = getConn();
			String query = "SELECT price FROM stock WHERE symbol=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, symbol);
			rs=  ps.executeQuery();
			if(rs.next()) price = rs.getFloat(1);
			else throw new RecordNotFoundException("없는 주식입니다..");
		}finally{
			closeAll(rs, ps, conn);
		}
		return price;
	}

	/*
	 * 주식을 갖고 있느냐 / 없느냐를 먼저 알아야 한다.
	 * 가지고 있으면..UPDATE / 없는 주식을 사면...INSERT
	 */
	public void buyShares(String ssn, String symbol, int quantity)throws SQLException{
		Connection conn = null;
		PreparedStatement ps=  null;
		ResultSet rs=  null;
		try{
			conn = getConn();
			String query = "SELECT quantity FROM shares WHERE ssn=? AND symbol=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);
			ps.setString(2, symbol);
			rs = ps.executeQuery();
			if(rs.next()){//기존에 살려는 주식을 가지고 있다....update
				int q = rs.getInt(1); //현재 가지고 있는 주식의 수량
				int newQuantity=q+quantity;
				String query1 = "UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
				ps=  conn.prepareStatement(query1);
				ps.setInt(1,newQuantity);
				ps.setString(2, ssn);
				ps.setString(3, symbol);
				System.out.println(ps.executeUpdate()+" row buyShares()....update ok");
			}else{ //살려는 주식이 없다...insert
				String query2 = "INSERT INTO shares VALUES(?,?,?)";
				ps = conn.prepareStatement(query2);
				ps.setString(1,ssn);
				ps.setString(2,symbol);
				ps.setInt(3,quantity);
				System.out.println(ps.executeUpdate()+" row..buyShares()....insert ok");
			}
		}finally{
			closeAll(rs, ps, conn);
		}
	}

	/*
	 * 현재 가지고 있는 주식의 양을 먼저 알아야한다.
	 * 100개 가지고 있다.
	 * 1) 100개 파는 경우 --- DELETE
	 * 2) 200개 파는 경우 --- 폭탄 터뜨림
	 * 3) 20개 파는 경우  --- UPDATE
	 */
	public void sellShares(String ssn, String symbol, int quantity)throws InvalidTransactionException,RecordNotFoundException,SQLException{
		Connection conn = null;
		PreparedStatement ps=  null;
		ResultSet rs=  null;
		try{
			conn = getConn();
			if(!isExist(conn, ssn)){//팔 사람이 존재하지 않는다면...
				throw new RecordNotFoundException("주식을 팔 사람이 없어여..");
			}else{ //주식을 팔 사람이 있는 경우
				String query = "SELECT quantity FROM shares WHERE ssn=? AND symbol=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, ssn);
				ps.setString(2, symbol);
				rs=  ps.executeQuery();
				rs.next(); //엘레먼트가 있는 곳을 가리키게만 하겠다.
				int q = rs.getInt(1); //현재 보유하고 있는 주식의 수량..100개 가지고 있다
				int newQuantity=q-quantity;
				
				if(q==quantity){ //DELETE
					String query1 = "DELETE FROM shares WHERE ssn=? AND symbol=?";
					ps = conn.prepareStatement(query1);
					ps.setString(1, ssn);
					ps.setString(2, symbol);
					System.out.println(ps.executeUpdate()+" row shares DELETE OK..");
				}else if(q>quantity){ //UPDATE
					String query2 ="UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
					ps = conn.prepareStatement(query2);
					ps.setInt(1, newQuantity);
					ps.setString(2, ssn);
					ps.setString(3, symbol);
					System.out.println(ps.executeUpdate()+" row shares update...ok");
				}else{ //팔려는 주식의 양이 가진것보다 더 많을때..Exception
					throw new InvalidTransactionException("팔려는 양이 너무 많습니다..");
				}
			}
		}finally{
			closeAll(rs, ps, conn);
		}
	}
}