package test;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.MemberDAO;
import exception.DuplicateIDException;
import exception.RecordNotFoundException;
import vo.MemberVO;
import config.DataBaseInfo;

/**
 * @Auth: chunkind@naver.com
 * @Date: 2021. 05. 29.
 *
 * 테이블 생성

DROP TABLE member;

CREATE TABLE member(
  id varchar(50),
  name varchar(100),
  password varchar(100),
  address varchar(100)
);

 */
public class TestApp {
	
	static{
		try{
			Class.forName(DataBaseInfo.DRIVER_NAME);
			System.out.println("Connected to Driver.");
		}catch(ClassNotFoundException e){
			System.out.println("Fail to Connect to Driver.");
		}
	}
	
	public static void main(String[] args) throws SQLException, DuplicateIDException, RecordNotFoundException {

		MemberDAO test = new MemberDAO();
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		test.registerMember(new MemberVO("1", "박정희", "1234", "서울"));
		test.registerMember(new MemberVO("2", "김대중", "2345", "수원"));
		test.registerMember(new MemberVO("3", "노무현", "3456", "죽전"));
		test.registerMember(new MemberVO("4", "이명박", "5678", "부천"));
		test.registerMember(new MemberVO("5", "김영삼", "6789", "판교"));
		
		test.deleteMember("1");

		test.updateMember(new MemberVO("4", "문재인", "1111", "판교"));

		System.out.println(test.selectByID("4"));

		list = test.getAllMember();
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i));
		}

		list = test.selectByAddress("판교");
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i));
		}

		System.out.println(test.getCountByAddress("판교"));
	}
}
