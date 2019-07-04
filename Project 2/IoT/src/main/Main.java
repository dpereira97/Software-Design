package main;

import java.util.concurrent.TimeUnit;

import dynamicAlerts.AlertUpdateZirk;
import dynamicAlerts.AlertAssistantZirk;

import smsMessagingFeature.SMSMessagingZirk;
import userInterface.java.main.StartUp;

import database.StartDatabase;

/**
 * Main
 * @author fc47806, fc47878, fc47888
 */
public class Main {
	
	/**
	 * Starts the program
	 * @param args
	 */
	public static void main(String[] args) {
	
		// Features obrigatorias
		// Base de dados
		initializeDataBase();
		
		// SMS Feature
		initializeSMSFeature();
		
		// Alertas Dinamicos e inicializa GUI
		AlertUpdateZirk auz = initializeAlerts();
		
		// Inicializar o GUI
		StartUp.startGui(auz);
		
		// Wait for GUI inicialization
		waitForGUI();
	}

	/**
	 * Updates memory from the database which contains the list with user contacts
	 */
	private static void initializeDataBase() {
		
		StartDatabase.startDatabase();
	}

	/**
	 * Initializes the SMS feature
	 */
	private static void initializeSMSFeature() {

		new SMSMessagingZirk();
	}
	
	/**
	 * Intializes the custom alerts feature
	 */
	private static AlertUpdateZirk initializeAlerts() {
		
		new AlertAssistantZirk();
		return new AlertUpdateZirk();
	}

	/**
	 * Waits for the GUI to build
	 */
	private static void waitForGUI() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
