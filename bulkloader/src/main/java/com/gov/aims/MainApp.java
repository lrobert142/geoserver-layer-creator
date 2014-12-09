package com.gov.aims;
import java.io.File;
import java.io.IOException;
import com.gov.aims.MainApp;
import com.gov.aims.view.OverviewController;
import com.gov.aims.view.RootLayoutController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	public static BorderPane rootLayout;

	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Application Project");
		this.primaryStage.setResizable(false);	
		initRootLayout();
		showOverview();
	}

	// Shows the overview inside the root layout.
	public void showOverview() {
		try {
			// overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Overview.fxml"));			
			AnchorPane Overview = (AnchorPane) loader.load();
			// Set overview into the center of root layout.
			rootLayout.setCenter(Overview);
			// Give the controller access to the main app.
			OverviewController controller = loader.getController();
			controller.setMainApp(this);

			// Test Drag and Drop
			Overview.setOnDragOver(new EventHandler<DragEvent>() {

				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					if (db.hasFiles()) {
						event.acceptTransferModes(TransferMode.COPY);
					} else {
						event.consume();
					}
				}
			});
			// Test Dropping over surface
			Overview.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					boolean success = false;
					if (db.hasFiles()) {
						success = true;
						String filePath = null;
						for (File file : db.getFiles()) {
							filePath = file.getAbsolutePath();
							//RootLayoutController.FileObject.setUpShapeFilesForUpload(filePath.toString());
							OverviewController.showFileInView();
							OverviewController.showZipFileInView();
							String DirString = "";
							DirString = " " +filePath.toString();
							//OverviewController.DirectoryLabel.setText(DirString);
							//RootLayoutController.SelectedDirectory = filePath;
						}
					}
					event.setDropCompleted(success);
					event.consume();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Initializes the root layout.
	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	


	// Returns the main stage.
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}