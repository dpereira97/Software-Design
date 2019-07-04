package movementDetectorFeature;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;
import output.OutputEvent;

/**
 * Atuador de detecao de movimento
 * @author fc47806, fc47878, fc47888
 */
public class MovementAssistantZirk {
	
	// Attributes
	private final Bezirk bezirk;
	
	public MovementAssistantZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Movement Assistant Zirk");
		
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		System.err.println(I18N.getString(Messages.SYS_INFO_PROD_MOV));
		
		final EventSet movementEvents = new EventSet(NoMovementUpdateEvent.class,MovementUpdateEvent.class);
		
		movementEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event arg0, ZirkEndPoint arg1) {
				
				if(arg0 instanceof MovementUpdateEvent) {
		
					System.err.println(I18N.getString(Messages.MOVEMENT_PUB));
					MovementUpdateEvent event = (MovementUpdateEvent) arg0;
					HouseDivisions casa = HouseDivisions.values()[(int)(Math.random()*HouseDivisions.values().length)];
					
					String minutos = Integer.toString(event.getMinutes());
					
					if (event.getMinutes() < 10)
						minutos = "0" + minutos;
					
					sendOutputEvent(I18N.getString(Messages.MOVEMENT_DETECTED,
							casa.getNome(), Integer.toString(event.getHours()), minutos));
				}
				
				if (arg0 instanceof NoMovementUpdateEvent) {
					
					System.err.println(I18N.getString(Messages.NOMOVEMENT_PUB));
					NoMovementUpdateEvent event = (NoMovementUpdateEvent) arg0;
					sendOutputEvent(I18N.getString(Messages.NO_MOVEMENT_DETECTED,
							Long.toString(((event.getCurrent() - event.getLastSent()) / 1000) % 60),
							event.getLastSentToString(),event.getCurrentTime()));
				}
			}
		});
		
		bezirk.subscribe(movementEvents);
	}
	
	private void sendOutputEvent(String message) {
		OutputEvent outputEvent = new OutputEvent(message);
		bezirk.sendEvent(outputEvent);
	}
}