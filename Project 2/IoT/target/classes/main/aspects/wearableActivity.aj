package main.aspects;

import wearableDeviceFeature.activity.ActivityAssistantZirk;
import wearableDeviceFeature.activity.ActivitySensorZirk;

public aspect wearableActivity {

	pointcut inicializar(): call(* main.Main.initializeAlerts());
	
	before(): inicializar() {
		
		new ActivityAssistantZirk();
	}
	
	before(): call(* userInterface.java.main.StartUp.startGui(*)) {
		new Thread(() -> {
			new ActivitySensorZirk();
		}).start();
	}
}
