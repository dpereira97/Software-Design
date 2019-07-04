package environmentMonitorFeature;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;

/**
 * Sensor de temperatura do Ambiente
 * @author fc47806, fc47878, fc47888
 */
public class TemperatureSensorZirk {

	// Atributes
	private Bezirk bezirk;

	public TemperatureSensorZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Temperature Sensor Zirk");
		
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		System.err.println(I18N.getString(Messages.TEMPERATURE_SENSOR_INFO));
		System.out.println(I18N.getString(Messages.DEVICE_RUNNING, I18N.getString(Messages.TEMPERATURE_SENSOR)));
		
		try {
			this.sendTemperatureUpdate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendTemperatureUpdate() throws InterruptedException {
		
		// Produces random values because this is a mock
		Random rand = new Random();
		double temperature = 38;
		TemperatureUpdateEvent temperatureUpdateEvent = new TemperatureUpdateEvent(temperature);
		
		while (true) {
			bezirk.sendEvent(temperatureUpdateEvent);
			System.err.println(I18N.getString(Messages.TEMPERATURE_PUB_STRING, temperatureUpdateEvent.toString()));
			
			temperature = rand.nextInt(31)+10;
			temperatureUpdateEvent = new TemperatureUpdateEvent(temperature);
			
			TimeUnit.SECONDS.sleep(3);
		}
	}
}