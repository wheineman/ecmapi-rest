using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace RestService
{
    [ServiceContract]
    public interface IEcmApi
    {
        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "samplesearchquery")]
        EcmapiRestService.Models.SearchQuery GetSampleSearchQuery();

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "version")]
        string GetVersion();

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "domainid")]
        string GetDomainId();

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "userid")]
        string GetUserId();

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "folderinfos")]
        List<EcmapiRestService.Models.FolderInfo> GetFolderInfos();

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "folderinfos/{name}?level={level}&like={like}")]
        List<EcmapiRestService.Models.FolderInfo> GetFolderInfosByName(string name, int level, string like);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "folderinfo/{id}")]
        EcmapiRestService.Models.FolderInfo GetFolderInfo(string id);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "folderinfo/{id}?includefiles={includefiles}")]
        EcmapiRestService.Models.FolderInfo GetFolderInfoEx(string id, string includefiles);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "fileinfo/{id}")]
        EcmapiRestService.Models.FileInfo GetFileInfo(string id);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "fileinfo/{id}?includedetails={includedetails}")]
        EcmapiRestService.Models.FileInfo GetFileInfoEx(string id, string includedetails);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "filelink/{id}?secure={secure}&expirydatetime={expirydatetime}")]
        string GetFileLink(string id, string secure, string expirydatetime);

        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "filelinks?secure={secure}&expirydatetime={expirydatetime}")]
        string[] GetFileLinks(string secure, string expirydatetime, string[] fileids);

        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "findfiles")]
        List<EcmapiRestService.Models.FileInfo> FindFiles(EcmapiRestService.Models.SearchQuery query);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "userdefinedkey/{id}/{key}")]
        EcmapiRestService.Models.UserDefinedKey GetUserDefinedKey(string id, string key);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "userdefinedkey/{id}")]
        void SetUserDefinedKey(string id, EcmapiRestService.Models.UserDefinedKey newUdk);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "userdefinedkeys/{id}")]
        List<EcmapiRestService.Models.UserDefinedKey> GetUserDefinedKeys(string id);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "userdefinedkeys/{id}")]
        void SetUserDefinedKeys(string id, EcmapiRestService.Models.UserDefinedKey[] newUdks);

        [OperationContract]
        [WebInvoke(Method = "GET",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "file/{id}")]
        Stream GetFile(string id);

        [OperationContract]
        [WebInvoke(Method = "POST",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "folder/{folderid}/{filename}?reason={reason}")]
        EcmapiRestService.Models.FileInfo PostFile(string folderid, string filename, string reason, Stream stream);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "deletefile/{id}?reason={reason}")]
        void DeleteFile(string id, string reason);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "activitylog")]
        void AddActivityLogEntry(EcmapiRestService.Models.ActivityLogEntry entry);

    }
}
