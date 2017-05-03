package com.hatchtact.pinwi.child.postcard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.child.ChildMainDashboardActivity;
import com.hatchtact.pinwi.child.ChildMusicPlayer;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.child.SoundEffect;
import com.hatchtact.pinwi.child.postcard.AnimatedGifImageView.TYPE;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.GifView;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildPostcardDetailingAudioActivity  extends Activity {

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
	private boolean isMusicStop = false;
	private boolean isMute = false;

	private int selectedIndex;

	private LinearLayout child_header_title_label_layout;
	private TextView child_header_title_label;

	//common
	private LinearLayout layout_bottomlayer_postcard_detail;
	private ImageView child_selected_imageview;
	private TextView text_selected;
	private RelativeLayout child_postcard_detail_mid_audio,layout_postcard_detail,layout_post;

	//audio
	private GifView postcard_audio_image_gif;
	private ImageView postcard_audio_image;
	private TextView audio_title_label;
	private TextView audio_counter_label;
	private LinearLayout layout_bottomlayer_postcard_audio;
	private TextView audio_postcard_selected;

	//sent layout
	private RelativeLayout child_postcard_sent_layout;
	private TextView postcard_sent_message;

	private Handler handler;
	private Runnable runnable;

	private MediaRecorder myRecorder;
	private MediaPlayer myPlayer;
	private String outputFile = null;
	private int startValue = 120;
	private int min;
	private Handler handlerForTime;
	private Runnable runnableForTime;
	private Handler handlerForPlaying;
	private Runnable runnableForPlaying;
	private boolean isRecording;
	private boolean isPlaying;

	private int playDuration;
	private RelativeLayout layout_audio_postcard;
	private TextView text_postcard,text_post;
	private boolean isActivityFinished=false;
	//Donot allow the touch of bottom items when the recording is going on.
	private boolean isAudioRecordingON = false;
	private SocialConstants social;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_postcard_audio);
		typeFace = new TypeFace(ChildPostcardDetailingAudioActivity.this);
		sharepref = new SharePreferenceClass(ChildPostcardDetailingAudioActivity.this);
		social=new SocialConstants(this);
		System.gc();
		System.gc();
		System.gc();
		System.gc();

		setHeaderItems();
		initSoundData();
		getBundleValues();
		initialiseData();
		setOnClickListeners();

		if(AccessProfileActivity.soundEffectTransition==null){
			AccessProfileActivity.soundEffectTransition = new SoundEffect(this, R.raw.pageflip);
		}
		playSound(AccessProfileActivity.soundEffectTransition);

		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			if(bundle.getBoolean("PlaySound", false)){
				//####### play media Sound #######
				childMusicPlayer = new ChildMusicPlayer(AccessProfileActivity.getInstance(), R.raw.voice6);
				childMusicPlayer.play();
			}
		}
		overridePendingTransition(R.anim.activity_open_scale,R.anim.fade_in);
	}

	private void getBundleValues() {
		selectedIndex = getIntent().getIntExtra("ARRAY_VALUE", 0);
	}

	private void initSoundData() {
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildPostcardDetailingAudioActivity.this, R.raw.two_tone_nav);		
	}

	private void playSound(SoundEffect sound){
		if(!isMute && sound!=null){
			sound.play(1.0f);
		}
	}

	@SuppressLint("NewApi")
	private void setHeaderItems()
	{
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
		}catch(OutOfMemoryError e){
		}catch (Exception e) {
		}

		child_header_music = (ImageView) findViewById(R.id.child_header_music);

		if(StaticVariables.currentChild!=null){
			isMute = sharepref.isSound(StaticVariables.currentChild.getChildID() + "");
			isMusicStop = sharepref.isVoiceOver(StaticVariables.currentChild.getChildID() + "");
		}
		setVolumeIcon();

		child_header_music.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				isMute = !isMute ;
				sharepref.setSound(isMute,StaticVariables.currentChild.getChildID() + "");
				if(isMute){
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
					isMusicStop = !isMusicStop ;
					sharepref.setVoiceOvers(isMusicStop,StaticVariables.currentChild.getChildID() + "");
					if(isMusicStop){
						soundEffectButtonClicks.play(1.0f);
						if(childMusicPlayer!=null){
							if(childMusicPlayer.getMediaPlayer().isPlaying()){
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
				finishActivity();
			}
		});
	}

	@SuppressLint("NewApi")
	private void setVoiceOverIcon() {
		if(isMusicStop){
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {	
				child_header_voice_over.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_voiceovermute));
			} else {
				child_header_voice_over.setBackground(getResources().getDrawable(R.drawable.child_voiceovermute));
			}			
		}else{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){	
				child_header_voice_over.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_voiceover));
			} else {
				child_header_voice_over.setBackground(getResources().getDrawable(R.drawable.child_voiceover));
			}	
		}
	}

	@SuppressLint("NewApi")
	private void setVolumeIcon() {
		if(isMute){
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {	
				child_header_music.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_mute));
			} else {
				child_header_music.setBackground(getResources().getDrawable(R.drawable.child_mute));
			}			
		}else{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){	
				child_header_music.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_volume));
			} else {
				child_header_music.setBackground(getResources().getDrawable(R.drawable.child_volume));
			}	
		}
	}

	@SuppressLint("NewApi")
	private void initialiseData() {

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildPostcardDetailingAudioActivity.this);
		serviceMethod=new ServiceMethod();

		child_header_title_label_layout = (LinearLayout) findViewById(R.id.child_header_title_label_layout);
		child_header_title_label_layout.setVisibility(View.VISIBLE);
		child_header_title_label_layout.setBackgroundColor(Color.parseColor(ChildPostcardActivity.colorArray.get(selectedIndex).getInnerColor()));
		child_header_title_label_layout.setAlpha(1.0f);

		child_header_title_label = (TextView) findViewById(R.id.child_header_title_label);
		typeFace.setTypefaceGothamMedium(child_header_title_label);
		child_header_title_label.setText(ChildPostcardActivity.templateArray.get(selectedIndex));

		//common
		layout_bottomlayer_postcard_detail = (LinearLayout) findViewById(R.id.layout_bottomlayer_postcard_detail);
		layout_bottomlayer_postcard_detail.setVisibility(View.GONE);

		layout_postcard_detail = (RelativeLayout) findViewById(R.id.layout_postcard_detail);
		layout_post = (RelativeLayout) findViewById(R.id.layout_post);
		layout_post.setAlpha(.5f);

		child_selected_imageview = (ImageView) findViewById(R.id.child_selected_imageview);
		text_selected = (TextView) findViewById(R.id.text_selected);
		typeFace.setTypefaceGotham(text_selected);

		child_postcard_sent_layout = (RelativeLayout) findViewById(R.id.child_postcard_sent_layout);
		postcard_sent_message = (TextView) findViewById(R.id.postcard_sent_message);
		typeFace.setTypefaceGotham(postcard_sent_message);

		child_postcard_detail_mid_audio  = (RelativeLayout) findViewById(R.id.child_postcard_detail_mid_audio);
		child_postcard_detail_mid_audio.setBackgroundColor(Color.argb(66,218, 33, 39));
		//layout_alphabetical = (RelativeLayout) findViewById(R.id.layout_postcard_detail);
		layout_postcard_detail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(!isAudioRecordingON)
				{
					moveToDashboard();	
				}
			}
		});
		initAudioLayoutVariable();
	}

	private void initAudioLayoutVariable() {

		isRecording = false;
		isPlaying = false;
		//audio
		postcard_audio_image = (ImageView) findViewById(R.id.postcard_audio_image);

		postcard_audio_image_gif = (GifView) findViewById(R.id.postcard_audio_image_gif);

		audio_title_label = (TextView) findViewById(R.id.audio_title_label);
		typeFace.setTypefaceGotham(audio_title_label);
		audio_title_label.setAlpha(0.6f);

		audio_counter_label = (TextView) findViewById(R.id.audio_counter_label);
		typeFace.setTypefaceGotham(audio_counter_label);
		audio_counter_label.setAlpha(0.6f);

		layout_bottomlayer_postcard_audio = (LinearLayout) findViewById(R.id.layout_bottomlayer_postcard_audio);
		layout_audio_postcard= (RelativeLayout) findViewById(R.id.layout_audio_postcard);
		layout_audio_postcard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isAudioRecordingON){
					moveToDashboard();	
				}
			}
		});

		audio_postcard_selected = (TextView) findViewById(R.id.audio_postcard_selected);
		audio_postcard_selected.setText(getResources().getString(R.string.home_text));
		text_postcard=(TextView) findViewById(R.id.text_postcard);
		text_post = (TextView) findViewById(R.id.text_post);
		typeFace.setTypefaceGotham(audio_postcard_selected);
		typeFace.setTypefaceGotham(text_postcard);
		text_postcard.setText(getResources().getString(R.string.home_text));
		typeFace.setTypefaceGotham(text_post);

		startValue = 120;
		min = 2;

		setMediaRecorderObject();
	}

	//byte[] dd; 
	private void setMediaRecorderObject() {
		outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/audio_"+System.currentTimeMillis()+".3gpp";
		myRecorder = new MediaRecorder();
		/*FileInputStream fns;
		try {
			fns = new FileInputStream(new File(outputFile));
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			dd = new byte[fns.available()]; 
			fns.read(dd); 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		Toast.makeText(getApplicationContext(), "inside fileinputstream"+dd,Toast.LENGTH_LONG).show(); */
		/*try {
			convertToBytes();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myRecorder.setMaxDuration(2*60*1000);
		myRecorder.setOutputFile(outputFile);
	}

	String encodedAudioString="";
	@SuppressLint("NewApi")
	private void setOnClickListeners(){
		child_header_title_label_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});

		layout_post.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isAudioRecordingON){
					//*********** Need to check..stop the recording before sending to server  ***********
					setPlayRecordingNow();  
					stopPlay();
					//*********** Need to check..stop the recording before sending to server  ***********

					try {
						encodedAudioString= bitmapTobyte(convertToBytes());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						encodedAudioString="";
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						encodedAudioString="";
						e.printStackTrace();
					}

					if(encodedAudioString!=null && encodedAudioString.trim().length()>0)
					{
						showMessage.showAlertChildInterface(ChildPostcardDetailingAudioActivity.this, "Confirmation", "Woah!! All set to post this?", new OnEventListener<String>() {

							@Override
							public void onSuccess(String object)
							{
								new AddPostcardTask(ChildPostcardDetailingAudioActivity.this, selectedIndex+1, StaticVariables.currentChild.getChildID(), 3, encodedAudioString, 1, new OnEventListener<String>() {

									@Override
									public void onSuccess(String object) {
										// TODO Auto-generated method stub
										/*social.Create_Postcard_VOSentFacebookLog();
										social.Create_Postcard_VOGoogleAnalyticsLog();*/
										try {
											social.postcardAddedClevertap(ChildPostcardActivity.templateArray.get(selectedIndex), "Recording");
										}
										catch (Exception e)
										{

										}
										child_postcard_sent_layout.setVisibility(View.VISIBLE);
										layout_bottomlayer_postcard_detail.setVisibility(View.GONE);
										child_postcard_detail_mid_audio.setVisibility(View.INVISIBLE);
										child_header_title_label_layout.setVisibility(View.INVISIBLE);
										placeHandlerToFinishActivity();
									}

									@Override
									public void onFailure(String object) {
										// TODO Auto-generated method stub

									}
								}).execute();	
							}

							@Override
							public void onFailure(String object) {
								// TODO Auto-generated method stub

							}
						});
						/*new AddPostcardTask(ChildPostcardDetailingAudioActivity.this, selectedIndex+1, StaticVariables.currentChild.getChildID(), 3, encodedAudioString, 1, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							// TODO Auto-generated method stub
							child_postcard_sent_layout.setVisibility(View.VISIBLE);
							layout_bottomlayer_postcard_detail.setVisibility(View.GONE);
							child_postcard_detail_mid_audio.setVisibility(View.INVISIBLE);
							child_header_title_label_layout.setVisibility(View.INVISIBLE);
							placeHandlerToFinishActivity();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub

						}
					}).execute();*/
					}
					else
					{
						Toast.makeText(ChildPostcardDetailingAudioActivity.this, "Post cannot be left empty", Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

		child_selected_imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopAndDelete();
				setMediaRecorderObject();
				isPlaying = false;

				layout_bottomlayer_postcard_detail.setVisibility(View.VISIBLE);
				layout_bottomlayer_postcard_audio.setVisibility(View.INVISIBLE);
				postcard_audio_image.setVisibility(View.VISIBLE);
				postcard_audio_image_gif.setVisibility(View.INVISIBLE);
				audio_counter_label.setText("02:00 min (Max.)");
				audio_title_label.setVisibility(View.VISIBLE);
				audio_title_label.setText("Tap to Start Recording...");

				isRecording = false;
				layout_post.setAlpha(1f);

				isAudioRecordingON = false;

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){	
					postcard_audio_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.mic_icon));
				} else {
					postcard_audio_image.setBackground(getResources().getDrawable(R.drawable.mic_icon));
				}
			}
		});

		postcard_audio_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//audio_title_label.setVisibility(View.INVISIBLE);
				if(!isRecording){
					layout_post.setAlpha(.5f);

					isAudioRecordingON = true;
					layout_bottomlayer_postcard_detail.setVisibility(View.VISIBLE);
					layout_bottomlayer_postcard_audio.setVisibility(View.INVISIBLE);
					postcard_audio_image.setVisibility(View.INVISIBLE);
					postcard_audio_image_gif.setVisibility(View.VISIBLE);
					audio_title_label.setVisibility(View.VISIBLE);
					audio_title_label.setText("Tap to Stop Recording...");

					isRecording = true;
					//replace with wave form gif
					//postcard_audio_image_gif.setAnimatedGif(R.drawable.wave, TYPE.FIT_LAYOUT);
					start();
				}else{
					audio_title_label.setVisibility(View.INVISIBLE);
					layout_post.setAlpha(1f);

					isAudioRecordingON = false;
					//replace with play icon
					if(!isPlaying){
						setPlayRecordingNow();
					}else{
						if(myPlayer == null){
							play();
						}else{
							if(!myPlayer.isPlaying()){
								play();
							}else{
								stopPlay();
							}
						}
					}
				}
			}
		});

		postcard_audio_image_gif.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				audio_title_label.setVisibility(View.INVISIBLE);
				if(!isPlaying){
					setPlayRecordingNow();
				}else{
					if(myPlayer == null){
						play();
					}else{
						if(!myPlayer.isPlaying()){
							play();
						}else{
							stopPlay();
						}
					}
				}
			}
		});
	}

	private void placeHandlerToFinishActivity() {
		handler = new Handler();
		runnable = new Runnable() {

			@Override
			public void run() { 
				finishActivityHandler();
			}
		};
		handler.postDelayed(runnable, 1500);
	}

	private int dp2px(int dp) {

		if(SplashActivity.ScreenWidth >= 2000)
		{
			dp = 60;

		}

		else if(SplashActivity.ScreenWidth >= 1000)
		{
			dp = 60;

		}
		else if(SplashActivity.ScreenWidth >= 700)
		{
			dp = 60;

		}
		else if(SplashActivity.ScreenWidth >= 590)
		{
			dp = 80;
		}
		else
		{
			dp = 60;
		}
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

	private void start(){
		startValue = 120;
		try {
			myRecorder.prepare();
			myRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		startTimer();
	}

	private void stopTimer(){	
		if (handlerForTime != null) {
			handlerForTime.removeCallbacks(runnableForTime);
			handlerForTime = null;
		}
	}

	private void startTimer()
	{
		if (handlerForTime != null) {
			handlerForTime.removeCallbacks(runnableForTime);
			handlerForTime = null;
		}
		runnableForTime = new Runnable() 
		{

			public void run() {
				if(startValue >= 0 ) {
					playDuration = 120 - startValue;
					long diffSeconds = startValue % 60;
					min = startValue / 60  % 60;
					startValue -- ;

					String seconds = String.format("%02d", diffSeconds);
					String minutes = String.format("%02d", min);				

					audio_counter_label.setText(minutes+":"+seconds+" min (Remaining)");
					if(handlerForTime!=null)
						handlerForTime.postDelayed(runnableForTime,1000);
				}else{
					stopPlay();
					setPlayRecordingNow();
					stop();
				}				
			}
		};

		handlerForTime = new Handler();
		handlerForTime.postDelayed(runnableForTime, 1000);
	}

	@SuppressLint("NewApi")
	private void setPlayRecordingNow() {
		postcard_audio_image.setVisibility(View.VISIBLE);
		postcard_audio_image_gif.setVisibility(View.INVISIBLE);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){	
			postcard_audio_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.play_i));
		} else {
			postcard_audio_image.setBackground(getResources().getDrawable(R.drawable.play_i));
		}
		isPlaying = true;
		layout_post.setAlpha(1f);

		isAudioRecordingON = false;
		stopTimer();
		stop();
	}

	private void stopTimerForAudioPlay(){	
		if (handlerForPlaying != null) {
			handlerForPlaying.removeCallbacks(runnableForPlaying);
			handlerForPlaying = null;
		}
	}

	private void startTimerForAudioPlay()
	{
		startValue = 0;
		min = 0;
		stopTimerForAudioPlay();
		runnableForPlaying = new Runnable() {
			public void run() {
				if(startValue<playDuration){
					++startValue;

					if(startValue>=60){
						min += startValue/60;
						startValue%=60;
					}

					String seconds = String.format("%02d", startValue);
					String minutes = String.format("%02d", min);				
					audio_counter_label.setText(minutes+":"+seconds);
					if(handlerForPlaying!=null)
						handlerForPlaying.postDelayed(runnableForPlaying,1000);
				}else{

				}
			}
		};

		handlerForPlaying = new Handler();
		handlerForPlaying.postDelayed(runnableForPlaying, 1000);
	}
	private void stop(){
		try {
			myRecorder.stop();
			myRecorder.release();
			myRecorder = null;
		} catch (IllegalStateException e) {
			//  it is called before start()
			e.printStackTrace();
		} catch (RuntimeException e) {
			// no valid audio/video data has been received
			e.printStackTrace();
		}
	}

	private void play() {
		try{
			myPlayer = new MediaPlayer();
			myPlayer.setDataSource(outputFile);
			myPlayer.prepare();
			myPlayer.start();

			myPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stopTimer();
					stopTimerForAudioPlay();
				}
			});

			startTimerForAudioPlay();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopPlay() 
	{
		try {
			if (myPlayer != null) {
				myPlayer.stop();
				myPlayer.release();
				myPlayer = null;
			}
			stopTimerForAudioPlay();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopAndDelete()
	{
		try{
			File file = new File(outputFile);
			file.delete();

			stopTimer();
			stopPlay();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		finishActivity();
	}

	private void finishActivity() {
		if(!isActivityFinished)
		{
			isActivityFinished=true;
			/*stopAndDelete();
			if(myRecorder!=null){
				stop();
				myRecorder = null;
			}
			playSound(soundEffectButtonClicks);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(bitmap!=null){
				bitmap.recycle();
				bitmap = null;
			}
			if(handler != null){
				handler.removeCallbacks(runnable);
				handler = null;
				runnable = null;
			}
			if (handlerForPlaying != null) {
				handlerForPlaying.removeCallbacks(runnableForPlaying);
				handlerForPlaying = null;
			}
			System.gc();
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingAudioActivity.this, ChildPostcardActivity.class);
			startActivity(openTemplate);
			finish();*/

			showMessage.showAlertChildInterfaceDiscard(ChildPostcardDetailingAudioActivity.this, "Warning", "Finish posting before you go.", new OnEventListener<String>() {

				@Override
				public void onSuccess(String object) {
					// TODO Auto-generated method stub
					isActivityFinished=false;

				}

				@Override
				public void onFailure(String object) {
					// TODO Auto-generated method stub
					stopAndDelete();
					if(myRecorder!=null){
						stop();
						myRecorder = null;
					}
					playSound(soundEffectButtonClicks);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(bitmap!=null){
						bitmap.recycle();
						bitmap = null;
					}
					if(handler != null){
						handler.removeCallbacks(runnable);
						handler = null;
						runnable = null;
					}
					if (handlerForPlaying != null) {
						handlerForPlaying.removeCallbacks(runnableForPlaying);
						handlerForPlaying = null;
					}
					System.gc();
					disposeSound();
					Intent openTemplate = new Intent(ChildPostcardDetailingAudioActivity.this, ChildPostcardActivity.class);
					startActivity(openTemplate);
					finish();
				}
			});

		}
	}

	private void moveToDashboard() 
	{
		if(!isActivityFinished)
		{
			isActivityFinished=true;
			/*stopAndDelete();
			if(myRecorder!=null){
				stop();
				myRecorder = null;
			}
			playSound(soundEffectButtonClicks);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(bitmap!=null){
				bitmap.recycle();
				bitmap = null;
			}
			if(handler != null){
				handler.removeCallbacks(runnable);
				handler = null;
				runnable = null;
			}
			if (handlerForPlaying != null) {
				handlerForPlaying.removeCallbacks(runnableForPlaying);
				handlerForPlaying = null;
			}
			System.gc();
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingAudioActivity.this, ChildMainDashboardActivity.class);
			startActivity(openTemplate);
			finish();*/


			showMessage.showAlertChildInterfaceDiscard(ChildPostcardDetailingAudioActivity.this, "Warning", "Finish posting before you go.", new OnEventListener<String>() {

				@Override
				public void onSuccess(String object) {
					// TODO Auto-generated method stub
					isActivityFinished=false;

				}

				@Override
				public void onFailure(String object) {
					// TODO Auto-generated method stub
					stopAndDelete();
					if(myRecorder!=null){
						stop();
						myRecorder = null;
					}
					playSound(soundEffectButtonClicks);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(bitmap!=null){
						bitmap.recycle();
						bitmap = null;
					}
					if(handler != null){
						handler.removeCallbacks(runnable);
						handler = null;
						runnable = null;
					}
					if (handlerForPlaying != null) {
						handlerForPlaying.removeCallbacks(runnableForPlaying);
						handlerForPlaying = null;
					}
					System.gc();
					disposeSound();
					Intent openTemplate = new Intent(ChildPostcardDetailingAudioActivity.this, ChildMainDashboardActivity.class);
					startActivity(openTemplate);
					finish();
				}
			});

		}
	}

	public byte[] convertToBytes() throws FileNotFoundException, IOException {
		File file = new File(outputFile);

		FileInputStream fis = new FileInputStream(file);
		//System.out.println(file.exists() + "!!");
		//InputStream in = resource.openStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); //no doubt here is 0
				//Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
				System.out.println("read " + readNum + " bytes,");
			}
		} catch (IOException ex) {
		}
		byte[] bytes = bos.toByteArray();

		//below is the different part
		/*	File someFile = new File("java2.pdf");
		FileOutputStream fos = new FileOutputStream(someFile);
		fos.write(bytes);
		fos.flush();
		fos.close();*/
		return bytes;
	}

	private String bitmapTobyte(byte[] bitmapLength)
	{
		return Base64.encodeToString(bitmapLength,
				Base64.DEFAULT);	
	}

	private void finishActivityHandler() {
		if(!isActivityFinished)
		{
			isActivityFinished=true;
			stopAndDelete();
			if(myRecorder!=null){
				stop();
				myRecorder = null;
			}
			playSound(soundEffectButtonClicks);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(bitmap!=null){
				bitmap.recycle();
				bitmap = null;
			}
			if(handler != null){
				handler.removeCallbacks(runnable);
				handler = null;
				runnable = null;
			}
			if (handlerForPlaying != null) {
				handlerForPlaying.removeCallbacks(runnableForPlaying);
				handlerForPlaying = null;
			}
			System.gc();
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingAudioActivity.this, ChildPostcardActivity.class);
			startActivity(openTemplate);
			finish();



		}
	}

}
