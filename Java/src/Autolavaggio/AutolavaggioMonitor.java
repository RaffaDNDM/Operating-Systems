/**
@author Di Nardo Di Maio Raffaele
*/

package Autolavaggio;

import os.Monitor;

public class AutolavaggioMonitor extends Autolavaggio
{
	private Monitor m = new Monitor();
	private Monitor.Condition inPart = m.new Condition();
	private Monitor.Condition inTot = m.new Condition();

	public AutolavaggioMonitor()
	{
		type_app="                        MONITOR DI HOARE";
	}


	public void prenotaParziale()
	{
		m.mEnter();
		partial_wait++;

		stampaSituazioneLavaggio();

		if(free_A==0 || (total_wait>0 && total_size<NUM_PLACES_A))
			inPart.cWait();

		partial_wait--;
		partial_size++;
		free_A--;

		stampaSituazioneLavaggio();

		m.mExit();
	}

	public void pagaParziale()
	{
		m.mEnter();

		free_A++;
		partial_done++;
		partial_size++;

		stampaSituazioneLavaggio();

		if(total_wait>0 && total_size<NUM_PLACES_B)
			inTot.cSignal();
		else
			inPart.cSignal();


		m.mExit();
	}

	public void prenotaTotale()
	{
		m.mEnter();

		total_wait++;

		stampaSituazioneLavaggio();

		if(free_A==0 && total_size==NUM_PLACES_B)
			inTot.cWait();

		total_wait--;
		total_size++;
		free_A--;

		stampaSituazioneLavaggio();

		m.mExit();
	}

	public void lavaInterno()
	{
		m.mEnter();

		free_A++;
		free_B--;

		stampaSituazioneLavaggio();

		if(total_wait>0 && total_size<NUM_PLACES_B)
			inTot.cSignal();
		else if(partial_wait>0)
			inPart.cSignal();

		m.mExit();
	}

	public void pagaTotale()
	{
		m.mEnter();

		free_B++;
		total_size--;
		total_done++;

		stampaSituazioneLavaggio();

		if(total_wait>0 && free_A>0)
			inTot.cSignal();

		m.mExit();
	}
}
