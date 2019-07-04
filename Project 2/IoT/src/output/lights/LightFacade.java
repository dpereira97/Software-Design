package output.lights;

import output.screen.ScreenFacade;

public class LightFacade extends ScreenFacade {

	private LightHandler lightHandler;
	
	public LightFacade() {
		
		this.lightHandler = new LightHandler();
	}
	
	public void computeMessage(String message) {
	
		this.lightHandler.computeMessage(message);
	}
}
