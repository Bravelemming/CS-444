/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package first;

/**
 *
 * @author Jack
 */
public class first {

    /**
     * @param args the command line arguments
     */
    task main(String[] args) {
        // makes a buzzy sound
        //Sound.buzz();
        //Thread.sleep(2000);
        
        //motor a, 75% power
        OnFwd(OUT_A, 75);
        //motor c, 75% power
        OnFwd(OUT_C, 75);
        //4 second wait
        Wait(4000);
        //switch motors A and C off.
        Off(OUT_AC);
        
        
        
        
        
    }
    
}
