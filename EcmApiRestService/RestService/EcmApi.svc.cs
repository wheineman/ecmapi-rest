﻿using Agilent.ECMAPI;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Reflection;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Web;
using System.Web.Extensions;

namespace RestService
{
    public class EcmApi : IEcmApi
    {
        private static string version = FileVersionInfo.GetVersionInfo(Assembly.GetExecutingAssembly().Location).FileVersion;
        private static int DELAY_BEFORE_DELETING_FOLDERS = 30000;
        private static readonly object syncLock = new object();

        #region IEcmapi Members

        public EcmapiRestService.Models.SearchQuery GetSampleSearchQuery()
        {
            EcmapiRestService.Models.SearchQuery returnValue = new EcmapiRestService.Models.SearchQuery();
            returnValue.SearchType = "AdvancedSearch";
            returnValue.FolderId = "{E8842EB1-576B-4D45-B02A-8AA14F8A8F7B}";
            returnValue.Query = "[File Information.General Information.File Name] contains \"example*\"";
            returnValue.MaxNumberFiles = -1;
            returnValue.NumberOfMetadataTablesToSearch = -1;
            returnValue.StartingMetadataTableNumber = -1;
            returnValue.IncludeFileDetails = false;
            return returnValue;
        }

        public string GetVersion()
        {
            return version;
        }

