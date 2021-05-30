package broker.three.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import broker.three.exception.DuplicateSSNException;
import broker.three.exception.InvalidTransactionException;
import broker.three.exception.RecordNotFoundException;
import broker.three.shares.Command;
import broker.three.vo.CustomerRec;
import broker.three.vo.StockRec;

/*
 * 중요한 기능 2가지 ::
 * 1) 소켓통신의 로직을 담고 있어야 한다....지금 하세여..
 * 2) 도시락 싸고 날리고/ 다시 날라오는 도시락 받는 로직
 */
public class Protocol {
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Command cmd;

	private static final int MIDDLE_PORT = 60000;

	public Protocol(String serverIp)throws Exception{
		s = new Socket(serverIp, MIDDLE_PORT);
		oos = new ObjectOutputStream(s.getOutputStream());
		ois = new ObjectInputStream(s.getInputStream());
	}

	public void writeCommand(Command cmd){
		try{
			oos.writeObject(cmd);
			System.out.println("client writeCommand()....end");
		}catch(IOException e){
			System.out.println("protocol :  writeCommand()...."+e);
		}
	}

	public int getResponse(){ 
		//readObject() + getResult().getStatus()
		try{
			cmd= (Command) ois.readObject();
			System.out.println("client readCommand....");
		}catch(Exception e){
			System.out.println("client readCommand...."+e);
		}
		int status = cmd.getResults().getStatus();
		return status;
	}

	/**
	 * 1. 도시락 싼다.
	 * 2. 도시락 던진다...Jury쪽으로
	 * -----------------------------
	 * 3. Jury가 다시 던지는 도시락 받는다
	 * 4. 열어서 리턴한다
	 */
	public void buyShares(String ssn, String symbol, int quantity){
		cmd = new Command(Command.BUYSHARES);//10
		String[ ] str = {ssn, symbol, String.valueOf(quantity)};
		cmd.setArgs(str);
		
		writeCommand(cmd);
		
		getResponse();			
	}

	/**
	 * 1.도시락 싼다.
	 * 2.도시락 던진다.
	 * 3.도시락 받아서 상태값 뽑아낸다.
	 */
	public void sellShares(String ssn, String symbol, int quantity)	throws RecordNotFoundException, InvalidTransactionException{

		cmd = new Command(Command.SELLSHARES);//20
		String[ ] str = {ssn, symbol, String.valueOf(quantity)};
		cmd.setArgs(str); //도시락으로 pack
		
		writeCommand(cmd);
		
		int status = getResponse();
		if(status==-1) throw new RecordNotFoundException();
		if(status==-3) throw new InvalidTransactionException();			
	}
	
	public float getStockPrice(String symbol){	
		float price = 0.0f;
		cmd = new Command(Command.GETSTOCKPRICE);
		String[ ] str = {symbol};
		cmd.setArgs(str);
		
		writeCommand(cmd);
		
		getResponse();
		price=(float)cmd.getResults().get(0);
		return price;
	}	
	
	public ArrayList<CustomerRec> getAllCustomer(){	
		ArrayList<CustomerRec> list = new ArrayList<CustomerRec>();
		cmd = new Command(Command.GETALLCUSTOMER);
		
		writeCommand(cmd);
		
		getResponse(); //cmd를 읽어들인다..
		list = (ArrayList<CustomerRec>)cmd.getResults().get(0);
		return list;
	}	
	
	public CustomerRec getCustomer(String ssn)throws RecordNotFoundException{	
		CustomerRec cust = null;
		cmd = new Command(Command.GETCUSTOMER);
		String[ ] str = {ssn};
		cmd.setArgs(str);
		
		writeCommand(cmd);
		
		int status = getResponse();
		if(status==-1) throw new RecordNotFoundException();
		cust = (CustomerRec)cmd.getResults().get(0);
		
		return cust;		
	}	
	
	public ArrayList<StockRec> getAllStock(){
		ArrayList<StockRec> list = new ArrayList<StockRec>();
		
		cmd = new Command(Command.GETALLSTOCK);
		
		writeCommand(cmd);
		
		getResponse();
		list = (ArrayList<StockRec>)cmd.getResults().get(0);
		return list;
	}
	
	public void addCustomer(CustomerRec cust) throws DuplicateSSNException{	
		cmd = new Command(Command.ADDCUSTOMER);
		String[ ] str = {cust.getSsn(), cust.getName(), cust.getAddress()};
		cmd.setArgs(str);
		
		writeCommand(cmd);
		
		int status = getResponse();
		if(status==-2) throw new DuplicateSSNException();		
	}
	
	public void deleteCustomer(String ssn)	throws RecordNotFoundException{		
		cmd = new Command(Command.DELETECUSTOMER);
		String[ ] str = {ssn};
		cmd.setArgs(str);
		
		writeCommand(cmd);
		
		int status=  getResponse();
		if(status==-1) throw new RecordNotFoundException();		
	}
	
	public void updateCustomer(CustomerRec cust) throws RecordNotFoundException{
		cmd = new Command(Command.UPDATECUSTOMER);
		String[ ] str = {cust.getSsn(), cust.getName(), cust.getAddress()};
		cmd.setArgs(str);
		
		writeCommand(cmd);
		
		int status = getResponse();
		if(status==-1) throw new RecordNotFoundException();	
	}	
}