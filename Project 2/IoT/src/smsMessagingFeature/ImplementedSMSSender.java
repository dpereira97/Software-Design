package smsMessagingFeature;

import com.bezirk.middleware.Bezirk;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

import i18n.I18N;
import i18n.Messages;
import output.OutputEvent;

import com.twilio.rest.api.v2010.account.Message;

/**
 * Implemented SMS sender which sends sms's to registered users in Twillio
 * @author fc47806, fc47878, fc47888
 */
public class ImplementedSMSSender {
	  
	// Find your Account Sid and Token at twilio.com/user/account
	private static final String ACCOUNT_SID = "YOUR ACCOUNT SID HERE";
	private static final String AUTH_TOKEN = "YOUR AUTH TOKEN HERE";
	
	// Atributos  
	private Bezirk bezirk;
	
	public ImplementedSMSSender(Bezirk bezirk) {
		
		this.bezirk = bezirk;
	}
	
	// #Twilio
	@SuppressWarnings("unused")
	public void send(String mensagem) {
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		Message message = Message.creator(new PhoneNumber(PHONE_NUMBER),
				  			new PhoneNumber("+18655187039"), mensagem).create();
		
		bezirk.sendEvent(new OutputEvent(I18N.getString(Messages.SMS_MSG_SENT, mensagem, "INSERT CELL NUMBER HERE")));
	}
}
