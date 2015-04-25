using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Models
{
    public class User
    {
        [Key]
        public long id { get; set; }
        public string name { get; set; }
        [Required]
        public string userName { get; set; }
        [Required]
        public string password { get; set; }
        [Required]
        public virtual UserRole role { get; set; }
        public virtual List<Appointment> appointments { get; set; }
    }
}