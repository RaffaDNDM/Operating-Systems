package osTest;

import os.Sys;
import os.Buffer;
import os.SyncMultiBuf;
 
/**{c}
 * test produttore/i-consumatore/i
 * su buffer multiplo
 * @author M.Moro DEI UNIPD
 * @version 1.00 2002-04-17
 * @version 2.00 2003-10-02 package OsTest 
 * @version 2.01 2005-10-07 package os e osTest
 */

public class TestPCMultiBuf extends TestPC
{
    /**[m][s]
     * main di collaudo
     * @param args non usato
     */
    public static void main(String[] args) 
    {
    	int numEl = Sys.in.readInt("P/C su buffer multiplo; battere numero elementi buffer:");
        Buffer buf = new SyncMultiBuf(numEl);
        test(buf);
    } //[m][s] main
    
} //{c} TestPCMultiBuf