        public Stream GetAllMetaAttributes(bool includesynonyms)
        {
            Stream returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        string value = null;
                        value = ecm.GetAllMetaAttributesAsXML(includesynonyms);
                        WebOperationContext.Current.OutgoingResponse.ContentType = "text/xml";
                        UnicodeEncoding uniEncoding = new UnicodeEncoding();
                        returnValue = new MemoryStream(uniEncoding.GetBytes(value));
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public string GetDomainId()
        {
            string returnValue = null;
            ECMConnection ecmConnection = Login();
            try
            {
                if (ecmConnection.Connected)
                {
                    returnValue = ecmConnection.DomainID;
                }
            }
            finally
            {
                Logout(ecmConnection);
            }
            return returnValue;
        }

        public string GetDomainName(string id)
        {
            string returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        string serverURL = "http://" + Environment.MachineName;
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        returnValue = ecm.GetDomainName(serverURL, ecmObjectID);
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public string GetUserId()
        {
            string returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        returnValue = ecmConnection.UserID;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public List<EcmapiRestService.Models.FolderInfo> GetFolderInfos()
        {
            List<EcmapiRestService.Models.FolderInfo> returnValue = new List<EcmapiRestService.Models.FolderInfo>();
            bool isConnected = true;
            ECMConnection ecmConnection = null;
            lock (syncLock)
            {
                ecmConnection = Login();
                isConnected = ecmConnection.Connected;
                try
                {
                    if (isConnected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        FolderInfos folderInfos = new FolderInfos();
                        folderInfos = ecm.GetFolderInfos();
                        folderInfos.GetDetails();
                        foreach (FolderInfo folderInfo in folderInfos)
                        {
                            EcmapiRestService.Models.FolderInfo ecmFolderInfo = new EcmapiRestService.Models.FolderInfo(ecm, folderInfo);
                            returnValue.Add(ecmFolderInfo);
                        }
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public List<EcmapiRestService.Models.FolderInfo> GetFolderInfosQuick(string id, int levelsdeep)
        {
            List<EcmapiRestService.Models.FolderInfo> returnValue = new List<EcmapiRestService.Models.FolderInfo>();
            bool isConnected = true;
            ECMConnection ecmConnection = null;
            lock (syncLock)
            {
                ecmConnection = Login();
                isConnected = ecmConnection.Connected;
                try
                {
                    if (isConnected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        FolderInfos folderInfos = new FolderInfos();
                        if (id == null)
                        {
                            folderInfos = ecm.GetFolderInfos();
                            foreach (FolderInfo folderInfo in folderInfos)
                            {
                                EcmapiRestService.Models.FolderInfo ecmFolderInfo = new EcmapiRestService.Models.FolderInfo(ecm, folderInfo, levelsdeep);
                                returnValue.Add(ecmFolderInfo);
                            }
                        }
                        else
                        {
                            EcmapiRestService.Models.FolderInfo ecmFolderInfo = this.GetFolderInfoQuickEx(ecm, id, levelsdeep);
                            returnValue.Add(ecmFolderInfo);
                        }
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public List<EcmapiRestService.Models.FolderInfo> GetFolderInfosByName(string name, int level, string like)
        {
            List<EcmapiRestService.Models.FolderInfo> returnValue = new List<EcmapiRestService.Models.FolderInfo>();
            bool isConnected = true;
            ECMConnection ecmConnection = null;
            lock (syncLock)
            {
                ecmConnection = Login();
                isConnected = ecmConnection.Connected;
                try
                {
                    if (isConnected)
                    {
                        bool isLike = false;
                        if (like != null)
                        {
                            isLike = bool.Parse(like);
                        }
                        ECM ecm = new ECM(ecmConnection);
                        FolderInfos folderInfos = new FolderInfos();
                        folderInfos = ecm.GetFoldersByName(level, name, isLike);
                        folderInfos.GetDetails();
                        foreach (FolderInfo folderInfo in folderInfos)
                        {
                            EcmapiRestService.Models.FolderInfo ecmFolderInfo = new EcmapiRestService.Models.FolderInfo(ecm, folderInfo);
                            returnValue.Add(ecmFolderInfo);
                        }
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public EcmapiRestService.Models.FolderInfo GetFolderInfo(string id)
        {
            return GetFolderInfoEx(id, "false");
        }

        public EcmapiRestService.Models.FolderInfo GetFolderInfoQuick(string id, int levelsDeep)
        {
            EcmapiRestService.Models.FolderInfo returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        returnValue = GetFolderInfoQuickEx(ecm, id, levelsDeep);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The ECM object type is not valid for this call"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public EcmapiRestService.Models.FolderInfo GetFolderInfoQuickEx(ECM ecm, string id, int levelsDeep)
        {
            EcmapiRestService.Models.FolderInfo returnValue = null;
            FolderInfo folderInfo = new FolderInfo(ecm);
            ECMObjectID ecmObjectID = new ECMObjectID(id);
            folderInfo.ID = ecmObjectID;
            returnValue = new EcmapiRestService.Models.FolderInfo(ecm, folderInfo, levelsDeep);
            return returnValue;
        }

        public EcmapiRestService.Models.FolderInfo GetFolderInfoEx(string id, string includefiles)
        {
            EcmapiRestService.Models.FolderInfo returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        FolderInfo folderInfo = new FolderInfo(ecm);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        folderInfo.ID = ecmObjectID;
                        folderInfo.GetDetails();
                        returnValue = new EcmapiRestService.Models.FolderInfo(ecm, folderInfo, bool.Parse(includefiles));
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The ECM object type is not valid for this call"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            } return returnValue;
        }

        public EcmapiRestService.Models.Ancestor GetFolderAncestry(string id)
        {
            EcmapiRestService.Models.Ancestor returnValue = new EcmapiRestService.Models.Ancestor();
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        returnValue = GetFolderAncestryEx(ecm, id);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The ECM object type is not valid for this call"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
                return returnValue;
            }
        }

        private EcmapiRestService.Models.Ancestor GetFolderAncestryEx(ECM ecm, string id)
        {
            EcmapiRestService.Models.Ancestor returnValue = new EcmapiRestService.Models.Ancestor();
            FolderInfo folderInfo = new FolderInfo(ecm);
            ECMObjectID ecmObjectID = new ECMObjectID(id);
            folderInfo.ID = ecmObjectID;
            folderInfo.GetDetails();
            returnValue.ID = id;
            returnValue.Name = folderInfo.Name;
            returnValue.Path = ecm.GetFullPathFromID(folderInfo.ID);
            if ((folderInfo.ParentID != null) && (folderInfo.ParentID.ToString().Length > 0))
            {
                returnValue.Parent = GetFolderAncestryEx(ecm, folderInfo.ParentID.ToString());
            }
            return returnValue;
        }

        public List<EcmapiRestService.Models.UserDefinedKeyDefault> GetFolderUserDefinedKeyDefaults(string id)
        {
            List<EcmapiRestService.Models.UserDefinedKeyDefault> returnValue = new List<EcmapiRestService.Models.UserDefinedKeyDefault>();
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        FolderInfo folderInfo = new FolderInfo(ecm);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        folderInfo.ID = ecmObjectID;
                        UserDefinedKeyDefaults userDefinedKeyDefaults = null;
                        folderInfo.GetDetails();
                        userDefinedKeyDefaults = folderInfo.GetUserDefinedKeyDefaults();
                        foreach (Agilent.ECMAPI.UserDefinedKeyDefault udk in userDefinedKeyDefaults)
                        {
                            returnValue.Add(new EcmapiRestService.Models.UserDefinedKeyDefault(udk));
                        }
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public EcmapiRestService.Models.FolderInfo AddFolder(string parentFolderId, string name)
        {
            EcmapiRestService.Models.FolderInfo returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        ECMObjectID ecmParentFolderID = null;
                        if (parentFolderId != null)
                        {
                            ecmParentFolderID = new ECMObjectID(parentFolderId);
                        }
                        FolderInfo folderInfo = null;
                        folderInfo = ecm.AddFolder(ecmParentFolderID, name);
                        returnValue = new EcmapiRestService.Models.FolderInfo(ecm, folderInfo);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The ECM object type is not valid for this call"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public EcmapiRestService.Models.FolderInfo AddFolderToRoot(string name)
        {
            return AddFolder(null, name);
        }

        public void DeleteFolder(string id, string reason, string deep)
        {
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        ECMObjectID ecmFolderID = new ECMObjectID(id);
                        bool isDeep = false;
                        if ((deep != null) && (deep.Length > 0))
                        {
                            isDeep = bool.Parse(deep);
                        }
                        enumDeleteFolderMode deleteFolderMode = isDeep ? enumDeleteFolderMode.Deep : enumDeleteFolderMode.Shallow;
                        ecm.DeleteFolder(ecmFolderID, reason, deleteFolderMode);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The ECM object type is not valid for this call"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
        }

        public EcmapiRestService.Models.FileInfo GetFileInfo(string id)
        {
            return GetFileInfoEx(id, "false");
        }

        public EcmapiRestService.Models.FileInfo GetFileInfoEx(string id, string includedetails)
        {
            EcmapiRestService.Models.FileInfo returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        Agilent.ECMAPI.FileInfo fileInfo = new Agilent.ECMAPI.FileInfo(ecm);
                        bool includekeysets = bool.Parse(includedetails);
                        bool includeudks = bool.Parse(includedetails);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        fileInfo.ID = ecmObjectID;
                        fileInfo.GetDetails();
                        returnValue = new EcmapiRestService.Models.FileInfo(fileInfo, includekeysets, includeudks);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public string GetFileLink(string id, string secure, string expirydatetime)
        {
            string returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        bool isSecure = false;
                        if (secure != null)
                        {
                            isSecure = bool.Parse(secure);
                        }
                        bool placeInWindowsClipboard = false;
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        returnValue = ecm.GetFileLink(ecmObjectID, isSecure, expirydatetime, placeInWindowsClipboard);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public string[] GetFileLinks(string secure, string expirydatetime, string[] fileids)
        {
            string[] returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        bool isSecure = false;
                        if (secure != null)
                        {
                            isSecure = bool.Parse(secure);
                        }
                        bool placeInWindowsClipboard = false;
                        ECMObjectID[] ecmObjectIDs = new ECMObjectID[fileids.Length];
                        int index = 0;
                        foreach (string id in fileids)
                        {
                            ECMObjectID ecmObjectID = new ECMObjectID(id);
                            ecmObjectIDs[index] = ecmObjectID;
                            index += 1;
                        }
                        returnValue = ecm.GetFilesLinks(ecmObjectIDs, isSecure, expirydatetime, placeInWindowsClipboard);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public List<EcmapiRestService.Models.FileInfo> FindFiles(EcmapiRestService.Models.SearchQuery query)
        {
            List<EcmapiRestService.Models.FileInfo> returnValue = new List<EcmapiRestService.Models.FileInfo>();
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        // Create a FileFinder
                        FileFinder fileFinder = new FileFinder(ecm);
                        // Set the search parameters:
                        fileFinder.GetCount = true;
                        fileFinder.MetatableNumber = query.StartingMetadataTableNumber;
                        if ((query.StartDate != null) && (query.StartDate.Length > 0))
                        {
                            fileFinder.StartDate = query.StartDate;
                        }
                        if ((query.EndDate != null) && (query.EndDate.Length > 0))
                        {
                            fileFinder.EndDate = query.EndDate;
                        }
                        // Search for all files in the folder or below.
                        if (query.FolderId != null)
                        {
                            ECMObjectID ecmFolderID = new ECMObjectID(query.FolderId);
                            fileFinder.FolderID = ecmFolderID;
                        }
                        fileFinder.SrchType = evalSearchType(query.SearchType);
                        fileFinder.SearchExpression = query.Query;
                        // Run the search synchronously
                        fileFinder.Asyncronous = false;

                        int metatableCount = 0;
                        while ((fileFinder.MetatableNumber <= fileFinder.LastMetatableNumber)
                            && ((query.NumberOfMetadataTablesToSearch == -1) || (metatableCount < query.NumberOfMetadataTablesToSearch))
                            && ((query.MaxNumberFiles == -1) || (returnValue.Count < query.MaxNumberFiles)))
                        {
                            fileFinder.DoSearch();
                            // Process the results
                            FileInfos fileInfos = fileFinder.FileResults;
                            if (fileInfos != null && fileInfos.Count > 0)
                            {
                                foreach (Agilent.ECMAPI.FileInfo fileInfo in fileInfos)
                                {
                                    if (query.IncludeFileDetails)
                                    {
                                        fileInfo.GetDetails();
                                    }
                                    if ((query.MaxNumberFiles == -1) || (returnValue.Count < query.MaxNumberFiles))
                                    {
                                        returnValue.Add(new EcmapiRestService.Models.FileInfo(fileInfo));
                                    }
                                }
                            }
                            fileFinder.MetatableNumber += 1;
                            metatableCount += 1;
                        }
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public List<EcmapiRestService.Models.FileKeySet> GetFileKeySets(string id)
        {
            List<EcmapiRestService.Models.FileKeySet> returnValue = new List<EcmapiRestService.Models.FileKeySet>();
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        Agilent.ECMAPI.FileInfo fileInfo = new Agilent.ECMAPI.FileInfo(ecm);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        fileInfo.ID = ecmObjectID;
                        FileKeySets fileKeySets = null;
                        fileInfo.GetDetails();
                        fileKeySets = fileInfo.GetFileKeySets();
                        foreach (Agilent.ECMAPI.FileKeySet fks in fileKeySets)
                        {
                            returnValue.Add(new EcmapiRestService.Models.FileKeySet(fks));
                        }
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public EcmapiRestService.Models.UserDefinedKey GetUserDefinedKey(string id, string key)
        {
            EcmapiRestService.Models.UserDefinedKey returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        Agilent.ECMAPI.FileInfo fileInfo = new Agilent.ECMAPI.FileInfo(ecm);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        fileInfo.ID = ecmObjectID;
                        UserDefinedKeys userDefinedKeys = null;
                        fileInfo.GetDetails();
                        userDefinedKeys = fileInfo.GetUserDefinedKeys();
                        foreach (Agilent.ECMAPI.UserDefinedKey udk in userDefinedKeys)
                        {
                            if (udk.Key.Equals(key))
                            {
                                returnValue = new EcmapiRestService.Models.UserDefinedKey(udk);
                                break;
                            }
                        }
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public void SetUserDefinedKey(string id, EcmapiRestService.Models.UserDefinedKey newUdk)
        {
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        Agilent.ECMAPI.FileInfo fileInfo = new Agilent.ECMAPI.FileInfo(ecm);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        fileInfo.ID = ecmObjectID;
                        UserDefinedKeys userDefinedKeys = null;
                        fileInfo.GetDetails();
                        userDefinedKeys = fileInfo.GetUserDefinedKeys();
                        setOrAddUserDefinedKeys(ecm, userDefinedKeys, newUdk);
                        fileInfo.GetUserDefinedKeys().SaveToECM(ecmObjectID);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
        }

        public List<EcmapiRestService.Models.UserDefinedKey> GetUserDefinedKeys(string id)
        {
            List<EcmapiRestService.Models.UserDefinedKey> returnValue = new List<EcmapiRestService.Models.UserDefinedKey>();
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        Agilent.ECMAPI.FileInfo fileInfo = new Agilent.ECMAPI.FileInfo(ecm);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        fileInfo.ID = ecmObjectID;
                        UserDefinedKeys userDefinedKeys = null;
                        fileInfo.GetDetails();
                        userDefinedKeys = fileInfo.GetUserDefinedKeys();
                        foreach (Agilent.ECMAPI.UserDefinedKey udk in userDefinedKeys)
                        {
                            returnValue.Add(new EcmapiRestService.Models.UserDefinedKey(udk));
                        }
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public void SetUserDefinedKeys(string id, EcmapiRestService.Models.UserDefinedKey[] newUdks)
        {
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        Agilent.ECMAPI.FileInfo fileInfo = new Agilent.ECMAPI.FileInfo(ecm);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        fileInfo.ID = ecmObjectID;
                        UserDefinedKeys userDefinedKeys = null;
                        fileInfo.GetDetails();
                        userDefinedKeys = fileInfo.GetUserDefinedKeys();
                        foreach (EcmapiRestService.Models.UserDefinedKey newUdk in newUdks)
                        {
                            setOrAddUserDefinedKeys(ecm, userDefinedKeys, newUdk);
                        }
                        userDefinedKeys.SaveToECM(ecmObjectID);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
        }

        public Stream GetFile(string id)
        {
            Stream returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        string actualDownloadDirectoryPath = null;
                        if (actualDownloadDirectoryPath == null)
                        {
                            actualDownloadDirectoryPath = "C:\\temp";
                        }
                        ensureDirectoryExists(actualDownloadDirectoryPath);
                        Guid guid = Guid.NewGuid();
                        string uniqueSubFolderPath = actualDownloadDirectoryPath + "\\" + guid.ToString();
                        DirectoryInfo di = new DirectoryInfo(uniqueSubFolderPath);
                        di.Create();
                        try
                        {
                            Agilent.ECMAPI.FileInfo fileInfo = new Agilent.ECMAPI.FileInfo(ecm);
                            ECMObjectID ecmObjectID = new ECMObjectID(id);
                            fileInfo.ID = ecmObjectID;
                            fileInfo.GetDetails();
                            ecm.RetrieveFile(fileInfo.ID, uniqueSubFolderPath, false, true);
                            string filename = EcmapiRestService.Models.FileInfo.extractDisplayName(fileInfo.DisplayName);
                            string path = uniqueSubFolderPath + "\\" + filename;
                            string headerInfo = "attachment; filename=" + filename;
                            WebOperationContext.Current.OutgoingResponse.Headers["Content-Disposition"] = headerInfo;
                            WebOperationContext.Current.OutgoingResponse.ContentType = "application/octet-stream";
                            return File.OpenRead(path);
                        }
                        catch (ECMException e)
                        {
                            if (e.Message.Equals("The file was not found"))
                            {
                                WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                            }
                            else
                            {
                                throw e;
                            }
                        }
                        finally
                        {
                            Janitor janitor = new Janitor(uniqueSubFolderPath);
                            (new Thread(new ThreadStart(janitor.cleanupAfterGetFile))).Start();
                        }
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public EcmapiRestService.Models.FileInfo PostFile(string folderid, string filename, string reason, Stream stream)
        {
            EcmapiRestService.Models.FileInfo returnValue = null;
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        string actualUploadDirectoryPath = null;
                        if (actualUploadDirectoryPath == null)
                        {
                            actualUploadDirectoryPath = "C:\\temp";
                        }
                        ensureDirectoryExists(actualUploadDirectoryPath);

                        Guid guid = Guid.NewGuid();
                        string uniqueSubFolderPath = actualUploadDirectoryPath + "\\" + guid.ToString();
                        DirectoryInfo di = new DirectoryInfo(uniqueSubFolderPath);
                        di.Create();
                        try
                        {
                            String localFilePath = uniqueSubFolderPath + "\\" + filename;
                            Stream fileStream = File.Create(localFilePath);
                            try
                            {
                                byte[] buffer = new byte[32768];
                                int bytesRead, totalBytesRead = 0;
                                do
                                {
                                    bytesRead = stream.Read(buffer, 0, buffer.Length);
                                    fileStream.Write(buffer, 0, bytesRead);
                                    totalBytesRead += bytesRead;
                                } while (bytesRead > 0);
                            }
                            finally
                            {
                                fileStream.Close();
                            }
                            stream.Close();
                            ECMObjectID ecmFolderID = new ECMObjectID(folderid);
                            Agilent.ECMAPI.FileInfo fileInfo = null;
                            fileInfo = ecm.AddFileWithReason(localFilePath, ecmFolderID, reason);
                            returnValue = new EcmapiRestService.Models.FileInfo(fileInfo);
                        }
                        finally
                        {
                            di.Delete(true);
                        }
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
            return returnValue;
        }

        public void DeleteFile(string id, string reason)
        {
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        ECMObjectID ecmObjectID = new ECMObjectID(id);
                        ecm.DeleteFile(ecmObjectID, reason);
                    }
                }
                catch (ECMException e)
                {
                    if (e.Message.Equals("The file was not found"))
                    {
                        WebOperationContext.Current.OutgoingResponse.StatusCode = HttpStatusCode.NotFound;
                    }
                    else
                    {
                        throw e;
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
        }

        public string PostDeleteFile(string id, string reason)
        {
            DeleteFile(id, reason);
            return "ok";
        }

        public void AddActivityLogEntry(EcmapiRestService.Models.ActivityLogEntry entry)
        {
            lock (syncLock)
            {
                ECMConnection ecmConnection = Login();
                try
                {
                    if (ecmConnection.Connected)
                    {
                        ECM ecm = new ECM(ecmConnection);
                        ActivityLog activityLog = new ActivityLog();
                        activityLog.ECMInstance = ecm;
                        activityLog.AddEntry(
                            entry.Description,
                            entry.Reason,
                            evalLogActivityType(entry.ActivityType),
                            entry.ComputerName,
                            entry.Application,
                            entry.ObjectId,
                            evalLogObjectType(entry.ObjectType));
                    }
                }
                finally
                {
                    Logout(ecmConnection);
                }
            }
        }

        #endregion

        private ECMConnection Login()
        {
            ECMConnection ecmConnection = new ECMConnection();
            ECM ecm = new ECM(ecmConnection);
            string server = "http://" + Environment.MachineName;
            // --------------------------------------------------------------------
            // server = "http://amrndhw4159";      // Uncomment if debugging locally
            // --------------------------------------------------------------------
            string authorizationHeader = WebOperationContext.Current.IncomingRequest.Headers["Authorization"];
            string domain = null;
            string account = null;
            string username = null;
            string password = null;
            if (authorizationHeader != null)
            {
                try
                {
                    // Determine the beginning index of the Base64-encoded string in the Authorization header by finding the first space.
                    // Add 1 to the index so we can properly grab the substring.
                    int beginPasswordIndexPosition = authorizationHeader.IndexOf(' ') + 1;
                    string encodedAuth = authorizationHeader.Substring(beginPasswordIndexPosition);

                    // Decode the authentication credentials.
                    string decodedAuth = Encoding.UTF8.GetString(Convert.FromBase64String(encodedAuth));
                    // Split the credentials into the username and password portions on the colon character.
                    string[] splits = decodedAuth.Split(':');
                    if (splits.Length < 2)
                    {

                    }
                    string usercredential = splits[0];
                    password = splits[1].Trim();

                    string[] usercredSplits = usercredential.Split('|');
                    domain = usercredSplits[0].Trim();
                    // if the domain is prefixed with a domain like 'AMER\' remove it.
                    if (domain.Contains("\\"))
                    {
                        domain = domain.Substring(domain.IndexOf("\\") + 1);
                    }
                    account = usercredSplits[1].Trim();
                    username = usercredSplits[2].Trim();
                }
                catch (Exception e)
                {
                    WebOperationContext.Current.OutgoingResponse.StatusCode =
                        HttpStatusCode.Unauthorized;
                    return ecmConnection;
                }
            }
            else
            {
                WebOperationContext.Current.OutgoingResponse.StatusCode =
                    HttpStatusCode.Unauthorized;
                return ecmConnection;
            }
            string loginXML = "";
            loginXML += "<LoginInfo>";
            loginXML += "<ServerName>" + server + "</ServerName>";
            loginXML += "<DomainName>" + domain + "</DomainName>";
            loginXML += "<UserName>" + username + "</UserName>";
            loginXML += "<Password>" + password + "</Password>";
            loginXML += "<AccountName>" + account + "</AccountName>";
            loginXML += "<LoginType>P</LoginType>";
            loginXML += "</LoginInfo>";

            try
            {
                ecmConnection.Connect(loginXML);
            }
            catch (ECMException e)
            {
                WebOperationContext.Current.OutgoingResponse.StatusCode =
                    HttpStatusCode.Unauthorized;
                return ecmConnection;
            }
            return ecmConnection;
        }

        private void Logout(ECMConnection ecmConnection)
        {
            if (ecmConnection.Connected)
            {
                ecmConnection.Disconnect();
            }
        }

        private static string magicToken = "71B847E1-4CD7-4759-BA11-4256FE313EE3";

        private void setOrAddUserDefinedKeys(ECM ecm, UserDefinedKeys userDefinedKeys, EcmapiRestService.Models.UserDefinedKey newUdk)
        {
            bool found = false;
            foreach (Agilent.ECMAPI.UserDefinedKey udk in userDefinedKeys)
            {
                if (udk.Key.Equals(newUdk.Key))
                {
                    udk.Value = newUdk.Value;
                    udk.DataType = evalUdkDataType(newUdk.DataType);
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                UserDefinedKey item = new Agilent.ECMAPI.UserDefinedKey(ecm);
                item.CustomSet_Key(magicToken, newUdk.Key);
                item.CustomSet_DataType(magicToken, evalUdkDataType(newUdk.DataType));
                item.CustomSet_Value(magicToken, newUdk.Value);
                userDefinedKeys.Add(item);
            }
        }

        private string unescapeUri(string text)
        {
            return Regex.Replace(
                text,
                @"%[0-9a-f][0-9a-f]",
                m => ((char)Convert.ToByte(m.Value.Substring(1), 16)).ToString(),
                RegexOptions.IgnoreCase
            );
        }

        private void ensureDirectoryExists(string directoryPath)
        {
            DirectoryInfo di = new DirectoryInfo(directoryPath);
            if (!di.Exists)
            {
                di.Create();
            }
        }

        private enumSearchType evalSearchType(string searchType)
        {
            switch (searchType)
            {
                case "AdvancedSearch":
                    return enumSearchType.AdvancedSearch;
                case "QuickSearch":
                    return enumSearchType.QuickSearch;
                case "SmartSearch":
                    return enumSearchType.SmartSearch;
                default:
                    throw new InvalidOperationException(searchType);
            }
        }

        private enumUserDefinedKeyDataType evalUdkDataType(string dataType)
        {
            switch (dataType)
            {
                case "Datetime":
                    return enumUserDefinedKeyDataType.Datetime;
                case "Number":
                    return enumUserDefinedKeyDataType.Number;
                case "String":
                    return enumUserDefinedKeyDataType.String;
                case "Unknown":
                    return enumUserDefinedKeyDataType.Unknown;
                default:
                    throw new InvalidOperationException(dataType);
            }
        }

        private enumActivityLogType evalActivityLogType(string activityLogType)
        {
            switch (activityLogType)
            {
                case "AuditTrail":
                    return enumActivityLogType.AuditTrail;
                case "SystemLog":
                    return enumActivityLogType.SystemLog;
                default:
                    throw new InvalidOperationException(activityLogType);
            }
        }

        private enumLogActivityType evalLogActivityType(string logActivityType)
        {
            switch (logActivityType)
            {
                case "File":
                    return enumLogActivityType.File;
                case "Folder":
                    return enumLogActivityType.Folder;
                case "Instrument":
                    return enumLogActivityType.Instrument;
                case "Scheduler":
                    return enumLogActivityType.Scheduler;
                case "Storage":
                    return enumLogActivityType.Storage;
                case "System":
                    return enumLogActivityType.System;
                case "None":
                    return enumLogActivityType.None;
                default:
                    throw new InvalidOperationException(logActivityType);
            }
        }

        private enumLogObjectType evalLogObjectType(string logObjectType)
        {
            switch (logObjectType)
            {
                case "File":
                    return enumLogObjectType.File;
                case "Folder":
                    return enumLogObjectType.Folder;
                case "None":
                    return enumLogObjectType.None;
                default:
                    throw new InvalidOperationException(logObjectType);
            }
        }

        private class Janitor
        {
            private string directoryPath;

            public Janitor(string directoryPath)
            {
                this.directoryPath = directoryPath;
            }
            public void cleanupAfterGetFile()
            {
                DirectoryInfo di = new DirectoryInfo(directoryPath);
                do
                {
                    Thread.Sleep(DELAY_BEFORE_DELETING_FOLDERS);
                    try
                    {
                        di.Delete(true);
                    }
                    catch (IOException ex)
                    {
                        // Deliberately left empty
                    }
                    if (!di.Exists)
                    {
                        return;
                    }

                } while (true);
            }
        }
    }
}
