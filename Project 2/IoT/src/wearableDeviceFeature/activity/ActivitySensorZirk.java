package wearableDeviceFeature.activity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;

/**
 * Responsible to detect the user heartbit
 * @author fc47806, fc47878, fc47888
 */
public class ActivitySensorZirk {
	
	// Attributes
	private final Bezirk bezirk;
	
	public ActivitySensorZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Activity Sensor Zirk");
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		System.err.println(I18N.getString(Messages.SYS_INFO_ACTIVITY));
		System.out.println(I18N.getString(Messages.DEVICE_RUNNING, I18N.getString(Messages.ACTIVITY_SENSOR)));
		this.sendActivityUpdate();
	}
	
	public void sendActivityUpdate() {
		
		Random rand = new Random();
		int heartBeat = 68;
		ActivityUpdateEvent activityEvent = new ActivityUpdateEvent(heartBeat);
		
		while(true) {
			bezirk.sendEvent(activityEvent);
			System.err.println(I18N.getString(Messages.ACTIVITY_PUBLISHED,activityEvent.toString()));
			heartBeat = 50 + rand.nextInt(180 - 50);
			activityEvent = new ActivityUpdateEvent(heartBeat);
			
			try {
				TimeUnit.SECONDS.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}