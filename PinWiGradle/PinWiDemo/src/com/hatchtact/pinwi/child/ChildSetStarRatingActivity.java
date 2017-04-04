package com.hatchtact.pinwi.child;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class ChildSetStarRatingActivity extends Activity
{

	private ServiceMethod serviceMethod=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharepref = null;

	private LinearLayout child_subject_indicator;
	private LinearLayout child_star_layout;
	private RelativeLayout child_set_star_pinwi_layout;
	private ImageView child_pinwheel;
	private ImageView child_rating_confirm;


	private ImageView child_pinwheel_arrow;
	private ImageView child_skip_arrow;


	private ImageView child_1_rating_selected;
	private ImageView child_2_rating_selected;
	private ImageView child_3_rating_selected;
	private ImageView child_4_rating_selected;
	private ImageView child_5_rating_selected;
	private ImageView child_6_rating_selected;
	private ImageView child_7_rating_selected;
	private ImageView child_8_rating_selected;
	private ImageView child_9_rating_selected;
	private ImageView child_10_rating_selected;

	private TextView child_header_title;
	private ImageView child_header_music;
	private ImageView  child_star_header_music_voiceover;

	private ImageView child_smiley;


	private static Bitmap imageOriginal = null,imageScaled = null;
	private static Matrix matrix;
	private int pinwiHeight, pinwiWidth;
	//private GestureDetector detector;
	// needed for detecting the inversed rotations
	private boolean[] quadrantTouched;

	/** StarCounter: 1-10, for star rating */
	private int starCounter, waitCnt;
	private boolean isDoneRating = false;

	private ArrayList<ImageView> star;

	private ArrayList<ImageView> rate;

	private Builder builder_Skip;

	private int screenWidth;
	private int screenHeight;
	private float ScreenWidthFactor ;
	private float ScreenHeightFactor ;
	private int subject;
	private int subjectRatingDoneCount = 0;

	private int ParentId;

	private String ratingDataToSend="";
	private TypeFace typeFace;



	private SoundEffect soundEffectStarAwarded = null;
	//private SoundEffect soundEffectPinwheel = null;
	private SoundEffect soundEffectButtonClicks = null;
	//private SoundEffect soundEffectTransition = null;

	private Integer[] arrayChildSmiley={R.drawable.child_smiley1,R.drawable.child_smiley2,R.drawable.child_smiley3,R.drawable.child_smiley4,
			R.drawable.child_smiley4,R.drawable.child_smiley5,R.drawable.child_smiley6,R.drawable.child_smiley7,
			R.drawable.child_smiley8,R.drawable.child_smiley9,R.drawable.child_smiley10};

	private Animation animBlink;
	private ChildMusicPlayer musicForFirstSubject = null;
	private ChildMusicPlayer musicForSecondSubject = null;
	private ChildMusicPlayer musicForSkip_No_No = null;

	private boolean isMute = false;

	private boolean isMusicStop = false;

	private boolean isFromPendingScreen=false;
	private CustomLoader customProgressLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_child_set_star_rating);
		customProgressLoader=new CustomLoader(this);
		getDisplayHeight(ChildSetStarRatingActivity.this);
		getDisplayWidth(ChildSetStarRatingActivity.this);

		typeFace = new TypeFace(ChildSetStarRatingActivity.this);
		sharepref = new SharePreferenceClass(ChildSetStarRatingActivity.this);
		isMute = sharepref.isSound(StaticVariables.currentChild.getChildID() + "");
		isMusicStop = sharepref.isVoiceOver(StaticVariables.currentChild.getChildID() + "");

		initSoundData();
		initHeaderItems();
		initialiseData();
		initialiseSubjectView();
		setDataForViews();
		setTouchListeners();
		generateSkipDialog();

		playSound(AccessProfileActivity.soundEffectTransition);

	}



	private void initSoundData()
	{
		// TODO Auto-generated method stub
		//soundEffectTransition = new SoundEffect(ChildSetStarRatingActivity.this, R.raw.pageflip);
		soundEffectStarAwarded = new SoundEffect(ChildSetStarRatingActivity.this, R.raw.wheel_rotation);
		soundEffectButtonClicks = new SoundEffect(ChildSetStarRatingActivity.this, R.raw.two_tone_nav);

		musicForFirstSubject = new ChildMusicPlayer(AccessProfileActivity.getInstance(), R.raw.voice1);
		musicForSecondSubject = new ChildMusicPlayer(AccessProfileActivity.getInstance(), R.raw.voice3);

		if(StaticVariables.isTutorialSoundEnabled)
			playMusic(musicForFirstSubject);

	}

	private void playSound(SoundEffect sound)
	{
		if(!isMute && sound!=null)
		{
			sound.play(1.0f);
		}
	}


	private void initHeaderItems()
	{

		child_header_title = (TextView)findViewById(R.id.child_header_title);
		child_header_title.setVisibility(View.VISIBLE);
		typeFace.setTypefaceGotham(child_header_title);

		child_header_music = (ImageView) findViewById(R.id.child_star_header_music);
		isMute = sharepref.isSound(StaticVariables.currentChild.getChildID() + "");
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

		child_star_header_music_voiceover  = (ImageView) findViewById(R.id.child_star_header_music_voiceover);
		setVoiceOverIcon();
		/*
		if(!StaticVariables.isTutorialSoundEnabled)
		{
			child_star_header_music_voiceover.setVisibility(View.GONE);
		}
		else*/
		{
			child_star_header_music_voiceover.setOnClickListener(new OnClickListener() {


				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					isMusicStop = !isMusicStop ;

					sharepref.setVoiceOvers(isMusicStop,StaticVariables.currentChild.getChildID() + "");
					if(isMusicStop)
					{
						soundEffectButtonClicks.play(1.0f);
						if(musicForFirstSubject!=null)
						{
							if(musicForFirstSubject.getMediaPlayer().isPlaying())
							{
								musicForFirstSubject.getMediaPlayer().stop();
							}
						}

						if(musicForSecondSubject!=null)
						{
							if(musicForSecondSubject.getMediaPlayer().isPlaying())
							{
								musicForSecondSubject.getMediaPlayer().stop();
							}
						}

						if(musicForSkip_No_No!=null)
						{
							if(musicForSkip_No_No.getMediaPlayer().isPlaying())
							{
								musicForSkip_No_No.getMediaPlayer().stop();
							}
						}
					}
					setVoiceOverIcon();

				}
			});
		}
	}


	private void initialiseData()
	{
		// TODO Auto-generated method stub

		try {
			Bundle bundle = getIntent().getExtras();
			ParentId = bundle.getInt("ParentId");
			isFromPendingScreen=bundle.getBoolean("Pendingflag");
			//isFromPendingScreen=false;//this will be removed when backpress functionality
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildSetStarRatingActivity.this);
		serviceMethod=new ServiceMethod();

		animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

		child_subject_indicator = (LinearLayout) findViewById(R.id.child_subject_indicator);
		child_star_layout = (LinearLayout) findViewById(R.id.child_star_layout);
		child_set_star_pinwi_layout = (RelativeLayout) findViewById(R.id.child_set_star_pinwi_layout);

		child_pinwheel_arrow = (ImageView) findViewById(R.id.child_pinwheel_arrow);
		child_pinwheel_arrow.setVisibility(View.VISIBLE);
		child_pinwheel_arrow.startAnimation(animBlink);

		child_pinwheel_arrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				replacePinwheelArrorWithSkipArror();


			}
		});

		child_skip_arrow = (ImageView) findViewById(R.id.child_skip_arrow);
		child_skip_arrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				child_skip_arrow.setVisibility(View.GONE);
			}
		});
		child_skip_arrow.setVisibility(View.GONE);


		child_pinwheel = (ImageView) findViewById(R.id.child_pinwheel);
		child_rating_confirm = (ImageView) findViewById(R.id.child_rating_confirm);

		child_1_rating_selected = (ImageView) findViewById(R.id.child_1_rating_selected);
		child_2_rating_selected = (ImageView) findViewById(R.id.child_2_rating_selected);
		child_3_rating_selected = (ImageView) findViewById(R.id.child_3_rating_selected);
		child_4_rating_selected = (ImageView) findViewById(R.id.child_4_rating_selected);
		child_5_rating_selected = (ImageView) findViewById(R.id.child_5_rating_selected);
		child_6_rating_selected = (ImageView) findViewById(R.id.child_6_rating_selected);
		child_7_rating_selected = (ImageView) findViewById(R.id.child_7_rating_selected);
		child_8_rating_selected = (ImageView) findViewById(R.id.child_8_rating_selected);
		child_9_rating_selected = (ImageView) findViewById(R.id.child_9_rating_selected);
		child_10_rating_selected = (ImageView) findViewById(R.id.child_10_rating_selected);

		child_smiley = (ImageView) findViewById(R.id.child_smiley);

		star = new ArrayList<ImageView>();
		star.add(child_1_rating_selected);
		star.add(child_2_rating_selected);
		star.add(child_3_rating_selected);
		star.add(child_4_rating_selected);
		star.add(child_5_rating_selected);
		star.add(child_6_rating_selected);
		star.add(child_7_rating_selected);
		star.add(child_8_rating_selected);
		star.add(child_9_rating_selected);
		star.add(child_10_rating_selected);

		// there is no 0th quadrant, to keep it simple the first value gets ignored
		quadrantTouched = new boolean[] { false, false, false, false, false };

		// initialize the matrix only once
		if (matrix == null) {
			matrix = new Matrix();
		} else {
			// not needed, you can also post the matrix immediately to restore the old state
			matrix.reset();
		}

		subject = AccessProfileActivity.subjectsScheduled.size(); //self.allData.count;   Subject count
		subjectRatingDoneCount = 0;

	}

	private void setDataForViews()
	{
		// TODO Auto-generated method stub

		// load the image only once
		if (imageOriginal == null) {
			imageOriginal = ((BitmapDrawable)child_pinwheel.getDrawable()).getBitmap();
		}


		setInitialStarRating();

	}

	private void playMusic(ChildMusicPlayer music)
	{
		if(music !=null )
		{
			if(!isMusicStop)
			{
				music.play();
			}
			else
			{
				music.pause();
			}
		}
	}



	private void setInitialStarRating()
	{
		//initially 0...when 1st subject...subjectRatingDoneCount incremented in function end
		if(subjectRatingDoneCount == 1 && StaticVariables.isTutorialSoundEnabled)
		{
			playMusic(musicForSecondSubject);
		}
		for(int i=0;i<rate.size();i++)
		{
			if(i<subjectRatingDoneCount)
			{
				rate.get(i).setBackgroundColor(Color.GREEN);
				rate.get(i).setAlpha(0.5f);
			}
		}

		child_header_title.setText(AccessProfileActivity.subjectsScheduled.get(subjectRatingDoneCount).getSubjectName());

		++subjectRatingDoneCount;
		isDoneRating = false;
		starCounter = 0;
		waitCnt = 0;

		child_rating_confirm.setBackgroundResource(R.drawable.child_skip_rating);
		child_smiley.setImageResource(arrayChildSmiley[0]);
		for(int i=0;i<star.size();i++)
		{
			star.get(i).setAlpha(0.4f);
		}
	}


	private void stopFirstSubjectSounds()
	{
		// TODO Auto-generated method stub
		if(musicForFirstSubject!=null)
		{
			musicForFirstSubject.stop();
			musicForFirstSubject.release();
			musicForFirstSubject = null;
		}


		if(musicForSkip_No_No!=null)
		{
			musicForSkip_No_No.stop();
			musicForSkip_No_No.release();
			musicForSkip_No_No = null;
		}

	}



	private void setTouchListeners()
	{
		// TODO Auto-generated method stub
	//	detector = new GestureDetector(this, new MyGestureDetector());

		child_pinwheel.setOnTouchListener(new MyOnTouchListener());
		child_pinwheel.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// method called more than once, but the values only need to be initialized one time
				if (pinwiHeight == 0 || pinwiWidth == 0) {
					pinwiHeight = child_pinwheel.getHeight();
					pinwiWidth = child_pinwheel.getWidth();

					// resize
					Matrix resize = new Matrix();
					resize.postScale((float)Math.min(pinwiWidth, pinwiHeight) / (float)imageOriginal.getWidth(), (float)Math.min(pinwiWidth, pinwiHeight) / (float)imageOriginal.getHeight());
					imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0, imageOriginal.getWidth(), imageOriginal.getHeight(), resize, false);

					// translate to the image view's center
					float translateX = pinwiWidth / 2 - imageOriginal.getWidth() / 2;
					float translateY = pinwiHeight / 2 - imageOriginal.getHeight() / 2;
					matrix.postTranslate(translateX, translateY);

					child_pinwheel.setImageBitmap(imageOriginal);
					child_pinwheel.setImageMatrix(matrix);

				}
			}
		});

		child_rating_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(subjectRatingDoneCount == 1)
				{
					child_pinwheel_arrow.clearAnimation();
					child_pinwheel_arrow.setVisibility(View.GONE);
					child_skip_arrow.clearAnimation();
					child_skip_arrow.setVisibility(View.GONE);
				}

				playSound(soundEffectButtonClicks);

				if(isDoneRating)
				{
					//Rating has been done...move to next subject if any..or else show rating reward screen
					ratingDoneNowMoveToNextStep(false);
				}
				else
				{
					//for 1st subject star rating only
					if(subjectRatingDoneCount == 1 && StaticVariables.isTutorialSoundEnabled)
					{
						if(musicForFirstSubject!=null)
						{
							musicForFirstSubject.stop();
							musicForFirstSubject.release();
							musicForFirstSubject = null;
						}

						musicForSkip_No_No =  new ChildMusicPlayer(AccessProfileActivity.getInstance(), R.raw.voice2);
						playMusic(musicForSkip_No_No);
					}

					//show alert
					builder_Skip.show();
				}
			}
		});
	}

	private void generateSkipDialog()
	{
		builder_Skip = new AlertDialog.Builder(ChildSetStarRatingActivity.this);
		builder_Skip.setMessage("Do you want to skip this activity?");
		builder_Skip.setTitle("Skip Activity");
		builder_Skip.setCancelable(true);
		builder_Skip.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				//for 1st subject star rating only
				if(subjectRatingDoneCount == 1 && StaticVariables.isTutorialSoundEnabled)
				{
					if(musicForSkip_No_No !=null)
					{
						musicForSkip_No_No.stop();
						musicForSkip_No_No.release();
						musicForSkip_No_No = null;
					}

				}

				playSound(soundEffectButtonClicks);
				ratingDoneNowMoveToNextStep(true);
			}

		});

		builder_Skip.setNegativeButton("No",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// TODO Auto-generated method stub
				if(subjectRatingDoneCount == 1 && StaticVariables.isTutorialSoundEnabled)
				{
					if(musicForSkip_No_No !=null)
					{
						if(musicForSkip_No_No.getMediaPlayer().isPlaying())
							musicForSkip_No_No.stop();
					}

				}

				playSound(soundEffectButtonClicks);
			}
		});
	}
	protected void ratingDoneNowMoveToNextStep(boolean isSkipped)
	{
		// TODO Auto-generated method stub
		if(subjectRatingDoneCount == 1 && StaticVariables.isTutorialSoundEnabled)
		{
			stopFirstSubjectSounds();
		}

		//if(!isSkipped)
		{
			if(AccessProfileActivity.subjectsScheduled!=null)
				ratingDataToSend+=AccessProfileActivity.subjectsScheduled.get(subjectRatingDoneCount-1).getSubjectID()+"-"+(starCounter)+",";
		}

		if(subjectRatingDoneCount == subject)
		{
			if(ratingDataToSend.endsWith(","))
			{
				ratingDataToSend = ratingDataToSend.substring(0, ratingDataToSend.length()-1);
			}
			//start Web Service....
			new AddActivityRating(StaticVariables.currentChild.getChildID()).execute();

		}
		else
		{
			playSound(AccessProfileActivity.soundEffectTransition);
			setInitialStarRating();
		}


	}

	/**
	 * Simple implementation of an {@link OnTouchListener} for registering the pinwi's touch events. 
	 */
	private class MyOnTouchListener implements OnTouchListener {

		private double startAngle;

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if(subjectRatingDoneCount == 1)
			{
				replacePinwheelArrorWithSkipArror();
			}


			//playSound(soundEffectPinwheel);

			final float xc = pinwiWidth/ 2;
			final float yc = pinwiHeight/ 2;

			final float x = event.getX();
			final float y = event.getY();
			switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					startAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));



					// reset the touched quadrants
					//   for (int i = 0; i < quadrantTouched.length; i++) {
					//      quadrantTouched[i] = false;
					//  }
					//startAngle = getAngle(event.getX(), event.getY());
					break;

				case MotionEvent.ACTION_MOVE:


					double currentAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));

					//double currentAngle = getAngle(event.getX(), event.getY());
					//rotatePinWheel((float) (startAngle - currentAngle ));
					rotatePinWheel((float) (currentAngle - startAngle ));
					startAngle = currentAngle;
					break;

				case MotionEvent.ACTION_UP:

					break;
			}

			// set the touched quadrant to true
			quadrantTouched[getQuadrant(event.getX() - (pinwiWidth / 2), pinwiHeight - event.getY() - (pinwiHeight / 2))] = true;

			//detector.onTouchEvent(event);

			return true;
		}



	}

	private void replacePinwheelArrorWithSkipArror()
	{
		child_pinwheel_arrow.clearAnimation();
		child_pinwheel_arrow.setVisibility(View.GONE);
		child_skip_arrow.setVisibility(View.VISIBLE);
		child_skip_arrow.setAnimation(animBlink);
	}

	/**
	 * Simple implementation of a {@link SimpleOnGestureListener} for detecting a fling event. 
	 */
	private class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

			// get the quadrant of the start and the end of the fling
			int q1 = getQuadrant(e1.getX() - (pinwiWidth / 2), pinwiHeight - e1.getY() - (pinwiHeight / 2));
			int q2 = getQuadrant(e2.getX() - (pinwiWidth / 2), pinwiHeight - e2.getY() - (pinwiHeight / 2));

			// the inversed rotations
			if ((q1 == 2 && q2 == 2 && Math.abs(velocityX) < Math.abs(velocityY))
					|| (q1 == 3 && q2 == 3)
					|| (q1 == 1 && q2 == 3)
					|| (q1 == 4 && q2 == 4 && Math.abs(velocityX) > Math.abs(velocityY))
					|| ((q1 == 2 && q2 == 3) || (q1 == 3 && q2 == 2))
					|| ((q1 == 3 && q2 == 4) || (q1 == 4 && q2 == 3))
					|| (q1 == 2 && q2 == 4 && quadrantTouched[3])
					|| (q1 == 4 && q2 == 2 && quadrantTouched[3])) {

				child_pinwheel.post(new FlingRunnable(-1 * (velocityX + velocityY)));
			} else {
				// the normal rotation
				child_pinwheel.post(new FlingRunnable(velocityX + velocityY));
			}


			return false;
		}
	}

	/**
	 * A {@link Runnable} for animating the the dialer's fling.
	 */
	private class FlingRunnable implements Runnable {

		private float velocity;

		public FlingRunnable(float velocity) {
			this.velocity = velocity;
		}

		@Override
		public void run() {
			if (Math.abs(velocity) > 5) {
				rotatePinWheel(velocity / 75);
				velocity /= 1.0666F;

				// post this instance again
				child_pinwheel.post(this);
			}
		}
	}

	//	/**
	//	 * @return The angle of the unit circle with the image view's center
	//	 */
	//	private double getAngle(double xTouch, double yTouch) {
	//		double x = xTouch - (pinwiWidth / 2d);
	//		double y = pinwiHeight - yTouch - (pinwiHeight / 2d);
	//
	//		switch (getQuadrant(x, y)) {
	//		case 1:
	//			return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
	//		case 2:
	//			return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
	//		case 3:
	//			return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	//		case 4:
	//			return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
	//		default:
	//			return 0;
	//		}
	//	}

	/**
	 * @return The selected quadrant.
	 */
	private static int getQuadrant(double x, double y) {
		if (x >= 0) {
			return y >= 0 ? 1 : 4;
		} else {
			return y >= 0 ? 2 : 3;
		}
	}

	/**
	 * Rotate the PinWheel.
	 *
	 * @param degrees The degrees, the pinwi should get rotated.
	 */
	private void rotatePinWheel(float degrees)
	{
		try{
			String sign = degrees+"";
			if(sign.startsWith("-"))
			{
				//decrement the star rating
				waitCnt--;
				if(waitCnt == -10 && starCounter >=0)
				{
					if(starCounter >0)
						playSound(soundEffectStarAwarded);

					star.get(starCounter).setAlpha(0.4f);
					waitCnt=0;
					starCounter--;
					if(starCounter <0)
					{
						starCounter=0;
						star.get(0).setAlpha(0.4f);
					}
				}

				if(starCounter <= 0)
				{
					star.get(0).setAlpha(0.4f);
					isDoneRating = false;
					child_rating_confirm.setBackgroundResource(R.drawable.child_skip_rating);
				}
			}
			else if(degrees>=.6f)
			{
				//increment the star rating
				waitCnt++;
				if(waitCnt == 10 &&  starCounter < 10)
				{
					if(starCounter <9)
						playSound(soundEffectStarAwarded);
					star.get(starCounter).setAlpha(1.0f);
					waitCnt=0;
					starCounter++;
					if(starCounter >9){
						starCounter=9;
					}

				}
				if(starCounter>0)
				{
					child_rating_confirm.setBackgroundResource(R.drawable.child_done_rating);
					isDoneRating = true;
				}
			}


			child_smiley.setImageResource(arrayChildSmiley[starCounter]);

			//playSoundPinWheel();
			matrix.postRotate(degrees, pinwiWidth / 2, pinwiHeight / 2);
			child_pinwheel.setImageMatrix(matrix);
			System.out.println("Star rating: "+starCounter);
		}
		catch(OutOfMemoryError e)
		{
			e.printStackTrace();
		}
	}

	private void initialiseSubjectView()
	{

		int x=screenWidth/20;
		rate = new ArrayList<ImageView>();
		float width = screenWidth/subject - 8*ScreenWidthFactor;

		if(width>20*ScreenWidthFactor)
		{
			width=20*ScreenWidthFactor;
		}
		x=(int) (14*ScreenWidthFactor);

		for (int i=0; i<subject; i++)
		{
			ImageView iv = new ImageView(ChildSetStarRatingActivity.this);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)width,
					(int)(8*ScreenHeightFactor));
			lp.setMargins(5, 5, 5, 5);

			iv.setLayoutParams(lp);

			//iv.setLayoutParams(new LinearLayout.LayoutParams(width, 3*ScreenHeightFactor));
			iv.setBackgroundColor(Color.WHITE);
			iv.setAlpha(0.2f);

			rate.add(iv);
			child_subject_indicator.addView(iv);
			x+=width+5*ScreenWidthFactor;
		}


	}

	private void getDisplayWidth(Activity a)
	{

		Display display = a.getWindowManager().getDefaultDisplay();

		// creating an empty Point so that the compiler
		// does not complain about null reference
		Point displaySize = new Point();
		display.getSize(displaySize);
		screenWidth = displaySize.x;
		ScreenWidthFactor = screenWidth/320;

	}

	private void getDisplayHeight(Activity a) {

		Display display = a.getWindowManager().getDefaultDisplay();

		// creating an empty Point so that the compiler
		// does not complain about null reference
		Point displaySize = new Point();
		display.getSize(displaySize);
		screenHeight = displaySize.y;
		ScreenHeightFactor =screenHeight/568;

	}


	//private ProgressDialog progressDialog=null;

	private class AddActivityRating extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public AddActivityRating(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
		/*	progressDialog = ProgressDialog.show(ChildSetStarRatingActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildSetStarRatingActivity.this))
			{
				ErrorCode = serviceMethod.sendChildStarRating(childID, ratingDataToSend);
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
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
				customProgressLoader.dismissProgressBar();
				if(result==-1)
				{
					showMessage.showToastMessage("Please check your network connection");

					if(checkNetwork.checkNetworkConnection(ChildSetStarRatingActivity.this))
						new AddActivityRating(childID).execute();
				}
				else
				{
					if(result == 0)
					{
						disposeSound();
						finish();
						Intent intent = new Intent(ChildSetStarRatingActivity.this, ChildStarEarnedPointActivity.class);
						startActivity(intent);
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

	}

	private void getError()
	{
		Error err = serviceMethod.getError();
		//showMessage.showAlert("Warning", err.getErrorDesc());
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChildSetStarRatingActivity.this);

		alertBuilder.setTitle("Warning");
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(err.getErrorDesc());
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				isFromPendingScreen=true;
				finishActivity();
			}
		});
		alertBuilder.show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finishActivity();
	}



	/**
	 *
	 */
	private void finishActivity() {
		playSound(soundEffectButtonClicks);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disposeSound();
		if(!isFromPendingScreen)
		{
			Intent intent = new Intent(ChildSetStarRatingActivity.this, AccessProfileActivity.class);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(ChildSetStarRatingActivity.this, ChildMainDashboardActivity.class);
			startActivity(intent);
			/*Intent intent = new Intent(ChildSetStarRatingActivity.this, ChildDashboardActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("ParentId", StaticVariables.currentParentId);
			intent.putExtras(bundle);
			startActivity(intent);*/
		}

		finish();
	}


	private void disposeSound()
	{
		try{

			stopFirstSubjectSounds();

			if(musicForSecondSubject!=null)
			{
				musicForSecondSubject.stop();
				musicForSecondSubject.release();
				musicForSecondSubject = null;
			}


			if(soundEffectStarAwarded!=null){
				soundEffectStarAwarded.release();
				soundEffectStarAwarded = null;
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

	private void setVoiceOverIcon() {
		if(isMusicStop)
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
			{
				child_star_header_music_voiceover.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_voiceovermute));

			} else
			{
				child_star_header_music_voiceover.setBackground(getResources().getDrawable(R.drawable.child_voiceovermute));

			}
		}
		else
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
			{
				child_star_header_music_voiceover.setBackgroundDrawable(getResources().getDrawable(R.drawable.child_voiceover));

			} else
			{
				child_star_header_music_voiceover.setBackground(getResources().getDrawable(R.drawable.child_voiceover));

			}
		}
	}
}
