package com.hatchtact.pinwi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.child.postcard.CompressImage;
import com.hatchtact.pinwi.classmodel.CityList;
import com.hatchtact.pinwi.classmodel.CountryList;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetLocationDetails;
import com.hatchtact.pinwi.classmodel.GetNeighbourhoodRadiusList;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.UpdateLocationByParentID;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.GettingLattitude;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;
import com.hatchtact.pinwi.view.AutoCompleteAdapter;

public class LocationActivity extends MainActionBarActivity implements OnTouchListener, LocationListener
{
	private EditText flat_editText=null;
	private EditText street_editText=null;
	private TextView text_neighbourhood = null;
	private AutoCompleteTextView city_autoCompleteTextView=null;
	private AutoCompleteTextView country_autoCompleteTextView=null;
	private AutoCompleteTextView place_autoCompView=null;
	private AutoCompleteTextView neighbourHood_radius_autoCompleteTextView=null;
	private Button continue_button=null;

	private TypeFace typeFace=null; 

	private Validation validation=null;
	private ShowMessages showMessages=null;
	private CountryList countryList=null;
	private CityList cityList=null;
	private GetNeighbourhoodRadiusList getNeighbourhoodRadiusList=null;

	private ParentProfile parentProfile=null;
	private	ServiceMethod serviceMethod;

	private ArrayList<String> countryStringList=new ArrayList<String>();
	private ArrayList<String> cityStringList=new ArrayList<String>();
	private ArrayList<String> neighbourhoodRadiusStringList=new ArrayList<String>();

	private int CountryId=0;

	private GoogleMap googleMap;

	//private String location=null;

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	//---------- -- make your specific key ------------
	private static final String API_KEY = "AIzaSyAVQ7WteFKSk-Jv4InrEoibZzQkTTwPOXI";

	private static final String LOG_TAG = "PinwiDemo App";

	private CheckNetwork checkNetwork=null;

	private Gson gsonRegistration=null;

	private Context mContext;

	private SharePreferenceClass sharePref=null;

	String URLTOGetLatLongPreffix = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	String URLTOGetLatLongSuffix = "&sensor=true_or_false";

	private boolean onTouchContinueButton=false;

	private boolean isGPSEnabled;
	private boolean isNetworkEnabled;
	private boolean canGetLocation;

	private boolean locationUpdate=false;
	private UpdateLocationByParentID updateLocationByParentID=null;

	/*private TextView text_countryName=null;
	private TextView text_cityName=null;*/

