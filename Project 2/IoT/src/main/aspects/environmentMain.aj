package main.aspects;

import environmentMonitorFeature.TemperatureAssistantZirk;
import environmentMonitorFeature.TemperatureSensorZirk;

public aspect environmentMain {
	
	pointcut inicializar(): call(* main.Main.initializeAlerts());
	
	before(): inicializar() {
		
		new TemperatureAssistantZirk();
	}
	
	before(): call(* userInterface.java.main.StartUp.startGui(*)) {
		new Thread(() -> {
			new TemperatureSensorZirk();
		}).start();
	}
}