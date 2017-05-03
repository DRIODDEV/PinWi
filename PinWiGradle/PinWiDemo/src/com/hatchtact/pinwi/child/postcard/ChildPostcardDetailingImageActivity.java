package com.hatchtact.pinwi.child.postcard;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildPostcardDetailingImageActivity  extends Activity {

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
	private TextView text_selected,text_postcard,text_post;
	private RelativeLayout child_postcard_detail_mid_image,layout_postcard_detail,layout_post;

	//image
	private ImageView postcard_detail_image;

	//sent layout
	private RelativeLayout child_postcard_sent_layout;
	private TextView postcard_sent_message;

	private Handler handler;
	private Runnable runnable;
	private Bitmap bitmapToSend;
	private String imageByte="";
	private RelativeLayout layout_alphabetical;
	private boolean isActivityFinished=false;
	private SocialConstants social;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_postcard_image);
		typeFace = new TypeFace(ChildPostcardDetailingImageActivity.this);
		sharepref = new SharePreferenceClass(ChildPostcardDetailingImageActivity.this);
		social=new SocialConstants(ChildPostcardDetailingImageActivity.this);

		setHeaderItems();
		initSoundData();
		getBundleValues();
		initialiseData();
		setOnClickListeners();

		selectPictureFromGallery();

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

	private void selectPictureFromGallery() {
		Intent intent = new Intent(
				Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 2);
	}

	private void getBundleValues() {
		selectedIndex = getIntent().getIntExtra("ARRAY_VALUE", 0);
	}

	private void initSoundData() {
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildPostcardDetailingImageActivity.this, R.raw.two_tone_nav);		
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

	private void initialiseData() {

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildPostcardDetailingImageActivity.this);
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
		layout_postcard_detail = (RelativeLayout) findViewById(R.id.layout_postcard_detail);
		layout_post = (RelativeLayout) findViewById(R.id.layout_post);

		child_selected_imageview = (ImageView) findViewById(R.id.child_selected_imageview);
		text_selected = (TextView) findViewById(R.id.text_selected);
		typeFace.setTypefaceGotham(text_selected);
		text_postcard = (TextView) findViewById(R.id.text_postcard);
		text_postcard.setText(getResources().getString(R.string.home_text));
		text_post = (TextView) findViewById(R.id.text_post);
		typeFace.setTypefaceGotham(text_postcard);
		typeFace.setTypefaceGotham(text_post);


		child_postcard_detail_mid_image = (RelativeLayout) findViewById(R.id.child_postcard_detail_mid_image);
		postcard_detail_image = (ImageView) findViewById(R.id.postcard_detail_image);

		child_postcard_sent_layout = (RelativeLayout) findViewById(R.id.child_postcard_sent_layout);
		postcard_sent_message = (TextView) findViewById(R.id.postcard_sent_message);
		typeFace.setTypefaceGotham(postcard_sent_message);
		layout_alphabetical = (RelativeLayout) findViewById(R.id.layout_postcard_detail);
		layout_alphabetical.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveToDashboard();	
			}
		});
	}

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

				if(imageByte!=null && imageByte.trim().length()>0 )
				{
					showMessage.showAlertChildInterface(ChildPostcardDetailingImageActivity.this, "Confirmation", "Woah!! All set to post this?", new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							new AddPostcardTask(ChildPostcardDetailingImageActivity.this, selectedIndex+1, StaticVariables.currentChild.getChildID(), 2, imageByte, 1, new OnEventListener<String>() {

								@Override
								public void onSuccess(String object) {
									// TODO Auto-generated method stub
									/*social.Create_Postcard_PhotoSentFacebookLog();
									social.Create_Postcard_PhotoGoogleAnalyticsLog();*/
									try {
										social.postcardAddedClevertap(ChildPostcardActivity.templateArray.get(selectedIndex), "Image");
									}
									catch (Exception e)
									{

									}
									child_postcard_sent_layout.setVisibility(View.VISIBLE);
									layout_bottomlayer_postcard_detail.setVisibility(View.INVISIBLE);
									child_postcard_detail_mid_image.setVisibility(View.INVISIBLE);
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
					/*new AddPostcardTask(ChildPostcardDetailingImageActivity.this, selectedIndex+1, StaticVariables.currentChild.getChildID(), 2, imageByte, 1, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							// TODO Auto-generated method stub
							child_postcard_sent_layout.setVisibility(View.VISIBLE);
							layout_bottomlayer_postcard_detail.setVisibility(View.INVISIBLE);
							child_postcard_detail_mid_image.setVisibility(View.INVISIBLE);
							child_header_title_label_layout.setVisibility(View.INVISIBLE);
							placeHandlerToFinishActivity();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub

						}
					}).execute();*/
				}			
			}
		});

		child_selected_imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectPictureFromGallery();
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) 
		{
			if (requestCode == 2) //Select picture from gallery Option
			{       
				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(
						selectedImage, filePath, null, null, null);
				c.moveToFirst();

				int columnIndex = c.getColumnIndex(filePath[0]);
				String picturePath = c.getString(columnIndex);
				//https://lh3.googleusercontent.com/-nP7uAve16Ek/UuM4CTJVZ4I/AAAAAAAAAD8/6aLRzGRKeWU/I/tumblr_static_infinity.jpg
				if(!picturePath.startsWith("https://")){
					if(bitmapToSend!=null){
						bitmapToSend.recycle();
						bitmapToSend = null;
					}
					bitmapToSend = new CompressImage(ChildPostcardDetailingImageActivity.this).compressImage(picturePath);
					if(bitmapToSend != null){
						postcard_detail_image.setImageBitmap(bitmapToSend);
						imageByte=bitmapTobyte(getImage(bitmapToSend));
					}
				}else{
					Toast.makeText(ChildPostcardDetailingImageActivity.this,"This image cannot be used! Please select other image.", Toast.LENGTH_SHORT).show();
					selectPictureFromGallery();
				}
			}
		}else if(resultCode == Activity.RESULT_CANCELED){
			if(bitmapToSend == null){
				finishActivity();
			}
		}
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

	@Override
	public void onBackPressed() {
		finishActivity();
	}

	private void finishActivity() {
		if(!isActivityFinished)
		{
			isActivityFinished=true;
			/*playSound(soundEffectButtonClicks);
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
			if(bitmapToSend!=null){
				bitmapToSend.recycle();
				bitmapToSend = null;
			}
			System.gc();
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingImageActivity.this, ChildPostcardActivity.class);
			startActivity(openTemplate);
			finish();*/
			if(imageByte.toString().trim().length()>0)
			{
				showMessage.showAlertChildInterfaceDiscard(ChildPostcardDetailingImageActivity.this, "Warning", "Finish posting before you go.", new OnEventListener<String>() {

					@Override
					public void onSuccess(String object) {
						// TODO Auto-generated method stub
						isActivityFinished=false;

					}

					@Override
					public void onFailure(String object) {
						// TODO Auto-generated method stub
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
						if(bitmapToSend!=null){
							bitmapToSend.recycle();
							bitmapToSend = null;
						}
						System.gc();
						disposeSound();
						Intent openTemplate = new Intent(ChildPostcardDetailingImageActivity.this, ChildPostcardActivity.class);
						startActivity(openTemplate);
						finish();
					}
				});
			}
			else
			{

				// TODO Auto-generated method stub
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
				if(bitmapToSend!=null){
					bitmapToSend.recycle();
					bitmapToSend = null;
				}
				System.gc();
				disposeSound();
				Intent openTemplate = new Intent(ChildPostcardDetailingImageActivity.this, ChildPostcardActivity.class);
				startActivity(openTemplate);
				finish();

			}
		}
	}


	private void moveToDashboard() {
		if(!isActivityFinished)
		{
			isActivityFinished=true;
			/*playSound(soundEffectButtonClicks);
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
			if(bitmapToSend!=null){
				bitmapToSend.recycle();
				bitmapToSend = null;
			}
			System.gc();
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingImageActivity.this, ChildMainDashboardActivity.class);
			startActivity(openTemplate);
			finish();*/
			if(imageByte.toString().trim().length()>0)
			{
				showMessage.showAlertChildInterfaceDiscard(ChildPostcardDetailingImageActivity.this, "Warning", "Finish posting before you go.", new OnEventListener<String>() {

					@Override
					public void onSuccess(String object) {
						// TODO Auto-generated method stub
						isActivityFinished=false;

					}

					@Override
					public void onFailure(String object) {
						// TODO Auto-generated method stub
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
						if(bitmapToSend!=null){
							bitmapToSend.recycle();
							bitmapToSend = null;
						}
						System.gc();
						disposeSound();
						Intent openTemplate = new Intent(ChildPostcardDetailingImageActivity.this, ChildMainDashboardActivity.class);
						startActivity(openTemplate);
						finish();
					}
				});
			}
			else
			{

				// TODO Auto-generated method stub
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
				if(bitmapToSend!=null){
					bitmapToSend.recycle();
					bitmapToSend = null;
				}
				System.gc();
				disposeSound();
				Intent openTemplate = new Intent(ChildPostcardDetailingImageActivity.this, ChildMainDashboardActivity.class);
				startActivity(openTemplate);
				finish();

			}
		}
	}

	private byte[] getImage(Bitmap bmp) 
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
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
			if(bitmapToSend!=null){
				bitmapToSend.recycle();
				bitmapToSend = null;
			}
			System.gc();
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingImageActivity.this, ChildPostcardActivity.class);
			startActivity(openTemplate);
			finish();
		
			}
		}
	



}
