/**
@author Di Nardo Di Maio Raffaele
*/

package Autolavaggio;

public class AutolavaggioJava extends Autolavaggio
{
	private static int ticketParziale=0;
	private static int ticketTotale=0;

	private static int ticketTot_attuale=0;
	private static int ticketPar_attuale=0;

	public AutolavaggioJava()
	{
		type_app="                        MONITOR DI JAVA";
	}

	public synchronized void prenotaParziale()
	{
		int ticket = ticketParziale++;
		partial_wait++;

		while((total_wait>0 && total_size < NUM_PLACES_B) || ticket!=ticketPar_attuale || free_A==0)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		partial_wait--;
		partial_size++;
		free_A--;
		ticketPar_attuale++;
		stampaSituazioneLavaggio();
	}

	public synchronized void pagaParziale()
	{
		partial_done++;
		partial_size--;
		free_A++;
		stampaSituazioneLavaggio();
		notifyAll();
	}

	public synchronized void prenotaTotale()
	{
		int ticket = ticketTotale++;
		total_wait++;

		while(ticket != ticketTot_attuale || free_A==0 || total_size == NUM_PLACES_B)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		total_wait--;
		total_size++;
		free_A--;
		ticketTot_attuale++;
		stampaSituazioneLavaggio();
	}

	public synchronized void lavaInterno()
	{
		//liberazione di A dopo sicurezza di essere in B

		free_A++;
		notifyAll();

		//ingresso in B
		free_B--;
		stampaSituazioneLavaggio();
	}

	public synchronized void pagaTotale()
	{
		//liberazione di B senza controllo
		free_B++;
		total_size--;
		total_done++;
		stampaSituazioneLavaggio();
		notifyAll();
	}
}
