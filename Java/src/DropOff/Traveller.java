package DropOff;

import java.util.Random;

import os.Util;

public class Traveller extends Thread
{
	private long minT = 1000L;
	private long maxT = 3000L;
	
	private final static long PRESATIM = 1000L;
	private final static long TRASFTIM = 2000L;
	
	private int num;
	private DropOff d;
	
	public Traveller(int num, DropOff d)
	{
		this.num=num;
		this.d=d;
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
		int banco = d.inCoda(priority);
		System.out.println(type+"Il viaggiatore "+num+" è al banco "+banco);
		Util.rsleep(minT, maxT);
		System.out.println(type+"Il bagaglio di "+num+" è stato registrato");
		d.regok(banco);
		Util.sleep(PRESATIM);
		System.out.println(type+"Il bagaglio di "+num+" è stato trasferito sul nastro");
		Util.sleep(TRASFTIM+(banco*TRASFTIM));
		d.term(banco);
		System.out.println(type+"Il viaggiatore "+num+" esce dal banco");
	}
}