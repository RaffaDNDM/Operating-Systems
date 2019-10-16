package Airport;

import os.Region;
import os.RegionCondition;

public class TorreDiControlloReg extends TorreDiControllo
{
	Region r = new Region(0);
	
	public TorreDiControlloReg()
	{
		type_app = "                         REGIONE CRITICA";
	}
	
	public void richAccessoPista(int io)
	{
		attesaPista++;
		
		stampaSituazioneAeroporto();
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return liberiA>0 && attesaAtterraggio==0 && prenotaAtt==0;
			}
		});
		
		attesaPista--;
		liberiA--;
		
		stampaSituazioneAeroporto();
		
		r.leave();
	}

	public void richAutorizDecollo(int io)
	{
		attesaDecollo++;
		
		stampaSituazioneAeroporto();
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return liberiB>0;
			}
				
		});
		
		liberiB--;
		liberiA++;
		attesaDecollo--;
		
		stampaSituazioneAeroporto();
		
		r.leave();
	}

	public void inVolo(int io)
	{		
		r.enterWhen();
		
		liberiB++;
		aereiDecollati++;
		
		stampaSituazioneAeroporto();
		
		r.leave();
	}

	public void richAutorizAtterraggio(int io)
	{
		attesaAtterraggio++;
		
		stampaSituazioneAeroporto();
		
		r.enterWhen(new RegionCondition() {
			public boolean evaluate()
			{
				return liberiA==2 && liberiB==2 && prenotaAtt==0;
			}
		});
		
		attesaAtterraggio--;
		liberiA=0;
		prenotaAtt=1;
		
		stampaSituazioneAeroporto();
		
		r.leave();
	}

	public void freniAttivati(int io)
	{
		r.enterWhen();
		
		liberiA=2;
		liberiB=0;
		aereiAtterrati++;
		
		stampaSituazioneAeroporto();
		
		r.leave();
	}

	public void inParcheggio(int io)
	{
		r.enterWhen();
		
		liberiB=2;
		prenotaAtt=0;
		aereiParcheggiati++;
		
		stampaSituazioneAeroporto();
		
		r.leave();
	}
}
