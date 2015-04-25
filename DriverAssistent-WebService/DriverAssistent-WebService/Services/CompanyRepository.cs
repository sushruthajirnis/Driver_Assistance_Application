using DriverAssistent_WebService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Services
{
    public class CompanyRepository
    {
        DBContext dbContext;
        ServiceRepository serviceRepository;

        public CompanyRepository()
        {
            dbContext = DBContextSingleton.Instance;
            serviceRepository = new ServiceRepository();
        }

        public Company find(long id)
        {
            Company c = dbContext.Companies.Find(id);

            return c;
        }

        public List<Company> findAll()
        {
            var query = from c in dbContext.Companies orderby c.id select c;

            return query.ToList();
        }

        public Company save(Company c)
        {
            Company savedCompany = c;
            if (c.id == 0)
            {
                savedCompany = dbContext.Companies.Add(c);
            }
            dbContext.SaveChanges();
            return savedCompany;
        }

        public Company findByService(long serviceId)
        {
         
            List<Company> companies = findAll();

            Company foundCompany = null;

            foreach(Company c in companies){
                foreach (Service s in c.services)
                {
                    if (s.id == serviceId)
                    {
                        foundCompany = c;
                        break;
                    }
                }
            }


            return foundCompany;
        }
    }
}