package com.gov.aims.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.gov.aims.model.FileHandlerWrapper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class OverviewController {

	//Labels for Overview.fxml
	@FXML
	private ListView<String> listViewFiles;
	@FXML
	private ListView<String> listViewZipFiles;
	@FXML
	private Label DirectoryLabel;	
	@FXML
	private Label DirectoryTitleLabel;	
	@FXML
	private Label ZipFileTitleLabel;
	@FXML
	private Label FileTitleLabel;
	
	public static List<String> fileNames = new ArrayList<String>();
	public static ObservableList<String> fileItems;
	public static List<String> ZipfileNames = new ArrayList<String>();
	public static ObservableList<String> ZipfileItems;
	
	//String variables
	public static String fileString;
	public static String zipString;
	// Reference to the main application.
	@SuppressWarnings("unused")
	private com.gov.aims.MainApp mainApp;

	public OverviewController() {
		
	}

	// Initializes the controller class. This method is automatically called
	// after the fxml file has been loaded.
	@FXML
	private void initialize() throws IOException {
        fileItems = listViewFiles.getItems();
        DirectoryLabel.setText(RootLayoutController.SelectedDirectory);
        ZipfileItems = listViewZipFiles.getItems();
        
	}

	// Is called by the main application to give a reference back to itself.
	public void setMainApp(com.gov.aims.MainApp mainApp) {
		this.mainApp = mainApp;
	}

	//Method for showing files found in directory and attaching
	//it to the labels in Overview.fxml
	public static void showFileInView() { 
		if ( FileHandlerWrapper.sortedFiles!= null) {
			for (List<File> FilesList : FileHandlerWrapper.sortedFiles) {				
				System.out.println(FilesList);
				for (File Files : FilesList){
					System.out.println("Files Test" + Files);
					System.out.println("Files Test2" + Files);
					//System.out.println("Test1");
				String split[]  = Files.toString().split("\\\\");
				int e = split.length - 1;
				fileNames.add(split[e]);
				System.out.println("Test2");
				}
			}
		}
		java.util.Collections.sort(fileNames);
		System.out.println("Test3");
		 for (String file : fileNames){
			 fileString = file;
			 fileItems.add(fileString);
		 }		
		fileNames.clear();
	}
	 
	//Method for showing zip files found in directory 
	public static void showZipFileInView() {
		if ( FileHandlerWrapper.files!= null) {
			for (File zipfiles : FileHandlerWrapper.files) {
				System.out.println(zipfiles);
				String split[]  = zipfiles.toString().split("\\\\");
				int e = split.length - 1;
				ZipfileNames.add(split[e]);				
			}
		}
		java.util.Collections.sort(ZipfileNames);
		System.out.println("Test3");
		 for (String file : ZipfileNames){
			 zipString = file;
			 ZipfileItems.add(zipString);
		 }	
		 ZipfileNames.clear();
	}
	
}