package step02.dao;

import config.DataBaseInfo;
import step02.vo.CustVO;

import java.sql.*;
import java.util.ArrayList;

/*
 * dao 객체란?
 * Data(DataBase) Access Object를 말하는 것으로
 * 디비로 접근하는 로직(Business Logic)만을 담고 있는 클래스를 말한다.
 * 실제 프로그램에서 가장 중요한 기능을 말하고 있다.
 */
public class CustDAO {
	// 공통적인 기능을 추출한 getConnection() | close()
	public Connection getConn() throws SQLException {
		Connection conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USER, DataBaseInfo.PASS);
		System.out.println("DB Connecting..OK");
		return conn;
	}

	public void closeAll(Connection conn, PreparedStatement ps) throws SQLException{
		if(conn != null){
			conn.close();
		}if(ps !=null){
			ps.close();
		}
	}

	public void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException{
		if(rs != null){
			rs.close();
		}
		closeAll(conn, ps);
	}

	// Business Logic
	public void addCust(CustVO vo) throws SQLException{ // addCust에 인자 (CustVO) 를 하는 이유
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConn();	// 앤 왜함? 

		String query = "INSERT INTO cust VALUES(?,?,?)";	//물음표가 3가지인 이유는  public CustVO(String id, String name, String addr) 이거땜시
		ps = conn.prepareStatement(query);	// 여기서 ps를 쓰는 이유(?)는? 해당 쿼리문을 가진 ps 객체를 만들었고, 그것의 주소를 할당해주는 과정.
		ps.setString(1, vo.getId());	//ps.setString(1, id);
		ps.setString(2, vo.getName());	//ps.setString(2, Name);
		ps.setString(3, vo.getAddr());	//ps.setString(3, Addr); 이유를 내가 설명하고 맞는지 확인요청

		int row = ps.executeUpdate();	//int row의 의미 질문 거기서 row가 왜 나오는지 그리고 리턴타입(?)이 왜 int인지
		System.out.println(row + "row addCust().. OK");

		closeAll(conn, ps);	// close를 하는 이유. 문장이 끝나서 하는건데 근까 그걸 왜해?		
	}

	public void deleteCust(String id) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConn();

		String query = "DELETE FROM cust WHERE id = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, id);

		int row =ps.executeUpdate();
		System.out.println(row + "row deleteCust().. OK");

		closeAll(conn, ps);
	}

	public void updateCust(CustVO vo) throws SQLException{// id로 찾을꺼니까 ()안에는 id인자가 들어가야 하는거아님?
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConn();

		String query = "UPDATE cust SET name = ?, addr = ? WHERE id = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, vo.getName());	//어느때에는 ps.setString(1, vo.getName()); 이렇게 하고 또 어느때에는 ps.setString(1, Name); 이렇게 하는지 구별방법. 처음에 ps.setString(1, Name); 이렇게 하니까 오류나서 옆에꺼 보니까 ps.setString(1, vo.getName()); 이렇게 하는거 였음
		ps.setString(2, vo.getAddr());
		ps.setString(3, vo.getId());

		int row = ps.executeUpdate();
		System.out.println(row + "row UpdateCust().. OK");

		closeAll(conn, ps);
	}
	public void updateCust(String id, String name, String addr) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConn();

		String query = "UPDATE cust SET name = ?, addr = ? WHERE id = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, name);
		ps.setString(2, addr);
		ps.setString(3, id);

		int row = ps.executeUpdate();
		System.out.println(row + "row UpdateCust().. OK");

		closeAll(conn, ps);
	} //위에랑 똑같은거

	public CustVO getCust(String id) throws SQLException{	// 여기는 왜 void가 아니라 리턴값이 CustVO임?
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	// 이건 왜 쓰임.........
		CustVO vo = null;
		conn = getConn();	//아니구나 쓰이는 구나!
		
		String query = "SELECT * FROM cust WHERE id = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, id);	//ps랑 rs가 왜 이렇게 쓰이는지 정확한 해석 요망
		rs = ps.executeQuery();
		
		if(rs.next()){
			vo = new CustVO(id, rs.getString(2), rs.getString(3));	//rs.getString(2) 이게 몬데 ㅅㅂ. rs는 여기서 나오는데 도대체 여기에서 getString(2)이게 의미하는게 뭐야 ㅅㅂ 무슨 두번째인데 씨팔 그리고 if문은 또 왜써!!!
			System.out.println("VO 객체 만들었음.");
		}
		closeAll(conn, ps, rs);
		return vo;
	}	// 얘랑 밑에는 찾았다고 출력문 안날려? 그리고 return 은 왜써?

	public ArrayList<CustVO> getAllCust() throws SQLException{	// <>이안에 CustVO는 왜 들어가는데!!!??
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CustVO> list = new ArrayList<CustVO>();
		
		conn = getConn();
		
		String query = "SELECT * FROM cust";	// 얜 where로 어디인지 지정 안해줘도 됨?
		ps = conn.prepareStatement(query);
		rs = ps.executeQuery();
		
		while(rs.next()){
			list.add(new CustVO(rs.getString(1), rs.getString(2), rs.getString(3)));
		}	// 위에 if가 왜 쓰이는지 이제 쫌 알껏 같음! 위에 if는 id라는 조건으로 찾아야 해서 if를 쓴건데 여기는 왜 while을 써?
		closeAll(conn, ps, rs);
		return list;	
	}
}
