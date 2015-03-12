package com.pfizer.ecmapi.client.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SearchQuery")
public class SearchQuery {
	@XStreamAlias("SearchType")
	private String searchType;
	@XStreamAlias("FolderId")
	private String folderId;
	@XStreamAlias("Query")
	private String query;
	@XStreamAlias("MaxNumberFiles")
	private int maxNumberFiles = -1;
	@XStreamAlias("StartingMetadataTableNumber")
	private int startingMetadataTableNumber = -1;
	@XStreamAlias("NumberOfMetadataTablesToSearch")
	private int numberOfMetadataTablesToSearch = -1;
	@XStreamAlias("StartDate")
	private String startDate;
	@XStreamAlias("EndDate")
	private String endDate;
	@XStreamAlias("IncludeFileDetails")
	private boolean includeFileDetails = true;
	
	
	public SearchQuery() {}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getMaxNumberFiles() {
		return maxNumberFiles;
	}

	public void setMaxNumberFiles(int maxNumberFiles) {
		this.maxNumberFiles = maxNumberFiles;
	}

	public int getStartingMetadataTableNumber() {
		return startingMetadataTableNumber;
	}

	public void setStartingMetadataTableNumber(int startingMetadataTableNumber) {
		this.startingMetadataTableNumber = startingMetadataTableNumber;
	}

	public int getNumberOfMetadataTablesToSearch() {
		return numberOfMetadataTablesToSearch;
	}

	public void setNumberOfMetadataTablesToSearch(int numberOfMetadataTablesToSearch) {
		this.numberOfMetadataTablesToSearch = numberOfMetadataTablesToSearch;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public boolean isIncludeFileDetails() {
		return includeFileDetails;
	}

	public void setIncludeFileDetails(boolean includeFileDetails) {
		this.includeFileDetails = includeFileDetails;
	}

}
