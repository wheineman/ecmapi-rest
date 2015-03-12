package com.pfizer.ecmapi.client.models;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FileInfo")
public class FileInfo {
	@XStreamAlias("COState")
    private String coState;
	@XStreamAlias("CanHaveRevisions")
    private boolean canHaveRevisions;
	@XStreamAlias("CanHaveSignatures")
    private boolean canHaveSignatures;
	@XStreamAlias("CheckSum")
    private long checkSum;
	@XStreamAlias("CheckoutLocation")
    private String checkoutLocation;
	@XStreamAlias("CheckoutReason")
    private String checkoutReason;
	@XStreamAlias("CheckoutTime")
    private String checkoutTime;
	@XStreamAlias("CheckoutUser")
    private String checkoutUser;
	@XStreamAlias("Comment")
    private String comment;
	@XStreamAlias("CompoundUserID")
    private String compoundUserID;
	@XStreamAlias("CreateDate")
    private String createDate;
	@XStreamAlias("DisplayName")
    private String displayName;
	@XStreamAlias("DocumentRetentionClass")
    private String documentRetentionClass;
	@XStreamAlias("DomainID")
    private String domainID;
	@XStreamAlias("FileSize")
    private long fileSize;
	@XStreamAlias("FolderID")
    private String folderID;
    @XStreamAlias("ID")
    private String id;
	@XStreamAlias("LatestRevision")
    private boolean latestRevision;
	@XStreamAlias("LoggedInUserAtUpload")
    private String loggedInUserAtUpload;
	@XStreamAlias("MD5Checksum")
    private String md5Checksum;
	@XStreamAlias("MaxRevisions")
    private long maxRevisions;
	@XStreamAlias("ModifiedDate")
    private String modifiedDate;
	@XStreamAlias("NumSignatures")
    private long numSignatures;
    @XStreamAlias("OSOwnerAtUpload")
    private String osOwnerAtUpload;
	@XStreamAlias("OriginalName")
    private String originalName;
	@XStreamAlias("Permanent")
    private boolean permanent;
	@XStreamAlias("ProcessingState")
    private String processingState;
	@XStreamAlias("ReadOnly")
    private boolean readOnly;
	@XStreamAlias("RevisionNumber")
    private long revisionNumber;
	@XStreamAlias("ShortDisplayName")
    private String shortDisplayName;
	@XStreamAlias("SourceComputerName")
    private String sourceComputerName;
	@XStreamAlias("SourceFolder")
    private String sourceFolder;
	@XStreamAlias("UploadDate")
    private String uploadDate;
	@XStreamAlias("UploadReason")
    private String uploadReason;
	@XStreamAlias("UploadUser")
    private String uploadUser;
    private List<FileKeySet> fileKeySets = new ArrayList<FileKeySet>();
    private List<UserDefinedKey> userDefinedKeys = new ArrayList<UserDefinedKey>();

    public FileInfo() { }

	public boolean getCanHaveRevisions() {
		return canHaveRevisions;
	}

	public void setCanHaveRevisions(boolean canHaveRevisions) {
		this.canHaveRevisions = canHaveRevisions;
	}

	public boolean getCanHaveSignatures() {
		return canHaveSignatures;
	}

	public void setCanHaveSignatures(boolean canHaveSignatures) {
		this.canHaveSignatures = canHaveSignatures;
	}

	public String getCheckoutLocation() {
		return checkoutLocation;
	}

	public void setCheckoutLocation(String checkoutLocation) {
		this.checkoutLocation = checkoutLocation;
	}

	public String getCheckoutReason() {
		return checkoutReason;
	}

	public void setCheckoutReason(String checkoutReason) {
		this.checkoutReason = checkoutReason;
	}

	public String getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getCheckoutUser() {
		return checkoutUser;
	}

	public void setCheckoutUser(String checkoutUser) {
		this.checkoutUser = checkoutUser;
	}

	public long getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(long checkSum) {
		this.checkSum = checkSum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCompoundUserID() {
		return compoundUserID;
	}

	public void setCompoundUserID(String compoundUserID) {
		this.compoundUserID = compoundUserID;
	}

	public String getCoState() {
		return coState;
	}

	public void setCoState(String coState) {
		this.coState = coState;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDocumentRetentionClass() {
		return documentRetentionClass;
	}

	public void setDocumentRetentionClass(String documentRetentionClass) {
		this.documentRetentionClass = documentRetentionClass;
	}

	public String getDomainID() {
		return domainID;
	}

	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFolderID() {
		return folderID;
	}

	public void setFolderID(String folderID) {
		this.folderID = folderID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getLatestRevision() {
		return latestRevision;
	}

	public void setLatestRevision(boolean latestRevision) {
		this.latestRevision = latestRevision;
	}

	public String getLoggedInUserAtUpload() {
		return loggedInUserAtUpload;
	}

	public void setLoggedInUserAtUpload(String loggedInUserAtUpload) {
		this.loggedInUserAtUpload = loggedInUserAtUpload;
	}

	public long getMaxRevisions() {
		return maxRevisions;
	}

	public void setMaxRevisions(long maxRevisions) {
		this.maxRevisions = maxRevisions;
	}

	public String getMd5Checksum() {
		return md5Checksum;
	}

	public void setMd5Checksum(String md5Checksum) {
		this.md5Checksum = md5Checksum;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getNumSignatures() {
		return numSignatures;
	}

	public void setNumSignatures(long numSignatures) {
		this.numSignatures = numSignatures;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOsOwnerAtUpload() {
		return osOwnerAtUpload;
	}

	public void setOsOwnerAtUpload(String osOwnerAtUpload) {
		this.osOwnerAtUpload = osOwnerAtUpload;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public String getProcessingState() {
		return processingState;
	}

	public void setProcessingState(String processingState) {
		this.processingState = processingState;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public long getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public String getShortDisplayName() {
		return shortDisplayName;
	}

	public void setShortDisplayName(String shortDisplayName) {
		this.shortDisplayName = shortDisplayName;
	}

	public String getSourceComputerName() {
		return sourceComputerName;
	}

	public void setSourceComputerName(String sourceComputerName) {
		this.sourceComputerName = sourceComputerName;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadReason() {
		return uploadReason;
	}

	public void setUploadReason(String uploadReason) {
		this.uploadReason = uploadReason;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public List<FileKeySet> getFileKeySets() {
		return fileKeySets;
	}

	public void setFileKeySets(List<FileKeySet> fileKeySets) {
		this.fileKeySets = fileKeySets;
	}

	public List<UserDefinedKey> getUserDefinedKeys() {
		return userDefinedKeys;
	}

	public void setUserDefinedKeys(List<UserDefinedKey> userDefinedKeys) {
		this.userDefinedKeys = userDefinedKeys;
	}

}
