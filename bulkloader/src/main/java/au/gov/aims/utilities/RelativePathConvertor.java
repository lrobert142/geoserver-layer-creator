/*
@author	Stuart Garrigan
@version 1.0.0
@since 11-12-2014
 */
package au.gov.aims.utilities;

public class RelativePathConvertor {

	
	
	public static String convertToRelativePath(String fileToConvert,
			String fileToConvertFrom) {
		StringBuilder relativePath = null;

		fileToConvert = fileToConvert.replaceAll("\\\\", "/");
		fileToConvertFrom = fileToConvertFrom.replaceAll("\\\\", "/");

		if (fileToConvert.equals(fileToConvertFrom) == true) {

		} else {
			String[] absoluteDirectories = fileToConvert.split("/");
			String[] relativeDirectories = fileToConvertFrom.split("/");

			// Get the shortest of the two paths
			int length = absoluteDirectories.length < relativeDirectories.length ? absoluteDirectories.length
					: relativeDirectories.length;

			// Use to determine where in the loop we exited
			int lastCommonRoot = -1;
			int index;

			// Find common root
			for (index = 0; index < length; index++) {
				if (absoluteDirectories[index]
						.equals(relativeDirectories[index])) {
					lastCommonRoot = index;
				} else {
					break;
					// If we didn't find a common prefix then throw
				}
			}
			if (lastCommonRoot != -1) {
				// Build up the relative path
				relativePath = new StringBuilder();
				// Add on the ..
				for (index = lastCommonRoot + 1; index < absoluteDirectories.length; index++) {
					if (absoluteDirectories[index].length() > 0) {
						relativePath.append("/");
					}
				}
				for (index = lastCommonRoot + 1; index < relativeDirectories.length - 1; index++) {
					relativePath.append(relativeDirectories[index] + "/");
				}
				relativePath
						.append(relativeDirectories[relativeDirectories.length - 1]);
			}
		}
		return relativePath == null ? null : relativePath.toString();
	}

}
