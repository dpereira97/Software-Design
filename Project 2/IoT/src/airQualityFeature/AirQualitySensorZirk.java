package airQualityFeature;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;

/**
 * Mock implementation
 * Sensor which checks the current air quality taking into account three factors:
 * Humidity, dust level and pollen level.
 */
public class AirQualitySensorZirk {
	
	private final Bezirk bezirk;

	public AirQualitySensorZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Air Quality Sensor Zirk");
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		
		System.err.println(I18N.getString(Messages.SYS_INFO_AIR));

		System.out.println(I18N.getString(Messages.DEVICE_RUNNING, I18N.getString(Messages.AIR_QUALITY_SENSOR)));
		
		try {
			this.sendAirQualityUpdate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendAirQualityUpdate() throws InterruptedException {
		
		// produces some values since this is a mock
		Random rand = new Random();
		double humidity = 0.8;
		int dustLevel = 30;
		int pollenLevel = 1000;
		
		AirQualityUpdateEvent airQualityUpdateEvent = new AirQualityUpdateEvent(humidity, dustLevel,
				pollenLevel);
		
		while (true) {

			// sends the event
			bezirk.sendEvent(airQualityUpdateEvent);
			System.err.println(airQualityUpdateEvent.toString());
			
			humidity = rand.nextDouble();
			dustLevel = rand.nextInt(31);
			pollenLevel = rand.nextInt(1001);
			airQualityUpdateEvent = new AirQualityUpdateEvent(humidity, dustLevel, 
					pollenLevel);
			
			TimeUnit.SECONDS.sleep(3);
		}
	}
}