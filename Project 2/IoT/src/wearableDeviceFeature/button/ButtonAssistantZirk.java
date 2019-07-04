package wearableDeviceFeature.button;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import i18n.I18N;
import i18n.Messages;
import output.OutputEvent;
import smsMessagingFeature.SMSMessageEvent;

/**
 * Assistant which receives a message of help and sends SMS messages to family
 * and sends a help message
 * @author fc47806, fc47878, fc47888
 */
public class ButtonAssistantZirk {

	// Attributes
	private final Bezirk bezirk;
	
	/**
	 * Contructor for the button actuator 
	 */
	public ButtonAssistantZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Button wearable Zirk");
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		System.err.println(I18N.getString(Messages.SYS_BTN));

		final EventSet buttonContinuousEvents = new EventSet(ContinuousPressButtonEvent.class, 
															  MultipleClickButtonEvent.class);

		buttonContinuousEvents.setEventReceiver(new EventSet.EventReceiver() {

			@Override
			public void receiveEvent(Event arg0, ZirkEndPoint arg1) {
				
				if(arg0 instanceof ContinuousPressButtonEvent) {
					
					final ButtonEvent buttonUpdate = (ContinuousPressButtonEvent) arg0;
					System.err.println(I18N.getString(Messages.LONG_BTN_PUB, buttonUpdate.toString()));
					sendOutputEvent(I18N.getString(Messages.CRY_FOR_HELP));
				}
				
				if(arg0 instanceof MultipleClickButtonEvent) {
					
					final ButtonEvent buttonUpdate = (MultipleClickButtonEvent) arg0;
					System.err.println(I18N.getString(Messages.MULTIPLE_BTN_PUB, buttonUpdate.toString()));
					sendOutputEvent(I18N.getString(Messages.CRY_FOR_HELP));
				}
			}
		});

		bezirk.subscribe(buttonContinuousEvents);
	}
	
	/**
	 * Private message which sends a message to the output assistant
	 * @param message message to be sent
	 * @requires message != null
	 */
	private void sendOutputEvent(String message) {
		
		SMSMessageEvent msgEvent = new SMSMessageEvent(message);
		bezirk.sendEvent(msgEvent);
		
		OutputEvent outputEvent = new OutputEvent(message);
		bezirk.sendEvent(outputEvent);
	}
}