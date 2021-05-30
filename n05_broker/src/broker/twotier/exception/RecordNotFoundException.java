package broker.twotier.exception;

/**
 * 존재하지 않는 레코드 관련 Exception class
 */
public class RecordNotFoundException extends Exception{
	public RecordNotFoundException(String message){
		super(message);
	}
	public RecordNotFoundException(){
		this("그런 사람이 존재하지 않습니다.");
	}
}
