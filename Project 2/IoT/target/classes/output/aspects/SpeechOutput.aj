package output.aspects;

import output.screen.ScreenHandler;
import output.speech.SpeechFacade;

public aspect SpeechOutput {

	pointcut daSom(String msg, ScreenHandler screenHandler) : 
		call(void ScreenHandler.computeMessage(String)) && args(msg) && target(screenHandler);
	
	after(String msg, ScreenHandler screenHandler) : daSom(msg, screenHandler) {
		
		SpeechFacade speechFacade = new SpeechFacade();
		speechFacade.computeMessage(msg);
	}
}
