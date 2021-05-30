package broker.three.shares;

import java.util.Vector;

public class Result extends Vector{
	private int status = -1;

	public Result(){
		super(1); //한칸짜리로 만들다...왜냐면 2칸은 필요없다.
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
}