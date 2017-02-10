package com.hatchtact.pinwi.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AddHolidaysByChildIDModel;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetHolidayDetailsByHolidayDesc;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HolidayDetailsFragment extends ParentFragment
{
	private View view;

	private TextView titleaddNewHoliday=null;
	private TextView holidayNamelabel=null;
	private EditText holidayNamedata=null;
	private TextView setdate_text=null;
	private TextView setdateValueHoliday_text=null;
	private TextView setendate_textlabel=null;
	private TextView setendate_text=null;	
	private LinearLayout layout_startdateHoliday=null;
	private LinearLayout layout_enddateholidaydate=null;
	private Button buttonDone=null;
	private Button button_delete=null;

	private DatePickerDialog datePickerDialog=null;
	private SimpleDateFormat dateFormatter=null;
	private int selectedYear,selectedMonth,selectedDay;

	private SharePreferenceClass sharePreferneceClass=null;
	private Gson gsonRegistration=null;
	private Validation checkValidation=null;
	private ShowMessages showMessage=null; 
	private CheckNetwork checkNetwork=null;
	private ServiceMethod serviceMethod=null;


	private GetHolidayDetailsByHolidayDesc getHolidayDetailsByHolidayDesc=null;


	private AddHolidaysByChildIDModel modelHolidays;
	private boolean isStartDate=false;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_holidaysdetails, container, false);

		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Holidays");
		modelHolidays=new AddHolidaysByChildIDModel();

		getHolidayDetailsByHolidayDesc=new GetHolidayDetailsByHolidayDesc();

		sharePreferneceClass=new SharePreferenceClass(getActivity());
		gsonRegistration = new GsonBuilder().create();
		checkNetwork=new CheckNetwork();
		checkValidation = new Validation();
		showMessage=new ShowMessages(getActivity());
		serviceMethod=new ServiceMethod();
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		titleaddNewHoliday=(TextView) view.findViewById(R.id.titleaddNewHoliday);
		layout_startdateHoliday=(LinearLayout) view.findViewById(R.id.layout_startdateHoliday);
		layout_enddateholidaydate=(LinearLayout) view.findViewById(R.id.layout_enddateholidaydate);
		holidayNamelabel=(TextView) view.findViewById(R.id.holidayNamelabel);
		setdate_text=(TextView) view.findViewById(R.id.setdate_text);
		setdateValueHoliday_text=(TextView) view.findViewById(R.id.setdateValueHoliday_text);
		buttonDone = (Button) view.findViewById(R.id.buttonDone);
		setendate_text=(TextView) view.findViewById(R.id.setendate_text);
		setendate_textlabel=(TextView) view.findViewById(R.id.setendate_textlabel);
		holidayNamedata=(EditText) view.findViewById(R.id.holidayNamedata);
		button_delete = (Button) view.findViewById(R.id.button_delete);


		typeFace.setTypefaceLight(titleaddNewHoliday);
		typeFace.setTypefaceRegular(holidayNamelabel);
		typeFace.setTypefaceRegular(setdate_text);
		typeFace.setTypefaceLight(setdateValueHoliday_text);
		typeFace.setTypefaceRegular(buttonDone);
		typeFace.setTypefaceLight(setendate_text);
		typeFace.setTypefaceRegular(setendate_textlabel);
		typeFace.setTypefaceLight(holidayNamedata);
		typeFace.setTypefaceRegular(button_delete);



		buttonDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance(); 
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				Calendar calender1=Calendar.getInstance();

				calender1.set(selectedYear, selectedMonth, selectedDay);

				long startDateValue=calender1.getTime().getTime();

				if(!checkValidation.isNotNullOrBlank(holidayNamedata.getText().toString()))
				{
					holidayNamedata.requestFocus();
					showMessage.showAlert("Warning", "Please select holiday Name");
				}

				else if(!checkValidation.isNotNullOrBlank(setdateValueHoliday_text.getText().toString()))
				{
					setdateValueHoliday_text.requestFocus();
					showMessage.showAlert("Warning", "Please select start date");
				}


				else if(!checkValidation.isNotNullOrBlank(setendate_text.getText().toString()))
				{
					setendate_text.requestFocus();
					showMessage.showAlert("Warning", "Please select end date");
				}

				else if(selectedYear < year)
					showMessage.showAlert("Warning", "Please select valid year");

				else if((selectedYear == year && selectedMonth < month))
					showMessage.showAlert("Warning", "Please select valid month");	

				else if(selectedMonth == month && selectedDay < day)
					showMessage.showAlert("Warning", "Please select valid day");

				/*	else if(startDateValue < System.currentTimeMillis() -(30 * 1000))
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");
				 */
				else
				{
					modelHolidays.setChildID(StaticVariables.currentChild.getChildID());
					modelHolidays.setHolidayDescription(holidayNamedata.getText().toString().trim());
					modelHolidays.setStartDate(setdateValueHoliday_text.getText().toString().trim());
					modelHolidays.setEndDate(setendate_text.getText().toString().trim());

					new AddHolidaysTask().execute();


				}
			}
		});



		button_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DeleteScheduledHolidayAsync().execute();

			}
		});

		if(HolidayListFragment.isAddingHoliday)
		{
			setDateField();
			holidayNamedata.requestFocus();
			buttonDone.setVisibility(View.VISIBLE);
			button_delete.setVisibility(View.GONE);
		}
		else
		{

			holidayNamedata.setFocusable(false);
			
			holidayNamedata.setFocusableInTouchMode(false);
			holidayNamedata.setAlpha(.5f);
			setdateValueHoliday_text.setAlpha(.5f);
			setendate_text.setAlpha(.5f);
			//less visibility of data
			button_delete.setVisibility(View.VISIBLE);
			buttonDone.setVisibility(View.VISIBLE);
			buttonDone.setAlpha(.5f);
			buttonDone.setEnabled(false);
			new getHolidayDetailsFromServer().execute();
		}


		return view;
	}


	public class DeleteScheduledHolidayAsync extends AsyncTask<Void, Void, Integer>
	{

		public DeleteScheduledHolidayAsync()
		{

		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		String status;
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				status=serviceMethod.deleteScheduledHolidayByActChildID(StaticVariables.currentChild.getChildID(), HolidayListFragment.holidayDesc);


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
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new DeleteScheduledHolidayAsync().execute();

			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{

					hideKeyBoard();
					if(!StaticVariables.isFromSettingsScreen)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=80;
						switchingFragments(new HolidayListFragment());
					}
					else
					{

						StaticVariables.fragmentIndexCurrentTabSettings=201;
						switchingFragmentsSettings(new HolidayListFragment());	

					}



				}

			}	
		}	
	}

	protected void switchingFragmentsSettings(Fragment toFragment)
	{

		getFragmentManager().beginTransaction().replace(R.id.framelayout1,
				toFragment).commit();  
		getFragmentManager().executePendingTransactions();      // <----- This is the key 
	}

	private void setDateField() 
	{
		layout_startdateHoliday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isStartDate=true;
				datePickerDialog.show();
			}
		});

		layout_enddateholidaydate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isStartDate=false;
				datePickerDialog.show();
			}
		});

		Calendar newCalendar = Calendar.getInstance();

		datePickerDialog=new DatePickerDialog(getActivity(), new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				Calendar newDate=Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);

				if(isStartDate)
				{
					setdateValueHoliday_text.setText(dateFormatter.format(newDate.getTime()));
				}
				else
				{
					setendate_text.setText(dateFormatter.format(newDate.getTime()));

				}

				selectedYear=year;
				selectedMonth=monthOfYear;
				selectedDay=dayOfMonth;
			}
		}, newCalendar
		.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
	}

	private ProgressDialog progressDialog=null;	

	private class getHolidayDetailsFromServer extends AsyncTask<Void, Void, Integer>
	{


		public getHolidayDetailsFromServer()
		{
			// TODO Auto-generated constructor stub

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getHolidayDetailsByHolidayDesc =serviceMethod.getHolidayDetails(StaticVariables.currentChild.getChildID(), HolidayListFragment.holidayDesc);
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
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new getHolidayDetailsFromServer().execute();
			}
			else
			{
				if(result!=-1)
				{	


					if(getHolidayDetailsByHolidayDesc!=null)
					{
						//setting data based on receive from server
						holidayNamelabel=(TextView) view.findViewById(R.id.holidayNamelabel);
						setdateValueHoliday_text.setText(getHolidayDetailsByHolidayDesc.getStartDate());
						setendate_text.setText(getHolidayDetailsByHolidayDesc.getEndDate());
						holidayNamedata.setText(getHolidayDetailsByHolidayDesc.getHolidayDescription());

					}	
				}
				else
				{
					//getError();
				}
			}
		}	
	}



	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	} 

	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(false);
			else
			{
				menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());
				menu.getItem(i).setVisible(true);

			}
		}

		super.onCreateOptionsMenu(menu, inflater);
	} 



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==android.R.id.home)
		{
			getActivity().onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


	private class AddHolidaysTask extends AsyncTask<Void, Void, Integer>
	{
		private int ally=0;



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}


		String status;
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			int Errorcode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{

				status=serviceMethod.addHolidayDataToServer(modelHolidays);
			}
			else
			{
				Errorcode=-1;
			}
			return Errorcode;
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

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new AddHolidaysTask().execute();
			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{

					hideKeyBoard();
					//Toast.makeText(getActivity(), "Holiday added successfully", Toast.LENGTH_LONG).show();
					showMessage.showAlertInsights("Confirmation", "Holiday added successfully");
					if(!StaticVariables.isFromSettingsScreen)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=80;
						switchingFragments(new HolidayListFragment());
					}
					else
					{
						StaticVariables.fragmentIndexCurrentTabSettings=201;
						switchingFragmentsSettings(new HolidayListFragment());	
					}

				}
				else
				{
					//getError();
				}
			}
		}
	}


	private void hideKeyBoard() {



		try {
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(), 0);
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}

	}
}
