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
import com.hatchtact.pinwi.adapter.ChildWishListDetailAdapter;
import com.hatchtact.pinwi.classmodel.GetListofChildrensByChildActIDList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildWishListDetailActivity extends Activity
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
	//private RelativeLayout layout_alphabetical,layout_buddies,layout_search;
	//private TextView txtViewWishList;
	private TextView wishlist_name;
	private TextView txt_child_wishlist_number;

	private ListView childListView;
	private Animation shake;
	private ChildWishListDetailAdapter adapter;
	private GetListofChildrensByChildActIDList childList;
	private ProgressBar data_load_progress;
	private boolean flag_loading=false;

	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext;
	private ImageView child_wishlist_small_image;
	private View  emptyLine;
	//private RelativeLayout mainlayout;
	private View wishlistLayout;
	private CustomLoader customProgressLoader;


	//private ImageView child_alphabetical_imageview,child_search_imageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_child_wishlistdetail);
		customProgressLoader=new CustomLoader(this);
		isButtonClicked=false;
		typeFace = new TypeFace(ChildWishListDetailActivity.this);
		sharepref = new SharePreferenceClass(ChildWishListDetailActivity.this);
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

		childList=new GetListofChildrensByChildActIDList();
		childList.getListofChildrensByChildActID().clear();

		new GetListofChildrensByChildActIDAsync(StaticVariables.currentChild.getChildID(),1,StaticVariables.childIdBuddiesDetail).execute();
	}

	private void initSoundData() 
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildDashboardActivity.this, R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(ChildWishListDetailActivity.this, R.raw.two_tone_nav);		
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
		showMessage=new ShowMessages(ChildWishListDetailActivity.this);
		serviceMethod=new ServiceMethod();
		/*	layout_alphabetical=(RelativeLayout) findViewById(R.id.layout_alphabetical);
		layout_buddies=(RelativeLayout) findViewById(R.id.layout_buddies);
		layout_search=(RelativeLayout) findViewById(R.id.layout_search);*/
		/*txtViewWishList=(TextView) findViewById(R.id.txtViewWishlist);
		txtViewWishList.setBackgroundResource(R.drawable.rounded_inactive_buddies);*/
		txt_child_wishlist_number=(TextView) findViewById(R.id.txt_child_wishlist_number);
		wishlist_name=(TextView) findViewById(R.id.wishlist_name);

		data_load_progress=(ProgressBar) findViewById(R.id.data_load_progress);
		typeFace.setTypefaceGothamBold(wishlist_name);
		typeFace.setTypefaceGotham(txt_child_wishlist_number);
		txt_child_wishlist_number.setAlpha(.7f);

		child_wishlist_small_image=(ImageView) findViewById(R.id.child_wishlist_small_image);
		emptyLine=(View) findViewById(R.id.emptyLine);
		child_wishlist_small_image.setVisibility(View.INVISIBLE);
		emptyLine.setVisibility(View.INVISIBLE);
		wishlistLayout=(View) findViewById(R.id.wishlistLayout);
		wishlistLayout.setVisibility(View.INVISIBLE);

		/*child_alphabetical_imageview=(ImageView) findViewById(R.id.child_alphabetical_imageview);
		child_search_imageview=(ImageView) findViewById(R.id.child_search_imageview);*/

		layout_nodata=(LinearLayout)findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView)findViewById(R.id.noconnectionimage);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext=(TextView) findViewById(R.id.noconnectiontext);
		/*imageSearch=(ImageView)findViewById(R.id.imageSearch);
		editsearch=(EditText)findViewById(R.id.editSearch);
		editsearch.setHint("Search");
		editBuddiesLayout=(View)findViewById(R.id.editWishlistLayout);*/
		//layoutSearch=(LinearLayout)findViewById(R.id.layoutSearch);

		//editBuddiesLayout.setVisibility(View.GONE);

		//editBuddiesLayout.setBackgroundResource(R.drawable.rounded_background);
		//typeFace.setTypefaceRegular(editsearch);
		typeFace.setTypefaceGotham(noconnectiontext);

		shake = AnimationUtils.loadAnimation(this, R.anim.grow_from_top);
		//layoutpoint.startAnimation(shake);
		childListView=(ListView) findViewById(R.id.buddies_list);



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
								new GetListofChildrensByChildActIDAsync(StaticVariables.currentChild.getChildID(),(totalItemCount/8)+1,StaticVariables.childIdBuddiesDetail).execute();	
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
		/*layout_alphabetical.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type=3;
				isSearchList=false;
				editsearch.setText("");
				isSearchDone=true;
				editBuddiesLayout.setVisibility(View.GONE);

				child_alphabetical_imageview.setImageResource(R.drawable.alphabetical_active);
				child_search_imageview.setImageResource(R.drawable.search_i);


				if(wishList!=null&& wishList.getListOfWishListsByChildID()!=null)
					wishList.getListOfWishListsByChildID().clear();
				else
				{
					wishList=new GetListOfWishListsByChildIDList();
					wishList.getListOfWishListsByChildID().clear();
				}
				new GetListOfWishListsByChildIDAsync(StaticVariables.currentChild.getChildID(),type,1).execute();
			}
		});
		layout_buddies.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSearchList=false;
				playSound(soundEffectButtonClicks);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disposeSound();
				Intent intent = new Intent(ChildWishListDetailActivity.this, ChildMainDashboardActivity.class);

				startActivity(intent);

				finish();

			}
		});
		layout_search.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				child_alphabetical_imageview.setImageResource(R.drawable.alphabetical_i);
				child_search_imageview.setImageResource(R.drawable.search_active);
				isSearchList=true;
				isSearchDone=false;

				//editsearch.setText("");
				editBuddiesLayout.setVisibility(View.VISIBLE);
				wish_list.setAdapter(null);

			}
		});
		txtViewWishList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSearchList=false;
				isMyWishList=!isMyWishList;
				isSearchDone=true;
				editsearch.setText("");
				editBuddiesLayout.setVisibility(View.GONE);
				wish_list.setAdapter(null);
				child_alphabetical_imageview.setImageResource(R.drawable.alphabetical_i);
				child_search_imageview.setImageResource(R.drawable.search_i);

				if(isMyWishList)
				{
					txtViewWishList.setBackgroundResource(R.drawable.rounded_background_buddies);
					type=2;	
				}
				else
				{
					txtViewWishList.setBackgroundResource(R.drawable.rounded_inactive_buddies);
					type=1;		
				}


				if(wishList!=null&& wishList.getListOfWishListsByChildID()!=null)
					wishList.getListOfWishListsByChildID().clear();
				else
				{
					wishList=new GetListOfWishListsByChildIDList();
					wishList.getListOfWishListsByChildID().clear();
				}
				new GetListOfWishListsByChildIDAsync(StaticVariables.currentChild.getChildID(),type,1).execute();

			}
		});
		 */	}

	//private ProgressDialog progressDialog=null;

	private class GetListofChildrensByChildActIDAsync extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		int pageIndex=1;
		String activityId;


		public  GetListofChildrensByChildActIDAsync(int childID, int i,String childIdBuddiesDetail)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;

			pageIndex=i;
			activityId=childIdBuddiesDetail;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(pageIndex==1)
			{
				/*progressDialog = ProgressDialog.show(ChildWishListDetailActivity.this, "", StaticVariables.progressBarText, false);
				progressDialog.setCancelable(false);*/
				if(customProgressLoader!=null)
				{
					customProgressLoader.startHandler();
				}
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

			if(checkNetwork.checkNetworkConnection(ChildWishListDetailActivity.this))
			{
				if(childList.getListofChildrensByChildActID().size()==0)
				{
					childList =serviceMethod.getListofChildrensByChildActID(childID, activityId, 8, pageIndex);
					flag_loading=false;
				}
				else
				{
					GetListofChildrensByChildActIDList list=serviceMethod.getListofChildrensByChildActID(childID, activityId, 8, pageIndex);
					if(list!=null && list.getListofChildrensByChildActID().size()>0)
					{
						flag_loading=false;
						childList.getListofChildrensByChildActID().addAll(list.getListofChildrensByChildActID());
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
					customProgressLoader.removeCallbacksHandler();
					/*if (progressDialog.isShowing())
						progressDialog.cancel();*/
				}
				else
				{
					data_load_progress.setVisibility(View.GONE);
				}
				if(result==-1)
				{
					showMessage.showToastMessage("Please check your network connection");

					if(checkNetwork.checkNetworkConnection(ChildWishListDetailActivity.this))
						new GetListofChildrensByChildActIDAsync(childID,pageIndex,activityId).execute();
				}
				else
				{
					if(childList!=null && childList.getListofChildrensByChildActID().size()>0)
					{

						{
							layout_nodata.setVisibility(View.GONE);
							noconnectionimage.setVisibility(View.GONE);
							noconnectiontext.setVisibility(View.GONE);
							childListView.setVisibility(View.VISIBLE);
							child_wishlist_small_image.setVisibility(View.VISIBLE);
							emptyLine.setVisibility(View.VISIBLE);
							wishlistLayout.setVisibility(View.VISIBLE);
							if(pageIndex==1)
							{
								wishlistLayout.startAnimation(shake);
								txt_child_wishlist_number.setText(childList.getListofChildrensByChildActID().get(0).getFriendsDoingThis());
								wishlist_name.setText(StaticVariables.childWishlistName);
								adapter=new ChildWishListDetailAdapter(ChildWishListDetailActivity.this, childList);
								childListView.setAdapter(adapter);
								adapter.notifyDataSetChanged();
							}
							else
							{
								adapter.notifyDataSetChanged();
								childListView.invalidate();
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
			Intent intent = new Intent(ChildWishListDetailActivity.this, ChildWishListActivity.class);

			startActivity(intent);
			ChildWishListDetailActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

			finish();
		}
	}
}
