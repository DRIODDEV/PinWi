package com.hatchtact.pinwi.sync;

import java.io.IOException;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.google.gson.Gson;
import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.classmodel.AccessProfileList;
import com.hatchtact.pinwi.classmodel.ActivityDaysByCalendarMonth;
import com.hatchtact.pinwi.classmodel.AddAfterSchoolActivities;
import com.hatchtact.pinwi.classmodel.AddAllyInformationOnActivity;
import com.hatchtact.pinwi.classmodel.AddCustomActivity;
import com.hatchtact.pinwi.classmodel.AddHolidaysByChildIDModel;
import com.hatchtact.pinwi.classmodel.AddSubjectActivity;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivitiesByDateList;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityByCatIdList;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityByChildIDList;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityDetails;
import com.hatchtact.pinwi.classmodel.AfterSchoolCategoriesByOwnerIDList;
import com.hatchtact.pinwi.classmodel.AllyList;
import com.hatchtact.pinwi.classmodel.AllyProfile;
import com.hatchtact.pinwi.classmodel.AuthenticateUser;
import com.hatchtact.pinwi.classmodel.AuthenticateUserResult;
import com.hatchtact.pinwi.classmodel.CalenderDateList;
import com.hatchtact.pinwi.classmodel.CategoriesAndSubCategoriesList;
import com.hatchtact.pinwi.classmodel.CheckConfirmationCode;
import com.hatchtact.pinwi.classmodel.CheckPasscode;
import com.hatchtact.pinwi.classmodel.CheckPasswordCode;
import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.CityList;
import com.hatchtact.pinwi.classmodel.CountryList;
import com.hatchtact.pinwi.classmodel.DisplayAllyListByChildAndActivityId;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetAllyDetails;
import com.hatchtact.pinwi.classmodel.GetAllyInformationOnActivity;
import com.hatchtact.pinwi.classmodel.GetAutolockTimeList;
import com.hatchtact.pinwi.classmodel.GetBadgeDetailsByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetChildAfterSchoolActiviesByDaySubjectModelList;
import com.hatchtact.pinwi.classmodel.GetChildDetails;
import com.hatchtact.pinwi.classmodel.GetChildDetailsByChildIDList;
import com.hatchtact.pinwi.classmodel.GetChildDetailsOnBuddiesByChildIDOnCIList;
import com.hatchtact.pinwi.classmodel.GetChildSubjectActiviesByDaySubjectModelList;
import com.hatchtact.pinwi.classmodel.GetChildsDetailsOnRecommendedByActivityIDList;
import com.hatchtact.pinwi.classmodel.GetDelightTraitsByActivityList;
import com.hatchtact.pinwi.classmodel.GetDelightTraitsByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetDetailByChildIDList;
import com.hatchtact.pinwi.classmodel.GetDetailByMapEmoticIDList;
import com.hatchtact.pinwi.classmodel.GetExhilaratorsListByChildIDList;
import com.hatchtact.pinwi.classmodel.GetFriendDetailsByFriendIDList;
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedIDList;
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedIDOnCIList;
import com.hatchtact.pinwi.classmodel.GetFriendsTempleteMessageListByChildID;
import com.hatchtact.pinwi.classmodel.GetFriendsTempleteMessageListByChildIDList;
import com.hatchtact.pinwi.classmodel.GetHolidayDetailsByHolidayDesc;
import com.hatchtact.pinwi.classmodel.GetHolidaysByChildIDList;
import com.hatchtact.pinwi.classmodel.GetInterestPatternByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetInterestTraitsByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetListOfActivitiesOnRecommendedByClusterIDList;
import com.hatchtact.pinwi.classmodel.GetListOfBuddiesByChildIDOnCIList;
import com.hatchtact.pinwi.classmodel.GetListOfClustersOnRecommendedByChildIDList;
import com.hatchtact.pinwi.classmodel.GetListOfMessageTempletesList;
import com.hatchtact.pinwi.classmodel.GetListOfPendingRequestsByLoggedIDList;
import com.hatchtact.pinwi.classmodel.GetListOfPinWiNetworksByLoggedIDList;
import com.hatchtact.pinwi.classmodel.GetListOfWishListsByChildIDList;
import com.hatchtact.pinwi.classmodel.GetListofAllysByParentIDList;
import com.hatchtact.pinwi.classmodel.GetListofChildrensByChildActIDList;
import com.hatchtact.pinwi.classmodel.GetListofChildsByParentIDList;
import com.hatchtact.pinwi.classmodel.GetLocationDetails;
import com.hatchtact.pinwi.classmodel.GetNeighbourhoodRadiusList;
import com.hatchtact.pinwi.classmodel.GetNewNotificationCount;
import com.hatchtact.pinwi.classmodel.GetNotificationByNotificationID;
import com.hatchtact.pinwi.classmodel.GetNotificationListByChildIDOnCIList;
import com.hatchtact.pinwi.classmodel.GetNotificationListByParentIDList;
import com.hatchtact.pinwi.classmodel.GetParentDetails;
import com.hatchtact.pinwi.classmodel.GetPastDaysRatingStatusModelList;
import com.hatchtact.pinwi.classmodel.GetPeopleYouMayKnowListByLoggedIDList;
import com.hatchtact.pinwi.classmodel.GetPercentageCount;
import com.hatchtact.pinwi.classmodel.GetPointsInfoByChildIDModel;
import com.hatchtact.pinwi.classmodel.GetPointsInfoByChildIDOnInsightsList;
import com.hatchtact.pinwi.classmodel.GetProfileDetailsByLoggedIDList;
import com.hatchtact.pinwi.classmodel.GetSchoolActivitiesByDateList;
import com.hatchtact.pinwi.classmodel.GetWishListsByChildIDList;
import com.hatchtact.pinwi.classmodel.ListOfAllysList;
import com.hatchtact.pinwi.classmodel.LocalityList;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.RequestAddOnVersionModel;
import com.hatchtact.pinwi.classmodel.ResetPassword;
import com.hatchtact.pinwi.classmodel.ResultIsSubscribeList;
import com.hatchtact.pinwi.classmodel.SchedularHolidayList;
import com.hatchtact.pinwi.classmodel.SchoolActivityDetails;
import com.hatchtact.pinwi.classmodel.SchoolList;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByActivityNameList;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByChildIDList;
import com.hatchtact.pinwi.classmodel.SearchActivityByNameList;
import com.hatchtact.pinwi.classmodel.SearchFriendListGloballyList;
import com.hatchtact.pinwi.classmodel.SearchListOfBuddiesOnCIList;
import com.hatchtact.pinwi.classmodel.SearchWishListByChildIDList;
import com.hatchtact.pinwi.classmodel.SendConfirmationCodeToMail;
import com.hatchtact.pinwi.classmodel.SubjectActivitiesByChildIDList;
import com.hatchtact.pinwi.classmodel.SubjectActivitiesList;
import com.hatchtact.pinwi.classmodel.UpdateAllyProfile;
import com.hatchtact.pinwi.classmodel.UpdateChildProfile;
import com.hatchtact.pinwi.classmodel.UpdateLocationByParentID;
import com.hatchtact.pinwi.classmodel.UpdateParentProfile;
import com.hatchtact.pinwi.utility.GetValidJson;
import com.hatchtact.pinwi.utility.StaticVariables;

public class ServiceMethod 
{
	/** The url. */
	//private final String URL = "http://api.pinwi.in:2015/PinWiService.asmx";
	//private final String URL = "http://api.pinwi.in:2016/PinWiService.asmx";
	//private final String URL = "http://pinwi.staging4.nz-technologies.com/PinWiService.asmx";
	//private final String URL = "http://166.78.47.124:2016/pinwiservice.asmx";
	private final String URL = "http://166.78.47.124:2015/pinwiservice.asmx";


	/** The Constant NAMESPACE. */
	private final String NAMESPACE = "http://tempuri.org/";

	/** The Constant HEADER. */
	private final String HEADER = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>";

	/** The Constant SOAP_VERSION. */
	private final int SOAP_VERSION = SoapEnvelope.VER11;

	/** The Constant DEBUG. */
	private final boolean DEBUG = true;

	/** The time out. */
	private final int TIMEOUT = 60*1000;

	private final String WSID = "pinwistars";

	private final String WSPWD = "appconnect@$2015";

	private final String WSIDKEY = "wsid";

	private final String WSPWDKEY = "wspwd";

	private GetValidJson getValidJson;

	private String errorMessage=null;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ServiceMethod() {
		super();
		// TODO Auto-generated constructor stub	
		getValidJson = new GetValidJson();
	}

