using DriverAssistent_WebService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Services
{
    public class ServiceRepository
    {
        DBContext db;

        public ServiceRepository()
        {
            db = DBContextSingleton.Instance;

        }

        public Service save(Service s)
        {
            Service savedService = db.Services.Add(s);
            db.SaveChanges();

            return savedService;
        }

        public Service find(long id)
        {
            Service foundService = db.Services.Find(id);

            return foundService;
        }

        public Boolean remove(Service s)
        {
            Service removedUser = db.Services.Remove(s);

            return true;
        }


        public List<Service> getAllServices()
        {
            var query = from s in db.Services orderby s.id select s;

            return query.ToList();
        }

        public List<Service> getByType(ServiceType serviceType)
        {
            var query = from s in db.Services 
                        where s.serviceType == serviceType
                        orderby s.id select s;

            return query.ToList();

        }
    }
}