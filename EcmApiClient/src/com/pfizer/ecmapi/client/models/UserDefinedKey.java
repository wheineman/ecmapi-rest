package com.pfizer.ecmapi.client.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("UserDefinedKey")
public class UserDefinedKey {
	@XStreamAlias("Key")
    private String key;
	@XStreamAlias("Value")
    private String value;
	@XStreamAlias("DataType")
    private String dataType;

    public UserDefinedKey() { }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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


}
