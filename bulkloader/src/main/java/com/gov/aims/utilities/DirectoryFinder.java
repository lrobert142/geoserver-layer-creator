package com.gov.aims.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class DirectoryFinder {
	
	//public static Path  File;
	public static Path dir ;
	public static ArrayList<Path> FilesList = new ArrayList<Path>();
	
	
	
	public static void fileFinder (String ROOT) throws IOException{
		System.out.println(ROOT);
		//String ROOT = FileChooser.FileChooserMethod().toString();
		
		//String ROOT = "C:\\Users\\zmcintos\\Documents\\test";
		FileVisitor<Path> fileProcessor = new ProcessFile();
		Files.walkFileTree(Paths.get(ROOT), fileProcessor);
	}
	
	
	//public static void main(String... aArgs) throws IOException {
	//}

	private static final class ProcessFile extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs)
				throws IOException {
			System.out.println("Processing file:" + aFile);
			//File = aFile;
			FilesList.add(aFile);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path aDir,
				BasicFileAttributes aAttrs) throws IOException {
			System.out.println("Processing directory:" + aDir);
			dir = aDir;
			return FileVisitResult.CONTINUE;
		}
	}
}
