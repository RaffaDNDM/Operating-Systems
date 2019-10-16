package Airport;

public class TorreDiControlloMJ extends TorreDiControllo
{
	private int ticketPista=0;
	private int ticketDecollo=0;
	private int ticketAtterraggio=0;
	private int servizioPista=0;
	private int servizioDecollo=0;
	private int servizioAtterraggio=0;
	
	public TorreDiControlloMJ()
	{
		type_app="                        MONITOR DI JAVA";
	}
	
	public synchronized void richAccessoPista(int io)
	{
		int ticket = ticketPista++;
		attesaPista++;
		stampaSituazioneAeroporto();
		
		while(ticket!=servizioPista || liberiA==0 || attesaAtterraggio>0 || prenotaAtt ==1 )
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		attesaPista--;
		servizioPista++;
		liberiA--;
		stampaSituazioneAeroporto();
	}

	public synchronized void richAutorizDecollo(int io)
	{
		int ticket = ticketDecollo++;
		attesaDecollo++;
		stampaSituazioneAeroporto();
		
		while(ticket!=servizioDecollo || liberiB==0)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		attesaDecollo--;
		servizioDecollo++;
		liberiA++;
		liberiB--;
		stampaSituazioneAeroporto();
		
		notifyAll();
	}

	public synchronized void inVolo(int io)
	{
		liberiB++;
		aereiDecollati++;
		stampaSituazioneAeroporto();
		
		notifyAll();
	}

	public synchronized void richAutorizAtterraggio(int io)
	{
		int ticket = ticketAtterraggio++;
		attesaAtterraggio++;
		stampaSituazioneAeroporto();
		
		while(ticket!=servizioAtterraggio || (liberiA<2 || liberiB<2) )
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		prenotaAtt = 1;
		liberiA=0;
		liberiB=0;
		servizioAtterraggio++;
		attesaAtterraggio--;
		stampaSituazioneAeroporto();
	}

	public synchronized void freniAttivati(int io)
	{
		liberiA=2;
		aereiAtterrati++;
		stampaSituazioneAeroporto();
	}

	public synchronized void inParcheggio(int io)
	{
		liberiB=2;
		aereiParcheggiati++;
		prenotaAtt = 0;
		stampaSituazioneAeroporto();
		notifyAll();
	}
}
