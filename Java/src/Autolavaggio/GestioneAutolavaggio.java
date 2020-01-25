/**
@author Di Nardo Di Maio Raffaele
*/

package Autolavaggio;

import java.util.Scanner;
import os.Util;

public class GestioneAutolavaggio
{
	public static void main(String[] args) throws InterruptedException
    {
		System.out.println("Scegliere il numero di automobili");
		Scanner input = new Scanner(System.in);

		int num_cars = Integer.parseInt(input.nextLine());

		System.out.println("Scegliere il tipo di programma da eseguire");
		System.out.println("1. Monitor di Hoare");
		System.out.println("2. Monitor di Java");
		System.out.println("3. Regione critica");
		System.out.println("4. Semaforo numerico");

		Autolavaggio lav=null;

		int choice = Integer.parseInt(input.nextLine());

		switch(choice)
		{
			case 1:
				lav = new AutolavaggioMonitor();
				break;
			case 2:
				lav = new AutolavaggioJava();
				break;
			case 3:
				lav = new AutolavaggioRegion();
				break;
			case 4:
				lav = new AutolavaggioSemPriv();
				break;
			default:
				System.exit(1);
		}

		lav.title();

		Util.sleep(4000);

		lav.stampaSituazioneLavaggio();

        for (int i = 0; i < num_cars; i++)
        {
            if (Util.randVal(1,4) < 4)
                new VeicoloParziale(i, lav).start();
            else
                new VeicoloTotale(i, lav).start() ;

            Util.rsleep(250, 1000);
        }
    }
}
