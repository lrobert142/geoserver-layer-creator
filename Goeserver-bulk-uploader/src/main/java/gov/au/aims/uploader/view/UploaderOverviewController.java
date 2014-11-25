package gov.au.aims.uploader.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import gov.au.aims.uploader.MainApp;
import gov.au.aims.uploader.model.Employee;
import gov.au.aims.uploader.util.CSVManipulator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class UploaderOverviewController {
	@FXML
	private Label selectedDirectory;
	@FXML
	private Label fileList;
	@FXML
	private Label generatedFileLabel;

	@FXML
	private Button selectDirButton;
	@FXML
	private Button generateCSV;

	private MainApp mainApp;
	private List<String> filesLocations = new ArrayList<String>();

	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void handleFileSelection() {
		DirectoryChooser dChooser = new DirectoryChooser();
		dChooser.setTitle("CSV Files");
		File selectedDir = dChooser.showDialog(mainApp.getPrimaryStage());

		if (selectedDir != null) {
			selectedDirectory.textProperty().set(selectedDir.getAbsolutePath());
			fileList.textProperty().set("Loading...");

			try {
				filesLocations = getOtherFilesInDir(selectedDir
						.getAbsolutePath());
				String otherFilesDisplay = "";
				for (String otherFile : filesLocations) {
					if (otherFile.toString()
							.equals(selectedDirectory.getText())) {
						continue;
					}
					otherFilesDisplay += otherFile + "\n";
				}

				if (otherFilesDisplay == "") {
					fileList.textProperty().set("No other files...");
				} else {
					fileList.textProperty().set(otherFilesDisplay);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void handleSave() {
		CSVManipulator csvMan = new CSVManipulator();
		List<Employee> records = new ArrayList<Employee>();
		FileChooser fChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"CSV files (*.csv)", "*.csv");
		fChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".csv")) {
				file = new File(file.getPath() + ".csv");
			}

			try {
				generatedFileLabel.setText("Generating file...");
				for (String fileLocation : filesLocations) {
					csvMan.parseCSVToBeanList(fileLocation);
				}
				records = csvMan.getEmps();
				csvMan.writeCSVData(records, file);
				generatedFileLabel.setText("Success! File saved to: "
						+ file.toString());
			} catch (IOException e) {
				e.printStackTrace();
				generatedFileLabel
						.setText("An unexpected error occurred! File could not be saved to: "
								+ file.toString());
			}
		} else { // No file chosen
			generatedFileLabel.setText("No file generated...");
		}
	}

	private List<String> getOtherFilesInDir(String path) throws IOException {
		List<String> files = new ArrayList<String>();
		Files.walk(Paths.get(path)).forEach(
				filePath -> {
					if (Files.isRegularFile(filePath)
							&& filePath.toString().endsWith(".csv")) {
						files.add(filePath.toString());
					}
				});
		return files;
	}
}
