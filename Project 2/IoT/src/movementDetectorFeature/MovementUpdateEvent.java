package movementDetectorFeature;

import java.util.Calendar;

import com.bezirk.middleware.messages.Event;

/**
 * Evento de Movimento
 * @author fc47806, fc47878, fc47888
 */
public class MovementUpdateEvent extends Event {
	
	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = 1L;
	private Calendar c;
	
	public MovementUpdateEvent() {
		c = Calendar.getInstance();
	}
	
	public int getHours() {
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getMinutes() {
		return c.get(Calendar.MINUTE);
	}
}