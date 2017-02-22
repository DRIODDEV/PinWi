package com.hatchtact.pinwi.child.postcard;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.child.ChildMainDashboardActivity;
import com.hatchtact.pinwi.child.ChildMusicPlayer;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.child.SoundEffect;
import com.hatchtact.pinwi.child.postcard.ChildPostcardTemplateAdapter.SwipeCallback;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetListOfMessageTempletesList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildPostcardActivity extends FragmentActivity {

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
	private TextView text_postcard_label;
	private ImageView child_postcard_leftarrow,child_postcard_rightarrow;
	private TemplateViewPager postcard_templates;
	private ChildPostcardTemplateAdapter childPostcardTemplateAdapter;
	public static ArrayList<String> templateArray;
	public static ArrayList<TemplateColorModel> colorArray;

	private String[] outerColor = {"#8850be","#f9a94a","#fdc642","#83bd45","#bdcd60","#f58d89","#7cb1e4","#b54fa5"};
	private String[] innerColor = {"#6a24ae","#f7941d","#fdb813","#64ad17","#acc138","#f2716c","#5b9ddd","#a3238e"};

	private int previousColorIndex = 0;
	private Random random;
	private GetListOfMessageTempletesList getListOfMessageTempletesList;
	private RelativeLayout layout_alphabetical;
	private CustomLoader customProgressLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_postcard);
		customProgressLoader=new CustomLoader(this);
		typeFace = new TypeFace(ChildPostcardActivity.this);
		sharepref = new SharePreferenceClass(ChildPostcardActivity.this);
		hideKeyBoard();
		setHeaderItems();
		initSoundData();
		initialiseData();

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
		getListOfMessageTempletesList=new GetListOfMessageTempletesList();
		getListOfMessageTempletesList.getListOfMessageTempletesList().clear();
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		new GetListOfMessageTempletesListAsync().execute();
	}

	private void initSoundData() {
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildPostcardActivity.this, R.raw.two_tone_nav);
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
			/*if(StaticVariables.currentChild.getProfileImage()!=null && StaticVariables.currentChild.getProfileImage().trim().length()>100)
			{	
				byte[] imageByteParent=Base64.decode(StaticVariables.currentChild.getProfileImage(), 0);
				if(imageByteParent!=null)
				{
					bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
					child_header_image.setImageBitmap(bitmap);
				}
				//1)color array
				//2)webservices
				//3)convert to byte array
				//4)text size in 7inch tab
			}*/
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
		showMessage=new ShowMessages(ChildPostcardActivity.this);
		serviceMethod=new ServiceMethod();
		text_postcard_label=(TextView) findViewById(R.id.text_postcard_label);
		text_postcard_label.setText(getResources().getString(R.string.home_text));
		text_postcard_label.setAlpha(0.7f);
		typeFace.setTypefaceGotham(text_postcard_label);
		child_postcard_leftarrow = (ImageView) findViewById(R.id.child_postcard_leftarrow);
		child_postcard_rightarrow = (ImageView) findViewById(R.id.child_postcard_rightarrow);
		layout_alphabetical = (RelativeLayout) findViewById(R.id.layout_alphabetical);
		layout_alphabetical.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});

		child_postcard_leftarrow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(postcard_templates.getCurrentItem()>0){
					postcard_templates.setCurrentItem(postcard_templates.getCurrentItem()-1,true);
				}else{
					postcard_templates.setCurrentItem(ChildPostcardTemplateAdapter.NUM_PAGES-1,true);
				}
			}
		});

		child_postcard_rightarrow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (postcard_templates.getCurrentItem() == ChildPostcardTemplateAdapter.NUM_PAGES-1) {
					postcard_templates.setCurrentItem(0,true);
				}else if(postcard_templates.getCurrentItem() < (ChildPostcardTemplateAdapter.NUM_PAGES)){
					postcard_templates.setCurrentItem(postcard_templates.getCurrentItem()+1,true);
				}
			}
		});

		postcard_templates = (TemplateViewPager) findViewById(R.id.postcard_templates);
		postcard_templates.setOnPageChangeListener(onPageChangeListener);

		//fillData();

	}

	private int previousState, currentState;

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int pageSelected) {
		}

		@Override
		public void onPageScrolled(int pageSelected, float positionOffset,
								   int positionOffsetPixel) {
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			int currentPage = postcard_templates.getCurrentItem();
			if (currentPage == (ChildPostcardTemplateAdapter.NUM_PAGES-1) || currentPage == 0) {
				previousState = currentState;
				currentState = state;
				if (previousState == 1 && currentState == 0) {
					postcard_templates.setCurrentItem(currentPage == 0 ?  (ChildPostcardTemplateAdapter.NUM_PAGES-1) : 0,true);
				}
			}
		}
	};


	private void colorIndexSelected(){
		int randomNumber = random.nextInt(outerColor.length - 1);
		if(randomNumber == previousColorIndex){
			colorIndexSelected();
		}else{
			previousColorIndex = randomNumber;
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
		finishActivity();
	}
	private boolean isActivityFinished=false;


	private void finishActivity() {
		if(!isActivityFinished)
		{
			isActivityFinished=true;
			playSound(soundEffectButtonClicks);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			disposeSound();
			Intent intent = new Intent(ChildPostcardActivity.this, ChildMainDashboardActivity.class);
			startActivity(intent);
			// overridePendingTransition(R.anim.activity_close_translate,R.anim.activity_close_scale);
			finish();
		}
	}

	//private ProgressDialog progressDialog=null;

	private class GetListOfMessageTempletesListAsync extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			/*progressDialog = ProgressDialog.show(ChildPostcardActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildPostcardActivity.this))
			{
				try {
					getListOfMessageTempletesList = serviceMethod.getListOfMessageTempletesList();
				} catch (Exception e) {
					e.printStackTrace();
					ErrorCode=-1;
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
			super.onPostExecute(result);
			try {
				customProgressLoader.removeCallbacksHandler();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/

				if(result==-1)
				{
					showMessage.showToastMessage("Please check your network connection");

					/*if(checkNetwork.checkNetworkConnection(ChildPostcardActivity.this))
						new GetListOfMessageTempletesListAsync().execute();*/
				}
				else
				{
					if(getListOfMessageTempletesList!=null && getListOfMessageTempletesList.getListOfMessageTempletesList().size()>0)
					{
						fillData();
					}
					else
					{
						//getError();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void getError(){
			Error err = serviceMethod.getError();
			showMessage.showAlert("Warning", err.getErrorDesc());
		}
	}

	private void fillData()
	{
		if(templateArray == null){
			templateArray = new ArrayList<String>();
		}else{
			templateArray.clear();
		}

		if(colorArray == null){
			colorArray = new ArrayList<TemplateColorModel>();
		}else{
			colorArray.clear();
		}


		for(int i=0;i<getListOfMessageTempletesList.getListOfMessageTempletesList().size();i++){
			templateArray.add(getListOfMessageTempletesList.getListOfMessageTempletesList().get(i).getMessage());
		}

		random = new Random();
		previousColorIndex = 0;

		for(int i=0;i<templateArray.size();i++){
			colorIndexSelected();
			TemplateColorModel templateColorModel = new TemplateColorModel();
			templateColorModel.setInnerColor(outerColor[previousColorIndex]);
			templateColorModel.setOuterColor(innerColor[previousColorIndex]);
			colorArray.add(templateColorModel);
		}

		//Collections.shuffle(colorArray);

		childPostcardTemplateAdapter = new ChildPostcardTemplateAdapter(getSupportFragmentManager(),new SwipeCallback() {
			@Override
			public void isSwipeEnable(boolean isSwipeEnable) {
				postcard_templates.setPagingEnabled(isSwipeEnable);
				if(isSwipeEnable){
					child_postcard_leftarrow.setVisibility(View.VISIBLE);
					child_postcard_rightarrow.setVisibility(View.VISIBLE);
				}else{
					child_postcard_leftarrow.setVisibility(View.INVISIBLE);
					child_postcard_rightarrow.setVisibility(View.INVISIBLE);
				}
			}
		});
		ChildPostcardTemplateAdapter.NUM_PAGES = templateArray.size();
		postcard_templates.setAdapter(childPostcardTemplateAdapter);

		if(StaticVariables.selectedPostcardIndex <= (ChildPostcardTemplateAdapter.NUM_PAGES - 1)){
			postcard_templates.setCurrentItem(StaticVariables.selectedPostcardIndex, true);
		}
	}

	private void hideKeyBoard() {



		try {
			ChildPostcardActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager) ChildPostcardActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow( ChildPostcardActivity.this
					.getCurrentFocus().getWindowToken(), 0);
			ChildPostcardActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}

	}

}
