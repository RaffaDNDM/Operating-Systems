/**
@author Di Nardo Di Maio Raffaele
*/

package Treni;

public class Azione
{
   public final static int SEQUENCES = 6;
   public final static int A = 0;
   public final static int B = 0;
   public final static int C = 0;
   public final static int D = 0;
   public final static int E = 0;
   public final static int F = 0;

   public boolean[] sequences_state = new boolean[SEQUENCES];

   public Azione()
   {
      for(int i=0; i<SEQUENCES; sequences_state[i++]=true);

      sequences_state[A] = false;
      sequences_state[C] = false;
   }

   public synchronized int entra(int tratta)
   {
      int next_sequence = next(tratta);

      while(!sequences_state[next_sequence])
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruption");
         }
      }

      sequences_state[tratta] = true;

      if(tratta == B)
         sequences_state[E] = true;
      else if(tratta == E)
         sequences_state[B] = true;

      sequences_state[next_sequence] = false;

      if(next_sequence == B)
         sequences_state[E] = false;
      else if(next_sequence == E)
         sequences_state[B] = false;

      System.out.println(this);
      notify();
      return next_sequence;
   }

   private int next(int tratta)
   {
      return (tratta+1)%SEQUENCES;
   }

   public static final String tr2TR[] = {"A", "B", "C", "D", "E", "F"};

   public synchronized String toString()
   {
      // qui e` meglio sincronizzare
      String ret = "*s* stato semafori [";

      for (int i=0; i<SEQUENCES; i++)
         if (i==B || i==E)
             continue;
         else
             ret += tr2TR[i]+"=" +(!sequences_state[i] ? "X" : " ")+" ";

      return ret + "]\n                      BE="
      +(!sequences_state[B] || !sequences_state[E] ? "X" : " ");
   }
}
