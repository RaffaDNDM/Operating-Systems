package DropOff;

public class DropOffJava extends DropOff
{	
	private int ticketPrio = 0;
	private int servicePrio = 0;
	private int ticketNorm = 0;
	private int serviceNorm = 0;
	
	public DropOffJava()
	{}
	
	public synchronized int inCoda(boolean prio)
	{
		if(prio)
		{	
			int ticket = ticketPrio++;
			wprio++;
			
			while(ticket!=servicePrio || punLib==0)
			{
				try
				{
					wait();
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			wprio--;
			servicePrio++;
		}
		else
		{
			int ticket = ticketNorm++;
			wnorm++;
			
			while(ticket!=serviceNorm || punLib==0 || wprio>0)
			{
				try
				{
					wait();
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		
			wnorm--;
			serviceNorm++;
		}
		
		if(pLib[0])
		{
			pLib[0]=false;
			punLib--;
			
			return 0;
		}
		else
		{
			pLib[1]=false;
			punLib--;

			return 1;
		}
	}
	
	public synchronized void regok(int pos)
	{
		while(pos==1 && !libCD)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		if(pos==0)
			libCD=false;
	}
	
	public synchronized void term(int pos)
	{	
		if(pos==0)
			libCD=true;
		
		punLib++;
		pLib[pos] = true;
		
		notifyAll();
	}
}
