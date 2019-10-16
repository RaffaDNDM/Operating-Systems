package Airport;

import os.Monitor;

public class TorreDiControlloMon extends TorreDiControllo
{
	private Monitor m = new Monitor(); 
	private Monitor.Condition versoA = m.new Condition();
	private Monitor.Condition versoB = m.new Condition();
	private Monitor.Condition atterraggio = m.new Condition();
	
	public TorreDiControlloMon()
	{
		type_app="            MONITOR DI HOARE";
	}
	
	public void richAccessoPista(int io)
	{
		m.mEnter();
		
		attesaPista++;
		
		stampaSituazioneAeroporto();
		
		if(liberiA==0 || (attesaAtterraggio>0 && prenotaAtt==1))
		{
			versoA.cWait();
		}
		
		attesaPista--;
		liberiA--;
		
		stampaSituazioneAeroporto();
		
		m.mExit();
	}

	public void richAutorizDecollo(int io)
	{
		m.mEnter();
		
		attesaDecollo++;
		
		stampaSituazioneAeroporto();
		
		if(liberiB==0)
		{
			versoB.cWait();
		}
		
		if(attesaPista>0)
			versoA.cSignal();
		
		stampaSituazioneAeroporto();
		
		m.mExit();
	}

	public void inVolo(int io)
	{
		m.mEnter();
		
		aereiDecollati++;
		
		stampaSituazioneAeroporto();
		
		if(attesaAtterraggio>0 && liberiA==2 && liberiB==2)
			atterraggio.cSignal();
		else if(attesaDecollo>0)
			versoB.cSignal();
		
		m.mExit();
	}

	public void richAutorizAtterraggio(int io)
	{
		m.mEnter();
		
		attesaAtterraggio++;
		
		stampaSituazioneAeroporto();
		
		if(liberiA<2 || liberiA<2 || prenotaAtt==1)
			atterraggio.cWait();
		
		prenotaAtt=1;
		attesaAtterraggio--;
		liberiA=0;
		
		stampaSituazioneAeroporto();
		
		m.mExit();
	}

	public void freniAttivati(int io)
	{
		m.mEnter();
		
		liberiA=2;
		liberiB=0;
		
		stampaSituazioneAeroporto();
		
		m.mExit();
	}

	public void inParcheggio(int io)
	{
		m.mEnter();
		
		liberiB=2;
		stampaSituazioneAeroporto();
		
		if(attesaAtterraggio>0)
			atterraggio.cSignal();
		else if(attesaPista>0)
			versoA.cSignal();
		
		m.mExit();
	}
}
