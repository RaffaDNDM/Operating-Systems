package os;

import java.io.IOException;

/**{c}
 * Device handler per ADC
 *
 * @author M.Moro DEI UNIPD
 * @version 1.00 2004-11-12
 * @version 2.00 2005-10-07 package os
 * @version 2.01 2005-11-18 alcuni elementi pubblici
 * @version 2.02 2012-05-09 terminazione aggiunta
 */

class ADCDH extends DeviceHandler
{
    /**[c]
     * @param dd  descrittore del dispositivo
     */
    ADCDH(DeviceDesc dd)
    { dDesc=dd; }
    
    /**[m]
     * corpo del thread
     */
    public void run()
    {
/* 2012-05-09: M.Moro terminazione aggiunta        
            while(true)
*/            
        while(!toBeStopped)
        {
            dDesc.ar.p();
            if (toBeStopped)
                break;
            dDesc.prot.p();
            IORB iorb = (IORB)dDesc.iorbQueue.remove(0);
              // rimuove primo IORB
            dDesc.prot.v();
            this.data=iorb.data;
            toBeMoved = iorb.qty;
            pos=0;
            // abilita dispositivo
            dDesc.dev.enable();
            dDesc.co.p();
            if (errcode != DeviceDesc.NOERR)
                iorb.err.errcode=errcode;
                iorb.err.errDesc=
                  "Error ADC cod="+errcode;
            iorb.sr.v();  // richiesta servita
        }
        System.out.println("ADCDH terminato!");
    } //[m] run
    
    /**[m]
     * routine di servizio -
     * gestisce l'intero trasferimento del buffer
     * della quantita' di dati richiesta
     */
    public void isr()
    {
        // verifica errore
        if((errcode = dDesc.dev.error()) != DeviceDesc.NOERR)
        {
            // condizione d'errore: operazione interrotta
            toBeMoved=0;
            dDesc.dev.disable();
            dDesc.co.v();
            
        }
        else if (toBeMoved>0)
        {
            // ancora campioni da acquisire
            byte[] d = new byte[4];
            ((InputDevice)dDesc.dev).read(d);
            // campione da 16 bit
            data[pos++] = d[0];
            data[pos++] = d[1];
            toBeMoved-=2;
        }
        else
        {
            // terminato, spegne e segnala al DH
            dDesc.dev.disable();
            dDesc.co.v();
        }
    }  //[m] isr

} //{c} ADCDH
