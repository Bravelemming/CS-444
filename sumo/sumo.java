import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;
import lejos.nxt.SoundSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

//William Kennedy
//Jack Daniel Kinne
// Jared McGreevy
//edit 5.2.17

public class sumo {

public static void main (String[] aArg)
{

	//lightSensor instances (x)
	final LightSensor line = new LightSensor(SensorPort.S4);
	//ultrasound instance
	final UltrasonicSensor seeingStuff = new UltrasonicSensor(SensorPort.S1);
	//touch instance
	final TouchSensor touchLeft = new TouchSensor(SensorPort.S2);
	final TouchSensor touchRight = new TouchSensor(SensorPort.S3);

	//controllers
	int distance = 26;
	int surfaceColor = 30;
	// 30 is good for tabletop, stops at edge.
	// 90 is good for carpet, stops at white

	LCD.drawString("SUMOENGAGE", 0, 1);
	
	//wait for button press
	Button.ENTER.waitForPressAndRelease();
			
	int scanCounter = 1;
	
    while (true)
    {
        LCD.drawString("base case ", 0, 1);		
		
        // 1. scan; are they in front of us?
		if ( !(seeingStuff.getDistance() < distance) && scanCounter % 2 == 0 )
		{
			scanCounter++;
			
			Motor.A.setSpeed(100); //front right
			Motor.B.setSpeed(100); //front left	
			
			//we don't see anything, scan.
			while( ! (seeingStuff.getDistance() < distance) )
			{
				//scan right
				LCD.drawString("scan righ", 0, 1);
				Motor.A.forward();
				Motor.B.backward();			
			}		
		}
		else if ( !(seeingStuff.getDistance() < distance) )
		{
			scanCounter++;
			
			Motor.A.setSpeed(100); //front right
			Motor.B.setSpeed(100); //front left	
			
			//we don't see anything, scan.
			while( ! (seeingStuff.getDistance() < distance) )
			{
				//scan left
				LCD.drawString("scan left", 0, 1);
				Motor.B.forward();
				Motor.A.backward();			
			}				
		}	
		
        if (seeingStuff.getDistance() < distance)
        {
            LCD.drawString("sees robot", 0, 1);
            //while it has a target in front of it
            while ( seeingStuff.getDistance() < distance )
            {
                //it sees a robot, RAM BEHAVIOR
                Motor.A.setSpeed(9999); //front right
				Motor.B.setSpeed(9999); //front left
				Motor.C.setSpeed(9999); //rear motor 

				Motor.A.forward();
				Motor.B.forward();
				Motor.C.backward();
				
				Delay.msDelay(500);
				
            }
				//stop and exit behavior
				Motor.A.stop();
				Motor.B.stop();
				Motor.C.stop();						
			
        } // 2. line check; are we near out?
        if (line.readValue() <= surfaceColor)
        {
            LCD.drawString("line issue", 0, 1);

			//another behavior to boost, potentially turning slightly.			

        } // 3. touch check: are we attacked from the left
        if (touchLeft.isPressed() )
        {
			//can implement touch behavior here.
            LCD.drawString("touch righ", 0, 1);

        } // 3. touch check: are we attacked from the right
        if (touchRight.isPressed() )
        {
			//can implement touch behavior here.
            LCD.drawString("touch left", 0, 1);

        }

    }//end while(true)

}//end main
}//end sumo class



/*
		//initial movement and position: not needed because small track in our case.
		//this was designed to find the edge of the track, and go from a certain position.
		
		LCD.drawString(" position ", 0, 1);
		
		Motor.A.setSpeed(250); //front right
		Motor.B.setSpeed(999); //front left
		Motor.C.setSpeed(250); //rear motor

		//"backward" on c
		Motor.C.forward();
		
		//move backward on a and b, 
		Motor.A.forward();
		Motor.B.backward();
		
		//>= for a white surface
		while(line.readValue() >= surfaceColor){
			//keep going until line
		}
		
		Motor.A.stop();
		Motor.B.stop();
		Motor.C.stop();

*/

