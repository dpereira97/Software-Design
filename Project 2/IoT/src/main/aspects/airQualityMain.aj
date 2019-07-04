package main.aspects;

import airQualityFeature.AirQualitySensorZirk;
import airQualityFeature.AsthmaAssistantZirk;

public aspect airQualityMain {
	
	pointcut inicializar(): call(* main.Main.initializeAlerts());
	
	before(): inicializar() {
		
		new AsthmaAssistantZirk();
	}
	
	before(): call(* userInterface.java.main.StartUp.startGui(*)) {
		new Thread(() -> {
			new AirQualitySensorZirk();
		}).start();
	}
}
