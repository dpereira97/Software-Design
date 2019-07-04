package smsMessagingFeature;

import java.util.List;

import com.bezirk.middleware.Bezirk;

import i18n.I18N;
import i18n.Messages;
import output.OutputEvent;

/**
 * Handler de SMSs
 * @author fc47806, fc47878, fc47888
 */
public class SMSHandler {

	// Atributos
	private Bezirk bezirk;
	private List<String> contactos;
	
	public SMSHandler(List<String> contactos, Bezirk bezirk) {
		
		this.contactos = contactos;
		this.bezirk = bezirk;
	}
	
	public void sendMessages(SMSMessageEvent evento) {
		
		String mensagem = evento.getMessage();
			
		for (String contacto : this.contactos)
			this.bezirk.sendEvent(new OutputEvent(I18N.getString(Messages.SMS_MSG_SENT, mensagem, contacto)));
	}
}
