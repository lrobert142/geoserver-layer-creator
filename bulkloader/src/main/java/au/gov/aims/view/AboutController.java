package au.gov.aims.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import au.gov.aims.MainApp;

public class AboutController {

	@FXML
	public static Label HelpLabel;	
	
	private MainApp mainApp;
	
	// Is called by the main application to give a reference back to itself.
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void CloseAbout() {
		try {
			// overview.

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Overview.fxml"));
			AnchorPane overview = (AnchorPane) loader.load();
			// Set overview into the center of root layout.
			 MainApp.rootLayout.setCenter(overview);
			// Give the controller access to the main app.
			 OverviewController controller =
			 loader.getController();
			 controller.setMainApp(mainApp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}