package gov.au.aims.uploader;

import java.io.IOException;

import gov.au.aims.uploader.view.UploaderOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;

	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Geoserver Bulk Uploader");

		showUploaderOverview();
	}

	private void showUploaderOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/UploaderOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(personOverview);
			primaryStage.setScene(scene);
			UploaderOverviewController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}