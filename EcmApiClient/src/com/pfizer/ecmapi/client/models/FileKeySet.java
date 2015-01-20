package com.pfizer.ecmapi.client.models;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FileKeySet")
public class FileKeySet {
	@XStreamAlias("Index")
    private int index;
	@XStreamAlias("KeyType")
	private String keyType;
	@XStreamAlias("Filter")
    private String filter;
    private List<FileKey> filekeys = new ArrayList<FileKey>();

    public FileKeySet() { }

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<FileKey> getFilekeys() {
		return filekeys;
	}

	public void setFilekeys(List<FileKey> filekeys) {
		this.filekeys = filekeys;
	}

}
