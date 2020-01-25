/**
@author Di Nardo Di Maio Raffaele
*/

package Airport;

import os.Util;

public class AereoCheDecolla extends Thread
{
	private int io;
	private TorreDiControllo tc;
	
	public AereoCheDecolla(int io, TorreDiControllo tc)
	{
		this.io=io;
		this.tc=tc;
	}

	public void run()
	{
		//il pilota � pronto per entrare in pista
		tc.richAccessoPista(io);
		Util.rsleep(1000, 3000);
		//il pilota entra nella zona A
		tc.richAutorizDecollo(io);
		Util.rsleep(1000, 3000);
		//il pilota impegna la zona B e libera la A
		tc.inVolo(io);
		//il pilota � in volo
	}
}
