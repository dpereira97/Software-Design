package main.aspects;

import movementDetectorFeature.MovementAssistantZirk;
import movementDetectorFeature.MovementDetectorZirk;

public aspect movementMain {
	
	pointcut inicializar(): call(* main.Main.initializeAlerts());
	
	before(): inicializar() {
		
		new MovementAssistantZirk();
	}
	
	before(): call(* userInterface.java.main.StartUp.startGui(*)) {
		new Thread(() -> {
			new MovementDetectorZirk();
		}).start();
	}
}