using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using System.Runtime.Serialization.Json;
using Web_Client.ReqResponse;
using Web_Client.Models;

namespace Web_Client.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Message = "Modify this template to jump-start your ASP.NET MVC application.";


            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your app description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            var u = RequestResp.MakeUserRequest(RequestResp.CreateRequest("users"));
            return View(u);

        }
        
    }
}
