package output;

import com.bezirk.middleware.messages.Event;

/**
 * OutputEvent sent by other zirks and received by OutputZirk
 * @author fc47806, fc47878, fc47888
 */
public class OutputEvent extends Event {

	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = -140319758037610562L;

	// Attributes
	private String message;
	
	public OutputEvent(String message) {
		
		this.message = message;
	}
	
	public String getMessage() {
		
		return this.message;
	}
}
