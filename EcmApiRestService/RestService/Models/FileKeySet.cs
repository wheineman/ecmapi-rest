using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Agilent.ECMAPI;

namespace EcmapiRestService.Models
{
    public class FileKeySet
    {
        public int Index;
        public string KeyType;
        public string Filter;
        public List<EcmapiRestService.Models.FileKey> filekeys = new List<EcmapiRestService.Models.FileKey>();

        public FileKeySet() { }

        public FileKeySet(Agilent.ECMAPI.FileKeySet fileKeySet)
        {
            this.Index = fileKeySet.Index;
            this.KeyType = fileKeySet.KeyType;
            this.Filter = fileKeySet.Filter;
            foreach (Agilent.ECMAPI.FileKey fileKey in fileKeySet)
            {
                filekeys.Add(new EcmapiRestService.Models.FileKey(fileKey));
            }
        }
    }
}