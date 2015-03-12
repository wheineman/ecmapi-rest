using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace EcmapiRestService.Models
{
    [DataContract(Namespace = "")]
    public class UserDefinedKeyDefault
    {
        public UserDefinedKeyDefault() { }

        public UserDefinedKeyDefault(Agilent.ECMAPI.UserDefinedKeyDefault userDefinedKeyDefault)
        {
            this.Key = userDefinedKeyDefault.Key;
            this.Value = userDefinedKeyDefault.Value;
            this.DataType = userDefinedKeyDefault.DataTypeAsString();
        }

        [DataMember(Order = 1)]
        public string Key { get; set; }
        [DataMember(Order = 2)]
        public string Value { get; set; }
        [DataMember(Order = 3)]
        public string DataType { get; set; }

    }
}