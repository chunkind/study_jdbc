package broker.twotier.exception;

public class DuplicateSSNException extends Exception{
	public DuplicateSSNException(String message){
		super(message);
	}
	
	public DuplicateSSNException(){
		this("해당하는 ssn을 가진 사람이 이미 있습니다.");
	}
}
