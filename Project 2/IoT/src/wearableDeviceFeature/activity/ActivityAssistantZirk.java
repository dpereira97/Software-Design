package wearableDeviceFeature.activity;

import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;
import output.OutputEvent;

/**
 * Responsible to check the heart bit of the user and send alerts according to
 * the default values of an average adult heart bit.
 * @author fc47806, fc47878, fc47888
 */
public class ActivityAssistantZirk {
	
	// Attributes
	private final Bezirk bezirk;
	private int lowerLimit;
	private int upperLimit;
	
	public ActivityAssistantZirk() {
		
		// Initializes Bezirk
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Activity Assistant Zirk");
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		System.err.println(I18N.getString(Messages.SYS_ACTIVITY));
		
		getHeartBeatLimits();
		
		final EventSet activityEvents = new EventSet(ActivityUpdateEvent.class);
		
		activityEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event arg0, ZirkEndPoint arg1) {
				final ActivityUpdateEvent activityEvent = (ActivityUpdateEvent) arg0;
				System.err.println(I18N.getString(Messages.ACTIVITY_PUB,activityEvent.toString()));
				
				if(activityEvent.getHeartbeat() < lowerLimit) {
					sendOutputEvent(I18N.getString(Messages.LOW_HEART));
				}
				
				if(activityEvent.getHeartbeat() > upperLimit) {
					sendOutputEvent(I18N.getString(Messages.HIGH_HEART));
				}
				
				if(activityEvent.getHeartbeat() > lowerLimit && activityEvent.getHeartbeat() < upperLimit) {
					sendOutputEvent(I18N.getString(Messages.NORMAL_HEART));
				}
			}
		});
		bezirk.subscribe(activityEvents);
	}
	
	private void sendOutputEvent(String message) {
		
		OutputEvent outputEvent = new OutputEvent(message);
		bezirk.sendEvent(outputEvent);
	}
	
	@SuppressWarnings("resource")
	private void getHeartBeatLimits() {
		boolean notDone = true;
		Scanner sc = new Scanner(System.in);
		while(notDone) {
			try {
				System.out.println(I18N.getString(Messages.INFERIOR_LIMIT_ACTIVITY));
				lowerLimit = Integer.parseInt(sc.nextLine());
				System.out.println(I18N.getString(Messages.UPPER_LIMIT_ACTIVITY));
				upperLimit = Integer.parseInt(sc.nextLine());
				notDone = false;
			}catch (NumberFormatException e) {
				System.out.println(I18N.getString(Messages.ACTIVITY_NOT_SUPPORTED));
			}
		}
	}
}