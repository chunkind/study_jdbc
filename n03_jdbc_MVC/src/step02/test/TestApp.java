package step02.test;

import config.DataBaseInfo;
import step02.dao.CustDAO;
import step02.vo.CustVO;

import java.util.ArrayList;

/**
 * @Auth: chunkind@naver.com
 * @Date: 2021. 05. 29.
 *
 * step1에서는 ROW를 인스턴스화 시킨 객체 vo를 사용하지 않았다
 * step2에서는
 * 	1) VO를 사용하고
 *  2) Business Logic을 별도의 DAO 클래스에 담는다
 *  3) Test는 따로 작성하서 실행한다.
 *  --------------------------------------------------------
 *  cust 클래스를 다시 생성...

CREATE TABLE cust(
    id varchar2(20) primary key,
    name varchar2(30) not null,
    addr varchar2(40) not null
);

CREATE TABLE cust(
    id varchar(20) primary key,
    name varchar(30) not null,
    addr varchar(40) not null
);

 * 테이블 작성이 끝났으면
 * vo 클래스 작성으로 바로 간다.
 * 1)이때 vo클래스 이름은 주로 테이블명을 사용한다
 * ex)CustVO | MemberVO | CustomerVO....
 * 2)VO클래스는 주로 패키지 이름에 vo가 들어간다...
 * 나중에는 domain | dto 라는 이름으로도 사용되는데, 이건 나중에 이야기 하자
 */
public class TestApp {
    static{
        try{
            Class.forName(DataBaseInfo.DRIVER_NAME);
            System.out.println("드라이버 로딩 성공...");
        }catch(ClassNotFoundException e){
            System.out.println("드라이버 로딩 실패...");
        }
    }

    public static void main(String[] args) throws Exception{
        /*
         * DAO 클래스에서 b.l() 하나씩 구현할때마다
         * main() 밑에서 하나씩 호출해서 일일히 실행해봐야 한다.
         * 실행에 성공하면 호출한 부분을 다시 주석처리 한다.
         */
        CustDAO dao = new CustDAO();
        ArrayList<CustVO> list = null;

        //생성
        dao.addCust(new CustVO("chunkind", "춘부리", "부천"));
        dao.addCust(new CustVO("momo02", "박부리", "인천"));
        dao.addCust(new CustVO("xogml", "김태희", "서울"));

        //조희
        System.out.println("등록후 모두 조회");
        list = dao.getAllCust();
        for(CustVO v : list){
            System.out.println(v);
        }

        //삭제
        dao.deleteCust("xogml");

        //조희
        System.out.println("삭제후 전체 조회");
        list = dao.getAllCust();
        for(CustVO v : list){
            System.out.println(v);
        }

        //수정
        dao.updateCust(new CustVO("momo02", "이쁘니", "서울"));

        //한명 조회
        System.out.println("수정후 한명 조회");
        System.out.println(dao.getCust("momo02"));
    }
}
