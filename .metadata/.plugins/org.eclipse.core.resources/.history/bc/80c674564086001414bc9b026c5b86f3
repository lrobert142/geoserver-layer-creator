/**
 * Author Zoe McIntosh
 */ 
package au.gov.aims.view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import au.gov.aims.MainApp;

public class RootLayoutController {

	private MainApp mainApp;

	private File defaultDirectory;
	//private String selectedDirectory;

	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}


	/**
	 * Close MenuItem
	 */
	@FXML
	private void close() {
		System.exit(0);
	}

	/**
	 * 	Change or set Directory, used in the Change Directory MenuItem
	 */
	@FXML
	private void ChangeDefaultDirectory() {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Find Default Directory to Use");
			chooser.setInitialDirectory(defaultDirectory);
			File selectedDirectory = chooser.showDialog(mainApp.getPrimaryStage());
			setDefaultDirectory(selectedDirectory);
			
		} catch (NullPointerException e) {
			mainApp.getLogger().error(e.getMessage() + e.getStackTrace());
		}
	}
	
	@FXML
	private void openServerDetailsView(){
		 mainApp.showServerDetailsView();
	}
	
	public File getDefaultDirectory() {
		return defaultDirectory;
	}


	public void setDefaultDirectory(File defaultDirectory) {
		this.defaultDirectory = defaultDirectory;
	}
}