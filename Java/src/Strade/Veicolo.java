/**
@author Di Nardo Di Maio Raffaele
*/

package Strade;

import os.Util;

public class Veicolo extends Thread
{
   private int id;
   private Strade s;

   public Veicolo(int id, Strade s)
   {
      this.id = id;
      this.s = s;
   }

   public void run()
   {
      int strada = Util.randVal(0,2);
      int corsia = s.entra(strada);
      Util.rsleep(4000, 8000);
      s.esce(corsia);
   }
}
