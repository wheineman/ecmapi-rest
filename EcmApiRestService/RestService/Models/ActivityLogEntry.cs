using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace EcmapiRestService.Models
{
    [DataContract(Namespace = "")]
    public class ActivityLogEntry
    {
        [DataMember(Order = 1)]
        public string Description { get; set; }
        [DataMember(Order = 2)]
        public string Reason { get; set; }
        [DataMember(Order = 3)]
        public string ActivityType { get; set; }
        [DataMember(Order = 4)]
        public string ComputerName { get; set; }
        [DataMember(Order = 5)]
        public string Application { get; set; }
        [DataMember(Order = 6)]
        public string ObjectId { get; set; }
        [DataMember(Order = 7)]
        public string ObjectType { get; set; }
    }
}