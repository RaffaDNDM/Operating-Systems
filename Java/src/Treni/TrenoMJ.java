/**
@author Di Nardo Di Maio Raffaele
*/

package Treni;

import os.*;

public class TrenoMJ extends Thread
{
    private Azione ctrl;
    private int treno, tratta;

    public TrenoMJ(Azione c, int treno, int tratta)
    {
        ctrl = c;
        this.treno = treno;
        this.tratta = tratta;
    } //[c]

    public void run()
    {
        System.out.println("*1* Treno "+treno+
          " in tratta "+ctrl.tr2TR[tratta]);
        System.out.println(ctrl);
//        while(true)
        for(int i=1; i<=30; i++) // 5 giri
        {
            Util.rsleep(500, 4000);
            System.out.println("*3* T="+treno+
              " in "+ctrl.tr2TR[tratta]+
              " tenta di entrare");
            tratta = ctrl.entra(tratta);
            System.out.println("*4* T="+treno+
              " entrato in tratta "+ctrl.tr2TR[tratta]);
        } // while(true)
    } //[m] run

    public static void main(String args[])
    {
        Azione ct = new Azione();
        TrenoMJ t0 = new TrenoMJ(ct, 0, Azione.A);
        TrenoMJ t1 = new TrenoMJ(ct, 1, Azione.C);

        t0.start();
        t1.start();
    } //[m][s] main

} //{c} TrenoMJ
