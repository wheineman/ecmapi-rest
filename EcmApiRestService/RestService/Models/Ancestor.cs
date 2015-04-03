using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace EcmapiRestService.Models
{
    [DataContract(Namespace = "")]
    public class Ancestor
    {
        [DataMember(Order = 1)]
        public string Name { get; set; }
        [DataMember(Order = 2)]
        public string ID { get; set; }
        [DataMember(Order = 3)]
        public string Path { get; set; }
        [DataMember(Order = 4)]
        public Ancestor Parent { get; set; }

        public Ancestor() {}

        public Ancestor(string id)
        {
            ID = id;
        }

        public Ancestor(string name, string id, string path, Ancestor parent)
        {
            Name = name;
            ID = id;
            Path = path;
            Parent = parent;
        }
    }

}