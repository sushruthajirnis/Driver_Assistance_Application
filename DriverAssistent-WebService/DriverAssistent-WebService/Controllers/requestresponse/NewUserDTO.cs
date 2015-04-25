using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Controllers.requestresponse
{
    public class NewUserDTO
    {
        [Required]
        public string name { get; set; }
        [Required]
        public string userName { get; set; }
        [Required]
        public string plainPassword { get; set; }
        [Required]
        public virtual int roleId { get; set; }
    }
}