package airQualityFeature;

import i18n.I18N;
import i18n.Messages;
import output.OutputEvent;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

/**
 * Responsible to check whether the current air quality are set according
 * to some values. If any of the constraints are violated, a warning message
 * is sent to the user.
 */
public class AsthmaAssistantZirk {
	
	// Atributes
    private final Bezirk bezirk;
	
    /**
     * Initializes the bezirk and receives events
     */
    public AsthmaAssistantZirk() {
    	
        BezirkMiddleware.initialize();
        this.bezirk = BezirkMiddleware.registerZirk("Asthma Assistant Zirk");
        System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
        System.err.println(I18N.getString(Messages.SYS_ASTHMA));

        final EventSet airQualityEvents = new EventSet(AirQualityUpdateEvent.class);
        
        airQualityEvents.setEventReceiver(new EventSet.EventReceiver() {
            @Override
            public void receiveEvent(Event event, ZirkEndPoint sender) {
                
            	//Check if this event is of interest
                if (event instanceof AirQualityUpdateEvent) {
                	
                    final AirQualityUpdateEvent aqUpdate = (AirQualityUpdateEvent) event;
                    System.err.println(I18N.getString(Messages.ASTHMA_PUB,aqUpdate.toString()));
                  
                    //do something in response to this event
                    if (aqUpdate.getHumidity() > 0.7) {
                    	sendOutputEvent(I18N.getString(Messages.HUMIDITY_MSG));
                    }
                    if (aqUpdate.getDustLevel() > 20) {
                    	sendOutputEvent(I18N.getString(Messages.DUST_MSG));
                      }
                    if (aqUpdate.getPollenLevel() > 500) {
                    	sendOutputEvent(I18N.getString(Messages.POLLEN_MSG));
                     }
                    if (aqUpdate.getHumidity() <= 0.7 && aqUpdate.getDustLevel() <= 20 && aqUpdate.getPollenLevel() <= 500) {
                    	sendOutputEvent(I18N.getString(Messages.AIR_QUALITY_NORMAL));
                    }
                }
            }
        });
        
        bezirk.subscribe(airQualityEvents);
    }
    
    private void sendOutputEvent(String message) {
		OutputEvent outputEvent = new OutputEvent(message);
		bezirk.sendEvent(outputEvent);
	}
}