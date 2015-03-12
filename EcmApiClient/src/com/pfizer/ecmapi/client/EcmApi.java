package com.pfizer.ecmapi.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.pfizer.ecmapi.client.models.ActivityLogEntry;
import com.pfizer.ecmapi.client.models.FileInfo;
import com.pfizer.ecmapi.client.models.FileKey;
import com.pfizer.ecmapi.client.models.FileKeySet;
import com.pfizer.ecmapi.client.models.FolderInfo;
import com.pfizer.ecmapi.client.models.SearchQuery;
import com.pfizer.ecmapi.client.models.UserDefinedKey;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class EcmApi implements EcmApiInterface {
	private String host;
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    String encodedCredentials;

	public EcmApi(String host, String domain, String account, String username, String password) {
		this.host = host;
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(domain + "|" + account + "|" + username, password);
		String credentialsString =credentials.getUserName() + ":" + credentials.getPassword();
		encodedCredentials = "Basic " + new String(Base64.encodeBase64(credentialsString.toString().getBytes()));
        credsProvider.setCredentials(
                new AuthScope(host, 80),
                credentials);
	}
	
	public String getVersion() throws Exception {
		String returnValue = null;
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/version";
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					XStream xstream = new XStream(new StaxDriver());
					xstream.alias("string", String.class);
					returnValue = (String) xstream.fromXML(response.getEntity().getContent());
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public String getDomainId() throws Exception {
		String returnValue = null;
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/domainid";
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					XStream xstream = new XStream(new StaxDriver());
					xstream.alias("string", String.class);
					returnValue = (String) xstream.fromXML(response.getEntity().getContent());
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public String getUserId() throws Exception {
		String returnValue = null;
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/userid";
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					XStream xstream = new XStream(new StaxDriver());
					xstream.alias("string", String.class);
					returnValue = (String) xstream.fromXML(response.getEntity().getContent());
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public String getFileLink(String fileId, boolean secure,
			String expiryDateTime) throws ClientProtocolException, IOException, EcmApiClientException {
		String returnValue = null;
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/filelink/"
					+ URLEncoder.encode(fileId, "UTF-8") + "?secure="
					+ Boolean.toString(secure);
			if ((expiryDateTime != null) && (expiryDateTime.length() > 0)) {
				url += "&expirydatetime=" + expiryDateTime;
			}
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					try {
						returnValue = br.readLine();
					} finally {
						br.close();
					}
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public String[] getFileLinks(String[] fileIds, boolean secure,
			String expiryDateTime)  throws ClientProtocolException, IOException, EcmApiClientException {
		String[] returnValue = new String[0];
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/filelinks"
					+ "?secure="
					+ Boolean.toString(secure);
			if ((expiryDateTime != null) && (expiryDateTime.length() > 0)) {
				url += "&expirydatetime=" + expiryDateTime;
			}
			String postData = "<ArrayOfstring xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">";
			for (String fileId : fileIds) {
				postData += "<string>";
				postData += fileId;
				postData += "</string>";
			}
			postData += "</ArrayOfstring>";
			StringEntity postEntity = new StringEntity(postData);
			postEntity.setContentType("application/xml");
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Authorization", encodedCredentials);
			httpPost.setEntity(postEntity);
			System.out.println("Executing POST request "
					+ httpPost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					try {
						XStream xstream = new XStream(new StaxDriver());
						xstream.alias("ArrayOfstring", List.class);
						@SuppressWarnings("unchecked")
						List<String> links = (List<String>) xstream.fromXML(br);
						returnValue = links.toArray(new String[links.size()]);
					} finally {
						br.close();
					}
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public FolderInfo getFolderInfo(String folderId, Boolean includefiles) throws ClientProtocolException, IOException, EcmApiClientException {
		FolderInfo returnValue = new FolderInfo();
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/folderinfo/"
					+ URLEncoder.encode(folderId, "UTF-8");
			
			if (includefiles != null) {
				url += "?";
				url += "includefiles=" + includefiles.toString();
			}
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					try {
						XStream xstream = new XStream(new StaxDriver());
						xstream.alias("fileinfos", List.class);
						xstream.alias("folderinfos", List.class);
						xstream.alias("fileKeySets", List.class);
						xstream.alias("userDefinedKeys", List.class);
						xstream.alias("FolderInfo", FolderInfo.class);
						xstream.alias("FileInfo", FileInfo.class);
						xstream.alias("FileKeySet", FileKeySet.class);
						xstream.alias("FileKey", FileKey.class);
						xstream.alias("UserDefinedKey", UserDefinedKey.class);
						xstream.processAnnotations(FolderInfo.class);
						xstream.processAnnotations(FileInfo.class);
						xstream.processAnnotations(FileKeySet.class);
						xstream.processAnnotations(FileKey.class);
						xstream.processAnnotations(UserDefinedKey.class);
						returnValue = (FolderInfo) xstream.fromXML(br);
					} finally {
						br.close();
					}
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public FolderInfo[] getFolderInfos() throws ClientProtocolException, IOException, EcmApiClientException {
		FolderInfo[] returnValue = new FolderInfo[0];
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/folderinfos";
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					returnValue = getFolderInfoListFromStream(response.getEntity().getContent());
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public FolderInfo[] getFolderInfos(String name, Integer level,
			Boolean like) throws ClientProtocolException, IOException, EcmApiClientException {
		FolderInfo[] returnValue = new FolderInfo[0];
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/folderinfos/"
					+ URLEncoder.encode(name, "UTF-8");
			if (level == null) {
				level = -1;
			}
			url += "?";
			url += "level=" + level;
			if (like != null) {
				url += "&";
				url += "like=" + like.toString();
			}
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					returnValue = getFolderInfoListFromStream(response.getEntity().getContent());
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public FileInfo getFileInfo(String fileId,
			Boolean includedetails) throws ClientProtocolException, IOException, EcmApiClientException {
		FileInfo returnValue = new FileInfo();
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/fileinfo/"
					+ URLEncoder.encode(fileId, "UTF-8");
			
			if (includedetails != null) {
				url += "?";
				url += "includedetails=" + includedetails.toString();
			}
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					returnValue = getFileInfoFromStream(response.getEntity().getContent());
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public UserDefinedKey getUserDefinedKey(String fileId, String key) throws ClientProtocolException, IOException, EcmApiClientException {
		UserDefinedKey returnValue = new UserDefinedKey();
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/userdefinedkey/"
					+ URLEncoder.encode(fileId, "UTF-8")
					+ "/"
					+ URLEncoder.encode(key, "UTF-8");
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					try {
						XStream xstream = new XStream(new StaxDriver());
						xstream.alias("UserDefinedKey", UserDefinedKey.class);
						xstream.processAnnotations(UserDefinedKey.class);
						returnValue = (UserDefinedKey) xstream.fromXML(br);
					} finally {
						br.close();
					}
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public void setUserDefinedKey(String fileId, UserDefinedKey udk) throws ClientProtocolException, IOException, EcmApiClientException {
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
						+ "/EcmApi.svc"
						+ "/userdefinedkey/"
						+ URLEncoder.encode(fileId, "UTF-8");
			XStream xstream = new XStream(new StaxDriver());
			xstream.alias("UserDefinedKey", UserDefinedKey.class);
			xstream.processAnnotations(UserDefinedKey.class);
			String putData = xstream.toXML(udk);
			StringEntity putEntity = new StringEntity(putData);
			putEntity.setContentType("application/xml");
			HttpPut httpPut = new HttpPut(url);
			httpPut.setHeader("Authorization", encodedCredentials);
			httpPut.setEntity(putEntity);
			System.out.println("Executing PUT request "
					+ httpPut.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpPut);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} 
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
	}

	public UserDefinedKey[] getUserDefinedKeys(String fileId) throws Exception {
		UserDefinedKey[] returnValue = new UserDefinedKey[0];
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/userdefinedkeys/"
					+ URLEncoder.encode(fileId, "UTF-8");
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/xml");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					try {
						XStream xstream = new XStream(new StaxDriver());
						xstream.alias("ArrayOfUserDefinedKey", List.class);
						xstream.alias("UserDefinedKey", UserDefinedKey.class);
						xstream.processAnnotations(UserDefinedKey.class);
						@SuppressWarnings("unchecked")
						List<UserDefinedKey> udkList = (List<UserDefinedKey>) xstream.fromXML(br);
						returnValue = udkList.toArray(new UserDefinedKey[udkList.size()]);
					} finally {
						br.close();
					}
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public void setUserDefinedKeys(String fileId, UserDefinedKey[] udks) throws ClientProtocolException, IOException, EcmApiClientException {
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
						+ "/userdefinedkeys/"
						+ URLEncoder.encode(fileId, "UTF-8");
			XStream xstream = new XStream(new StaxDriver());
			xstream.alias("ArrayOfUserDefinedKey", UserDefinedKey[].class);
			xstream.alias("UserDefinedKey", UserDefinedKey.class);
			xstream.processAnnotations(UserDefinedKey.class);
			String putData = xstream.toXML(udks);
			StringEntity postEntity = new StringEntity(putData);
			postEntity.setContentType("application/xml");
			HttpPut httpPut = new HttpPut(url);
			httpPut.setHeader("Authorization", encodedCredentials);
			httpPut.setEntity(postEntity);
			System.out.println("Executing PUT request "
					+ httpPut.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpPut);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} 
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
	}

	public FileInfo[] findFiles(SearchQuery searchQuery) throws ClientProtocolException, IOException, EcmApiClientException {
		FileInfo[] returnValue = null;
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix() + "/findfiles";
			XStream xstream = new XStream(new StaxDriver());
			xstream.processAnnotations(SearchQuery.class);
			String postData = xstream.toXML(searchQuery);
			StringEntity postEntity = new StringEntity(postData);
			postEntity.setContentType("application/xml");
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Authorization", encodedCredentials);
			httpPost.setEntity(postEntity);
			System.out.println("Executing POST request "
					+ httpPost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					try {
						xstream.alias("ArrayOfFileInfo", List.class);
						xstream.alias("fileKeySets", List.class);
						xstream.alias("userDefinedKeys", List.class);
						xstream.alias("FileInfo", FileInfo.class);
						xstream.alias("FileKeySet", FileKeySet.class);
						xstream.alias("FileKey", FileKey.class);
						xstream.alias("UserDefinedKey", UserDefinedKey.class);
						xstream.processAnnotations(FileInfo.class);
						xstream.processAnnotations(FileKeySet.class);
						xstream.processAnnotations(FileKey.class);
						xstream.processAnnotations(UserDefinedKey.class);
						@SuppressWarnings("unchecked")
						List<FileInfo> fileInfoList = (List<FileInfo>) xstream.fromXML(br);
						returnValue = fileInfoList.toArray(new FileInfo[fileInfoList.size()]);
					} finally {
						br.close();
					}
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public void getFile(String fileId, OutputStream out) throws ClientProtocolException, IOException, EcmApiClientException {
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/file/"
					+ URLEncoder.encode(fileId, "UTF-8");
			
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Authorization", encodedCredentials);
			httpget.setHeader("Accept", "application/octet-stream");
			System.out.println("Executing GET request "
					+ httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
			        byte[] buffer = new byte[32768];
					InputStream in = null;
					in = response.getEntity().getContent();
					try {
						for (int length; (length = in.read(buffer)) > 0;) {
							out.write(buffer, 0, length);
			            }
					} finally {
			            if (out != null) try { out.close(); } catch (IOException logOrIgnore) {}
			            if (in != null) try { in.close(); } catch (IOException logOrIgnore) {}
					}
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
	}

	public FileInfo postFile(String folderId, String filename, String reason,
			String mimeContentType,	File f) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		FileInfo returnValue = new FileInfo();
		try {
			String url = getUrlPrefix()
					+ "/folder"
					+ "/"
					+ URLEncoder.encode(folderId, "UTF-8")
					+ "/"
					+ URLEncoder.encode(filename, "UTF-8")
					+ "?reason="
					+ URLEncoder.encode(reason, "UTF-8");
			HttpPost httpPost = new HttpPost(url);
			
			FileEntity reqEntity = new FileEntity(f);
			reqEntity.setChunked(true);
            httpPost.setEntity(reqEntity);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2229.0 Safari/537.36");
			httpPost.setHeader("Authorization", encodedCredentials);
			httpPost.setHeader("Content-Type", mimeContentType);
			httpPost.setHeader("Accept", "*/*");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "en-US,en;q=0.8");
			httpPost.setHeader("DNT", "1");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} else {
					returnValue = getFileInfoFromStream(response.getEntity().getContent());
				}
            } finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
            }
		} finally {
			httpclient.close();
		}
		return returnValue;
	}

	public void deleteFile(String fileId, String reason) throws ClientProtocolException, IOException, EcmApiClientException {
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/deletefile/"
					+ URLEncoder.encode(fileId, "UTF-8");
			if ((reason != null) && (reason.length() > 0)) {
				url += "?reason=" + URLEncoder.encode(reason, "UTF-8");
			}
			HttpDelete httpDelete = new HttpDelete(url);
			httpDelete.setHeader("Authorization", encodedCredentials);
			System.out.println("Executing DELETE request "
					+ httpDelete.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpDelete);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				}
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
	}
		
	public void addActivityLogEntry(ActivityLogEntry entry) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			String url = getUrlPrefix()
					+ "/activitylog";
			XStream xstream = new XStream(new StaxDriver());
			xstream.alias("ActivityLogEntry", ActivityLogEntry.class);
			xstream.processAnnotations(ActivityLogEntry.class);
			String putData = xstream.toXML(entry);
			StringEntity putEntity = new StringEntity(putData);
			putEntity.setContentType("application/xml");
			HttpPut httpPut = new HttpPut(url);
			httpPut.setHeader("Authorization", encodedCredentials);
			httpPut.setEntity(putEntity);
			System.out.println("Executing PUT request "
					+ httpPut.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpPut);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new EcmApiClientException("EcmApi Service returned: " + response.getStatusLine());
				} 
			} finally {
				try {
					response.close();
				} finally {
					EntityUtils.consume(response.getEntity());
				}
			}
		} finally {
			httpclient.close();
		}
	}

	private String getUrlPrefix() {
		String url = null;
		if (host.toLowerCase().contains("localhost")) {
			url = "http://" 
					+ host
					+ "/EcmApi.svc";
		} else {
			url = "http://" 
				+ host
				+ "/GainsEcmRestService"
				+ "/EcmApi.svc";
		}
		return url;
	}
	
	private FileInfo getFileInfoFromStream(InputStream in) throws IOException {
		FileInfo returnValue = new FileInfo();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			XStream xstream = new XStream(new StaxDriver());
			xstream.alias("fileKeySets", List.class);
			xstream.alias("userDefinedKeys", List.class);
			xstream.alias("FileInfo", FileInfo.class);
			xstream.alias("FileKeySet", FileKeySet.class);
			xstream.alias("FileKey", FileKey.class);
			xstream.alias("UserDefinedKey", UserDefinedKey.class);
			xstream.processAnnotations(FileInfo.class);
			xstream.processAnnotations(FileKeySet.class);
			xstream.processAnnotations(FileKey.class);
			xstream.processAnnotations(UserDefinedKey.class);
			returnValue = (FileInfo) xstream.fromXML(br);
		} finally {
			br.close();
		}
		return returnValue;
	}

	private FolderInfo[] getFolderInfoListFromStream(InputStream in) throws IOException {
		FolderInfo[] returnValue = new FolderInfo[0];
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			XStream xstream = new XStream(new StaxDriver());
			xstream.alias("ArrayOfFolderInfo", List.class);
			xstream.alias("fileinfos", List.class);
			xstream.alias("folderinfos", List.class);
			xstream.alias("fileKeySets", List.class);
			xstream.alias("userDefinedKeys", List.class);
			xstream.alias("FolderInfo", FileInfo.class);
			xstream.alias("FileInfo", FileInfo.class);
			xstream.alias("FileKeySet", FileKeySet.class);
			xstream.alias("FileKey", FileKey.class);
			xstream.alias("UserDefinedKey", UserDefinedKey.class);
			xstream.processAnnotations(FolderInfo.class);
			xstream.processAnnotations(FileInfo.class);
			xstream.processAnnotations(FileKeySet.class);
			xstream.processAnnotations(FileKey.class);
			xstream.processAnnotations(UserDefinedKey.class);
			@SuppressWarnings("unchecked")
			List<FolderInfo> folderInfoList = (List<FolderInfo>) xstream.fromXML(br);
			returnValue = folderInfoList.toArray(new FolderInfo[folderInfoList.size()]);
		} finally {
			br.close();
		}
		return returnValue;
	}

}
