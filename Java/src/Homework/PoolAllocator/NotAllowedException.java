/**
   @author Di Nardo Di Maio Raffaele
*/

package PoolAllocator;

public class NotAllowedException extends RuntimeException
{
   public NotAllowedException(String err)
   {
      System.out.println("NotAllowedException: "+err);
   }
}
