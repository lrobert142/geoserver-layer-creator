package au.gov.aims.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import au.gov.aims.MainApp;
import au.gov.aims.model.UploadManger;

public class RootLayoutController {

	// Reference to the main application
	private MainApp mainApp;
	
	// Reference to the main uploaderManager
	private UploadManger uploadManager;

	// variables
	Window primaryStage = null;
	public static File DefaultDirectory = new File(System.getProperty("user.home"));
	public static String SelectedDirectory = "";

	// Is called by the main application to give a reference back to itself.
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		uploadManager = new UploadManger();
	}

	// Menu Methods

	// Close MenuItem
	@FXML
	private void close() {
		System.exit(0);
	}

	// Display Shape MenuItem
	@FXML
	private void OpenShapeFiles() throws IOException {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Find Directory");
			chooser.setInitialDirectory(DefaultDirectory);
			File selectedDirectory = chooser.showDialog(primaryStage);
			SelectedDirectory = selectedDirectory.toString();
			uploadManager.setUpFiles(SelectedDirectory);
			try {
				// overview.
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/Overview.fxml"));
				AnchorPane overview = (AnchorPane) loader.load();
				// Set overview into the center of root layout.
				MainApp.rootLayout.setCenter(overview);
				// Give the controller access to the main app.
				OverviewController controller = loader.getController();
				controller.setMainApp(mainApp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			OverviewController.showFileInView();
			OverviewController.showZipFileInView();
		} catch (NullPointerException e) {
		}
	}

	// Open CSV MenuItem
	@FXML
	private void OpenCSV() throws IOException {
		if (Desktop.isDesktopSupported()) {
			try {
				SelectedDirectory += "/uploadLayers.csv";
				File myFile = new File(SelectedDirectory);
				Desktop.getDesktop().open(myFile);
			} catch (IllegalArgumentException iae) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open CSV File");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
				fileChooser.setInitialDirectory(DefaultDirectory);
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// Help MenuItem
	@FXML
	private void ViewHelp() {
		try {
			// overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/HelpDocView.fxml"));
			AnchorPane Overview = (AnchorPane) loader.load();
			// Set overview into the center of root layout.
			MainApp.rootLayout.setCenter(Overview);
			// Give the controller access to the main app.
			HelpController controller = loader.getController();
			controller.setMainApp(mainApp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// About MenuItem
	@FXML
	private void ViewAbout() {
		try {
			// overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AboutView.fxml"));
			AnchorPane overview = (AnchorPane) loader.load();
			// Set overview into the center of root layout.
			MainApp.rootLayout.setCenter(overview);
			// Give the controller access to the main app.
			AboutController controller = loader.getController();
			controller.setMainApp(mainApp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Change Directory MenuItem
	@FXML
	private void ChangeDefaultDirectory() {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Find Default Directory to Use");
			chooser.setInitialDirectory(DefaultDirectory);
			File selectedDirectory = chooser.showDialog(primaryStage);
			DefaultDirectory = selectedDirectory;
		} catch (NullPointerException e) {
		}
	}

	// Upload Tiff MenuItem
	@FXML
	private void UploadTiff() {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Find Directory");
			chooser.setInitialDirectory(DefaultDirectory);
			File selectedDirectory = chooser.showDialog(primaryStage);
			SelectedDirectory = selectedDirectory.toString();
			uploadManager.uploadGeoServerFilesToGeoServer(SelectedDirectory);
		} catch (NullPointerException e) {
		}
	}

	// Upload SHape MenuItem
	@FXML
	private void UploadShape() {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Find Directory");
			chooser.setInitialDirectory(DefaultDirectory);
			File selectedDirectory = chooser.showDialog(primaryStage);
			SelectedDirectory = selectedDirectory.toString();
			uploadManager.uploadGeoServerFilesToGeoServer(SelectedDirectory);
		} catch (NullPointerException e) {
		}
	}

	// Display Tiff MenuItem
	@FXML
	private void OpenTiffFiles() throws IOException {
		try {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Find Directory");
			chooser.setInitialDirectory(DefaultDirectory);
			File selectedDirectory = chooser.showDialog(primaryStage);
			SelectedDirectory = selectedDirectory.toString();
			uploadManager.setUpFiles(SelectedDirectory);
			OverviewController.showFileInView();
			OverviewController.showZipFileInView();
		} catch (NullPointerException e) {
		}
	}
}