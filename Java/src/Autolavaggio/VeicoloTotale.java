/**
@author Di Nardo Di Maio Raffaele
*/

package Autolavaggio;

import os.Util;

public class VeicoloTotale extends Thread
{
		private int n;
		private Autolavaggio al;

		public VeicoloTotale(int n, Autolavaggio al)
		{
			this.n=n;
			this.al=al;
		}

		public void run()
		{
			Util.rsleep(1000, 3000);
	        al.prenotaTotale();
	        Util.sleep(8000);            //ritardo inserito per simulare il lavaggio esterno
	        al.lavaInterno();
	        Util.rsleep(6000);     // simula lavaggio interno
	        al.pagaTotale();
		}
}
