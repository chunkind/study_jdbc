package step02.vo;
/*
 * vo 클래스란 ?
 * 1. Cust 테이블에 대한 정보를 담고 있는 클래스
 * 2. 이때 Cust 객체는 디비 테이블의 하나의  row가 인스턴스화 된것으로 볼수 있다
 * ::
 *  vo 클래스 작성방법
 *  1. 필드로 컬럼명을 선언한다....필드와 디비 컬럼이 반드시 일치하는것은 아니다.
 *  2. Encapsulation 기법으로 반드시 작성한다.
 *     field -- private
 *     method -- setter()/getter() 를 public
 *  3. toString()을 오버라이딩.
 */
public class CustVO {
	private String id;
	private String name;
	private String addr;
	
	public CustVO(String id, String name, String addr) {
		super();
		this.id = id;
		this.name = name;
		this.addr = addr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "CustVO [id=" + id + ", name=" + name + ", addr=" + addr + "]";
	}	
}

















