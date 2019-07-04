package wearableDeviceFeature.activity;

import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;

/**
 * Alert which carries the heartbit of the user
 * @author fc47806, fc47878, fc47888
 */
public class ActivityUpdateEvent extends Event{

	/**
	 * Generated Serial Version value
	 */
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private final int heartbeat;
	
	public ActivityUpdateEvent(int heartbeat) {
		this.heartbeat = heartbeat;
	}

	public int getHeartbeat() {
		return heartbeat;
	}
	
	/**
	 * Textual representation of the event
	 */
	@Override
	public String toString() {
		return I18N.getString(Messages.ACTIVITY_TO_STRING,Integer.toString(this.heartbeat));
	}
}