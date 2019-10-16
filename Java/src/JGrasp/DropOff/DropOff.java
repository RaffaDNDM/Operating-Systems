package DropOff;

import os.Util;

public abstract class DropOff
{
   protected final static int NUM_BAG = 20;

   protected int punLib=2; /* numero di banchi di consegna liberi */
   protected boolean[] pLib = new boolean[2]; /* stato dei banchi di consegna */
   protected boolean libCD = true; /*true se CD non occupata*/
   protected int wprio=0; /*numero di clienti in attesa nella coda di priorità*/
   protected int wnorm=0; /*numero di clienti in attesa nella coda normale*/
   protected int tot_num=0; /*numero totale di persone sevite*/
   
   public abstract int inCoda(boolean prio);
   public abstract void regok(int pos);
   public abstract void term(int pos);  
   
   public void stampaSituazione()
   {
      System.out.printf("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\///////////////////////////\n");
      System.out.printf("||         Il numero di clienti serviti: %2d         ||\n",tot_num);
      System.out.printf("||      Il numero di clienti in coda (prio): %2d     ||\n",wprio);
      System.out.printf("||      Il numero di clienti in coda (norm): %2d     ||\n",wnorm);
      System.out.printf("///////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
   } 
}