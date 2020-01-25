/**
@author Di Nardo Di Maio Raffaele
*/

package Airport;

import os.Util;

public class AereoCheAtterra extends Thread
{
	private int io;
	private TorreDiControllo tc;

	public AereoCheAtterra(int io, TorreDiControllo tc)
	{
		this.io=io;
		this.tc=tc;
	}

	public void run()
	{
		//il pilota � in volo e deve atterrare
		tc.richAutorizAtterraggio(io);
		Util.rsleep(1000, 4000);
		//il pilota atterra occupando la zona A
		tc.freniAttivati(io);
		Util.rsleep(1000, 4000);
		//il pilota impegna la zona B e libera la A
		tc.inParcheggio(io);
		//il pilota esce dalla pista e libera B
	}
}
