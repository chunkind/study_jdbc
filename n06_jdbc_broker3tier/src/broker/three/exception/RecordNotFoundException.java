package broker.three.exception;

public class RecordNotFoundException extends Exception{
	public RecordNotFoundException(String message){
		super(message);
	}
	
	public RecordNotFoundException(){
		this("그런 사람이 존재하지 않습니다.");
	}
}
