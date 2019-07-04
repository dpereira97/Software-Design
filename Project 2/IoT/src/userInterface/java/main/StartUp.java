package userInterface.java.main;

import dynamicAlerts.AlertUpdateZirk;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userInterface.java.controllers.ProductPageController;

public class StartUp extends Application {
	
	private static AlertUpdateZirk auz;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader mainMenu = new FXMLLoader(getClass().getResource("/userInterface/resources/ProductPage.fxml"));
		
		Parent root = mainMenu.load();
		Scene scene = new Scene(root, 640, 480);
		ProductPageController ppc = mainMenu.getController();
		ppc.setAlertUpdateZirk(auz);
		ppc.setStage(primaryStage);
		mainMenu.setController(ppc);
		
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	public static void startGui(AlertUpdateZirk auzIn) {
		
		StartUp.auz = auzIn;
		launch();
	}
}
