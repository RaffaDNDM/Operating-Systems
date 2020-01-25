/**
@author Di Nardo Di Maio Raffaele
*/

package Officina;

public abstract class Officina
{
   protected final static int TIPO0 = 0;
   protected final static int TIPO1 = 1;
   protected int NTAV;
   protected int stazioniLibere;
   protected int postiLiberi;
   protected int[] stato;
   protected int[] inAttesa = new int[2];
   protected int[] served = new int[2];

   public Officina(int NTAV)
   {
      this.NTAV = NTAV;

      this.stato = new int[NTAV];
      stazioniLibere = NTAV;
      postiLiberi = 2*NTAV;

      for(int i=0; i<NTAV; stato[i++]=0);

      inAttesa[TIPO0] = 0;
      inAttesa[TIPO1] = 0;
   }

   public int trovaStazione()
   {
      for(int i=0; i<NTAV; i++)
      {
         if(stato[i]==0)
            return i;
      }

      return -1;
   }

   public int trovaPosto()
   {
      int index = -1;
      boolean first = true;

      for(int i=0; i<NTAV; i++)
      {
         if(stato[i]==1)
            return i;

         if(stato[i]==0 && first)
         {
            index = i;
            first = false;
         }
      }

      return index;
   }

   public abstract int entra(int tipo);

   public abstract void esce(int tipo, int pos);

   public void printState()
   {
      System.out.println("-----------------------------------------------------");
      System.out.printf("    Posti Liberi: %2d   Stazioni Libere: %2d\n",postiLiberi, stazioniLibere);
      System.out.println("-----------------------------------------------------");
      System.out.printf("  TIPO 0 in coda: %2d    TIPO 1 in coda: %2d\n",inAttesa[TIPO0], inAttesa[TIPO1]);
      System.out.println("-----------------------------------------------------");
      System.out.printf("  TIPO 0 serviti: %2d    TIPO 1 serviti: %2d\n",served[TIPO0], served[TIPO1]);
      System.out.println("-----------------------------------------------------");

      String s1 = "      Stazioni-->";
      String s2 = "  Posti Liberi-->";
      for(int i=0; i<NTAV; i++)
      {
         s1 = s1+"  "+i;
         s2 = s2+"  "+(2-stato[i]);
      }

      System.out.println(s1);
      System.out.println(s2);
      System.out.println("-----------------------------------------------------");
   }
}
