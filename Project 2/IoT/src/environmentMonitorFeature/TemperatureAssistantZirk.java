package environmentMonitorFeature;

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
 * Atuador de temperatura do ambiente
 * @author fc47806, fc47878, fc47888
 */
public class TemperatureAssistantZirk {
	
	final Bezirk bezirk;
	private double inferiorLimit;
	private double superiorLimit;
	
	public TemperatureAssistantZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Temperature Assistant Zirk");
		
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		System.err.println(I18N.getString(Messages.SYS_INFO_TEMPERATURE));
		
		// Obtencao dos limites
		getTemperatureLimits();
		
		final EventSet temperatureEvents = new EventSet(TemperatureUpdateEvent.class);
		
		temperatureEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event arg0, ZirkEndPoint sender) {
				
				final TemperatureUpdateEvent temperatureUpdate = (TemperatureUpdateEvent) arg0;
				
				System.err.println(I18N.getString(Messages.TEMPERATURE_PUB, temperatureUpdate.toString()));
			
				if(temperatureUpdate.getTemperature() >= superiorLimit) {
					sendOutputEvent(I18N.getString(Messages.HIGH_TEMPERATURE));
				}
				if(temperatureUpdate.getTemperature() <= inferiorLimit) {
					sendOutputEvent(I18N.getString(Messages.LOW_TEMPERATURE));
				}
				if(temperatureUpdate.getTemperature() > inferiorLimit && temperatureUpdate.getTemperature() < superiorLimit) {
					sendOutputEvent(I18N.getString(Messages.NORMAL_TEMPERATURE));
				}
			}
		});
		
		bezirk.subscribe(temperatureEvents);
	}
	
	@SuppressWarnings("resource")
	private void getTemperatureLimits() {
		
		boolean notDone = true;
		Scanner sc = new Scanner(System.in);
		
		while(notDone) {
			try {
				System.out.println(I18N.getString(Messages.INFERIOR_LIMIT_TEMPERATURE)); 
				inferiorLimit = Double.parseDouble(sc.nextLine());
				System.out.println(I18N.getString(Messages.SUPERIOR_LIMIT_TEMPERATURE)); 
				superiorLimit = Double.parseDouble(sc.nextLine());
				notDone = false;
			} catch (NumberFormatException e) {
				System.out.println(I18N.getString(Messages.WRONG_TEMPERATURE_VALUE)); 
			}
		}
	}

	private void sendOutputEvent(String message) {
		OutputEvent outputEvent = new OutputEvent(message);
		bezirk.sendEvent(outputEvent);
	}
}