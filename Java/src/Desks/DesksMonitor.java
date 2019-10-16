package Desks;
import os.Monitor;

public class DesksMonitor extends Desks
{
	private int free_desks;
	private int num_desks;
	private Monitor m = new Monitor();
	private Monitor.Condition queue= m.new Condition();
	private boolean free[];
	
	public DesksMonitor(int num_desks)
	{
		this.num_desks=free_desks=num_desks;
		free= new boolean[num_desks];
		for(int i=0; i<num_desks; free[i++]=true);
	}

	public int enterQueue() 
	{
		m.mEnter();
		
		if(free_desks==0)
			queue.cWait();
		
		free_desks--;
		
		int i;
		for(i=0; i<num_desks; i++)
		{
			if(free[i])
				break;
		}
			
		free[i]=false;
		
		m.mExit();
		return i;
	}

	public void exitQueue(int desk) 
	{
		m.mEnter();
		free[desk]=true;
		free_desks++;
		queue.cSignal();
		m.mExit();
	}
}
