package au.gov.aims.utilities;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class ProcessFile extends SimpleFileVisitor<Path> {
	
	public static Path dir ;
	public static ArrayList<Path> FilesList = new ArrayList<Path>();
	
	@Override
	public FileVisitResult visitFile(Path filePath, BasicFileAttributes fileAttributes) throws IOException {
		FilesList.add(filePath);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path directoryPath, BasicFileAttributes fileAttributes) throws IOException {
		dir = directoryPath;
		return FileVisitResult.CONTINUE;
	}
}