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
    public class AppointmentController : ApiController
    {
        AppointmentRepository appointmentRepository;
        CompanyRepository companyRepository;
        UserRepository userRepository;

        AppointmentController()
        {
            appointmentRepository = new AppointmentRepository();
            companyRepository = new CompanyRepository();
            userRepository = new UserRepository();
        }

        [HttpPost]
        public Appointment findAppointment(long appointmentId)
        {
            return appointmentRepository.find(appointmentId);
        }

        public List<Appointment> Get(long userId)
        {
            List<Appointment> appointments = appointmentRepository.getAllUserAppointments(userId);

            return appointments;
        }

        /// <summary>
        /// Create new Appointment
        /// </summary>
        /// <param name="request">Request json</param>
        [HttpPost]
        public User newAppointment(NewAppointmentRequest request)
        {
            Company c = companyRepository.find(request.companyId);
            Appointment appointment = new Appointment() { title=request.title, startTime=request.startTime, company = c };
            
            User updatedUser = userRepository.addAppointment(request.userId, appointment);

            return updatedUser;
        }




    }
}