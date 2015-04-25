using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using System.Web.Security;
using Web_Client.Models;
using Web_Client.ReqResponse;


namespace Web_Client.Controllers
{
    [Authorize]
    public class AccountController : Controller
    {

        [AllowAnonymous]
        public ActionResult LogOn(string returnUrl)
        {
            ViewBag.ReturnUrl = returnUrl;
            return View();
        }

        [HttpPost]
        [AllowAnonymous]
        [ValidateAntiForgeryToken]
        public ActionResult LogOn(LogOnModel User)
        {
            if (Request.Params["submit"] != null)
            {

                if (Request.Params["submit"].Equals("True"))
                {
                    User u=RequestResp.MakeLoginRequest(RequestResp.CreateRequest("Users/GetUserByUserNameAndPassword?userName="+User.UserName+"&password="+User.Password));
                    if (u==null)
                        return RedirectToAction("RegisterNewUser", "Account");
                    Session["UserId"] = u.id;
                    Session["UserRole"] = u.role.Name;
                    Session["UserName"] = u.userName;
                    return RedirectToAction("Index", "Home");
                }


            }

            return View();
        }


        //
        // GET: /Account/Register
        [AllowAnonymous]
        public ActionResult RegisterNewUser()
        {
            return View();
        }

        [HttpPost]
        [AllowAnonymous]
        [ValidateAntiForgeryToken]
        public ActionResult RegisterNewUser(RegisterNewUserModel User)
        {
            if (Request.Params["submit"] != null)
            {

                if (Request.Params["submit"].Equals("True"))
                {
                    RegisterNewUser r = new RegisterNewUser();
                    r.Name = User.Name;
                    r.Password = User.Password;
                    r.RoleId = 2;
                    r.UserName = User.UserName;
                    
                    User u=RequestResp.RegisterNewUser(r,RequestResp.CreateRequest("Users/registerNewUser"));
                    if (u == null)
                        return View();

                    return RedirectToAction("LogOn", "Account");
                }


            }

            return View();
        }

        [HttpPost]
        [AllowAnonymous]
        [ValidateAntiForgeryToken]
        public void LogOff()
        {
            Session.Clear();
            Session.Abandon();
            Response.Redirect("LogOn", true);
        }


    }

    
}
