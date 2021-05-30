package broker.twotier.exception;

/**
 * 잘못된 트랜잭션 Exception class
 */
public class InvalidTransactionException extends Exception{
	public InvalidTransactionException(String message){
		super(message);
	}
	public InvalidTransactionException(){
		this("가진 주식의 량보다 팔려는 주식의 량이 많습니다");
	}
}
