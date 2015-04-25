using DriverAssistent_WebService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Services
{
    public class AppointmentRepository
    {
        DBContext db;
        UserRepository userRepository;

        public AppointmentRepository()
        {
            db = DBContextSingleton.Instance;
            userRepository = new UserRepository();
        }
        public Appointment save(Appointment a)
        {
            Appointment savedAppointment = db.Appointments.Add(a);


            db.SaveChanges();

            return savedAppointment;
        }

        public Appointment find(long id)
        {
            Appointment foundAppointment = db.Appointments.Find(id);

            return foundAppointment;
        }

        public Boolean remove(Appointment u)
        {
            Appointment removedAppointment = db.Appointments.Remove(u);

            return true;
        }




        /*
        public User makeAnAppointment(long userId, Appointment appointment)
        {
            User user = userRepository.find(userId);

            if (user.appointments == null)
            {
                user.appointments = new List<Appointment>();
            }

            user.appointments.Add(appointment);

            int numOfAptBefore = user.appointments.Count;
            User updatedUser = userRepository.save(user);

            return updatedUser;
        }
         */

        public List<Appointment> getAllUserAppointments(long userId)
        {
            User user = userRepository.find(userId);

            return user.appointments;
        }
    }
}