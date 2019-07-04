package environmentMonitorFeature;

import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;
/**
 * Evento de atualização de temperatura
 * @author fc47806, fc47878, fc47888
 */
public class TemperatureUpdateEvent extends Event {
	
	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private final double temperature; // Decimal and celsius, e.g. 25.7 degrees 
	
	public TemperatureUpdateEvent(double temperature) {
		this.temperature = temperature;
	}
	
	public double getTemperature() {
		return this.temperature;
	}
	
	@Override
	public String toString() {
		return I18N.getString(Messages.TEMPERATURE_TO_STRING, Double.toString(this.temperature));
	}
}