package com.gov.aims.view;

import java.io.IOException;

import com.gov.aims.MainApp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HelpController {
	
	@FXML
	public static Label HelpLabel;	
	
	private MainApp mainApp;
	
	// Is called by the main application to give a reference back to itself.
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}

	@FXML
	private void CloseHelp() {
		try {
			// overview.

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/Overview.fxml"));
			AnchorPane Overview = (AnchorPane) loader.load();
			// Set overview into the center of root layout.
			 MainApp.rootLayout.setCenter(Overview);
			// Give the controller access to the main app.
			 OverviewController controller =
			 loader.getController();
			 controller.setMainApp(mainApp);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
}