package com.pfizer.ecmapi.client.models;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FolderInfo")
public class FolderInfo {
	@XStreamAlias("ID")
    private String id;
	@XStreamAlias("Name")
	private String name;
	@XStreamAlias("ParentID")
	private String parentID;
	@XStreamAlias("Path")
	private String path;
	private List<FolderInfo> folderinfos = new ArrayList<FolderInfo>();
	private List<FileInfo> fileinfos = new ArrayList<FileInfo>();
	@XStreamAlias("Level")
    private int level;

    public FolderInfo() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<FolderInfo> getFolderinfos() {
		return folderinfos;
	}

	public void setFolderinfos(List<FolderInfo> folderinfos) {
		this.folderinfos = folderinfos;
	}

	public List<FileInfo> getFileinfos() {
		return fileinfos;
	}

	public void setFileinfos(List<FileInfo> fileinfos) {
		this.fileinfos = fileinfos;
	}

}
