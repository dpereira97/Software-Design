package userInterface.java.controllers;

import java.util.List;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import dynamicAlerts.AddAlertEvent;
import dynamicAlerts.AlertUpdateZirk;
import dynamicAlerts.EventDoesntExistException;
import i18n.I18N;
import i18n.Messages;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import output.OutputEvent;
import output.screen.ScreenFacade;

public class ProductPageController extends BaseController {
	
    private final Bezirk bezirk;
	
	public ProductPageController() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Product GUI");
		
		// Operacao de evento que eh concretizada
		final EventSet outputEvents = new EventSet(OutputEvent.class);
		ScreenFacade screen = new ScreenFacade();
		
		outputEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				
				if (event instanceof OutputEvent) {
					
					OutputEvent evento = (OutputEvent) event;
					
					String eventMessage = evento.getMessage();
					
					Platform.runLater(() -> {
						addText(eventMessage);	
					});
					
					screen.computeMessage(eventMessage);
				}
			}
		});
		
		bezirk.subscribe(outputEvents);
	}
	
	public void addText(String text) {
		alerts.appendText(text + "\n");
	}
	
	//Used to call methods needed
	private Stage stage;
	private AlertUpdateZirk auz;
	
	//Buttons and areas used to switch between scenes
	@FXML TextArea alerts;
	@FXML Button addAlertButton;
	@FXML Button removeAlertButton;
	@FXML AnchorPane alertAnchorPane;
	@FXML AnchorPane removeAnchorPane;
	@FXML Button addAlertButtonFinal;
	@FXML Button removeAlertButtonFinal;
	@FXML Menu closeWindowMenu; @FXML MenuItem closeButton;
	@FXML Menu goBackMenu;		@FXML MenuItem goBackButton;
	@FXML ListView<String> alertsListView;
	
	//Labels and Text Fields
	@FXML Label alertMessage; @FXML TextField alertMessageField; 
	@FXML Label beginDate; 	  @FXML TextField beginDateField; 
	@FXML Label beginHours;   @FXML TextField beginHoursField; 
	@FXML Label endDate;      @FXML TextField endDateField; 
	@FXML Label endHours;     @FXML TextField endHoursField; 
	@FXML Label interval; 	  @FXML TextField intervalField; 
	@FXML TextField idField;
	
	public void setStage(Stage stage) {
		
		this.stage = stage;
		this.stage.setAlwaysOnTop(true);
		
		//Adds text to buttons according to language
		addAlertButton.setText(I18N.getString(Messages.ALERT_BUTTON));
		addAlertButtonFinal.setText(I18N.getString(Messages.ALERT_BUTTON));
		removeAlertButton.setText(I18N.getString(Messages.REMOVE_BUTTON));
		removeAlertButtonFinal.setText(I18N.getString(Messages.REMOVE_BUTTON));
		
		//Adds text to labels for creating a new alert according to language
		alertMessage.setText(I18N.getString(Messages.UI_MESSAGE));
		beginDate.setText(I18N.getString(Messages.UI_BEGIN_DATE));
		beginHours.setText(I18N.getString(Messages.UI_BEGIN_HOURS));
		endDate.setText(I18N.getString(Messages.UI_END_DATE));
		endHours.setText(I18N.getString(Messages.UI_END_HOURS));
		interval.setText(I18N.getString(Messages.UI_INTERVAL));
		
		//Adds prompt text to field for creating a new alert according to language
		beginDateField.setPromptText(I18N.getString(Messages.UI_YEAR_PROMPT));
		endDateField.setPromptText(I18N.getString(Messages.UI_YEAR_PROMPT));
		beginHoursField.setPromptText("HH:MM");
		endHoursField.setPromptText("HH:MM");
		
		//Adds text to menu items according to language
		closeWindowMenu.setText(I18N.getString(Messages.UI_CLOSE));
		closeButton.setText(I18N.getString(Messages.UI_CLOSE));
		goBackMenu.setText(I18N.getString(Messages.UI_GO_BACK));
		goBackButton.setText(I18N.getString(Messages.UI_GO_BACK));
	}
	
	public void setAlertUpdateZirk(AlertUpdateZirk auzIn) {
		
		this.auz = auzIn;
	}
	
	@FXML
	public void closeStage(ActionEvent event) {
		
		stage.close();
	}
	
	@FXML
	public void goBack(ActionEvent event) {
		
		alertAnchorPane.setVisible(false);
		removeAnchorPane.setVisible(false);
		goBackMenu.setVisible(false);
		clearTextFields();
	}
	
	@FXML
	public void addAlert(ActionEvent event) {
		
		alertAnchorPane.setVisible(true);
		goBackMenu.setVisible(true);
	}
	
	@FXML
	public void addAlertFinal(ActionEvent event) {
		
		String errors = getErrors();
		
		if(!errors.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			showError(alert, errors);
			return;
		}
		
		try {
			auz.sendAddAlert(alertMessageField.getText(), beginDateField.getText(), endDateField.getText(), 
					beginHoursField.getText(), endHoursField.getText(), Integer.parseInt(intervalField.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(stage);
			showInfo(alert, I18N.getString(Messages.UI_ALERT_ADDED));
			clearTextFields();
			alertAnchorPane.setVisible(false);
		}
		
		catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			showError(alert, I18N.getString(Messages.UI_ERROR_ALERT_ADDED, e.getMessage()));
		}
	}
	
	private void clearTextFields() {
		alertMessageField.clear();
		beginDateField.clear();
		endDateField.clear();
		beginHoursField.clear();
		endHoursField.clear();
		intervalField.clear();
		idField.clear();
	}
	
	private String getErrors() {
		StringBuilder sb = new StringBuilder();
		
		if(alertMessageField.getText().isEmpty()) {
			sb.append(I18N.getString(Messages.UI_ERROR_MESSAGE) + "\n");
		}
		if(dateNotCorrect(beginDateField.getText())) {
			sb.append(I18N.getString(Messages.UI_ERROR_BEGIN_DATE) + "\n");
		}
		if(dateNotCorrect(endDateField.getText())) {
			sb.append(I18N.getString(Messages.UI_ERROR_END_DATE) + "\n");
		}
		if(hoursNotCorrect(beginHoursField.getText())) {
			sb.append(I18N.getString(Messages.UI_ERROR_BEGIN_HOURS) + "\n");
		}
		if(hoursNotCorrect(endHoursField.getText())) {
			sb.append(I18N.getString(Messages.UI_ERROR_END_HOURS) + "\n");
		}
		if(intervalField.getText().isEmpty()) {
			sb.append(I18N.getString(Messages.UI_ERROR_INTERVAL) + "\n");
		}
		try {
			Integer.parseInt(intervalField.getText());
		}catch(NumberFormatException e) {
			sb.append(I18N.getString(Messages.UI_ERROR_INTERVAL_INT) + "\n");
		}
		
		return sb.toString();
	}
	
	private boolean dateNotCorrect(String date) {
		String[] parts = date.split("-");
		if(parts.length != 3) {
			return true;
		}
		return false;
	}
	
	private boolean hoursNotCorrect(String hours) {
		String[] parts = hours.split(":");
		if(parts.length != 2) {
			return true;
		}
		return false;
	}
	
	@FXML
	public void removeAlert(ActionEvent event) {
		removeAnchorPane.setVisible(true);
		goBackMenu.setVisible(true);
		//Gets the list containing the alerts for and displays it
		displayAlerts();
	}
	
	private void displayAlerts() {
		List<AddAlertEvent> alertsList = auz.getAlerts();
		alertsListView.getItems().clear();
		
		for(AddAlertEvent event: alertsList) {
			alertsListView.getItems().add("ID: " + event.getAddMessageId() + "  " + I18N.getString(Messages.UI_MESSAGE) + " " + event.getAlertMessage());
		}
		
		alertsListView.setSelectionModel(new NoSelectionModel<String>());
	}
	
	@FXML
	public void removeAlertFinal(ActionEvent event) {
		String errors = getRemoveErrors();
		if(!errors.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			showError(alert, errors);
			return;
		}
		
		try {
			auz.sendRemoveAlert(Integer.parseInt(idField.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(stage);
			showInfo(alert, I18N.getString(Messages.UI_ALERT_REMOVED));
			removeAnchorPane.setVisible(false);
			clearTextFields();
		} catch (EventDoesntExistException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			showError(alert, I18N.getString(Messages.UI_ERROR_ALERT_REMOVED, e.getMessage()));
		}
	
	}
	
	private String getRemoveErrors() {
		StringBuilder sb = new StringBuilder();
		
		if(idField.getText().isEmpty()) {
			sb.append(I18N.getString(Messages.UI_NO_ID) + "\n");
		}
		try {
			Integer.parseInt(idField.getText());
		} catch (NumberFormatException e) {
			sb.append(I18N.getString(Messages.UI_ID_INT) + "\n");
		}
		
		return sb.toString();
	}
}
