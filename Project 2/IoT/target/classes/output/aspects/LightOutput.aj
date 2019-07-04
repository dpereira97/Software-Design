package output.aspects;

import output.lights.LightFacade;
import output.screen.ScreenHandler;

public aspect LightOutput {

	pointcut daLuz(String msg, ScreenHandler screenHandler) : 
		call(void ScreenHandler.computeMessage(String)) && args(msg) && target(screenHandler);
	
	after(String msg, ScreenHandler screenHandler) : daLuz(msg, screenHandler) {
		
		LightFacade lightFacade = new LightFacade();
		lightFacade.computeMessage(msg);
	}
}
