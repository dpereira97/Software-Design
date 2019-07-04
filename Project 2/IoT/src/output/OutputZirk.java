package output;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import i18n.I18N;
import i18n.Messages;
import output.screen.ScreenFacade;

import com.bezirk.middleware.java.proxy.BezirkMiddleware;

/**
 * OutputZirk. Responsavel por realizar o output para o ecra
 * @author fc47806, fc47878, fc47888
 */
public class OutputZirk {

	// Constantes
	public static final Object LOCKER = new Object();
	
	// Atributos
	private Bezirk bezirk;
	private OutputFacade output;
	
	public OutputZirk() {
		
		// Obter o bezirk 
		BezirkMiddleware.initialize();
		this.bezirk = BezirkMiddleware.registerZirk("Output Zirk");
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		
		this.output = new ScreenFacade();
		
		// Operacao de evento que eh concretizada
		final EventSet outputEvents = new EventSet(OutputEvent.class);
		
		outputEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				
				if (event instanceof OutputEvent) {
					OutputEvent evento = (OutputEvent) event;
					String eventMessage = evento.getMessage();
					output.computeMessage(eventMessage);
				}
			}
		});
		
		bezirk.subscribe(outputEvents);
	}
}