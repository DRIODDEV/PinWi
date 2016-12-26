package com.hatchtact.pinwi.child;

import java.util.Collections;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.ChildBuddiesListAdapter;
import com.hatchtact.pinwi.classmodel.GetListOfBuddiesByChildIDOnCI;
import com.hatchtact.pinwi.classmodel.GetListOfBuddiesByChildIDOnCIList;
import com.hatchtact.pinwi.classmodel.SearchListOfBuddiesOnCIList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ComparatorName;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildBuddiesActivity extends Activity
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
	private RelativeLayout layout_alphabetical,layout_buddies,layout_search;
	private TextView txtViewBuddies;
	private ListView buddies_list;
	private Animation shake;
	private ChildBuddiesListAdapter adapter;
	private GetListOfBuddiesByChildIDOnCIList buddiesList;
	private ProgressBar data_load_progress;
	private boolean flag_loading=false;
	private boolean isSearchList=false;
	private boolean isSearchDone=false;
	private int type=1;
	/*getType parameter- 
	·	1 for listing the friend/ pending friends and suggested friend,
	·	2 for listing the friends only (on tab to My Buddies), 
	·	3 for listing the list in sorted order from A-Z (on tab to Alphabetic order)*/

	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext;
	private EditText editsearch;
	private ImageView imageSearch;
	private View editBuddiesLayout;
	private SearchListOfBuddiesOnCIList searchList;
	private ImageView child_alphabetical_imageview,child_search_imageview;
	private TextView text_alphabetical,text_buddies,text_search;
	private boolean isMyBuddies=false;
	private boolean isAlphabetical=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_buddies);
		isAlphabetical=false;
		isButtonClicked=false;
		typeFace = new TypeFace(ChildBuddiesActivity.this);
		sharepref = new SharePreferenceClass(ChildBuddiesActivity.this);
		isMyBuddies=false;
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

		buddiesList=new GetListOfBuddiesByChildIDOnCIList();
		buddiesList.getBuddiesByChildIDOnCI().clear();
		searchList=new SearchListOfBuddiesOnCIList();
		searchList.getSearchListOfBuddiesOnCI().clear();



		new GetBuddiesListByChildID(StaticVariables.currentChild.getChildID(),type,1).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildBuddiesActivity.this, R.raw.two_tone_nav);		
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
		isSearchDone=false;
		isSearchList=false;
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildBuddiesActivity.this);
		serviceMethod=new ServiceMethod();
		layout_alphabetical=(RelativeLayout) findViewById(R.id.layout_alphabetical);
		layout_buddies=(RelativeLayout) findViewById(R.id.layout_buddies);
		layout_search=(RelativeLayout) findViewById(R.id.layout_search);
		txtViewBuddies=(TextView) findViewById(R.id.txtViewBuddies);
		txtViewBuddies.setBackgroundResource(R.drawable.rounded_inactive_buddies);
		data_load_progress=(ProgressBar) findViewById(R.id.data_load_progress);
		child_alphabetical_imageview=(ImageView) findViewById(R.id.child_alphabetical_imageview);
		child_search_imageview=(ImageView) findViewById(R.id.child_search_imageview);

		layout_nodata=(LinearLayout)findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView)findViewById(R.id.noconnectionimage);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext=(TextView) findViewById(R.id.noconnectiontext);
		imageSearch=(ImageView)findViewById(R.id.imageSearch);
		editsearch=(EditText)findViewById(R.id.editSearch);
		editsearch.setHint("Search");
		editBuddiesLayout=(View)findViewById(R.id.editBuddiesLayout);
		//layoutSearch=(LinearLayout)findViewById(R.id.layoutSearch);

		editBuddiesLayout.setVisibility(View.GONE);

		editBuddiesLayout.setBackgroundResource(R.drawable.rounded_background);
		typeFace.setTypefaceGotham(editsearch);
		typeFace.setTypefaceGotham(noconnectiontext);
		typeFace.setTypefaceGotham(txtViewBuddies);

		text_alphabetical=(TextView) findViewById(R.id.text_alphabetical);
		text_buddies=(TextView) findViewById(R.id.text_buddies);
		text_search=(TextView) findViewById(R.id.text_search);
		text_buddies.setText(getResources().getString(R.string.home_text));
		typeFace.setTypefaceGotham(text_alphabetical);
		typeFace.setTypefaceGotham(text_buddies);
		typeFace.setTypefaceGotham(text_search);

		editsearch.addTextChangedListener(new TextWatcher() {

			int len = 0;
			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				// TODO Auto-generated method stub
				String str = s.toString();
				len = str.length();
			}

			@Override
			public void afterTextChanged(Editable s) {
				if(!isSearchDone)
				{

					String str = s.toString();
					boolean hasChar = false;
					for (int k = 0; k < str.length(); k++) 
					{
						if (Character.isLetter(str.charAt(k))) {

							hasChar = true;
						}
					}
					if(len>1)
					{
						hasChar=true;
					}
					if(hasChar)
					{
						isSearchList=true;
						if(searchList!=null)
						{
							searchList.getSearchListOfBuddiesOnCI().clear();
						}
						else
							searchList=new SearchListOfBuddiesOnCIList();
					}
					isSearchDone=true;
					new searchBuddies(1,StaticVariables.currentChild.getChildID()).execute();

				}
				else
				{

					/*	if( s.toString().equalsIgnoreCase(""))
						{
							isSearchList=false;
							if(buddiesList!=null)
							{
								buddiesList.getBuddiesByChildIDOnCI().clear();
							}
							else
								buddiesList=new GetListOfBuddiesByChildIDOnCIList();
							isSearchDone=true;
						//	new fetchFriendsListByLoggedID(1).execute();	
						}*/
				}

			}
		});







		editsearch.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_NEXT)
				{
					return true;
				} else if (actionId == EditorInfo.IME_ACTION_DONE) 
				{
					return true;
				} else 
				{
					return true;
				}

			}
		});



		shake = AnimationUtils.loadAnimation(this, R.anim.grow_from_bottom);
		//layoutpoint.startAnimation(shake);
		buddies_list=(ListView) findViewById(R.id.buddies_list);



		buddies_list.setOnScrollListener(new OnScrollListener() {



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

						if(!isSearchList)
						{
							if(totalItemCount% 8==0)
							{
								new GetBuddiesListByChildID(StaticVariables.currentChild.getChildID(),type,(totalItemCount/8)+1).execute();	
							}
							else
							{
								if(totalItemCount% 8==0)
								{
									//search async task
									//new GetBuddiesListByChildID(StaticVariables.currentChild.getChildID(),type,(totalItemCount/8)+1).execute();	
								}

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
		layout_alphabetical.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playSound(soundEffectButtonClicks);

				//isMyBuddies=false;

				/*if(isMyBuddies)
				{
					txtViewBuddies.setBackgroundResource(R.drawable.rounded_background_buddies);
				}
				else
				{
					txtViewBuddies.setBackgroundResource(R.drawable.rounded_inactive_buddies);
				}
				 */

				//type=3;
				isAlphabetical=true;
				isSearchList=false;
				isSearchDone=true;
				editsearch.setText("");

				editBuddiesLayout.setVisibility(View.GONE);

				child_alphabetical_imageview.setImageResource(R.drawable.alphabetical_active);
				child_search_imageview.setImageResource(R.drawable.search_i);


				/*	if(buddiesList!=null&& buddiesList.getBuddiesByChildIDOnCI()!=null)
					buddiesList.getBuddiesByChildIDOnCI().clear();
				else
				{
					buddiesList=new GetListOfBuddiesByChildIDOnCIList();
					buddiesList.getBuddiesByChildIDOnCI().clear();
				}*/
				try {
					if(adapter.listBuddies!=null && adapter.listBuddies.getBuddiesByChildIDOnCI()!=null &&adapter.listBuddies.getBuddiesByChildIDOnCI().size()>0)
					{
						buddies_list.setVisibility(View.GONE);
						//buddies_list.setAdapter(null);
						ComparatorName simpleName=new ComparatorName();
						Collections.sort(adapter.listBuddies.getBuddiesByChildIDOnCI(), simpleName);
						Collections.reverse(adapter.listBuddies.getBuddiesByChildIDOnCI());
						buddies_list.setVisibility(View.VISIBLE);
						/*adapter=new ChildBuddiesListAdapter(ChildBuddiesActivity.this, adapter.listBuddies,ChildBuddiesActivity.this);
						buddies_list.setAdapter(adapter);*/
						adapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//new GetBuddiesListByChildID(StaticVariables.currentChild.getChildID(),type,1).execute();
			}
		});
		layout_buddies.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSearchList=false;
				isSearchDone=true;

				playSound(soundEffectButtonClicks);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disposeSound();
				Intent intent = new Intent(ChildBuddiesActivity.this, ChildMainDashboardActivity.class);

				startActivity(intent);
				ChildBuddiesActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

				finish();

			}
		});
		layout_search.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playSound(soundEffectButtonClicks);
				isMyBuddies=false;
				isAlphabetical=false;
				if(isMyBuddies)
				{
					txtViewBuddies.setBackgroundResource(R.drawable.rounded_background_buddies);
				}
				else
				{
					txtViewBuddies.setBackgroundResource(R.drawable.rounded_inactive_buddies);
					type=1;
					layout_nodata.setVisibility(View.GONE);
				}
				child_alphabetical_imageview.setImageResource(R.drawable.alphabetical_i);
				child_search_imageview.setImageResource(R.drawable.search_active);
				isSearchList=true;
				isSearchDone=false;

				//editsearch.setText("");
				editBuddiesLayout.setVisibility(View.VISIBLE);
				buddies_list.setAdapter(null);

			}
		});
		txtViewBuddies.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isAlphabetical=false;

				isSearchList=false;
				isMyBuddies=!isMyBuddies;
				isSearchDone=true;
				editsearch.setText("");
				editBuddiesLayout.setVisibility(View.GONE);


				buddies_list.setAdapter(null);

				child_alphabetical_imageview.setImageResource(R.drawable.alphabetical_i);
				child_search_imageview.setImageResource(R.drawable.search_i);

				if(isMyBuddies)
				{
					txtViewBuddies.setBackgroundResource(R.drawable.rounded_background_buddies);
					type=2;	

				}
				else
				{
					txtViewBuddies.setBackgroundResource(R.drawable.rounded_inactive_buddies);
					type=1;		
				}


				if(buddiesList!=null&& buddiesList.getBuddiesByChildIDOnCI()!=null)
					buddiesList.getBuddiesByChildIDOnCI().clear();
				else
				{
					buddiesList=new GetListOfBuddiesByChildIDOnCIList();
					buddiesList.getBuddiesByChildIDOnCI().clear();
				}
				new GetBuddiesListByChildID(StaticVariables.currentChild.getChildID(),type,1).execute();

			}
		});


	}

	private ProgressDialog progressDialog=null;

	private class GetBuddiesListByChildID extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		private int type;
		int pageIndex=1;

		public  GetBuddiesListByChildID(int childID,int type, int i)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
			this.type=type;
			pageIndex=i;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(pageIndex==1)
			{
				progressDialog = ProgressDialog.show(ChildBuddiesActivity.this, "", StaticVariables.progressBarText, false);
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


			if(checkNetwork.checkNetworkConnection(ChildBuddiesActivity.this))
			{
				if(buddiesList.getBuddiesByChildIDOnCI().size()==0)
				{
					buddiesList =serviceMethod.getBuddiesList(childID,type,pageIndex,8);;
					flag_loading=false;
				}
				else
				{
					GetListOfBuddiesByChildIDOnCIList list=serviceMethod.getBuddiesList(childID,type,pageIndex,8);;
					if(list!=null && list.getBuddiesByChildIDOnCI().size()>0)
					{
						flag_loading=false;
						buddiesList.getBuddiesByChildIDOnCI().addAll(list.getBuddiesByChildIDOnCI());
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

					if(checkNetwork.checkNetworkConnection(ChildBuddiesActivity.this))
						new GetBuddiesListByChildID(childID,type,pageIndex).execute();
				}
				else
				{

					if(buddiesList!=null && buddiesList.getBuddiesByChildIDOnCI().size()>0)
					{

						{
							layout_nodata.setVisibility(View.GONE);
							noconnectionimage.setVisibility(View.GONE);
							noconnectiontext.setVisibility(View.GONE);
							buddies_list.setVisibility(View.VISIBLE);
							if(pageIndex==1)
							{
								buddies_list.startAnimation(shake);
								adapter=new ChildBuddiesListAdapter(ChildBuddiesActivity.this, buddiesList,ChildBuddiesActivity.this);
								buddies_list.setAdapter(adapter);
								adapter.notifyDataSetChanged();
							}
							else
							{
								if(isAlphabetical)
								{
									ComparatorName simpleName=new ComparatorName();
									Collections.sort(adapter.listBuddies.getBuddiesByChildIDOnCI(), simpleName);
									Collections.reverse(adapter.listBuddies.getBuddiesByChildIDOnCI());
								}
								adapter.notifyDataSetChanged();
								buddies_list.invalidate();
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
		buddies_list.setVisibility(View.GONE);
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


	private ProgressDialog progressDialogSearch=null;	

	private class searchBuddies extends AsyncTask<Void, Void, Integer>
	{

		int pageIndexSearch=1;
		public searchBuddies(int pageIndex,int childId) {
			// TODO Auto-generated constructor stub
			pageIndexSearch=pageIndex;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogSearch = ProgressDialog.show(ChildBuddiesActivity.this, "", StaticVariables.progressBarText, false);
			progressDialogSearch.setCancelable(false);
			if(searchList==null)
			{
				searchList=new SearchListOfBuddiesOnCIList();
			}
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildBuddiesActivity.this))
			{
				if(searchList.getSearchListOfBuddiesOnCI().size()==0)
				{
					searchList =serviceMethod.getSearchListBuddies(StaticVariables.currentChild.getChildID(),editsearch.getText().toString().trim(), pageIndexSearch, 8);
					flag_loading=false;
				}
				else
				{
					SearchListOfBuddiesOnCIList list=serviceMethod.getSearchListBuddies(StaticVariables.currentChild.getChildID(),editsearch.getText().toString().trim(), pageIndexSearch, 8);
					if(list!=null && list.getSearchListOfBuddiesOnCI().size()>0)
					{
						flag_loading=false;
						searchList.getSearchListOfBuddiesOnCI().addAll(list.getSearchListOfBuddiesOnCI());
					}


				}}
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
				if (progressDialogSearch.isShowing())
					progressDialogSearch.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ChildBuddiesActivity.this))
					new searchBuddies(pageIndexSearch,StaticVariables.currentChild.getChildID()).execute();

			}
			else
			{
				if(searchList!=null && searchList.getSearchListOfBuddiesOnCI().size()>0)
				{
					layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					buddies_list.setVisibility(View.VISIBLE);
					GetListOfBuddiesByChildIDOnCIList  list=new GetListOfBuddiesByChildIDOnCIList();
					for(int i=0;i<searchList.getSearchListOfBuddiesOnCI().size();i++)
					{

						GetListOfBuddiesByChildIDOnCI model=new GetListOfBuddiesByChildIDOnCI();
						model.setChildName(searchList.getSearchListOfBuddiesOnCI().get(i).getChildName());
						model.setFriendID(searchList.getSearchListOfBuddiesOnCI().get(i).getFriendID());
						model.setProfileImage(searchList.getSearchListOfBuddiesOnCI().get(i).getProfileImage());
						model.setStatus(searchList.getSearchListOfBuddiesOnCI().get(i).getStatus());
						model.setTotalFriend(searchList.getSearchListOfBuddiesOnCI().get(i).getTotalFriend());
						list.getBuddiesByChildIDOnCI().add(model);

					}

					buddiesList=list;
					if(pageIndexSearch==1)
					{
						adapter=new ChildBuddiesListAdapter(ChildBuddiesActivity.this, list,ChildBuddiesActivity.this);
						buddies_list.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
					else
					{
						adapter.listBuddies.getBuddiesByChildIDOnCI().addAll(list.getBuddiesByChildIDOnCI());
						adapter.notifyDataSetChanged();
						buddies_list.invalidate();
					}

				}
				else
				{


					getError();
				}	
			}	
			isSearchDone=false;
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
			Intent intent = new Intent(ChildBuddiesActivity.this, ChildMainDashboardActivity.class);

			startActivity(intent);
			ChildBuddiesActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

			finish();
		}
	}

	public void errorMessageNoFriend()
	{
		/*	Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
		layout_nodata.setVisibility(View.VISIBLE);*/
		layout_nodata.setVisibility(View.VISIBLE);
		buddies_list.setVisibility(View.GONE);
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText("It can be boring without buddies. Add some to your list.");
	} 
}
