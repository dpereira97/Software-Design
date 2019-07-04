package main.aspects;

import wearableDeviceFeature.button.ButtonAssistantZirk;
import wearableDeviceFeature.button.ButtonSensorZirk;

public aspect wearableButton {

	pointcut inicializar(): call(* main.Main.initializeAlerts());
	
	before(): inicializar() {
		
		new ButtonAssistantZirk();
	}
	
	before(): call(* userInterface.java.main.StartUp.startGui(*)) {
		new Thread(() -> {
			new ButtonSensorZirk();
		}).start();
	}
}
