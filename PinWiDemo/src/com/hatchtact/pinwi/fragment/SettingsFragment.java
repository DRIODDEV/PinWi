package com.hatchtact.pinwi.fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hatchtact.pinwi.ChildListActivity;
import com.hatchtact.pinwi.LocationActivity;
import com.hatchtact.pinwi.ParentRegistrationActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.fragment.insights.OnFragmentAttachedListener;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.FutureNotificationAlarmForMe;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class SettingsFragment extends Fragment implements OnClickListener
{

	private View view;
	public static SettingsFragment settingsfragment;
	protected OnFragmentAttachedListener mListener;

	//private Context mActivity=null;

	private  final int[] info_ids= {
			R.id.text_setting1,
			R.id.text_setting2,
			R.id.text_setting3,
			R.id.text_setting4,
			R.id.text_setting5,
			R.id.text_setting6,
			R.id.text_setting7,
			R.id.text_setting8,
			R.id.text_setting9,
			R.id.text_setting10,
			R.id.text_setting11
	};

	RelativeLayout relativeFrequency;
	RelativeLayout relativeTime;
	ArrayList<TextView> arrayTxtView=new ArrayList<TextView>();
	private Dialog builder_time_picker = null;
	private SharePreferenceClass sharePref;

	private long alarmTime;

	private TypeFace typeFace;
	private Button button_save;
	private TextView text_settingreminerOff;
	private Switch reminder_switch;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		sharePref=new SharePreferenceClass(getActivity());
		typeFace=new TypeFace(getActivity());
	}


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mListener = (OnFragmentAttachedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentAttachedListener");
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.setting_layout, container, false);
		mListener.onFragmentAttached(true,"  Settings");

		arrayTxtView.clear();

		//mActivity=getActivity();


		button_save=(Button) view.findViewById(R.id.button_save);
		typeFace.setTypefaceRegular(button_save);
		button_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharePref.setReminder(reminder_switch.isChecked());
				if(reminder_switch.isChecked())
					new SetReminderAsync().execute();
				else
				{
					
				}
			}
		});
		setHasOptionsMenu(true);
		for(int i=0;i<info_ids.length;i++)
		{
			setTextViewId(info_ids[i],i);
		}

		text_settingreminerOff=(TextView)view.findViewById(R.id.text_settingreminderOff);
		typeFace.setTypefaceRegular(text_settingreminerOff);

		reminder_switch=(Switch) view.findViewById(R.id.reminder_switch);

		typeFace.setTypefaceLight(reminder_switch);
		if(sharePref.isReminderOn())
		{
			reminder_switch.setChecked(true);
		}
		else
		{
			reminder_switch.setChecked(false);
		}

		reminder_switch.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(reminder_switch.isChecked())
				{
					relativeFrequency.setAlpha(1f);
					relativeFrequency.setClickable(true);
					relativeFrequency.setEnabled(true);
					relativeTime.setAlpha(1f);
					relativeTime.setClickable(true);
					relativeTime.setEnabled(true);
					//sharePref.setReminder(true);
				}
				else
				{
					relativeFrequency.setAlpha(.5f);
					relativeFrequency.setClickable(false);
					relativeFrequency.setEnabled(false);
					relativeTime.setAlpha(.5f);
					relativeTime.setClickable(false);
					relativeTime.setEnabled(false);
					//sharePref.setReminder(false);
				}
			}
		});




		relativeFrequency=(RelativeLayout) view.findViewById(R.id.horizontal_layout_setting);
		relativeTime=(RelativeLayout) view.findViewById(R.id.horizontal_layout_setting1);

		initDialog();

		relativeFrequency.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openCustomDialog();
			}
		});


		relativeTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder_time_picker.show();
			}
		});

		arrayTxtView.get(8).setText(sharePref.getFrequency());
		arrayTxtView.get(10).setText(sharePref.getTime());

		//9:00 AM
		int hour = Integer.parseInt(sharePref.getTime().split(":")[0]);
		String min = sharePref.getTime().split(":")[1].split(" ")[0];
		if(min.equalsIgnoreCase("00"))
		{
			min = "0";
		}
		String AM_PM = sharePref.getTime().split(":")[1].split(" ")[1];
		if(AM_PM.equalsIgnoreCase("pm"))
		{
			hour+=12;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, Integer.parseInt(min));
		alarmTime = c.getTimeInMillis();

		return view;
	}

	public static SettingsFragment getInstance()
	{
		if(settingsfragment==null)
		{
			settingsfragment = new SettingsFragment();
		}
		return settingsfragment;
	}


	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			menu.getItem(i).setVisible(false);
		}
		super.onCreateOptionsMenu(menu, inflater);
	} 

	private void setTextViewId(int id,int i)
	{
		arrayTxtView.add((TextView) view.findViewById(id));
		if(arrayTxtView.get(i).getId()==R.id.text_setting7 || arrayTxtView.get(i).getId()==R.id.text_setting9 || arrayTxtView.get(i).getId()==R.id.text_setting11)
		{
			typeFace.setTypefaceLight(arrayTxtView.get(i));

		}
		else
		{
			typeFace.setTypefaceRegular(arrayTxtView.get(i));

		}
		arrayTxtView.get(i).setOnClickListener(SettingsFragment.this);
	}

	@SuppressLint("Recycle")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.text_setting1:

			Intent parentIntent=new Intent(getActivity(),ParentRegistrationActivity.class);
			Bundle bundle=new Bundle();
			bundle.putBoolean("ToParentScreen", true);
			parentIntent.putExtras(bundle);
			startActivity(parentIntent);
			break;
		case R.id.text_setting2:
			Intent locationIntent=new Intent(getActivity(),LocationActivity.class);
			Bundle bundleLocation=new Bundle();
			bundleLocation.putBoolean("ToLocationScreen", true);
			locationIntent.putExtras(bundleLocation);
			startActivity(locationIntent);
			break;
		case R.id.text_setting3:
			Intent childIntent=new Intent(getActivity(),ChildListActivity.class);
			startActivity(childIntent);
			break;
			/*case R.id.text_setting4:
			Intent allyIntent=new Intent(getActivity(),AllyListActivity.class);
			startActivity(allyIntent);
			break;*/
		case R.id.text_setting5:
			StaticVariables.fragmentIndexCurrentTabSettings=200;
			switchingFragments(new NotificationScreenFragment());
			break;
		case R.id.text_setting6:
			StaticVariables.isFromSettingsScreen=true;
			StaticVariables.fragmentIndexCurrentTabSettings=201;
			//switchingFragments(new HolidayCalenderFragment());
			switchingFragments(new HolidayListFragment());
			break;
		case R.id.text_setting7:

			break;
		case R.id.text_setting8:

			break;
		case R.id.text_setting9:

			break;
		case R.id.text_setting10:

			break;
		case R.id.text_setting11:

			break;

		default:
			break;
		}
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



	private void openCustomDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle("Select Frequency");
		final String[] array={"Daily","Weekly","Monthly"};
		int whichSelected=0;
		if(arrayTxtView.get(8).getText().toString().equalsIgnoreCase(array[2]))
		{
			whichSelected=2;
		}
		else if(arrayTxtView.get(8).getText().toString().equalsIgnoreCase(array[1]))

		{
			whichSelected=1;
		}
		else
		{
			whichSelected=0;
		}

		builder.setSingleChoiceItems(array, whichSelected,new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				arrayTxtView.get(8).setText(array[which]);
				sharePref.setFrequency(array[which]);

				//generatePendingIntent();
			}
		});



		AlertDialog alert = builder.create();
		alert.show();
	}



	private void initDialog()
	{
		// TODO Auto-generated method stub
		builder_time_picker = new Dialog(getActivity());
		builder_time_picker.setTitle("Select Time:");
		LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(R.layout.dialog_time_picker, null);

		final TimePicker mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);

		Calendar c = Calendar.getInstance();
		mTimePicker.setCurrentHour(c.get(Calendar.HOUR));
		mTimePicker.setCurrentMinute(c.get(Calendar.MINUTE));

		Button cancel_time = (Button) view.findViewById(R.id.cancel_time);
		cancel_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder_time_picker.dismiss();
			}
		});

		Button setTime = (Button) view.findViewById(R.id.set_time);
		setTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder_time_picker.dismiss();
				int month = Calendar.getInstance().get(Calendar.MONTH)+1;
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
				String dateStringToParse = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+
						month+"/"+
						Calendar.getInstance().get(Calendar.YEAR)+" "+
						mTimePicker.getCurrentHour()+":"+mTimePicker.getCurrentMinute();
				Date date =null;
				try {

					date = dateFormat.parse(dateStringToParse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				alarmTime = date.getTime();

				String dateToSet = new SimpleDateFormat("hh:mm aa").format(date);

				// Adding time for end time


				sharePref.setTime(dateToSet);
				SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
				sharePref.setTime24Hour(displayFormat.format(date));

				arrayTxtView.get(10).setText(dateToSet);

				/*selectedStartHour = mTimePicker.getCurrentHour();
					selectedStartMinute = mTimePicker.getCurrentMinute();*/



				//generatePendingIntent() ;

			}
		});

		builder_time_picker.setContentView(view);

	}
	/**
	 * @param array
	 * @param which
	 */
	private void generatePendingIntent() 
	{
		Intent alarmIntent = new Intent(getActivity(), FutureNotificationAlarmForMe.class);
		alarmIntent.putExtra("RepeatingType", sharePref.getFrequency());
		alarmIntent.setAction(Long.toString(System.currentTimeMillis()));

		long intervalMillis;
		//----Convert time for next day time scheduling------
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(alarmTime);

		if(sharePref.getFrequency().equalsIgnoreCase("Daily"))
		{
			c.add(Calendar.DAY_OF_MONTH, 1);
			intervalMillis = 24*60*60*1000;
		}
		else if(sharePref.getFrequency().equalsIgnoreCase("Monthly"))
		{
			intervalMillis = c.getActualMaximum(Calendar.DAY_OF_MONTH)*24*60*60*1000;
			c.add(Calendar.MONTH, 1);		
		}
		else
		{
			c.add(Calendar.WEEK_OF_YEAR, 1);
			intervalMillis = 7*24*60*60*1000;
		}


		PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);                        
		AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), intervalMillis, pendingIntent);

	}


	protected void switchingFragments(Fragment toFragment)
	{

		getFragmentManager().beginTransaction().replace(R.id.framelayout1,
				toFragment).commit();  
		getFragmentManager().executePendingTransactions();      // <----- This is the key 
	}


	private ProgressDialog progressDialog=null;

	private class SetReminderAsync extends AsyncTask<Void, Void, Integer>
	{





		private int day;
		com.hatchtact.pinwi.classmodel.Error err;

		public  SetReminderAsync()
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
		int ErrorCode=0;

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub

			if(new CheckNetwork().checkNetworkConnection(getActivity()))
			{


				if(sharePref.getFrequency().equalsIgnoreCase("Daily"))
				{
					day=1;
				}
				else if(sharePref.getFrequency().equalsIgnoreCase("Monthly"))
				{
					day=30;	
				}
				else
				{
					day=7;
				}
				err=new ServiceMethod().setRemainderByProfileID(StaticVariables.currentParentId, day,sharePref.getTime24Hour());

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

				if(result==-1)
				{
					new ShowMessages(getActivity()).showToastMessage("Please check your network connection");

					//if(checkNetwork.checkNetworkConnection(ChildEmoticonDetailActivity.this))
					//	new GetListofChildrensByChildActIDAsync(childID,pageIndex,activityId).execute();
				}
				else
				{
					ErrorCode=Integer.parseInt(err.getErrorCode());
					if(ErrorCode==0)
					{
						//com.hatchtact.pinwi.classmodel.Error err = new ServiceMethod().getError();	
						Toast.makeText(getActivity(),err.getErrorDesc(),Toast.LENGTH_LONG).show();
						//onBackSettings();
					}
					else
					{
						new ShowMessages(getActivity()).showAlert("Alert", err.getErrorDesc());
						//getError();

					}

					/*if(childList!=null && childList.getDetailByMapEmoticID().size()>0)
					{
						new ShowMessages(SettingsActivity.this).showToastMessage("Reminder set for user");

					}
					else
					{
						getError();
					}	*/
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}




	}
}
