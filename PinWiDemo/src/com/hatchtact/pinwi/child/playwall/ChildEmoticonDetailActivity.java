package com.hatchtact.pinwi.child.playwall;

import java.util.ArrayList;

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
import com.hatchtact.pinwi.adapter.ChildEmoticonDetailAdapter;
import com.hatchtact.pinwi.child.ChildMainDashboardActivity;
import com.hatchtact.pinwi.child.ChildMusicPlayer;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.child.SoundEffect;
import com.hatchtact.pinwi.classmodel.GetDetailByMapEmoticID;
import com.hatchtact.pinwi.classmodel.GetDetailByMapEmoticIDList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildEmoticonDetailActivity extends Activity
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

	private View playwall_smiley_layout;
	private LinearLayout layoutemojione,layoutemojitwo,layoutemojithree,layoutemojifour,layoutemojifive;
	private View emojiviewone,emojiviewtwo,emojiviewthree,emojiviewfour,emojiviewfive;
	private RelativeLayout layout_playwall;
	private TextView txtemojione,txtemojitwo,txtemojithree,txtemojifour,txtemojifive,text_playwall;

	private ListView childListView;
	private Animation shake;
	private ChildEmoticonDetailAdapter adapter;
	private GetDetailByMapEmoticIDList childList;
	private ProgressBar data_load_progress;
	private boolean flag_loading=false;

	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext;
	private String MapID;
	private ImageView  emojilayoutimageone,emojilayoutimagetwo,emojilayoutimagethree,emojilayoutimagefour,emojilayoutimagefive;
	private Integer[] arrayImagesSmileys={R.drawable.wow_i,R.drawable.wow_i,R.drawable.lol_i,R.drawable.inspired_i,R.drawable.welldone_i,R.drawable.cool_i,R.drawable.me_too_i,R.drawable.love_i,R.drawable.party_time_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i};

	private String EmoticID;
	private int screen=0;
	private String color="";
	private String actiontype="";
	ArrayList<Integer> emoticIdIntegerArray=new ArrayList<Integer>();
	private int currentSelectedEmoticId=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_emoticondetail);
		isButtonClicked=false;

		screen=getIntent().getExtras().getInt("screen");
		color=getIntent().getExtras().getString("color");
		actiontype=getIntent().getExtras().getString("actiontype");

		MapID=getIntent().getExtras().getString("mapid");
		EmoticID=getIntent().getExtras().getString("emoticid");
		emoticIdIntegerArray=getIntent().getIntegerArrayListExtra("arrayEmotic");
		/*	MapID="5";
		EmoticID="2";*/

		typeFace = new TypeFace(ChildEmoticonDetailActivity.this);
		sharepref = new SharePreferenceClass(ChildEmoticonDetailActivity.this);
		//isMyWishList=false;
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

		childList=new GetDetailByMapEmoticIDList();
		childList.getDetailByMapEmoticID().clear();
		currentSelectedEmoticId=Integer.parseInt(EmoticID);
		new GetDetailByMapEmoticIDAsync(1,currentSelectedEmoticId).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildEmoticonDetailActivity.this, R.raw.two_tone_nav);		
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
		showMessage=new ShowMessages(ChildEmoticonDetailActivity.this);
		serviceMethod=new ServiceMethod();

		txtemojione=(TextView) findViewById(R.id.txtemojione);
		txtemojitwo=(TextView) findViewById(R.id.txtemojitwo);
		txtemojithree=(TextView) findViewById(R.id.txtemojithree);
		txtemojifour=(TextView) findViewById(R.id.txtemojifour);
		txtemojifive=(TextView) findViewById(R.id.txtemojifive);
		txtemojione.setTextColor(getResources().getColor(R.color.black_color));
		txtemojitwo.setTextColor(getResources().getColor(R.color.black_color));
		txtemojithree.setTextColor(getResources().getColor(R.color.black_color));
		txtemojifour.setTextColor(getResources().getColor(R.color.black_color));
		txtemojifive.setTextColor(getResources().getColor(R.color.black_color));

		switch (StaticVariables.emooticionNo) 
		{
		case 1:
			txtemojione.setAlpha(1f);
			txtemojitwo.setAlpha(.7f);
			txtemojithree.setAlpha(.7f);
			txtemojifour.setAlpha(.7f);
			txtemojifive.setAlpha(.7f);
			break;
		case 2:
			txtemojione.setAlpha(.7f);
			txtemojitwo.setAlpha(1f);
			txtemojithree.setAlpha(.7f);
			txtemojifour.setAlpha(.7f);
			txtemojifive.setAlpha(.7f);
			break;
		case 3:
			txtemojione.setAlpha(.7f);
			txtemojitwo.setAlpha(.7f);
			txtemojithree.setAlpha(1f);
			txtemojifour.setAlpha(.7f);
			txtemojifive.setAlpha(.7f);
			break;
		case 4:
			txtemojione.setAlpha(.7f);
			txtemojitwo.setAlpha(.7f);
			txtemojithree.setAlpha(.7f);
			txtemojifour.setAlpha(1f);
			txtemojifive.setAlpha(.7f);
			break;
		case 5:
			txtemojione.setAlpha(.7f);
			txtemojitwo.setAlpha(.7f);
			txtemojithree.setAlpha(.7f);
			txtemojifour.setAlpha(.7f);
			txtemojifive.setAlpha(1f);
			break;

		default:
			break;
		}

		typeFace.setTypefaceGotham(txtemojione);
		typeFace.setTypefaceGotham(txtemojitwo);
		typeFace.setTypefaceGotham(txtemojithree);
		typeFace.setTypefaceGotham(txtemojifour);
		typeFace.setTypefaceGotham(txtemojifive);
		data_load_progress=(ProgressBar) findViewById(R.id.data_load_progress);

		text_playwall=(TextView) findViewById(R.id.text_playwall);
		text_playwall.setText(getResources().getString(R.string.home_text));
		typeFace.setTypefaceGotham(text_playwall);
		layout_playwall=(RelativeLayout) findViewById(R.id.layout_playwall);


		layoutemojione=(LinearLayout) findViewById(R.id.layoutemojione);
		layoutemojitwo=(LinearLayout) findViewById(R.id.layoutemojitwo);
		layoutemojithree=(LinearLayout) findViewById(R.id.layoutemojithree);
		layoutemojifour=(LinearLayout) findViewById(R.id.layoutemojifour);
		layoutemojifive=(LinearLayout) findViewById(R.id.layoutemojifive);
		
		emojiviewone=(View)findViewById(R.id.viewemojione);
		emojiviewtwo=(View)findViewById(R.id.viewemojitwo);
		emojiviewthree=(View)findViewById(R.id.viewemojithree);
		emojiviewfour=(View)findViewById(R.id.viewemojifour);
		emojiviewfive=(View)findViewById(R.id.viewemojifive);

		emojilayoutimageone=(ImageView) findViewById(R.id.emojilayoutimageone);
		emojilayoutimagetwo=(ImageView) findViewById(R.id.emojilayoutimagetwo);
		emojilayoutimagethree=(ImageView) findViewById(R.id.emojilayoutimagethree);
		emojilayoutimagefour=(ImageView) findViewById(R.id.emojilayoutimagefour);
		emojilayoutimagefive=(ImageView) findViewById(R.id.emojilayoutimagefive);


		playwall_smiley_layout=(View) findViewById(R.id.emojiLayout);
		playwall_smiley_layout.setVisibility(View.GONE);

		layout_nodata=(LinearLayout)findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView)findViewById(R.id.noconnectionimage);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext=(TextView) findViewById(R.id.noconnectiontext);
		typeFace.setTypefaceGotham(noconnectiontext);

		shake = AnimationUtils.loadAnimation(this, R.anim.grow_from_top);
		//layoutpoint.startAnimation(shake);
		childListView=(ListView) findViewById(R.id.playwall_emoticon_childlist);



		childListView.setOnScrollListener(new OnScrollListener() {



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
								new GetDetailByMapEmoticIDAsync((totalItemCount/8)+1,currentSelectedEmoticId).execute();	
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

		layout_playwall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveToDashboard();

			}
		});
	}

	protected void moveToDashboard() {
		// TODO Auto-generated method stub

		playSound(soundEffectButtonClicks);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disposeSound();
		Intent intent = new Intent(ChildEmoticonDetailActivity.this, ChildMainDashboardActivity.class);

		startActivity(intent);
		ChildEmoticonDetailActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

		finish();

	}

	private ProgressDialog progressDialog=null;

	private class GetDetailByMapEmoticIDAsync extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		private int currentEmoticId;

		int pageIndex=1;
		String activityId;




		public  GetDetailByMapEmoticIDAsync( int i,int id)
		{
			// TODO Auto-generated constructor stub 

			pageIndex=i;
			currentEmoticId=id;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(pageIndex==1)
			{
				progressDialog = ProgressDialog.show(ChildEmoticonDetailActivity.this, "", StaticVariables.progressBarText, false);
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

			if(checkNetwork.checkNetworkConnection(ChildEmoticonDetailActivity.this))
			{
				if(childList.getDetailByMapEmoticID().size()==0)
				{

					childList =serviceMethod.getDetailByMapEmoticID(Integer.parseInt(MapID), currentEmoticId/*Integer.parseInt(EmoticID)*/, pageIndex, 8);
					flag_loading=false;
				}
				else
				{
					GetDetailByMapEmoticIDList list=serviceMethod.getDetailByMapEmoticID(Integer.parseInt(MapID),currentEmoticId/* Integer.parseInt(EmoticID)*/, pageIndex, 8);
					if(list!=null && list.getDetailByMapEmoticID().size()>0)
					{
						flag_loading=false;
						childList.getDetailByMapEmoticID().addAll(list.getDetailByMapEmoticID());
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

					//if(checkNetwork.checkNetworkConnection(ChildEmoticonDetailActivity.this))
					//	new GetListofChildrensByChildActIDAsync(childID,pageIndex,activityId).execute();
				}
				else
				{
					if(childList!=null && childList.getDetailByMapEmoticID().size()>0)
					{
						layout_nodata.setVisibility(View.GONE);
						noconnectionimage.setVisibility(View.GONE);
						noconnectiontext.setVisibility(View.GONE);
						childListView.setVisibility(View.VISIBLE);

						playwall_smiley_layout.setVisibility(View.VISIBLE);
						//emptyLine.setVisibility(View.VISIBLE);
						//wishlistLayout.setVisibility(View.VISIBLE);
						if(pageIndex==1)
						{
							//wishlist_name.setText(StaticVariables.childWishlistName);


							childListView.startAnimation(shake);


							ArrayList<String> emoticountArray=new ArrayList<String>();
							GetDetailByMapEmoticID model=childList.getDetailByMapEmoticID().get(0);
							setEmoticons(model);

							/*if(model.getEmoticCount().equalsIgnoreCase("")||model.getEmoticCount()==null)
							{
								emoticountArray.add("0");
								emoticountArray.add("0");
								emoticountArray.add("0");
								emoticountArray.add("0");
								emoticountArray.add("0");
							}
							else
							{
								String[] emoticount=model.getEmoticCount().split(",");
								for(int i=0;i<emoticount.length;i++)
								{
									if(emoticount[i].equalsIgnoreCase(""))
									{
										emoticountArray.add("0");
									}
									else
									{
										emoticountArray.add(emoticount[i]);
									}
								}
							}

							txtemojione.setText(emoticountArray.get(0));
							txtemojitwo.setText(emoticountArray.get(1));
							txtemojithree.setText(emoticountArray.get(2));
							txtemojifour.setText(emoticountArray.get(3));
							txtemojifive.setText(emoticountArray.get(4));


							ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

							emojilayoutimageone.setImageResource(arrayImagesSmileys[emoticIdArray.get(0)]);
							emojilayoutimagetwo.setImageResource(arrayImagesSmileys[emoticIdArray.get(1)]);
							emojilayoutimagethree.setImageResource(arrayImagesSmileys[emoticIdArray.get(2)]);
							emojilayoutimagefour.setImageResource(arrayImagesSmileys[emoticIdArray.get(3)]);
							try {
								emojilayoutimagefive.setImageResource(arrayImagesSmileys[emoticIdArray.get(4)]);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								emojilayoutimagefive.setImageResource(arrayImagesSmileys[emoticIdArray.get(0)]);

							}
							 */
							adapter=new ChildEmoticonDetailAdapter(ChildEmoticonDetailActivity.this, childList);
							childListView.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}
						else
						{
							adapter.notifyDataSetChanged();
							childListView.invalidate();
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
		childListView.setVisibility(View.GONE);
		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
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


			if(screen==0)
			{
				Intent intent = new Intent(ChildEmoticonDetailActivity.this, ChildPlayWallActivity.class);

				startActivity(intent);
			}
			else
			{

				Intent intent = new Intent(ChildEmoticonDetailActivity.this, ChildPlayWallDetailActivity.class);
				intent.putExtra("mapid",MapID);
				intent.putExtra("color",color);
				intent.putExtra("actiontype",actiontype);
				startActivity(intent);

			}
			ChildEmoticonDetailActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

			finish();
		}
	}

	private ArrayList<Integer> getEmoticIdArray(
			GetDetailByMapEmoticID model) {
		ArrayList<Integer> emoticIdArray=new ArrayList<Integer>();
		if(model.getEmoticID().equalsIgnoreCase("")||model.getEmoticID()==null)
		{
			emoticIdArray.add(0);
			emoticIdArray.add(1);
			emoticIdArray.add(2);
			emoticIdArray.add(3);
			emoticIdArray.add(4);
		}
		else
		{
			String[] emoticId=model.getEmoticID().split(",");
			for(int i=0;i<emoticId.length;i++)
			{
				if(emoticId[i].equalsIgnoreCase(""))
				{
					emoticIdArray.add(0);
				}
				else
				{
					emoticIdArray.add(Integer.parseInt(emoticId[i]));
				}
			}
		}
		return emoticIdArray;
	}




	private void setEmoticons(GetDetailByMapEmoticID  model)
	{

		ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

		emojilayoutimageone.setImageResource(arrayImagesSmileys[emoticIdArray.get(0)]);
		emojilayoutimagetwo.setImageResource(arrayImagesSmileys[emoticIdArray.get(1)]);
		emojilayoutimagethree.setImageResource(arrayImagesSmileys[emoticIdArray.get(2)]);
		emojilayoutimagefour.setImageResource(arrayImagesSmileys[emoticIdArray.get(3)]);
		try {
			emojilayoutimagefive.setImageResource(arrayImagesSmileys[emoticIdArray.get(4)]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			emojilayoutimagefive.setImageResource(arrayImagesSmileys[emoticIdArray.get(0)]);

		}


		//private TextView txtemojione,txtemojitwo,txtemojithree,txtemojifour,txtemojifive;
		ArrayList<String> emoticountArray = getEmoticCountArray(model);

		txtemojione.setText(emoticountArray.get(0));
		txtemojitwo.setText(emoticountArray.get(1));
		txtemojithree.setText(emoticountArray.get(2));
		txtemojifour.setText(emoticountArray.get(3));

		try {
			txtemojifive.setText(emoticountArray.get(4));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			txtemojifive.setText("0");
		}

		if(currentSelectedEmoticId==emoticIdIntegerArray.get(0))
		{
			layoutemojione.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			emojiviewone.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			layoutemojitwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewtwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojithree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewthree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojifour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojifive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		}
		else if(currentSelectedEmoticId==emoticIdIntegerArray.get(1))
		{
			layoutemojitwo.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			emojiviewtwo.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			layoutemojione.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewone.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojithree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewthree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojifour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojifive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));

		}
		else if(currentSelectedEmoticId==emoticIdIntegerArray.get(2))
		{
			layoutemojithree.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			emojiviewthree.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			layoutemojione.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewone.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojitwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewtwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			
			layoutemojifour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojifive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));

		}
		else if(currentSelectedEmoticId==emoticIdIntegerArray.get(3))
		{
			layoutemojifour.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			emojiviewfour.setBackgroundColor(getResources().getColor(R.color.font_white_color));

			layoutemojione.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewone.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojitwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewtwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojithree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewthree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
	
			layoutemojifive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		}
		else
		{
			layoutemojifive.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			emojiviewfive.setBackgroundColor(getResources().getColor(R.color.font_white_color));
			layoutemojione.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewone.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojitwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewtwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojithree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewthree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			layoutemojifour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			emojiviewfour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
			
		}

		if(emoticountArray.get(0).equalsIgnoreCase("0")||emoticountArray.get(0).equalsIgnoreCase(""))
		{
			txtemojione.setVisibility(View.INVISIBLE);
			emojilayoutimageone.setAlpha(.2f);
			layoutemojione.setEnabled(false);
			layoutemojione .setClickable(false);
		}
		else 
		{
			txtemojione.setVisibility(View.VISIBLE);
			emojilayoutimageone.setAlpha(1f);
			layoutemojione.setEnabled(true);
			layoutemojione .setClickable(true);
		}

		if(emoticountArray.get(1).equalsIgnoreCase("0")||emoticountArray.get(1).equalsIgnoreCase(""))
		{
			txtemojitwo.setVisibility(View.INVISIBLE);
			emojilayoutimagetwo.setAlpha(.2f);
			layoutemojitwo.setEnabled(false);
			layoutemojitwo .setClickable(false);

		}
		else 
		{
			txtemojitwo.setVisibility(View.VISIBLE);
			emojilayoutimagetwo.setAlpha(1f);
			layoutemojitwo.setEnabled(true);
			layoutemojitwo .setClickable(true);
		}

		if(emoticountArray.get(2).equalsIgnoreCase("0")||emoticountArray.get(2).equalsIgnoreCase(""))
		{
			txtemojithree.setVisibility(View.INVISIBLE);
			emojilayoutimagethree.setAlpha(.2f);
			layoutemojithree.setEnabled(false);
			layoutemojithree.setClickable(false);
		}
		else 
		{
			txtemojithree.setVisibility(View.VISIBLE);
			emojilayoutimagethree.setAlpha(1f);
			layoutemojithree.setEnabled(true);
			layoutemojithree.setClickable(true);
		}

		if(emoticountArray.get(3).equalsIgnoreCase("0")||emoticountArray.get(3).equalsIgnoreCase(""))
		{
			txtemojifour.setVisibility(View.INVISIBLE);
			emojilayoutimagefour.setAlpha(.2f);
			layoutemojifour.setEnabled(false);
			layoutemojifour .setClickable(false);
		}
		else 
		{
			txtemojifour.setVisibility(View.VISIBLE);
			emojilayoutimagefour.setAlpha(1f);
			layoutemojifour.setEnabled(true);
			layoutemojifour .setClickable(true);
		}

		if(emoticountArray.get(4).equalsIgnoreCase("0")||emoticountArray.get(4).equalsIgnoreCase(""))
		{
			txtemojifive.setVisibility(View.INVISIBLE);
			emojilayoutimagefive.setAlpha(.2f);
			layoutemojifive.setEnabled(false);
			layoutemojifive .setClickable(false);
		}
		else 
		{
			txtemojifive.setVisibility(View.VISIBLE);
			emojilayoutimagefive.setAlpha(1f);
			layoutemojifive.setEnabled(true);
			layoutemojifive .setClickable(true);
		}



		layoutemojione.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(currentSelectedEmoticId!=emoticIdIntegerArray.get(0))
				{
					childListView.setAdapter(null);

					if(childList==null)
						childList=new GetDetailByMapEmoticIDList();
					childList.getDetailByMapEmoticID().clear();
					currentSelectedEmoticId=emoticIdIntegerArray.get(0);
					
					setBackgroundEmoji();
					layoutemojione.setBackgroundColor(getResources().getColor(R.color.font_white_color));
					emojiviewone.setBackgroundColor(getResources().getColor(R.color.font_white_color));
					new GetDetailByMapEmoticIDAsync(1,currentSelectedEmoticId).execute();
				}
			}
		});
		layoutemojitwo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentSelectedEmoticId!=emoticIdIntegerArray.get(1))
				{
					childListView.setAdapter(null);
					if(childList==null)
						childList=new GetDetailByMapEmoticIDList();
					childList.getDetailByMapEmoticID().clear();

					currentSelectedEmoticId=emoticIdIntegerArray.get(1);
					setBackgroundEmoji();
					layoutemojitwo.setBackgroundColor(getResources().getColor(R.color.font_white_color));
					emojiviewtwo.setBackgroundColor(getResources().getColor(R.color.font_white_color));

					new GetDetailByMapEmoticIDAsync(1,currentSelectedEmoticId).execute();
				}
			}
		});
		layoutemojithree.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentSelectedEmoticId!=emoticIdIntegerArray.get(2))
				{
					childListView.setAdapter(null);

					if(childList==null)
						childList=new GetDetailByMapEmoticIDList();
					childList.getDetailByMapEmoticID().clear();
					setBackgroundEmoji();

					layoutemojithree.setBackgroundColor(getResources().getColor(R.color.font_white_color));
					emojiviewthree.setBackgroundColor(getResources().getColor(R.color.font_white_color));
					currentSelectedEmoticId=emoticIdIntegerArray.get(2);
					new GetDetailByMapEmoticIDAsync(1,currentSelectedEmoticId).execute();
				}
			}
		});
		layoutemojifour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentSelectedEmoticId!=emoticIdIntegerArray.get(3))
				{
					childListView.setAdapter(null);

					if(childList==null)
						childList=new GetDetailByMapEmoticIDList();
					childList.getDetailByMapEmoticID().clear();
					setBackgroundEmoji();
					layoutemojifour.setBackgroundColor(getResources().getColor(R.color.font_white_color));
					emojiviewfour.setBackgroundColor(getResources().getColor(R.color.font_white_color));

					currentSelectedEmoticId=emoticIdIntegerArray.get(3);
					new GetDetailByMapEmoticIDAsync(1,currentSelectedEmoticId).execute();
				}
			}
		});

		layoutemojifive.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentSelectedEmoticId!=emoticIdIntegerArray.get(4))
				{
					childListView.setAdapter(null);

					if(childList==null)
						childList=new GetDetailByMapEmoticIDList();
					childList.getDetailByMapEmoticID().clear();
					setBackgroundEmoji();
					layoutemojifive.setBackgroundColor(getResources().getColor(R.color.font_white_color));
					emojiviewfive.setBackgroundColor(getResources().getColor(R.color.font_white_color));

					currentSelectedEmoticId=emoticIdIntegerArray.get(4);
					new GetDetailByMapEmoticIDAsync(1,currentSelectedEmoticId).execute();
				}
			}
		});

	}

	/**
	 * @param model
	 * @return
	 */
	private ArrayList<String> getEmoticCountArray(GetDetailByMapEmoticID model) 
	{
		ArrayList<String> emoticountArray=new ArrayList<String>();
		if(model.getEmoticCount()==null||model.getEmoticCount().equalsIgnoreCase(""))
		{
			emoticountArray.add("0");
			emoticountArray.add("0");
			emoticountArray.add("0");
			emoticountArray.add("0");
			emoticountArray.add("0");
		}
		else
		{
			String[] emoticount=model.getEmoticCount().split(",");
			for(int i=0;i<emoticount.length;i++)
			{
				if(emoticount[i].equalsIgnoreCase(""))
				{
					emoticountArray.add("0");
				}
				else
				{
					emoticountArray.add(emoticount[i]);
				}
			}
		}
		return emoticountArray;
	}
	
	private void setBackgroundEmoji()
	{
		layoutemojifive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		emojiviewfive.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		layoutemojione.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		emojiviewone.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		layoutemojitwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		emojiviewtwo.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		layoutemojithree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		emojiviewthree.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		layoutemojifour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
		emojiviewfour.setBackgroundColor(getResources().getColor(R.color.emoticon_gray));
	}
}
