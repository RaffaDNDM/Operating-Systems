package Airport;

public abstract class Airport
{
   public final static String LINE = "######################################################";
   public final static int AEREI=30;
   public final static int PLACES_A=2;
   public final static int PLACES_B=2;
   protected int wait_pista=0;
   protected int wait_decolli=0;
   protected int wait_atterraggi=0;
   protected int satisfiedD=0;
   protected int satisfiedA=0;
   protected int freeA=PLACES_A;
   protected int freeB=PLACES_B;
   

   public abstract void richAccessoPista(int num);
   public abstract void richAutorizDecollo(int num);
   public abstract void inVolo(int num);
   public abstract void richAutorizAtterraggio(int num);
   public abstract void freniAttivati(int num);
   public abstract void inParcheggio(int num);
   
   
   public void stampaSituazioneAeroporto()
   {
      System.out.println(LINE);
      System.out.println("Decolli in coda = "+wait_pista);
      System.out.println("Atterraggi in coda = "+wait_atterraggi);
      System.out.println("Posti liberi in A = " + freeA);   
      System.out.println("Posti liberi in B = " + freeB); 
      System.out.println("Decolli effettuati = " + satisfiedD);   
      System.out.println("Atterraggi effettuati = " + satisfiedA);  
   }
}