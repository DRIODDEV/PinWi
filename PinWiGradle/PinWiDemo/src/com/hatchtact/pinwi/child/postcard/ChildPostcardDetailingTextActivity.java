package com.hatchtact.pinwi.child.postcard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
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

public class ChildPostcardDetailingTextActivity  extends Activity {

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
	private RelativeLayout child_postcard_detail_mid_text,layout_postcard_detail,layout_post;

	//text
	private TextView postcard_detail_word_count;
	private TextView postcard_detail_message;
	private RelativeLayout layout_bottomlayer_postcard_typing;
	private ImageView msgDoneBtn;
	private EditText msgEditText;

	//sent layout
	private RelativeLayout child_postcard_sent_layout;
	private TextView postcard_sent_message;

	private boolean isWritingDone;
	private boolean isEditingEnabled;
	private boolean showCountLabel;;

	private Handler handler;
	private Runnable runnable;
	private RelativeLayout layout_alphabetical;
	private boolean isActivityFinished=false;
	private String textData="";
	private SocialConstants social;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_postcard_text);
		typeFace = new TypeFace(ChildPostcardDetailingTextActivity.this);
		sharepref = new SharePreferenceClass(ChildPostcardDetailingTextActivity.this);
		social=new SocialConstants(ChildPostcardDetailingTextActivity.this);
		msgEditText = (EditText) findViewById(R.id.msgEditText);
		//showKeyBoard();
		msgEditText.requestFocus();

		setEditTextMaxLength(msgEditText, 500);

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
		//overridePendingTransition(R.anim.activity_open_scale,R.anim.fade_in);

	}

	private void getBundleValues() {
		selectedIndex = getIntent().getIntExtra("ARRAY_VALUE", 0);
	}

	private void initSoundData() {
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildPostcardDetailingTextActivity.this, R.raw.two_tone_nav);
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

		showCountLabel = true;
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildPostcardDetailingTextActivity.this);
		serviceMethod=new ServiceMethod();

		child_header_title_label_layout = (LinearLayout) findViewById(R.id.child_header_title_label_layout);
		child_header_title_label_layout.setVisibility(View.VISIBLE);
		child_header_title_label_layout.setBackgroundColor(Color.parseColor(ChildPostcardActivity.colorArray.get(selectedIndex).getInnerColor()));
		child_header_title_label_layout.setAlpha(1.0f);


		child_header_title_label = (TextView) findViewById(R.id.child_header_title_label);
		typeFace.setTypefaceGothamMedium(child_header_title_label);
		child_header_title_label.setText(ChildPostcardActivity.templateArray.get(selectedIndex));

		//common
		layout_postcard_detail = (RelativeLayout) findViewById(R.id.layout_postcard_detail);
		layout_post = (RelativeLayout) findViewById(R.id.layout_post);

		layout_bottomlayer_postcard_detail = (LinearLayout) findViewById(R.id.layout_bottomlayer_postcard_detail);
		child_selected_imageview = (ImageView) findViewById(R.id.child_selected_imageview);
		text_selected = (TextView) findViewById(R.id.text_selected);
		typeFace.setTypefaceGotham(text_selected);
		text_postcard= (TextView) findViewById(R.id.text_postcard);
		text_post= (TextView) findViewById(R.id.text_post);
		text_postcard.setText(getResources().getString(R.string.home_text));
		typeFace.setTypefaceGotham(text_postcard);
		typeFace.setTypefaceGotham(text_post);


		child_postcard_detail_mid_text = (RelativeLayout) findViewById(R.id.child_postcard_detail_mid_text);

		child_postcard_sent_layout = (RelativeLayout) findViewById(R.id.child_postcard_sent_layout);
		postcard_sent_message = (TextView) findViewById(R.id.postcard_sent_message);
		postcard_sent_message.setText("Your post has been sent to your Playwall.");
		typeFace.setTypefaceGotham(postcard_sent_message);
		//init text variables
		child_postcard_detail_mid_text.setBackgroundColor(getResources().getColor(R.color.notepad_main));
		layout_alphabetical = (RelativeLayout) findViewById(R.id.layout_postcard_detail);
		layout_alphabetical.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveToDashboard();
			}
		});
		initTextLayoutVariable();
	}

	private void initTextLayoutVariable() {
		isWritingDone = false;
		isEditingEnabled = false;

		//text
		postcard_detail_word_count = (TextView) findViewById(R.id.postcard_detail_word_count);
		typeFace.setTypefaceGothamMedium(postcard_detail_word_count);
		postcard_detail_word_count.setAlpha(0.6f);

		postcard_detail_message = (TextView) findViewById(R.id.postcard_detail_message);
		typeFace.setTypefaceBold(postcard_detail_message);

		layout_bottomlayer_postcard_typing = (RelativeLayout) findViewById(R.id.layout_bottomlayer_postcard_typing);
		msgDoneBtn = (ImageView) findViewById(R.id.msgDoneBtn);

		typeFace.setTypefaceGotham(msgEditText);
		msgEditText.setAlpha(0.9f);

	}

	@SuppressLint("NewApi")
	private void setOnClickListeners(){
		child_header_title_label_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});

		msgEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int start,
									  int before, int count) {
				try{
					if(msgEditText.getVisibility() == View.VISIBLE
							&& cs.toString().length() <= 500){
					}else{
						msgEditText.setText(cs.toString().substring(0, 500));
						msgEditText.setSelection(500);
					}
				}catch(Exception e){

				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String str = s.toString();
				textData=str;
				if(str.length() >= 500){
					setTickIcon(2);
				}else if(str.length()>0){
					setTickIcon(1);
				}else{
					setTickIcon(0);
				}

			}
		});

		msgEditText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_NEXT)
				{
					return true;
				} else if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					return false;
				} else
				{
					return false;
				}

			}
		});

		msgDoneBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isWritingDone){
					hideWritingLayout();
				}
				showCountLabel = false;
			}
		});

		child_selected_imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isEditingEnabled = !isEditingEnabled;
				if(isEditingEnabled){
					child_postcard_detail_mid_text.setBackgroundColor(getResources().getColor(R.color.notepad_edit));
					text_selected.setText("DONE");
					//postcard_detail_message.setEnabled(false);
					layout_postcard_detail.setAlpha(0.3f);
					layout_post.setAlpha(0.3f);
					layout_postcard_detail.setEnabled(false);
					layout_post.setEnabled(false);
					child_selected_imageview.setImageDrawable(getResources().getDrawable(R.drawable.done_actv_i));
					layout_bottomlayer_postcard_typing.setVisibility(View.VISIBLE);
					layout_bottomlayer_postcard_detail.setVisibility(View.INVISIBLE);
					showKeyBoard();
					msgEditText.requestFocus();
					msgEditText.requestFocus();
					msgEditText.requestFocus();
					msgEditText.requestFocus();
				}else{
					child_postcard_detail_mid_text.setBackgroundColor(getResources().getColor(R.color.notepad_main));
					//postcard_detail_message.setEnabled(false);
					layout_postcard_detail.setAlpha(1f);
					layout_post.setAlpha(1f);
					layout_postcard_detail.setEnabled(true);
					layout_post.setEnabled(true);

					text_selected.setText("EDIT");
					child_selected_imageview.setImageDrawable(getResources().getDrawable(R.drawable.edit_i));
					hideWritingLayout();
				}
			}
		});

		layout_post.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(!isEditingEnabled){
					if(postcard_detail_message.getText().toString().trim().length()>0)
					{
						showMessage.showAlertChildInterface(ChildPostcardDetailingTextActivity.this, "Confirmation", "Woah!! All set to post this?", new OnEventListener<String>() {

							@Override
							public void onSuccess(String object) {
								// TODO Auto-generated method stub
								new AddPostcardTask(ChildPostcardDetailingTextActivity.this, selectedIndex+1, StaticVariables.currentChild.getChildID(), 1, postcard_detail_message.getText().toString().trim(), 1, new OnEventListener<String>() {

									@Override
									public void onSuccess(String object) {
										// TODO Auto-generated method stub
										//if(!isEditingEnabled){
										/*social.Create_Postcard_TextSentFacebookLog();
										social.Create_Postcard_TextGoogleAnalyticsLog();*/
										try {
											social.postcardAddedClevertap(ChildPostcardActivity.templateArray.get(selectedIndex), "Text");
										}
										catch (Exception e)
										{

										}
										child_postcard_sent_layout.setVisibility(View.VISIBLE);
										layout_bottomlayer_postcard_detail.setVisibility(View.INVISIBLE);
										child_postcard_detail_mid_text.setVisibility(View.INVISIBLE);
										child_header_title_label_layout.setVisibility(View.INVISIBLE);
										placeHandlerToFinishActivity();
										//}
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
						/*new AddPostcardTask(ChildPostcardDetailingTextActivity.this, selectedIndex+1, StaticVariables.currentChild.getChildID(), 1, postcard_detail_message.getText().toString().trim(), 1, new OnEventListener<String>() {

							@Override
							public void onSuccess(String object) {
								// TODO Auto-generated method stub
								//if(!isEditingEnabled){
								child_postcard_sent_layout.setVisibility(View.VISIBLE);
								layout_bottomlayer_postcard_detail.setVisibility(View.INVISIBLE);
								child_postcard_detail_mid_text.setVisibility(View.INVISIBLE);
								child_header_title_label_layout.setVisibility(View.INVISIBLE);
								placeHandlerToFinishActivity();
								//}
							}

							@Override
							public void onFailure(String object) {
								// TODO Auto-generated method stub

							}
						}).execute();*/
					}
					else
					{
						Toast.makeText(ChildPostcardDetailingTextActivity.this, "Post cannot be left empty", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

	}

	@SuppressLint("NewApi")
	private void setTickIcon(int setIndex) {
		if(setIndex == 0){
			isWritingDone = false;
			if(showCountLabel){
				postcard_detail_word_count.setText("Word Limit - 500");
				postcard_detail_word_count.setTextColor(Color.rgb(0, 0, 0));
				postcard_detail_word_count.setAlpha(0.6f);
				postcard_detail_word_count.setVisibility(View.VISIBLE);
			}else{
				postcard_detail_word_count.setVisibility(View.INVISIBLE);
			}
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				msgDoneBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.done_gray_i));
			} else {
				msgDoneBtn.setBackground(getResources().getDrawable(R.drawable.done_gray_i));
			}
		}else if(setIndex == 1){
			isWritingDone = true;
			postcard_detail_word_count.setVisibility(View.INVISIBLE);
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				msgDoneBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.done_text_i));
			} else {
				msgDoneBtn.setBackground(getResources().getDrawable(R.drawable.done_text_i));
			}
		}else{

			isWritingDone = false;
			if(showCountLabel){
				postcard_detail_word_count.setTextColor(Color.rgb(218, 33, 39));
				postcard_detail_word_count.setAlpha(1f);
				postcard_detail_word_count.setText("Word Limit Over");
				postcard_detail_word_count.setVisibility(View.VISIBLE);
			}else{
				postcard_detail_word_count.setVisibility(View.INVISIBLE);
			}
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				msgDoneBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.cancel_i));
			} else {
				msgDoneBtn.setBackground(getResources().getDrawable(R.drawable.cancel_i));
			}
		}
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

	private void hideKeyBoard()
	{
		try
		{
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		}
		catch (Exception e)
		{

		}
	}

	private void showKeyBoard()
	{
		try
		{
			/*getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.showSoftInput(msgEditText, 0);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);*/
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
		}
		catch (Exception e)
		{

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
			//isActivityFinished=true;
			isActivityFinished=true;

			// TODO Auto-generated method stub
			/*	playSound(soundEffectButtonClicks);
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
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingTextActivity.this, ChildPostcardActivity.class);
			startActivity(openTemplate);
			finish();*/

			if(textData.trim().length()>0)
			{
				showMessage.showAlertChildInterfaceDiscard(ChildPostcardDetailingTextActivity.this, "Warning", "Finish posting before you go.", new OnEventListener<String>() {

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
						disposeSound();
						Intent openTemplate = new Intent(ChildPostcardDetailingTextActivity.this, ChildPostcardActivity.class);
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
				disposeSound();
				Intent openTemplate = new Intent(ChildPostcardDetailingTextActivity.this, ChildPostcardActivity.class);
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
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingTextActivity.this, ChildMainDashboardActivity.class);
			startActivity(openTemplate);
			finish();*/
			if(textData.trim().length()>0)
			{
				showMessage.showAlertChildInterfaceDiscard(ChildPostcardDetailingTextActivity.this, "Warning", "Finish posting before you go.", new OnEventListener<String>() {

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
						disposeSound();
						Intent openTemplate = new Intent(ChildPostcardDetailingTextActivity.this, ChildMainDashboardActivity.class);
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
				disposeSound();
				Intent openTemplate = new Intent(ChildPostcardDetailingTextActivity.this, ChildMainDashboardActivity.class);
				startActivity(openTemplate);
				finish();

			}
		}
	}

	InputFilter restrictValue = new InputFilter() {
		@Override
		public CharSequence filter(CharSequence arg0, int arg1, int arg2,
								   Spanned arg3, int arg4, int arg5) {
			if (arg3.toString().length() <=500)
				return null;
			else
				return "";
		}
	};



	public void setEditTextMaxLength(EditText editText, int length) {
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(length);
		editText.setFilters(FilterArray);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isActivityFinished=false;
	}

	/**
	 *
	 */
	private void hideWritingLayout() {
		hideKeyBoard();
		layout_bottomlayer_postcard_typing.setVisibility(View.INVISIBLE);
		layout_bottomlayer_postcard_detail.setVisibility(View.VISIBLE);
		postcard_detail_message.setVisibility(View.VISIBLE);
		postcard_detail_word_count.setVisibility(View.INVISIBLE);
		postcard_detail_message.setText(msgEditText.getText().toString());
		//postcard_detail_message.setEnabled(false);
	}


	private void finishActivityHandler() {
		if(!isActivityFinished)
		{
			//isActivityFinished=true;
			isActivityFinished=true;

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
			disposeSound();
			Intent openTemplate = new Intent(ChildPostcardDetailingTextActivity.this, ChildPostcardActivity.class);
			startActivity(openTemplate);
			finish();



		}
	}

}


