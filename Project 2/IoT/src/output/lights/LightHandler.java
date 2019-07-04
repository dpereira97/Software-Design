package output.lights;

import i18n.I18N;
import i18n.Messages;

public class LightHandler {

	/**
	 * It should make lights turn on and off
	 * @param message
	 */
	public void computeMessage(String message) {
		
		System.out.println(I18N.getString(Messages.LIGHT_BLINKING));
	}
}