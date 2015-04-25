using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

using Web_Client.Models;
using Web_Client.ReqResponse;




namespace Web_Client.Controllers
{
    public class UserController : Controller
    {
       
        //
        // GET: /User/
        public ActionResult Destination()
        {
           

            return View();
        }
        public ActionResult Search()
        {
            return View();
        }
        public ActionResult Service()
        {

            return View();
        }
        public ActionResult Company()
        {
            List<Company> comp = new List<Company>();
            string reg = RequestResp.CreateRequest("Company/GetAllforServiceType?serviceType=" + Request.Params["category"]);
            comp = RequestResp.MakeCompanyRequest(reg);
            ViewBag.address = "";
            return View(comp);
        }
        public ActionResult Appointment(NewAppointmentRequest nar)
        {
            long uid=0;
            long compid =0;
            if(Request["submit"]!=null)
                if(Request["submit"].Equals("True")){
            if (Session["UserId"] != null)  
            {
                 uid= Convert.ToInt64(Session["UserId"]);
            }
            if (Request["Cid"] != null)
            {
                 compid = Convert.ToInt64(Request["Cid"]);
            }
            nar.userId = uid;
            nar.companyId = compid;
            
          
            User u = RequestResp.MakeAppointmentResponse(nar, RequestResp.CreateRequest("Appointment/newAppointment"));
                    return RedirectToAction("Service","User");
                }
            return View();
        }
        public ActionResult ViewAllAppointment()
        {
            long uid = 0;   
            if (Session["UserId"] != null)
            {
                uid = Convert.ToInt64(Session["UserId"]);
            }
            List<Appointment> apps = RequestResp.MakeAppointmentRequest(RequestResp.CreateRequest("Appointment/Get?userId=" + uid));
            return View(apps);

        }
            
    }
}
