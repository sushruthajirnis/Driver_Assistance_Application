using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace DriverAssistent_WebService.Models
{


    /**
     * 
     * Steps to rebuild database when entity is changed:
     * 1. Open Tools -> Library Package Manager -> Package Manager Console
     * 2. Run in the console: Enable-Migrations
     * 3. Run in the console: Add-Migration ModelChange -Force
     * 4. Run in the console: Update-Database –Verbose
     * 
     * More info: http://msdn.microsoft.com/en-us/data/jj193542
     **/


    public class DBContext : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<UserRole> Roles { get; set; }
        public DbSet<Company> Companies { get; set; }
        public DbSet<Appointment> Appointments { get; set; }
        public DbSet<Service> Services { get; set; }
        // public DbSet<ServiceType> ServiceTypes { get; set; }
    }

    public class DBContextSingleton
    {
        private static DBContext dbContextInstance;

        private DBContextSingleton() { }

        public static DBContext Instance
        {
            get 
            {
                if (dbContextInstance == null)
                {
                dbContextInstance = new DBContext();
                }
                return dbContextInstance;
            }
        }

    }

}