using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Agilent.ECMAPI;

namespace EcmapiRestService.Models
{
    public class FolderInfo
    {
        public string Name;
        public string ID;
        public string Path;
        public string ParentID;
        public int Level;
        public List<EcmapiRestService.Models.FolderInfo> folderinfos = new List<EcmapiRestService.Models.FolderInfo>();
        public List<EcmapiRestService.Models.FileInfo> fileinfos = new List<EcmapiRestService.Models.FileInfo>();

        public FolderInfo() { }

        public FolderInfo(ECM ecm, Agilent.ECMAPI.FolderInfo folderInfo, bool includeFiles)
            : this(ecm, folderInfo)
        {
            if (includeFiles)
            {
                Agilent.ECMAPI.FileInfos fileInfos = folderInfo.GetFileInfos();
                foreach (Agilent.ECMAPI.FileInfo fileInfo in fileInfos)
                {
                    fileinfos.Add(new EcmapiRestService.Models.FileInfo(fileInfo));
                }
            }
        }

        public FolderInfo(ECM ecm, Agilent.ECMAPI.FolderInfo folderInfo)
        {
            this.Name = folderInfo.Name;
            this.ID = folderInfo.ID.ToString();
            this.Path = folderInfo.Path;
            this.Path = ecm.GetFullPathFromID(folderInfo.ID);
            this.ParentID = folderInfo.ParentID.ToString();
            this.Level = folderInfo.GetFolderLevel();
            foreach (Agilent.ECMAPI.FolderInfo childFolderInfo in folderInfo.GetFolderInfos())
            {
                folderinfos.Add(new EcmapiRestService.Models.FolderInfo(ecm, childFolderInfo));
            }
        }
    }
}