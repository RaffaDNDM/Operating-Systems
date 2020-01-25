/**
@author Di Nardo Di Maio Raffaele
*/

package Strade;

public abstract class Strade
{
   protected int[] numv = new int[2];
   protected int[] attesa = new int[3];
   protected int corsia = 0;
   protected int serviti = 0;
   protected final static int MAXV = 3;

   public Strade()
   {
      for(int i=0; i<2; i++)
         numv[i]=0;

      for(int i=0; i<3; i++)
         attesa[i]=0;
   }

   public abstract int entra(int strada);

   public abstract void esce(int corsia);

   public void printState()
   {
      System.out.println("--------------------------------------------------");
      System.out.printf("         Corsia0: %2d      Corsia1: %2d\n",numv[0],numv[1]);
      System.out.println("--------------------------------------------------");
      System.out.printf("   Strada0: %2d    Strada1: %2d   Strada2: %2d\n",attesa[0],attesa[1],attesa[2]);
      System.out.println("--------------------------------------------------");
      System.out.printf("                   Serviti: %2d\n",serviti);
      System.out.println("--------------------------------------------------");
   }
}
