package smsMessagingFeature;

import com.bezirk.middleware.messages.Event;

/**
 * Evento que serve para indicar que pretendemos enviar SMSs
 * @author fc47806, fc47878, fc47888
 */
public class SMSMessageEvent extends Event {

	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = 5245261718312331890L;

	// Atributos
	private String message;
	
	public SMSMessageEvent(String message) {
		
		this.message = message;
	}
	
	public String getMessage() {
		
		return this.message;
	}
}
