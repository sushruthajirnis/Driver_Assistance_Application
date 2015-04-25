using DriverAssistent_WebService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace DriverAssistent_WebService.Controllers
{
    public class ServiceTypeController : ApiController
    {

        public static string ConvertToJson(Type e)
        {
            var ret = "{\n";
            var enums = Enum.GetValues(e);
            int count = 1;

            foreach (var val in enums)
            {
                
                var name = Enum.GetName(e, val);

                if (enums.Length != count)
                {
                    ret += name + ":" + ((int)val).ToString() + ",\n";
                }
                else
                {
                    ret += name + ":" + ((int)val).ToString() + "\n";
                }

                count ++;
            }
            ret += "}";
            return ret;
        }

       

        public String GetAll()
        {
            return ConvertToJson(typeof(ServiceType));
            /*
            List<ServiceType> allServiceTypes = new List<ServiceType>();
            foreach (ServiceType serviceType in (ServiceType[])Enum.GetValues(typeof(ServiceType)))
            {
                allServiceTypes.Add(serviceType);
            }

            return allServiceTypes;
             */
        }

    }
}
