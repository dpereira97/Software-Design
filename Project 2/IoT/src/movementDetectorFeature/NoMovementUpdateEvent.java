package movementDetectorFeature;

import com.bezirk.middleware.messages.Event;

/**
 * Evento de Inatividade
 * @author fc47806, fc47878, fc47888
 */
public class NoMovementUpdateEvent extends Event {

	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private long lastSent;
	private long current;
	
	/**
	 * Constructor which initializes the last sent movement update event and
	 * the current time which the no movement event has been sent
	 */
	public NoMovementUpdateEvent(long lastSent) {
		
		this.lastSent = lastSent;
		this.current = System.currentTimeMillis();
	}
	
	private int getHours(long val) {
		return (int) ((val / (1000 * 60 * 60)) % 24);
	}
	
	private int getMinutes(long val) {
		return (int) ((val / (1000 * 60)) % 60);
	}
	
	private int getSeconds(long val) {
		return (int) (val / 1000) % 60;
	}
	
	public long getLastSent() {
		return lastSent;
	}

	public long getCurrent() {
		return current;
	}
	
	public String getLastSentToString() {
		return getHours(lastSent) + ":" + getMinutes(lastSent) + ":" + getSeconds(lastSent);
	}
	
	public String getCurrentTime() {
		return getHours(current) + ":" + getMinutes(current) + ":" + getSeconds(current);
	}
}