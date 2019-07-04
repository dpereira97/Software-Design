package dynamicAlerts;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;
import output.OutputEvent;
import smsMessagingFeature.SMSMessageEvent;

/**
 * Adds or removes custom made alerts. After adding a custom made alert, executes it
 * @author fc47806, fc47878, fc47888
 */
public class AlertAssistantZirk {

	// Constants
	private static final long MINUTE_TO_MILLISECONDS = 60000;

	private static final String DATABASE_ALERTS = "dbAlerts";
	
	// Attributes
	private final Bezirk bezirk;
	private List<Pair<AddAlertEvent, InnerAlertAssistant>> addAlerts;
	
	public AlertAssistantZirk() {
		
		// Initialize Bezirk
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Alert Assistant Zirk");
		
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
		System.err.println(I18N.getString(Messages.SYS_INFO_ALERT));
		
		this.addAlerts = new CopyOnWriteArrayList<>();
		
		if ((new File(DATABASE_ALERTS)).exists())
			this.retrieveFromDatabase();
		
		final EventSet alertEvents = new EventSet(AddAlertEvent.class, RemoveAlertEvent.class);
		
		alertEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event evento, ZirkEndPoint receiver) {
								
				if (evento instanceof AddAlertEvent)
					addAlertEventProcedure((AddAlertEvent) evento);
				
				else if (evento instanceof RemoveAlertEvent)
					removeAlertEventProcedure((RemoveAlertEvent) evento);
			}
		});
		
		bezirk.subscribe(alertEvents);
	}

	@SuppressWarnings("unchecked")
	private void retrieveFromDatabase() {
		try (ObjectInputStream br = new ObjectInputStream(new FileInputStream(DATABASE_ALERTS));) {
	
			List<AddAlertEvent> eventosLista = (CopyOnWriteArrayList<AddAlertEvent>) br.readObject();
			
			for (AddAlertEvent evento : eventosLista)
				this.addAlertEventProcedure(evento);
		} 
		
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Responsible to remove a previously added event
	 * @param evento event with the id to be removed
	 * @requires evento != null
	 */
	private void removeAlertEventProcedure(RemoveAlertEvent evento) {
		
		int id = evento.getAddAlertId();
		
		Pair<AddAlertEvent, InnerAlertAssistant> objetoEncontrado = null;
		
		// Search for the object to be removed
		for (Pair<AddAlertEvent, InnerAlertAssistant> parEventoAlerta : this.addAlerts) {
			if (parEventoAlerta.getKey().getAddMessageId() == id) {
				
				// Stop its execution
				parEventoAlerta.getValue().cancelInnerAlert();
				objetoEncontrado = parEventoAlerta;
				break;
			}
		}
		
		// If it exists, remove it
		if (objetoEncontrado != null) {
		
			this.addAlerts.remove(objetoEncontrado);
			bezirk.sendEvent(new OutputEvent(I18N.getString(Messages.REMOVED_ALERT, 
							 objetoEncontrado.getKey().getAlertMessage())));
			this.saveToDatabase();
		}
	}
	
	/**
	 * Adds a new alert and starts it
	 * @param evento alert to be added
	 * @requires alert != null
	 */
	private void addAlertEventProcedure(AddAlertEvent evento) {
		
		Timer timer = new Timer();
		InnerAlertAssistant innerThread = new InnerAlertAssistant(evento.getAlertMessage(),
											   evento.getDataInicio(), evento.getDataFim(), 
											   evento.getIntervalo(), timer);	
		
		// Set timer wich a schedule
		timer.schedule(innerThread, 0, evento.getIntervalo() * MINUTE_TO_MILLISECONDS);
		
		// Update alerts list
		this.addAlerts.add(new Pair<>(evento, innerThread));
		this.saveToDatabase();
	}

	private void saveToDatabase() {
		
		List<AddAlertEvent> alertas = new CopyOnWriteArrayList<>();
		
		for (Pair<AddAlertEvent, InnerAlertAssistant> par : this.addAlerts)
			alertas.add(par.getKey());
	
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATABASE_ALERTS));) {		
			out.writeObject(alertas);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Private Inner class responsible to execute the alerts every X seconds
	 * @author fc47806, fc47878, fc47888
 	*/
	private class InnerAlertAssistant extends TimerTask {
				
		// Atributos
		private String alerta;
		private Date dataAtual;
		private Date dataFim;
		private int progressao;
		private Timer timer;
		
		public InnerAlertAssistant(String alerta, Date dataInicio, Date dataFim,
								   int progressao, Timer timer) {
		
			this.alerta = alerta;
			this.dataAtual = dataInicio;
			this.dataFim = dataFim;
			this.progressao = progressao;
			this.timer = timer;
		}
		
		@Override
		public void run() {
			if (dataAtual.before(dataFim)) {
				
				Calendar calendarioAtual = Calendar.getInstance();
				calendarioAtual.setTime(dataAtual);
				
				int horas = calendarioAtual.get(Calendar.HOUR_OF_DAY);
				int minutos = calendarioAtual.get(Calendar.MINUTE);
				
				String horasString = Integer.toString(horas);
				String minutosString = Integer.toString(minutos);
				
				if(horas < 10) 
					horasString = "0" + horasString;
				
				if(minutos < 10) 
					minutosString = "0" + minutosString;
				
				// Sends the alert to be displayed
				String msgAlerta = I18N.getString(Messages.REMINDER_ALERT) 
						  + " " + horasString + ":" + minutosString + " - " + alerta;
				
				OutputEvent outputEvent = new OutputEvent(msgAlerta);
				bezirk.sendEvent(outputEvent);
				
				// Criar SMS e 
				SMSMessageEvent msgEvent = new SMSMessageEvent(msgAlerta);
				bezirk.sendEvent(msgEvent);
				
				// Updates the current Calendar
				Calendar cal = Calendar.getInstance();
				cal.setTime(dataAtual);
				cal.add(Calendar.MINUTE, progressao);
				this.dataAtual = cal.getTime();
			}
			
			else {
				timer.cancel();
				timer.purge();
			}
		}
		
		/**
		 * Used to cancel the current alert
		 */
		public void cancelInnerAlert() {
			
			timer.cancel();
			timer.purge();
		}
	}
}
