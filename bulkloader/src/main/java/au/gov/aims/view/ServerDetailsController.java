/**
@author Zoe McIntosh
@version 1.0
@since 18/12/14
 */
package au.gov.aims.view;

import org.apache.commons.validator.routines.UrlValidator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import au.gov.aims.MainApp;
import au.gov.aims.model.GeoServerDetails;

public class ServerDetailsController {
	
	private MainApp mainApp;
	@FXML
	private TextField geoServerURLTextField;
	@FXML
	private TextField userNameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Label errorMessage;

	private String geoServerURLString;
	private String userNameString;
	private String passwordString;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/**
	 * function used to setup text fields with pre saved data
	 * 
	 */
	public void setupTextFieldWithData() {
		if (mainApp.getGeoServerDetailsList().size() > 0) {
			if (mainApp.getGeoServerDetailsList().get(0) != null) {
				String httpAddress = mainApp.getGeoServerDetailsList().get(0).getGeoServerHttpAddress();
				String username = mainApp.getGeoServerDetailsList().get(0).getUsername();
				setTextFieldAfterLoading(httpAddress, username);
			}
		}
	}

	/**
	 * function that brings the ServerDetailsView up before the application runs
	 * does the validating for the text fields and opens the main application once
	 * all validation is correct
	 */
	public void ValidateGeoServerLogin() {
		GeoServerDetails details = new GeoServerDetails();
		boolean validatorBoolean = true;
		String message = "";
		UrlValidator validator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
		geoServerURLString = geoServerURLTextField.getText();
		userNameString = userNameTextField.getText();
		passwordString = passwordTextField.getText();
		if (!validator.isValid(geoServerURLString)) {
			message += "GeoServer URL is not a Valid URL!" + "\n";
			validatorBoolean = false;
		}
		
		if (userNameString.isEmpty()) {
			message += "Username is empty!" + "\n";
		}
		
		if (passwordString.isEmpty()) {
			message += "Password is empty!" + "\n";
			validatorBoolean = false;
		}
		
		if (!validatorBoolean) {
			errorMessage.setText(message);
		} else {
			details.setGeoServerHttpAddress(geoServerURLString);
			details.setUsername(userNameString);
			setGeoServerURLString(geoServerURLString);
			setUserNameString(userNameString);
			setPasswordString(passwordString);
			
			mainApp.getGeoServerDetailsList().clear();
			mainApp.getGeoServerDetailsList().add(details);
			mainApp.saveGeoServerDataToFile(mainApp.GEOSERVER_DETAILS_FILE);
			mainApp.getPrimaryStage().show();
			mainApp.getServerDetailsStage().close();
		}
	}

	/**
	 * function that opens up the main application without any validation or entry
	 * It bypasses the GeoServer Details window
	 */
	public void setupApplicationWithoutServerDetails() {
		mainApp.getPrimaryStage().show();
		mainApp.getServerDetailsStage().close();
	}

	public void setTextFieldAfterLoading(String httpAddress, String Username) {
		geoServerURLTextField.setText(httpAddress);
		userNameTextField.setText(Username);
	}
	
	public String getGeoServerURLString() {
		return geoServerURLString;
	}

	public void setGeoServerURLString(String geoServerURLString) {
		this.geoServerURLString = geoServerURLString;
	}

	public String getUserNameString() {
		return userNameString;
	}

	public void setUserNameString(String userNameString) {
		this.userNameString = userNameString;
	}

	public String getPasswordString() {
		return passwordString;
	}

	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}
}