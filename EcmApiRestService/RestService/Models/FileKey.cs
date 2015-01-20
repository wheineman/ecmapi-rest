using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Agilent.ECMAPI;

namespace EcmapiRestService.Models
{
    public class FileKey
    {
        public string Name;
        public string Value;
        public string DataType;
        public string KeyType;

        public FileKey() { }

        public FileKey(Agilent.ECMAPI.FileKey fileKey)
        {
            this.Name = fileKey.Name;
            this.Value = fileKey.Value;
            this.DataType = fileKey.DataTypeAsString();
            this.KeyType = fileKey.KeyType;
        }
    }
}