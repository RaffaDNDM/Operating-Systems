/**
   @author Di Nardo Di Maio Raffaele 1204879
*/

package PoolAllocator;

public class NotAllowedException extends RuntimeException
{
   public NotAllowedException(String err)
   {
      System.out.println("NotAllowedException: "+err);
   }
}