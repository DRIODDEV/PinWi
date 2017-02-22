package com.hatchtact.pinwi.child;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.ChildAlertListAdapter;
import com.hatchtact.pinwi.classmodel.GetNotificationListByChildIDOnCIList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildAlertActivity extends Activity
{

	private ServiceMethod serviceMethod=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharepref = null;
	private HexagonImageView child_header_image;
	private ImageView child_header_music;
	private Bitmap bitmap;
	private TypeFace typeFace;
	private SoundEffect soundEffectButtonClicks = null;
	//private SoundEffect soundEffectTransition = null;
	private ImageView child_header_move_to_access_profile;
	private ChildMusicPlayer childMusicPlayer = null;
	private ImageView child_header_voice_over;
	private boolean isButtonClicked=false;
	private boolean isMusicStop = false;
	private boolean isMute = false;
	private RelativeLayout layout_alerts;
	private TextView txtViewAlertHeading;
	private ListView alerts_list;
	private Animation shake;
	private ChildAlertListAdapter adapter;
	private GetNotificationListByChildIDOnCIList alertsList;
	private ProgressBar data_load_progress;
	private boolean flag_loading=false;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext,text_alert;
	private ImageView child_alerts_imageview;
	private String newAlertCount;
	private CustomLoader customProgressLoader;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_alerts);
		customProgressLoader=new CustomLoader(this);
		newAlertCount=String.valueOf(getIntent().getExtras().getInt("count"));
		isButtonClicked=false;
		typeFace = new TypeFace(ChildAlertActivity.this);
		sharepref = new SharePreferenceClass(ChildAlertActivity.this);
		setHeaderItems();
		initSoundData();
		initialiseData();
		setClickListeners();

		playSound(AccessProfileActivity.soundEffectTransition);

		Bundle bundle = getIntent().getExtras();
		if(bundle!=null)
		{
			if(bundle.getBoolean("PlaySound", false))
			{
				//####### play media Sound #######
				childMusicPlayer = new ChildMusicPlayer(AccessProfileActivity.getInstance(), R.raw.voice6);
				childMusicPlayer.play();
			}
		}

		alertsList=new GetNotificationListByChildIDOnCIList();
		alertsList.getNotificationListByChildIDOnCIList().clear();


		new GetNotificationsByChildIdAsync(StaticVariables.currentChild.getChildID(),1).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildAlertActivity.this, R.raw.two_tone_nav);		
	}

	private void playSound(SoundEffect sound)
	{
		if(!isMute)
		{
			sound.play(1.0f);
		}
	}

	@SuppressLint("NewApi")
	private void setHeaderItems()
	{
		// TODO Auto-generated method stub
		child_header_image = (HexagonImageView) findViewById(R.id.child_header_image);
		try
		{
			if(StaticVariables.currentChild.getProfileImage()!=null && StaticVariables.currentChild.getProfileImage().trim().length()>10)
			{	
				/*byte[] imageByteParent=Base64.decode(StaticVariables.currentChild.getProfileImage(), 0);
				if(imageByteParent!=null)
				{
					bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
					child_header_image.setImageBitmap(bitmap);
				}*/
				String imagePath=AppUtils.PATHACCESSPROFILEIMAGES+StaticVariables.currentChild.getChildID()+".jpeg";
				Bitmap imageProfile=null;
				try {
					imageProfile = new AppUtils(this).getImagefromSDCard(imagePath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(imageProfile!=null)
				{
					bitmap=Bitmap.createScaledBitmap(imageProfile,dp2px(80),dp2px(80), false);
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
						if(childMusicPlayer!=null)
						{
							if(childMusicPlayer.getMediaPlayer().isPlaying())
							{
								childMusicPlayer.getMediaPlayer().stop();
							}
						}

					}
					setVoiceOverIcon();

				}
			});
		}

		setVoiceOverIcon();

		child_header_move_to_access_profile = (ImageView) findViewById(R.id.child_header_move_to_access_profile);
		child_header_move_to_access_profile.setBackgroundResource(R.drawable.back_child_dashboard);

		child_header_move_to_access_profile.setOnClickListener(new OnClickListener() {


			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
			}
		});

	}

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

	private void initialiseData() {

		// TODO Auto-generated method stub
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildAlertActivity.this);
		serviceMethod=new ServiceMethod();
		layout_alerts=(RelativeLayout) findViewById(R.id.layout_alert);
		txtViewAlertHeading=(TextView) findViewById(R.id.txtViewAlertHeading);
		data_load_progress=(ProgressBar) findViewById(R.id.data_load_progress);
		typeFace.setTypefaceGotham(txtViewAlertHeading);
		child_alerts_imageview=(ImageView) findViewById(R.id.child_alert_imageview);

		layout_nodata=(LinearLayout)findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView)findViewById(R.id.noconnectionimage);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext=(TextView) findViewById(R.id.noconnectiontext);
		typeFace.setTypefaceGotham(noconnectiontext);
		text_alert=(TextView) findViewById(R.id.text_alert);
		text_alert.setText(getResources().getString(R.string.home_text));
		typeFace.setTypefaceGotham(text_alert);



		shake = AnimationUtils.loadAnimation(this, R.anim.grow_from_bottom);
		//layoutpoint.startAnimation(shake);
		alerts_list=(ListView) findViewById(R.id.alerts_list);

		alerts_list.setVisibility(View.GONE);

		alerts_list.setOnScrollListener(new OnScrollListener() {



			public void onScrollStateChanged(AbsListView view, int scrollState) 
			{

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && totalItemCount>=8)
				{
					if(flag_loading == false)
					{
						flag_loading = true;
						//additems();//here we have to add items on scroll in search list and  

						//if(!isSearchList)
						{
							if(totalItemCount% 8==0)
							{
								new GetNotificationsByChildIdAsync(StaticVariables.currentChild.getChildID(),(totalItemCount/8)+1).execute();	
							}

						}
					}

				}
			}
		}
				);


	}


	private void setClickListeners()
	{
		// TODO Auto-generated method stub

		child_alerts_imageview.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playSound(soundEffectButtonClicks);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disposeSound();
				Intent intent = new Intent(ChildAlertActivity.this, ChildMainDashboardActivity.class);

				startActivity(intent);
				ChildAlertActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

				finish();

			}
		});


	}

	//private ProgressDialog progressDialog=null;
	private boolean isActivityFinished=false;

	private class GetNotificationsByChildIdAsync extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		int pageIndex=1;

		public  GetNotificationsByChildIdAsync(int childID, int i)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
			pageIndex=i;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(pageIndex==1)
			{
				if(customProgressLoader!=null)
				{
					customProgressLoader.startHandler();
				}
				/*progressDialog = ProgressDialog.show(ChildAlertActivity.this, "", StaticVariables.progressBarText, false);
				progressDialog.setCancelable(false);*/
			}
			else
			{
				data_load_progress.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;


			if(checkNetwork.checkNetworkConnection(ChildAlertActivity.this))
			{
				if(alertsList.getNotificationListByChildIDOnCIList().size()==0)
				{
					alertsList =serviceMethod.getNotificationList(childID,pageIndex,8);;
					flag_loading=false;
				}
				else
				{
					GetNotificationListByChildIDOnCIList list=serviceMethod.getNotificationList(childID,pageIndex,8);;
					if(list!=null && list.getNotificationListByChildIDOnCIList().size()>0)
					{
						flag_loading=false;
						alertsList.getNotificationListByChildIDOnCIList().addAll(list.getNotificationListByChildIDOnCIList());
					}

				}
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

				if(pageIndex==1)
				{
					customProgressLoader.removeCallbacksHandler();
					/*if (progressDialog.isShowing())
						progressDialog.cancel();*/
				}
				else
				{
					data_load_progress.setVisibility(View.GONE);
				}
				if(result==-1)
				{
					showMessage.showToastMessage("Please check your network connection");

					/*if(checkNetwork.checkNetworkConnection(ChildAlertActivity.this))
						new GetBuddiesListByChildID(childID,type,pageIndex).execute();*/
				}
				else
				{

					if(alertsList!=null && alertsList.getNotificationListByChildIDOnCIList().size()>0)
					{

						{
							/*if(alertsList.getNotificationListByChildIDOnCIList().size()>1)
								txtViewAlertHeading.setText(alertsList.getNotificationListByChildIDOnCIList().size()+" new alerts from your buddies.");
							else
							{
								txtViewAlertHeading.setText(alertsList.getNotificationListByChildIDOnCIList().size()+" new alert from your buddies.");

							}*/
							if(newAlertCount.length()>1)
								txtViewAlertHeading.setText(newAlertCount+" new alerts from your buddies.");
							else
							{
								txtViewAlertHeading.setText(newAlertCount+" new alert from your buddies.");

							}
							layout_nodata.setVisibility(View.GONE);
							noconnectionimage.setVisibility(View.GONE);
							noconnectiontext.setVisibility(View.GONE);
							alerts_list.setVisibility(View.VISIBLE);

							if(pageIndex==1)
							{
								alerts_list.startAnimation(shake);
								adapter=new ChildAlertListAdapter(ChildAlertActivity.this, alertsList);
								alerts_list.setAdapter(adapter);
								adapter.notifyDataSetChanged();
							}
							else
							{
								adapter.notifyDataSetChanged();
								alerts_list.invalidate();
							}


						}}
					else
					{


						getError();
					}	
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}




	}
	private void getError()
	{
		/*	Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
		layout_nodata.setVisibility(View.VISIBLE);*/
		layout_nodata.setVisibility(View.VISIBLE);
		alerts_list.setVisibility(View.GONE);
		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText(err.getErrorDesc());
	} 
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	private void disposeSound()
	{
		try{

			if(childMusicPlayer != null)
			{
				childMusicPlayer.stop();
				childMusicPlayer.release();
				childMusicPlayer = null;
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



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finishActivity();
	}

	/**
	 * 
	 */
	private void finishActivity() 
	{
		if(!isActivityFinished)
		{
			isActivityFinished=true;
		playSound(soundEffectButtonClicks);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disposeSound();
		Intent intent = new Intent(ChildAlertActivity.this, ChildMainDashboardActivity.class);

		startActivity(intent);
		ChildAlertActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

		finish();
		}
	}
}
