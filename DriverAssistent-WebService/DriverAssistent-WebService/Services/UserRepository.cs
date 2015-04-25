using DriverAssistent_WebService.Models;
using DriverAssistent_WebService.Security;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Services
{
    public class UserRepository
    {
        DBContext db;

        public UserRepository()
        {
            db = DBContextSingleton.Instance;
        }

        public User save(User u)
        {

            User savedUser = null;
            if (u.id == null && u.password == null)
            {
                u.password = PasswordHash.CreateHash(u.password); // Hashing password  
            }

            savedUser = db.Users.Add(u);
            db.SaveChanges();

            return savedUser;
        }



        public User find(long id)
        {
            User foundUser = db.Users.Find(id);

            return foundUser;
        }

        public Boolean remove(User u)
        {
            User removedUser = db.Users.Remove(u);

            return true;
        }


        public List<User> getAllUsers()
        {

            var query = from u in db.Users orderby u.id select u;
            //var query = from u in db.Users orderby u.Id select new User() { Id = u.Id, firstName = u.firstName, lastName = u.lastName, password = u.password, userName = u.userName };

            return query.ToList();
        }

        public User authenticateUser(string userName, string password)
        {

            var query = from u in db.Users where u.userName == userName select u;
            User user = query.Single();

            bool isValidPassword = PasswordHash.ValidatePassword(password, user.password);

            if (!isValidPassword)
            {
                throw new UnauthorizedAccessException("Not authorized. Please check your password");
            }
            
            return user;
        }

        public User addAppointment(long userId, Appointment a)
        {
            User dbUser = find(userId);
            if (dbUser.appointments == null)
            {
                dbUser.appointments = new List<Appointment>();
            }

            dbUser.appointments.Add(a);

            db.SaveChanges();

            return dbUser;
        }

        public List<UserRole> GetAllUserRoles()
        {
            var query = from ur in db.Roles orderby ur.id select ur;

            return query.ToList();
        }

        public UserRole getRole(long id)
        {
            UserRole foundUserRole = db.Roles.Find(id);

            return foundUserRole;
        }
    }
}