package project.perceptron;

import java.util.Timer;
import java.util.TimerTask;

public class NinjaTimer extends Thread {
	public boolean stop = false;
	
	public void run() {
		ninjaTimer();
	}
	
	public void ninjaTimer() {
		long in1Minute = 60 * 1000;
		Timer timer = new Timer();
		timer.schedule( new TimerTask(){
			public void run() {
				if (!stop) {
					ninjaTimer();
				}
			}
		},  in1Minute );
	}
	
	
	public void setFlag(boolean flag) {
		stop = flag;
	}
	
}
