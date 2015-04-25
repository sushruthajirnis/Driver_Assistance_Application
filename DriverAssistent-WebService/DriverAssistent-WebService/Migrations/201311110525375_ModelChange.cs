namespace DriverAssistent_WebService.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ModelChange : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Appointments",
                c => new
                    {
                        id = c.Long(nullable: false, identity: true),
                        title = c.String(),
                        startTime = c.DateTime(nullable: false),
                        company_id = c.Long(),
                    })
                .PrimaryKey(t => t.id)
                .ForeignKey("dbo.Companies", t => t.company_id)
                .Index(t => t.company_id);
            
            CreateTable(
                "dbo.Companies",
                c => new
                    {
                        id = c.Long(nullable: false, identity: true),
                        name = c.String(nullable: false),
                        address1 = c.String(),
                        address2 = c.String(),
                        city = c.String(),
                        state = c.String(),
                        zip = c.String(),
                        phone = c.String(),
                        rating = c.Single(nullable: false),
                        openDays = c.String(),
                        openHours = c.String(),
                        lat = c.Single(nullable: false),
                        lan = c.Single(nullable: false),
                        destinationCity = c.String(nullable: false),
                        User_Id = c.Long(),
                    })
                .PrimaryKey(t => t.id)
                .ForeignKey("dbo.Users", t => t.User_Id)
                .Index(t => t.User_Id);
            
            CreateTable(
                "dbo.UserRoles",
                c => new
                    {
                        id = c.Long(nullable: false, identity: true),
                        Name = c.String(),
                    })
                .PrimaryKey(t => t.id);
            
            CreateTable(
                "dbo.Services",
                c => new
                    {
                        id = c.Long(nullable: false, identity: true),
                        name = c.String(nullable: false),
                        specialDeal = c.Boolean(nullable: false),
                        price = c.Single(nullable: false),
                        serviceType = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.id);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        Id = c.Long(nullable: false, identity: true),
                        name = c.String(),
                        userName = c.String(nullable: false),
                        password = c.String(nullable: false),
                        role_id = c.Long(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.UserRoles", t => t.role_id, cascadeDelete: true)
                .Index(t => t.role_id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Users", "role_id", "dbo.UserRoles");
            DropForeignKey("dbo.Companies", "User_Id", "dbo.Users");
            DropForeignKey("dbo.Appointments", "company_id", "dbo.Companies");
            DropIndex("dbo.Users", new[] { "role_id" });
            DropIndex("dbo.Companies", new[] { "User_Id" });
            DropIndex("dbo.Appointments", new[] { "company_id" });
            DropTable("dbo.Users");
            DropTable("dbo.Services");
            DropTable("dbo.UserRoles");
            DropTable("dbo.Companies");
            DropTable("dbo.Appointments");
        }
    }
}
