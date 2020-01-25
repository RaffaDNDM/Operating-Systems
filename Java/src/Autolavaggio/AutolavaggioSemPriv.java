/**
@author Di Nardo Di Maio Raffaele
*/

package Autolavaggio;

import os.Semaphore;

public class AutolavaggioSemPriv extends Autolavaggio
{
	Semaphore m = new Semaphore(1,1);
	Semaphore inA = new Semaphore(0,1);
	Semaphore inB = new Semaphore(0,1);

	public AutolavaggioSemPriv()
	{
		type_app="                         SEMAFORO PRIVATO";
	}

	public void prenotaParziale()
	{
		m.p();

		partial_wait++;
		stampaSituazioneLavaggio();

		if((total_wait>0 && NUM_PLACES_B>total_size) || free_A==0)
		{
			m.v();
			inA.p();
		}

		partial_wait--;
		partial_size++;
		free_A--;
		stampaSituazioneLavaggio();
		m.v();
	}

	public void pagaParziale()
	{
		m.p();
		partial_done++;
		partial_size--;
		free_A++;
		stampaSituazioneLavaggio();

		if(total_wait>0 && NUM_PLACES_B>total_size)
			inB.v();

		else if(free_A!=0)
			inA.v();

		else
			m.v();
	}

	public void prenotaTotale()
	{
		m.p();
		total_wait++;
		stampaSituazioneLavaggio();

		if(free_A==0 && total_size==NUM_PLACES_B)
		{
			m.v();
			inB.p();
		}

		total_wait--;
		total_size++;
		free_A--;
		stampaSituazioneLavaggio();

		m.v();
	}

	public void lavaInterno()
	{
		m.p();
		free_A++;
		free_B--;
		stampaSituazioneLavaggio();

		if(total_wait>0 && total_size<NUM_PLACES_B)
			inB.v();
		else if(partial_wait>0)
			inA.v();
		else
			m.v();
	}

	public void pagaTotale()
	{
		m.p();

		free_B--;
		total_size--;
		total_done++;

		stampaSituazioneLavaggio();
		if(total_wait>0 && free_A>0)
			inB.v();
		else
			m.v();
	}
}
