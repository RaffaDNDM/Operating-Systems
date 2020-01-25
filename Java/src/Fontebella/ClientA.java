/**
@author Di Nardo Di Maio Raffaele
*/

package Fontebella;

import os.Util;

public class ClientA extends Thread
{
	private int num;
	private FontebellaBaths f;
	public static final long FILL_TIME = 15500L;

	public ClientA(int num, FontebellaBaths f)
	{
		this.num=num;
		this.f=f;
		this.num=num;
	}

	public void run()
	{
		int next = f.enterA();
		Util.sleep(FILL_TIME);
		f.endFill();
	}
}
