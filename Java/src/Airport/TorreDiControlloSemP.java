/**
@author Di Nardo Di Maio Raffaele
*/

package Airport;

import os.Semaphore;

public class TorreDiControlloSemP extends TorreDiControllo
{
	Semaphore m = new Semaphore(true);
	Semaphore versoA = new Semaphore(false);
	Semaphore versoB = new Semaphore(false);
	Semaphore atterraggio = new Semaphore(false);

	public TorreDiControlloSemP()
	{
		type_app="            SEMAFORO PRIVATO";
	}

	public void richAccessoPista(int io)
	{
		m.p();

		attesaPista++;

		stampaSituazioneAeroporto();

		if(liberiA==0 || (attesaAtterraggio>0 && prenotaAtt==1))
		{
			m.v();
			versoA.p();
		}

		attesaPista--;
		liberiA--;

		stampaSituazioneAeroporto();

		m.v();
	}

	public void richAutorizDecollo(int io)
	{
		m.p();
		attesaDecollo++;

		stampaSituazioneAeroporto();

		if(liberiB==0)
		{
			m.v();
			versoB.p();
		}

		attesaDecollo--;
		liberiB--;
		liberiA++;

		stampaSituazioneAeroporto();

		if(attesaPista>0)
		{
			versoA.v();
		}

		m.v();
	}

	public void inVolo(int io)
	{
		m.p();

		liberiB++;
		aereiDecollati++;

		stampaSituazioneAeroporto();

		if(attesaAtterraggio>0 && liberiA==2 && liberiB==2)
			atterraggio.v();
		else if(attesaDecollo>0)
			versoB.v();

		m.v();
	}

	public void richAutorizAtterraggio(int io)
	{
		m.p();
		attesaAtterraggio++;

		stampaSituazioneAeroporto();

		if(liberiA<2 || liberiB<2 || prenotaAtt==1)
		{
			m.v();
			atterraggio.p();
		}

		attesaAtterraggio--;
		liberiA=0;
		prenotaAtt=1;

		stampaSituazioneAeroporto();

		m.v();
	}

	public void freniAttivati(int io)
	{
		m.p();

		aereiAtterrati++;
		liberiA=2;
		liberiB=0;

		stampaSituazioneAeroporto();

		m.v();
	}

	public void inParcheggio(int io)
	{
		m.p();

		liberiB=2;
		aereiParcheggiati++;
		prenotaAtt=0;

		if(attesaAtterraggio>0)
			atterraggio.v();
		else if(attesaPista > 0)
			versoA.v();

		stampaSituazioneAeroporto();

		m.v();
	}

}
