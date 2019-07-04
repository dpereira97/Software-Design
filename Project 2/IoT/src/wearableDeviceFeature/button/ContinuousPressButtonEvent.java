package wearableDeviceFeature.button;

import i18n.I18N;
import i18n.Messages;

/**
 * Event for a continuous press of the button
 * @author fc47806, fc47878, fc47888
 */
public class ContinuousPressButtonEvent extends ButtonEvent {
	
	/**
	 * Generated serial version
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return I18N.getString(Messages.LONG_BTN_TO_STRING);
	}
}