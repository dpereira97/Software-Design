package wearableDeviceFeature.button;

import java.util.Calendar;
import java.util.Date;

import com.bezirk.middleware.messages.Event;

import i18n.I18N;
import i18n.Messages;


/**
 * Event for a button press
 * @author fc47806, fc47878, fc47888
 */
public class ButtonEvent extends Event {
	
	/**
	 * Generated serial version
	 */
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private Date startDate;
	private Date endDate;
	
	/**
	 * Contructor for button event
	 */
	public ButtonEvent() {
		
		this.startDate = Calendar.getInstance().getTime();
		this.endDate= Calendar.getInstance().getTime();
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	@Override
	public String toString() {
		return I18N.getString(Messages.BTN_TO_STRING);
	}
}