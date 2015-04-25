package com.sjsu.utility;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;


public class UserFunctions {

    private JSONParser jsonParser;

    //URL of the API
    private String loginURL = "http://10.0.0.14:1058/api/Users/GetUserByUserNameAndPassword";
    private String registerURL = "http://10.0.0.14:1058/api/Users/registerNewUser";
    private String forpassURL = "http://10.0.2.2/login_api/";
    private String chgpassURL = "http://10.0.2.2/login_api/";
    private String makeAppointURL = "http://10.0.2.2/login_api/";
    private String getAppointURL = "http://10.0.2.2/login_api/";
    private String getSpecificAppointURL = "http://10.0.2.2/login_api/";
    private String createServiceURL = "http://10.0.2.2/login_api/";
    private String getServiceURL = "http://10.0.2.2/login_api/";
    private String updateServiceURL = "http://10.0.2.2/login_api/";
    private String deleteServiceURL = "http://10.0.2.2/login_api/";
    
    private static String login_tag = "users";
    private static String register_tag = "register";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";
    private static String makeAppoint_tag = "makeappoint";
    private static String getAppoint_tag = "getappoint";
    private static String getSpecificAppoint_tag = "getspecificappoint";
    private static String createService_tag = "createservice";
    private static String getService_tag = "createservice";
    private static String updateService_tag = "createservice";
    private static String deleteService_tag = "createservice";

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * Function to Login
     **/
    public JSONObject loginUser(String uname, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String myloginURL = loginURL+"?userName="+uname+"&password="+password;
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl("get",myloginURL, params);
        return json;
    }

    /**
     * Function to change password
     **/
    public JSONObject chgPass(String newpas, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", chgpass_tag));
        params.add(new BasicNameValuePair("newpas", newpas));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl("get",chgpassURL, params);
        return json;
    }

    /**
     * Function to reset the password
     **/
    public JSONObject forPass(String forgotpassword){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl("get",forpassURL, params);
        return json;
    }

     /**
      * Function to  Register
      **/
    public JSONObject registerUser(String fname, String uname, String password, String role){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", fname));
        params.add(new BasicNameValuePair("userName", uname));
        params.add(new BasicNameValuePair("plainPassword", password));
        params.add(new BasicNameValuePair("roleId", role));
        JSONObject json = jsonParser.getJSONFromUrl("post",registerURL,params);
        return json;
    }
    
    /**
     * Make Appointment 
     * Param - title, startTime
     * */
    public JSONObject makeAppointment(String title, String startTime){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", makeAppoint_tag));
    	params.add(new BasicNameValuePair("title", title));
    	params.add(new BasicNameValuePair("startTime", startTime));
    	JSONObject json = jsonParser.getJSONFromUrl("get",makeAppointURL,params);
    	return json;
    }

    /**
     * Get All Appointment for the User 
     * Param - uid(company id)
     * */
    public JSONObject getAppointmentList(String uid){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", getAppoint_tag));
    	params.add(new BasicNameValuePair("uid", uid));
    	JSONObject json = jsonParser.getJSONFromUrl("get",getAppointURL,params);
    	return json;
    }
    
    /**
     * Get Specific Appointment for the User 
     * Param - uid(userid), appointmentid(id of appintment)
     * 
     * */
    public JSONObject getAppointment(String uid , String appointmentid){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", getSpecificAppoint_tag));
    	params.add(new BasicNameValuePair("uid", uid));
    	params.add(new BasicNameValuePair("appointmentid", appointmentid));
    	JSONObject json = jsonParser.getJSONFromUrl("get",getSpecificAppointURL,params);
    	return json;
    }

    
    /**
     * create service for the company 
     * Param - name(of service), isSpecialDeal(about service),
     * price(of service), serviceType(of service), uid(id of company)
     * 
     * */
    public JSONObject createService(String name, String isSpecialDeal, String price, String serviceType, String uid){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", createService_tag));
    	params.add(new BasicNameValuePair("name", name));
    	params.add(new BasicNameValuePair("isSpecialDeal", isSpecialDeal));
    	params.add(new BasicNameValuePair("price", price));
    	params.add(new BasicNameValuePair("serviceType", serviceType));
    	params.add(new BasicNameValuePair("uid", uid));
    	JSONObject json = jsonParser.getJSONFromUrl("get",createServiceURL,params);
    	return json;
    }
    
    /**
     * get all services for the company 
     * Param - uid(company id)
     * 
     * */
    public JSONObject getService(String uid){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", getService_tag));
    	params.add(new BasicNameValuePair("uid", uid));
    	JSONObject json = jsonParser.getJSONFromUrl("get",getServiceURL,params);
    	return json;
    }
    
    public JSONObject updateService(String name, String isSpecialDeal, String price, String uid){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", updateService_tag));
    	params.add(new BasicNameValuePair("name", name));
    	params.add(new BasicNameValuePair("isSpecialDeal", isSpecialDeal));
    	params.add(new BasicNameValuePair("price", price));
    	params.add(new BasicNameValuePair("uid", uid));
    	JSONObject json = jsonParser.getJSONFromUrl("get",updateServiceURL,params);
    	return json;
    }

    public JSONObject deleteService(String serviceid){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", deleteService_tag));
    	params.add(new BasicNameValuePair("serviceid", serviceid));
    	JSONObject json = jsonParser.getJSONFromUrl("get",deleteServiceURL,params);
    	return json;
    }
    
    
    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }

}

