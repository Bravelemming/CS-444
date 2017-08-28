import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;
import lejos.util.Timer;

//Dwight Hall
//Jack Daniel Kinne
// Kevin Josey


public class tri {

   public static void delay(int time)
   {
      try {Thread.sleep(time);} catch (Exception e) {}
   }


	public static void main (String[] aArg)
	throws Exception
	{
		// Change last parameter of Pilot to specify on which
		// direction you want to be "forward" for your vehicle.
		// The wheel and axle dimension parameters should be
		// set for your robot, but are not critical.

// Note: tachopilot instantiation removed--does this now need a motor declaration??

		final LightSensor light = new LightSensor(SensorPort.S1);
        /**
         * this behavior wants to take control when the light sensor sees the line
         */
		Behavior DriveForward = new Behavior()
		{

			private boolean suppressed = false;

			public boolean takeControl()
			{return true;}//light.readValue() >= 20

			public void suppress() {
				suppressed = true;

			}
			public void action() {

                while (true){
                    //rotate back to position
                    //Motor.A.rotateTo( 60);
                    //Motor.B.rotateTo( 60);
                    Motor.C.rotateTo( 45);
                    //rotation for movement
                    Motor.C.rotate(45,true);
                    //Motor.A.rotate(10,true);
                    //Motor.B.rotate(10,true);

                    delay(100);

                    //45 and 45 is great
                }
			}
		};

		Behavior DriveBack = new Behavior()
		{
			private boolean suppressed = false;

			public boolean takeControl()
			{return light.readValue() > 40;}

			public void suppress() {
				suppressed = true;
			}

			public void action() {

				Motor.A.setSpeed(700);
				Motor.C.setSpeed(444);

			}
		};
		Behavior[] bArray = {DriveForward };
      	LCD.drawString("tripod ", 0, 1);

       	Button.ENTER.waitForPressAndRelease();
        Motor.C.setSpeed(1300);

        Motor.A.rotate(60,true);
        Motor.B.rotate(60,true);
        Motor.C.rotate(45,true);

        delay(350);


	  	(new Arbitrator(bArray)).start();


	// 300 - 450 nice, circuit complete. 1.5

	// 487 - 325, better!

	// 600 - 400, even better!

	// 700 - 467, turns were too wide, but time was great at 12 secs

	// 700  - 444 best time at 11 seconds, slightly frenetic on the turns.


	}
}


