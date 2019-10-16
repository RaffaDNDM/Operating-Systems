package Airport;

import java.util.Scanner;

import os.Util;

public class AeroportoSemP
{
	public static void main(String[] args)
	{
		TorreDiControllo tc = new TorreDiControlloSemP();
		
		tc.title();
		
		Util.sleep(4000);
		
		System.out.println("Specificare il numero di aerei");
		Scanner input = new Scanner(System.in);
		
		int aerei = Integer.parseInt(input.nextLine());
		
		tc.stampaSituazioneAeroporto();
		
		for(int i=0; i<aerei; i++)
		{
			int r = Util.randVal(1,10);
			
			if(r<4)
				new AereoCheDecolla(i, tc).start();
			else
				new AereoCheAtterra(i, tc).start();
			
			Util.rsleep(250, 2000);
		}
	}
}
