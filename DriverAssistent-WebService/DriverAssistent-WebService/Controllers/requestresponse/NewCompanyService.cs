using DriverAssistent_WebService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Controllers.requestresponse
{
    public class NewCompanyService
    {
        public long parentCompanyId { get; set; }
        public string name { get; set; }
        public bool specialDeal { get; set; }
        public double price { get; set; }
        public ServiceType serviceType { get; set; }
    }
}