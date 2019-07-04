package wearableDeviceFeature.button;

import i18n.I18N;
import i18n.Messages;

/**
 * Event for a multiple click of the button
 * @author fc47806, fc47878, fc47888
 */
public class MultipleClickButtonEvent extends ButtonEvent {

	private static final long serialVersionUID = 1L;
	
	private final int times;
	
	public MultipleClickButtonEvent(int times) {
		
		this.times = times;
	}
	
	public int getTimes() {
		return times;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + I18N.getString(Messages.MULTIPLE_BTN_TO_STRING, Integer.toString(times));
	}
}