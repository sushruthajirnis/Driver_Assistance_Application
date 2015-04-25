using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Web_Client.Models;
using Web_Client.ReqResponse;


namespace Web_Client.Controllers
{
    public class AdminController : Controller
    {
        //
        // GET: /Admin/

        public ActionResult Index()
        {
            return View();
        }
        public ActionResult ServiceHome(Service s)
        {
            s.id = 0;
            var c = "";
            if (Request.Params["Cid"] != null)
            {
                  c = Request.Params["Cid"];
            }
            else if (Request["cmpId"] != null)
            {
                 c = Request["cmpId"];
            }
            else
            {
                 c = "";
            }
            if (Request.Params["submit"] != null)
            {
                if (Request.Params["submit"].Equals("True"))
                {

                    Service saved = RequestResp.MakeServiceResponse(s, RequestResp.CreateRequest("Service/AddNewCompanyService?companyId=" + c));

                    return RedirectToAction("Index", "Home");
                }
            }


            return View();
        }

        public ActionResult CompanyHome(Company C)
        {
            if (C.name != null)
            {
                if (Request.Params["submit"] != null)
                {

                    if (Request.Params["submit"].Equals("True"))
                    {
                        C.services = new List<Service>();
                        Company comp = RequestResp.MakeCompanyResponse(C, RequestResp.CreateRequest("Company/Post"));

                        return RedirectToAction("ServiceHome", "Admin", new { Cid = comp.id });

                    }

                   
                }
                return View();
            }
            else if (Request["CID"] != null)
            {
                return RedirectToAction("ServiceHome", "Admin", new { CID = Request["CID"] });
            }
            else
            {
                return View();
            }
        }

        public ActionResult ViewAllCompanies()
        {
          List<Company> Comp = null;   
            if (Session["UserRole"] != null)
            {
                
            if(Session["UserRole"].Equals("admin")){
            Comp = RequestResp.MakeCompanyRequest(RequestResp.CreateRequest("Company/GetAll"));
            }
            }
            return View(Comp);
            
        }
    }
}
