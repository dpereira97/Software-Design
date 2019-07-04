package smsMessagingFeature;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.lang.reflect.Type;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import database.Contact;
import database.StartDatabase;

/**
 * Zirk que recebe mensagens e envia SMS's
 * @author fc47806, fc47878, fc47888
 */
public class SMSMessagingZirk {

	// Atributos
	private final Bezirk bezirk;
	
	private List<String> contactos;
	private SMSHandler smsHandler;
	
	public SMSMessagingZirk() {
		
		// Obter o bezirk
		BezirkMiddleware.initialize();
		this.bezirk = BezirkMiddleware.registerZirk("Output Zirk");
		System.err.println("Got Bezirk instance");

		this.contactos = getContactsFromDatabase();
		this.smsHandler = new SMSHandler(contactos, bezirk);
		
		// Operacao de evento que eh concretizada
		final EventSet outputEvents = new EventSet(SMSMessageEvent.class);
		
		outputEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				
				if (event instanceof SMSMessageEvent) {
					
					SMSMessageEvent evento = (SMSMessageEvent) event;
					
					smsHandler.sendMessages(evento);
				}
			}
		});
		
		bezirk.subscribe(outputEvents);
	}

	private List<String> getContactsFromDatabase() {
		
		List<String> resultado = new CopyOnWriteArrayList<>();
		
		Gson gson = new Gson();
		
		try (BufferedReader br = new BufferedReader(new FileReader(StartDatabase.DATABASE));) {
			
			String json = br.readLine();
			
			Type collectionType = new TypeToken<ArrayList<Contact>>(){}.getType();
			List<Contact> list2 = gson.fromJson(json, collectionType);
			
			for (Contact contacto : list2)
				resultado.add(contacto.getPhoneNum());
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
}
