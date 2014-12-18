package au.gov.aims.utilities;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryFinder {
	public static void fileFinder (String rootDirectory) throws IOException {
		FileVisitor<Path> fileProcessor = new ProcessFile();
		Files.walkFileTree(Paths.get(rootDirectory), fileProcessor);
	}
}