package dynamicAlerts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import i18n.I18N;
import i18n.Messages;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

/**
 * Allows the user to create and remove custom alerts.
 * @author fc47806, fc47878, fc47888
 */
public class AlertUpdateZirk {
	
	// Constants
	private static final String DATABASE_ALERTS = "dbAlerts";
	
	// Atributos
	private int lastAddMessageId;
	
	private Bezirk bezirk;
	private List<AddAlertEvent> alertas;
	
	/**
	 * Constructor which initializes bezirk
	 */
	public AlertUpdateZirk() {

		BezirkMiddleware.initialize();
		this.bezirk = BezirkMiddleware.registerZirk("Alert Update Zirk");
		
		this.lastAddMessageId = 0;
		this.alertas = new CopyOnWriteArrayList<>();
		
		if ((new File(DATABASE_ALERTS)).exists())
			this.retrieveFromDatabase();
		
		System.err.println(I18N.getString(Messages.SYS_INFO_BEZIRK));
	}
	
	@SuppressWarnings("unchecked")
	private void retrieveFromDatabase() {
		try (ObjectInputStream br = new ObjectInputStream(new FileInputStream(DATABASE_ALERTS));) {
	
			List<AddAlertEvent> eventosLista = (CopyOnWriteArrayList<AddAlertEvent>) br.readObject();
			
			for (AddAlertEvent evento : eventosLista) {
			
				this.alertas.add(evento);
				this.lastAddMessageId = evento.getAddMessageId();
			}
			
			this.lastAddMessageId++;
		} 
		
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<AddAlertEvent> getAlerts() {
		return this.alertas;
	}
	
	/**
	 * Defines and sends an alert created by the user
	 * @param alertaName
	 * @param beginDate
	 * @param endDate
	 * @param horaMinutosInicio
	 * @param horaMinutosFim
	 * @param intervalo
	 * @requires alertName != null && beginDate != null && endDate != null && 
	 * horasMinutosInicio != null && horMinutosFim != null && intervalo > 0
	 */
	public void sendAddAlert(String alertaName, String beginDate, String endDate, 
			String horaMinutosInicio, String horaMinutosFim, int intervalo)  {
		
		Date dataInicio;
		
		try {
			dataInicio = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(beginDate + " " + horaMinutosInicio);
			Date dataFim = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(endDate + " " + horaMinutosFim);
			
			AddAlertEvent addEvent = new AddAlertEvent(this.lastAddMessageId, alertaName, dataInicio, dataFim, intervalo);
			
			// Update attributes
			this.alertas.add(addEvent);
			this.lastAddMessageId++;
			
			// Send event
			bezirk.sendEvent(addEvent);
				
		} catch (ParseException e) {
			System.err.println(I18N.getString(Messages.WRONG_INPUT));
		}
	}
	
	/**
	 * Defines and sends an event to remove a previously created event by the user
	 * @param alertId add alert id previously sent
	 * @throws Exception 
	 */
	public void sendRemoveAlert(int alertId) throws EventDoesntExistException{
		
		RemoveAlertEvent removeEvent = new RemoveAlertEvent(alertId);
		
		AddAlertEvent eventoARemover = null;
		
		for (AddAlertEvent evento : this.alertas)
			if (alertId == evento.getAddMessageId()) {
				
				eventoARemover = evento;
				break;
			}
		
		if (eventoARemover != null)
			this.alertas.remove(eventoARemover);
		
		if(eventoARemover == null) {
			throw new EventDoesntExistException(I18N.getString(Messages.EVENT_DOESNT_EXIST));
		}
		
		bezirk.sendEvent(removeEvent);
	}
}