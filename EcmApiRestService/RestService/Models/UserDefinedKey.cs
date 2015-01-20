using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace EcmapiRestService.Models
{
    [DataContract(Namespace = "")]
    public class UserDefinedKey
    {
        public UserDefinedKey() { }

        public UserDefinedKey(Agilent.ECMAPI.UserDefinedKey userDefinedKey)
        {
            this.Key = userDefinedKey.Key;
            this.Value = userDefinedKey.Value;
            this.DataType = userDefinedKey.DataTypeAsString();
        }

        [DataMember(Order = 1)]
        public string Key { get; set; }
        [DataMember(Order = 2)]
        public string Value { get; set; }
        [DataMember(Order = 3)]
        public string DataType { get; set; }

    }
}