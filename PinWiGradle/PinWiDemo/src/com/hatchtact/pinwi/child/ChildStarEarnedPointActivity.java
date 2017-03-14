package com.hatchtact.pinwi.child;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
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

public class ChildStarEarnedPointActivity extends Activity
{
	private ServiceMethod serviceMethod=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharepref = null;


	private TextView child_star_earned_points_first_text;
	private TextView child_star_earned_points_last_text;

	private TextView child_star_earned_points_text;
	private HexagonImageView child_header_image;
	private ImageView child_header_music;
	private Bitmap bitmap;

	private int earnedPoints = 0;
	private TypeFace typeFace;


	private SoundEffect soundEffectStar = null;
	//private SoundEffect soundEffectTransition = null;
	private SoundEffect soundEffectButtonClicks = null;
	private ImageView child_header_move_to_access_profile;

	private ChildMusicPlayer childMusicPlayer = null;

	private boolean isHandlerActive = false;
	private ImageView child_header_voice_over;

	private boolean isMusicStop = false;
	private boolean isMute = false;
	private SocialConstants social;
	private CustomLoader customProgressLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_child_star_earned_point);
		customProgressLoader=new CustomLoader(this);

		social=new SocialConstants(this);
		social.Child_RatingsFacebookLog();
		social.Child_RatingsGoogleAnalyticsLog();
		typeFace = new TypeFace(ChildStarEarnedPointActivity.this);
		sharepref = new SharePreferenceClass(ChildStarEarnedPointActivity.this);

		setHeaderItems();
		initSoundData();
		initialiseData();

		new AddPointsEarned(StaticVariables.currentChild.getChildID()).execute();

	}

	private void initSoundData()
	{
		// TODO Auto-generated method stub
		soundEffectStar = new SoundEffect(ChildStarEarnedPointActivity.this, R.raw.star_animation);
		//soundEffectTransition  = new SoundEffect(ChildStarEarnedPointActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildStarEarnedPointActivity.this, R.raw.two_tone_nav);
		childMusicPlayer = new ChildMusicPlayer(ChildStarEarnedPointActivity.this, R.raw.voice4);

		playSound(AccessProfileActivity.soundEffectTransition);
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
		try{
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

						placeHandlerToFinishActivity();

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


	private void initialiseData() {
		// TODO Auto-generated method stub
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildStarEarnedPointActivity.this);
		serviceMethod=new ServiceMethod();


		child_star_earned_points_first_text = (TextView) findViewById(R.id.child_star_earned_points_first_text);
		typeFace.setTypefaceGotham(child_star_earned_points_first_text);

		child_star_earned_points_last_text = (TextView) findViewById(R.id.child_star_earned_points_last_text);
		typeFace.setTypefaceGotham(child_star_earned_points_last_text);

		child_star_earned_points_text = (TextView) findViewById(R.id.child_star_earned_points_text);
		typeFace.setTypefaceGotham(child_star_earned_points_text);

	}
	//private ProgressDialog progressDialog=null;

	private class AddPointsEarned extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public AddPointsEarned(int childID)
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
			/*progressDialog = ProgressDialog.show(ChildStarEarnedPointActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildStarEarnedPointActivity.this))
			{
				earnedPoints = serviceMethod.getAddPointsEarned(childID);
			}
			else
			{
				ErrorCode=-1;
				earnedPoints = -1;
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer  result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
				customProgressLoader.removeCallbacksHandler();
				if(result==-1)
				{
					showMessage.showToastMessage("Please check your network connection");

					if(checkNetwork.checkNetworkConnection(ChildStarEarnedPointActivity.this))
						new AddPointsEarned(childID).execute();
				}
				else
				{
					if(earnedPoints == -1)
					{
						getError();
					}
					else
					{
						child_star_earned_points_text.setText(earnedPoints+"");
						playSound(soundEffectStar);

						//if voice over is not to be playes...comes from pending point screen
						if(StaticVariables.isTutorialSoundEnabled)
						{
							childMusicPlayer.play();
							childMusicPlayer.getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer mp) {
									// TODO Auto-generated method stub
									if(bitmap!=null)
									{
										bitmap.recycle();
										bitmap = null;
									}
									System.gc();
									disposeSound();
									finish();
									//Intent intent = new Intent(ChildStarEarnedPointActivity.this, ChildDashboardActivity.class);
									Intent intent = new Intent(ChildStarEarnedPointActivity.this, ChildMainDashboardActivity.class);

									startActivity(intent);
								}
							});
						}
						else
						{
							placeHandlerToFinishActivity();
						}
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



	}

	private void placeHandlerToFinishActivity() {
		Handler handler = new Handler();

		// run a thread after 2 seconds to start new screen
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if(bitmap!=null)
				{
					bitmap.recycle();
					bitmap = null;
				}
				System.gc();
				disposeSound();
				finish();
				//Intent intent = new Intent(ChildStarEarnedPointActivity.this, ChildDashboardActivity.class);
				Intent intent = new Intent(ChildStarEarnedPointActivity.this, ChildMainDashboardActivity.class);
				startActivity(intent);

			}

		}, 1500);
	}

	private void getError()
	{
		Error err = serviceMethod.getError();
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	private void disposeSound()
	{
		try{
			if(soundEffectStar!=null){
				soundEffectStar.release();
				soundEffectStar = null;
			}

			if(childMusicPlayer != null)
			{
				childMusicPlayer.stop();
				childMusicPlayer.release();
				childMusicPlayer = null;
			}
			System.gc();


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
		playSound(soundEffectButtonClicks);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disposeSound();

		Intent intent = new Intent(ChildStarEarnedPointActivity.this, AccessProfileActivity.class);

		startActivity(intent);

		finish();
	}
}
