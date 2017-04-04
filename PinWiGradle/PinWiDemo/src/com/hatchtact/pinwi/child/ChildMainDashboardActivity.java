package com.hatchtact.pinwi.child;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.child.playwall.ChildPlayWallActivity;
import com.hatchtact.pinwi.child.postcard.ChildPostcardActivity;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildMainDashboardActivity extends Activity
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
	private RelativeLayout layoutpostcard,layoutplaywall,layoutalerts,layoutbuddies,layoutwishlist,layoutpoint;
	private TextView textpostcard,textplaywall,textalerts,textbuddies,textwishlist,textpoint,textnotification;
	private Animation shake;
	private boolean isActivityFinished=false;
	private CustomLoader customProgressLoader;
	private SocialConstants social;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_maindashboard);
		customProgressLoader=new CustomLoader(this);
		social=new SocialConstants(ChildMainDashboardActivity.this);
		isButtonClicked=false;
		typeFace = new TypeFace(ChildMainDashboardActivity.this);
		sharepref = new SharePreferenceClass(ChildMainDashboardActivity.this);
		hideKeyBoard();

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



		new GetStatusForChildModule(StaticVariables.currentChild.getChildID()).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildMainDashboardActivity.this, R.raw.two_tone_nav);		
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
		showMessage=new ShowMessages(ChildMainDashboardActivity.this);
		serviceMethod=new ServiceMethod();
		layoutpostcard=(RelativeLayout) findViewById(R.id.layout_postcard);
		layoutplaywall=(RelativeLayout) findViewById(R.id.layout_playwall);
		layoutalerts=(RelativeLayout) findViewById(R.id.layout_alerts);
		layoutbuddies=(RelativeLayout) findViewById(R.id.layout_buddies);
		layoutwishlist=(RelativeLayout) findViewById(R.id.layout_wishlist);
		layoutpoint=(RelativeLayout) findViewById(R.id.layout_point);
		textpostcard=(TextView) findViewById(R.id.text_postcard);
		textplaywall=(TextView) findViewById(R.id.text_playwall);
		textalerts=(TextView) findViewById(R.id.text_alerts);
		textbuddies=(TextView) findViewById(R.id.text_buddies);
		textwishlist=(TextView) findViewById(R.id.text_wishlist);
		textpoint=(TextView) findViewById(R.id.text_point);
		textnotification=(TextView) findViewById(R.id.textView_notification);
		typeFace.setTypefaceGotham(textpostcard);
		typeFace.setTypefaceGotham(textplaywall);
		typeFace.setTypefaceGotham(textalerts);
		typeFace.setTypefaceGotham(textbuddies);
		typeFace.setTypefaceGotham(textwishlist);
		typeFace.setTypefaceGotham(textpoint);
		typeFace.setTypefaceGotham(textnotification);
		shake = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		layoutpostcard.startAnimation(shake);
		layoutplaywall.startAnimation(shake);
		layoutalerts.startAnimation(shake);
		layoutbuddies.startAnimation(shake);
		layoutpostcard.startAnimation(shake);
		layoutwishlist.startAnimation(shake);
		layoutpoint.startAnimation(shake);



	}


	private void setClickListeners()
	{

		// TODO Auto-generated method stub
		layoutpostcard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				social.child_AccessAnalyticsLog("PostCard_Tab");
				Intent intent = new Intent(ChildMainDashboardActivity.this, ChildPostcardActivity.class);
				startActivity(intent);
				ChildMainDashboardActivity.this.finish();
			}
		});
		layoutplaywall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				social.child_AccessAnalyticsLog("Playwall_Tab");
				Intent intent = new Intent(ChildMainDashboardActivity.this, ChildPlayWallActivity.class);
				startActivity(intent);
				ChildMainDashboardActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				ChildMainDashboardActivity.this.finish();


			}
		});
		layoutalerts.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				social.child_AccessAnalyticsLog("Alerts_Tab");
				Intent intent = new Intent(ChildMainDashboardActivity.this, ChildAlertActivity.class);
				intent.putExtra("count", count);
				startActivity(intent);
				ChildMainDashboardActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				ChildMainDashboardActivity.this.finish();
			}
		});
		layoutbuddies.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				social.child_AccessAnalyticsLog("Buddies_Tab");
				Intent intent = new Intent(ChildMainDashboardActivity.this, ChildBuddiesActivity.class);
				startActivity(intent);
				ChildMainDashboardActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				ChildMainDashboardActivity.this.finish();

			}
		});
		layoutwishlist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				social.child_AccessAnalyticsLog("Wishlist_Tab");
				Intent intent = new Intent(ChildMainDashboardActivity.this, ChildWishListActivity.class);
				startActivity(intent);
				ChildMainDashboardActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				ChildMainDashboardActivity.this.finish();

			}
		});
		layoutpoint.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub'
				social.child_AccessAnalyticsLog("Points_Tab");
				Intent intent = new Intent(ChildMainDashboardActivity.this, ChildPendingPointActivity.class);
				startActivity(intent);
				ChildMainDashboardActivity.this.finish();

			}
		});

	}

	//private ProgressDialog progressDialog=null;
	private int count=0;

	private class GetNotificationCountByChildIdAsync extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public GetNotificationCountByChildIdAsync(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildMainDashboardActivity.this))
			{
				count=serviceMethod.getNewNotificationCountOnCI(childID);
				//getPointsInfoByChildIDModel = serviceMethod.getPointsInfoByChildIDForChildModule(childID);
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
				customProgressLoader.removeCallbacksHandler();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/

				if(result==-1)
				{
					showMessage.showToastMessage("Please check your network connection");

					if(checkNetwork.checkNetworkConnection(ChildMainDashboardActivity.this))
						new GetNotificationCountByChildIdAsync(childID).execute();
				}
				else
				{
					textnotification.setText(count+"");
					/*
					if(getPointsInfoByChildIDModel!=null)
					{
						fillData();	
					}
					else
					{	
						getError();
					}	
					 */}	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		private void fillData() 
		{
			// TODO Auto-generated method stub


		}


		private void getError()
		{
			Error err = serviceMethod.getError();	
			showMessage.showAlert("Warning", err.getErrorDesc());
		} 

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
	private void finishActivity() {

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
			Intent intent = new Intent(ChildMainDashboardActivity.this, AccessProfileActivity.class);

			startActivity(intent);

			finish();
		}
	}

	private class GetStatusForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public GetStatusForChildModule(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			/*progressDialog = ProgressDialog.show(ChildMainDashboardActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildMainDashboardActivity.this))
			{
				ErrorCode = serviceMethod.getCurrentDayRatingStatusForChildModule(childID);
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

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");
				try {
					customProgressLoader.removeCallbacksHandler();
					/*if (progressDialog.isShowing())
						progressDialog.cancel();*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkNetwork.checkNetworkConnection(ChildMainDashboardActivity.this))
					new GetStatusForChildModule(childID).execute();
			}
			/*else if(result == 0)//Directly Move to DashBoard Activity of Child Module 
			{
				Intent intent = new Intent(AccessProfileActivity.this, ChildDashboardActivity.class);

				//bundle.putInt("ParentId", parentId);
				if(earnedPointsForChildSelected != 0)
				{
					Bundle bundle = new Bundle();
					bundle.putBoolean("PlaySound", true);
					intent.putExtras(bundle);
				}
				startActivity(intent);

				AccessProfileActivity.this.finish();
			}*/
			else /*if(result == 1)*/// Fetch Subjects by Day for that child one by one
			{
				StaticVariables.statusChild=result;//holding status of child 
				new GetNotificationCountByChildIdAsync(StaticVariables.currentChild.getChildID()).execute();
			}
		}	
	}

	private void hideKeyBoard() {



		try {
			ChildMainDashboardActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager) ChildMainDashboardActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow( ChildMainDashboardActivity.this
					.getCurrentFocus().getWindowToken(), 0);
			ChildMainDashboardActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}

	}

}
