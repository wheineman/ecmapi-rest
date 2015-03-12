package com.pfizer.ecmapi.client;

import java.io.File;
import java.io.OutputStream;

import com.pfizer.ecmapi.client.models.ActivityLogEntry;
import com.pfizer.ecmapi.client.models.FileInfo;
import com.pfizer.ecmapi.client.models.FolderInfo;
import com.pfizer.ecmapi.client.models.SearchQuery;
import com.pfizer.ecmapi.client.models.UserDefinedKey;

public interface EcmApiInterface {
	
	public String getVersion() throws Exception;
	
	public String getDomainId() throws Exception;
	
	public String getUserId() throws Exception;
	
	public String getFileLink(String fileId, boolean secure, String expiryDateTime) throws Exception;

	public String[] getFileLinks(String[] fileIds, boolean secure, String expiryDateTime) throws Exception;
	
	public FolderInfo getFolderInfo(String folderId, Boolean includefiles) throws Exception;

	public FolderInfo[] getFolderInfos() throws Exception;
	
	public FolderInfo[] getFolderInfos(String name, Integer level, Boolean like) throws Exception;
	
	public FileInfo getFileInfo(String fileId, Boolean includedetails) throws Exception;
	
	public UserDefinedKey getUserDefinedKey(String fileId, String key) throws Exception;
	
	public void setUserDefinedKey(String fileId, UserDefinedKey udk) throws Exception;

	public UserDefinedKey[] getUserDefinedKeys(String fileId) throws Exception;
	
	public void setUserDefinedKeys(String fileId, UserDefinedKey udks[]) throws Exception;

	public FileInfo[] findFiles(SearchQuery searchQuery) throws Exception;
	
	public void getFile(String fileId, OutputStream out) throws Exception;
	
	public FileInfo postFile(String folderId, String filename, String reason, String mimeContentType, File f) throws Exception;
	
	public void deleteFile(String fileId, String reason) throws Exception;
	
	public void addActivityLogEntry(ActivityLogEntry entry) throws Exception;
	
}