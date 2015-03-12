using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace EcmapiRestService.Models
{
    [DataContract(Namespace = "")]
    public class SearchQuery
    {
        [DataMember(Order = 1)]
        public string SearchType { get; set; }
        [DataMember(Order = 2)]
        public string FolderId { get; set; }
        [DataMember(Order = 3)]
        public string Query { get; set; }
        [DataMember(Order = 4)]
        public int MaxNumberFiles { get; set; }
        [DataMember(Order = 5)]
        public int StartingMetadataTableNumber { get; set; }
        [DataMember(Order = 6)]
        public int NumberOfMetadataTablesToSearch { get; set; }
        [DataMember(Order = 7)]
        public string StartDate { get; set; }       // For example: 2015-02-09 10:43:54
        [DataMember(Order = 8)]
        public string EndDate { get; set; }         // For example: 2015-02-09 17:00:00
        [DataMember(Order = 9)]
        public bool IncludeFileDetails { get; set; }
    }

}