namespace DriverAssistent_WebService.Migrations
{
    using DriverAssistent_WebService.Models;
    using DriverAssistent_WebService.Security;
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<DriverAssistent_WebService.Models.DBContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(DriverAssistent_WebService.Models.DBContext context)
        {

            /** ************************** COMPANY 1 DATA ************************** **/
            Company company1 = new Company();
            company1.name = "Cisco Systems, Inc.";
            company1.city = "San Jose";
            company1.address1 = "1234 Tasman Rd.";
            company1.address2 = "Apt. 234";
            company1.lan = -121.936092;
            company1.lat = 37.413147;

            Service s1 = new Service();
            s1.name = "Car network";
            s1.price = 54.22;
            s1.serviceType = ServiceType.SERVICESTATION;
            s1.specialDeal = false;

            Service s2 = new Service();
            s2.name = "Quick Stop Gas Station";
            s2.price = 3.22;
            s2.serviceType = ServiceType.GASSTATION;
            s2.specialDeal = true;

            //context.Services.AddOrUpdate(new Service[] {s1, s2 });

            if (company1.services == null)
            {
                company1.services = new List<Service>();
            }
            company1.services.Add(s1);
            company1.services.Add(s2);


            /** ************************** COMPANY DATA ************************** **/
            Company company2 = new Company();
            company2.name = "San Jose State University";
            company2.city = "San Jose";
            company2.address1 = "1234 North First Street.";
            company2.address2 = "";
            company2.lan = -121.882019;
            company2.lat = 37.338118;

            Service s21 = new Service();
            s21.name = "Education";
            s21.price = 540.22;
            s21.serviceType = ServiceType.RESTAURANT;
            s21.specialDeal = false;

            Service s22 = new Service();
            s22.name = "Quick Stop Gas Station";
            s22.price = 6.22;
            s22.serviceType = ServiceType.GASSTATION;
            s22.specialDeal = true;

            if (company2.services == null)
            {
                company2.services = new List<Service>();
            }

            context.Services.AddOrUpdate(new Service[] { s21, s22 });

            company2.services.Add(s21);
            company2.services.Add(s22);

            context.Companies.AddOrUpdate(new Company[] { company1, company2 });



            // INIT USERS with list of appointments
            context.Users.AddOrUpdate(new User[] {
                new User() {
                    userName = "maksim", 
                    name = "Maksim Ustinov", 
                    password = PasswordHash.CreateHash("password"),
                    role = new UserRole{Name="admin"},
                    appointments = new List<Appointment>{
                                                new Appointment(){
                                                    company = company1,
                                                    title = "Quick appointment at Cisco with PM",
                                                    startTime = new DateTime(2013, 12, 1, 15, 30, 0)
                                                }, new Appointment(){
                                                    company = company2,
                                                    title = "Clean up air filter",
                                                    startTime = new DateTime(2013, 12, 2, 15, 30, 0)
                                                }
                                    }
                },
                new User() {
                    userName = "john", 
                    name = "John Smith", 
                    password = PasswordHash.CreateHash("password"),
                    role = new UserRole{Name="user"},
                    appointments = new List<Appointment>{
                                                new Appointment(){
                                                    company = company1,
                                                    title = "Meeting with my co-worker",
                                                    startTime = new DateTime(2013, 12, 3, 15, 30, 0)
                                                }, new Appointment(){
                                                    company = company2,
                                                    title = "Need gas",
                                                    startTime = new DateTime(2013, 12, 4, 15, 30, 0)
                                                }
                                    }
                }
            });

        }
    }
}
