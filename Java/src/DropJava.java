import java.util.Random;

import os.Util;

public class DropJava
{	
	private int punLib = 2;
	private boolean[] pLib = new boolean[2]; // true se libero
	private boolean libCD = true;
	
	private int wprio = 0;
	private int wnorm = 0;
	
	private long minT = 1000L;
	private long maxT = 3000L;
	
	private final static long PRESATIM = 1000L;
	private final static long TRASFTIM = 2000L;
	private static final int VIAGGIATORI = 20;
	
	private int ticketPrio = 0;
	private int servicePrio = 0;
	private int ticketNorm = 0;
	private int serviceNorm = 0;
	
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
	
	private class Viag extends Thread
	{
		private int num;
		
		public Viag(int num)
		{
			this.num=num;
		}
		
		public void run()
		{
			Random r = new Random();
			
			boolean priority = r.nextBoolean();
			
			String type = "";
			if(priority)
			{
				type = "PRIORITY: ";
			}
			else 
			{
				type = "NORMAL: ";
			}
			
			System.out.println(type+"Il viaggiatore "+num+" entra nell'aeroporto");
			int banco = inCoda(priority);
			System.out.println(type+"Il viaggiatore "+num+" è al banco "+banco);
			Util.rsleep(minT, maxT);
			System.out.println(type+"Il bagaglio di "+num+" è stato registrato");
			regok(banco);
			Util.sleep(PRESATIM);
			System.out.println(type+"Il bagaglio di "+num+" è stato trasferito sul nastro");
			Util.sleep(TRASFTIM+(banco*TRASFTIM));
			term(banco);
			System.out.println(type+"Il viaggiatore "+num+" esce dal banco");
		}
	}
	
	public static void main(String[] args)
	{
		DropJava d = new DropJava();
		
		for(int i=0; i<VIAGGIATORI; i++)
		{
			d.new Viag(i).start();
			Util.rsleep(250, 1000);
		}
	}
}
