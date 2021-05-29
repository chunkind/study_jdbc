package exception;

public class RecordNotFoundException extends Exception{
	public RecordNotFoundException(String message) {
		super(message);
	}
	public RecordNotFoundException(){
		this("데이터가 존재하지 않습니다.");
	}
}
