package com.hatchtact.pinwi.child.playwall;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.adapter.ChildPlaywallListAdapter;
import com.hatchtact.pinwi.child.ChildMainDashboardActivity;
import com.hatchtact.pinwi.child.ChildMusicPlayer;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.child.SoundEffect;
import com.hatchtact.pinwi.classmodel.GetFriendsTempleteMessageListByChildID;
import com.hatchtact.pinwi.classmodel.GetFriendsTempleteMessageListByChildIDList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildPlayWallActivity extends Activity
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
	private ImageView child_header_move_to_access_profile;
	private ChildMusicPlayer childMusicPlayer = null;
	private ImageView child_header_voice_over;
	private boolean isButtonClicked=false;
	private boolean isMusicStop = false;
	private boolean isMute = false;
	private RelativeLayout layout_playwall;
	private TextView txtViewMyWall;
	private ListView playwall_list;
	private Animation shake;
	private ChildPlaywallListAdapter adapter;
	private GetFriendsTempleteMessageListByChildIDList playwallList;
	private ProgressBar data_load_progress;
	private boolean flag_loading=false;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext,text_playwall;
	private ImageView child_playwall_imageview;
	/** To Show options on filter button. */
	public View popupViewAction = null;
	public PopupWindow popupWindowAction = null;
	private int getType=1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_playwall);
		isButtonClicked=false;
		typeFace = new TypeFace(ChildPlayWallActivity.this);
		sharepref = new SharePreferenceClass(ChildPlayWallActivity.this);
		setHeaderItems();
		initSoundData();
		initializePopUpAction();
		initialiseData();
		setClickListeners();
		getType=1;
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

		playwallList=new GetFriendsTempleteMessageListByChildIDList();
		playwallList.getFriendsTempleteMessageListByChildID().clear();


		new GetFriendsTempleteMessageListByChildIDAsync(StaticVariables.currentChild.getChildID(),1).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildPlayWallActivity.this, R.raw.two_tone_nav);		
	}

	private void playSound(SoundEffect sound)
	{
		if(!isMute && sound!=null)
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
		showMessage=new ShowMessages(ChildPlayWallActivity.this);
		serviceMethod=new ServiceMethod();
		serviceMethod.initRandom();
		layout_playwall=(RelativeLayout) findViewById(R.id.layout_playwall);
		txtViewMyWall=(TextView) findViewById(R.id.txtViewplaywall);
		data_load_progress=(ProgressBar) findViewById(R.id.data_load_progress);
		typeFace.setTypefaceGotham(txtViewMyWall);
		child_playwall_imageview=(ImageView) findViewById(R.id.child_playwall_imageview);

		layout_nodata=(LinearLayout)findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView)findViewById(R.id.noconnectionimage);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext=(TextView) findViewById(R.id.noconnectiontext);
		typeFace.setTypefaceGotham(noconnectiontext);
		text_playwall=(TextView) findViewById(R.id.text_playwall);
		text_playwall.setText(getResources().getString(R.string.home_text));
		typeFace.setTypefaceGotham(text_playwall);



		shake = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom);
		txtViewMyWall.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				playwall_list.setAdapter(null);
				if(playwallList!=null&& playwallList.getFriendsTempleteMessageListByChildID()!=null)
					playwallList.getFriendsTempleteMessageListByChildID().clear();
				else
				{
					playwallList=new GetFriendsTempleteMessageListByChildIDList();
					playwallList.getFriendsTempleteMessageListByChildID().clear();
				}
				if(getType==1)
				{
					getType=2;
					txtViewMyWall.setTextColor(getResources().getColor(R.color.font_white_coloralphanew));
					txtViewMyWall.setBackgroundResource(R.drawable.rounded_background_playwall);
				}
				else
				{
					getType=1;
					txtViewMyWall.setTextColor(getResources().getColor(R.color.buddies_normal_bg));

					txtViewMyWall.setBackgroundResource(R.drawable.rounded_inactive_buddies);
				}
				new GetFriendsTempleteMessageListByChildIDAsync(StaticVariables.currentChild.getChildID(),1).execute();
			
				
			}
		});
		//layoutpoint.startAnimation(shake);
		playwall_list=(ListView) findViewById(R.id.playwall_list);
		

		playwall_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//here put friend id and open friend details fragment
				GetFriendsTempleteMessageListByChildID model=adapter.listPlaywall.getFriendsTempleteMessageListByChildID().get(position);
				Intent intent=new Intent(ChildPlayWallActivity.this,ChildPlayWallDetailActivity.class);
				intent.putExtra("mapid", model.getMapID());
				intent.putExtra("color", model.getColorHeading());
				intent.putExtra("actiontype", model.getActionType());

				startActivity(intent);
				finish();

			}
		});

		playwall_list.setVisibility(View.GONE);

		playwall_list.setOnScrollListener(new OnScrollListener() {



			public void onScrollStateChanged(AbsListView view, int scrollState) 
			{

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				popUpWindowActionDismiss();
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
								new GetFriendsTempleteMessageListByChildIDAsync(StaticVariables.currentChild.getChildID(),(totalItemCount/8)+1).execute();	
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

		child_playwall_imageview.setOnClickListener(new View.OnClickListener() {

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
				Intent intent = new Intent(ChildPlayWallActivity.this, ChildMainDashboardActivity.class);

				startActivity(intent);
				ChildPlayWallActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

				finish();

			}
		});


	}

	private ProgressDialog progressDialog=null;

	private class GetFriendsTempleteMessageListByChildIDAsync extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		int pageIndex=1;

		public  GetFriendsTempleteMessageListByChildIDAsync(int childID, int i)
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
				progressDialog = ProgressDialog.show(ChildPlayWallActivity.this, "", StaticVariables.progressBarText, false);
				progressDialog.setCancelable(false);
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


			if(checkNetwork.checkNetworkConnection(ChildPlayWallActivity.this))
			{
				if(playwallList.getFriendsTempleteMessageListByChildID().size()==0)
				{
					playwallList =serviceMethod.getFriendsTempleteMessageListByChildID(childID,getType,pageIndex,8);
					flag_loading=false;
				}
				else
				{
					GetFriendsTempleteMessageListByChildIDList list=serviceMethod.getFriendsTempleteMessageListByChildID(childID,getType,pageIndex,8);;
					if(list!=null && list.getFriendsTempleteMessageListByChildID().size()>0)
					{
						flag_loading=false;
						playwallList.getFriendsTempleteMessageListByChildID().addAll(list.getFriendsTempleteMessageListByChildID());
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
					if (progressDialog.isShowing())
						progressDialog.cancel();
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

					if(playwallList!=null && playwallList.getFriendsTempleteMessageListByChildID().size()>0)
					{

						//{txtViewAlertHeading.setText(alertsList.getNotificationListByChildIDOnCIList().size()+" new alerts from your buddies.");
						layout_nodata.setVisibility(View.GONE);
						noconnectionimage.setVisibility(View.GONE);
						noconnectiontext.setVisibility(View.GONE);
						playwall_list.setVisibility(View.VISIBLE);
						if(pageIndex==1)
						{
							playwall_list.startAnimation(shake);
							adapter=new ChildPlaywallListAdapter(ChildPlayWallActivity.this, playwallList,ChildPlayWallActivity.this);
							playwall_list.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}
						else
						{
							adapter.notifyDataSetChanged();
							playwall_list.invalidate();
						}


						}
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
		playwall_list.setVisibility(View.GONE);
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
		Intent intent = new Intent(ChildPlayWallActivity.this, ChildMainDashboardActivity.class);

		startActivity(intent);
		ChildPlayWallActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

		finish();
		}
	}
	public ImageView emojione,emojitwo,emojithree,emojifour,emojifive;

	private void initializePopUpAction()
	{
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = (LayoutInflater) ChildPlayWallActivity.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		popupViewAction = layoutInflater.inflate(R.layout.layout_popup_playwall, null);

		//popupViewAction.setBackgroundResource(R.drawable.imeterborder);
		popupWindowAction = new PopupWindow(popupViewAction,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);//we can put on key listener on view
		popupWindowAction.setBackgroundDrawable(new BitmapDrawable());
		popupWindowAction.setWidth((int)(SplashActivity.ScreenWidth * .65f));

		popupWindowAction.setOutsideTouchable(true);
		emojione=(ImageView) popupViewAction.findViewById(R.id.emojione);		
		emojitwo=(ImageView) popupViewAction.findViewById(R.id.emojitwo);		
		emojithree=(ImageView) popupViewAction.findViewById(R.id.emojithree);		
		emojifour=(ImageView) popupViewAction.findViewById(R.id.emojifour);		
		emojifive=(ImageView) popupViewAction.findViewById(R.id.emojifive);	
		/*move=(TextView) popupViewAction.findViewById(R.id.action_move);
		issue=(TextView)popupViewAction. findViewById(R.id.action_issue);*/

		popupViewAction.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				popupWindowAction.dismiss();
				return false;
			}
		});



	}
	
	private  void popUpWindowActionDismiss()
	{
		if(popupWindowAction!=null)
			popupWindowAction.dismiss();
	}
	


}
