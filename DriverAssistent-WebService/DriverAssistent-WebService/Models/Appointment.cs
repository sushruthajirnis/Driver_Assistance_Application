using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Models
{
    public class Appointment
    {
        [Key]
        public long id { get; set; }
        public string title { get; set; }
        public virtual Company company { get; set; }
        public DateTime startTime { get; set; }

    }
}