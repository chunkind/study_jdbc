package broker.three.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server Side에서의 통신을 담당하는 대표주자
 * 서버측으로 접속하는 클라이언트의 접속을 이곳에서 다 받는다..
 * ServerSocket이 먼저 만들어져야 한다.
 * 클라이언트가 접속하면 소켓을 리턴받아서 JuryThread에게 넘긴다.
 * JuryThread는 클라이언트에서 보낸 정보를 열어서 알맞는 Database의 메소드를
 * 호출하게 될 것이다.
 * ::
 * 무한루핑을 돌면서 접속하는 클라이언트를 맞아들이는 역할만 하는 리스너 쓰레드
 */
public class ProtocolHandler extends Thread{
	ServerSocket server;
	Socket s;
	JuryThread jury;
	Database db;
	static final int MIDDLE_PORT = 60000;
	
	/*
	 * 생성자 안의 로직과 run() 안의 로직을 구분할수 있음..참 좋겠어여...
	 */
	public ProtocolHandler(String serverIp){ //생성자
		try{
			server = new ServerSocket(MIDDLE_PORT);
			db = new Database(serverIp);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("start Protocol Handler....service port :: "+MIDDLE_PORT);
	}

	/**
	 * 무한 루핑을 돌면서 클라이언트가 접속하면 소켓을 리턴
	 * 리턴된 소켓을 JuryThread가 가져갈수 있는 로직을 작성
	 * 그리고 이곳에서 JuryThread는 start되어진다...
	 */
	public void run(){
		while(true){
			try{
				s = server.accept();
				jury = new JuryThread(s,db);
				jury.start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}