namespace DriverAssistent_WebService.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AddRoleAndDestinatin : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Destinations",
                c => new
                    {
                        DestinationId = c.Long(nullable: false, identity: true),
                        name = c.String(nullable: false),
                        arrivalTime = c.DateTime(nullable: false),
                        lat = c.Single(nullable: false),
                        lan = c.Single(nullable: false),
                        destinationCity = c.String(nullable: false),
                        User_Id = c.Long(),
                    })
                .PrimaryKey(t => t.DestinationId)
                .ForeignKey("dbo.Users", t => t.User_Id)
                .Index(t => t.User_Id);
            
            CreateTable(
                "dbo.UserRoles",
                c => new
                    {
                        RoleId = c.Long(nullable: false, identity: true),
                        Name = c.String(),
                    })
                .PrimaryKey(t => t.RoleId);
            
            AddColumn("dbo.Users", "role_RoleId", c => c.Long(nullable: false));
            AlterColumn("dbo.Users", "password", c => c.String(nullable: false));
            CreateIndex("dbo.Users", "role_RoleId");
            AddForeignKey("dbo.Users", "role_RoleId", "dbo.UserRoles", "RoleId", cascadeDelete: true);
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Users", "role_RoleId", "dbo.UserRoles");
            DropForeignKey("dbo.Destinations", "User_Id", "dbo.Users");
            DropIndex("dbo.Users", new[] { "role_RoleId" });
            DropIndex("dbo.Destinations", new[] { "User_Id" });
            AlterColumn("dbo.Users", "password", c => c.String());
            DropColumn("dbo.Users", "role_RoleId");
            DropTable("dbo.UserRoles");
            DropTable("dbo.Destinations");
        }
    }
}
