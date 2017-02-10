package com.hatchtact.pinwi.child;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.SubjectActivities;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildTutorialActivity extends FragmentActivity
{
	ViewPager viewpager;
	//private ImageView apptutorialimage;
	private ChildTutorialScreenSlidePagerAdapter mPagerAdapter;
	TypeFace typefaceClass;
	private SharePreferenceClass sharePreferenceClass=null;
	private ChildMusicPlayer childMusicPlayer = null;


	private Integer[] arrayChildTutorialSound={R.raw.vo1_1,R.raw.vo1_2,R.raw.vo1_3,R.raw.vo1_4,
			R.raw.vo1_5,R.raw.vo1_6,R.raw.vo1_7,R.raw.vo1_8};


	/*New Changes*/
	private TextView textHeadingAppTutorial;
	private ImageView imageAppTutorial;
	private Integer[] arrayImages={R.drawable.frame_hi,R.drawable.frame_trophy,R.drawable.frame_hi,R.drawable.frame_hi,R.drawable.frame_trophy,R.drawable.frame_hi};
	private Integer[] arraySoundForImages={R.raw.vo9,R.raw.vo7,R.raw.vo8,R.raw.vo9,
			R.raw.vo7,R.raw.vo8_1};//8,7,9,8.1,7,9

	private String[] textScreens={"NO ACTIVITIES TO RATE!!",
			"GREAT JOB RATING ACTIVITIES!!",
			"RATE ACTIVTIES FOR TODAY",
			"NO ACTIVITIES TO RATE!!",
			"GREAT JOB RATING ACTIVITIES!!",
			"RATE ACTIVITIES FOR YESTERDAY"
	};
	/*New Changes*/

	private ServiceMethod serviceMethod=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;



	private boolean isMusicStop = false;
	private TextView btnNext;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		sharePreferenceClass=new SharePreferenceClass(ChildTutorialActivity.this);
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ChildTutorialActivity.this);
		serviceMethod=new ServiceMethod();

		isMusicStop=sharePreferenceClass.isVoiceOver(StaticVariables.currentChild.getChildID() + "");

		if(!sharePreferenceClass.getIsChildTutorialDone(StaticVariables.currentChild.getChildID()))
		{
			sharePreferenceClass.setIsChildTutorialDone(StaticVariables.currentChild.getChildID());

			setContentView(R.layout.activity_screen_slide);
			typefaceClass=new TypeFace(ChildTutorialActivity.this);

			viewpager=(ViewPager) findViewById(R.id.pager);

			mPagerAdapter = new ChildTutorialScreenSlidePagerAdapter(getSupportFragmentManager());
			viewpager.setAdapter(mPagerAdapter);

			childMusicPlayer = new ChildMusicPlayer(AccessProfileActivity.getInstance(), arrayChildTutorialSound[0]);
			childMusicPlayer.play();
			childMusicPlayer.getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					viewpager.setCurrentItem(1, true);
					mPagerAdapter.getItem(1);


				}
			});

			viewpager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(final int position) {
					// TODO Auto-generated method stub
					if(childMusicPlayer != null)
					{
						childMusicPlayer.stop();
						childMusicPlayer.release();
						childMusicPlayer = null;
					}

					System.gc();

					childMusicPlayer = new ChildMusicPlayer(AccessProfileActivity.getInstance(), arrayChildTutorialSound[position]);
					childMusicPlayer.play();
					childMusicPlayer.getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							if(position != (arrayChildTutorialSound.length - 1))
							{
								mPagerAdapter.getItem(position+1);
								viewpager.setCurrentItem(position+1, true);

							}
							else
							{
								//finishActivity();
							}

						}
					});
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});

		}
		else
		{
			setContentView(R.layout.apptutorial);
			imageAppTutorial=(ImageView) findViewById(R.id.imageAppTutorial);
			textHeadingAppTutorial=(TextView) findViewById(R.id.textHeadingAppTutorial);
			typefaceClass=new TypeFace(ChildTutorialActivity.this);
			typefaceClass.setTypefaceRegular(textHeadingAppTutorial);
			//apptutorialimage = (ImageView) findViewById(R.id.apptutorialimage);
			imageAppTutorial.setImageResource(arrayImages[StaticVariables.statusChild]);
			textHeadingAppTutorial.setText(textScreens[StaticVariables.statusChild]);
			childMusicPlayer = new ChildMusicPlayer(AccessProfileActivity.getInstance(), arraySoundForImages[StaticVariables.statusChild]);

			playMusic();
			
			btnNext=(TextView)findViewById(R.id.imageNext);
			btnNext.setVisibility(View.VISIBLE);
			typefaceClass.setTypefaceRegular(textHeadingAppTutorial);

			btnNext.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onBackPressed();
				}
			});

			childMusicPlayer.getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					finishActivity();
				}
			});
		}
	}


	/**
	 * 
	 */
	private void playMusic() {
		if(childMusicPlayer !=null )
		{
			if(!isMusicStop)
			{
				childMusicPlayer.play();
			}
			else
			{
				childMusicPlayer.pause();
			}
		}
	}


	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		finishActivity();

	}


	private void finishActivity() {
		if(childMusicPlayer != null)
		{
			childMusicPlayer.stop();
			childMusicPlayer.release();
			childMusicPlayer = null;
		}
		System.gc();

		if(StaticVariables.statusChild==2 || StaticVariables.statusChild==5)
		{
			if(StaticVariables.statusChild==2)
			{
				//issue may arise when scheduled subject size is zero
				if(AccessProfileActivity.subjectsScheduled.size()>0)
				{
					Intent intent = new Intent(ChildTutorialActivity.this, ChildSetStarRatingActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("ParentId", StaticVariables.currentParentId);
					intent.putExtras(bundle);
					startActivity(intent);
					ChildTutorialActivity.this.finish();
				}
			}
			else
			{
				callingWebServiceForChildData("0");
			}
		}
		else 
		{
			//Intent intent = new Intent(ChildTutorialActivity.this, ChildDashboardActivity.class);
			/*Bundle bundle = new Bundle();
			bundle.putInt("ParentId", StaticVariables.currentParentId);
			intent.putExtras(bundle);*/
			Intent intent = new Intent(ChildTutorialActivity.this, ChildMainDashboardActivity.class);
			startActivity(intent);
			ChildTutorialActivity.this.finish();
		}





	}




	/**  ---------------------Child Module Methods-----------------------   */
	private void callingWebServiceForChildData(final String daysAgo)
	{
		// TODO Auto-generated method stub
		if(AccessProfileActivity.subjectsFetched!=null)
			AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().clear();

		if(AccessProfileActivity.subjectsFetchedAfterSchool!=null)
			AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().clear();

		AccessProfileActivity.subjectsScheduled.clear();

		new GetChildAfterSchoolActiviesByDayForChildModule(StaticVariables.currentChild.getChildID(),daysAgo).execute();

	}

	private ProgressDialog progressDialog=null;
	private class GetChildAfterSchoolActiviesByDayForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		private String daysAgo;

		public GetChildAfterSchoolActiviesByDayForChildModule(int childID,String daysAgo)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
			this.daysAgo = daysAgo;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(ChildTutorialActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildTutorialActivity.this))
			{
				ErrorCode = serviceMethod.getChildAfterSchoolActiviesByDayForChildModule(childID,daysAgo);
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

			/*try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				try {
					if (progressDialog.isShowing())
						progressDialog.cancel();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(checkNetwork.checkNetworkConnection(ChildTutorialActivity.this))
					new GetChildAfterSchoolActiviesByDayForChildModule(childID,daysAgo).execute();
			}
			else if(result == 0)//Call another webservice
			{
				new GetChildSubjectActiviesByDayForChildModule(childID,daysAgo).execute();
			}

		}	
	}

	private class GetChildSubjectActiviesByDayForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;
		private String daysAgo;


		public GetChildSubjectActiviesByDayForChildModule(int childID, String daysAgo)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
			this.daysAgo = daysAgo;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			/*
			progressDialog = ProgressDialog.show(ChildTutorialActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
			 */		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildTutorialActivity.this))
			{
				ErrorCode = serviceMethod.getChildSubjectActiviesByDayForChildModule(childID,daysAgo);
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ChildTutorialActivity.this))
					new GetChildSubjectActiviesByDayForChildModule(childID,daysAgo).execute();
			}
			else if(result == 0)//Move to Star Rating if any subjects are scheduled
			{
				if(AccessProfileActivity.subjectsFetchedAfterSchool!=null && AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().size()>0)
				{
					for(int i=0;i<AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().size();i++)
					{
						SubjectActivities subjectActivities = new SubjectActivities();
						subjectActivities.setSubjectID(AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().get(i).getActivityID());
						subjectActivities.setSubjectName(AccessProfileActivity.subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().get(i).getName());
						AccessProfileActivity.subjectsScheduled.add(subjectActivities);
					}

				}

				if(AccessProfileActivity.subjectsFetched!=null && AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().size()>0)
				{
					for(int i=0;i<AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().size();i++)
					{
						SubjectActivities subjectActivities = new SubjectActivities();
						subjectActivities.setSubjectID(AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().get(i).getActivityID());
						subjectActivities.setSubjectName(AccessProfileActivity.subjectsFetched.getListOfSchoolSubjects().get(i).getName());
						AccessProfileActivity.subjectsScheduled.add(subjectActivities);
					}

				}

				if(AccessProfileActivity.subjectsScheduled.size()>0)
				{


					StaticVariables.daysAgo = daysAgo;
					StaticVariables.isTutorialSoundEnabled = false;
					Intent intent = new Intent(ChildTutorialActivity.this, ChildSetStarRatingActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("ParentId", StaticVariables.currentParentId);
					bundle.putBoolean("Pendingflag", false);
					intent.putExtras(bundle);
					startActivity(intent);
					ChildTutorialActivity.this.finish();

				}
				else
				{
					//getError();
				}

			}

		}	
	}



}
