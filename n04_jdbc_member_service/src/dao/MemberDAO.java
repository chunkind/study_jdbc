package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.DataBaseInfo;
import exception.DuplicateIDException;
import exception.RecordNotFoundException;
import vo.MemberVO;

public class MemberDAO {
	public Connection getConn() throws SQLException {
		Connection conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USER, DataBaseInfo.PASS);
		return conn;
	}

	public void closeAll(PreparedStatement ps, Connection conn) {
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("resource has still opened.");
		}
	}
	public void closeAll(PreparedStatement ps, Connection conn, ResultSet rs) {
		try {
			closeAll(ps, conn);
			rs.close();
		} catch (SQLException e) {
			System.out.println("resource has still opened.");
		}
	}

	public boolean idExist(String id) throws SQLException {
		Connection conn = getConn();
		String query = "SELECT id FROM member WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(query);

		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		boolean bool = false;
		if (rs.next()) {
			if (rs.getString(1).equals(id)) {
				bool = true;
			} else {
				bool = false;
			}
		}
		return bool;
	}

	// 지금부터는 메서드 이름만 세팅합니다. 인자값은 알아서.
	public void registerMember(MemberVO vo) throws SQLException, DuplicateIDException {
		Connection conn = null;
		PreparedStatement ps = null;

		if (idExist(vo.getId()) == false) {
			conn = getConn();
			String query = "INSERT INTO member VALUES (?, ?, ?, ?)";
			ps = conn.prepareStatement(query);

			ps.setString(1, vo.getId());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getPassword());
			ps.setString(4, vo.getAddress());

			int row = ps.executeUpdate();
			System.out.println(row + " executed!");
		} else {
			throw new DuplicateIDException();
		}
	}

	public void deleteMember(String id) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;

		if (idExist(id) == true) {
			conn = getConn();
			String query = "DELETE FROM member WHERE id = ?";
			ps = conn.prepareStatement(query);

			ps.setString(1, id);

			int row = ps.executeUpdate();
			System.out.println(row + " executed!");
		} else {
			throw new RecordNotFoundException();
		}
	}

	public void updateMember(MemberVO vo) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;

		if (idExist(vo.getId()) == true) {
			conn = getConn();
			String query = "UPDATE member SET name=?, password=?, address=? WHERE id =?";
			ps = conn.prepareStatement(query);

			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPassword());
			ps.setString(3, vo.getAddress());
			ps.setString(4, vo.getId());

			int row = ps.executeUpdate();
			System.out.println(row + " executed!");
		} else {
			throw new RecordNotFoundException();
		}
	}

	public MemberVO selectByID(String id) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		MemberVO vo = null;
		ResultSet rs = null;

		if (idExist(id) == true) {
			conn = getConn();
			String query = "SELECT * FROM member WHERE id =?";
			ps = conn.prepareStatement(query);

			ps.setString(1, id);

			rs = ps.executeQuery();
			while (rs.next()) {
				vo = new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} else {
			throw new RecordNotFoundException();
		}
		return vo;
	}

	public ArrayList<MemberVO> selectByAddress(String address) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		ResultSet rs = null;

		conn = getConn();
		String query = "SELECT * FROM member WHERE address =?";
		ps = conn.prepareStatement(query);

		ps.setString(1, address);

		rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		return list;
	}

	public ArrayList<MemberVO> getAllMember() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();

		conn = getConn();
		String query = "SELECT * FROM member";
		ps = conn.prepareStatement(query);

		rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		return list;
	}

	public int getCountByAddress(String address) throws SQLException {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		list = selectByAddress(address);
		return list.size();
	}
}