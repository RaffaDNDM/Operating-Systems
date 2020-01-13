package Roundabout;

public abstract class Roundabout
{
   public static final int SECTORS = 4;
   protected static final int NORTH = 0;
   protected static final int EAST = 1;
   protected static final int SOUTH = 2;
   protected static final int WEST = 3;
   protected int[] enqueued_cars;
   protected int served_cars = 0;
   
   public static final String LINE = "-----------------------------------------------";
   protected boolean occupied[];
   
   public Roundabout()
   {
      occupied = new boolean[SECTORS];
      enqueued_cars = new int[SECTORS];
            
      for(int i=0; i<SECTORS; i++)
      {
         occupied[i] = false;
         enqueued_cars[i] = 0;
      }
   }
   
   protected int succ(int sector)
   {
      return (sector+1)%SECTORS;
   }
   
   protected int prev(int sector)
   {
      return (sector-1+SECTORS)%SECTORS;
   }
   
   public abstract void enter(int sector);
   
   public abstract int next(int sector);
   
   public abstract void exit(int sector);

   public void printState()
   {
      System.out.println("-----------------------------------------------");
      System.out.printf("                 served : %2d \n", served_cars);
      System.out.println("-----------------------------------------------");
      System.out.printf("                     Enqueued \n");
      System.out.printf("                     NORTH: %2d  \n", enqueued_cars[NORTH]);
      System.out.printf("   WEST: %2d                         EAST: %2d\n", enqueued_cars[WEST], enqueued_cars[EAST]);
      System.out.printf("                     SOUTH: %2d  \n", enqueued_cars[SOUTH]);
      System.out.println("-----------------------------------------------");
      System.out.println("-----------------------------------------------");
      System.out.printf("                     Availability \n");
      System.out.printf("                     NORTH: %s  \n", occupied[NORTH]?"occupied":"free");
      System.out.printf("   WEST: %s                         EAST: %s\n", occupied[WEST]?"occupied":"free", occupied[EAST]?"occupied":"free");
      System.out.printf("                     SOUTH: %s  \n", occupied[SOUTH]?"occupied":"free");
      System.out.println("-----------------------------------------------");
   }
}