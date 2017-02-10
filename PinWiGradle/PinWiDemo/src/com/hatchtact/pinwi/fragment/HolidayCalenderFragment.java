package com.hatchtact.pinwi.fragment;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.calendarHoliday.Day;
import com.hatchtact.pinwi.calendarHoliday.ExtendedCalendarView;
import com.hatchtact.pinwi.calendarHoliday.ExtendedCalendarView.OnDayClickListener;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetHolidaysByChildID;
import com.hatchtact.pinwi.classmodel.GetHolidaysByChildIDList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class HolidayCalenderFragment extends Fragment
{
	private View view;
	public static HolidayCalenderFragment holidayCalenderFragment;
	private SharePreferenceClass sharePreferenceMananger;

	//private Button btnDoneHolidayCalenderScreen;
	private EditText descriptionHoliday;
	private ExtendedCalendarView calendarHoliday;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	//For sending Data
	private String HolidayDate="06/10/2015,07/09/2015";
	private String HolidayDescription="";

	private TextView txt_holidaycalender;

	private TypeFace typeface;

	/** For Range Selection*/
	//private TextView button_select_range;
	//private TextView button_undo_range;
	private TextView enterHolidays;
	/*private boolean selectedRange = false;
	private boolean undoRange = false;*/



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharePreferenceMananger=new SharePreferenceClass(getActivity());
		setHasOptionsMenu(true);
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();

		if(!sharePreferenceMananger.isholidaycalendertutorial())
		{
			sharePreferenceMananger.setholidaycalendertutorial(true);
			/*Intent tutorial=new Intent(getActivity(), ApptTutorialActivity.class);
			startActivity(tutorial);
			StaticVariables.currentTutorialValue=5;*/
		}
		//daysSelectedForCalendarHoliday = new ArrayList<Day>();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.holiday_calender, container, false);

		enterHolidays=(TextView) view.findViewById(R.id.txtEnterHoliday);
		enterHolidays.setVisibility(View.VISIBLE);
		typeface=new TypeFace(getActivity());


		enterHolidays.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StaticVariables.isFromSettingsScreen=true;
				StaticVariables.fragmentIndexCurrentTabSettings=202;
				switchingFragments(new HolidayListFragment());				

			}
		});

		descriptionHoliday=(EditText) view.findViewById(R.id.txt_holidaydescription);
		//	btnDoneHolidayCalenderScreen=(Button) view.findViewById(R.id.button_done_holiday);
		txt_holidaycalender=(TextView) view.findViewById(R.id.txt_holidaycalender);
		/*btnDoneHolidayCalenderScreen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetHolidaysByChildIDUploadAsync().execute();	
			}
		});*/

		/** For Range Selection*/
		//button_select_range = (TextView) view.findViewById(R.id.button_select_range);
		//button_undo_range= (TextView) view.findViewById(R.id.button_undo_range);
		/*typeface.setTypefaceRegular(button_select_range);
		typeface.setTypefaceRegular(button_undo_range);
		button_select_range.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(selectedRange){
					fillRangeValues();
					setUndoRange();
				}
			}
		});

		button_undo_range.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(undoRange){
					clearRangeSelected();
					setDefaultRangeNotSelected();
				}	
			}
		});*/

		typeface.setTypefaceRegular(txt_holidaycalender);
		typeface.setTypefaceRegular(enterHolidays);
		//	typeface.setTypefaceRegular(btnDoneHolidayCalenderScreen);

		calendarHoliday = (ExtendedCalendarView) view.findViewById(R.id.calendarHoliday);
		calendarHoliday.setHolidayCalenderFragmentInstance(HolidayCalenderFragment.this);
		calendarHoliday.setOnDayClickListener(new OnDayClickListener() {

			@Override
			public void onDayClicked(AdapterView<?> adapter, View view, int position,
					long id, Day day) {

				/*if(day.canHolidatBeEditted)
				{
					setDateSelected(position, day);
					calendarHoliday.getCalendarHolidayAdapter().notifyDataSetChanged();
				}*/


			}

		});

		return view;

	}

	/*private void setDateSelected(int position, Day day) 
	{

		String dateTouched = day.getDay()+"/"+(day.getMonth()+1)+"/"+day.getYear();
		if(calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList() == null){
			calendarHoliday.getCalendarHolidayAdapter().setHolidaysByChildIDList(new GetHolidaysByChildIDList());
			calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().setGetHolidaysByChildIDList(new ArrayList<GetHolidaysByChildID>());
		}

		if(calendarHoliday.getCalendarHolidayAdapter().dayList.get(position).isSelectedFromRange)
		{
			calendarHoliday.getCalendarHolidayAdapter().dayList.get(position).setDayForHolidayCalendar = false;
			calendarHoliday.getCalendarHolidayAdapter().dayList.get(position).isSelectedFromRange = false;
			for(int daySaved=0;daySaved<calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().size();daySaved++)
			{
				if(calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().get(daySaved).getHolidayDate().equalsIgnoreCase(dateTouched))
				{
					calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().remove(daySaved);
					break;
				}		
			}
		}
		else{

			calendarHoliday.getCalendarHolidayAdapter().dayList.get(position).setDayForHolidayCalendar =
					!calendarHoliday.getCalendarHolidayAdapter().dayList.get(position).setDayForHolidayCalendar;

			if(calendarHoliday.getCalendarHolidayAdapter().dayList.get(position).setDayForHolidayCalendar)
			{
				//add it in arraylist
				//daysSelectedForCalendarHoliday.add(day);
				GetHolidaysByChildID obj = new GetHolidaysByChildID();
				obj.setChildID(StaticVariables.currentChild.getChildID());
				obj.setHolidayDate(dateTouched);

				calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().add(obj);

				if(!undoRange){
					//check for previous dates...do nothing in this case
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(System.currentTimeMillis());
					if(day.getYear() > cal.get(Calendar.YEAR)){
						//it's not a previous date
						if(calendarHoliday.currentSelectedDate != day.getDay())
						{
							calendarHoliday.justPreviousSelectedDate = calendarHoliday.currentSelectedDate;
							calendarHoliday.currentSelectedDate = day.getDay();
							if(calendarHoliday.justPreviousSelectedDate!=0 && calendarHoliday.currentSelectedDate!=0){
								setSelectRange();
							}
						}

					}else if(day.getYear() == cal.get(Calendar.YEAR) && day.getMonth() >= cal.get(Calendar.MONTH)){
						if(day.getDay() >= cal.get(Calendar.DAY_OF_MONTH)){
							//it's not a previous date
							if(calendarHoliday.currentSelectedDate != day.getDay())
							{
								calendarHoliday.justPreviousSelectedDate = calendarHoliday.currentSelectedDate;
								calendarHoliday.currentSelectedDate = day.getDay();
								if(calendarHoliday.justPreviousSelectedDate!=0 && calendarHoliday.currentSelectedDate!=0){
									setSelectRange();
								}
							}

						}
					}
				}
			}
			else
			{
				if(calendarHoliday.currentSelectedDate == day.getDay() && !undoRange){
					calendarHoliday.currentSelectedDate = 0;
				}

				//remove the date
				//daysSelectedForCalendarHoliday.remove(day);
				for(int daySaved=0;daySaved<calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().size();daySaved++)
				{
					if(calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().get(daySaved).getHolidayDate().equalsIgnoreCase(dateTouched))
					{
						calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().remove(daySaved);
						break;
					}		
				}

			}
		}
	}*/

	/** For Range Selection*/

	protected void clearRangeSelected() {
		for(int i=0;i<calendarHoliday.getCalendarHolidayAdapter().dayList.size();i++)
		{
			if(calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).isSelectedFromRange){

				calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).isSelectedFromRange = false;

				String dateTouched = calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).getDay()+"/"+
						(calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).getMonth()+1)+"/"+
						calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).getYear();



				Iterator iterator = calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().iterator();
				while (iterator.hasNext()) {

					GetHolidaysByChildID model=(GetHolidaysByChildID) iterator.next();
					if(calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).canHolidatBeEditted && 
							model.getHolidayDate().equalsIgnoreCase(dateTouched))
					{
						System.out.println("Date undo: "+dateTouched);
						calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).setDayForHolidayCalendar = false;
						iterator.remove();


					}	
				}

				/*for(int daySaved=0;daySaved<calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().size();daySaved++)
				{
					if(calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).canHolidatBeEditted && 
							calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().get(daySaved).getHolidayDate().equalsIgnoreCase(dateTouched))
					{
						System.out.println("Date undo: "+dateTouched);
						calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).setDayForHolidayCalendar = false;
						calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().remove(daySaved);


					}		
				}*/





			}	
		}

		calendarHoliday.getCalendarHolidayAdapter().notifyDataSetChanged();

	}

	/** For Range Selection*/
	protected void saveRangeSelected() {
		for(int i=0;i<calendarHoliday.getCalendarHolidayAdapter().dayList.size();i++)
		{
			if(calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).isSelectedFromRange){
				calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).isSelectedFromRange = false;
				calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).setDayForHolidayCalendar = true;
				//calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).canHolidatBeEditted=false;

			}	
		}

		calendarHoliday.getCalendarHolidayAdapter().notifyDataSetChanged();

	}

	private void fillRangeValues()
	{	
		for(int i=0;i<calendarHoliday.getCalendarHolidayAdapter().dayList.size();i++)
		{
			if(calendarHoliday.currentSelectedDate<calendarHoliday.justPreviousSelectedDate){
				changeSelectionRangeValueInDayList(calendarHoliday.currentSelectedDate,calendarHoliday.justPreviousSelectedDate,i);
			}else{
				changeSelectionRangeValueInDayList(calendarHoliday.justPreviousSelectedDate,calendarHoliday.currentSelectedDate,i);
			}	
		}

		calendarHoliday.getCalendarHolidayAdapter().notifyDataSetChanged();
	}

	private void changeSelectionRangeValueInDayList(int start, int end, int i) {
		for(int dateToChange=start ;dateToChange<=end ;dateToChange++)
		{
			if(calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).getDay() == dateToChange /*&& !calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).setDayForHolidayCalendar*/)
			{	
				String dateTouched = dateToChange+"/"+
						(calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).getMonth()+1)+"/"+
						calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).getYear();
				System.out.println("Date selected: "+dateTouched);
				if(calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList() == null){
					calendarHoliday.getCalendarHolidayAdapter().setHolidaysByChildIDList(new GetHolidaysByChildIDList());
					calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().setGetHolidaysByChildIDList(new ArrayList<GetHolidaysByChildID>());
				}

				calendarHoliday.getCalendarHolidayAdapter().dayList.get(i).isSelectedFromRange = true;

				GetHolidaysByChildID obj = new GetHolidaysByChildID();
				obj.setChildID(StaticVariables.currentChild.getChildID());
				obj.setHolidayDate(dateTouched);

				calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().add(obj);

				/*boolean alreadyAdded=false;

				//for removing duplicacy 
				for(int j=0;i<calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().size();i++)
				{
					if(dateTouched.equalsIgnoreCase(calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().get(j).getHolidayDate()))
					{
						alreadyAdded=true;
						break;
					}
				}
				//for removing duplicacy 

				if(!alreadyAdded)
				{
					//add it in arraylist
					//daysSelectedForCalendarHoliday.add(day);

				}*/


				break;
			}
		}
	}

	/*private void setSelectRange(){
		selectedRange = true;
		undoRange = false;
		button_select_range.setBackgroundColor(getActivity().getResources().getColor(R.color.font_blue_color));
		button_undo_range.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));

	}

	private void setUndoRange(){
		selectedRange = false;
		undoRange = true;
		button_select_range.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
		button_undo_range.setBackgroundColor(getActivity().getResources().getColor(R.color.font_blue_color));
	}*/


	public void setDefaultRangeNotSelected(){
		calendarHoliday.justPreviousSelectedDate = 0;
		calendarHoliday.currentSelectedDate = 0;
		/*selectedRange = false;
		undoRange = false;
		button_select_range.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
		button_undo_range.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));*/
	}
	/** For Range Selection*/



	private void refreshDataAccordingToChild() 
	{
		// TODO Auto-generated method stub
		calendarHoliday.rebuildCalendar();
	}

	public static HolidayCalenderFragment getInstance()
	{
		if(holidayCalenderFragment==null)
		{
			holidayCalenderFragment = new HolidayCalenderFragment();
		}
		return holidayCalenderFragment;
	}


	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(true);
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
		else if(item.getItemId()!=R.id.action_childName)
		{
			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();

			//here we will refresh data of hoilday calender
			calendarHoliday.rebuildCalendar();


		} 
		return super.onOptionsItemSelected(item);
	}

	private ProgressDialog progressDialogHolidaysByChildID=null;		

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	public void setCalenderData() 

	{
		// TODO Auto-generated method stub

	}







	private class GetHolidaysByChildIDUploadAsync extends AsyncTask<Void, Void, Integer>
	{
		public GetHolidaysByChildIDUploadAsync()
		{ 
			HolidayDate = "";

			ArrayList<String> holidayArray = new ArrayList<String>();

			for(int daySaved=0;daySaved<calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().size();daySaved++)
			{
				String[] dateArray=calendarHoliday.getCalendarHolidayAdapter().getHolidaysByChildIDList().getGetHolidaysByChildIDList().get(daySaved).getHolidayDate().split("/");

				String day;
				String month;
				if(dateArray[0].length()==1)
					day = "0"+dateArray[0];
				else
					day = dateArray[0];

				if(dateArray[1].length()==1)
					month = "0"+dateArray[1];
				else
					month = dateArray[1];

				String dateFormed ;
				dateFormed = day+"/"
						+ month+"/"
						+ Integer.parseInt(dateArray[2]);

				holidayArray.add(dateFormed);

			}


			//removing duplicate items
			holidayArray = removeDupliciacy(holidayArray);
			HolidayDate = getStringFromHolidayDatesArray(holidayArray);

		}

		private String getStringFromHolidayDatesArray(ArrayList<String> dateList) {
			String holidays="";
			for(int i=0;i<dateList.size();i++)
			{
				if(i == (dateList.size()-1))
				{
					holidays += dateList.get(i);
				}

				else
				{
					holidays += dateList.get(i)+",";
				}
			}
			return holidays;
		}


		private ArrayList<String> removeDupliciacy(ArrayList<String> hashStreetAddress) {
			ArrayList<String> uniqueList = new ArrayList<String>();
			if (hashStreetAddress.size() > 0) {
				String[] array = new String[hashStreetAddress.size()];
				for (int i = 0; i < hashStreetAddress.size(); i++)
					array[i] = hashStreetAddress.get(i);
				uniqueList.add(array[0]);
				for (int i = 0; i < array.length; i++) {
					String items = array[i];
					boolean add = false;
					for (int j = 0; j < uniqueList.size(); j++) {
						if (items.equalsIgnoreCase(uniqueList.get(j)))
							add = true;
					}
					if (!add)
						uniqueList.add(items);
				}
			}
			return uniqueList;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogHolidaysByChildID = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogHolidaysByChildID.setCancelable(false);
		}

		String status;
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				status=serviceMethod.addHolidaysByChildId(StaticVariables.currentChild.getChildID()+"",HolidayDate, HolidayDescription);


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
				if (progressDialogHolidaysByChildID.isShowing())
					progressDialogHolidaysByChildID.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetHolidaysByChildIDUploadAsync().execute();

			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{
					Toast.makeText(getActivity(), "Holiday Added", Toast.LENGTH_LONG).show();
					HolidayDate="";
					HolidayDescription="";
					saveRangeSelected();
					setDefaultRangeNotSelected();
				}
				else
				{
					getError();
				}
			}	
		}	
	}


	protected void switchingFragments(Fragment toFragment)
	{

		getFragmentManager().beginTransaction().replace(R.id.framelayout1,
				toFragment).commit();  
		getFragmentManager().executePendingTransactions();      // <----- This is the key 
	}
}
