package com.pfizer.ecmapi.client.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FileKey")
public class FileKey {
	@XStreamAlias("Name")
    private String name;
	@XStreamAlias("Value")
    private String value;
	@XStreamAlias("DataType")
    private String dataType;
	@XStreamAlias("KeyType")
    private String keyType;

    public FileKey() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}


}
