package au.gov.aims.utilities;

public enum FilterType {
	STARTSWITH("startsWith"), CONTAINS("contains"), ENDSWITH("endsWith"), REGEX(
			"regex"), NOFILTER("none"), UNKNOWN(null);

	private final String filterName;
	
	private FilterType(String filterName) {
			this.filterName = filterName;
	}
	
	public static FilterType get(String filterName) {
			for (FilterType filter : values()) {
				if (filter == UNKNOWN)
					continue;
				if (filter.filterName.equals(filterName))
					return filter;
			}
			return UNKNOWN;
	}
}