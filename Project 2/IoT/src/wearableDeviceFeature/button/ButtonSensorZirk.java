package wearableDeviceFeature.button;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;

/**
 * Responsible for the detection of the button presses
 * @author fc47806, fc47878, fc47888
 */
public class ButtonSensorZirk {
	
	private boolean clicked;
	private Bezirk bezirk;
	
	/**
	 * Constructor for the button sensor
	 */
	public ButtonSensorZirk() {
		
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Button Sensor Zirk");
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		
		try {
			this.startOperation();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void click() {
		
		this.clicked = true;
		ButtonEvent event = new ButtonEvent();
		bezirk.sendEvent(event);
		this.clicked = false;
	}
	
	public void longClick() {
		
		this.clicked = true;
		ContinuousPressButtonEvent event = new ContinuousPressButtonEvent();
		bezirk.sendEvent(event);
		this.clicked = false;
	}
	
	public void clickMultipleTimes(int times) {
		
		MultipleClickButtonEvent event = new MultipleClickButtonEvent(times);
		bezirk.sendEvent(event);
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void startOperation() throws InterruptedException {
		
		System.err.println(I18N.getString(Messages.SYS_INFO_BTN));
		System.out.println(I18N.getString(Messages.DEVICE_RUNNING, I18N.getString(Messages.BUTTON_SENSOR)));
		
		Random r = new Random();
		boolean choice = r.nextBoolean();
		
		
		while(true) {
			if(choice)
				this.clickMultipleTimes(3);
			else
				this.longClick();
			
			choice = r.nextBoolean();
			TimeUnit.SECONDS.sleep(6);
		}
	}
}