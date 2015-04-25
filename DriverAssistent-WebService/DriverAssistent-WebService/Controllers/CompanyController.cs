using DriverAssistent_WebService.Models;
using DriverAssistent_WebService.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace DriverAssistent_WebService.Controllers
{
    public class CompanyController : ApiController
    {
        CompanyRepository companyRepository;
        ServiceRepository serviceRepository;

        public CompanyController()
        {
            companyRepository = new CompanyRepository();
            serviceRepository = new ServiceRepository();
        }

        public Company Get(long companyId)
        {
            Company company = companyRepository.find(companyId);
            return company;
        }

        public Company Post(Company company)
        {
            Company savedCompany = companyRepository.save(company);
            return savedCompany;
        }

        public List<Company> GetAll()
        {
            List<Company> allCompanies = companyRepository.findAll();

            return allCompanies;
        }

        // Get Company for the Service
        public Company GetForServiceId(long serviceId)
        {
            Company company = companyRepository.findByService(serviceId);
            return company;
        }

        public List<Company> GetAllforServiceType(ServiceType serviceType)
        {
            List<Company> companies = new List<Company>();
            List<Service> servicesForType = serviceRepository.getByType(serviceType);

            foreach (Service s in servicesForType)
            {
                Company c = GetForServiceId(s.id);

                if(c != null){
                    companies.Add(c);
                }
            }

            return companies;
        }
    }
}