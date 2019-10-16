package GestioneCarrelli;

public abstract class Smista
{
   protected final static int CARRELLI = 30;

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
   
   //Situazione del complesso
   protected int freeB=2;
   protected int freeC=3;
   protected int[] served = new int[3];
   protected int[] enqueued = new int[3];
   private final static String BOLD_LINE = "#######################################################";
   private final static String LINE = "-------------------------------------------------------";   
   
   public Smista()
   {
      for(int i=0; i<3; i++)
      {
         ticket[i]=0;
         serv[i]=0;
         C[i]=true;
         served[i]=0;
      }
      
      B[0]=B[1]=true;      
   }
   
   public void stampaSituazione()
   {
      System.out.println(BOLD_LINE);
      System.out.println("IN CODA");
      System.out.println("C0: "+served[0]+"    C1: "+served[1]+"    C2: "+served[2]);
      System.out.println("A0: "+enqueued[0]+"    A1: "+enqueued[1]+"    A2: "+enqueued[2]);
      System.out.println(LINE);
      System.out.println("Elevatori liberi = "+freeB);
      System.out.println("Tratte di uscita libere = "+freeC);
   }
}