package Desks;

import os.Util;

public class Client extends Thread 
{	
	private Desks d;
	
	public Client(int num, Desks d)
	{
		super(num+"^");
		this.d=d;
	}
	
	public void run() 
	{
		System.out.println("Il cliente "+getName()+" è entrato in coda");
		int sp=d.enterQueue();
		System.out.println("Il cliente "+getName()+" viene servito da "+sp);
		Util.rsleep(1000,10000);
		d.exitQueue(sp);
		System.out.println("Il cliente "+getName()+" è stato servito da "+sp);
	}
}