package com.hatchtact.pinwi.child.playwall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.child.ChildMusicPlayer;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.child.SoundEffect;
import com.hatchtact.pinwi.classmodel.GetDetailByChildID;
import com.hatchtact.pinwi.classmodel.GetDetailByChildIDList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class ChildPlayWallDetailActivity extends Activity {

	private ServiceMethod serviceMethod = null;
	private ShowMessages showMessage = null;
	private CheckNetwork checkNetwork = null;
	private SharePreferenceClass sharepref = null;
	private HexagonImageView child_header_image;
	private ImageView child_header_music;
	private Bitmap bitmap;
	private TypeFace typeFace;
	private SoundEffect soundEffectButtonClicks = null;
	private ImageView child_header_move_to_access_profile;
	private ChildMusicPlayer childMusicPlayer = null;
	private ImageView child_header_voice_over;
	private boolean isButtonClicked = false;
	private boolean isMusicStop = false;
	private boolean isMute = false;

	private Animation shake;
	private GetDetailByChildIDList detailList;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext, text_playwall;

	private HexagonImageView child_buddies_image;
	private HexagonImageView child_buddies;
	private TextView buddies_name, playwall_heading, playwall_desc,
	songDuration, detail_time;
	private ImageView playwall_image, mp3Image;
	private View playwall_audio;
	private SeekBar seekBar;
	private RelativeLayout mainLayoutDetail;

	private String MapId;
	private String color;
	private String actionType;
	private MediaPlayer mediaPlayerAudioMsg;

	// audio

	private int startValue = 120;
	private int min;
	/*
	 * private Handler handlerForTime; private Runnable runnableForTime;
	 */
	private Handler handlerForPlaying;
	private Runnable runnableForPlaying;
	private boolean isRecording;
	private boolean isPlaying;
	private int playDuration;
	private int startProgress = 0;
	RelativeLayout playwalldatalayout;
	private TextView txtemojione,txtemojitwo,txtemojithree,txtemojifour,txtemojifive,buddies_time;
	private ImageView  emojilayoutimageone,emojilayoutimagetwo,emojilayoutimagethree,emojilayoutimagefour,emojilayoutimagefive;
	private RelativeLayout layout_bottom;
	private Integer[] arrayImagesSmileys={R.drawable.wow_i,R.drawable.wow_i,R.drawable.lol_i,R.drawable.inspired_i,R.drawable.welldone_i,R.drawable.cool_i,R.drawable.me_too_i,R.drawable.love_i,R.drawable.party_time_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i};
	private LinearLayout layoutemojione,layoutemojitwo,layoutemojithree,layoutemojifour,layoutemojifive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_playwalldetail);
		MapId = getIntent().getExtras().getString("mapid");
		color = getIntent().getExtras().getString("color");
		actionType = getIntent().getExtras().getString("actiontype");

		isButtonClicked = false;
		typeFace = new TypeFace(ChildPlayWallDetailActivity.this);
		sharepref = new SharePreferenceClass(ChildPlayWallDetailActivity.this);
		setHeaderItems();
		initSoundData();
		initialiseData();
		setClickListeners();
		playSound(AccessProfileActivity.soundEffectTransition);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getBoolean("PlaySound", false)) {
				// ####### play media Sound #######
				childMusicPlayer = new ChildMusicPlayer(
						AccessProfileActivity.getInstance(), R.raw.voice6);
				childMusicPlayer.play();
			}
		}

		detailList = new GetDetailByChildIDList();
		detailList.getDetailByChildID().clear();

		new GetDetailByChildIDAsync().execute();
	}

	private void initSoundData() {
		// TODO Auto-generated method stub
		// soundEffectTransition = new SoundEffect(ChildDashboardActivity.this,
		// R.raw.pageflip);
		soundEffectButtonClicks = new SoundEffect(
				ChildPlayWallDetailActivity.this, R.raw.two_tone_nav);
	}

	private void playSound(SoundEffect sound) {
		if (!isMute && sound != null) {
			sound.play(1.0f);
		}
	}

	@SuppressLint("NewApi")
	private void setHeaderItems() {
		// TODO Auto-generated method stub
		child_header_image = (HexagonImageView) findViewById(R.id.child_header_image);
		try {
			/*if (StaticVariables.currentChild.getProfileImage() != null
					&& StaticVariables.currentChild.getProfileImage().trim()
					.length() > 100) {
				byte[] imageByteParent = Base64.decode(
						StaticVariables.currentChild.getProfileImage(), 0);
				if (imageByteParent != null) {
					bitmap = Bitmap.createScaledBitmap(BitmapFactory
							.decodeByteArray(imageByteParent, 0,
									imageByteParent.length), dp2px(80),
									dp2px(80), false);
					child_header_image.setImageBitmap(bitmap);
				}
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
			
			else {
				bitmap = Bitmap.createScaledBitmap(
						BitmapFactory.decodeResource(getResources(),
								R.drawable.child_image), dp2px(80), dp2px(80),
								false);
				child_header_image.setImageBitmap(bitmap);
			}
		} catch (OutOfMemoryError e) {

		} catch (Exception e) {
			// TODO: handle exception
		}

		child_header_music = (ImageView) findViewById(R.id.child_header_music);

		isMute = sharepref.isSound(StaticVariables.currentChild.getChildID()
				+ "");
		isMusicStop = sharepref.isVoiceOver(StaticVariables.currentChild
				.getChildID() + "");

		setVolumeIcon();

		child_header_music.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isMute = !isMute;
				sharepref.setSound(isMute,
						StaticVariables.currentChild.getChildID() + "");
				if (isMute) {
					soundEffectButtonClicks.play(1.0f);
				}
				setVolumeIcon();

			}
		});

		child_header_voice_over = (ImageView) findViewById(R.id.child_header_voice_over);
		/*
		 * if(!StaticVariables.isTutorialSoundEnabled) {
		 * child_header_voice_over.setVisibility(View.GONE); } else
		 */
		{
			child_header_voice_over.setOnClickListener(new OnClickListener() {

				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					isMusicStop = !isMusicStop;
					sharepref.setVoiceOvers(isMusicStop,
							StaticVariables.currentChild.getChildID() + "");

					if (isMusicStop) {
						soundEffectButtonClicks.play(1.0f);
						if (childMusicPlayer != null) {
							if (childMusicPlayer.getMediaPlayer().isPlaying()) {
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
		child_header_move_to_access_profile
		.setBackgroundResource(R.drawable.back_child_dashboard);

		child_header_move_to_access_profile
		.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
			}
		});

	}

	private void setVoiceOverIcon() {
		if (isMusicStop) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				child_header_voice_over.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.child_voiceovermute));

			} else {
				child_header_voice_over.setBackground(getResources()
						.getDrawable(R.drawable.child_voiceovermute));

			}
		} else {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				child_header_voice_over.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.child_voiceover));

			} else {
				child_header_voice_over.setBackground(getResources()
						.getDrawable(R.drawable.child_voiceover));

			}
		}
	}

	@SuppressLint("NewApi")
	private void setVolumeIcon() {
		if (isMute) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				child_header_music.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.child_mute));

			} else {
				child_header_music.setBackground(getResources().getDrawable(
						R.drawable.child_mute));

			}
		} else {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				child_header_music.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.child_volume));

			} else {
				child_header_music.setBackground(getResources().getDrawable(
						R.drawable.child_volume));

			}
		}
	}

	private void initialiseData() {

		// TODO Auto-generated method stub
		checkNetwork = new CheckNetwork();
		showMessage = new ShowMessages(ChildPlayWallDetailActivity.this);
		serviceMethod = new ServiceMethod();
		layout_nodata = (LinearLayout) findViewById(R.id.layout_nodata);
		noconnectionimage = (ImageView) findViewById(R.id.noconnectionimage);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext = (TextView) findViewById(R.id.noconnectiontext);
		typeFace.setTypefaceGotham(noconnectiontext);

		child_buddies_image = (HexagonImageView) findViewById(R.id.child_buddies_image);
		child_buddies = (HexagonImageView) findViewById(R.id.child_buddies);
		buddies_name = (TextView) findViewById(R.id.buddies_name);
		playwall_heading = (TextView) findViewById(R.id.playwall_heading);
		playwall_desc = (TextView) findViewById(R.id.playwall_desc);
		songDuration = (TextView) findViewById(R.id.songDuration);
		detail_time = (TextView) findViewById(R.id.detail_time);
		playwall_image = (ImageView) findViewById(R.id.playwall_image);
		mp3Image = (ImageView) findViewById(R.id.mp3Image);
		mp3Image.setFocusable(true);
		mp3Image.setClickable(true);
		playwall_audio = findViewById(R.id.playwall_audio);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		typeFace.setTypefaceGotham(buddies_name);
		typeFace.setTypefaceGotham(playwall_heading);
		typeFace.setTypefaceGotham(playwall_desc);
		typeFace.setTypefaceGotham(songDuration);
		typeFace.setTypefaceGotham(detail_time);
		mainLayoutDetail = (RelativeLayout) findViewById(R.id.mainLayoutDetail);
		mainLayoutDetail.setVisibility(View.GONE);
		child_buddies_image.setVisibility(View.GONE);
		playwalldatalayout = (RelativeLayout) findViewById(R.id.playwalldatalayout);

		buddies_name.setTextColor(getResources().getColor(R.color.black_color));
		playwall_heading.setTextColor(getResources().getColor(
				R.color.black_color));
		playwall_desc.setTextColor(getResources().getColor(
				R.color.font_blackcoloralphanew));
		detail_time.setTextColor(getResources().getColor(R.color.black_color));
		detail_time.setAlpha(.6f);

		emojilayoutimageone=(ImageView)findViewById(R.id.emojilayoutimageone);	
		emojilayoutimagetwo=(ImageView)findViewById(R.id.emojilayoutimagetwo);
		emojilayoutimagethree=(ImageView)findViewById(R.id.emojilayoutimagethree);	
		emojilayoutimagefour=(ImageView)findViewById(R.id.emojilayoutimagefour);
		emojilayoutimagefive=(ImageView)findViewById(R.id.emojilayoutimagefive);	
		layout_bottom=(RelativeLayout)findViewById(R.id.layout_bottom);
		layoutemojione = (LinearLayout)findViewById(R.id.layoutemojione);
		layoutemojitwo = (LinearLayout)findViewById(R.id.layoutemojitwo);
		layoutemojithree = (LinearLayout)findViewById(R.id.layoutemojithree);
		layoutemojifour = (LinearLayout)findViewById(R.id.layoutemojifour);
		layoutemojifive = (LinearLayout)findViewById(R.id.layoutemojifive);

		txtemojione = (TextView)findViewById(R.id.txtemojione);
		txtemojitwo = (TextView)findViewById(R.id.txtemojitwo);
		txtemojithree = (TextView)findViewById(R.id.txtemojithree);
		txtemojifour = (TextView)findViewById(R.id.txtemojifour);
		txtemojifive = (TextView)findViewById(R.id.txtemojifive);
		typeFace.setTypefaceGotham(txtemojione);
		typeFace.setTypefaceGotham(txtemojitwo);
		typeFace.setTypefaceGotham(txtemojithree);
		typeFace.setTypefaceGotham(txtemojifour);
		typeFace.setTypefaceGotham(txtemojifive);

		shake = AnimationUtils.loadAnimation(this, R.anim.fade_in);

	}

	private void setClickListeners() {

	}

	private ProgressDialog progressDialog = null;

	private class GetDetailByChildIDAsync extends
	AsyncTask<Void, Void, Integer> {

		public GetDetailByChildIDAsync() {
			// TODO Auto-generated constructor stub

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(
					ChildPlayWallDetailActivity.this, "",
					StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);

		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int ErrorCode = 0;

			if (checkNetwork
					.checkNetworkConnection(ChildPlayWallDetailActivity.this)) {
				if (detailList.getDetailByChildID().size() == 0) {
					detailList = serviceMethod.getDetailByChildID(Integer
							.parseInt(MapId));
				}

			} else {
				ErrorCode = -1;
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				if (progressDialog.isShowing())
					progressDialog.cancel();

				if (result == -1) {
					showMessage
					.showToastMessage("Please check your network connection");

					/*
					 * if(checkNetwork.checkNetworkConnection(ChildAlertActivity.
					 * this)) new
					 * GetBuddiesListByChildID(childID,type,pageIndex)
					 * .execute();
					 */
				} else {

					if (detailList != null
							&& detailList.getDetailByChildID().size() > 0) {

						// {txtViewAlertHeading.setText(alertsList.getNotificationListByChildIDOnCIList().size()+" new alerts from your buddies.");
						layout_nodata.setVisibility(View.GONE);
						noconnectionimage.setVisibility(View.GONE);
						noconnectiontext.setVisibility(View.GONE);
						mainLayoutDetail.setVisibility(View.VISIBLE);
						child_buddies_image.setVisibility(View.VISIBLE);

						final GetDetailByChildID model = detailList
								.getDetailByChildID().get(0);
						model.setActionType(actionType);
						setProfileImage(model);

						setEmoticons(model);

						buddies_name.setText(model.getChildName());
						playwall_heading.setText(model.getTempleteText());
						detail_time.setText(model.getTime());
						playwall_heading.setBackgroundColor(Color
								.parseColor(color));

						if (model.getActionType().equalsIgnoreCase("1")) {
							mainLayoutDetail.startAnimation(shake);
							layout_bottom.startAnimation(shake);

							playwall_image.setVisibility(View.GONE);
							playwall_desc.setVisibility(View.VISIBLE);
							playwall_audio.setVisibility(View.GONE);
							playwall_desc.setText(model.getText());
							playwall_desc.setBackgroundColor(getResources()
									.getColor(R.color.notepad_edit));
						} else if (model.getActionType().equalsIgnoreCase("2")) {
							mainLayoutDetail.startAnimation(shake);
							layout_bottom.startAnimation(shake);

							playwall_image.setVisibility(View.VISIBLE);
							playwall_desc.setVisibility(View.GONE);
							playwall_audio.setVisibility(View.GONE);

							if (model.getText() != null
									&& model.getText().length() > 100) {
								byte[] imageByteRefill = Base64.decode(
										model.getText(), 0);
								if (imageByteRefill != null) {
									playwall_image.setImageBitmap(BitmapFactory
											.decodeByteArray(imageByteRefill,
													0, imageByteRefill.length));
								}
							}
						} else if (model.getActionType().equalsIgnoreCase("3")) {
							playwalldatalayout
							.setBackgroundColor(getResources()
									.getColor(
											R.color.black_colorwithalpha));

							playwall_image.setVisibility(View.GONE);
							playwall_desc.setVisibility(View.GONE);
							playwall_audio.setVisibility(View.VISIBLE);
							if (model.getText() != null
									&& model.getText().length() > 100) {
								byte[] imageByteRefill = Base64.decode(
										model.getText(), 0);
								if (imageByteRefill != null) {
									if (mediaPlayerAudioMsg == null) {
										playMp3(imageByteRefill);
									} else {
										if (!mediaPlayerAudioMsg.isPlaying()) {
											playMp3(imageByteRefill);
										} else {
											stopVoiceMsg();
										}

									}
								}

								mp3Image.setImageResource(R.drawable.pause);

								mp3Image.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (mediaPlayerAudioMsg != null) {
											if (mediaPlayerAudioMsg.isPlaying()) {
												mp3Image.setImageResource(R.drawable.play_i);
												stopTimerForAudioPlay();
												stopVoiceMsg();

											} else {

												mp3Image.setImageResource(R.drawable.pause);

												byte[] imageByteRefill = Base64.decode(
														model.getText(), 0);
												if (imageByteRefill != null) {
													if (mediaPlayerAudioMsg == null) {
														playMp3(imageByteRefill);
													} else {
														if (!mediaPlayerAudioMsg
																.isPlaying()) {
															playMp3(imageByteRefill);
														} else {
															stopVoiceMsg();
														}

													}
												}

											}
										} else {
											mp3Image.setImageResource(R.drawable.pause);

											byte[] imageByteRefill = Base64
													.decode(model.getText(), 0);
											if (imageByteRefill != null) {
												if (mediaPlayerAudioMsg == null) {
													playMp3(imageByteRefill);
												} else {
													if (!mediaPlayerAudioMsg
															.isPlaying()) {
														playMp3(imageByteRefill);
													} else {
														stopVoiceMsg();
													}

												}
											}
										}

									}
								});

							}

						} else {

							getError();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * @param model
		 */
		private void setEmoticons(final GetDetailByChildID model) {
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
					stopTimerForAudioPlay();
					stopVoiceMsg();
					ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

					Intent intent=new Intent(ChildPlayWallDetailActivity.this,ChildEmoticonDetailActivity.class);
					intent.putExtra("screen",1);
					intent.putExtra("color",color);
					intent.putExtra("actiontype",actionType);

					intent.putExtra("mapid",MapId);
					intent.putExtra("emoticid",emoticIdArray.get(0)+"");
					intent.putExtra("arrayEmotic", emoticIdArray);

					StaticVariables.emooticionNo=1;
					startActivity(intent);
					finish();
				}
			});
			layoutemojitwo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//GetFriendsTempleteMessageListByChildID model=(GetFriendsTempleteMessageListByChildID) v.getTag();
					stopTimerForAudioPlay();
					stopVoiceMsg();
					ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

					Intent intent=new Intent(ChildPlayWallDetailActivity.this,ChildEmoticonDetailActivity.class);
					intent.putExtra("screen",1);
					intent.putExtra("color",color);
					intent.putExtra("actiontype",actionType);
					intent.putExtra("mapid",MapId);
					intent.putExtra("emoticid",emoticIdArray.get(1)+"");
					intent.putExtra("arrayEmotic", emoticIdArray);

					StaticVariables.emooticionNo=2;

					startActivity(intent);
					finish();

				}
			});
			layoutemojithree.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					stopTimerForAudioPlay();
					stopVoiceMsg();
					ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

					Intent intent=new Intent(ChildPlayWallDetailActivity.this,ChildEmoticonDetailActivity.class);
					intent.putExtra("screen",1);
					intent.putExtra("color",color);
					intent.putExtra("actiontype",actionType);
					intent.putExtra("mapid",MapId);
					intent.putExtra("emoticid",emoticIdArray.get(2)+"");
					intent.putExtra("arrayEmotic", emoticIdArray);

					StaticVariables.emooticionNo=3;

					startActivity(intent);
					finish();

				}
			});
			layoutemojifour.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					stopTimerForAudioPlay();
					stopVoiceMsg();
					ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

					Intent intent=new Intent(ChildPlayWallDetailActivity.this,ChildEmoticonDetailActivity.class);
					intent.putExtra("screen",1);
					intent.putExtra("color",color);
					intent.putExtra("actiontype",actionType);
					intent.putExtra("mapid",MapId);
					intent.putExtra("emoticid",emoticIdArray.get(3)+"");
					intent.putExtra("arrayEmotic", emoticIdArray);

					StaticVariables.emooticionNo=4;

					startActivity(intent);
					finish();

				}
			});

			layoutemojifive.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					stopTimerForAudioPlay();
					stopVoiceMsg();
					ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

					Intent intent=new Intent(ChildPlayWallDetailActivity.this,ChildEmoticonDetailActivity.class);
					intent.putExtra("screen",1);
					intent.putExtra("color",color);
					intent.putExtra("actiontype",actionType);
					intent.putExtra("mapid",MapId);
					intent.putExtra("emoticid",emoticIdArray.get(4)+"");
					intent.putExtra("arrayEmotic", emoticIdArray);

					StaticVariables.emooticionNo=5;

					startActivity(intent);
					finish();

				}
			});
		}

	}

	private void getError() {
		/*
		 * Error err = serviceMethod.getError();
		 * showMessage.showAlert("Warning", err.getErrorDesc());
		 * layout_nodata.setVisibility(View.VISIBLE);
		 */
		layout_nodata.setVisibility(View.VISIBLE);
		// playwall_list.setVisibility(View.GONE);
		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();
		// showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectionimage.setImageResource(R.drawable.not_found);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText(err.getErrorDesc());
	}

	private void disposeSound() {
		try {

			if (childMusicPlayer != null) {
				childMusicPlayer.stop();
				childMusicPlayer.release();
				childMusicPlayer = null;
			}

			if (soundEffectButtonClicks != null) {
				soundEffectButtonClicks.release();
				soundEffectButtonClicks = null;
			}
			stopVoiceMsg();

			System.gc();
		} catch (Exception e) {
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
			Intent intent = new Intent(ChildPlayWallDetailActivity.this,
					ChildPlayWallActivity.class);

			startActivity(intent);
			ChildPlayWallDetailActivity.this.overridePendingTransition(
					R.anim.slide_in_left, R.anim.slide_out_right);

			finish();
		}
	}

	private void setProfileImage(GetDetailByChildID model) {
		if (model.getProfileImage() != null
				&& model.getProfileImage().trim().length() > 100) {
			byte[] imageByteParent = Base64.decode(model.getProfileImage(), 0);
			if (imageByteParent != null) {
				Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory
						.decodeByteArray(imageByteParent, 0,
								imageByteParent.length), dp2px(80), dp2px(80),
								false);
				child_buddies_image.setImageBitmap(bitmap);
			}
		} else {
			/*
			 * holder.child_buddies_image.setBackgroundResource(R.drawable.
			 * child_image); holder.child_buddies_image.setImageBitmap(null);
			 */
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.child_image),
					dp2px(80), dp2px(80), false);
			child_buddies_image.setImageBitmap(bitmap);
		}

		if (model.getProfileImage() != null
				&& model.getProfileImage().trim().length() > 100) {
			byte[] imageByteParent = Base64.decode(model.getProfileImage(), 0);
			if (imageByteParent != null) {
				Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory
						.decodeByteArray(imageByteParent, 0,
								imageByteParent.length), dp2px(80), dp2px(80),
								false);
				child_buddies.setImageBitmap(bitmap);
			}
		} else {
			/*
			 * holder.child_buddies.setBackgroundResource(R.drawable.child_image)
			 * ; holder.child_buddies.setImageBitmap(null);
			 */
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.child_image),
					dp2px(80), dp2px(80), false);
			child_buddies.setImageBitmap(bitmap);
		}
	}

	private void playMp3(byte[] mp3SoundByteArray) {
		try {
			// create temp file that will hold byte array
			File tempMp3 = File
					.createTempFile("playwall", "mp3", getCacheDir());
			tempMp3.deleteOnExit();
			FileOutputStream fos = new FileOutputStream(tempMp3);
			fos.write(mp3SoundByteArray);
			fos.close();

			// Tried reusing instance of media player
			// but that resulted in system crashes...
			mediaPlayerAudioMsg = new MediaPlayer();

			// Tried passing path directly, but kept getting
			// "Prepare failed.: status=0x1"
			// so using file descriptor instead
			FileInputStream fis = new FileInputStream(tempMp3);
			mediaPlayerAudioMsg.setDataSource(fis.getFD());

			mediaPlayerAudioMsg.prepare();
			mediaPlayerAudioMsg.start();
			mediaPlayerAudioMsg
			.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stopTimerForAudioPlay();
					mp3Image.setImageResource(R.drawable.play_i);
					stopVoiceMsg();
					// stopTimerForAudioPlay();
				}
			});
			playDuration = mediaPlayerAudioMsg.getDuration() / 1000;
			String seconds = String.format("%02d", playDuration % 60);
			String minutes = String.format("%02d", playDuration / 60);
			seekBar.setClickable(false);
			seekBar.setEnabled(false);
			seekBar.setMax(playDuration);
			seekBar.setProgress(Integer.parseInt("0"));
			songDuration.setVisibility(View.VISIBLE);
			songDuration.setText(minutes + ":" + seconds);
			startTimerForAudioPlay();

		} catch (IOException ex) {
			String s = ex.toString();
			ex.printStackTrace();
		}
	}

	private void stopVoiceMsg() {
		if (mediaPlayerAudioMsg != null) {
			mediaPlayerAudioMsg.stop();
			mediaPlayerAudioMsg.release();
			mediaPlayerAudioMsg = null;
		}
		seekBar.setProgress(0);
		String seconds = String.format("%02d", playDuration % 60);
		String minutes = String.format("%02d", playDuration / 60);
		songDuration.setText(minutes + ":" + seconds);
		startProgress = 0;

	}

	private void startTimerForAudioPlay() {
		startValue = playDuration;// 120sec
		min = playDuration / 60;// 2min
		// stopTimerForAudioPlay();
		runnableForPlaying = new Runnable() {
			public void run() {
				if (startValue > 0) {
					--startValue;
					// seekBar.setProgress(startValue);
					if (startValue > 0) {
						startProgress++;
						seekBar.setProgress(startProgress);
					} else {
					}

					if (startValue >= 60) {
						min = startValue / 60;// 1
						startValue %= 60;// 59
					}

					String seconds = String.format("%02d", startValue);
					String minutes = String.format("%02d", min);
					songDuration.setText(minutes + ":" + seconds);
					if (handlerForPlaying != null)
						handlerForPlaying.postDelayed(runnableForPlaying, 1000);
				} else {

				}
			}
		};

		handlerForPlaying = new Handler();
		handlerForPlaying.postDelayed(runnableForPlaying, 1000);
	}

	private int dp2px(int dp) {

		if (SplashActivity.ScreenWidth >= 2000) {
			dp = 60;

		}

		else if (SplashActivity.ScreenWidth >= 1000) {
			dp = 55;

		} else if (SplashActivity.ScreenWidth >= 700) {
			dp = 50;

		} else if (SplashActivity.ScreenWidth >= 590) {
			dp = 80;
		} else {
			dp = 50;
		}
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());

	}

	private void stopTimerForAudioPlay() {
		if (handlerForPlaying != null) {
			handlerForPlaying.removeCallbacks(runnableForPlaying);
			handlerForPlaying = null;
		}
	}


	/**
	 * @param model
	 * @return
	 */
	private ArrayList<String> getEmoticCountArray(
			GetDetailByChildID model) {
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
	/**
	 * @param model
	 * @return
	 */
	private ArrayList<Integer> getEmoticIdArray(
			GetDetailByChildID model) {
		ArrayList<Integer> emoticIdArray=new ArrayList<Integer>();
		if(model.getEmoticID()==null || model.getEmoticID().equalsIgnoreCase(""))
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


}
