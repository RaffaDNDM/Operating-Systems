package Fontebella;

import os.Region;
import os.RegionCondition;
import os.Util;

public class FontebellaRegion extends Fontebella
{   
   private Region r = new Region(0);

   public int entraCodaA()
   {
      int pos;
   
      r.enterWhen();
         clientiA++;
      r.leave();

      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return zampilliLiberi>0 && (clientiB==0 || stat==0);
         }
      });
      
         zampilliLiberi--;
         clientiA--;
         stat=2;
         ultimoZampillo = (ultimoZampillo + 1)%8;
         pos=ultimoZampillo;
      
      r.leave();
      return pos;
   }
   
   public int entraCodaB()
   {
      int pos;
   
      r.enterWhen();
         clientiB++;
      r.leave();

      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return zampilliLiberi>0 && (clientiA==0 || stat!=0);
         }
      });
      
         zampilliLiberi--;
         clientiB--;
         
         if(clientiA>0)
            stat--;
            
         ultimoZampillo = (ultimoZampillo + 1)%8;
         pos=ultimoZampillo;
      
      r.leave();
      return pos;
   }
   
   public void fineRiempimento()
   {
      r.enterWhen();
         zampilliLiberi++;
         serviti++;
         stampaSituazione();
      r.leave();
   }
   
   public static void main(String[] args)
   {
      Fontebella f = new FontebellaRegion();
      
      for(int i=0; i<CLIENTI; i++)
      {
         int choice = Util.randVal(1, 10);
         
         if(choice<4)
            new ClienteB(i, f).start();               
         else
            new ClienteA(i, f).start();            
      
         Util.rsleep(Long.parseLong(args[0]), Long.parseLong(args[1]));
      }
   }
}