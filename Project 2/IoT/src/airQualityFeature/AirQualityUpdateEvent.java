package airQualityFeature;

import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;

/**
 * Event which carries the values of the current air quality
 */
public class AirQualityUpdateEvent extends Event {
	
    /**
	 * Generated Serial Version value
	 */
	private static final long serialVersionUID = 1L;
	
	private final double
            humidity /* decimal, e.g., 0.5 */,
            dustLevel /* milligrams per cubic meter. Above 20 is high. */,
            pollenLevel /* grams per cubic meter. Above 500 is high. */;

    public AirQualityUpdateEvent(double humidity, double dustLevel, double pollenLevel) {
        this.humidity = humidity;
        this.dustLevel = dustLevel;
        this.pollenLevel = pollenLevel;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getDustLevel() {
        return dustLevel;
    }

    public double getPollenLevel() {
        return pollenLevel;
    }

    public String toString() {
        return I18N.getString(Messages.AIR_QUALITY_TOSTRING, Double.toString(humidity), 
        		Double.toString(dustLevel), Double.toString(pollenLevel));
    }
}
