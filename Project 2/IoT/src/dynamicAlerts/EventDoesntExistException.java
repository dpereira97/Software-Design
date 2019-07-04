package dynamicAlerts;

/**
 * Event used for User Interface
 * @author fc47806, fc47878, fc47888
 */
public class EventDoesntExistException extends Exception {
	
	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public EventDoesntExistException(String string) {
		
		this.message = string;
	}

	public String getMessage() {
		
		return this.message;
	}
}
