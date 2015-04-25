using DriverAssistent_WebService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Controllers.requestresponse
{
    public class NewAppointmentRequest
    {
        public long userId { get; set; }
        public string title { get; set; }
        public DateTime startTime { get; set; }
        public long companyId { get; set; }
    }
}