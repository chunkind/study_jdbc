package broker.twotier.exception;

public class InvalidTransactionException extends Exception{
	public InvalidTransactionException(String message){
		super(message);
	}
	
	public InvalidTransactionException(){
		this("가진 주식의 량보다 팔려는 주식의 량이 많습니다");
	}
}
