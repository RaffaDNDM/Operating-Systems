/**
   @author Di Nardo Di Maio Raffaele 1204879   

   ------------------------------------------------------
                         Traccia 8
   ------------------------------------------------------
     Implementazione dell'incrocio con Monitor di Hoare
   ------------------------------------------------------
*/

package Crossroad;

import os.Monitor;
import os.Monitor.Condition;

public class CrossroadMon extends Crossroad
{
   //monitor
   Monitor m = new Monitor();
   //waiting queue at the arrival for cars from North
   Condition ns = m.new Condition();
   //waiting queue at the arrival for cars from East
   Condition ew = m.new Condition();


   public void enterNS()
   {
      m.mEnter();
   
      //increase number of cars from North waiting for their turn
      enqueued_NS++;
      print_state();
   
      /* 
         Waiting condition
         -> no free places in the crossroad
         -> no turn of cars from North
         -> turn of cars from North but it reached max number of consecutive NS cars 
      */
      if(capacity==0 || ((enqueued_EW>0 || in_service>0) && !isNS) || (isNS && on_dir==0))
         ns.cWait();
      
      //dequeue the car from NS waiting queue
      enqueued_NS--;
      //occupy a free place in the crossroad
      capacity--;
      //increase number of cars crossing the crossroad in this moment
      in_service++;
      
      /*
         update of number of possible consecutive NS cars for the future
         if and only if there are some waiting EW cars
      */
      on_dir = (enqueued_EW>0)? (on_dir-1): on_dir;
      print_state();
         
      m.mExit();
   }
   
   public void enterEW()
   {
      m.mEnter();
   
      //increase number of cars from East waiting for their turn
      enqueued_EW++;
      print_state();
   
      /* 
         Waiting condition
         -> no free places in the crossroad
         -> no turn of cars from East
         -> turn of cars from East but it reached max number of consecutive EW cars 
      */
      if(capacity==0 || ((enqueued_NS>0 || in_service>0) && isNS) || (!isNS && on_dir==0))
         ew.cWait();
      
      //dequeue the car from EW waiting queue
      enqueued_EW--;
      //occupy a free place in the crossroad
      capacity--;
      //increase number of cars crossing the crossroad in this moment
      in_service++;
      
      /*
         update of number of possible consecutive EW cars for the future
         if and only if there are some waiting NS cars
      */
      on_dir = (enqueued_NS>0)? (on_dir-1): on_dir;
      print_state();
         
      m.mExit();
   }
   
   public void exit()
   {
      m.mEnter();
            
      //increase the number of cars that crossed the crossroad
      served++;
      //increase the number of free places in the crossroad
      capacity++;
      //decrease the number of cars that are crossing the crossroad in this moment
      in_service--;
      print_state();
      
      if(in_service==0)
      {
         /*
            Update the turn of the cars if:
            -> the actual direction has reached the max number of consecutive cars
            -> it's the turn of the actual type of cars that crossed the crossroad
               but there are no more cars waiting on this direction      
         */
         boolean end = on_dir==0 || (on_dir>0 && ((isNS && enqueued_NS==0) || (!isNS && enqueued_EW==0)));
         isNS = end ? !isNS : isNS;
         on_dir = MAX_ON_DIR;
      
      
         if(isNS && enqueued_NS>0)
            ns.cSignal();
         else if(!isNS && enqueued_EW>0)
            ew.cSignal();
      }
      
      m.mExit();
   }
}