using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Models
{
    public class Destination
    {
        [Key]
        public long DestinationId { get; set; }
        [Required]
        public string name { get; set; }
        public DateTime arrivalTime { get; set; }
        public float lat { get; set; }
        public float lan { get; set; }
        [Required]
        public string destinationCity { get; set; }

    }
}