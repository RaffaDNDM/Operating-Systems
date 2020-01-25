/**
@author Di Nardo Di Maio Raffaele
*/

package Airport;

import java.util.Scanner;

public class Aeroporto
{
	public static void main (String args[])
	{
		Scanner input = new Scanner(System.in);

		System.out.println("Scegliere il tipo di programma da eseuire");
		System.out.println("1. Monitor di Hoare");
		System.out.println("2. Monitor di Java");
		System.out.println("3. Regione critica");
		System.out.println("4. Semaforo numerico");

		int choice = Integer.parseInt(input.nextLine());

		switch(choice)
		{
			case 1:
				AeroportoMon.main(args);
				break;
			case 2:
				AeroportoMJ.main(args);
				break;
			case 3:
				AeroportoReg.main(args);
				break;
			case 4:
				AeroportoSemP.main(args);
				break;
			default:
				System.exit(1);
		}
	}
}
