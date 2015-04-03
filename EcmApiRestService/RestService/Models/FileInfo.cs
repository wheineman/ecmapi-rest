using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Agilent.ECMAPI;

namespace EcmapiRestService.Models
{
    public class FileInfo
    {
        public bool CanHaveRevisions;
        public bool CanHaveSignatures;
        public string CheckoutLocation;
        public string CheckoutReason;
        public string CheckoutTime;
        public string CheckoutUser;
        public long CheckSum;
        public string Comment;
        public string CompoundUserID;
        public string COState;
        public string CreateDate;
        public string DisplayName;
        public string DocumentRetentionClass;
        public string DomainID;
        public long FileSize;
        public string FolderID;
        public string ID;
        public bool LatestRevision;
        public string LoggedInUserAtUpload;
        public long MaxRevisions;
        public string MD5Checksum;
        public string ModifiedDate;
        public long NumSignatures;
        public string OriginalName;
        public string OSOwnerAtUpload;
        public bool Permanent;
        public string ProcessingState;
        public bool ReadOnly;
        public long RevisionNumber;
        public string ShortDisplayName;
        public string SourceComputerName;
        public string SourceFolder;
        public string UploadDate;
        public string UploadReason;
        public string UploadUser;
        public List<FileKeySet> fileKeySets = new List<FileKeySet>();
        public List<UserDefinedKey> userDefinedKeys = new List<UserDefinedKey>();

        public FileInfo() { }

        public FileInfo(Agilent.ECMAPI.FileInfo fileInfo)
        {
            this.CanHaveRevisions = fileInfo.CanHaveRevisions;
            this.CanHaveSignatures = fileInfo.CanHaveSignatures;
            this.CheckoutLocation = fileInfo.CheckoutLocation;
            this.CheckoutReason = fileInfo.CheckoutReason;
            this.CheckoutTime = fileInfo.CheckoutTime;
            this.CheckoutUser = fileInfo.CheckoutUser;
            this.CheckSum = fileInfo.CheckSum;
            this.Comment = fileInfo.Comment;
            this.CompoundUserID = fileInfo.CompoundUserID;
            this.COState = fileInfo.COState.ToString();
            this.CreateDate = fileInfo.CreateDate;
            this.DisplayName = extractDisplayName(fileInfo.DisplayName);
            this.DocumentRetentionClass = fileInfo.DocumentRetentionClass;
            this.DomainID = fileInfo.DomainID;
            this.FileSize = fileInfo.FileSize;
            this.FolderID = fileInfo.FolderID.ToString();
            this.ID = fileInfo.ID.ToString();
            this.LatestRevision = fileInfo.LatestRevision;
            this.LoggedInUserAtUpload = fileInfo.LoggedInUserAtUpload;
            this.MaxRevisions = fileInfo.MaxRevisions;
            this.MD5Checksum = fileInfo.MD5Checksum;
            this.ModifiedDate = fileInfo.ModifiedDate;
            this.NumSignatures = fileInfo.NumSignatures;
            this.OriginalName = fileInfo.OriginalName;
            this.OSOwnerAtUpload = fileInfo.OSOwnerAtUpload;
            this.Permanent = fileInfo.Permanent;
            this.ProcessingState = fileInfo.ProcessingState;
            this.ReadOnly = fileInfo.ReadOnly;
            this.RevisionNumber = fileInfo.RevisionNumber;
            this.ShortDisplayName = fileInfo.ShortDisplayName;
            this.SourceComputerName = fileInfo.SourceComputerName;
            this.SourceFolder = fileInfo.SourceFolder;
            this.UploadDate = fileInfo.UploadDate;
            this.UploadReason = fileInfo.UploadReason;
            this.UploadUser = fileInfo.UploadUser;
        }

        public FileInfo(Agilent.ECMAPI.FileInfo fileInfo, bool includekeysets, bool includeudks)
            : this(fileInfo)
        {
            if (includekeysets)
            {
                Agilent.ECMAPI.FileKeySets ecmFileKeySets = fileInfo.GetFileKeySets();
                foreach (Agilent.ECMAPI.FileKeySet fileKeySet in ecmFileKeySets)
                {
                    fileKeySets.Add(new EcmapiRestService.Models.FileKeySet(fileKeySet));
                }
            }
            if (includeudks)
            {
                Agilent.ECMAPI.UserDefinedKeys ecmUserDefinedKeys = fileInfo.GetUserDefinedKeys();
                foreach (Agilent.ECMAPI.UserDefinedKey userDefinedKey in ecmUserDefinedKeys)
                {
                    userDefinedKeys.Add(new EcmapiRestService.Models.UserDefinedKey(userDefinedKey));
                }
            }
        }

        public static string extractDisplayName(string s)
        {
            string returnValue = s;
            if (s.Contains("|"))
            {
                string[] pieces = s.Split(new char[] {'|'});
                returnValue = pieces[pieces.Length - 1].Trim();
            }
            return returnValue;
        }
    }
}
