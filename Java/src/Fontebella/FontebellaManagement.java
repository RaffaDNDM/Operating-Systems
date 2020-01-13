package Fontebella;

import java.util.Scanner;
import os.Util;

public class FontebellaManagement
{
	public static void main(String[] args)
	{
		System.out.println("Scegliere il numero di persone che arrivano per dissetarsi");
		Scanner input = new Scanner(System.in);

		int num_customers = Integer.parseInt(input.nextLine());

		System.out.println("Scegliere il tipo di programma da eseguire");
		System.out.println("1. Monitor di Hoare");
		System.out.println("2. Monitor di Java");
		System.out.println("3. Regione critica");
		System.out.println("4. Semaforo numerico");

		FontebellaBaths f = null;

		int choice = Integer.parseInt(input.nextLine());

		switch(choice)
		{
			case 1:
				f = new FontebellaBathsMonitor();
				break;
			case 2:
				f = new FontebellaBathsJava();
				break;
			case 3:
				f = new FontebellaBathsRegion();
				break;
			case 4:
				f = new FontebellaBathsSemaphore();
				break;
			default:
				System.exit(1);
		}

		f.title();

		Util.sleep(4000);

		f.initMatrix();
		f.printFontebellaState();

		for(int i=0; i<num_customers; i++)
		{
			Util.rsleep(500,2000);
			int n = Util.randVal(1, 2);

			if(n>1)
				new ClientA(i, f).start();
			else
				new ClientB(i, f).start();
		}
	}
}
