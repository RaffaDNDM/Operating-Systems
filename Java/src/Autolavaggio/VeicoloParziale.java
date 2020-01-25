/**
@author Di Nardo Di Maio Raffaele
*/

package Autolavaggio;

import os.Util;

public class VeicoloParziale extends Thread
{
	private int n;
	private Autolavaggio al;

	public VeicoloParziale(int n, Autolavaggio al)
	{
		this.n=n;
		this.al=al;
	}

	public void run()
	{
		Util.rsleep(500, 2000);
        al.prenotaParziale();
        Util.sleep(8000);            //ritardo inserito per simulare il lavaggio esterno
        al.pagaParziale();
	}
}
