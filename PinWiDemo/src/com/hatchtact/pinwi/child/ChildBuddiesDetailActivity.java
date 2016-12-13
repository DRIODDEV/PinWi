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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetChildDetailsOnBuddiesByChildIDOnCI;
import com.hatchtact.pinwi.classmodel.GetChildDetailsOnBuddiesByChildIDOnCIList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildBuddiesDetailActivity extends Activity
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
	private LinearLayout layout_buddies;
	private TextView text_nameChild,doblabel,dobdata,schoolLabel,schooldata,parentLabel,parentdata,siblingslabel,siblingsdata,citylabel,citydata,buddieslabel,buddiesdata;
	private Animation shake;
	private HexagonImageView  child_buddy_image;
	private GetChildDetailsOnBuddiesByChildIDOnCIList getChildDetails;
	private View child_buddies_detail_item;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext;
	private ImageView imgArrow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_buddies_detail);
		isButtonClicked=false;
		typeFace = new TypeFace(ChildBuddiesDetailActivity.this);
		sharepref = new SharePreferenceClass(ChildBuddiesDetailActivity.this);

	
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



		new GetChildDetailsByChildIDOnCITask(Integer.parseInt(StaticVariables.childIdBuddiesDetail)).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildBuddiesDetailActivity.this, R.raw.two_tone_nav);		
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
			if(StaticVariables.currentChild.getProfileImage()!=null && StaticVariables.currentChild.getProfileImage().trim().length()>100)
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
		showMessage=new ShowMessages(ChildBuddiesDetailActivity.this);
		serviceMethod=new ServiceMethod();
		layout_buddies=(LinearLayout) findViewById(R.id.layout_buddies);
		text_nameChild=(TextView) findViewById(R.id.text_nameChild);
		child_buddies_detail_item=(View) findViewById(R.id.child_buddies_detail_item);
		doblabel=(TextView) findViewById(R.id.doblabel);
		dobdata=(TextView) findViewById(R.id.dobdata);
		schoolLabel=(TextView) findViewById(R.id.schoolLabel);
		schooldata=(TextView) findViewById(R.id.schooldata);
		parentLabel=(TextView) findViewById(R.id.parentLabel);
		parentdata=(TextView) findViewById(R.id.parentdata);
		siblingslabel=(TextView) findViewById(R.id.siblingslabel);
		siblingsdata=(TextView) findViewById(R.id.siblingsdata);
		citylabel=(TextView) findViewById(R.id.citylabel);
		citydata=(TextView) findViewById(R.id.citydata);
		buddieslabel=(TextView) findViewById(R.id.buddieslabel);
		buddiesdata=(TextView) findViewById(R.id.buddiesdata);
		child_buddy_image=(HexagonImageView) findViewById(R.id.child_buddy_image);
		child_buddy_image.setVisibility(View.GONE);
		text_nameChild.setVisibility(View.GONE);
		child_buddies_detail_item.setVisibility(View.GONE);
		layout_nodata=(LinearLayout)findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView)findViewById(R.id.noconnectionimage);
		noconnectionimage.setImageResource(R.drawable.not_found);
		imgArrow=(ImageView)findViewById(R.id.imgArrow);
		noconnectiontext=(TextView) findViewById(R.id.noconnectiontext);
		shake = AnimationUtils.loadAnimation(this, R.anim.grow_from_top);
		typeFace.setTypefaceGothamBold(text_nameChild);
		typeFace.setTypefaceGothamBold(dobdata);
		typeFace.setTypefaceGotham(doblabel);
		doblabel.setAlpha(.7f);
		schoolLabel.setAlpha(.7f);
		parentLabel.setAlpha(.7f);
		siblingslabel.setAlpha(.7f);
		citylabel.setAlpha(.7f);
		buddieslabel.setAlpha(.7f);
		imgArrow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*	GetChildDetailsOnBuddiesByChildIDOnCI model= getChildDetails.getChildDetailsOnBuddiesByChildIDOnCI().get(0);
				StaticVariables.childIdBuddiesDetail=String.valueOf(model.getChildID());
				StaticVariables.childWishlistName=model.getChidName();
				//pass intent to child detail
				startActivity(new Intent(ChildBuddiesDetailActivity.this,ChildBuddiesListActivity.class));
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();*/
			}
		});
		typeFace.setTypefaceGotham(schoolLabel);
		typeFace.setTypefaceGothamBold(schooldata);
		typeFace.setTypefaceGotham(parentLabel);
		typeFace.setTypefaceGothamBold(parentdata);
		typeFace.setTypefaceGotham(noconnectiontext);

		typeFace.setTypefaceGotham(siblingslabel);
		typeFace.setTypefaceGothamBold(siblingsdata);
		typeFace.setTypefaceGotham(citylabel);
		typeFace.setTypefaceGothamBold(citydata);
		typeFace.setTypefaceGotham(buddieslabel);
		typeFace.setTypefaceGothamBold(buddiesdata);



	}


	private void setClickListeners()
	{
		// TODO Auto-generated method stub

		layout_buddies.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub'
				GetChildDetailsOnBuddiesByChildIDOnCI model= getChildDetails.getChildDetailsOnBuddiesByChildIDOnCI().get(0);
				StaticVariables.childIdBuddiesDetail=String.valueOf(model.getChildID());
				StaticVariables.childWishlistName=model.getChidName();
				//pass intent to child detail
				startActivity(new Intent(ChildBuddiesDetailActivity.this,ChildBuddiesListActivity.class));
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();

			}
		});

	}

	private ProgressDialog progressDialog=null;

	private class GetChildDetailsByChildIDOnCITask extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public GetChildDetailsByChildIDOnCITask(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(ChildBuddiesDetailActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildBuddiesDetailActivity.this))
			{
				getChildDetails = serviceMethod.getChildDetailsBuddies(childID);
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

					if(checkNetwork.checkNetworkConnection(ChildBuddiesDetailActivity.this))
						new GetChildDetailsByChildIDOnCITask(childID).execute();
				}
				else
				{
					if(getChildDetails!=null && getChildDetails.getChildDetailsOnBuddiesByChildIDOnCI()!=null)
					{
						layout_nodata.setVisibility(View.GONE);
						noconnectionimage.setVisibility(View.GONE);
						noconnectiontext.setVisibility(View.GONE);
						child_buddy_image.setVisibility(View.VISIBLE);
						text_nameChild.setVisibility(View.VISIBLE);
						child_buddies_detail_item.startAnimation(shake);

						child_buddies_detail_item.setVisibility(View.VISIBLE);
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

			GetChildDetailsOnBuddiesByChildIDOnCI model=getChildDetails.getChildDetailsOnBuddiesByChildIDOnCI().get(0);

			try
			{
				if(model.getProfileImage()!=null && model.getProfileImage().trim().length()>100)
				{	
					byte[] imageByteParent=Base64.decode(model.getProfileImage(), 0);
					if(imageByteParent!=null)
					{
						bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
						child_buddy_image.setImageBitmap(bitmap);
					}
				}
				else
				{
					bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.child_image), dp2px(80), dp2px(80), false);
					child_buddy_image.setImageBitmap(bitmap);
				}
			}
			catch(OutOfMemoryError e)
			{

			}
			catch (Exception e) {
				// TODO: handle exception
			}

			
			text_nameChild.setText(model.getChidName());
			dobdata.setText(model.getDateOfBirth());
			schooldata.setText(model.getSchoolName());
			parentdata.setText(model.getParentName());
			siblingsdata.setText(model.getSiblings());
			citydata.setText(model.getCityName());
			buddiesdata.setText(model.getTotalFriend());

		}


		private void getError()
		{
			layout_nodata.setVisibility(View.VISIBLE);
			com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();	
			//showMessage.showAlert("Warning", err.getErrorDesc());
			noconnectionimage.setVisibility(View.VISIBLE);
			noconnectionimage.setImageResource(R.drawable.not_found);
			noconnectiontext.setVisibility(View.VISIBLE);
			noconnectiontext.setText(err.getErrorDesc());
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
	private boolean isActivityFinished=false;

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
		Intent intent = new Intent(ChildBuddiesDetailActivity.this, ChildBuddiesActivity.class);

		startActivity(intent);
		ChildBuddiesDetailActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


		finish();
		}
	}
}
