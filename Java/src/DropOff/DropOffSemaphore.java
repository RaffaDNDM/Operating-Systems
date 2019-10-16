package DropOff;

import os.Semaphore;

public class DropOffSemaphore extends DropOff
{
	private Semaphore m = new Semaphore(true);
	private Semaphore priorityQueue = new Semaphore(false);
	private Semaphore normalQueue = new Semaphore(false);
	private Semaphore waitFreeC = new Semaphore(false);
	
	public int inCoda(boolean prio)
	{
		m.p();
		
		if(prio)
		{
			wprio++;
			
			if(punLib==0)
				priorityQueue.p();
		
			wprio--;
			
		}
		else 
		{
			wnorm++;
			
			if(punLib==0 || wprio>0)
				normalQueue.p();
		
			wnorm--;
		}
		
		punLib--;
		
		int pos=0;
		
		if(pLib[1])
		{
			pos=1;
		}
		
		pLib[pos]=false;
		m.v();
	
		return pos;
	}

	public void regok(int pos)
	{
		m.p();
		
		if(pos==1 && !libCD)
			waitFreeC.p();
		
		if(pos==0)
			libCD=false;
			
		m.v();
	}

	public void term(int pos)
	{
		m.p();
		
		if(pos==0)
		{
			libCD=true;
			
			if(waitFreeC.queue()>0)
				waitFreeC.v();
		}
			
		punLib++;
		pLib[pos]=true;
		
		if(wprio>0)
			priorityQueue.v();
		else if(wnorm>0)
			normalQueue.v();
		
		m.v();
	}
}
