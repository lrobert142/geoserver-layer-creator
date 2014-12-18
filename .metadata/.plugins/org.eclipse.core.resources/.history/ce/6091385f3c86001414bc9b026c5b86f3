/**
 * Author Zoe McIntosh
 */
package au.gov.aims;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import au.gov.aims.MainApp;
import au.gov.aims.model.BulkloaderDataWrapper;
import au.gov.aims.model.GeoServerDetails;
import au.gov.aims.model.UploadManger;
import au.gov.aims.view.OverviewController;
import au.gov.aims.view.RootLayoutController;
import au.gov.aims.view.ServerDetailsController;
import au.gov.aims.view.UploadingController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Stage serverDetailsStage;
	private List<GeoServerDetails> geoServerDetailsList = new ArrayList<GeoServerDetails>();
	public static final File GEOSERVER_DETAILS_FILE = new File("GeoServerLogonDetails.xml");
	private Logger logger;
	private OverviewController overviewController;
	private RootLayoutController rootLayoutController;
	private ServerDetailsController serverDetailsController;
	private UploadingController uploadingController;
	public MainApp() {
		DOMConfigurator.configure("log4j.xml");
		logger = Logger.getLogger(MainApp.class);
	}

	public static void main(String[] args) {
		Application.launch(MainApp.class, args);
	} 
	
	/**
	 * set up primary stage settings,
	 * loads the HTTPAddress and Username from file if known
	 * shows main layout 
	 */
	@Override
	public void start(Stage primaryStage) {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("GeoServer BulkLoader");
			this.primaryStage.setMinHeight(740);
			this.primaryStage.setMinWidth(720);
			this.primaryStage.setWidth(720);
			this.primaryStage.setHeight(740);
			
			loadGeoServerDataFromFile(GEOSERVER_DETAILS_FILE);

			initRootLayout();
			showServerDetailsView();
			showOverview();
			//getCSVFilePath();
			overviewController.setRootLayoutController(rootLayoutController);
			overviewController.setServerDetailsController(serverDetailsController);
			overviewController.setUploadingController(uploadingController);
			
	}

	/**
	 * Shows the overview inside the root layout.
	 */
	public void showOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Overview.fxml"));
			AnchorPane Overview = (AnchorPane) loader.load();
			rootLayout.setCenter(Overview);
			overviewController = loader.getController();
			overviewController.setMainApp(this);
		
			Overview.setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard dragboard = event.getDragboard();
					if (dragboard.hasFiles()) {
						event.acceptTransferModes(TransferMode.COPY);
					} else {
						event.consume();
					}
				}
			});
			
			Overview.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard dragboard = event.getDragboard();
					boolean success = false;
					if (dragboard.hasFiles()) {
						success = true;
						String filePath = null;
						for (File file : dragboard.getFiles()) {
							filePath = file.toString();
							overviewController.displayFilesAndDirectory(new File (filePath));
						}
					}
					event.setDropCompleted(success);
					event.consume();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + e.getStackTrace());
		}
	}
	
	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add("/view/BlueStyle.css");
			primaryStage.setScene(scene);
			rootLayoutController = loader.getController();
			rootLayoutController.setMainApp(this);

		} catch (IOException e) {
			logger.error(e.getMessage() + e.getStackTrace());
		}
	}
	
	
	// 
	/**
	 * Shows the ServerDetailsView.
	 */
	public void showServerDetailsView() {
		serverDetailsStage = new Stage();
		serverDetailsStage.initModality(Modality.WINDOW_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ServerDetailsView.fxml"));
			AnchorPane serverView = (AnchorPane) loader.load();
			Scene serverScene = new Scene(serverView);
			serverScene.getStylesheets().add("/view/BlueStyle.css");
			serverDetailsStage.setScene(serverScene);
			serverDetailsStage.setMinHeight(550);
			serverDetailsStage.setMinWidth(650.0);
			serverDetailsStage.setWidth(650.0);
			serverDetailsStage.setHeight(550);
			serverDetailsController = loader.getController();
			serverDetailsController.setMainApp(this);
			serverDetailsController.setupTextFieldWithData();
			primaryStage.hide();
			serverDetailsStage.show();
			
			if(overviewController != null)
				overviewController.setServerDetailsController(serverDetailsController);
			
			serverDetailsStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					primaryStage.show();
					serverDetailsStage.hide();
					
				}
			});
		} catch (IOException e) {
			logger.error(e.getMessage() + e.getStackTrace());
		}
	}
	
	/**
	 * The preference is read from the OS specific registry. If nothing found, null is returned.
	 * @return the file that was last opened.
	 */
	public File getCSVFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	/**
	 * Sets the file path of the currently loaded file. The path is persisted in the OS specific registry
	 * @param file
	 */
	public void setCSVFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());
	    } else {
	        prefs.remove("filePath");
	    }
	}
	
	/**
	 * load GeoServer Data from File
	 * @param file
	 */
	public void loadGeoServerDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext.newInstance(BulkloaderDataWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();
	        BulkloaderDataWrapper wrapper = (BulkloaderDataWrapper) um.unmarshal(file);
	        geoServerDetailsList.clear();
	        geoServerDetailsList.addAll(wrapper.getDetails());
	        setCSVFilePath(file);
	    } catch (Exception e) { 
	    	logger.error(e.getMessage() + e.getStackTrace());
	    }
	}
	
	/**
	 * function to save GeoServer Data to the file
	 * @param file
	 */
	public void saveGeoServerDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext.newInstance(BulkloaderDataWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        BulkloaderDataWrapper wrapper = new BulkloaderDataWrapper();
	        wrapper.setDetails(geoServerDetailsList);
	        m.marshal(wrapper, file);
	        setCSVFilePath(file);
	    } catch (Exception e) { 
	    	logger.error(e.getMessage() + e.getStackTrace());
	    }
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	public Stage getServerDetailsStage() {
		return serverDetailsStage;
	}

	public void setServerDetailsStage(Stage serverDetailsStage) {
		this.serverDetailsStage = serverDetailsStage;
	}

	public List<GeoServerDetails> getGeoServerDetailsList() {
		return geoServerDetailsList;
	}

	public void setGeoServerDetailsList(List<GeoServerDetails> geoServerDetailsList) {
		this.geoServerDetailsList = geoServerDetailsList;
	}

	public File getGeoServerDetailsFile() {
		return GEOSERVER_DETAILS_FILE;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public Logger getLogger() {
		return logger;
	}
}