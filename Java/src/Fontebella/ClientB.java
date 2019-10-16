package Fontebella;

import os.Util;

public class ClientB extends Thread
{
	private int num;
	private FontebellaBaths f;
	public static final long FILL_TIME = 15500;
	
	public ClientB(int num, FontebellaBaths f)
	{
		this.num=num;
		this.f=f;
		this.num=num;
	}
	
	public void run()
	{
		int spout = f.enterB();
		Util.sleep(FILL_TIME);		
		f.endFill();
	}
}

