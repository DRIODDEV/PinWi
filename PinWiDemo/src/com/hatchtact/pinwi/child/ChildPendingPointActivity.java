package com.hatchtact.pinwi.child;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetPastDaysRatingStatusModel;
import com.hatchtact.pinwi.classmodel.GetPastDaysRatingStatusModelList;
import com.hatchtact.pinwi.classmodel.SubjectActivities;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildPendingPointActivity extends Activity
{
	private ServiceMethod serviceMethod=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharepref = null;
	private GetPastDaysRatingStatusModelList getPastDaysRatingStatusModelList = null;


	private LinearLayout child_star_pending_points_date_layout;
	private LinearLayout child_star_pending_points_day_layout;

	private HexagonImageView child_header_image;
	private ImageView child_header_music;

	private TextView child_star_pending_points_label;
	private TextView child_star_pending_points_date;

	private TextView child_star_pending_points_day;
	private ImageView child_star_pending_points_imageBar;

	private TextView child_star_pending_points_date_select_label;

	private RelativeLayout child_star_pending_points_day1_layout;
	private RelativeLayout child_star_pending_points_day2_layout;
	private RelativeLayout child_star_pending_points_day3_layout;
	private RelativeLayout child_star_pending_points_day4_layout;
	private RelativeLayout child_star_pending_points_day5_layout;
	private RelativeLayout child_star_pending_points_day6_layout;

	private Bitmap bitmap;

	private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy"); 
	private SimpleDateFormat initialFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	private SimpleDateFormat newFormat;

	private TypeFace typeFace;

	private boolean isMute = false;
	private SoundEffect soundEffectButtonClicks = null;
	private SoundEffect soundEffectTransition;
	private View child_header_move_to_access_profile;

	private boolean isPendingPointLeft = false;

	private ChildMusicPlayer childMusicPlayerPendingPt = null;
	private ImageView child_header_voice_over;
	private boolean isMusicStop = false;

	private ChildMusicPlayer childMusicPlayerNoPendingPt=null;

	private final long MILLISECONDS_IN_ONE_MIN = 60 * 1000;
	private final long MILLISECONDS_IN_ONE_HOUR = 60 * 60 * 1000;

	private Handler handler;
	/** The runnable1. */
	private Runnable runnable1;
	private boolean isTimerRunning=false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_child_pending_points);
		isTimerRunning=false;

		typeFace = new TypeFace(ChildPendingPointActivity.this);
		sharepref = new SharePreferenceClass(ChildPendingPointActivity.this);

		isPendingPointLeft = false;
		setHeaderItems();
		initSoundData();
		initialiseData();
		setClickListeners();

		playSound(AccessProfileActivity.soundEffectTransition);

		new GetPastDaysRatingStatus(StaticVariables.currentChild.getChildID()).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		soundEffectButtonClicks = new SoundEffect(ChildPendingPointActivity.this, R.raw.two_tone_nav);
		//soundEffectTransition = new SoundEffect(ChildPendingPointActivity.this, R.raw.pageflip);
		childMusicPlayerPendingPt = new ChildMusicPlayer(AccessProfileActivity.getInstance(), R.raw.voice5);
		childMusicPlayerNoPendingPt = new ChildMusicPlayer(AccessProfileActivity.getInstance(), R.raw.vo6_1);
	}

	private void playSound(SoundEffect sound)
	{
		if(!isMute)
		{
			sound.play(1.0f);
		}
	}
	@SuppressLint("NewApi")
	private void setHeaderItems() {
		// TODO Auto-generated method stub
		child_header_image = (HexagonImageView) findViewById(R.id.child_header_image);
		try
		{
			if(StaticVariables.currentChild!=null && StaticVariables.currentChild.getProfileImage()!=null && StaticVariables.currentChild.getProfileImage().trim().length()>100)
			{	
				byte[] imageByteParent=Base64.decode(StaticVariables.currentChild.getProfileImage(), 0);
				if(imageByteParent!=null)
				{
					bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
					child_header_image.setImageBitmap(bitmap);
				}
			}
			else
			{
				bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.child_image), dp2px(80), dp2px(80), false);
				child_header_image.setImageBitmap(bitmap);
			}
		}
		catch(OutOfMemoryError e)
		{

		}
		catch (Exception e) {
			// TODO: handle exception
		}

		child_header_music = (ImageView) findViewById(R.id.child_header_music);
		isMute = sharepref.isSound(StaticVariables.currentChild.getChildID() + "");
		isMusicStop = sharepref.isVoiceOver(StaticVariables.currentChild.getChildID() + "");

		setVolumeIcon();

		child_header_music.setOnClickListener(new OnClickListener() {


			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isMute = !isMute ;
				sharepref.setSound(isMute,StaticVariables.currentChild.getChildID() + "");
				if(isMute)
				{
					soundEffectButtonClicks.play(1.0f);
				}
				setVolumeIcon();

			}
		});

		child_header_voice_over  = (ImageView) findViewById(R.id.child_header_voice_over);
		/*if(!StaticVariables.isTutorialSoundEnabled)
		{
			child_header_voice_over.setVisibility(View.GONE);
		}
		else*/
		{
			child_header_voice_over.setOnClickListener(new OnClickListener() {


				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					isMusicStop = !isMusicStop ;
					sharepref.setVoiceOvers(isMusicStop,StaticVariables.currentChild.getChildID() + "");

					if(isMusicStop)
					{
						soundEffectButtonClicks.play(1.0f);
						if(childMusicPlayerPendingPt!=null)
						{
							if(childMusicPlayerPendingPt.getMediaPlayer().isPlaying())
							{
								childMusicPlayerPendingPt.getMediaPlayer().stop();
							}
						}


						if(childMusicPlayerNoPendingPt!=null)
						{
							if(childMusicPlayerNoPendingPt.getMediaPlayer().isPlaying())
							{
								childMusicPlayerNoPendingPt.getMediaPlayer().stop();
							}
						}

					}
					setVoiceOverIcon();

				}
			});
		}
		setVoiceOverIcon();

		child_header_move_to_access_profile = (ImageView) findViewById(R.id.child_header_move_to_access_profile);
		child_header_move_to_access_profile.setVisibility(View.GONE);
		child_header_move_to_access_profile.setOnClickListener(new OnClickListener() {


			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();

			}
		});

	}

	@SuppressLint("NewApi")
	private void setVoiceOverIcon() {
		if(isMusicStop)
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) 
			{	
				child_header_voice_over.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_voiceovermute));

			} else 
			{
				child_header_voice_over.setBackground(getResources().getDrawable(R.drawable.child_voiceovermute));

			}			
		}
		else
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) 
			{	
				child_header_voice_over.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_voiceover));

			} else 
			{
				child_header_voice_over.setBackground(getResources().getDrawable(R.drawable.child_voiceover));

			}	
		}
	}


	@SuppressLint("NewApi")
	private void setVolumeIcon() {
		if(isMute)
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) 
			{	
				child_header_music.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_mute));

			} else 
			{
				child_header_music.setBackground(getResources().getDrawable(R.drawable.child_mute));

			}			
		}
		else
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) 
			{	
				child_header_music.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_volume));

			} else 
			{
				child_header_music.setBackground(getResources().getDrawable(R.drawable.child_volume));

			}	
		}
	}

	@SuppressLint("NewApi")
	private void initialiseData() {

		// TODO Auto-generated method stub

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildPendingPointActivity.this);
		serviceMethod=new ServiceMethod();


		child_star_pending_points_date_layout = (LinearLayout) findViewById(R.id.child_star_pending_points_date_layout);
		child_star_pending_points_day_layout =  (LinearLayout) findViewById(R.id.child_star_pending_points_day_layout);

		child_star_pending_points_label = (TextView) findViewById(R.id.child_star_pending_points_label);
		typeFace.setTypefaceGotham(child_star_pending_points_label);
		child_star_pending_points_label.setAlpha(0.8f);

		child_star_pending_points_date_select_label = (TextView) findViewById(R.id.child_star_pending_points_date_select_label);
		typeFace.setTypefaceGothamLight(child_star_pending_points_date_select_label);
		child_star_pending_points_date_select_label.setAlpha(0.6f);


		child_star_pending_points_date = (TextView) findViewById(R.id.child_star_pending_points_date);
		child_star_pending_points_day = (TextView) findViewById(R.id.child_star_pending_points_day);
		child_star_pending_points_imageBar=(ImageView) findViewById(R.id.child_star_pending_points_imageBar);

		typeFace.setTypefaceGothamExtraLight(child_star_pending_points_date);
		typeFace.setTypefaceGotham(child_star_pending_points_day);


		int colorDay = Color.argb(20,255, 255, 255);
		int colorDate = Color.argb(50,255, 255, 255);


		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) 
		{
			child_star_pending_points_date_layout.setBackgroundDrawable(new CustomDrawable(new int[] {
					colorDate, colorDate }, dp2px(5),false));
			child_star_pending_points_day_layout.setBackgroundDrawable(new CustomDrawable(new int[] {
					colorDay, colorDay }, dp2px(5),true));

		} else 
		{
			child_star_pending_points_date_layout.setBackground(new CustomDrawable(new int[] {
					colorDate, colorDate}, dp2px(5),false));
			child_star_pending_points_day_layout.setBackground(new CustomDrawable(new int[] {
					colorDay, colorDay}, dp2px(5),true));

		}

		child_star_pending_points_day.setAlpha(0.8f);
		child_star_pending_points_date.setAlpha(1f);


		child_star_pending_points_day1_layout = (RelativeLayout) findViewById(R.id.child_star_pending_points_day1_layout);
		child_star_pending_points_day2_layout = (RelativeLayout) findViewById(R.id.child_star_pending_points_day2_layout);
		child_star_pending_points_day3_layout = (RelativeLayout) findViewById(R.id.child_star_pending_points_day3_layout);
		child_star_pending_points_day4_layout = (RelativeLayout) findViewById(R.id.child_star_pending_points_day4_layout);
		child_star_pending_points_day5_layout = (RelativeLayout) findViewById(R.id.child_star_pending_points_day5_layout);
		child_star_pending_points_day6_layout = (RelativeLayout) findViewById(R.id.child_star_pending_points_day6_layout);


		Calendar c = Calendar.getInstance();
		c.getTime();

		String strCurrentDate = c.getTime().toString();//"Wed, 18 Apr 2012 07:55:29 +0000";
		Date newDate;
		try {
			newDate = initialFormat.parse(strCurrentDate);

			newFormat = (new SimpleDateFormat("dd MMMM"));
			String date = newFormat.format(newDate);

			child_star_pending_points_date.setText("Today, " + date.toUpperCase());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		setBackgroundOfViews(child_star_pending_points_day1_layout);
		setBackgroundOfViews(child_star_pending_points_day2_layout);
		setBackgroundOfViews(child_star_pending_points_day3_layout);
		setBackgroundOfViews(child_star_pending_points_day4_layout);
		setBackgroundOfViews(child_star_pending_points_day5_layout);
		setBackgroundOfViews(child_star_pending_points_day6_layout);

		setInitialDataForPastDays();

	}

	private void setInitialDataForPastDays()
	{
		// TODO Auto-generated method stub
		Calendar todayDate = Calendar.getInstance();
		newFormat = new SimpleDateFormat("EEE, dd MMM");
		String strCurrentDate;
		Date newDate;

		RelativeLayout pastView1 = (RelativeLayout) findViewById(R.id.child_star_pending_points_day1);
		TextView item_child_pending_points_done_date1 = (TextView) pastView1.findViewById(R.id.item_child_pending_points_done_date);
		styleDonePointView(item_child_pending_points_done_date1);

		todayDate.setTimeInMillis(System.currentTimeMillis());
		todayDate.add(Calendar.DAY_OF_MONTH, -6);

		strCurrentDate = todayDate.getTime().toString();//"Wed, 18 Apr 2012 07:55:29 +0000";


		try {
			newDate = initialFormat.parse(strCurrentDate);
			String date = newFormat.format(newDate);
			item_child_pending_points_done_date1.setText(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RelativeLayout pastView2 = (RelativeLayout) findViewById(R.id.child_star_pending_points_day2);
		TextView item_child_pending_points_done_date2 = (TextView) pastView2.findViewById(R.id.item_child_pending_points_done_date);
		styleDonePointView(item_child_pending_points_done_date2);
		todayDate.setTimeInMillis(System.currentTimeMillis());
		todayDate.add(Calendar.DAY_OF_MONTH, -5);
		strCurrentDate = todayDate.getTime().toString();//"Wed, 18 Apr 2012 07:55:29 +0000";


		try {
			newDate = initialFormat.parse(strCurrentDate);
			String date = newFormat.format(newDate);
			item_child_pending_points_done_date2.setText(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		RelativeLayout pastView3 = (RelativeLayout) findViewById(R.id.child_star_pending_points_day3);
		TextView item_child_pending_points_done_date3 = (TextView) pastView3.findViewById(R.id.item_child_pending_points_done_date);
		styleDonePointView(item_child_pending_points_done_date3);
		todayDate.setTimeInMillis(System.currentTimeMillis());
		todayDate.add(Calendar.DAY_OF_MONTH, -4);
		strCurrentDate = todayDate.getTime().toString();//"Wed, 18 Apr 2012 07:55:29 +0000";

		try {
			newDate = initialFormat.parse(strCurrentDate);
			String date = newFormat.format(newDate);
			item_child_pending_points_done_date3.setText(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		RelativeLayout pastView4 = (RelativeLayout) findViewById(R.id.child_star_pending_points_day4);
		TextView item_child_pending_points_done_date4 = (TextView) pastView4.findViewById(R.id.item_child_pending_points_done_date);
		styleDonePointView(item_child_pending_points_done_date4);
		todayDate.setTimeInMillis(System.currentTimeMillis());
		todayDate.add(Calendar.DAY_OF_MONTH, -3);

		strCurrentDate = todayDate.getTime().toString();//"Wed, 18 Apr 2012 07:55:29 +0000";

		try {
			newDate = initialFormat.parse(strCurrentDate);
			String date = newFormat.format(newDate);
			item_child_pending_points_done_date4.setText(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		RelativeLayout pastView5 = (RelativeLayout) findViewById(R.id.child_star_pending_points_day5);
		TextView item_child_pending_points_done_date5 = (TextView) pastView5.findViewById(R.id.item_child_pending_points_done_date);
		styleDonePointView(item_child_pending_points_done_date5);
		todayDate.setTimeInMillis(System.currentTimeMillis());
		todayDate.add(Calendar.DAY_OF_MONTH, -2);

		strCurrentDate = todayDate.getTime().toString();//"Wed, 18 Apr 2012 07:55:29 +0000";

		try {
			newDate = initialFormat.parse(strCurrentDate);
			String date = newFormat.format(newDate);
			item_child_pending_points_done_date5.setText(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		RelativeLayout pastView6 = (RelativeLayout) findViewById(R.id.child_star_pending_points_day6);
		TextView item_child_pending_points_done_date6 = (TextView) pastView6.findViewById(R.id.item_child_pending_points_done_date);
		styleDonePointView(item_child_pending_points_done_date6);
		todayDate.setTimeInMillis(System.currentTimeMillis());
		todayDate.add(Calendar.DAY_OF_MONTH, -1);


		strCurrentDate = todayDate.getTime().toString();//"Wed, 18 Apr 2012 07:55:29 +0000";

		try {
			newDate = initialFormat.parse(strCurrentDate);
			String date = newFormat.format(newDate);
			item_child_pending_points_done_date6.setText(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}


	@SuppressLint("NewApi")
	private void setBackgroundOfViews(View viewToChange)
	{
		String hexColor = String.format("#%08X", (0x50FFFFFF & Color.argb(30,255, 255, 255)));;

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) 
		{
			viewToChange.setBackgroundDrawable(new CustomDrawable(new int[] {
					Color.TRANSPARENT, Color.TRANSPARENT },hexColor));

		} else 
		{

			viewToChange.setBackground(new CustomDrawable(new int[] {
					Color.TRANSPARENT, Color.TRANSPARENT },hexColor));

		}
	}

	private void setClickListeners() {
		// TODO Auto-generated method stub

	}

	boolean isFront[]={false,false,false,false,false,false};

	private void setDataDayWise(final int i, final GetPastDaysRatingStatusModel getPastDaysRatingStatusModel, final RelativeLayout mainLayout,final int day, final String daysAgo)
	{
		mainLayout.removeAllViews();
		final LayoutInflater  li =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if(getPastDaysRatingStatusModel.getStatus().equalsIgnoreCase("1"))
		{
			View view = li.inflate(R.layout.item_pending_point_done, mainLayout);
			TextView item_child_pending_points_done_date = (TextView) view.findViewById(R.id.item_child_pending_points_done_date);
			styleDonePointView(item_child_pending_points_done_date);

			ImageView item_child_pending_points_done_image=(ImageView) view.findViewById(R.id.item_child_pending_points_done_image);
			item_child_pending_points_done_image.setImageResource(R.drawable.earned_points_past);


			String strCurrentDate = getPastDaysRatingStatusModel.getActivityDate();
			newFormat = new SimpleDateFormat("EEE, dd MMM");

			try {
				Date newDate = format.parse(strCurrentDate);
				String date = newFormat.format(newDate);
				item_child_pending_points_done_date.setText(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			setBackgroundOfViews(view);	  

		}
		else if(getPastDaysRatingStatusModel.getStatus().equalsIgnoreCase("0")) //if it has pending points
		{
			isPendingPointLeft = true;
			//first show front layout...then back layout

			final View cardFace = li.inflate(R.layout.item_pending_points_left_front,mainLayout);
			TextView item_child_pending_points_left_date = (TextView) cardFace.findViewById(R.id.item_child_pending_points_left_date);
			final TextView item_child_pending_points_left_points = (TextView) cardFace.findViewById(R.id.item_child_pending_points_left_points);
			final TextView item_child_pending_points_left_label = (TextView) cardFace.findViewById(R.id.item_child_pending_points_left_label);

			stylePendingLeftPointFrontView(item_child_pending_points_left_date, item_child_pending_points_left_points, item_child_pending_points_left_label);

			String strCurrentDate = getPastDaysRatingStatusModel.getActivityDate();
			newFormat = new SimpleDateFormat("EEE, dd MMM");

			try {
				Date newDate = format.parse(strCurrentDate);
				String date = newFormat.format(newDate);
				item_child_pending_points_left_date.setText(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		

			item_child_pending_points_left_points.setText(getPastDaysRatingStatusModel.getPointsValue()+"");

			setBackgroundOfViews(cardFace);





			final FlipAnimationCustom flipAnimation = new FlipAnimationCustom(item_child_pending_points_left_points, item_child_pending_points_left_points);
			flipAnimation.setRepeatCount(FlipAnimation.INFINITE);
			flipAnimation.setRepeatMode(FlipAnimation.REVERSE);

			flipAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

					if(!isFront[i])
					{
						item_child_pending_points_left_points.setBackgroundResource(R.drawable.pending_points_past);
						item_child_pending_points_left_points.setText("");
						item_child_pending_points_left_label.setText("");

					}
					else
					{

						item_child_pending_points_left_points.setBackgroundResource(0);
						item_child_pending_points_left_points.setText(getPastDaysRatingStatusModel.getPointsValue()+"");
						item_child_pending_points_left_label.setText("Points ");


					}

					isFront[i]=!isFront[i];
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					/*View cardBack = li.inflate(R.layout.item_pending_point_done, null);
					TextView item_child_pending_points_done_date = (TextView) cardBack.findViewById(R.id.item_child_pending_points_done_date);
					styleDonePointView(item_child_pending_points_done_date);
					ImageView item_child_pending_points_done_image=(ImageView) cardBack.findViewById(R.id.item_child_pending_points_done_image);
					item_child_pending_points_done_image.setImageResource(R.drawable.pending_points_past);

					String strCurrentDate = getPastDaysRatingStatusModel.getActivityDate();
					newFormat = new SimpleDateFormat("EEE, dd MMM");

					try {
						Date newDate = format.parse(strCurrentDate);
						String date = newFormat.format(newDate);
						item_child_pending_points_done_date.setText(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					setBackgroundOfViews(cardBack);	
					mainLayout.removeAllViews();
					mainLayout.addView(cardBack);*/

				}
			});

			//			if (cardFace.getVisibility() == View.GONE)
			//			{
			//				flipAnimation.reverse();
			//			}
			item_child_pending_points_left_points.startAnimation(flipAnimation);

			mainLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					isTimerRunning=false;
					playSound(soundEffectButtonClicks);
					callingWebServiceForChildData(daysAgo);
				}
			});
		}
		else
		{
			/*View cardBack = li.inflate(R.layout.item_pending_points_left_back, mainLayout);
			TextView item_child_pending_points_left_weekday = (TextView) cardBack.findViewById(R.id.item_child_pending_points_left_weekday);
			TextView item_child_pending_points_left_date_of_month = (TextView) cardBack.findViewById(R.id.item_child_pending_points_left_date_of_month);
			TextView item_child_pending_points_left_month = (TextView) cardBack.findViewById(R.id.item_child_pending_points_left_month);

			stylePendingLeftPointBackView(item_child_pending_points_left_weekday, item_child_pending_points_left_date_of_month, item_child_pending_points_left_month);

			String strCurrentDate = getPastDaysRatingStatusModel.getActivityDate();
			newFormat = new SimpleDateFormat("EEE dd MMM");

			try {
				Date newDate = format.parse(strCurrentDate);
				String date = newFormat.format(newDate);

				String dates[] = date.split(" ");

				item_child_pending_points_left_weekday.setText(dates[0]);
				item_child_pending_points_left_date_of_month.setText(dates[1]);
				item_child_pending_points_left_month.setText(dates[2]);


			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			setBackgroundOfViews(cardBack);*/


			View view = li.inflate(R.layout.item_pending_point_done, mainLayout);
			TextView item_child_pending_points_done_date = (TextView) view.findViewById(R.id.item_child_pending_points_done_date);
			styleDonePointView(item_child_pending_points_done_date);
			ImageView item_child_pending_points_done_image=(ImageView) view.findViewById(R.id.item_child_pending_points_done_image);
			item_child_pending_points_done_image.setImageResource(R.drawable.lazy_past);

			String strCurrentDate = getPastDaysRatingStatusModel.getActivityDate();
			newFormat = new SimpleDateFormat("EEE, dd MMM");

			try {
				Date newDate = format.parse(strCurrentDate);
				String date = newFormat.format(newDate);
				item_child_pending_points_done_date.setText(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			setBackgroundOfViews(view);	  



		}
	}

	private void stylePendingLeftPointFrontView(TextView date, TextView points, TextView label)
	{
		date.setTextColor(Color.rgb(253, 184, 19));
		typeFace.setTypefaceGothamLight(date);
		date.setAlpha(0.6f);

		points.setTextColor(Color.rgb(253, 184, 19));
		typeFace.setTypefaceGothamExtraLight(points);
		points.setAlpha(0.8f);

		label.setTextColor(Color.rgb(253, 184, 19));
		typeFace.setTypefaceGothamLight(label);
		label.setAlpha(0.6f);
	}

	private void stylePendingLeftPointBackView(TextView day, TextView dateOfMonth, TextView month)
	{
		day.setTextColor(Color.WHITE);
		typeFace.setTypefaceGotham(day);
		day.setAlpha(0.6f);

		dateOfMonth.setTextColor(Color.WHITE);
		typeFace.setTypefaceGothamThin(dateOfMonth);
		dateOfMonth.setAlpha(0.6f);

		month.setTextColor(Color.WHITE);
		typeFace.setTypefaceGotham(month);
		month.setAlpha(0.6f);
	}

	private void styleDonePointView(TextView date)
	{
		typeFace.setTypefaceGotham(date);
		date.setTextColor(Color.WHITE);
		date.setAlpha(0.4f);		
	}

	private ProgressDialog progressDialog=null;

	private class GetPastDaysRatingStatus extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public GetPastDaysRatingStatus(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(ChildPendingPointActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildPendingPointActivity.this))
			{
				getPastDaysRatingStatusModelList = serviceMethod.getPastDaysRatingStatusForChildModule(childID);
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
					showMessage.showToastMessage("Please check your network connection");

					if(checkNetwork.checkNetworkConnection(ChildPendingPointActivity.this))
						new GetPastDaysRatingStatus(childID).execute();
				}
				else
				{
					if(getPastDaysRatingStatusModelList!=null)
					{

						fillBarData();
						fillData();	
					}
					else
					{	
						getError();
					}	
				}	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



		private void fillData() 
		{
			// TODO Auto-generated method stub


			setDataDayWise(5,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(5), child_star_pending_points_day2_layout,1,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(5).getDaysAgo());
			setDataDayWise(4,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(4), child_star_pending_points_day1_layout,2,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(4).getDaysAgo());
			setDataDayWise(3,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(3), child_star_pending_points_day4_layout,3,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(3).getDaysAgo());
			setDataDayWise(2,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(2), child_star_pending_points_day3_layout,4,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(2).getDaysAgo());
			setDataDayWise(1,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(1), child_star_pending_points_day6_layout,5,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(1).getDaysAgo());
			setDataDayWise(0,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(0), child_star_pending_points_day5_layout,6,getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(0).getDaysAgo());

			//play music for pending points lefts
			if(isPendingPointLeft)
			{
				playMusic(childMusicPlayerPendingPt);
			}
			else
			{
				playMusic(childMusicPlayerNoPendingPt);
			}

		}
	}


	/**
	 * 
	 */
	private void playMusic(ChildMusicPlayer childMusicPlayer) {
		if(childMusicPlayer !=null )
		{
			if(!isMusicStop)
			{
				childMusicPlayer.play();
			}
			else
			{
				childMusicPlayer.pause();
			}
		}
	}


	private void getError()
	{/*
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	 */} 

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	private void disposeSound()
	{
		try
		{

			if(childMusicPlayerPendingPt != null)
			{
				childMusicPlayerPendingPt.stop();
				childMusicPlayerPendingPt.release();
				childMusicPlayerPendingPt = null;
			}


			if(childMusicPlayerNoPendingPt != null)
			{
				childMusicPlayerNoPendingPt.stop();
				childMusicPlayerNoPendingPt.release();
				childMusicPlayerNoPendingPt = null;
			}


			if(soundEffectButtonClicks!=null){
				soundEffectButtonClicks.release();
				soundEffectButtonClicks = null;
			}

			System.gc();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	/**  ---------------------Child Module Methods-----------------------   */
	private void callingWebServiceForChildData(final String daysAgo)
	{
		// TODO Auto-generated method stub
		if(AccessProfileActivity.subjectsFetched!=null)
			AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().clear();

		if(AccessProfileActivity.subjectsFetchedAfterSchool!=null)
			AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().clear();

		AccessProfileActivity.subjectsScheduled.clear();

		new GetChildAfterSchoolActiviesByDayForChildModule(StaticVariables.currentChild.getChildID(),daysAgo).execute();

	}


	private class GetChildAfterSchoolActiviesByDayForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		private String daysAgo;

		public GetChildAfterSchoolActiviesByDayForChildModule(int childID,String daysAgo)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
			this.daysAgo = daysAgo;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(ChildPendingPointActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildPendingPointActivity.this))
			{
				ErrorCode = serviceMethod.getChildAfterSchoolActiviesByDayForChildModule(childID,daysAgo);
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

			/*try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");
				try {
					if (progressDialog.isShowing())
						progressDialog.cancel();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkNetwork.checkNetworkConnection(ChildPendingPointActivity.this))
					new GetChildAfterSchoolActiviesByDayForChildModule(childID,daysAgo).execute();
			}
			else if(result == 0)//Call another webservice
			{
				new GetChildSubjectActiviesByDayForChildModule(childID,daysAgo).execute();
			}

		}	
	}

	private class GetChildSubjectActiviesByDayForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		private String daysAgo;


		public GetChildSubjectActiviesByDayForChildModule(int childID, String daysAgo)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
			this.daysAgo = daysAgo;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			/*progressDialog = ProgressDialog.show(ChildPendingPointActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildPendingPointActivity.this))
			{
				ErrorCode = serviceMethod.getChildSubjectActiviesByDayForChildModule(childID,daysAgo);
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

				if(checkNetwork.checkNetworkConnection(ChildPendingPointActivity.this))
					new GetChildSubjectActiviesByDayForChildModule(childID,daysAgo).execute();
			}
			else if(result == 0)//Move to Star Rating if any subjects are scheduled
			{
				if(AccessProfileActivity.subjectsFetchedAfterSchool!=null && AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().size()>0)
				{
					for(int i=0;i<AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().size();i++)
					{
						SubjectActivities subjectActivities = new SubjectActivities();
						subjectActivities.setSubjectID(AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().get(i).getActivityID());
						subjectActivities.setSubjectName(AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().get(i).getName());
						AccessProfileActivity.subjectsScheduled.add(subjectActivities);
					}

				}

				if(AccessProfileActivity.subjectsFetched!=null && AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().size()>0)
				{
					for(int i=0;i<AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().size();i++)
					{
						SubjectActivities subjectActivities = new SubjectActivities();
						subjectActivities.setSubjectID(AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().get(i).getActivityID());
						subjectActivities.setSubjectName(AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().get(i).getName());
						AccessProfileActivity.subjectsScheduled.add(subjectActivities);
					}

				}

				if(AccessProfileActivity.subjectsScheduled.size()>0)
				{
					//then toh fine h ji
					disposeSound();

					StaticVariables.daysAgo = daysAgo;
					StaticVariables.isTutorialSoundEnabled = false;

					if(!isTimerRunning)
					{
						Intent intent = new Intent(ChildPendingPointActivity.this, ChildSetStarRatingActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("ParentId", StaticVariables.currentParentId);
						bundle.putBoolean("Pendingflag", true);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					else
					{
						StaticVariables.statusChild=2;
						Intent intent = new Intent(ChildPendingPointActivity.this, ChildTutorialActivity.class);
						startActivity(intent);

					}
					ChildPendingPointActivity.this.finish();

				}
				else
					getError();
			}

		}	
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finishActivity();

	}

	/**
	 * 
	 */
	private void finishActivity() {
		playSound(soundEffectButtonClicks);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disposeSound();
	//	Intent intent = new Intent(ChildPendingPointActivity.this, ChildDashboardActivity.class);
		Intent intent = new Intent(ChildPendingPointActivity.this, ChildMainDashboardActivity.class);

		/*Bundle bundle = new Bundle();
		bundle.putInt("ParentId", StaticVariables.currentParentId);
		intent.putExtras(bundle);*/
		startActivity(intent);

		if (handler != null) 
		{
			handler.removeCallbacks(runnable1);
			handler = null;
			runnable1=null;
		}

		finish();
	}
	String reducedTimeString;

	private void fillBarData() 
	{
		// TODO Auto-generated method stub

		if(StaticVariables.statusChild==2 || StaticVariables.statusChild==1 )
		{
			child_star_pending_points_imageBar.setImageResource(R.drawable.cup_bar);
			//child_star_pending_points_day.setText("Great! You earned " + StaticVariables.earnedPts + " points.");
			child_star_pending_points_day.setText("Great! You earned 70 points.");

		}

		else if(StaticVariables.statusChild==3 || StaticVariables.statusChild==5 || StaticVariables.statusChild==4)//time updation
		{

			child_star_pending_points_imageBar.setImageResource(R.drawable.clock_bar);
			reducedTimeString=getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(0).getRemaningTime();

			refreshTimerData(false);
			//child_star_pending_points_day.setText("Start rating in " +getPastDaysRatingStatusModelList.getListOfSchoolSubjects().get(0).getRemaningTime());

			if (handler == null)
			{
				startTimerForRefresh(MILLISECONDS_IN_ONE_MIN,true);
			}
		} 
		else if(StaticVariables.statusChild==0 )
		{

			child_star_pending_points_imageBar.setImageResource(R.drawable.lazy_bar);
			child_star_pending_points_day.setText("You had no activities today!");

		}



	}



	private void startTimerForRefresh(final long refresh, final boolean isTimer)
	{
		// TODO Auto-generated method stub
		if (handler != null) 
		{
			handler.removeCallbacks(runnable1);
			handler = null;
		}

		runnable1 = new Runnable() 
		{
			public void run() 
			{

				try 
				{
					refreshTimerData(isTimer);


				} catch (Exception e) {

					e.printStackTrace();
				}

				if (handler != null)
					handler.postDelayed(this, refresh);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable1,refresh);


	}

	String displayedText;
	private void refreshTimerData(boolean isTimer)
	{
		// TODO Auto-generated method stub

		try {
			String[] timeArray =reducedTimeString.split("h");
			int getHour=Integer.parseInt(timeArray[0].replace("h", ""));
			String[] minute=timeArray[1].split("m");
			int get_minute =Integer.parseInt(minute[0].toString().trim());

			int hourinMilliseconds = (int) (getHour * MILLISECONDS_IN_ONE_HOUR);
			int minutesinMilliseconds = (int) (get_minute * MILLISECONDS_IN_ONE_MIN);

			int totaltime=hourinMilliseconds + minutesinMilliseconds;

			int reducedTime;
			if(isTimer)
			{
				reducedTime=(int) (totaltime - MILLISECONDS_IN_ONE_MIN);//need to make changes

			}
			else
			{
				reducedTime=totaltime;
			}

			int calculatedminutes = (int) ((reducedTime / (1000*60)) % 60);
			int calculatedhours   = (int) ((reducedTime / (1000*60*60)) % 24);

			if(calculatedhours==0 && calculatedminutes==0)
			{
				reducedTimeString="";
				displayedText="";
				displayedText+= "0" + " h " + "0" +" m";
				isTimerRunning=true;
				callingWebServiceForChildData("0");
				if (handler != null) 
				{
					handler.removeCallbacks(runnable1);
					handler = null;
					runnable1=null;
				}
			}
			else
			{
				reducedTimeString="";
				displayedText="";
				if(calculatedhours<10)
				{
					reducedTimeString="0";

				}
				displayedText+=reducedTimeString + calculatedhours + " h " + calculatedminutes +" m";
				reducedTimeString+=calculatedhours+ "h" + calculatedminutes + "m";

			}




			child_star_pending_points_day.setText("Rate for today in " +displayedText);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
