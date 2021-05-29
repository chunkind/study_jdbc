package exception;

public class DuplicateIDException extends Exception{
	public DuplicateIDException(String message) {
		super(message);
	}
	public DuplicateIDException(){
		this("이미 존재하는 데이터입니다.");
	}
}
