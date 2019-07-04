package userInterface.java.controllers;

import i18n.I18N;
import i18n.Messages;
import javafx.scene.control.Alert;

public class BaseController {
	
	public void showError(Alert alert, String errorText) {
		alert.setTitle(I18N.getString(Messages.UI_ERROR_TITLE));
		alert.setHeaderText(null);
		alert.setContentText(errorText);
		alert.showAndWait();
	}
	
	public void showInfo(Alert alert, String message) {

		alert.setTitle(I18N.getString(Messages.UI_INFO_TITLE));
		alert.setHeaderText(null);
		alert.setContentText(message);
		
		alert.showAndWait();
	}

}
