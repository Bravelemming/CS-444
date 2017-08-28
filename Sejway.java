import lejos.nxt.*;

public class Sejway {

	// PID constants
	final int KP = 28;
	final int KI = 4;
	final int KD = 33;
	final int SCALE = 18;
	final int FORWARD = 1;
	final int BACKWARD = 2;

	// Global vars:
	int offset;
	int prev_error;
	float int_error;
	
	LightSensor ls;
	
	public Sejway() {
		ls = new LightSensor(SensorPort.S2, true);
	}
	
	public void getBalancePos() {
		// Wait for user to balance and press orange button
		//while (!Button.ENTER.isPressed()) {  
               Button.ENTER.waitForPress();  // these two lines fix bad button method
			   //Button.ENTER.waitForPressAndRelease();
               Sound.beep();
			// NXTway must be balanced.
			offset = ls.readNormalizedValue();
			LCD.clear();
			LCD.drawInt(offset, 2, 4);
			LCD.refresh();
		//}
	}
	
	public void pidControl() {
		while (true) {
			int normVal = ls.readNormalizedValue();

			// Proportional Error:
			int error = normVal - offset;
			// Adjust far and near light readings:
			if (error < 0) error = (int)(error * 1.8F);
			
			// Integral Error:
			int_error = ((int_error + error) * 2)/3;
			
			// Derivative Error:
			int deriv_error = error - prev_error;
			prev_error = error;
			
			int pid_val = (int)(KP * error + KI * int_error + KD * deriv_error) / SCALE;
			
			if (pid_val > 100)
				pid_val = 100;
			if (pid_val < -100)
				pid_val = -100;

			// Power derived from PID value:
			int power = Math.abs(pid_val);
			power = 55 + (power * 45) / 100; // NORMALIZE POWER

			if (pid_val > 0) {
				MotorPort.B.controlMotor(power, FORWARD);
				MotorPort.C.controlMotor(power, FORWARD);
			} else {
				MotorPort.B.controlMotor(power, BACKWARD);
				MotorPort.C.controlMotor(power, BACKWARD);
			}
		}
	}
	
	public void shutDown() {
		// Shut down light sensor, motors
		MotorPort.B.controlMotor(0, 0);
		MotorPort.C.controlMotor(0, 0);
		ls.setFloodlight(false);
	}
	
	public static void main(String[] args) {
		Sejway sej = new Sejway();
		sej.getBalancePos();
		sej.pidControl();
		sej.shutDown();
	}
}