using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Data.Entity.Validation;

namespace DriverAssistent_WebService.Models
{
    public class Company
    {
        [Key]
        public long id { get; set; }
        [Required]
        public string name { get; set; }
        public string address1 { get; set; }
        public string address2 { get; set; }
        public string city { get; set; }
        public string state { get; set; }
        public string zip { get; set; }
        public string phone { get; set; }
        public double rating { get; set; }
        public string openDays { get; set; }
        public string openHours { get; set; }

        public double lat { get; set; }
        public double lan { get; set; }

        public virtual List<Service> services { get; set; }

    }
}