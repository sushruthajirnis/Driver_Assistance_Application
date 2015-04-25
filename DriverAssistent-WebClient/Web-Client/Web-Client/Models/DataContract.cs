using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using System.ComponentModel.DataAnnotations;

namespace Web_Client.Models
{
     [DataContract]
    public class User
     {
         [DataMember(Name="id")]
         public long id { get; set; }
         [DataMember(Name = "name")]
         public string name { get; set; }
       [DataMember(Name = "userName")]
         public string userName { get; set; }
         [DataMember(Name = "password")]
         public string password { get; set; }
        [DataMember(Name = "role")]
         public virtual UserRole role { get; set; }
         [DataMember(Name = "appointments")]
         public virtual List<Appointment> appointments { get; set; }

    }
     [DataContract]
     public class Service
     {
          [DataMember(Name = "id")]
         public long id { get; set; }
          [DataMember(Name = "name")]
         public string name { get; set; }
         [DataMember(Name="specialDeal")]
          public bool specialDeal { get; set; }
         [DataMember(Name = "price")]
         public double price { get; set; }
         [DataMember(Name="serviceType")]
         public ServiceType serviceType { get; set; }

     }
    [DataContract]
     public class Company
     {
         [DataMember(Name = "id")]
         public long id { get; set; }

         [DataMember(Name = "name")]
          [Required]
         public string name { get; set; }
        [DataMember(Name = "address1")]
         public string address1 { get; set; }
        [DataMember(Name = "address2")]
         public string address2 { get; set; }
         [DataMember(Name = "city")]
         public string city { get; set; }
         [DataMember(Name = "state")]
         public string state { get; set; }
         [DataMember(Name = "zip")]
         public string zip { get; set; }
         [DataMember(Name = "phone")]
         public string phone { get; set; }
         [DataMember(Name = "rating")]
         public double rating { get; set; }
         [DataMember(Name = "openDays")]
         public string openDays { get; set; }
         [DataMember(Name = "openHours")]
         public string openHours { get; set; }
         [DataMember(Name = "lat")]
         public double lat { get; set; }
         [DataMember(Name = "lan")]
         public double lan { get; set; }
         [DataMember(Name = "services")]
         public virtual List<Service> services { get; set; }
     }

     [DataContract]
     public class Appointment
     {
         [DataMember(Name = "id")]
         public long id { get; set; }
         [DataMember(Name = "title")]
         public string title { get; set; }
         [DataMember(Name = "Company")]
         public virtual Company company { get; set; }
         [DataMember(Name = "startTime")]
         public string startTime { get; set; }

     }
    [DataContract]
     public class UserRole
     {
       [DataMember(Name = "id")]
         public long id { get; set; }
         [DataMember(Name = "Name")]
         public string Name { get; set; }
     }
    [DataContract]
     public class NewAppointmentRequest
     {
          [DataMember(Name = "userId")]
         public long userId { get; set; }
          [DataMember(Name = "title")]
         public string title { get; set; }
          [DataMember(Name = "startTime")]
            
         public DateTime startTime { get; set; }
          [DataMember(Name = "companyId")]
         public long companyId { get; set; }
     }

    [DataContract(Name = "NewUserDTO")]
    public class RegisterNewUser
    {

        [DataMember(Name = "name")]
        public string Name { get; set; }
        [DataMember(Name = "userName")]
        public string UserName { get; set; }
        [DataMember(Name = "plainPassword")]
        public string Password { get; set; }
        [DataMember(Name = "roleId")]
       public long RoleId { get; set; }
    }
     public enum ServiceType
     {
         GASSTATION,
         TOWING,
         TIREREPAIR,
         SERVICESTATION,
         RESTAURANT,
         HOTEL,
         HOSPITAL
     }
     
    
    
}