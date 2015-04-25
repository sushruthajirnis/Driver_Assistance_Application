using DriverAssistent_WebService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using DriverAssistent_WebService.Services;
using DriverAssistent_WebService.Controllers.requestresponse;
using DriverAssistent_WebService.Security;

namespace DriverAssistent_WebService.Controllers
{
    public class UsersController : ApiController
    {
        UserRepository userRepository;

        public UsersController()
        {
            this.userRepository = new UserRepository(); 
        }

        
        /**
         * Returns all users in the database
         **/
        public List<User> Get()
        {
            return userRepository.getAllUsers();
        }

        /**
         * 
         * URL: GET [host]/api/users/1
         **/
        public User GetUser(long id)
        {
            User foundUser = userRepository.find(id);

            if (foundUser == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound); 
            }

            return foundUser;
        }

        /**
         * User registration/saving
         * */
        public User Post(User user)
        {
            User savedUser = userRepository.save(user);
            return savedUser;
        }

        /**
         **/
        public User GetUserByUserNameAndPassword(string userName, string password)
        {
            User foundUser = userRepository.authenticateUser(userName, password);

            if (foundUser == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }

            return foundUser;
        }

        [HttpPost]
        public User registerNewUser(NewUserDTO newUser)
        {
            UserRole role = userRepository.getRole(newUser.roleId);

            if (role == null)
            {
                HttpResponseMessage responseMessage = new HttpResponseMessage(HttpStatusCode.ExpectationFailed);
                responseMessage.ReasonPhrase = "!!!! No such role with id=" + newUser.roleId + " found !!!! There are following roles in the system:" + getAllRolesString();

                throw new HttpResponseException(responseMessage);
            }

            User u = new User();
            u.name = newUser.name;
            u.userName = newUser.userName;
            u.password = PasswordHash.CreateHash(newUser.plainPassword);
            u.role = role;

            User savedUser = userRepository.save(u);

            return savedUser;
        }

        public List<UserRole> GetAllUserRoles()
        {
            return userRepository.GetAllUserRoles();
        }

        private string getAllRolesString()
        {
            string roles = "";
            foreach (UserRole u in userRepository.GetAllUserRoles())
            {
                roles += "[" + u.Name + " (id=" + u.id + ")],";
            }

            return roles;
        }
    }
}
