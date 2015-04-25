        using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Models
{
    public class Service
    {
        [Key]
        public long id { get; set; }
        [Required]
        public string name { get; set; }
        public bool specialDeal { get; set; }
        public double price { get; set; }
        public ServiceType serviceType { get; set; }
    }
}