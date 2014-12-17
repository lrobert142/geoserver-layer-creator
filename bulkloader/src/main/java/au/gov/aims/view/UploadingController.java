/**
 * Author Zoe McIntosh
 */
package au.gov.aims.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import au.gov.aims.MainApp;
import au.gov.aims.model.GeoServerManager;
import au.gov.aims.model.UploadManger;
import au.gov.aims.utilities.PathsHandler;

public class UploadingController {

	@FXML
	private Label uploadingDetailsLabel;
	@FXML
	private Button stopUploadButton;
	@FXML
	private Button backButton;

	@FXML
	private ListView<String> responseListview;

	private static ObservableList<String> responsesListItems;
	private MainApp mainApp;
	private RootLayoutController rootLayoutController;
	private ServerDetailsController serverDetailsController;
	private UploadManger uploadManager;
	private int currentIndex;

	public void setServerDetailsController(
			ServerDetailsController serverDetailsController) {
		this.serverDetailsController = serverDetailsController;
	}

	public void setRootLayoutController(
			RootLayoutController rootLayoutController) {
		this.rootLayoutController = rootLayoutController;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() throws IOException {
		responsesListItems = responseListview.getItems();
		uploadManager = new UploadManger();
		backButton.setVisible(false);
	}

	/**
	 * function used to close the about view and return back to the
	 * overview.fxml
	 */
	@FXML
	private void closeUploaderView() {
		try {

			uploadManager.stopUpload(PathsHandler.getBasePath(rootLayoutController.getDefaultDirectory().toString()));

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Overview.fxml"));
			AnchorPane overview = (AnchorPane) loader.load();
			mainApp.getRootLayout().setCenter(overview);
			OverviewController controller = loader.getController();
			controller.setMainApp(mainApp);

		} catch (Exception e) {
			mainApp.getLogger().error(e.getMessage() + e.getStackTrace());
			e.printStackTrace();
			System.exit(1);
		}

	}

	public void uploaderFunctions() {
		uploadManager.login(serverDetailsController.getGeoServerURLString(),
				serverDetailsController.getUserNameString(),
				serverDetailsController.getPasswordString());
		addMessage("Zipping files....");
		uploadManager.setUpFiles(PathsHandler.getBasePath(rootLayoutController.getDefaultDirectory().toString()));
		addMessage("Files zipped");
		addMessage("Starting upload......");
		listResponseThread();
		if (uploadManager.uploadGeoServerFilesToGeoServer(rootLayoutController.getDefaultDirectory().toString())) {
			addMessage("Upload complete");
		} else {
			addMessage("Not all files to upload were successful, the failed uploads have been written to a csv file named failedUploads.csv, please fix them and try again");
		}
		stopUploadButton.setVisible(false);
		backButton.setVisible(true);
		
	}
	
	public void addMessage(final String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				responsesListItems.add(message);
				responseListview.scrollTo(responseListview.getItems().size() - 1);
			}
		});
	}

	public void uploadThread() {
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() {
		    	uploaderFunctions();
				return null;
		    }
		};
		
		Thread th = new Thread(task);

		th.setDaemon(true);

		th.start();
	}
	
	public void listResponseThread() {
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				currentIndex = 0;
				while (!uploadManager.isUploadComplete()) {
					if (currentIndex != uploadManager.getCurrentIndex()) {
						currentIndex = uploadManager.getCurrentIndex();
						addMessage("Uploading..... " + PathsHandler.absoluteToRelativePath(uploadManager.getCurrentUpload(currentIndex), PathsHandler.getBasePath(uploadManager.getCurrentUpload(currentIndex))));
					}
				}
				return null;
			}
		};
		
		Thread th = new Thread(task);

		th.setDaemon(true);

		th.start();
	}
}