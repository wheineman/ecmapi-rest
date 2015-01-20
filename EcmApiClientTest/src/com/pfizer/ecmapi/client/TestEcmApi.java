package com.pfizer.ecmapi.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pfizer.ecmapi.client.models.ActivityLogEntry;
import com.pfizer.ecmapi.client.models.FileInfo;
import com.pfizer.ecmapi.client.models.FolderInfo;
import com.pfizer.ecmapi.client.models.SearchQuery;
import com.pfizer.ecmapi.client.models.UserDefinedKey;

public class TestEcmApi {

	private String server = "YOUR_HOST_SERVER";
	private String domain = "YOUR_DOMAIN";
	private String account = "YOUR_ACCOUNT";
	private String username = "YOUR_USERNAME";
	private String password = "YOUR_PASSWORD";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testVersion() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String response = ecmApi.getVersion();
		System.out.println(response);
		assertEquals("1.0", response);
	}
	
	@Test
	public void testGetDomainId() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String response = ecmApi.getDomainId();
		System.out.println(response);
		assertEquals("{59B4BF70-2E8D-48E0-A379-1A89424B0642}", response);
	}
	
	@Test
	public void testGetUserId() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String response = ecmApi.getUserId();
		System.out.println(response);
		assertEquals("BUILT-IN::gains_uploadagent::GAINS_UploadAgent::{615CBF53-2116-44D2-BF55-DB3B5A24250F}", response.substring(0, response.lastIndexOf("::")));
	}
	
	@Test
	public void testGetFileLink() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{B72B44B4-B539-4DCE-931E-FF3FC4ADB02F}";
		boolean secure = false;
		String expiryDateTime = null;
		String response = ecmApi.getFileLink(fileId, secure, expiryDateTime);
		System.out.println(response);
		assertEquals("<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">http://AMRNDHW4159/ECM/url.asp?SessID=1F7A8996B467B6B311768AD5EDC6DC79039F8E71B306B1E30E5CDEC0B47E73F8DBE8AA51E62E7B234</string>",
				response);
	}

	@Test
	public void testGetFileLinks() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{B72B44B4-B539-4DCE-931E-FF3FC4ADB02F}";
		String[] fileIds = new String[1];
		fileIds[0] = fileId;
		boolean secure = false;
		String expiryDateTime = null;
		String[] response = ecmApi.getFileLinks(fileIds, secure, expiryDateTime);
		assertTrue((response != null) && (response.length > 0));
		System.out.println(response);
//		assertEquals("<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">http://amrndhw4159/ECM/url.asp?SessID=1F7A8996B467B6B311768AD5EDC6DC79039F8E71B306B1E30E5CDEC0B47E73F8DBE8AA51E62E7B234</string>",
//				response);
	}	

	@Test
	public void testGetFolderInfo() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String folderId = "{E8842EB1-576B-4D45-B02A-8AA14F8A8F7B}";
		Boolean includefiles = true;
		FolderInfo response = ecmApi.getFolderInfo(folderId, includefiles);
		System.out.println(response);
	}

	@Test
	public void testGetFolderInfos() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		FolderInfo[] response = ecmApi.getFolderInfos();
		System.out.println(response);
	}

	@Test
	public void testGetFolderInfos2() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String name = "Masshunter";
		Integer level = null;
		Boolean like = null;
		FolderInfo[] response = ecmApi.getFolderInfos(name, level, like);
		System.out.println(response);
	}

	@Test
	public void testGetFileInfo() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{B72B44B4-B539-4DCE-931E-FF3FC4ADB02F}";
		Boolean includedetails = true;
		FileInfo response = ecmApi.getFileInfo(fileId, includedetails);
		System.out.println(response);
	}

	@Test
	public void testGetUserDefinedKey() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{B72B44B4-B539-4DCE-931E-FF3FC4ADB02F}";
		String key = "UDK-AN_Test";
		UserDefinedKey response = ecmApi.getUserDefinedKey(fileId, key);
		assertEquals("UDK-AN_Test", response.getKey());
		assertEquals("String", response.getDataType());
		assertEquals("This is a test", response.getValue());
		System.out.println(response);
	}
	
	@Test
	public void testSetUserDefinedKey() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{B72B44B4-B539-4DCE-931E-FF3FC4ADB02F}";
		String key = "UDK-AN_Test";
		String value = "This is a test";
		UserDefinedKey udk = new UserDefinedKey();
		udk.setKey(key);
		udk.setDataType("String");
		udk.setValue(value);
		ecmApi.setUserDefinedKey(fileId, udk);
		UserDefinedKey response = ecmApi.getUserDefinedKey(fileId, key);
		assertEquals(key, response.getKey());
		assertEquals("String", response.getDataType());
		assertEquals(value, response.getValue());
		System.out.println(response);
	}

	@Test
	public void testGetUserDefinedKeys() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{B72B44B4-B539-4DCE-931E-FF3FC4ADB02F}";
		UserDefinedKey[] response = ecmApi.getUserDefinedKeys(fileId);
		System.out.println(response);
	}
	
	@Test
	public void testFindFiles() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		SearchQuery searchQuery = new SearchQuery();
		searchQuery.setSearchType("AdvancedSearch");
		searchQuery.setFolderId("{E8842EB1-576B-4D45-B02A-8AA14F8A8F7B}");
		searchQuery.setQuery("[File Information.General Information.File Name] contains \"example*\"");
		FileInfo[] response = ecmApi.findFiles(searchQuery);
		System.out.println(response);
	}

	@Test
	public void testGetFile() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{B72B44B4-B539-4DCE-931E-FF3FC4ADB02F}";
		FileOutputStream out = new FileOutputStream("data/DownloadedFile.pdf");
		ecmApi.getFile(fileId, out);
	}
	
	@Test
	public void testPostFile() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String folderId = "{E8842EB1-576B-4D45-B02A-8AA14F8A8F7B}";
		String filename = "Number1Again.png";
		String reason = "testing";
		File f = new File("data/Number1Again.png");
		FileInfo fileInfo = ecmApi.postFile(folderId, filename, reason, "image/png", f);
		System.out.println(fileInfo);
	}
	
	@Test
	public void testDeleteFile() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		String fileId = "{582C3904-5C4B-42DC-A5DF-CC91B666DDD5}";
		ecmApi.deleteFile(fileId, "testing");
	}
	
	@Test
	public void testAddActivityLogEntry() throws Exception {
		EcmApi ecmApi = new EcmApi(server, domain, account, username, password);
		ActivityLogEntry entry = new ActivityLogEntry();
		entry.setActivityType("None");
		entry.setApplication("TextEcmApi");
		entry.setComputerName(System.getenv("COMPUTERNAME"));
		entry.setDescription("This is a test");
		entry.setReason("For the purpose of testing");
		ecmApi.addActivityLogEntry(entry);
	}
	
}
