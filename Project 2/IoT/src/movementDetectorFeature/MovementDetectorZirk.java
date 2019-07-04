package movementDetectorFeature;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;

/**
 * Sensor de Detecao de Movimento
 * @author fc47806, fc47878, fc47888
 */
public class MovementDetectorZirk {
	
	// Attributes
	private Bezirk bezirk;
	
	public MovementDetectorZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Movement Detector Zirk");
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		
		try {
			runMovementDetector();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMovementDetectedUpdate() {
		MovementUpdateEvent movementUpdate = new MovementUpdateEvent();
		bezirk.sendEvent(movementUpdate);
	}
	
	private void sendNoMovementDetectedUpdate(long last) {
		NoMovementUpdateEvent noMovement = new NoMovementUpdateEvent(last);
		bezirk.sendEvent(noMovement);
	}
	
	public void runMovementDetector() throws InterruptedException {
		
		Random rand = new Random();
		long sendMovement = System.currentTimeMillis();
		System.err.println(I18N.getString(Messages.SYS_INFO_SENS));
		
		System.out.println(I18N.getString(Messages.DEVICE_RUNNING, I18N.getString(Messages.MOVEMENT_SENSOR)));
		
		try {
			new ImplementedWebcamDetection(this.bezirk);
		} catch(Exception e) {
			
			System.err.println();
			
			while(true) {
				if (rand.nextDouble() > 0.70) {
					this.sendNoMovementDetectedUpdate(sendMovement);
					this.sendMovementDetectedUpdate();
					sendMovement = System.currentTimeMillis();
				}
				
				TimeUnit.SECONDS.sleep(3);
			}
		}
	}
}
