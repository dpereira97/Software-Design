package output.speech;

import output.screen.ScreenFacade;

/**
 * Speech Facade
 * @author fc47806, fc47878, fc47888
 */
public class SpeechFacade extends ScreenFacade {

	// Attributes
	private SpeechHandler speechHandler;
	
	public SpeechFacade() {
		
		this.speechHandler = new SpeechHandler();
	}
	
	@Override
	public void computeMessage(String message) {
		
		this.speechHandler.computeMessage(message);
	}
}
