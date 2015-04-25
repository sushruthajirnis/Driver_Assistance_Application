using DriverAssistent_WebService.Models;
using DriverAssistent_WebService.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace DriverAssistent_WebService.Controllers
{
    public class AppointmentService : ApiController
    {
        AppointmentRepository appointmentRepository;

        AppointmentService()
        {
            appointmentRepository = new AppointmentRepository();
        }


        public List<Appointment> Get(long userId)
        {
            List<Appointment> appointments = appointmentRepository.getAllUserAppointments(userId);

            return appointments;
        }


        public User Post(long userId, Appointment newAppointment)
        {
            User updatedUser = appointmentRepository.makeAnAppointment(userId, newAppointment);

            return updatedUser;
        }

    }
}