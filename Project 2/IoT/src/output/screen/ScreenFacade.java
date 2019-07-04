package output.screen;

import output.OutputFacade;

public class ScreenFacade implements OutputFacade {

	private ScreenHandler screenHandler;
	
	public ScreenFacade() {
		
		this.screenHandler = new ScreenHandler();
	}
	
	public void computeMessage(String message) {
		this.screenHandler.computeMessage(message);
	}
}
