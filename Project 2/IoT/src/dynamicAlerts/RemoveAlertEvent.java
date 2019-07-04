package dynamicAlerts;

import com.bezirk.middleware.messages.Event;

/**
 * Used to remove a custom created alert from the user
 * @author fc47806, fc47878, fc47888
 */
public class RemoveAlertEvent extends Event {

	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = -7231456605965828277L;
	
	// Atributos
	private int addAlertId;
	
	public RemoveAlertEvent(int alertId) {
		
		this.addAlertId = alertId;
	}

	public int getAddAlertId() {
		return addAlertId;
	}

	public void setAddAlertId(int addAlertId) {
		this.addAlertId = addAlertId;
	}
}