	private int parentId;
	private TextView text_optional;
	private TextView text_optionalBelowNeighbour;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		screenName="Location Setup";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.location_activity);

		mContext = LocationActivity.this;

		onTouchContinueButton=false;

		checkNetwork=new CheckNetwork();

		gsonRegistration = new GsonBuilder().create();

		sharePref=new SharePreferenceClass(this);
		serviceMethod = new ServiceMethod();

		place_autoCompView = (AutoCompleteTextView) findViewById(R.id.place_autoCompleteTextView);

		place_autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_place));

		place_autoCompView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String str = (String) arg0.getItemAtPosition(arg2);
				place_autoCompView.setText(str);
				new getLattAndLongFromAddress(str).execute();
			}
		});

		parentProfile = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
		parentId=parentProfile.getParentID();

		Bundle bundle = getIntent().getExtras();

		if(bundle!=null)
		{
			try {
				locationUpdate = bundle.getBoolean("ToLocationScreen");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(bundle.getSerializable("parentProfile")!=null)
			{
				try {
					parentProfile =(ParentProfile) bundle.getSerializable("parentProfile");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		typeFace= new TypeFace(LocationActivity.this);
		validation=new Validation();
		showMessages=new ShowMessages(LocationActivity.this);
		countryList=new CountryList();
		cityList=new CityList();
		getNeighbourhoodRadiusList=new GetNeighbourhoodRadiusList();

		flat_editText=(EditText) findViewById(R.id.text_flat);
		street_editText=(EditText) findViewById(R.id.text_street);
		city_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_city);
		country_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_country);
		neighbourHood_radius_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_nearRadius);
		continue_button=(Button) findViewById(R.id.button_continueLocation);
		text_neighbourhood = (TextView) findViewById(R.id.text_neighbourhood);

		text_optional = (TextView) findViewById(R.id.text_optional);
		text_optionalBelowNeighbour = (TextView) findViewById(R.id.text_optionalBelowNeighbour);
		/*text_countryName=(TextView) findViewById(R.id.text_countryName);
		text_cityName=(TextView) findViewById(R.id.text_cityName);
		 */
		initilizeMap();

		location = null;
		try {
			googleMap.setMyLocationEnabled(true);

			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String bestProvider = locationManager.getBestProvider(criteria, true);
			location = locationManager.getLastKnownLocation(bestProvider);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(location==null)
		{
			location=getLocation();
		}

		if(googleMap!=null)
		{
			if(googleMap.getMyLocation()!=null)
			{
				LatLng 	currentPosition = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());	
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
				//fitZoomAndPositionToMapByMarkers(currentPosition);
			}
			else
			{
				if(location!=null)
				{
					LatLng 	currentPosition = new LatLng(location.getLatitude(),location.getLongitude());	
					googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
				}
				//fitZoomAndPositionToMapByMarkers(currentPosition);
			}
		}

		typeFace.setTypefaceLight(flat_editText);
		typeFace.setTypefaceLight(street_editText);	
		typeFace.setTypefaceLight(city_autoCompleteTextView);
		typeFace.setTypefaceLight(country_autoCompleteTextView);
		typeFace.setTypefaceLight(neighbourHood_radius_autoCompleteTextView);
		typeFace.setTypefaceRegular(text_neighbourhood);
		typeFace.setTypefaceRegular(continue_button);
		typeFace.setTypefaceLight(place_autoCompView);
		typeFace.setTypefaceLight(text_optional);
		typeFace.setTypefaceLight(text_optionalBelowNeighbour);


		/*typeFace.setTypefaceRegular(text_countryName);
		typeFace.setTypefaceRegular(text_cityName);*/

		country_autoCompleteTextView.setOnTouchListener(this);
		city_autoCompleteTextView.setOnTouchListener(this);
		neighbourHood_radius_autoCompleteTextView.setOnTouchListener(this);


		country_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				String textSelected = arg0.getItemAtPosition(arg2).toString();	
				int currentCountryId = 0;

				for(int i=0;i<countryList.getCountry().size();i++)
				{
					if(textSelected.trim().equalsIgnoreCase(countryList.getCountry().get(i).getCountryName().trim()))
					{
						currentCountryId=countryList.getCountry().get(i).getCountryID();
					}
				}

				if(currentCountryId==CountryId)
				{
					System.out.println("current CountryId"+currentCountryId+"previous id"+CountryId);
				}
				else
				{

					new GetListOfField("city",currentCountryId).execute();	
					CountryId=currentCountryId;
				}
			}
		});

		city_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

		neighbourHood_radius_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				// TODO Auto-generated method stub
				String textSelectedNeighbourhoodRadius =arg0.getItemAtPosition(arg2).toString().trim();
				try {
					radius=Integer.parseInt(textSelectedNeighbourhoodRadius.split("KM")[0].trim());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					radius=5;
				}
				if(googleMap.getMyLocation()!=null)
				{
					LatLng 	currentPosition = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());	
					drawCircleMap(currentPosition);
					//googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
					//fitZoomAndPositionToMapByMarkers(currentPosition);
				}
				else
				{
					if(location!=null)
					{
						LatLng 	currentPosition = new LatLng(location.getLatitude(),location.getLongitude());	
						drawCircleMap(currentPosition);
						googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
					}
					//fitZoomAndPositionToMapByMarkers(currentPosition);
				}

			}
		});

		continue_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*if(!validation.isNotNullOrBlank(flat_editText.getText().toString()))
					showMessages.showAlert("Warning", "Please enter your flat address");			
				else */
				if(!validation.isNotNullOrBlank(city_autoCompleteTextView.getText().toString()) && !validation.isNotNullOrBlank(country_autoCompleteTextView.getText().toString())
						/*&& !validation.isNotNullOrBlank(street_editText.getText().toString())*/)
				{
					showMessages.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				}

				else if(!validation.isNotNullOrBlank(country_autoCompleteTextView.getText().toString()))
				{
					showMessages.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
					country_autoCompleteTextView.requestFocus();
				}
				else if(!validation.isNotNullOrBlank(city_autoCompleteTextView.getText().toString()))
				{
					showMessages.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
					city_autoCompleteTextView.requestFocus();

				}
				/*else if(!validation.isNotNullOrBlank(street_editText.getText().toString()))
				{
					showMessages.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
					street_editText.requestFocus();
				}*/
				/*else if(!validation.isNotNullOrBlank(neighbourHood_radius_autoCompleteTextView.getText().toString()))
					showMessages.showAlert("Warning", "Please enter nearer radius");
				else if(!validation.isNotNullOrBlank(place_autoCompView.getText().toString()))
					showMessages.showAlert("Warning", "Please enter address");*/
				else
				{
					String textSelectedCity = city_autoCompleteTextView.getText().toString();	

					for(int i=0;i<cityList.getCity().size();i++)
					{
						if(textSelectedCity.trim().equalsIgnoreCase(cityList.getCity().get(i).getCityName().trim()))
						{
							parentProfile.setCityID(cityList.getCity().get(i).getCityID());	

							parentProfile.setCity(textSelectedCity);
						}
					}

					String textSelectedCountry = country_autoCompleteTextView.getText().toString();	

					for(int i=0;i<countryList.getCountry().size();i++)
					{
						if(textSelectedCountry.trim().equalsIgnoreCase(countryList.getCountry().get(i).getCountryName().trim()))
						{
							parentProfile.setCountryID(countryList.getCountry().get(i).getCountryID());	
							parentProfile.setCountry(textSelectedCountry);
						}
					}

					String textSelectedNeighbourhoodRadius = neighbourHood_radius_autoCompleteTextView.getText().toString();	

					for(int i=0;i<getNeighbourhoodRadiusList.getGetNeighbourhoodRadius().size();i++)
					{
						if(textSelectedNeighbourhoodRadius.trim().equalsIgnoreCase(getNeighbourhoodRadiusList.getGetNeighbourhoodRadius().get(i).getRadiusValue().trim()))
						{
							parentProfile.setNeighbourhoodID(getNeighbourhoodRadiusList.getGetNeighbourhoodRadius().get(i).getNeighbourhoodID());				
							parentProfile.setNeighbourhoodRadius(textSelectedNeighbourhoodRadius);
						}
					}

					parentProfile.setFlatNoBuilding(flat_editText.getText().toString());
					parentProfile.setStreetLocality(street_editText.getText().toString());
					parentProfile.setGoogleMapAddress(place_autoCompView.getText().toString());
					parentProfile.setLatitude(String.valueOf(GettingLattitude.lat));
					parentProfile.setLongitude(String.valueOf(GettingLattitude.lng));

					String IMEI = "";
					try {
						TelephonyManager tManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

						IMEI = tManager.getDeviceId();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					parentProfile.setDeviceID(IMEI);
					parentProfile.setDeviceToken(IMEI);

					if(locationUpdate)
					{
						//Create UpdateLocation object...call webservice

						updateLocationByParentID  = new UpdateLocationByParentID();

						updateLocationByParentID.setParentID(parentId);
						updateLocationByParentID.setFlatNoBuilding(parentProfile.getFlatNoBuilding());
						updateLocationByParentID.setStreetLocality(parentProfile.getStreetLocality());
						updateLocationByParentID.setCity(parentProfile.getCityID()+"");
						updateLocationByParentID.setCountry(parentProfile.getCountryID()+"");
						updateLocationByParentID.setGoogleMapAddress(parentProfile.getGoogleMapAddress());
						updateLocationByParentID.setLongitude(parentProfile.getLongitude());
						updateLocationByParentID.setLatitude(parentProfile.getLatitude());
						updateLocationByParentID.setNeighbourhoodRadius(parentProfile.getNeighbourhoodID()+"");

						new UpdateParentInformationOnServer().execute();	
					}
					else
					{
						new RegisterParentTask().execute();
					}	
				}
			}
		});

		//if(!StaticVariables.isSignUpClicked)
		{

			if(!locationUpdate)
			{
				addPreFilledData();
				showAlertLocation("Location Set Up", "Knowing your Location helps us send you more targetted information. You may choose not to share your exact address, but we do need to know your city and locality.\n You also have the option to search and Select Neighbourhood  you are comfortable operating in and around your home or office for your children's activity.\n Allowing the app to access your location ensures we stay relevant with our information at all times. ");
			}
			else
			{
				setDataFromServer();
				new GetListOfField("country",0).execute();
				new GetListOfNeighbourhoodRadius().execute();
				new GetListOfField("city",1).execute();
			}
		}
		/*else
   {
		showAlertLocation("Location Set Up", "Knowing your Location helps us send you more targetted information. You may choose not to share your exact address, but we do need to know your city and locality.\n You also have the option to search and Select Neighbourhood  you are comfortable operating in and around your home or office for your children's activity.\n Allowing the app to access your location ensures we stay relevant with our information at all times. ");

   }*/
	}

	private ProgressDialog progressDialog3=null;	

	private class UpdateParentInformationOnServer extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog3 = ProgressDialog.show(LocationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog3.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(LocationActivity.this))
			{
				ErrorCode = serviceMethod.getupdateLocation(updateLocationByParentID);
			}
			else 
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer  result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if (progressDialog3.isShowing())
					progressDialog3.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			onTouchContinueButton=false;

			if(result==-1)
			{
				showMessages.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(LocationActivity.this))
					new UpdateParentInformationOnServer().execute();
			}
			else
			{
				if(result==0)
				{
					Toast.makeText(LocationActivity.this, "Parent Location is updated.", Toast.LENGTH_SHORT).show();

					parentProfile = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);

					parentProfile.setParentID(updateLocationByParentID.getParentID());
					parentProfile.setFlatNoBuilding(updateLocationByParentID.getFlatNoBuilding());
					parentProfile.setStreetLocality(updateLocationByParentID.getStreetLocality());
					parentProfile.setCity(updateLocationByParentID.getCity());
					parentProfile.setCountry(updateLocationByParentID.getCountry());
					parentProfile.setGoogleMapAddress(updateLocationByParentID.getGoogleMapAddress());
					parentProfile.setLongitude(updateLocationByParentID.getLongitude());
					parentProfile.setLatitude(updateLocationByParentID.getLatitude());
					parentProfile.setNeighbourhoodID(Integer.parseInt(updateLocationByParentID.getNeighbourhoodRadius()));

					String parentInformation = gsonRegistration.toJson(parentProfile);
					sharePref.setParentProfile(parentInformation);  
					LocationActivity.this.finish();	
				}
				else
				{
					getError();
				}
			}	
		}	
	}

	private void setDataFromServer()
	{
		// TODO Auto-generated method stub

		continue_button.setText("Save");

		new GetLocationDetailFromServer(parentId).execute();
	}

	private ProgressDialog progressDialog2=null;	

	private class GetLocationDetailFromServer extends AsyncTask<Void, Void, Integer>
	{
		private int parentId;

		private GetLocationDetails locationInformationFromServer=null;

		public GetLocationDetailFromServer(int parentId)
		{
			// TODO Auto-generated constructor stub 
			this.parentId = parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog2 = ProgressDialog.show(LocationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog2.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(LocationActivity.this))
			{
				locationInformationFromServer = serviceMethod.getLocationinformation(parentId);	
			}
			else 
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer  result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if (progressDialog2.isShowing())
					progressDialog2.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessages.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(LocationActivity.this))
					new GetLocationDetailFromServer(parentId).execute();
			}
			else
			{
				if(locationInformationFromServer!=null)
				{
					if(locationInformationFromServer.getCountry()!=0)
					{
						country_autoCompleteTextView.setText(locationInformationFromServer.getCountryName());
						parentProfile.setCountryID(locationInformationFromServer.getCountry());
					}

					if(locationInformationFromServer.getCity()!=0)
					{
						city_autoCompleteTextView.setText(locationInformationFromServer.getCityName());
						parentProfile.setCityID(locationInformationFromServer.getCity());
					}

					if(locationInformationFromServer.getStreetLocality()!=null && locationInformationFromServer.getStreetLocality().trim().length()>0)
						street_editText.setText(locationInformationFromServer.getStreetLocality());

					if(locationInformationFromServer.getFlatNoBuilding()!=null && locationInformationFromServer.getFlatNoBuilding().trim().length()>0)
						flat_editText.setText(locationInformationFromServer.getFlatNoBuilding());

					if(locationInformationFromServer.getGoogleMapAddress()!=null && locationInformationFromServer.getGoogleMapAddress().trim().length()>0)
						place_autoCompView.setText(locationInformationFromServer.getGoogleMapAddress());

					if(locationInformationFromServer.getNeighbourhoodRadius()!=0)
					{
						neighbourHood_radius_autoCompleteTextView.setText(locationInformationFromServer.getNeighbourhoodRadiusValue());
						String textSelectedNeighbourhoodRadius =locationInformationFromServer.getNeighbourhoodRadiusValue().toString().trim();
						try {
							radius=Integer.parseInt(textSelectedNeighbourhoodRadius.split("KM")[0].trim());
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							radius=5;
						}
						if(googleMap.getMyLocation()!=null)
						{
							LatLng 	currentPosition = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());	
							drawCircleMap(currentPosition);
							//googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
							//fitZoomAndPositionToMapByMarkers(currentPosition);
						}
						else
						{
							if(location!=null)
							{
								LatLng 	currentPosition = new LatLng(location.getLatitude(),location.getLongitude());	
								drawCircleMap(currentPosition);
								googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
							}
							//fitZoomAndPositionToMapByMarkers(currentPosition);
						}

						parentProfile.setNeighbourhoodID(locationInformationFromServer.getNeighbourhoodRadius());
					}
					else
					{
						radius=5;
						if(googleMap.getMyLocation()!=null)
						{
							LatLng 	currentPosition = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());	
							drawCircleMap(currentPosition);
							//googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
							//fitZoomAndPositionToMapByMarkers(currentPosition);
						}
						else
						{
							if(location!=null)
							{
								LatLng 	currentPosition = new LatLng(location.getLatitude(),location.getLongitude());	
								drawCircleMap(currentPosition);
								googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
							}
							//fitZoomAndPositionToMapByMarkers(currentPosition);
						}	
					}
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}


	private void addPreFilledData() {
		// TODO Auto-generated method stub
		ParentProfile parentInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);

		radius=5;
		if(googleMap.getMyLocation()!=null)
		{
			LatLng 	currentPosition = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());	
			drawCircleMap(currentPosition);
			//googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
			//fitZoomAndPositionToMapByMarkers(currentPosition);
		}
		else
		{
			if(location!=null)
			{
				LatLng 	currentPosition = new LatLng(location.getLatitude(),location.getLongitude());	
				drawCircleMap(currentPosition);
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,10));
			}
			//fitZoomAndPositionToMapByMarkers(currentPosition);
		}	


		if(parentInformation!=null)
		{
			if(parentInformation.getCountry()!=null && parentInformation.getCountry().trim().length()>0)
				country_autoCompleteTextView.setText(parentInformation.getCountry());

			if(parentInformation.getCity()!=null && parentInformation.getCity().trim().length()>0)
				city_autoCompleteTextView.setText(parentInformation.getCity());

			if(parentInformation.getStreetLocality()!=null && parentInformation.getStreetLocality().trim().length()>0)
				street_editText.setText(parentInformation.getStreetLocality());

			if(parentInformation.getFlatNoBuilding()!=null && parentInformation.getFlatNoBuilding().trim().length()>0)
				flat_editText.setText(parentInformation.getFlatNoBuilding());

			if(parentInformation.getGoogleMapAddress()!=null && parentInformation.getGoogleMapAddress().trim().length()>0)
				place_autoCompView.setText(parentInformation.getGoogleMapAddress());

			if(parentInformation.getNeighbourhoodRadius()!=null && parentInformation.getNeighbourhoodRadius().trim().length()>0)
				neighbourHood_radius_autoCompleteTextView.setText(parentInformation.getNeighbourhoodRadius());
		}

	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub

		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(view instanceof AutoCompleteTextView)

				((AutoCompleteTextView) view).showDropDown();
		}

		else if(event.getAction()==MotionEvent.ACTION_DOWN)
			hideKeyBoard();

		return false;
	}

	private void hideKeyBoard()
	{
		try  
		{
			LocationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			InputMethodManager inputManager = (InputMethodManager) LocationActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(LocationActivity.this
					.getCurrentFocus().getWindowToken(), 0);

			LocationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} 
		catch (Exception e) 
		{

		}
	}

	private ProgressDialog progressDialogCity=null;	
	private ProgressDialog progressDialogCountry=null;	

	private class GetListOfField extends AsyncTask<Void, Void, Integer>
	{
		private String getWebservice;
		private int countryId ;

		public GetListOfField(String getWebservice, int countryId)
		{
			// TODO Auto-generated constructor stub 
			this.getWebservice = getWebservice;
			this.countryId = countryId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(getWebservice.equalsIgnoreCase("country"))
			{
				progressDialogCountry = ProgressDialog.show(LocationActivity.this, "", StaticVariables.progressBarText, false);
				progressDialogCountry.setCancelable(false);
			}
			else
			{

				progressDialogCity = ProgressDialog.show(LocationActivity.this, "", StaticVariables.progressBarText, false);
				progressDialogCity.setCancelable(false);

			}
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(LocationActivity.this))
			{
				if(getWebservice.equalsIgnoreCase("country"))
				{
					countryList = serviceMethod.getCountry();

					if(countryList!=null)
					{
						countryStringList = getCountryStringList();
					}
					else
					{

					}
				}

				else
				{
					cityList = serviceMethod.getCity(countryId);

					if(cityList!=null)
					{
						cityStringList = getCityStringList();
					}
					else
					{

					}
				}
			}
			else
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		private ArrayList<String> getCountryStringList() {
			// TODO Auto-generated method stub
			countryStringList=new ArrayList<String>();

			for(int i=0;i<countryList.getCountry().size();i++)
			{
				countryStringList.add(countryList.getCountry().get(i).getCountryName());
			}

			return countryStringList;
		}

		private ArrayList<String> getCityStringList() {
			// TODO Auto-gen																																																																																																														erated method stub

			cityStringList=new ArrayList<String>();

			for(int i=0;i<cityList.getCity().size();i++)
			{
				cityStringList.add(cityList.getCity().get(i).getCityName()); 
			}

			return cityStringList;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if(getWebservice.equalsIgnoreCase("country"))
				{
					if (progressDialogCountry.isShowing())
						progressDialogCountry.cancel();
				}
				else
				{

					if (progressDialogCity.isShowing())
						progressDialogCity.cancel();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessages.showToastMessage("Please check your network connection");
			}
			else
			{
				if(getWebservice.equalsIgnoreCase("country"))
				{
					if(countryList!=null)
					{
						if(countryStringList!=null && countryStringList.size()>0)
						{
							AutoCompleteAdapter   checkUpAdapter = new AutoCompleteAdapter(LocationActivity.this, R.layout.list_item, R.id.item,countryStringList);
							if(!locationUpdate)
								country_autoCompleteTextView.setText(countryStringList.get(0));

							country_autoCompleteTextView.setAdapter(checkUpAdapter);
							country_autoCompleteTextView.setValidator(new ValidateText(countryStringList,0));



						}
						else
						{

						}
					}
					else
					{	
						getError();
						new GetListOfField("country",0).execute();
					}	
				}
				else
				{
					if(cityList!=null)
					{
						if(cityStringList!=null && cityStringList.size()>0)
						{
							AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(LocationActivity.this, R.layout.list_item, R.id.item,cityStringList);
							city_autoCompleteTextView.setAdapter(checkUpAdapter);
							city_autoCompleteTextView.setValidator(new ValidateText(cityStringList,1));
						}
					}
					else
					{	
						getError();
						new GetListOfField("city",countryId).execute();	
					}	
				}	
			}
		}	
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessages.showAlert("Alert", err.getErrorDesc());
	}

	private ProgressDialog progressDialog;
	private class RegisterParentTask extends AsyncTask<Void, Void, Integer>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(LocationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			int parentId=0;

			if(checkNetwork.checkNetworkConnection(LocationActivity.this))
			{
				parentId=-1;

				try {
					String path=parentProfile.getProfileImage();

					if(path!=null)
					{
						parentProfile.setProfileImage(bitmapTobyte(getImageforCitation(path)));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				parentId=serviceMethod.createParentProfile(parentProfile);

				if(parentId!=0)
				{
					parentProfile.setParentID(parentId);
				}
				else
				{

				}
			}
			return parentId;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result); 

			try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			onTouchContinueButton=false;

			if(result==0)
			{
				showMessages.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(LocationActivity.this))
					new RegisterParentTask().execute();
			}
			else
			{
				if(result!=-1)
				{
					String parentInformation = gsonRegistration.toJson(parentProfile);
					sharePref.setParentProfile(parentInformation);  

					Intent intent=new Intent(LocationActivity.this, ConfirmationActivity.class);
					// pass profile email id and profile id
					Bundle bundle = new Bundle();
					bundle.putString("EmailAddress",parentProfile.getEmailAddress());
					bundle.putInt("ParentID", parentProfile.getParentID());
					intent.putExtras(bundle);
					startActivity(intent);
					LocationActivity.this.finish();
					GettingLattitude.lat=0.0;
					GettingLattitude.lng=0.0;
				}
				else
				{
					getError();
				}
			}
		}
	}

	private byte[] getImageforCitation(String name) {
		Bitmap bmp = null;
		byte[] byteArray = null;

		File imageFile = new File(name);
		if (imageFile.exists()) {

			//bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			bmp = new CompressImage(LocationActivity.this).compressImage(name);
			ByteArrayOutputStream stream = new ByteArrayOutputStream ();
			bmp.compress (Bitmap.CompressFormat.JPEG, 100, stream);
			byteArray = stream.toByteArray ();
		}
		return byteArray;
	}

	private String bitmapTobyte(byte[] bitmapLength)
	{
		return Base64.encodeToString(bitmapLength,
				Base64.DEFAULT);	
	}

	class ValidateText implements AutoCompleteTextView.Validator
	{
		private ArrayList<String> arrayObj;

		private int autoCompleteTextView;

		public ValidateText(ArrayList<String> arrayOfObjectsToValidate, int autoCompleteTextView) {
			this.arrayObj = arrayOfObjectsToValidate;
			this.autoCompleteTextView=autoCompleteTextView;
		}
		public boolean isValid(CharSequence text) {
			if (arrayObj.contains(text.toString())) {
				setFocuesToNextView();
				System.out.println("in validation caheck");
				return true;
			}
			return false;
		}

		private void setFocuesToNextView()
		{
			// TODO Auto-generated method stub
			if(autoCompleteTextView==0)
			{
				city_autoCompleteTextView.setFocusable(true);
			}
			else
			{
				neighbourHood_radius_autoCompleteTextView.setFocusable(true);
			}
		}

		public CharSequence fixText(CharSequence invalidText) 
		{
			Log.v("Test", "Returning fixed text");
			System.out.println(invalidText+"  text in change");
			return "";
		}
	}	

	public static ArrayList<String> autocomplete(String input) 
	{
		ArrayList<String> resultList = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();

		try 
		{
			StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?key=" + API_KEY);
			//sb.append("&components=country:gr");
			sb.append("&input=" + URLEncoder.encode(input, "utf8"));

			URL url = new URL(sb.toString());

			System.out.println("URL: "+url);
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try 
		{
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
				System.out.println("============================================================");
				resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}

		return resultList;
	}

	class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
		private ArrayList<String> resultList;

		public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public int getCount() {
			return resultList.size();
		}

		@Override
		public String getItem(int index) {
			return resultList.get(index);
		}

		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults filterResults = new FilterResults();
					if (constraint != null) {
						// Retrieve the autocomplete results.
						resultList = autocomplete(constraint.toString());

						// Assign the data to the FilterResults
						filterResults.values = resultList;
						filterResults.count = resultList.size();
					}
					return filterResults;
				}

				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					if (results != null && results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}
			};
			return filter;
		}
	}

	private class GetListOfNeighbourhoodRadius extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(LocationActivity.this))
			{
				getNeighbourhoodRadiusList = serviceMethod.getNeighbourhoodRadiusList();

				if(getNeighbourhoodRadiusList!=null)
				{
					neighbourhoodRadiusStringList = getneighbourhoodRadiusStringList();
				}
				else
				{

				}
			}
			else
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		private ArrayList<String> getneighbourhoodRadiusStringList() {
			// TODO Auto-generated method stub
			neighbourhoodRadiusStringList=new ArrayList<String>();

			for(int i=0;i<getNeighbourhoodRadiusList.getGetNeighbourhoodRadius().size();i++)
			{
				neighbourhoodRadiusStringList.add(getNeighbourhoodRadiusList.getGetNeighbourhoodRadius().get(i).getRadiusValue());
			}
			return neighbourhoodRadiusStringList;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result==-1)
			{
				showMessages.showToastMessage("Please check your network connection");
			}
			else
			{
				if(neighbourhoodRadiusStringList!=null && neighbourhoodRadiusStringList.size()>0)
				{
					AutoCompleteAdapter   checkUpAdapter = new AutoCompleteAdapter(LocationActivity.this, R.layout.list_item, R.id.item,neighbourhoodRadiusStringList);
					neighbourHood_radius_autoCompleteTextView.setAdapter(checkUpAdapter);
					neighbourHood_radius_autoCompleteTextView.setValidator(new ValidateText(neighbourhoodRadiusStringList,0));
				}
				else
				{
					getError();
				}
			}
		}	
	}

	private class getLattAndLongFromAddress extends AsyncTask<String, Integer, Integer>
	{
		private int ErrorCode = 0;
		private String locationValue;

		public getLattAndLongFromAddress(String str) {
			// TODO Auto-generated constructor stub
			locationValue=str.toString();
		}

		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			GettingLattitude.getLatLongFromGivenAddress(locationValue);
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer result)                
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			LatLng 	myposition = new LatLng(GettingLattitude.lat, GettingLattitude.lng);	

			googleMap.addMarker(new MarkerOptions().position(myposition).title("").snippet("").icon(BitmapDescriptorFactory
					.fromResource(R.drawable.map)));	

			// drawCircleMap(myposition);
			fitZoomAndPositionToMapByMarkers(myposition);
		}


	}


	/**
	 * @param myposition
	 */
	private Circle mapCircle;//map circle
	private int radius=5;//5km
	private void drawCircleMap(LatLng myposition) {
		if(myposition!=null)
		{
			if(mapCircle!=null)
			{
				mapCircle.remove();
			}
			CircleOptions circleOptions = new CircleOptions()
			.center(myposition)   //set center
			.radius(radius* 1000)   //set radius in meters
			.fillColor(0x300000aa)  //default
			.strokeColor(Color.CYAN)
			.strokeWidth(1);
			mapCircle=googleMap.addCircle(circleOptions);
		}
	}

	public void fitZoomAndPositionToMapByMarkers(LatLng myposition) {

		int minLat = Integer.MAX_VALUE;
		int maxLat = Integer.MIN_VALUE;
		int minLon = Integer.MAX_VALUE;
		int maxLon = Integer.MIN_VALUE;

		int lat = (int) (myposition.latitude * 1E6);
		int lon = (int) (myposition.longitude * 1E6);

		maxLat = Math.max(lat, maxLat);
		minLat = Math.min(lat, minLat);
		maxLon = Math.max(lon, maxLon);
		minLon = Math.min(lon, minLon);

		LatLng southWestLatLon = new LatLng(minLat / 1E6, minLon / 1E6);
		LatLng northEastLatLon = new LatLng(maxLat / 1E6, maxLon / 1E6);

		zoomInUntilAllMarkersAreStillVisible(southWestLatLon, northEastLatLon);
	}

	private void zoomInUntilAllMarkersAreStillVisible(final LatLng southWestLatLon, final     LatLng northEastLatLon)
	{
		googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(southWestLatLon, northEastLatLon), 50));
	}


	public Location getLocation() {
		Location location = null;
		try {
			LocationManager locationManager = (LocationManager) LocationActivity.this
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							2000,
							0, this);
					Log.d("Network", "Network Enabled");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							///  latitude = location.getLatitude();
							// longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								2000,
								0, this);
						Log.d("GPS", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								//   latitude = location.getLatitude();
								// longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void showAlertLocation(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				new GetListOfField("country",0).execute();
				new GetListOfNeighbourhoodRadius().execute();

				new GetListOfField("city",1).execute();
			}
		});	
		alertBuilder.show();
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if(!locationUpdate)
		{
			Intent intent=new Intent(LocationActivity.this, ParentRegistrationActivity.class);
			startActivity(intent);
			LocationActivity.this.finish();
		}
		else
		{
			super.onBackPressed();
		}
	}
}
