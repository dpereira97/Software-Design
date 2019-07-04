package dynamicAlerts;

import java.util.Date;

import com.bezirk.middleware.messages.Event;

/**
 * Event sent to add a new alert created by the user
 * @author fc47806, fc47878, fc47888
 */
public class AddAlertEvent extends Event {

	/**
	 * Generated Serial Version
	 */
	private static final long serialVersionUID = 2573920313453332307L;

	/**
	 * Generated Serial value
	 */

	// Atributos
	private int addMessageId;
	
	private String alertMessage;
	private Date dataInicio;
	private Date dataFim;
	private int intervalo;
	
	public AddAlertEvent(int messageId, String alertMessage, Date dataInicio, 
						 Date dataFim, int intervalo) {
		
		this.addMessageId = messageId;
		this.alertMessage = alertMessage;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.intervalo = intervalo;
	}

	// Getters and setters
	
	public int getAddMessageId() {
		return addMessageId;
	}

	public void setAddMessageId(int messageId) {
		this.addMessageId = messageId;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}
}
