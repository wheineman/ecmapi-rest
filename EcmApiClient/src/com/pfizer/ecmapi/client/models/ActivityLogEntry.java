package com.pfizer.ecmapi.client.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ActivityLogEntry")
public class ActivityLogEntry {
	@XStreamAlias("Description")
	private String description;
	@XStreamAlias("Reason")
	private String reason;
	@XStreamAlias("ActivityType")
	private String activityType = "None";
	@XStreamAlias("ComputerName")
	private String computerName;
	@XStreamAlias("Application")
	private String application;
	@XStreamAlias("ObjectId")
	private String objectId;
	@XStreamAlias("ObjectType")
	private String objectType = "None";

	public ActivityLogEntry() {}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

}
