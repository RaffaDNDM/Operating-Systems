package GestioneCarrelli;

public abstract class Smista2
{
   private final static long I1ATT = 1000L;
   private final static long I2ATT = 2000L;
   private final static long BMIN = 2000L;
   private final static long BMAX = 4000L;
   private final static long CMIN = 1000L;
   private final static long CMAX = 4000L;

   //true se il convogliatore non è occupato
   protected boolean I1 = true;
   protected boolean I2 = true;
   
   //stato degli elevatori (true se libero)
   protected boolean[] B = new boolean[2];

   //stato delle tratte d'uscita (true se libero)
   protected boolean[] C = new boolean[3];
   
   //ticket di ciascuna coda in ingresso
   protected int[] ticket = new int[3];
   
   //ticket da servire in ciascuna coda in ingresso
   protected int[] serv = new int[3];
   
   public abstract int instrada(final int i);
   public abstract void outI1(int h);
   public abstract int outB(int h);
   public abstract void inC(int h);
   public abstract void outC(int h);

   public Smista()
   {
      for(int i=0; i<3; i++)
      {
         ticket[i]=0;
         serv[i]=0;
         C[i]=true;
      }
      
      B[0]=B[1]=true;      
   }
}