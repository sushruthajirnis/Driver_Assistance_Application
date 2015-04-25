using DriverAssistent_WebService.Controllers.requestresponse;
using DriverAssistent_WebService.Models;
using DriverAssistent_WebService.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace DriverAssistent_WebService.Controllers
{
    public class ServiceController : ApiController
    {
        ServiceRepository serviceRepository;
        CompanyRepository companyRepository;

        public ServiceController()
        {
            serviceRepository = new ServiceRepository();
            companyRepository = new CompanyRepository();

        }

        public Service Get(long userId)
        {
            Service service = serviceRepository.find(userId);

            return service;

        }

        public Service Post(Service service)
        {
            Service savedService = serviceRepository.save(service);

            return savedService;
        }

        public List<Service> GetAll()
        {
            List<Service> allServices = serviceRepository.getAllServices();
            return allServices;
        }

        // Get Service by Type
        public List<Service> GetByType(ServiceType serviceType)

        {
            List<Service> services = serviceRepository.getByType(serviceType);
            return services;
        }

        [HttpPost]
        public Service AddNewCompanyService(long companyId, Service newCompanyService)
        {

            Company c = companyRepository.find(companyId);

            c.services.Add(newCompanyService);

            Company savedCompany = companyRepository.save(c);


            return newCompanyService;
        }


    }
}