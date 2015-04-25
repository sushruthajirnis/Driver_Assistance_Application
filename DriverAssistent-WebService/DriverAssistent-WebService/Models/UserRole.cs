using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Models
{
    public class UserRole
    {
        [Key]
        public long id { get; set; }
        public string Name { get; set; }
    }
}