	public CountryList getCountry()
	{
		CountryList countryList=null;

		String METHOD_NAME = "GetCountryList";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {
			httpTransport.call(SOAP_ACTION, envelope);
		} 
		catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String countryListString = getValidJson.getValidJsonArray(returnString);

		try {
			JSONArray arrayJson = new JSONArray(countryListString);

			JSONObject  onj = new JSONObject();
			onj.put("country", arrayJson);

			Gson gson = new Gson();

			countryList = gson.fromJson(onj.toString(), CountryList.class);	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return countryList;
	}


	public CityList getCity(int countryId)
	{
		CityList cityList =null;

		String METHOD_NAME = "GetCityList";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("CountryID",countryId+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String cityListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(cityListString);
			JSONObject  onj = new JSONObject();
			onj.put("city", arrayJson);

			Gson gson = new Gson();

			cityList = gson.fromJson(onj.toString(), CityList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cityList;
	}

	public AuthenticateUserResult authenticateUser(AuthenticateUser userCrenditial)
	{
		AuthenticateUserResult userResult =null;

		String METHOD_NAME = "AuthenticateUser";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("EmailAddress",userCrenditial.getEmailAddress());
		request.addProperty("Password",userCrenditial.getPassword());
		/*request.addProperty("DeviceID",DeviceID);
		request.addProperty("DeviceType",DeviceType);*/

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			userResult = gson.fromJson(userString, AuthenticateUserResult.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userResult;
	}

	public int createParentProfile(ParentProfile parentProfie)
	{
		int parentId =0;

		String METHOD_NAME = "CreateParentProfile";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		/*request.addProperty("DeviceID",DeviceID);
		request.addProperty("DeviceType",DeviceType);*/
		//request.addProperty("ParentID",parentProfie.getParentID());
		request.addProperty("DeviceID",parentProfie.getDeviceID());
		request.addProperty("DeviceToken",parentProfie.getDeviceToken());
		request.addProperty("ProfileImage",parentProfie.getProfileImage());
		request.addProperty("FirstName",parentProfie.getFirstName());
		request.addProperty("LastName",parentProfie.getLastName());
		request.addProperty("EmailAddress",parentProfie.getEmailAddress());
		request.addProperty("Password",parentProfie.getPassword());
		if(parentProfie.getDateOfBirth()==null ||parentProfie.getDateOfBirth().trim().equalsIgnoreCase("null")||parentProfie.getDateOfBirth().trim().equalsIgnoreCase(""))
		{
			parentProfie.setDateOfBirth("01/01/1900");
			request.addProperty("DateOfBirth",parentProfie.getDateOfBirth());
		}
		else
		{
			request.addProperty("DateOfBirth",parentProfie.getDateOfBirth());
		}
		//request.addProperty("DateOfBirth",parentProfie.getDateOfBirth());
		request.addProperty("Relation",parentProfie.getRelation());
		request.addProperty("Gender",parentProfie.getGender());
		request.addProperty("Contact",parentProfie.getContact());
		request.addProperty("Passcode",parentProfie.getPasscode());
		request.addProperty("AutolockTime",parentProfie.getAutolockID()+"");
		request.addProperty("FlatNoBuilding",parentProfie.getFlatNoBuilding());
		request.addProperty("StreetLocality",parentProfie.getStreetLocality());
		//request.addProperty("City",parentProfie.getCityID()+"");
		request.addProperty("City",parentProfie.getCity());
		request.addProperty("Country",parentProfie.getCountryID()+"");
		request.addProperty("GoogleMapAddress",parentProfie.getGoogleMapAddress());
		request.addProperty("Longitude",parentProfie.getLongitude());
		request.addProperty("Latitude",parentProfie.getLatitude());
		request.addProperty("NeighbourhoodRadius",parentProfie.getNeighbourhoodID()+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			parentId = object.getInt("ParentID");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			parentId = -1;
		}
		return parentId;
	}

	public int sendCodeToMail(SendConfirmationCodeToMail sendCodeTomail, int type)
	{
		int confirmationId=0;

		String METHOD_NAME = "SendConfirmationCodeToMail";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		if(type==0)
		{
			request.addProperty("EmailAddress",sendCodeTomail.getEmailAddress());
			request.addProperty("Contact","");
		}
		else
		{
			request.addProperty("EmailAddress","");
			request.addProperty("Contact",sendCodeTomail.getContact());
		}

		request.addProperty("ParentID",sendCodeTomail.getParentID());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			confirmationId = object.getInt("ConfirmationID");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return confirmationId;
	}

	public int checkConfirmationCodeById(CheckConfirmationCode checkConfirmationCode)
	{
		int parentId=0;

		String METHOD_NAME = "CheckConfirmationCodeByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("EmailAddress",checkConfirmationCode.getEmailAddress());
		request.addProperty("ParentID",checkConfirmationCode.getParentID());
		request.addProperty("ConfirmationCode", checkConfirmationCode.getConfirmationCode());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			parentId = object.getInt("ParentID");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parentId;
	}

	public int createChildProfile(ChildProfile childProfile)
	{
		int childId =0;

		String METHOD_NAME = "CreateChildProfile";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",childProfile.getParentID());
		request.addProperty("ProfileImage",childProfile.getProfileImage());
		request.addProperty("FirstName",childProfile.getFirstName());
		request.addProperty("LastName",childProfile.getLastName());
		request.addProperty("NickName",childProfile.getNickName());
		if(childProfile.getDateOfBirth()==null ||childProfile.getDateOfBirth().trim().equalsIgnoreCase("null")||childProfile.getDateOfBirth().trim().equalsIgnoreCase(""))
		{
			childProfile.setDateOfBirth("01/01/1900");
			request.addProperty("DateOfBirth",childProfile.getDateOfBirth());
		}
		else
		{
			request.addProperty("DateOfBirth",childProfile.getDateOfBirth());
		}
		//request.addProperty("DateOfBirth",childProfile.getDateOfBirth());
		request.addProperty("Gender",childProfile.getGender());
		//request.addProperty("SchoolName",childProfile.getSchoolID()+"");
		request.addProperty("SchoolName",childProfile.getSchoolName());
		request.addProperty("Passcode",childProfile.getPasscode());
		request.addProperty("AutolockTime",childProfile.getAutolockID()+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			childId = object.getInt("ChildID");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return childId;
	}

	public SchoolList getSchool(int parentId)
	{
		SchoolList schoolList =null;

		String METHOD_NAME = "GetSchoolNamesByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put5f	

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);




		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String schoolListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(schoolListString);
			JSONObject  onj = new JSONObject();
			onj.put("schoolList", arrayJson);

			Gson gson = new Gson();

			schoolList = gson.fromJson(onj.toString(), SchoolList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return schoolList;
	}


	public int createAllyProfile(AllyProfile allyProfile)
	{
		int allyId =0;

		String METHOD_NAME = "CreateAllyProfile";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",allyProfile.getParentID());
		request.addProperty("ProfileImage",allyProfile.getProfileImage());
		request.addProperty("FirstName",allyProfile.getFirstName());
		request.addProperty("LastName",allyProfile.getLastName());
		request.addProperty("Relationship",allyProfile.getAllyID()+"");
		request.addProperty("EmailAddress",allyProfile.getEmailAddress());
		request.addProperty("Contact",allyProfile.getContact());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			allyId = object.getInt("AllyID");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allyId;
	}

	public AllyList getAlly()
	{
		AllyList allyList=null;

		String METHOD_NAME = "GetAllyTypes";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {
			httpTransport.call(SOAP_ACTION, envelope);
		} 
		catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String allyListString = getValidJson.getValidJsonArray(returnString);

		try {
			JSONArray arrayJson = new JSONArray(allyListString);

			JSONObject  onj = new JSONObject();
			onj.put("allyList", arrayJson);

			Gson gson = new Gson();

			allyList = gson.fromJson(onj.toString(), AllyList.class);	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return allyList;
	}

	public AccessProfileList getaccessProfile(int parentId)
	{
		AccessProfileList accessProfileList = null;

		String METHOD_NAME = "GetNamesByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String accessProfileString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(accessProfileString);
			JSONObject  onj = new JSONObject();
			onj.put("accessProfileList", arrayJson);

			Gson gson = new Gson();

			accessProfileList = gson.fromJson(onj.toString(), AccessProfileList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return accessProfileList;
	}

	public int checkpassCode(CheckPasscode checkPasscode)
	{
		int profileId =0;

		String METHOD_NAME = "CheckPasscode";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",checkPasscode.getProfileID());
		request.addProperty("Passcode",checkPasscode.getPasscode());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			profileId = object.getInt("ProfileID");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			profileId = -1;
		}
		return profileId;
	}

	public GetAutolockTimeList getAutoLockTime()
	{
		GetAutolockTimeList getAutolockTimeList=null;

		String METHOD_NAME = "GetAutolockTime";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {
			httpTransport.call(SOAP_ACTION, envelope);
		} 
		catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String countryListString = getValidJson.getValidJsonArray(returnString);

		try {
			JSONArray arrayJson = new JSONArray(countryListString);

			JSONObject  onj = new JSONObject();
			onj.put("getAutolockTime", arrayJson);

			Gson gson = new Gson();

			getAutolockTimeList = gson.fromJson(onj.toString(), GetAutolockTimeList.class);	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getAutolockTimeList;
	}

	public GetNeighbourhoodRadiusList getNeighbourhoodRadiusList()
	{
		GetNeighbourhoodRadiusList getNeighbourhoodRadiusList=null;

		String METHOD_NAME = "GetNeighbourhoodRadius";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {
			httpTransport.call(SOAP_ACTION, envelope);
		} 
		catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String countryListString = getValidJson.getValidJsonArray(returnString);

		try {
			JSONArray arrayJson = new JSONArray(countryListString);

			JSONObject  onj = new JSONObject();
			onj.put("getNeighbourhoodRadius", arrayJson);

			Gson gson = new Gson();

			getNeighbourhoodRadiusList = gson.fromJson(onj.toString(), GetNeighbourhoodRadiusList.class);	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getNeighbourhoodRadiusList;
	}

	public int recoverPasscode(int profileId)
	{
		int errorcode = 0;
		String METHOD_NAME = "RecoverPasscode";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",profileId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorcode;
	}

	public CalenderDateList getDay(ActivityDaysByCalendarMonth activityDaysByCalendarMonth)
	{
		CalenderDateList calenderDateList =null;

		String METHOD_NAME = "GetActivityDaysByCalendarMonth";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",activityDaysByCalendarMonth.getChildID()+"");
		request.addProperty("Month",activityDaysByCalendarMonth.getMonth());
		request.addProperty("Year",activityDaysByCalendarMonth.getYear());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String calenderListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(calenderListString);
			JSONObject  onj = new JSONObject();
			onj.put("calenderDate", arrayJson);

			Gson gson = new Gson();

			calenderDateList = gson.fromJson(onj.toString(), CalenderDateList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return calenderDateList;
	}

	public AfterSchoolActivitiesByDateList getAfterSchoolActivityByDate(int childId,String activityDate)
	{
		AfterSchoolActivitiesByDateList afterSchoolActivitiesByDateList =null;

		String METHOD_NAME = "GetAfterSchoolActivitiesByDate";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("ActivityDate",activityDate);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String afterschoolactivitybyDateListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(afterschoolactivitybyDateListString);
			JSONObject  onj = new JSONObject();
			onj.put("afterSchoolActivitiesByDate", arrayJson);

			Gson gson = new Gson();

			afterSchoolActivitiesByDateList = gson.fromJson(onj.toString(), AfterSchoolActivitiesByDateList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return afterSchoolActivitiesByDateList;
	}

	public GetSchoolActivitiesByDateList getSchoolActivityByDate(int childId,String activityDate)
	{
		GetSchoolActivitiesByDateList getSchoolActivitiesByDateList =null;

		String METHOD_NAME = "GetSchoolActivitiesByDate";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("ActivityDate",activityDate);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String schoolActivtiyBydateListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(schoolActivtiyBydateListString);
			JSONObject  onj = new JSONObject();
			onj.put("schoolActivitiesByDate", arrayJson);

			Gson gson = new Gson();

			getSchoolActivitiesByDateList = gson.fromJson(onj.toString(), GetSchoolActivitiesByDateList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getSchoolActivitiesByDateList;
	}

	public AfterSchoolActivityByChildIDList getAfterSchoolActivityByChildId(int childId)
	{
		AfterSchoolActivityByChildIDList afterSchoolActivityByChildIDList =null;

		String METHOD_NAME = "GetAfterSchoolActivityByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String afterSchoolActivityByChildIdListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(afterSchoolActivityByChildIdListString);
			JSONObject  onj = new JSONObject();
			onj.put("afterSchoolActivityByChildID", arrayJson);

			Gson gson = new Gson();

			afterSchoolActivityByChildIDList = gson.fromJson(onj.toString(), AfterSchoolActivityByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return afterSchoolActivityByChildIDList;
	}

	public AfterSchoolCategoriesByOwnerIDList getAfterSchoolCategoriesByOwnerID(int ownerId)
	{
		AfterSchoolCategoriesByOwnerIDList afterSchoolCategoriesByOwnerIDList =null;

		String METHOD_NAME = "GetAfterSchoolCategoriesByOwnerID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("OwnerID",ownerId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String afterSchoolCategoriesByOwnerIdListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(afterSchoolCategoriesByOwnerIdListString);
			JSONObject  onj = new JSONObject();
			onj.put("afterSchoolCategoriesByOwnerID", arrayJson);

			Gson gson = new Gson();

			afterSchoolCategoriesByOwnerIDList = gson.fromJson(onj.toString(), AfterSchoolCategoriesByOwnerIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return afterSchoolCategoriesByOwnerIDList;
	}

	public CategoriesAndSubCategoriesList getCategoriesAndSubCategories(int ownerCategoryId)
	{
		CategoriesAndSubCategoriesList categoriesAndSubCategoriesList =null;

		String METHOD_NAME = "GetCategoriesAndSubCategories";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("OwnerCategoryID",ownerCategoryId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String categoriesAndSubCategoriesListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(categoriesAndSubCategoriesListString);
			JSONObject  onj = new JSONObject();
			onj.put("categoriesAndSubCategories", arrayJson);

			Gson gson = new Gson();

			categoriesAndSubCategoriesList = gson.fromJson(onj.toString(), CategoriesAndSubCategoriesList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return categoriesAndSubCategoriesList;
	}

	public ListOfAllysList getListOfAllys(int childId)
	{
		ListOfAllysList listOfAllysList =null;

		String METHOD_NAME = "GetListOfAllys";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String listOfAllyListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(listOfAllyListString);
			JSONObject  onj = new JSONObject();
			onj.put("listOfAllys", arrayJson);

			Gson gson = new Gson();

			listOfAllysList = gson.fromJson(onj.toString(), ListOfAllysList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOfAllysList;
	}

	public int AddAfterSchoolActivity(AddAfterSchoolActivities addAfterSchoolActivities)
	{
		int errorcode = 0;

		String METHOD_NAME = "AddAfterSchoolActivities";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ActivityID",addAfterSchoolActivities.getActivityID());
		request.addProperty("ChildID",addAfterSchoolActivities.getChildID());
		request.addProperty("Remarks",addAfterSchoolActivities.getRemarks());
		request.addProperty("ExamDate",addAfterSchoolActivities.getStartDate());
		request.addProperty("StartDate",addAfterSchoolActivities.getStartDate());
		request.addProperty("StartTime",addAfterSchoolActivities.getStartTime());
		request.addProperty("EndTime",addAfterSchoolActivities.getEndTime());
		request.addProperty("Enddate",addAfterSchoolActivities.getEnddate());
		if(addAfterSchoolActivities.getfMode()==0)
		{
			request.addProperty("ActivityDays","1,2,3,4,5,6,7");
		}
		else
			request.addProperty("ActivityDays",addAfterSchoolActivities.getActivityDays());

		request.addProperty("FMode",addAfterSchoolActivities.getfMode());
		request.addProperty("BWFMode",addAfterSchoolActivities.getBWFMode());

		request.addProperty("IsSpecial",addAfterSchoolActivities.getIsSpecial());
		request.addProperty("IsPrivate",addAfterSchoolActivities.getIsPrivate());

		if(addAfterSchoolActivities.getIsSpecial().trim().equalsIgnoreCase("1"))
			request.addProperty("SpecialDate",addAfterSchoolActivities.getSpecialDate());
		else
			request.addProperty("SpecialDate",addAfterSchoolActivities.getStartDate());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		Error err = null ;
		try {
			err = getError();	
			errorcode=Integer.parseInt(err.getErrorCode());

		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return errorcode;
	}

	public int AddAllyInformation(AddAllyInformationOnActivity addAllyInformationOnActivity)
	{
		int activityAllyID =0;

		String METHOD_NAME = "AddAllyInformationOnActivity";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",addAllyInformationOnActivity.getChildID());
		request.addProperty("ActivityID",addAllyInformationOnActivity.getActivityID());
		request.addProperty("AllyID",addAllyInformationOnActivity.getAllyID());
		request.addProperty("AllyIndex",addAllyInformationOnActivity.getAllyIndex());
		request.addProperty("Date",addAllyInformationOnActivity.getDate());
		request.addProperty("Time",addAllyInformationOnActivity.getTime());
		request.addProperty("PickUp",addAllyInformationOnActivity.getPickUp());
		request.addProperty("Drop",addAllyInformationOnActivity.getDrop());
		request.addProperty("SpeicalInstructions",addAllyInformationOnActivity.getSpeicalInstructions());
		request.addProperty("NotificationMode",addAllyInformationOnActivity.getNotificationMode());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			activityAllyID = object.getInt("ActivityAllyID");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activityAllyID;
	}

	public AfterSchoolActivityDetails getAfterSchoolActivityDetail(int childId,String activityId)
	{
		AfterSchoolActivityDetails afterSchoolActivityDetails =null;

		String METHOD_NAME = "GetAfterSchoolActivityDetails";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("ActivityID",activityId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;

		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			afterSchoolActivityDetails = gson.fromJson(userString, AfterSchoolActivityDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return afterSchoolActivityDetails;
	}

	public SubjectActivitiesByChildIDList getSubjectActivitiesByChild(int childId)
	{
		SubjectActivitiesByChildIDList subjectActivitiesByChildIDList =null;

		String METHOD_NAME = "GetSubjectActivitiesByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String subjectActivitiesByChildIdListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(subjectActivitiesByChildIdListString);
			JSONObject  onj = new JSONObject();
			onj.put("subjectActivitiesByChildID", arrayJson);

			Gson gson = new Gson();

			subjectActivitiesByChildIDList = gson.fromJson(onj.toString(), SubjectActivitiesByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return subjectActivitiesByChildIDList;
	}

	public SubjectActivitiesList getSubjectActivities()
	{
		SubjectActivitiesList subjectActivitiesList=null;

		String METHOD_NAME = "GetSubjectActivities";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {
			httpTransport.call(SOAP_ACTION, envelope);
		} 
		catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String subjectActivitiesListString = getValidJson.getValidJsonArray(returnString);

		try {
			JSONArray arrayJson = new JSONArray(subjectActivitiesListString);

			JSONObject  onj = new JSONObject();
			onj.put("subjectActivitiesList", arrayJson);

			Gson gson = new Gson();

			subjectActivitiesList = gson.fromJson(onj.toString(), SubjectActivitiesList.class);	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return subjectActivitiesList;
	}

	public int AddSubjectActivity(AddSubjectActivity addSubjectActivity)
	{
		int errorcode = 0;

		String METHOD_NAME = "AddSubjectActivity";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ActivityID",addSubjectActivity.getActivityID());
		request.addProperty("ChildID",addSubjectActivity.getChildID());
		request.addProperty("ActivityDays",addSubjectActivity.getActivityDays());
		request.addProperty("Remarks",addSubjectActivity.getRemarks());
		request.addProperty("ExamDate",addSubjectActivity.getExamDate());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		Error err = null ;
		try {
			err = getError();	
			errorcode=Integer.parseInt(err.getErrorCode());

		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return errorcode;
	}

	public SchoolActivityDetails getSchoolActivityDetail(int childId,String activityId)
	{
		SchoolActivityDetails schoolActivityDetails =null;

		String METHOD_NAME = "GetSchoolActivityDetails";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("ActivityID",activityId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			schoolActivityDetails = gson.fromJson(userString, SchoolActivityDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return schoolActivityDetails;
	}

	public GetAllyInformationOnActivity getAllyInformationOnActivity(int childId,int activityId,int allyId,int allyIndex)
	{
		GetAllyInformationOnActivity getAllyInformation =null;

		String METHOD_NAME = "GetAllyInformationOnActivity";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("ActivityID",activityId);
		request.addProperty("AllyID",allyId);
		request.addProperty("AllyIndex",allyIndex);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			getAllyInformation = gson.fromJson(userString, GetAllyInformationOnActivity.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getAllyInformation;
	}

	public int AddCustomActivity(AddCustomActivity addCustomActivity)
	{
		int errorcode = 0;

		String METHOD_NAME = "AddCustomActivity";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("SubCategoryID",addCustomActivity.getSubCategoryID());
		request.addProperty("Name",addCustomActivity.getName());
		request.addProperty("ParentID",addCustomActivity.getParentID());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorcode;
	}

	public int forgetPassword(String emailId)
	{
		int errorcode = 0;
		String METHOD_NAME = "ForgetPassword";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("EmailAddress",emailId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorcode;
	}

	public int checkPasswordCode(CheckPasswordCode resetPassword)
	{
		int ErrorCode=0;

		String METHOD_NAME = "CheckPassword";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("EmailAddress",resetPassword.getEmailAddress());
		request.addProperty("Code", resetPassword.getCode());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			ErrorCode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ErrorCode;
	}

	public int ResetPasswordCode(ResetPassword resetPassword)
	{
		int ErrorCode=0;

		String METHOD_NAME = "ResetPassword";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("EmailAddress",resetPassword.getEmailAddress());
		request.addProperty("NewPassword", resetPassword.getNewPassword());
		request.addProperty("ConfirmPassword", resetPassword.getConfirmPassword());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			ErrorCode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ErrorCode;
	}
	public String inviteUrl(String parentId)
	{
		String METHOD_NAME = "GetInviteURL";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);

		String returnStringvalue = null;

		try 
		{	
			returnStringvalue = userString.substring(2, userString.length()-2);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnStringvalue;
	}

	public AfterSchoolActivityByCatIdList getactivityByCatId(int categoryId,int parentId)
	{
		AfterSchoolActivityByCatIdList afterSchoolActivityByCatIdList =null;

		String METHOD_NAME = "GetActivitiesByCatID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("CategoryID",categoryId);
		request.addProperty("ParentID",parentId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String categoriesAndSubCategoriesListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(categoriesAndSubCategoriesListString);
			JSONObject  onj = new JSONObject();
			onj.put("afterschoolActivityByCatId", arrayJson);

			Gson gson = new Gson();

			afterSchoolActivityByCatIdList = gson.fromJson(onj.toString(), AfterSchoolActivityByCatIdList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return afterSchoolActivityByCatIdList;
	}

	public Error getError()
	{	
		Error er = getValidJson.getError(errorMessage);

		if(er==null)
		{
			er = new Error();
			er.setErrorCode(0+"");
			er.setErrorDesc("Please Try Again.");
		}
		return er;
	}


	public GetBadgeDetailsByChildIDOnInsightList getBadgeDetailsByChildIDOnInsightList(int childId)
	{
		GetBadgeDetailsByChildIDOnInsightList getBadgeDetailsByChildIDOnInsightList =null;

		String METHOD_NAME = "GetBadgeDetailsByChildIDOnInsight";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getBadgeDetailsByChildIDOnInsightString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getBadgeDetailsByChildIDOnInsightString);
			JSONObject  onj = new JSONObject();
			onj.put("getBadgeDetailsByChildIDOnInsight", arrayJson);

			Gson gson = new Gson();

			getBadgeDetailsByChildIDOnInsightList = gson.fromJson(onj.toString(), GetBadgeDetailsByChildIDOnInsightList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getBadgeDetailsByChildIDOnInsightList;
	}



	public GetDelightTraitsByChildIDOnInsightList getDelightTraitsByChildIDOnInsightList(int childId)
	{
		GetDelightTraitsByChildIDOnInsightList getDelightTraitsByChildIDOnInsightList =null;

		String METHOD_NAME = "GetDelightTraitsByChildIDOnInsight";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getDelightTraitsByChildIDOnInsight = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getDelightTraitsByChildIDOnInsight);
			JSONObject  onj = new JSONObject();
			onj.put("getDelightTraitsByChildIDOnInsight", arrayJson);

			Gson gson = new Gson();

			getDelightTraitsByChildIDOnInsightList = gson.fromJson(onj.toString(), GetDelightTraitsByChildIDOnInsightList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getDelightTraitsByChildIDOnInsightList;
	}


	public GetPointsInfoByChildIDOnInsightsList getPointsInfoByChildIDOnInsightsList(int childId)
	{
		GetPointsInfoByChildIDOnInsightsList getPointsInfoByChildIDOnInsightsList =null;

		String METHOD_NAME = "GetPointsInfoByChildIDOnInsights";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getPointsInfoByChildIDOnInsights = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getPointsInfoByChildIDOnInsights);
			JSONObject  onj = new JSONObject();
			onj.put("getPointsInfoByChildIDOnInsights", arrayJson);

			Gson gson = new Gson();

			getPointsInfoByChildIDOnInsightsList = gson.fromJson(onj.toString(), GetPointsInfoByChildIDOnInsightsList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getPointsInfoByChildIDOnInsightsList;
	}




	public GetInterestTraitsByChildIDOnInsightList getInterestTraitsByChildIDOnInsight(int childId)
	{
		GetInterestTraitsByChildIDOnInsightList getInterestTraitsByChildIDOnInsightList =null;

		String METHOD_NAME = "GetInterestTraitsByChildIDOnInsight";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getInterestTraitsByChildIDOnInsight = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getInterestTraitsByChildIDOnInsight);
			JSONObject  onj = new JSONObject();
			onj.put("getInterestTraitsByChildIDOnInsight", arrayJson);

			Gson gson = new Gson();

			getInterestTraitsByChildIDOnInsightList = gson.fromJson(onj.toString(), GetInterestTraitsByChildIDOnInsightList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getInterestTraitsByChildIDOnInsightList;
	}


	public GetDelightTraitsByActivityList getDelightTraitsByActivityList(int childId,int ActivityId)
	{
		GetDelightTraitsByActivityList getDelightTraitsByActivityList =null;

		String METHOD_NAME = "GetDelightTraitsByActivity";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("ActivityID",ActivityId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getDelightTraitsByActivity = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getDelightTraitsByActivity);
			JSONObject  onj = new JSONObject();
			onj.put("getDelightTraitsByActivity", arrayJson);

			Gson gson = new Gson();

			getDelightTraitsByActivityList = gson.fromJson(onj.toString(), GetDelightTraitsByActivityList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getDelightTraitsByActivityList;
	}






	public GetHolidaysByChildIDList getHolidaysByChildIDList(int childId,String Month,int Year)
	{
		GetHolidaysByChildIDList getHolidaysByChildIDList =null;

		String METHOD_NAME = "GetHolidaysByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId+"");
		request.addProperty("Month",Month);
		request.addProperty("Year",Year+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getHolidaysByChildId = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getHolidaysByChildId);
			JSONObject  onj = new JSONObject();
			onj.put("getHolidaysByChildIDList", arrayJson);

			Gson gson = new Gson();

			getHolidaysByChildIDList = gson.fromJson(onj.toString(), GetHolidaysByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getHolidaysByChildIDList;
	}




	public String addHolidaysByChildId(String ChildId,String HolidayDate,String HolidayDescription)
	{

		String METHOD_NAME = "AddHolidaysByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildId);
		request.addProperty("HolidayDate",HolidayDate);
		request.addProperty("HolidayDescription",HolidayDescription);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorCode();
	}





	/**  ---------------------Child Module Methods-----------------------   */
	public int getCurrentDayRatingStatusForChildModule(int childID)
	{
		int status =0;

		String METHOD_NAME = "GetCurrentDayRatingStatus";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childID+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			status = object.getInt("Status");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//status = -1;
		}
		return status;
	}


	public int getChildAfterSchoolActiviesByDayForChildModule(int childID,String daysAgo)
	{
		int status =0;

		String METHOD_NAME = "GetChildAfterSchoolActiviesByDay";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childID);
		request.addProperty("DaysAgo",daysAgo);//("0");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonArray(returnString);
		try {

			if(!userString.equalsIgnoreCase("0"))
			{
				JSONArray arrayObject = new JSONArray(userString);
				JSONObject object = new JSONObject();
				object.put("listOfAfterSubjects", arrayObject);

				Gson gson = new Gson();

				AccessProfileActivity.subjectsFetchedAfterSchool = gson.fromJson(object.toString(), GetChildAfterSchoolActiviesByDaySubjectModelList.class);
			}
			else
				status = Integer.parseInt(userString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//status = -1;
		}
		return status;
	}

	public int getChildSubjectActiviesByDayForChildModule(int childID, String daysAgo)
	{
		int status =0;

		String METHOD_NAME = "GetChildSubjectActiviesByDay";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childID);
		request.addProperty("DaysAgo",daysAgo);//"0");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			if(!userString.equalsIgnoreCase("0"))
			{
				JSONArray arrayObject = new JSONArray(userString);
				JSONObject object = new JSONObject();
				object.put("listOfSubjects", arrayObject);

				Gson gson = new Gson();

				AccessProfileActivity.subjectsFetched = gson.fromJson(object.toString(), GetChildSubjectActiviesByDaySubjectModelList.class);
			}
			else
				status = Integer.parseInt(userString);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//status = -1;
		}
		return status;
	}

	public GetPointsInfoByChildIDModel getPointsInfoByChildIDForChildModule(int childID)
	{
		GetPointsInfoByChildIDModel getPointsInfoByChildIDModel = null;

		String METHOD_NAME = "GetPointsInfoByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childID+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);

			Gson gson = new Gson();

			getPointsInfoByChildIDModel = gson.fromJson(object.toString(), GetPointsInfoByChildIDModel.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return getPointsInfoByChildIDModel;
	}


	public int getAddPointsEarned(int childID)
	{
		int earnedPoints =-1;

		String METHOD_NAME = "AddPointsEarned";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childID);
		request.addProperty("DaysAgo",StaticVariables.daysAgo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			earnedPoints = object.getInt("PointsEarned");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//earnedPoints = -1;
		}
		return earnedPoints;
	}


	public GetPastDaysRatingStatusModelList getPastDaysRatingStatusForChildModule(int childID)
	{
		GetPastDaysRatingStatusModelList getPastDaysRatingStatusModelList = null;

		String METHOD_NAME = "GetPastDaysRatingStatus";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childID+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonArray(returnString);
		try {

			JSONArray arrayObject = new JSONArray(userString);
			JSONObject object = new JSONObject();
			object.put("listOfPastDays", arrayObject);

			Gson gson = new Gson();

			getPastDaysRatingStatusModelList = gson.fromJson(object.toString(), GetPastDaysRatingStatusModelList.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return getPastDaysRatingStatusModelList;
	}

	public int sendChildStarRating(int childID, String ActRatingValue)
	{
		int ErrorCode=0;

		String METHOD_NAME = "AddActivityRating";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childID);
		request.addProperty("ActRatingValue",ActRatingValue);
		request.addProperty("DaysAgo",StaticVariables.daysAgo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		/*Error err = null ;
		try {
			err = getError();	
			ErrorCode=Integer.parseInt(err.getErrorCode());

		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			ErrorCode = Integer.parseInt(userString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//ErrorCode =-1;
		}
		return ErrorCode;
	}

	//Response of Webservice: Method: GetChildSubjectActiviesByDay(29890): 
	//[{"ErrorCode":"0","ErrorDesc":"Subject Activies fetched."}][{"ActivityID":1,"Name":"Maths","DayID":4}]





	//add on version
	public RequestAddOnVersionModel getRequestAddOnVersion(int parentId)
	{
		RequestAddOnVersionModel requestaddonversionModel =null;

		String METHOD_NAME = "RequestAddOnVersion";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			requestaddonversionModel = gson.fromJson(userString, RequestAddOnVersionModel.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestaddonversionModel;
	}




	//Update Parent,Location, Child and ally

	public int getupdateParentProfile(UpdateParentProfile updateParentProfie, UpdateLocationByParentID updateLocationByParentID)
	{
		int Errorcode =0;

		String METHOD_NAME = "UpdateParentProfile";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",updateParentProfie.getParentID());
		request.addProperty("ProfileImage",updateParentProfie.getProfileImage());
		request.addProperty("FirstName",updateParentProfie.getFirstName());
		request.addProperty("LastName",updateParentProfie.getLastName());
		request.addProperty("Password",updateParentProfie.getPassword());
		request.addProperty("Relation",updateParentProfie.getRelation());
		request.addProperty("Contact",updateParentProfie.getContact());
		request.addProperty("Passcode",updateParentProfie.getPasscode());
		request.addProperty("AutolockTime",updateParentProfie.getAutolockTime()+"");
		if(updateParentProfie.getDateOfBirth()==null ||updateParentProfie.getDateOfBirth().trim().equalsIgnoreCase("null")||updateParentProfie.getDateOfBirth().trim().equalsIgnoreCase(""))
		{
			updateParentProfie.setDateOfBirth("01/01/1900");
			request.addProperty("DateOfBirth",updateParentProfie.getDateOfBirth());

		}
		else
		{
			request.addProperty("DateOfBirth",updateParentProfie.getDateOfBirth());
		}
		request.addProperty("FlatNoBuilding",updateLocationByParentID.getFlatNoBuilding());
		request.addProperty("StreetLocality",updateLocationByParentID.getStreetLocality());
		request.addProperty("City",updateLocationByParentID.getCity());
		request.addProperty("Country",updateLocationByParentID.getCountry());
		request.addProperty("GoogleMapAddress",updateLocationByParentID.getGoogleMapAddress());
		request.addProperty("Longitude",updateLocationByParentID.getLongitude());
		request.addProperty("Latitude",updateLocationByParentID.getLatitude());
		request.addProperty("NeighbourhoodRadius",updateLocationByParentID.getNeighbourhoodRadius()+"");
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			Errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Errorcode = -1;
		}
		return Errorcode;
	}

	public int getupdateLocation(UpdateLocationByParentID updateLocationByParentID)
	{
		int Errorcode =0;

		String METHOD_NAME = "UpdateLocationByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",updateLocationByParentID.getParentID());
		request.addProperty("FlatNoBuilding",updateLocationByParentID.getFlatNoBuilding());
		request.addProperty("StreetLocality",updateLocationByParentID.getStreetLocality());
		request.addProperty("City",updateLocationByParentID.getCity());
		request.addProperty("Country",updateLocationByParentID.getCountry());
		request.addProperty("GoogleMapAddress",updateLocationByParentID.getGoogleMapAddress());
		request.addProperty("Longitude",updateLocationByParentID.getLongitude());
		request.addProperty("Latitude",updateLocationByParentID.getLatitude());
		request.addProperty("NeighbourhoodRadius",updateLocationByParentID.getNeighbourhoodRadius()+"");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			Errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//	Errorcode = -1;
		}
		return Errorcode;
	}

	public int updateChildProfile(UpdateChildProfile updateChildProfile)
	{
		int Errorcode =0;

		String METHOD_NAME = "UpdateChildProfile";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",updateChildProfile.getParentID());
		request.addProperty("ChildID",updateChildProfile.getChildID());
		request.addProperty("ProfileImage",updateChildProfile.getProfileImage());
		request.addProperty("FirstName",updateChildProfile.getFirstName());
		request.addProperty("LastName",updateChildProfile.getLastName());
		request.addProperty("NickName",updateChildProfile.getNickName());
		if(updateChildProfile.getDateOfBirth()==null ||updateChildProfile.getDateOfBirth().trim().equalsIgnoreCase("null")||updateChildProfile.getDateOfBirth().trim().equalsIgnoreCase(""))
		{
			updateChildProfile.setDateOfBirth("01/01/1900");
			request.addProperty("DateOfBirth",updateChildProfile.getDateOfBirth());
		}
		else
		{
			request.addProperty("DateOfBirth",updateChildProfile.getDateOfBirth());
		}
		//request.addProperty("DateOfBirth",updateChildProfile.getDateOfBirth());
		request.addProperty("Gender",updateChildProfile.getGender());
		request.addProperty("SchoolName",updateChildProfile.getSchoolName());
		request.addProperty("Passcode",updateChildProfile.getPasscode());
		request.addProperty("AutolockTime",updateChildProfile.getAutolockTime());


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			Errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//	Errorcode = -1;
		}
		return Errorcode;
	}

	public int updateAllyProfile(UpdateAllyProfile updateAllyProfile)
	{
		int Errorcode =0;

		String METHOD_NAME = "UpdateAllyProfile";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",updateAllyProfile.getParentID());
		request.addProperty("AllyID",updateAllyProfile.getAllyID());
		request.addProperty("ProfileImage",updateAllyProfile.getProfileImage());
		request.addProperty("FirstName",updateAllyProfile.getFirstName());
		request.addProperty("LastName",updateAllyProfile.getLastName());
		request.addProperty("Relationship",updateAllyProfile.getRelationship());
		request.addProperty("EmailAddress",updateAllyProfile.getEmailAddress());
		request.addProperty("Contact",updateAllyProfile.getContact());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			Errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Errorcode = -1;
		}
		return Errorcode;
	}

	//List to get child and ally

	public GetListofChildsByParentIDList getGetListofChildsByParentIDList(int parentId)
	{
		GetListofChildsByParentIDList getListofChildsByParentIDList =null;

		String METHOD_NAME = "GetListofChildsByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getBadgeDetailsByChildIDOnInsightString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getBadgeDetailsByChildIDOnInsightString);
			JSONObject  onj = new JSONObject();
			onj.put("getListofChildsByParentID", arrayJson);

			Gson gson = new Gson();

			getListofChildsByParentIDList = gson.fromJson(onj.toString(), GetListofChildsByParentIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getListofChildsByParentIDList;
	}

	public GetListofAllysByParentIDList getGetListofAllysByParentIDList(int parentId)
	{
		GetListofAllysByParentIDList getListofAllysByParentIDList =null;

		String METHOD_NAME = "GetListofAllysByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getBadgeDetailsByChildIDOnInsightString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getBadgeDetailsByChildIDOnInsightString);
			JSONObject  onj = new JSONObject();
			onj.put("getListofAllysByParentID", arrayJson);

			Gson gson = new Gson();

			getListofAllysByParentIDList = gson.fromJson(onj.toString(), GetListofAllysByParentIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getListofAllysByParentIDList;
	}

	// get all the details of parent,child and ally form server

	//GetAllProfilesDetails

	public GetParentDetails getParentinformation(int parentId)
	{
		GetParentDetails getParentDetails =null;

		String METHOD_NAME = "GetAllProfilesDetails";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",parentId);
		request.addProperty("ProfileType",1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			getParentDetails = gson.fromJson(userString, GetParentDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getParentDetails;
	}

	public GetChildDetails getChildDetails(int childId)
	{
		GetChildDetails getChildDetails =null;

		String METHOD_NAME = "GetAllProfilesDetails";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",childId);
		request.addProperty("ProfileType",2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			getChildDetails = gson.fromJson(userString, GetChildDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getChildDetails;
	}

	public GetAllyDetails getAllyDetails(int allyId)
	{
		GetAllyDetails getAllyDetails =null;

		String METHOD_NAME = "GetAllProfilesDetails";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",allyId);
		request.addProperty("ProfileType",3);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			getAllyDetails = gson.fromJson(userString, GetAllyDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getAllyDetails;
	}

	public GetListofChildsByParentIDList getchildListByParentId(int parentId)
	{
		GetListofChildsByParentIDList getListofChildsByParentIDList=null;

		String METHOD_NAME = "GetListofChildsByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {
			httpTransport.call(SOAP_ACTION, envelope);
		} 
		catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String subjectActivitiesListString = getValidJson.getValidJsonArray(returnString);

		try {
			JSONArray arrayJson = new JSONArray(subjectActivitiesListString);

			JSONObject  onj = new JSONObject();
			onj.put("getListofChildsByParentID", arrayJson);

			Gson gson = new Gson();

			getListofChildsByParentIDList = gson.fromJson(onj.toString(), GetListofChildsByParentIDList.class);	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getListofChildsByParentIDList;
	}

	public GetListofAllysByParentIDList getAllyListByParentId(int parentId)
	{
		GetListofAllysByParentIDList getListofAllysByParentIDList=null;

		String METHOD_NAME = "GetListofAllysByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {
			httpTransport.call(SOAP_ACTION, envelope);
		} 
		catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String subjectActivitiesListString = getValidJson.getValidJsonArray(returnString);

		try {
			JSONArray arrayJson = new JSONArray(subjectActivitiesListString);

			JSONObject  onj = new JSONObject();
			onj.put("getListofAllysByParentID", arrayJson);

			Gson gson = new Gson();

			getListofAllysByParentIDList = gson.fromJson(onj.toString(), GetListofAllysByParentIDList.class);	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getListofAllysByParentIDList;
	}

	public  GetLocationDetails getLocationinformation(int parentId)
	{
		GetLocationDetails getLocationDetails =null;

		String METHOD_NAME = "GetAllProfilesDetails";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",parentId);
		request.addProperty("ProfileType",0);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			getLocationDetails = gson.fromJson(userString, GetLocationDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getLocationDetails;
	}


	//Notification Screen

	@SuppressWarnings("null")
	public GetNewNotificationCount getNewNotificationCount(int parentId)
	{
		GetNewNotificationCount getNewNotificationCountModel =null;

		String METHOD_NAME = "GetNewNotificationCount";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getNewNotificationCountString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject  onj = new JSONObject(getNewNotificationCountString);
			getNewNotificationCountModel=new GetNewNotificationCount();
			getNewNotificationCountModel.setCount(onj.getInt("Count"));



		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getNewNotificationCountModel;
	}






	public GetNotificationByNotificationID getNotificationByNotificationID(int notificationId)
	{
		GetNotificationByNotificationID getNotificationByNotificationIDModel =null;

		String METHOD_NAME = "GetNotificationByNotificationID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("NotificationID",notificationId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getNotificationByNotificationIdString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getNotificationByNotificationIdString);
			JSONObject  onj = new JSONObject();
			onj.put("getNotificationByNotificationId", arrayJson);

			Gson gson = new Gson();

			getNotificationByNotificationIDModel = gson.fromJson(onj.toString(), GetNotificationByNotificationID.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getNotificationByNotificationIDModel;
	}



	public GetNotificationListByParentIDList getNotificationListByParentIDList(int parentId)
	{
		GetNotificationListByParentIDList getNotificationListByParentIDListDummy =null;

		String METHOD_NAME = "GetNotificationListByParentID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getNotificationListByParentIDListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getNotificationListByParentIDListString);
			JSONObject  onj = new JSONObject();
			onj.put("getNotificationListByParentID", arrayJson);

			Gson gson = new Gson();

			getNotificationListByParentIDListDummy = gson.fromJson(onj.toString(), GetNotificationListByParentIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getNotificationListByParentIDListDummy;
	}

	public int checkDeviceIDExist(String deviceId)
	{
		int errorcode = 0;

		String METHOD_NAME = "CheckDeviceIDExists";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("DeviceID",deviceId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorcode;
	}








	public String deleteAllyByAllyid(int AllyId)
	{

		String METHOD_NAME = "DeleteAllyByAllyID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("AllyID",AllyId);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorCode();
	}









	public String addNotificationSettingsByProfileID(int ProfileID,String NotificationType,String Status)
	{

		String METHOD_NAME = "AddNotificationSettingsByProfileID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",ProfileID);
		request.addProperty("NotificationType",NotificationType);
		request.addProperty("Status",Status);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorCode();
	}



	public String deleteChildByChildId(int ChildId)
	{

		String METHOD_NAME = "DeleteChildByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildId);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorCode();
	}




	public String deleteScheduledActivityByActChildID(int ChildID,int ActivityID)
	{

		String METHOD_NAME = "DeleteScheduledActivityByActChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("ActivityID",ActivityID);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorCode();
	}


	public DisplayAllyListByChildAndActivityId getAllyListByChildAndActivityId(int childId,int activityId)
	{
		DisplayAllyListByChildAndActivityId displayAllyListByChildAndActivityId =null;

		String METHOD_NAME = "GetListOfAllysOnActivityByChildIDAct";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("ActivityID",activityId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String schoolActivtiyBydateListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(schoolActivtiyBydateListString);
			JSONObject  onj = new JSONObject();
			onj.put("displayAllyByChildAndActivityId", arrayJson);

			Gson gson = new Gson();

			displayAllyListByChildAndActivityId = gson.fromJson(onj.toString(), DisplayAllyListByChildAndActivityId.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return displayAllyListByChildAndActivityId;
	}



	//Insights status
	public int getinsightReportActiveStatus(int parentId,int childId)
	{
		int status =0;

		String METHOD_NAME = "InsightReportActiveStatus";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ParentID",parentId);
		request.addProperty("ChildID",childId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			status = object.getInt("Status");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = -1;
		}
		return status;
	}







	public SchedularHolidayList getListOfHolidays(int childId)
	{
		SchedularHolidayList listofHolidays =null;

		String METHOD_NAME = "GetListofHolidaysByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String listofHolidaysString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(listofHolidaysString);
			JSONObject  onj = new JSONObject();
			onj.put("holidayList", arrayJson);//this is always name oflist in model

			Gson gson = new Gson();

			listofHolidays = gson.fromJson(onj.toString(), SchedularHolidayList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listofHolidays;
	}



	public GetHolidayDetailsByHolidayDesc getHolidayDetails(int childId,String holidayDesc)
	{
		GetHolidayDetailsByHolidayDesc getHolidayDetailsByHolidayDesc =null;

		String METHOD_NAME = "GetHolidayDetailsByHolidayDesc";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);
		request.addProperty("HolidayDescription",holidayDesc);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			getHolidayDetailsByHolidayDesc = gson.fromJson(userString, GetHolidayDetailsByHolidayDesc.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getHolidayDetailsByHolidayDesc;
	}




	public String addHolidayDataToServer(AddHolidaysByChildIDModel addHolidayDataToServer)
	{


		String METHOD_NAME = "AddHolidaysByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",addHolidayDataToServer.getChildID());
		request.addProperty("HolidayDescription",addHolidayDataToServer.getHolidayDescription());
		request.addProperty("StartDate",addHolidayDataToServer.getStartDate());
		request.addProperty("EndDate",addHolidayDataToServer.getEndDate());


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		Error err = null ;
		try {

			err = getError();	


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return err.getErrorCode();
	}



	public String deleteScheduledHolidayByActChildID(int ChildID,String holidayDesc)
	{

		String METHOD_NAME = "DeleteHolidayByHolidayDesc";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("HolidayDescription",holidayDesc);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorCode();
	}



	/* Network Webservices */

	public GetFriendsListByLoggedIDList getFriendsListByLoggedID(int LoggedID,int PageIndex,int NumberOfRows)
	{
		GetFriendsListByLoggedIDList getFriendsListByLoggedID =null;

		String METHOD_NAME = "GetFriendsListByLoggedID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getFriendsListByLoggedID", arrayJson);

			Gson gson = new Gson();

			getFriendsListByLoggedID = gson.fromJson(onj.toString(), GetFriendsListByLoggedIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getFriendsListByLoggedID;
	}






	public SearchFriendListGloballyList getsearchFriendListGloballyList(String  SearchText,int LoggedID,int PageIndex,int NumberOfRows)
	{
		NumberOfRows=8;
		SearchFriendListGloballyList searchFriendListGloballyList =null;

		String METHOD_NAME = "SearchFriendListGlobally";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("SearchText",SearchText);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("searchFriendListGloballyList", arrayJson);

			Gson gson = new Gson();

			searchFriendListGloballyList = gson.fromJson(onj.toString(), SearchFriendListGloballyList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return searchFriendListGloballyList;
	}



	public String sendFriendRequest(String FriendID,String LoggedID )
	{

		String METHOD_NAME = "SendFriendRequest";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("FriendID",FriendID);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorDesc();
	}


	public String updateStatusOnAction(String FriendID,String LoggedID ,String actionFlag)
	{

		String METHOD_NAME = "UpdateStatusOnAction";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("FriendID",FriendID);
		request.addProperty("actionFlag",actionFlag);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorDesc();
	}





	public GetListOfPendingRequestsByLoggedIDList getPendingRequestsListByLoggedID(int LoggedID,int PageIndex,int NumberOfRows)
	{
		GetListOfPendingRequestsByLoggedIDList getListOfPendingRequestsByLoggedID =null;

		String METHOD_NAME = "GetListOfPendingRequestsByLoggedID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID+"");
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		System.setProperty("http.keepAlive", "false"); 
		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListOfPendingRequestsByLoggedID", arrayJson);

			Gson gson = new Gson();

			getListOfPendingRequestsByLoggedID = gson.fromJson(onj.toString(), GetListOfPendingRequestsByLoggedIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListOfPendingRequestsByLoggedID;
	}



	public GetPeopleYouMayKnowListByLoggedIDList getPeopleYouMayKnowListByLoggedID(int LoggedID,int PageIndex,int NumberOfRows)
	{
		GetPeopleYouMayKnowListByLoggedIDList getPeopleYouMayKnowListByLoggedID =null;

		String METHOD_NAME = "GetPeopleYouMayKnowListByLoggedID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID+"");
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		System.setProperty("http.keepAlive", "false"); 
		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getPeopleYouMayKnowListByLoggedID", arrayJson);

			Gson gson = new Gson();

			getPeopleYouMayKnowListByLoggedID = gson.fromJson(onj.toString(), GetPeopleYouMayKnowListByLoggedIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getPeopleYouMayKnowListByLoggedID;
	}




	public GetFriendDetailsByFriendIDList getFriendDetails(int LoggedID,String FriendID)
	{
		GetFriendDetailsByFriendIDList getFriendDetailsByFriendIDList =null;

		String METHOD_NAME = "GetFriendDetailsByFriendID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID+"");
		request.addProperty("FriendID",FriendID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		System.setProperty("http.keepAlive", "false"); 
		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getFriendDetailsByFriendIDList", arrayJson);

			Gson gson = new Gson();

			getFriendDetailsByFriendIDList = gson.fromJson(onj.toString(), GetFriendDetailsByFriendIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getFriendDetailsByFriendIDList;
	}




	public GetChildDetailsByChildIDList getChildDetailsByChildId(int LoggedID,int ChildID)
	{
		//ChildID=36;
		GetChildDetailsByChildIDList getChildDetailsByChildIDList =null;

		String METHOD_NAME = "GetChildDetailsByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("ChildID",ChildID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		System.setProperty("http.keepAlive", "false"); 
		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getChildDetailsByChildIDList", arrayJson);

			Gson gson = new Gson();

			getChildDetailsByChildIDList = gson.fromJson(onj.toString(), GetChildDetailsByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getChildDetailsByChildIDList;
	}




	public GetExhilaratorsListByChildIDList getExhilaratorsListByChildID(int LoggedID,int ChildID)
	{
		GetExhilaratorsListByChildIDList getExhilaratorsListByChildID =null;

		String METHOD_NAME = "GetExhilaratorsListByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("ChildID",ChildID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		System.setProperty("http.keepAlive", "false"); 
		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getExhilaratorsListByChildID", arrayJson);

			Gson gson = new Gson();

			getExhilaratorsListByChildID = gson.fromJson(onj.toString(), GetExhilaratorsListByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getExhilaratorsListByChildID;
	}


	public GetProfileDetailsByLoggedIDList getProfileDetailsByLoggedID(int LoggedID)
	{
		GetProfileDetailsByLoggedIDList getProfileDetailsByLoggedIDList =null;

		String METHOD_NAME = "GetProfileDetailsByLoggedID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		System.setProperty("http.keepAlive", "false"); 
		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getProfileDetailsByLoggedIDList", arrayJson);

			Gson gson = new Gson();

			getProfileDetailsByLoggedIDList = gson.fromJson(onj.toString(), GetProfileDetailsByLoggedIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getProfileDetailsByLoggedIDList;
	}



	public GetListOfPinWiNetworksByLoggedIDList getListOfPinWiNetworksByLoggedID(int LoggedID,int ChildID,int PageIndex,int NumberOfRows)
	{
		GetListOfPinWiNetworksByLoggedIDList getListOfPinWiNetworksByLoggedIDList =null;

		String METHOD_NAME = "GetListOfPinWiNetworksByLoggedID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("ChildID",ChildID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		System.setProperty("http.keepAlive", "false"); 
		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListOfPinWiNetworksByLoggedIDList", arrayJson);

			Gson gson = new Gson();

			getListOfPinWiNetworksByLoggedIDList = gson.fromJson(onj.toString(), GetListOfPinWiNetworksByLoggedIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListOfPinWiNetworksByLoggedIDList;
	}




	/* WhatToDo Webservices */

	public GetListOfClustersOnRecommendedByChildIDList getListOfClustersOnRecommendedByChildID(int ChildID,int PageIndex,int NumberOfRows)
	{
		GetListOfClustersOnRecommendedByChildIDList getListOfClusters =null;

		String METHOD_NAME = StaticVariables.webserviceName;
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListOfClustersOnRecommendedByChildID", arrayJson);

			Gson gson = new Gson();

			getListOfClusters = gson.fromJson(onj.toString(), GetListOfClustersOnRecommendedByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListOfClusters;
	}





	public GetListOfActivitiesOnRecommendedByClusterIDList getListOfActivities(int ClusterID,int PageIndex,int NumberOfRows, String activityListWebserviceName)
	{
		GetListOfActivitiesOnRecommendedByClusterIDList getListOfActivitiesOnRecommendedByClusterID =null;

		String METHOD_NAME = activityListWebserviceName;
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		//if(activityListWebserviceName.contains("Network"))
		{
			request.addProperty("ChildID",StaticVariables.currentChild.getChildID()/*child id*/);
		}
		request.addProperty("ClusterID",ClusterID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListOfActivitiesOnRecommendedByClusterID", arrayJson);

			Gson gson = new Gson();

			getListOfActivitiesOnRecommendedByClusterID = gson.fromJson(onj.toString(), GetListOfActivitiesOnRecommendedByClusterIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListOfActivitiesOnRecommendedByClusterID;
	}





	public SearchActivitiesByChildIDList searchActivitiesList(int ChildID,int ClusterID,String SearchText,int PageIndex,int NumberOfRows, String searchWebserviceName)
	{
		SearchActivitiesByChildIDList searchActivitiesByChildID =null;

		String METHOD_NAME = searchWebserviceName;
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("ClusterID",ClusterID);
		request.addProperty("SearchText",SearchText);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("searchActivitiesByChildID", arrayJson);

			Gson gson = new Gson();

			searchActivitiesByChildID = gson.fromJson(onj.toString(), SearchActivitiesByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return searchActivitiesByChildID;
	}





	public GetWishListsByChildIDList getWishlist(int ChildID,int PageIndex,int NumberOfRows)
	{
		GetWishListsByChildIDList getWishListsByChildID =null;

		String METHOD_NAME = "GetWishListsByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getWishListsByChildID", arrayJson);

			Gson gson = new Gson();

			getWishListsByChildID = gson.fromJson(onj.toString(), GetWishListsByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getWishListsByChildID;
	}





	public SearchWishListByChildIDList searchWishList(int ChildID,int ClusterID,String SearchText,int PageIndex,int NumberOfRows)
	{
		SearchWishListByChildIDList searchWishListByChildIDList =null;

		String METHOD_NAME = "SearchWishListByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		//request.addProperty("ClusterID",ClusterID);
		request.addProperty("SearchText",SearchText);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("searchWishListByChildIDList", arrayJson);

			Gson gson = new Gson();

			searchWishListByChildIDList = gson.fromJson(onj.toString(), SearchWishListByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return searchWishListByChildIDList;
	}



	public GetChildsDetailsOnRecommendedByActivityIDList getChildDetails(int ActivityID,int PageIndex,int NumberOfRows)
	{
		GetChildsDetailsOnRecommendedByActivityIDList getChildsDetailsOnRecommendedByActivityID =null;

		String METHOD_NAME = "GetChildsDetailsOnRecommendedByActivityID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ActivityID",ActivityID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getChildsDetailsOnRecommendedByActivityID", arrayJson);

			Gson gson = new Gson();

			getChildsDetailsOnRecommendedByActivityID = gson.fromJson(onj.toString(), GetChildsDetailsOnRecommendedByActivityIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getChildsDetailsOnRecommendedByActivityID;
	}




	public GetListOfBuddiesByChildIDOnCIList getBuddiesList(int ChildID,int getType,int PageIndex,int NumberOfRows)
	{
		GetListOfBuddiesByChildIDOnCIList getListOfBuddiesByChildIDOnCI =null;

		String METHOD_NAME = "GetListOfBuddiesByChildIDOnCI";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("getType",getType);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListOfBuddiesByChildIDOnCI", arrayJson);

			Gson gson = new Gson();

			getListOfBuddiesByChildIDOnCI = gson.fromJson(onj.toString(), GetListOfBuddiesByChildIDOnCIList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListOfBuddiesByChildIDOnCI;
	}


	public GetChildDetailsOnBuddiesByChildIDOnCIList getChildDetailsBuddies(int ChildID)
	{
		GetChildDetailsOnBuddiesByChildIDOnCIList getChildDetailsOnBuddiesByChildIDOnCIList =null;

		String METHOD_NAME = "GetChildDetailsOnBuddiesByChildIDOnCI ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);




		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getChildDetailsOnBuddiesByChildIDOnCIList", arrayJson);

			Gson gson = new Gson();

			getChildDetailsOnBuddiesByChildIDOnCIList = gson.fromJson(onj.toString(), GetChildDetailsOnBuddiesByChildIDOnCIList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getChildDetailsOnBuddiesByChildIDOnCIList;
	}


	public SearchListOfBuddiesOnCIList getSearchListBuddies(int ChildID, String searchtexthere,int PageIndex, int NumberOfRows)
	{
		SearchListOfBuddiesOnCIList searchListOfBuddiesOnCI =null;

		String METHOD_NAME = "SearchListOfBuddiesOnCI ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("searchtexthere",searchtexthere);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("searchListOfBuddiesOnCI", arrayJson);

			Gson gson = new Gson();

			searchListOfBuddiesOnCI = gson.fromJson(onj.toString(), SearchListOfBuddiesOnCIList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return searchListOfBuddiesOnCI;
	}



	public GetNotificationListByChildIDOnCIList getNotificationList(int ChildID,int PageIndex,int NumberOfRows)
	{
		GetNotificationListByChildIDOnCIList getNotificationListByChildIDOnCI =null;

		String METHOD_NAME = "GetNotificationListByChildIDOnCI ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);




		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getNotificationListByChildIDOnCI", arrayJson);

			Gson gson = new Gson();

			getNotificationListByChildIDOnCI = gson.fromJson(onj.toString(), GetNotificationListByChildIDOnCIList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getNotificationListByChildIDOnCI;
	}


	public int getNewNotificationCountOnCI(int ChildID)
	{
		int count =0;

		String METHOD_NAME = "GetNewNotificationCountOnCI";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			count = object.getInt("Count");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//status = -1;
		}
		return count;
	}



	public String sendFriendRequestOnCI(String FriendID,String LoggedID )
	{

		String METHOD_NAME = "SendFriendRequestOnCI";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("FriendID",FriendID);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorDesc();
	}


	public String actionOnPendingRequestsOnCI(String FriendID,String LoggedID ,String actionFlag)
	{

		String METHOD_NAME = "ActionOnPendingRequestsOnCI";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("FriendID",FriendID);
		request.addProperty("actionFlag",actionFlag);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorDesc();
	}



	public String addWishlistByChildID(String ChildID,String ActivityID )
	{

		String METHOD_NAME = "AddWishlistByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("ActivityID",ActivityID);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorDesc();
	}


	public SearchActivityByNameList searchActivityByName(int ChildID,String searchtexthere,int PageIndex,int NumberOfRows)
	{
		SearchActivityByNameList searchActivityByName =null;
		String METHOD_NAME = "SearchActivityByName ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("searchtexthere",searchtexthere);
		request.addProperty("NumberOfRows",NumberOfRows);
		request.addProperty("PageIndex",PageIndex);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("searchActivityByName", arrayJson);

			Gson gson = new Gson();

			searchActivityByName = gson.fromJson(onj.toString(), SearchActivityByNameList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return searchActivityByName;
	}



	public GetListofChildrensByChildActIDList getListofChildrensByChildActID(int ChildID,String ActivityID,int NumberOfRows,int PageIndex)
	{
		GetListofChildrensByChildActIDList getListofChildrensByChildActID =null;

		String METHOD_NAME = "GetListofChildrensByChildActID ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("ActivityID",ActivityID);
		request.addProperty("NumberOfRows",NumberOfRows);
		request.addProperty("PageIndex",PageIndex);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListofChildrensByChildActID", arrayJson);

			Gson gson = new Gson();

			getListofChildrensByChildActID = gson.fromJson(onj.toString(), GetListofChildrensByChildActIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListofChildrensByChildActID;
	}






	public GetListOfWishListsByChildIDList getListOfWishListsByChildID(int ChildID,int getType,int PageIndex,int NumberOfRows)
	{
		GetListOfWishListsByChildIDList getListOfWishListsByChildIDList =null;

		String METHOD_NAME = "GetListOfWishListsByChildID ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);
		request.addProperty("getType",getType);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListOfWishListsByChildIDList", arrayJson);

			Gson gson = new Gson();

			getListOfWishListsByChildIDList = gson.fromJson(onj.toString(), GetListOfWishListsByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListOfWishListsByChildIDList;
	}


	public GetListOfMessageTempletesList getListOfMessageTempletesList()
	{
		GetListOfMessageTempletesList getListOfMessageTempletes =null;

		String METHOD_NAME = "GetListOfMessageTempletes ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getListOfMessageTempletes", arrayJson);

			Gson gson = new Gson();

			getListOfMessageTempletes = gson.fromJson(onj.toString(), GetListOfMessageTempletesList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getListOfMessageTempletes;
	}


	public String addPostcard(int TemplateID,int LoggedID,int actionType, String Text )
	{
		String METHOD_NAME = "AddViewsOnMessageTempletesByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("TemplateID",TemplateID);
		request.addProperty("actionType",actionType);
		request.addProperty("Text",Text);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return errorMessage;
	}



	public GetFriendsTempleteMessageListByChildIDList addEmoticByMapID(int MapID,int LoggedID,int EmoticID )
	{


		GetFriendsTempleteMessageListByChildIDList getFriendsTempleteMessageListByChildIDList =null;

		String METHOD_NAME = "AddEmoticByMapID ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("MapID",MapID);
		request.addProperty("EmoticID",EmoticID);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getFriendsTempleteMessageListByChildIDList", arrayJson);

			Gson gson = new Gson();

			getFriendsTempleteMessageListByChildIDList = gson.fromJson(onj.toString(), GetFriendsTempleteMessageListByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getFriendsTempleteMessageListByChildIDList;


		/*	d
		String METHOD_NAME = "AddEmoticByMapID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("MapID",MapID);
		request.addProperty("EmoticID",EmoticID);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return errorMessage;*/
	}


	public GetFriendsTempleteMessageListByChildIDList getFriendsTempleteMessageListByChildID(int LoggedID,int getType,int PageIndex,int NumberOfRows)
	{
		GetFriendsTempleteMessageListByChildIDList getFriendsTempleteMessageListByChildIDList =null;

		String METHOD_NAME = "GetFriendsTempleteMessageListByChildID ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("getType",getType);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getFriendsTempleteMessageListByChildIDList", arrayJson);

			Gson gson = new Gson();

			getFriendsTempleteMessageListByChildIDList = gson.fromJson(onj.toString(), GetFriendsTempleteMessageListByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if(getFriendsTempleteMessageListByChildIDList!=null )
		{
			for(int i=0;i<getFriendsTempleteMessageListByChildIDList.getFriendsTempleteMessageListByChildID().size();i++)
			{
				colorIndexSelected();
				final GetFriendsTempleteMessageListByChildID model=getFriendsTempleteMessageListByChildIDList.getFriendsTempleteMessageListByChildID().get(i);
				model.setColorHeading(innerColor[previousColorIndex]);
				getFriendsTempleteMessageListByChildIDList.getFriendsTempleteMessageListByChildID().set(i, model);
			}
		}
		return getFriendsTempleteMessageListByChildIDList;
	}


	public GetDetailByMapEmoticIDList getDetailByMapEmoticID(int j,int k,int PageIndex,int NumberOfRows)
	{
		GetDetailByMapEmoticIDList getDetailByMapEmoticIDList =null;

		String METHOD_NAME = "GetDetailByMapEmoticID ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("MapID",j);
		request.addProperty("EmoticID",k);

		//	request.addProperty("getType",getType);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getDetailByMapEmoticIDList", arrayJson);

			Gson gson = new Gson();

			getDetailByMapEmoticIDList = gson.fromJson(onj.toString(), GetDetailByMapEmoticIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getDetailByMapEmoticIDList;
	}


	public GetDetailByChildIDList getDetailByChildID(int mapId)
	{
		GetDetailByChildIDList getDetailByChildIDList =null;

		String METHOD_NAME = "GetDetailByChildID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("MapID",mapId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getDetailByChildIDList", arrayJson);

			Gson gson = new Gson();

			getDetailByChildIDList = gson.fromJson(onj.toString(), GetDetailByChildIDList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getDetailByChildIDList;
	}
	private int previousColorIndex = 0;
	private Random random;
	private String[] innerColor = {"#6a24ae","#f7941d","#fdb813","#64ad17","#acc138","#f2716c","#5b9ddd","#a3238e"};

	private void colorIndexSelected(){
		int randomNumber = random.nextInt(innerColor.length - 1);
		if(randomNumber == previousColorIndex){
			colorIndexSelected();
		}else{
			previousColorIndex = randomNumber;
		}
	}



	public GetFriendsListByLoggedIDOnCIList getListofBuddies(int LoggedID,int NumberOfRows,int PageIndex)
	{
		GetFriendsListByLoggedIDOnCIList getFriendsListByLoggedID =null;

		String METHOD_NAME = "GetFriendsListByLoggedIDOnCI ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("LoggedID",LoggedID);
		request.addProperty("NumberOfRows",NumberOfRows);
		request.addProperty("PageIndex",PageIndex);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("getFriendsListByLoggedID", arrayJson);

			Gson gson = new Gson();

			getFriendsListByLoggedID = gson.fromJson(onj.toString(), GetFriendsListByLoggedIDOnCIList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return getFriendsListByLoggedID;
	}






	public void initRandom()
	{
		random = new Random();
		previousColorIndex = 0;
	}

	public ResultIsSubscribeList isSubscribed(int ProfileID,int DeviceType)
	{
		ResultIsSubscribeList isSubscribeList =null;

		String METHOD_NAME = "IsSubscriptionValid ";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",ProfileID);
		request.addProperty("DeviceType",DeviceType);
		request.addProperty("ChildID",StaticVariables.currentChild.getChildID());


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		System.setProperty("http.keepAlive", "false"); 


		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("isSubscribeList", arrayJson);

			Gson gson = new Gson();

			isSubscribeList = gson.fromJson(onj.toString(), ResultIsSubscribeList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isSubscribeList;
	}


	public String addSubscription(int ProfileID,String DeviceID,int DeviceType,String SubscriptionType,String Amount)
	{

		String METHOD_NAME = "AddSubscription";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",ProfileID);
		request.addProperty("DeviceID",DeviceID);
		request.addProperty("DeviceType",DeviceType);
		request.addProperty("SubscriptionType",Integer.parseInt(SubscriptionType));
		request.addProperty("Amount",Amount);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}


		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		//String userString = getValidJson.getValidJsonObject(returnString);
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return err.getErrorCode();
	}




	public int recoverPasscodeChild(int profileId)
	{
		int errorcode = 0;
		String METHOD_NAME = "RecoverPasscodeForChild";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",profileId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorcode;
	}



	public int appLauncherByDeviceID(String DeviceID,int DeviceType)
	{
		int errorcode = 0;

		String METHOD_NAME = "AppLauncherByDeviceID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("DeviceID",DeviceID);
		request.addProperty("DeviceType",DeviceType);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		/*String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return errorcode;
	}



	public AuthenticateUserResult authenticateUserWithDeviceID(AuthenticateUser userCrenditial,String DeviceID)
	{
		AuthenticateUserResult userResult =null;

		String METHOD_NAME = "AuthenticateUserWithDeviceID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("EmailAddress",userCrenditial.getEmailAddress());
		request.addProperty("Password",userCrenditial.getPassword());
		request.addProperty("DeviceID",DeviceID);
		request.addProperty("DeviceType",1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;
		String userString = getValidJson.getValidJsonObject(returnString);
		try {

			Gson gson = new Gson();

			userResult = gson.fromJson(userString, AuthenticateUserResult.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userResult;
	}





	public Error setRemainderByProfileID(int ProfileID/*,int ProfileMode*/,int Frequency,String Time, int flag)
	{
		int errorcode = 0;

		String METHOD_NAME = "SetRemainderByProfileID";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ProfileID",ProfileID);
		request.addProperty("ProfileMode",/*ProfileMode*/1);
		request.addProperty("Frequency",Frequency);
		request.addProperty("Time",Time);
		request.addProperty("IsActive",flag);//1-on 0-off
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't
		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} // send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);
		errorMessage  = returnString;
		Error err = null ;
		try {

			err = getError();	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int code=0;;
		try {
			code = Integer.parseInt(err.getErrorCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return err;
		/*	String userString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject object = new JSONObject(userString);
			errorcode = object.getInt("ErrorCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorcode;*/
	}



	public GetInterestPatternByChildIDOnInsightList getInterestPatternByChildIDOnInsightList(int childId)
	{
		GetInterestPatternByChildIDOnInsightList getInterestPatternByChildIDOnInsight =null;

		String METHOD_NAME = "GetInterestPatternByChildIDOnInsight";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",childId);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getInterestTraitsByChildIDOnInsight = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(getInterestTraitsByChildIDOnInsight);
			JSONObject  onj = new JSONObject();
			onj.put("getInterestPatternByChildIDOnInsight", arrayJson);

			Gson gson = new Gson();

			getInterestPatternByChildIDOnInsight = gson.fromJson(onj.toString(), GetInterestPatternByChildIDOnInsightList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getInterestPatternByChildIDOnInsight;
	}


	public LocalityList getLocality(int cityId)
	{

		LocalityList locality =null;

		String METHOD_NAME = "GetLocalityList";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("CityID",cityId);
		request.addProperty("SearchText","");


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String cityListString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(cityListString);
			JSONObject  onj = new JSONObject();
			onj.put("locality", arrayJson);

			Gson gson = new Gson();

			locality = gson.fromJson(onj.toString(), LocalityList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return locality;

	}


	public SearchActivitiesByActivityNameList searchActivityByName(String  SearchText)
	{
		//NumberOfRows=8;
		SearchActivitiesByActivityNameList searchActivitiesByActName =null;

		String METHOD_NAME = "SearchActivity";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("SearchText",SearchText);
		/*request.addProperty("LoggedID",LoggedID);
		request.addProperty("PageIndex",PageIndex);
		request.addProperty("NumberOfRows",NumberOfRows);*/

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;




		String userString = getValidJson.getValidJsonArray(returnString);
		try {
			JSONArray arrayJson = new JSONArray(userString);
			JSONObject  onj = new JSONObject();
			onj.put("searchActivitiesByActName", arrayJson);

			Gson gson = new Gson();

			searchActivitiesByActName = gson.fromJson(onj.toString(), SearchActivitiesByActivityNameList.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return searchActivitiesByActName;
	}


	@SuppressWarnings("null")
	public GetPercentageCount getPercentageCountOnCI(int ChildID)
	{
		GetPercentageCount getPercentageCount =null;

		String METHOD_NAME = "GetPercentageCountOnCI";
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty(WSIDKEY, WSID);
		request.addProperty(WSPWDKEY,WSPWD);
		request.addProperty("ChildID",ChildID);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SOAP_VERSION); // put

		envelope.dotNet = true;

		envelope.setAddAdornments(false);
		envelope.implicitTypes = false;
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(URL, TIMEOUT);

		httpTransport.debug = DEBUG; // this is optional, use it if you don't

		// want to use a packet sniffer to check
		// what the sent
		// message was (httpTransport.requestDump)
		httpTransport.setXmlVersionTag(HEADER);

		try {

			httpTransport.call(SOAP_ACTION, envelope);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// send request
		Log.d("RAM RAM3", "XML: " + httpTransport.requestDump);

		SoapObject result = null;
		String returnString = "";
		try {
			envelope.getResponse();
			result = (SoapObject) envelope.bodyIn;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				returnString = result.getProperty(i).toString();
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Log.d("Response of Webservice: Method: "+METHOD_NAME, " " + returnString);

		errorMessage  = returnString;

		String getNewNotificationCountString = getValidJson.getValidJsonObject(returnString);
		try {

			JSONObject  onj = new JSONObject(getNewNotificationCountString);
			getPercentageCount=new GetPercentageCount();
			getPercentageCount.setGetPercentageCountOnCI(onj.getString("GetPercentageCountOnCI"));



		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getPercentageCount;
	}






}
