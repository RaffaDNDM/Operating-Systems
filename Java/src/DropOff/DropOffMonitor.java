package DropOff;

import os.Monitor;
import os.Monitor.Condition;

public class DropOffMonitor extends DropOff
{
	private Monitor m = new Monitor();
	private Condition priorityQueue = m.new Condition();
	private Condition normalQueue = m.new Condition();
	private Condition waitFreeC = m.new Condition();
	
	public int inCoda(boolean prio)
	{
		m.mEnter();
		
		if(prio)
		{
			wprio++; 
			
			if(punLib==0)
				priorityQueue.cWait();
		
			wprio--;
		}
		else
		{
			wnorm++;
			
			if(punLib==0 || wprio>0)
				normalQueue.cWait();
			
			wnorm--;
		}
		
		punLib--;
		
		int pos=0;
		
		if(pLib[1])
		{
			pos=1;
		}		
		
		pLib[pos]=false;
		
		m.mExit();
		
		return pos;
	}

	public void regok(int pos)
	{
		m.mEnter();
		
		if(pos==1 && !libCD)
			waitFreeC.cWait();
		
		if(pos==0)
			libCD=false;
	
		m.mExit();
	}

	public void term(int pos)
	{
		m.mEnter();
		
		if(pos==0)
		{
			libCD=true;
			if(waitFreeC.queue()>0)
			{
				waitFreeC.cSignal();
			}
		}
		
		punLib++;
		pLib[pos]=true;
		
		if(wprio>0)
			priorityQueue.cSignal();
		else if(wnorm>0)
			normalQueue.cSignal();
			
		m.mExit();
	}
}
