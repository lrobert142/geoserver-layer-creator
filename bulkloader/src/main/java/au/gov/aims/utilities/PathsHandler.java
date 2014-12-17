/**
@author Justin Osbaldiston
@version 1.0.0
@since 3/12/14
*/

package au.gov.aims.utilities;

/**
 * Handles paths, and converting to and from absolute and relative paths
 */
public class PathsHandler {
	/**
	 * Converts absolute file paths containing '\' characters and converts them to '/' characters
	 * @param path - Absolute path for a file to be converted.
	 * @return The converted string.
	 */
	public static String backslashToForwardslash(String path) {
		StringBuilder builder = new StringBuilder();
		char[] pathArray = path.toCharArray();
		
		for (int j = 0; j < pathArray.length; j++) {
			if(pathArray[j] == '\\')
				pathArray[j] = '/';
			builder.append(pathArray[j]);
		}
		return builder.toString();
	}
	
	/**
	 * Gets the relative path from an absolute path, based on the relative directory passed in
	 * @param absolutePath - the absolute path to to calculate the relative path from
	 * @param relativeDirectory - the relative directory to calculate the relative path from
	 * @return String - the relative path
	 */
	public static String absoluteToRelativePath(String absolutePath, String relativeDirectory) {
		return absolutePath.substring(relativeDirectory.length() + 1, absolutePath.length());
	}
	
	/**
	 * Gets the relative path from a given path, based on the last slash character in the target path
	 * @param targetPath - the path to create the relative path from
	 * @return String - the relative path
	 */
	public static String getBasePath(String targetPath) {
		int lastIndex = targetPath.lastIndexOf("\\") == -1 ? targetPath.lastIndexOf("/") : targetPath.lastIndexOf("\\");
		return targetPath.substring(0, lastIndex);
	}
	
	/**
	 * Creates an absolute path based on a base path and a relative path
	 * <b>NOTE:</b>This function will not work if the base path ends with a slash and the relative path starts with a slash
	 * It also won't work if the base path doesn't end with a slash and the relative path doesn't start with a slash
	 * Either the base path should start with a slash and the relative path doesn't start with a slash, or vice versa
	 * @param basePath - the base path to start the absolute path with
	 * @param relativePath - the relative path to end the absolute path with
	 * @return String - the absolute path based on the base and relative paths
	 */
	public static String relativePathToAbsolutePath(String basePath, String relativePath) {
		return basePath + relativePath;
	}
}
