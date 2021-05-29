package broker.three.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import broker.three.exception.DuplicateSSNException;
import broker.three.exception.InvalidTransactionException;
import broker.three.exception.RecordNotFoundException;
import broker.three.shares.Command;
import broker.three.shares.Result;
import broker.three.vo.CustomerRec;
import broker.three.vo.StockRec;

public class JuryThread extends Thread{
	Database db;
	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Command cmd;
	
	JuryThread(Socket s, Database db){
		this.s = s;
		this.db = db;
		try{
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		}catch(IOException e){
			
		}
		System.out.println("Jury Creating.....");
	}
	
	/*
		계속 돌면서 도시락 받아낸다.
		도식락받아서 까본다....상수값, 반찬들이 튀어나온다.
		이걸 토대로 Database의 메소드를 직접 호출한다...
		switch문 안에서		
	 */
	public void run(){
		System.out.println("jury run().....");
		while(true){
			try{
				//1. 도시락 받는다.
				cmd = (Command)ois.readObject();
				System.out.println("cmd....jury reading....");
			}catch(Exception e){
				e.printStackTrace();
			}//catch
			
			//2. 도시락 깐다....Data UnPack
			int comm=cmd.getCommandValue();//10~90
			String[ ] args = cmd.getArgs();
			Result r = cmd.getResults();
			
			//3. switch문 안에서 상수값에 따라서 데이타베이스의 기능을 호출
			switch(comm){
			case 10 : //buyShares()
				try{
					db.buyShares(args[0], args[1], Integer.parseInt(args[2]));
					r.setStatus(0);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 20 : //sellShares()
				try{
					db.sellShares(args[0], args[1], Integer.parseInt(args[2]));
					r.setStatus(0);
				}catch(RecordNotFoundException e){
					System.out.println("주식을 팔 고객이 없네여..jury..");
					r.setStatus(-1);
				}catch(InvalidTransactionException e){
					System.out.println("주식을  너무 많이 팔려고..jury..");
					r.setStatus(-3);
				}catch(SQLException e){
					
				}
				break;
			case 30 : //getAllStock()
				try{
					ArrayList<StockRec> list=db.getAllStock();
					r.setStatus(0);
					r.add(list); //이부분이 있다..
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 40://getStockPrice
				try{
					float price=db.getStckPrice(args[0]);
					r.setStatus(0);
					r.add(price);
				}catch(Exception e){
					e.printStackTrace();
				}				
				break;
			case 50: //getAllCustomer()
				try{
					ArrayList<CustomerRec> list=db.getAllCustomer();
					r.setStatus(0);
					r.add(list);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 60: //getCustomer()
				try{
					CustomerRec cust=db.getCustomer(args[0]);
					r.setStatus(0);
					r.add(cust);
				}catch(Exception e){
					e.printStackTrace();
				}				
				break;
			case 70: //addCustomer
				try{
					db.addCustomer(new CustomerRec(args[0], args[1], args[2]));
					r.setStatus(0);
				}catch(DuplicateSSNException e){
					r.setStatus(-2);
					System.out.println("그런 사람 이미 있어여...jury..");
				}catch(Exception e){
					
				}
				break;
			case 80: //deleteCustomer
				try{
					db.deleteCustomer(args[0]);
					r.setStatus(0);
				}catch(RecordNotFoundException e){
					r.setStatus(-1);
					System.out.println("삭제할 사람 없어여.....jury..");
				}catch(Exception e){
					
				}
				break;
			case 90: //updateCustomer
				try{
					db.updateCustomer(new CustomerRec(args[0], args[1], args[2]));
					r.setStatus(0);
				}catch(RecordNotFoundException e){
					r.setStatus(-2);
					System.out.println("수정할 사람이 없어여...jury..");
				}catch(Exception e){
					
				}
				break;
			}//switch		
			
			//다시 Protocol 쪽으로 보낸다.
			try{
				oos.writeObject(cmd);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
}