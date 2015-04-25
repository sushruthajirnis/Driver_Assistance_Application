using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Runtime.Serialization.Json;
using System.Web;
using Web_Client.Models;

namespace Web_Client.ReqResponse
{
    public class RequestResp
    {
        public static string CreateRequest(string queryString)
        {
            string UrlRequest = "http://localhost:1058/api/" + queryString;
            return (UrlRequest);
        }

        public static List<User> MakeUserRequest(string queryString)
        {
            try
            {
                HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception(String.Format(
                        "Server error (HTTP {0}: {1}).",
                        response.StatusCode,
                        response.StatusDescription));
                    DataContractJsonSerializer jsonSerializer = new DataContractJsonSerializer(typeof(List<User>));
                    object objResponse = jsonSerializer.ReadObject(response.GetResponseStream());
                    List<User> jsonResponse
                    = objResponse as List<User>;
                    return jsonResponse;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }

        }
        public static User MakeLoginRequest(string queryString)
        {
            try
            {
                HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception(String.Format(
                        "Server error (HTTP {0}: {1}).",
                        response.StatusCode,
                        response.StatusDescription));
                    DataContractJsonSerializer jsonSerializer = new DataContractJsonSerializer(typeof(User));
                    object objResponse = jsonSerializer.ReadObject(response.GetResponseStream());
                    User jsonResponse
                    = objResponse as User;
                    return jsonResponse;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }

        }

        public static User RegisterNewUser(RegisterNewUser user, string queryString)
        {
            HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
            request.Method = "POST";
            request.MediaType = "application/json";
            request.Accept = "application/json";
            request.ContentType = "application/json";
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(RegisterNewUser));
            ser.WriteObject(request.GetRequestStream(), user);

            try
            {
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    DataContractJsonSerializer s = new DataContractJsonSerializer(typeof(User));
                    object objResponse = s.ReadObject(response.GetResponseStream());
                    User u = objResponse as User;
                    return u;
                }
            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.Data);
                return null;
            }
        }

        public static List<Service> MakeServiceRequest(string queryString)
        {
            try
            {
                HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception(String.Format(
                        "Server error (HTTP {0}: {1}).",
                        response.StatusCode,
                        response.StatusDescription));
                    DataContractJsonSerializer jsonSerializer = new DataContractJsonSerializer(typeof(List<Service>));
                    object objResponse = jsonSerializer.ReadObject(response.GetResponseStream());
                    List<Service> jsonResponse
                    = objResponse as List<Service>;
                    return jsonResponse;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        public static List<Appointment> MakeAppointmentRequest(string queryString)
        {
            try
            {
                HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception(String.Format(
                        "Server error (HTTP {0}: {1}).",
                        response.StatusCode,
                        response.StatusDescription));
                    DataContractJsonSerializer jsonSerializer = new DataContractJsonSerializer(typeof(List<Appointment>));
                    object objResponse = jsonSerializer.ReadObject(response.GetResponseStream());
                    List<Appointment> jsonResponse
                    = objResponse as List<Appointment>;
                    return jsonResponse;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }
        public static Company MakeSingleCompanyRequest(string queryString)
        {
            try
            {
                HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception(String.Format(
                        "Server error (HTTP {0}: {1}).",
                        response.StatusCode,
                        response.StatusDescription));
                    DataContractJsonSerializer jsonSerializer = new DataContractJsonSerializer(typeof(Company));
                    object objResponse;
                    Company jsonResponse = new Company();
                    if (response.GetResponseStream() != null)
                    {
                        objResponse = jsonSerializer.ReadObject(response.GetResponseStream());

                        jsonResponse = objResponse as Company;
                    }
                    return jsonResponse;


                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }
        public static List<Company> MakeCompanyRequest(string queryString)
        {
            try
            {
                HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception(String.Format(
                        "Server error (HTTP {0}: {1}).",
                        response.StatusCode,
                        response.StatusDescription));
                    DataContractJsonSerializer jsonSerializer = new DataContractJsonSerializer(typeof(List<Company>));
                    object objResponse;
                    List<Company> jsonResponse = new List<Company>();
                    if (response.GetResponseStream() != null)
                    {
                        objResponse = jsonSerializer.ReadObject(response.GetResponseStream());

                        jsonResponse = objResponse as List<Company>;
                    }
                    return jsonResponse;


                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        public static string MakeUserResponse(User user, string queryString)
        {
            HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
            request.Method = "POST";
            request.MediaType = "application/json";
            request.Accept = "application/json";
            request.ContentType = "application/json";
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(User));
            ser.WriteObject(request.GetRequestStream(), user);

            try
            {
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    object objResponse = ser.ReadObject(response.GetResponseStream());
                    User u = objResponse as User;
                    var c = response.GetResponseStream();
                    if (request.HaveResponse)
                        Console.WriteLine(response.ContentType);
                    else return "no response";
                }

                return "success";
            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.Data);
                return exp.Message;
            }
        }
        public static Company MakeCompanyResponse(Company company, string queryString)
        {
            HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
            request.Method = "POST";
            request.MediaType = "application/json";
            request.Accept = "application/json";
            request.ContentType = "application/json";
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Company));
            ser.WriteObject(request.GetRequestStream(), company);

            try
            {
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    object objResponse = ser.ReadObject(response.GetResponseStream());
                    Company u = objResponse as Company;
                    return u;

                }

                return null;
            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.Data);
                return null;
            }
        }

        public static Service MakeServiceResponse(Service service, string queryString)
        {
            HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
            request.Method = "POST";
            request.MediaType = "application/json";
            request.Accept = "application/json";
            request.ContentType = "application/json";
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Service));
            ser.WriteObject(request.GetRequestStream(), service);

            try
            {
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    object objResponse = ser.ReadObject(response.GetResponseStream());
                    Service s = objResponse as Service;
                    return s;

                }


            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.Data);
                return null;
            }
        }
        public static User MakeAppointmentResponse(NewAppointmentRequest nar, string queryString)
        {
            HttpWebRequest request = WebRequest.Create(queryString) as HttpWebRequest;
            request.Method = "POST";
            request.MediaType = "application/json";
            request.Accept = "application/json";
            request.ContentType = "application/json";
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(NewAppointmentRequest));
            
            ser.WriteObject(request.GetRequestStream(), nar);

            try
            {
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)

                {
                    DataContractJsonSerializer serv = new DataContractJsonSerializer(typeof(User));
                    object objResponse = serv.ReadObject(response.GetResponseStream());
                    User ur = objResponse as User;
                    return ur;

                }


            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.Data);
                return null;
            }
        }
    }

}