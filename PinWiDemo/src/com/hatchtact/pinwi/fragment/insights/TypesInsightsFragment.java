package com.hatchtact.pinwi.fragment.insights;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.TutorialPageActivity;
import com.hatchtact.pinwi.adapter.ScreenSlidePagerAdapter;
import com.hatchtact.pinwi.classmodel.GetBadgeDetailsByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetDelightTraitsByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetInterestPatternByChildIDOnInsight;
import com.hatchtact.pinwi.classmodel.GetInterestPatternByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetInterestTraitsByChildIDOnInsight;
import com.hatchtact.pinwi.classmodel.GetInterestTraitsByChildIDOnInsightList;
import com.hatchtact.pinwi.classmodel.GetPointsInfoByChildIDOnInsightsList;
import com.hatchtact.pinwi.classmodel.ResultIsSubscribed;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

@SuppressLint("DefaultLocale")
public class TypesInsightsFragment extends ParentFragment implements View.OnClickListener 
{

	private View view;
	public static TypesInsightsFragment typesinsightfragment;
	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private TextView currentDate;

	//Badge Frame Elements
	private ImageView imgBadge;
	private TextView txtBadge;
	private GetBadgeDetailsByChildIDOnInsightList getBadgeDetailsByChildIDOnInsightList;
	private ArrayList<Integer> imageBadgesList=new ArrayList<Integer>();

	//Interest Drivers Elements
	private LinearLayout interestDriversLayout;
	private LinearLayout linearlayoutinsightinterestpattern;

	public static GetInterestTraitsByChildIDOnInsightList getInterestTraitsByChildIDOnInsightList;

	//Delight Trends Elements
	public static GetDelightTraitsByChildIDOnInsightList getDelightTraitsByChildIDOnInsightList;
	private LinearLayout delightTrendsLayout;
	private TextView moreActivitiesDelightTrends;

	//Points Summary Elements
	private GetPointsInfoByChildIDOnInsightsList getPointsInfoByChildIDOnInsightsList;
	private LinearLayout pointSummaryLayout;


	private Button subscribeNow;

	private TextView txtQualityBadge;
	private TextView txtInterestDrivers;
	private TextView txtDelightTrends;
	private TextView txtPointsSummary;
	private TextView textinsightmoreinsight;
	private TextView txtInterestPatterns;

	private ImageView instructionBadge;
	private ImageView instructionInterestDrivers;
	private ImageView instructionDelightTrends;
	private ImageView instructionPointsSummary;
	private ImageView instructionInterestPattern;


	private RelativeLayout layoutOne,layoutTwo,layoutThree,layoutFour,layoutInterestPattern;
	private String[] arrayQualityBadgeText={"The Only Way is Up!\nBased on the consistency and quality of rating data, this report is currently at Level 1. Level 5 reports are most realistic and reliable. ",
			"Steady as you go!\nBased on the consistency and quality of rating data, this report is currently at Level 2. Level 5 reports are most realistic and reliable.",
			"Half Way There!\nBased on the consistency and quality of rating data, this report is currently at Level 3. Level 5 reports are the most realistic and reliable.",
			"The Last Mile!\nBased on the consistency and frequency of rating data, this report is currently at Level 4. Level 5 reports are the most realistic and reliable.",
	"Isn't this Awesome!!\nBased on the consistency and quality of rating data, this report is now at Level 5. It doesn’t get any better than this. "};


	private SharePreferenceClass sharePref;
	private ScrollView mainscroll;
	private LinearLayout linearlayoutinsightsubscribe;
	public static GetInterestPatternByChildIDOnInsightList getInterestPatternByChildIDOnInsightList;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		//GoogleAnalytics.getInstance(getActivity()).getTracker("UA-XXXX-Y");
		/*Bundle parameters = new Bundle();
		parameters.putString(AppEventsConstants.EVENT_NAME_UNLOCKED_ACHIEVEMENT, "");
		logger.logEvent(AppEventsConstants.EVENT_NAME_UNLOCKED_ACHIEVEMENT,parameters);*/

		checkNetwork=new CheckNetwork();
		getBadgeDetailsByChildIDOnInsightList=new GetBadgeDetailsByChildIDOnInsightList();
		getInterestTraitsByChildIDOnInsightList=new GetInterestTraitsByChildIDOnInsightList();
		getDelightTraitsByChildIDOnInsightList=new GetDelightTraitsByChildIDOnInsightList();
		getPointsInfoByChildIDOnInsightsList=new GetPointsInfoByChildIDOnInsightsList();
		getInterestPatternByChildIDOnInsightList=new GetInterestPatternByChildIDOnInsightList();

		sharePref=new SharePreferenceClass(getActivity());

		//sharePref.setInsightsTutorial(false);
		if(!sharePref.isinsightsTutorial())
		{
			sharePref.setInsightsTutorial(true);
			ScreenSlidePagerAdapter.NUM_PAGES=6;

			Intent tutorial=new Intent(getActivity(), TutorialPageActivity.class);
			startActivity(tutorial);
			StaticVariables.currentTutorialValue=StaticVariables.insightsTutorial;
		}


		try {
			new GetInsightsReportStatusByParentAndChildId(StaticVariables.currentParentId, StaticVariables.currentChild.getChildID()).execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reportStatus=0;
		}






	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			if(StaticVariables.isChildUpdated)
			{
				StaticVariables.isChildUpdated=false;
				getActivity().invalidateOptionsMenu();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{

		view=inflater.inflate(R.layout.insights_typesfragment, container, false);

		StaticVariables.fragmentIndexCurrentTabInsight=1;

		mListener.onFragmentAttached(true," Insights");

		currentDate=(TextView) view.findViewById(R.id.texttypesinsightdate);
		textinsightmoreinsight = (TextView)view.findViewById(R.id.textinsightmoreinsight);
		linearlayoutinsightsubscribe=(LinearLayout) view.findViewById(R.id.linearlayoutinsightsubscribe);
		linearlayoutinsightsubscribe.setVisibility(View.GONE);
		setDate();//Set current date

		/*............................................initialize Data.....................................*/
		initBadgeFrame();
		initInterestDrivers();
		initDelightTrends();
		initPointsSummary();
		mainscroll=(ScrollView) view.findViewById(R.id.mainscroll);
		mainscroll.setAlpha(0f);

		txtQualityBadge=(TextView) view.findViewById(R.id.texttypesinsightqualitybadge);
		txtInterestDrivers=(TextView) view.findViewById(R.id.textinsightinterestdrivers);
		txtInterestPatterns=(TextView) view.findViewById(R.id.textinsightinterestpattern);
		txtDelightTrends=(TextView) view.findViewById(R.id.textinsightdelighttrends);
		txtPointsSummary=(TextView) view.findViewById(R.id.textinsightpointssummary);

		instructionBadge=(ImageView) view.findViewById(R.id.imageQualityBadgeInstruction);
		instructionDelightTrends=(ImageView) view.findViewById(R.id.imageinsightdelightTrendsInstruction);
		instructionInterestDrivers=(ImageView) view.findViewById(R.id.imageinsightInterestDriversInstruction);
		instructionInterestPattern=(ImageView) view.findViewById(R.id.imageinsightInterestPatternInstruction);
		instructionPointsSummary=(ImageView) view.findViewById(R.id.imageinsightPointsSummaryInstruction);

		layoutOne=(RelativeLayout) view.findViewById(R.id.layoutOne);
		layoutTwo=(RelativeLayout) view.findViewById(R.id.layoutTwo);
		layoutThree=(RelativeLayout) view.findViewById(R.id.layoutThree);
		layoutFour=(RelativeLayout) view.findViewById(R.id.layoutFour);
		layoutInterestPattern=(RelativeLayout) view.findViewById(R.id.layoutInterestPattern);

		layoutOne.setOnClickListener(this);
		layoutTwo.setOnClickListener(this);
		layoutThree.setOnClickListener(this);
		layoutFour.setOnClickListener(this);
		layoutInterestPattern.setOnClickListener(this);
		instructionBadge.setOnClickListener(this);
		instructionDelightTrends.setOnClickListener(this);
		instructionInterestDrivers.setOnClickListener(this);
		instructionInterestPattern.setOnClickListener(this);
		instructionPointsSummary.setOnClickListener(this);

		typeFace.setTypefaceRegular(textinsightmoreinsight);	
		typeFace.setTypefaceRegular(txtQualityBadge);
		typeFace.setTypefaceRegular(txtInterestDrivers);
		typeFace.setTypefaceRegular(txtInterestPatterns);
		typeFace.setTypefaceRegular(txtDelightTrends);
		typeFace.setTypefaceRegular(txtPointsSummary);
		txtDelightTrends.setText("Activity Rating");

		subscribeNow=(Button) view.findViewById(R.id.buttoninsightsubscribe);
		subscribeNow.setVisibility(View.GONE);
		typeFace.setTypefaceRegular(subscribeNow);
		subscribeNow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StaticVariables.fragmentIndexCurrentTabInsight=4;
				StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
				StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
				StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
				social.Subscribe_Button_ClickedFacebookLog();
				social.Subscribe_Button_ClickedGoogleAnalyticsLog();
				switchingFragments(new SubscribeFragment());



			}
		});

		/*............................................initialize Data.....................................*/

		/*............................................setting data........................................*/
		//setRetainedDataInsights();
		/*............................................setting data........................................*/


		return view;		
	}



	/**
	 * 
	 */
	private void setRetainedDataInsights()
	{
		if(getBadgeDetailsByChildIDOnInsightList!=null && getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().size()>0)
		{
			setBadgeFrameData();
		}

		if(getInterestTraitsByChildIDOnInsightList!=null && getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().size()>0)
		{
			setInterestDriversFrameData();
		}

		if(getDelightTraitsByChildIDOnInsightList!=null && getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().size()>0)
		{
			setDelightTrendsFrameData();
		}

		if(getPointsInfoByChildIDOnInsightsList!=null && getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().size()>0)
		{
			setPointsSummaryFrameData();
		}

		if(getInterestPatternByChildIDOnInsightList!=null && getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().size()>0)
		{
			//setBadgeInterestTraitsData();
		}
	}





	private void initPointsSummary() {
		// TODO Auto-generated method stub
		pointSummaryLayout=(LinearLayout) view.findViewById(R.id.linearlayoutinsightpointssummary);

	}


	private ImageView imgLock;
	private void initDelightTrends() {
		// TODO Auto-generated method stub
		delightTrendsLayout=(LinearLayout) view.findViewById(R.id.linearlayoutinsightdelighttrends);
		imgLock=(ImageView) view.findViewById(R.id.imgLock);


		moreActivitiesDelightTrends=(TextView) view.findViewById(R.id.textmoreactivitiesinsightdelighttrends);
		typeFace.setTypefaceRegular(moreActivitiesDelightTrends);

		imgLock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StaticVariables.fragmentIndexCurrentTabInsight=4;
				StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
				StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
				StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
				switchingFragments(new SubscribeFragment());
			}
		});

		moreActivitiesDelightTrends.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
				{
					StaticVariables.fragmentIndexCurrentTabInsight=2;
					switchingFragments(new DelightTrendsFragment());
				}
				else
				{
					StaticVariables.fragmentIndexCurrentTabInsight=4;
					StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
					StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
					StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
					switchingFragments(new SubscribeFragment());
				}

			}
		});
	}



	private void initInterestDrivers() {
		// TODO Auto-generated method stub
		interestDriversLayout=(LinearLayout) view.findViewById(R.id.linearlayoutinsightinterestdrivers);
		linearlayoutinsightinterestpattern=(LinearLayout) view.findViewById(R.id.linearlayoutinsightinterestpattern);

	}

	private void initBadgeFrame()
	{
		// TODO Auto-generated method stub
		imgBadge=(ImageView) view.findViewById(R.id.imagetypesinsightbadge);
		txtBadge=(TextView) view.findViewById(R.id.texttypesinsightbadge);
		typeFace.setTypefaceLight(txtBadge);
		imageBadgesList.add(R.drawable.rating_1);
		imageBadgesList.add(R.drawable.rating_2);
		imageBadgesList.add(R.drawable.rating_3);
		imageBadgesList.add(R.drawable.rating_4);
		imageBadgesList.add(R.drawable.rating_5);
	}




	private void setDate()
	{
		// TODO Auto-generated method stub

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy");

			Date date;
			String dateString;
			date = new Date(System.currentTimeMillis());
			dateString=formatter.format(date);

			currentDate.setText("Report as on "+ dateString);;
			typeFace.setTypefaceLight(currentDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




	public static TypesInsightsFragment getInstance()
	{
		if(typesinsightfragment==null)
		{
			typesinsightfragment = new TypesInsightsFragment();
		}
		return typesinsightfragment;
	}




	private ProgressDialog progressDialogBadgeDetails=null;	

	private class GetBadgeDetailsByChildIDOnInsightListAsync extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogBadgeDetails = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogBadgeDetails.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getBadgeDetailsByChildIDOnInsightList =serviceMethod.getBadgeDetailsByChildIDOnInsightList(StaticVariables.currentChild.getChildID());
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
				if (progressDialogBadgeDetails.isShowing())
					progressDialogBadgeDetails.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetBadgeDetailsByChildIDOnInsightListAsync().execute();

			}
			else
			{
				if(getBadgeDetailsByChildIDOnInsightList!=null && getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().size()>0)
				{
					setBadgeFrameData();
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}

	private void getError()
	{/*
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	 */}









	public void setBadgeFrameData() 
	{
		// TODO Auto-generated method stub
		imgBadge.setVisibility(View.VISIBLE);  	

		txtBadge.setMovementMethod(ScrollingMovementMethod.getInstance());
		social.Insights_ActivatedFacebookLog();
		social.Insights_ActivatedGoogleAnalyticsLog();
		social.Quality_Badge_LevelFacebookLog(getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().get(0).getQuality_Badge()+"");
		social.Quality_Badge_LevelGoogleAnalyticsLog(getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().get(0).getQuality_Badge()+"");
		
	
		imgBadge.setImageResource(imageBadgesList.get(getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().get(0).getQuality_Badge() -1));

		//String Level="Level " + getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().get(0).getQuality_Badge() + ".";
		//String sourceString = "<b>" + Level + "</b> "; 
		//String badgeLevelString="Based on consistency and frequency of rating you are currently at ";


		//txtBadge.setText(badgeLevelString + Level);

		int levelNo=getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().get(0).getQuality_Badge() -1;

		txtBadge.setText(arrayQualityBadgeText[levelNo]);
		if(currentDate!=null)
			currentDate.setText("Report as on "+ getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().get(0).getCreatedOn());

		int startPos = txtBadge.getText().toString().indexOf("Level");
		int endPos = startPos + 7;
		final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
		Spannable spanText = Spannable.Factory.getInstance().newSpannable(txtBadge.getText().toString());
		spanText.setSpan(bss,startPos, endPos ,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		txtBadge.setText(spanText);


	}





	private ProgressDialog progressDialogInsigts=null;	

	private class AsyncTaskInterestDrivers extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogInsigts = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogInsigts.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getInterestTraitsByChildIDOnInsightList =serviceMethod.getInterestTraitsByChildIDOnInsight(StaticVariables.currentChild.getChildID());
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
				if (progressDialogInsigts.isShowing())
					progressDialogInsigts.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new AsyncTaskInterestDrivers().execute();

			}
			else
			{
				if(getInterestTraitsByChildIDOnInsightList!=null && getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().size()>0)
				{
					setInterestDriversFrameData();
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}


	public void setInterestDriversFrameData() 
	{
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String[] arrayInterestDrivers={"Exhilarators   ",
				"Amusers        ",
				"Unexciting     ",
				"Non-Influencers",
		"Unexplored     "};

		String[] backgroundColorInterestDrivers={"#6a18b6","#ff6c00","#ffae00","#90b316","#7f7f7f"};

		Integer[] arrayInterestTrendsImages={R.drawable.ic_launcher,
				R.drawable.one_small,
				R.drawable.two_small,
				R.drawable.three_small,
				R.drawable.four_small,
				R.drawable.five_small,
				R.drawable.six_small,
				R.drawable.seven_small,
				R.drawable.eight_small,
				R.drawable.nine_small,
				R.drawable.ten_small,
				R.drawable.eleven_small,
				R.drawable.twelve_small,
				R.drawable.thirteen_small,
				R.drawable.fourteen_small,
				R.drawable.fifteen_small,
				R.drawable.sixteen_small,
				R.drawable.seventeen_small

		};
		SortDataTableByInterestTraitID sortTable = new SortDataTableByInterestTraitID();
		Collections.sort(getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight(), sortTable);
		//Collections.reverse(getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight());

		//ArrayList<GetInterestTraitsByChildIDOnInsight> getInterestTraits=new ArrayList<GetInterestTraitsByChildIDOnInsight>();
		int counter=0;
		for(int i=0;i<5;i++)
		{

			LayoutInflater layoutInflater = (LayoutInflater) getActivity()
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);

			View viewInsights = layoutInflater.inflate(R.layout.interestdrivers_item, null);

			counter=0;

			viewInsights.setId(i+1);
			LinearLayout circleLinearLayout=(LinearLayout)viewInsights.findViewById(R.id.drawCircleInterestdrivers);
			TextView texttypesinsightinterestdriversname=(TextView)viewInsights.findViewById(R.id.texttypesinsightinterestdriversname);
			typeFace.setTypefaceRegular(texttypesinsightinterestdriversname);

			ImageView imageinterstdrivers=(ImageView)viewInsights.findViewById(R.id.imageinterstdrivers);
			if(i!=0)
				insightInterestDriverLock(imageinterstdrivers);
			else
			{
				imageinterstdrivers.setImageResource(R.drawable.arrow_i);
			}

			imageinterstdrivers.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StaticVariables.fragmentIndexCurrentTabInsight=4;
					StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
					StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
					StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
					switchingFragments(new SubscribeFragment());	
				}
			});
			//circleLinearLayout.removeAllViews();
			viewInsights.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					StaticVariables.isInterestDriver=true;
					StaticVariables.BucketIdInsight=v.getId();

					if(StaticVariables.BucketIdInsight==1||modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
					{
						//here we have to start a fragment
						StaticVariables.fragmentIndexCurrentTabInsight=3;
						switchingFragments(new InsightDriversFragment());
					}
					else
					{
						StaticVariables.fragmentIndexCurrentTabInsight=4;
						StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
						StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
						StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
						switchingFragments(new SubscribeFragment());
					}


				}
			});

			viewInsights.setBackgroundColor(Color.parseColor(backgroundColorInterestDrivers[i]));

			//getInterestTraits.clear();
			for(int j=0;j<getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().size();j++)
			{

				if((counter<=4 && i+1==getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().get(j).getBucketID()))
				{

					counter++;
					LayoutInflater layoutInflaterCircle = (LayoutInflater) getActivity()
							.getApplicationContext().getSystemService(
									Context.LAYOUT_INFLATER_SERVICE);
					View viewInsightsCircle = layoutInflaterCircle.inflate(R.layout.layoutcircleinterestpattern, null);

					ImageView arrowInsight=(ImageView) viewInsightsCircle.findViewById(R.id.imageInterestTrendsCircle);
					ImageView imageInterestImageId=(ImageView) viewInsightsCircle.findViewById(R.id.viewInterestTrendsCircle);
					GetInterestTraitsByChildIDOnInsight model=getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().get(j);

					//getInterestTraits.add(model);

					if(model.getBucketID()==1||modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
					{

						imageInterestImageId.setImageResource(arrayInterestTrendsImages[model.getInterestTraitID()]);
					}
					else
					{
						imageInterestImageId.setImageResource(R.drawable.blank_driver);
					}

					if(model.getStatus()==1)
					{
						arrowInsight.setImageResource(R.drawable.up_driver);

					}
					else if(model.getStatus()==0)
					{
						arrowInsight.setVisibility(View.GONE);

					}
					else if(model.getStatus()==2)
					{
						arrowInsight.setImageResource(R.drawable.down_driver);

					}

					circleLinearLayout.addView(viewInsightsCircle);

				}

			}


			texttypesinsightinterestdriversname.setText(arrayInterestDrivers[i]);

			//imageinterstdrivers.setVisibility(View.GONE);
			if(i!=3)
				interestDriversLayout.addView(viewInsights);





		}

	}

	/**
	 * @param imageinterstdrivers
	 */
	private void insightInterestDriverLock(ImageView imageinterstdrivers) {
		if(modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
			imageinterstdrivers.setImageResource(R.drawable.arrow_i);
		else
		{
			imageinterstdrivers.setImageResource(R.drawable.lock_i);

		}
	}





	private ProgressDialog progressDialogDelightTrends=null;	

	private class AsyncTaskDelightTrends extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogDelightTrends = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogDelightTrends.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getDelightTraitsByChildIDOnInsightList =serviceMethod.getDelightTraitsByChildIDOnInsightList(StaticVariables.currentChild.getChildID());
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
				if (progressDialogDelightTrends.isShowing())
					progressDialogDelightTrends.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new AsyncTaskDelightTrends().execute();

			}
			else
			{
				if(getDelightTraitsByChildIDOnInsightList!=null && getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().size()>0)
				{
					setDelightTrendsFrameData();
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}

	public void setDelightTrendsFrameData()
	{
		// TODO Auto-generated method stub


		SortDataTableByRating sortTabelByRating = new SortDataTableByRating();
		Collections.sort(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight(), sortTabelByRating);
		Collections.reverse(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight());
		int size=0;

		if(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().size()<=0)
		{
			moreActivitiesDelightTrends.setVisibility(View.GONE);
		}
		else
		{
			if(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().size()>=5)
			{
				size=5;
			}
			else
			{
				size=getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().size();
			}
		}



		for(int i=0;i<size;i++)
		{

			LayoutInflater layoutInflater = (LayoutInflater) getActivity()
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);

			View viewDelightTrends = layoutInflater.inflate(R.layout.insight_delighttrends, null);

			viewDelightTrends.setTag(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(i));

			viewDelightTrends.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					/*	GetDelightTraitsByChildIDOnInsight model = (GetDelightTraitsByChildIDOnInsight) v.getTag();
					StaticVariables.ActivityIdInsight=model.getActivityID();

					StaticVariables.fragmentIndexCurrentTabInsight=5;
					switchingFragments(new DelightTrendsByActivityFragment());*/


				}
			});
			SeekBar seekBarDelightTrends=(SeekBar)viewDelightTrends.findViewById(R.id.seekbardelighttrends);
			ImageView imageDelightTrends=(ImageView)viewDelightTrends.findViewById(R.id.imagedelighttrends);
			TextView textDelightTrends=(TextView)viewDelightTrends.findViewById(R.id.textSeekBarDelightTrends);
			TextView textNameDelightTrends=(TextView)viewDelightTrends.findViewById(R.id.textNameSeekBarDelightTrends);

			typeFace.setTypefaceRegular(textNameDelightTrends);
			typeFace.setTypefaceLight(textDelightTrends);


			seekBarDelightTrends.setEnabled(false);
			seekBarDelightTrends.setProgress((int)(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(i).getRating()));
			textDelightTrends.setText(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(i).getRating() +"");
			textNameDelightTrends.setText(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(i).getName() +"");


			if(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(i).getChange()==1.0)
			{
				imageDelightTrends.setImageResource(R.drawable.up_arrow);
			}
			else if(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(i).getChange()==2.0)

			{

				imageDelightTrends.setImageResource(R.drawable.down_arrow);

			}
			else
			{
				imageDelightTrends.setVisibility(View.INVISIBLE);
			}

			delightTrendsLayout.addView(viewDelightTrends);

		}

	}




	private ProgressDialog progressDialogPointsSummary=null;	

	private class AsyncTaskPointsSummary extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogPointsSummary = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogPointsSummary.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getPointsInfoByChildIDOnInsightsList =serviceMethod.getPointsInfoByChildIDOnInsightsList(StaticVariables.currentChild.getChildID());
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
				if (progressDialogPointsSummary.isShowing())
					progressDialogPointsSummary.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new AsyncTaskDelightTrends().execute();

			}
			else
			{
				if(getPointsInfoByChildIDOnInsightsList!=null && getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().size()>0)
				{
					setPointsSummaryFrameData();
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}

	private PieChart mChart;
	@SuppressLint("DefaultLocale")
	public void setPointsSummaryFrameData() 
	{
		// TODO Auto-generated method stub





		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

		View viewPointsSummary = layoutInflater.inflate(R.layout.insights_pointssummary, null);



		TextView percentagepoints1=(TextView)viewPointsSummary.findViewById(R.id.percentagepoints1);
		TextView pointsdetails1=(TextView)viewPointsSummary.findViewById(R.id.pointsdetails1);
		TextView percentagepoints2=(TextView)viewPointsSummary.findViewById(R.id.percentagepoints2);
		TextView pointsdetails2=(TextView)viewPointsSummary.findViewById(R.id.pointsdetails2);
		TextView percentagepoints3=(TextView)viewPointsSummary.findViewById(R.id.percentagepoints3);
		TextView pointsdetails3=(TextView)viewPointsSummary.findViewById(R.id.pointsdetails3);
		ImageView imagepointssummary1=(ImageView)viewPointsSummary.findViewById(R.id.imagepointssummary1);
		ImageView imagepointssummary2=(ImageView)viewPointsSummary.findViewById(R.id.imagepointssummary2);
		ImageView imagepointssummary3=(ImageView)viewPointsSummary.findViewById(R.id.imagepointssummary3);

		typeFace.setTypefaceLight(percentagepoints1);
		typeFace.setTypefaceLight(pointsdetails1);
		typeFace.setTypefaceLight(percentagepoints2);
		typeFace.setTypefaceLight(pointsdetails2);
		typeFace.setTypefaceLight(percentagepoints3);
		typeFace.setTypefaceLight(pointsdetails3);



		String points1details=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getEarnedPoints() + "";
		String points2details=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getPendingPoints() + "";
		String points3details=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getLostPoints() + "";

		pointsdetails1.setText("Earned  : "+points1details);
		pointsdetails2.setText("Pending : "+points2details);
		pointsdetails3.setText("Lost    : "+points3details);
		imagepointssummary1.setImageResource(R.drawable.earned);
		imagepointssummary2.setImageResource(R.drawable.pending);
		imagepointssummary3.setImageResource(R.drawable.lost);

		float earnedPts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getEarnedPoints();
		float pendingpts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getPendingPoints();
		float lostpts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getLostPoints();

		float totalpoints=earnedPts + pendingpts + lostpts;
		String earnedPercentage;
		String pendingPercentage;
		String lostPercentage;

		if(totalpoints>0)
		{
			earnedPercentage=(Math.round(earnedPts * 100/totalpoints)) +"%";
			pendingPercentage=(Math.round(pendingpts * 100/totalpoints)) +"%";
			lostPercentage=(Math.round(lostpts * 100/totalpoints)) +"%";





		}
		else
		{
			earnedPercentage="0%";
			pendingPercentage="0%";
			lostPercentage="0%";
		}

		percentagepoints1.setText(earnedPercentage);
		percentagepoints2.setText(pendingPercentage);
		percentagepoints3.setText(lostPercentage);

		initializePieChart(viewPointsSummary);

		pointSummaryLayout.addView(viewPointsSummary);

	}




	private void initializePieChart(View viewPointsSummary)
	{
		mChart = (PieChart) viewPointsSummary.findViewById(R.id.chart1);
		mChart.setUsePercentValues(true);
		mChart.setDescription("");
		mChart.setX(-20);


		mChart.setDragDecelerationFrictionCoef(0.95f);


		mChart.setDrawHoleEnabled(true);
		mChart.setHoleColorTransparent(true);

		mChart.setTransparentCircleColor(Color.WHITE);
		mChart.setTransparentCircleAlpha(110);

		mChart.setHoleRadius(58f);
		mChart.setTransparentCircleRadius(61f);

		mChart.setDrawCenterText(true);   

		mChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		mChart.setRotationEnabled(true);

		// mChart.setUnit(" â‚¬");
		// mChart.setDrawUnitsInChart(true);



		//mChart.setCenterText("MPAndroidChart\nby Philipp Jahoda");

		setData();

		//	mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
		// mChart.spin(2000, 0, 360);

		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(0f);
		l.setYOffset(0f);
		l.setEnabled(false);
	}



	private void setData(/*int count, float range*/) {

		// float mult = range;

		ArrayList<Entry> yVals1 = new ArrayList<Entry>();

		// IMPORTANT: In a PieChart, no values (Entry) should have the same
		// xIndex (even if from different DataSets), since no values can be
		// drawn above each other.
		/*  for (int i = 0; i < 3; i++) {
	            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
	        }*/

		int earnedPts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getEarnedPoints();
		int pendingpts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getPendingPoints();
		int lostpts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getLostPoints();


		ArrayList<Integer> colors = new ArrayList<Integer>();

		/*for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);
		 */
		//colors.add(ColorTemplate.getHoloBlue());
		if(earnedPts==0 && pendingpts==0 && lostpts==0)
		{
			colors.add(Color.GRAY);
			colors.add(Color.YELLOW);
			colors.add(Color.RED);
			earnedPts=100;
		}
		else
		{
			colors.add(getResources().getColor(R.color.font_greenpiechart));
			colors.add(getResources().getColor(R.color.font_yellowpiechart));
			colors.add(getResources().getColor(R.color.font_redpiechart));
		}

		//earnedPts=2500;
		//pendingpts=1100;
		//lostpts=100;	
		yVals1.add(new Entry(earnedPts, 0));
		yVals1.add(new Entry(pendingpts, 1));
		yVals1.add(new Entry(lostpts, 2));
		ArrayList<String> xVals = new ArrayList<String>();
		xVals.add("");
		xVals.add("");
		xVals.add("");

		/*for (int i = 0; i < count + 1; i++)
	            xVals.add(mParties[i % mParties.length]);*/

		PieDataSet dataSet = new PieDataSet(yVals1, "");
		dataSet.setSliceSpace(3f);
		dataSet.setSelectionShift(5f);

		// add a lot of colors




		dataSet.setColors(colors);

		PieData data = new PieData(xVals, dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setValueTextColor(Color.TRANSPARENT);
		// data.setValueTypeface(tf);
		mChart.setData(data);

		// undo all highlights
		mChart.highlightValues(null);

		mChart.invalidate();
	}



	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			menu.getItem(i).setVisible(true);
		}
		menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());

		/*

		SpannableString spanString = new SpannableString(menu.getItem(0).getTitle().toString());
//		spanString.setSpan(new TypefaceSpan("gotham.ttf"), 0, spanString.length(),
//				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spanString.setSpan(new ForegroundColorSpan(R.color.font_white_color), 0, spanString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); //fix the color to white

		menu.findItem(R.id.action_childName).setTitle(spanString);

		menu.getItem(0).setTitle(spanString);*/

		super.onCreateOptionsMenu(menu, inflater);
	}  


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub


		if(item.getItemId()==android.R.id.home)
		{

			getActivity().onBackPressed();
		}

		else if(item.getItemId()!=R.id.action_childName)
		{
			mainscroll.setAlpha(0f);

			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();
			interestDriversLayout.removeAllViews();
			linearlayoutinsightinterestpattern.removeAllViews();
			// TODO Auto-generated method stub
			delightTrendsLayout.removeAllViews();


			pointSummaryLayout.removeAllViews();
			try {
				imgBadge.setVisibility(View.INVISIBLE);  		
				txtBadge.setText("Interest report is being generated");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//refreshDataAccordingToChild();
			try {
				new GetInsightsReportStatusByParentAndChildId(StaticVariables.currentParentId, StaticVariables.currentChild.getChildID()).execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reportStatus=0;
			}

		}


		return super.onOptionsItemSelected(item);
	}


	private void refreshDataAccordingToChild()
	{

		if(getBadgeDetailsByChildIDOnInsightList!=null)
		{
			if(getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight()!=null)
				getBadgeDetailsByChildIDOnInsightList.getGetBadgeDetailsByChildIDOnInsight().clear();

			if(getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight()!=null)
				getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().clear();

			if(getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight()!=null)
				getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().clear();

			if(getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights()!=null)
				getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().clear();

			if(getInterestPatternByChildIDOnInsightList!=null)
			{
				if(getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList()!=null)
					getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().clear();
			}
		}

		if(modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
		{
			imgLock.setVisibility(View.GONE);
		}
		else
		{
			imgLock.setVisibility(View.VISIBLE);
		}
		if(modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
		{
			subscribeNow.setVisibility(View.GONE);
			linearlayoutinsightsubscribe.setVisibility(View.GONE);

		}
		else
		{
			subscribeNow.setVisibility(View.VISIBLE);
			linearlayoutinsightsubscribe.setVisibility(View.VISIBLE);

		}


		/*................Badge Frame...................................*/

		new GetBadgeDetailsByChildIDOnInsightListAsync().execute();
		/*................Badge Frame...................................*/


		/*....................Intererest Drivers.........................*/

		new AsyncTaskInterestDrivers().execute();
		/*....................Intererest Drivers.........................*/


		/*....................Delight Trends.........................*/

		new AsyncTaskDelightTrends().execute();

		/*....................Delight Trends.........................*/


		/*....................Points Summary.........................*/

		new AsyncTaskPointsSummary().execute();

		/*...................Points Summary.........................*/

		/*....................Interest Pattern.........................*/

		if(modelSubscribe.getPatternStatus().trim().equalsIgnoreCase("true"))
		{
			linearlayoutinsightinterestpattern.setVisibility(View.GONE);
			layoutInterestPattern.setVisibility(View.GONE);
			new GetInterestPatternAsync().execute();
		}
		else
		{
			linearlayoutinsightinterestpattern.setVisibility(View.GONE);
			layoutInterestPattern.setVisibility(View.GONE);
		}
		mainscroll.setAlpha(1f);

		/*....................Points Summary.........................*/

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
		case R.id.layoutOne:
			showMessage.showAlertInsights("Data Quality Badge",getString(R.string.namesBadges));
			break;

		case R.id.layoutTwo:
			showMessage.showAlertInsights("Interest Drivers",getString(R.string.namesInterestDrivers));

			break;

		case R.id.layoutThree:
			showMessage.showAlertInsights("Activity Rating",getString(R.string.namesDelightTrends));

			break;
		case R.id.layoutFour:
			showMessage.showAlertInsights("Points Summary",getString(R.string.namesPointsSummary));

			break;
		case R.id.layoutInterestPattern:
			showMessage.showAlertInsights("Interest Pattern",getString(R.string.namesInterestPattern));
			break;
		case R.id.imageQualityBadgeInstruction:
			showMessage.showAlertInsights("Data Quality Badge",getString(R.string.namesBadges));

			break;
		case R.id.imageinsightInterestDriversInstruction:
			showMessage.showAlertInsights("Interest Drivers",getString(R.string.namesInterestDrivers));

			break;
		case R.id.imageinsightdelightTrendsInstruction:
			showMessage.showAlertInsights("Activity Rating",getString(R.string.namesDelightTrends));

			break;

		case R.id.imageinsightPointsSummaryInstruction:
			showMessage.showAlertInsights("Points Summary",getString(R.string.namesPointsSummary));

			break;
		case R.id.imageinsightInterestPatternInstruction:
			showMessage.showAlertInsights("Interest Pattern",getString(R.string.namesInterestPattern));
			break;
		}

	}



	private ProgressDialog progressDialog=null;	
	private int reportStatus;
	private ResultIsSubscribed modelSubscribe;

	private class GetInsightsReportStatusByParentAndChildId extends AsyncTask<Void, Void, Integer>
	{
		int parentId;
		int childId;


		public GetInsightsReportStatusByParentAndChildId(int parentID,int ChildID)
		{
			// TODO Auto-generated constructor stub
			parentId=parentID;
			childId=ChildID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				reportStatus =serviceMethod.getinsightReportActiveStatus(parentId, childId);
				try {
					modelSubscribe=serviceMethod.isSubscribed(StaticVariables.currentParentId, 1).getIsSubscribed().get(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				StaticVariables.fragmentIndexCurrentTabInsight=7;
				/*getFragmentManager().beginTransaction().replace(R.id.tabcontent_frame_layout,
						new InsightsErrorFragment()).commit(); */
				getFragmentManager().beginTransaction().replace(R.id.tabcontent_frame_layout,
						new InsightsErrorFragment()).commitAllowingStateLoss(); 
				/*if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetInsightsReportStatusByParentAndChildId(parentId,childId).execute();*/

			}
			else
			{
				if(result!=-1)
				{

					if(reportStatus==0)
					{
						StaticVariables.fragmentIndexCurrentTabInsight=7;
						/*getFragmentManager().beginTransaction().replace(R.id.tabcontent_frame_layout,
								new InsightsErrorFragment()).commit(); */
						getFragmentManager().beginTransaction().replace(R.id.tabcontent_frame_layout,
								new InsightsErrorFragment()).commitAllowingStateLoss(); 
					}
					else if(reportStatus==1)
					{
						//mainscroll.setAlpha(1f);

						refreshDataAccordingToChild();

					}
				}	
				else
				{

				}
			}

		}	
	}


	private ProgressDialog progressPatterns;

	private class GetInterestPatternAsync extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressPatterns = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressPatterns.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getInterestPatternByChildIDOnInsightList =serviceMethod.getInterestPatternByChildIDOnInsightList(StaticVariables.currentChild.getChildID());
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
				if (progressPatterns.isShowing())
					progressPatterns.cancel();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetBadgeDetailsByChildIDOnInsightListAsync().execute();

			}
			else
			{
				if(getInterestPatternByChildIDOnInsightList!=null && getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().size()>0)
				{
					//setBadgeFrameData();
					linearlayoutinsightinterestpattern.setVisibility(View.VISIBLE);
					layoutInterestPattern.setVisibility(View.VISIBLE);
					setInterestPatternData();
				}
				else
				{	
					linearlayoutinsightinterestpattern.setVisibility(View.VISIBLE);
					layoutInterestPattern.setVisibility(View.VISIBLE);
					setInterestPatternNodata();
					getError();
				}	
			}	
		}

		/**
		 * 
		 */
		private void setInterestPatternNodata() {
			String[] arrayInterestPattern={"Long Held",
					"New Found",
			"SEE-SAW"};

			Integer[] arrayInterestPatternImages={R.drawable.logn_held,R.drawable.new_found,R.drawable.see_saw};

			String[] backgroundColorInterestDrivers={"#ffffff","#ffffff","#ffffff"};

			for(int i=0;i<3;i++)
			{

				LayoutInflater layoutInflater = (LayoutInflater) getActivity()
						.getApplicationContext().getSystemService(
								Context.LAYOUT_INFLATER_SERVICE);

				View viewInsights = layoutInflater.inflate(R.layout.item_interestpattern, null);
				viewInsights.setId(i+1);
				ImageView imagepattern=(ImageView) viewInsights.findViewById(R.id.imagepattern);
				//LinearLayout circleLinearLayout=(LinearLayout)viewInsights.findViewById(R.id.drawCircleInterestpattern);
				TextView texttypesinsightinterestdriversname=(TextView)viewInsights.findViewById(R.id.texttypesinsightinterestdriversname);
				typeFace.setTypefaceRegular(texttypesinsightinterestdriversname);
				ImageView imageinterstdrivers=(ImageView)viewInsights.findViewById(R.id.imageinterstdrivers);

				imageinterstdrivers.setImageResource(R.drawable.arrow_i);

				viewInsights.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						StaticVariables.BucketIdInsight=v.getId();
						StaticVariables.isInterestDriver=false;


						//if(StaticVariables.BucketIdInsight==1||modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
						{
							//here we have to start a fragment
							StaticVariables.fragmentIndexCurrentTabInsight=3;
							switchingFragments(new InsightDriversFragment());
						}
						/*else
						{
							StaticVariables.fragmentIndexCurrentTabInsight=4;
							StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
							StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
							StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
							switchingFragments(new SubscribeFragment());
						}*/


					}
				});

				viewInsights.setBackgroundColor(Color.parseColor(backgroundColorInterestDrivers[i]));

				//getInterestTraits.clear();


				texttypesinsightinterestdriversname.setText(arrayInterestPattern[i]);
				if(i==3)
				{
					View viewline=(View)viewInsights.findViewById(R.id.lining_view);
					viewline.setVisibility(View.GONE);

				}


				switch (i) {
				case 0:
					texttypesinsightinterestdriversname.setTextColor(getActivity().getResources().getColor(R.color.font_greenpiechart));
					break;
				case 1:
					texttypesinsightinterestdriversname.setTextColor(getActivity().getResources().getColor(R.color.font_red_color));
					break;
				case 2:
					texttypesinsightinterestdriversname.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
					break;
				default:
					break;
				}

				imagepattern.setImageResource(arrayInterestPatternImages[i]);

				//imageinterstdrivers.setVisibility(View.GONE);
				//if(i!=3)
				linearlayoutinsightinterestpattern.addView(viewInsights);

			}
		}	
	}



	public void setInterestPatternData() 
	{
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String[] arrayInterestPattern={"Long Held",
				"New Found",
		"SEE-SAW"};

		Integer[] arrayInterestPatternImages={R.drawable.logn_held,R.drawable.new_found,R.drawable.see_saw};

		String[] backgroundColorInterestDrivers={"#ffffff","#ffffff","#ffffff"};

		Integer[] arrayInterestTrendsImages={R.drawable.ic_launcher,
				R.drawable.one_small,
				R.drawable.two_small,
				R.drawable.three_small,
				R.drawable.four_small,
				R.drawable.five_small,
				R.drawable.six_small,
				R.drawable.seven_small,
				R.drawable.eight_small,
				R.drawable.nine_small,
				R.drawable.ten_small,
				R.drawable.eleven_small,
				R.drawable.twelve_small,
				R.drawable.thirteen_small,
				R.drawable.fourteen_small,
				R.drawable.fifteen_small,
				R.drawable.sixteen_small,
				R.drawable.seventeen_small

		};
		SortDataTableByInterestPatternID sortTable = new SortDataTableByInterestPatternID();
		Collections.sort(getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList(), sortTable);
		//Collections.reverse(getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight());

		//ArrayList<GetInterestTraitsByChildIDOnInsight> getInterestTraits=new ArrayList<GetInterestTraitsByChildIDOnInsight>();
		int counter=0;
		for(int i=0;i<3;i++)
		{

			LayoutInflater layoutInflater = (LayoutInflater) getActivity()
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);

			View viewInsights = layoutInflater.inflate(R.layout.item_interestpattern, null);

			counter=0;

			viewInsights.setId(i+1);
			ImageView imagepattern=(ImageView) viewInsights.findViewById(R.id.imagepattern);
			LinearLayout circleLinearLayout=(LinearLayout)viewInsights.findViewById(R.id.drawCircleInterestpattern);
			TextView texttypesinsightinterestdriversname=(TextView)viewInsights.findViewById(R.id.texttypesinsightinterestdriversname);
			typeFace.setTypefaceRegular(texttypesinsightinterestdriversname);

			ImageView imageinterstdrivers=(ImageView)viewInsights.findViewById(R.id.imageinterstdrivers);
			/*if(i!=0)
				insightInterestDriverLock(imageinterstdrivers);
			else*/
			{
				imageinterstdrivers.setImageResource(R.drawable.arrow_i);
			}
			/*
			imageinterstdrivers.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StaticVariables.fragmentIndexCurrentTabInsight=4;
					StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
					StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
					StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
					switchingFragments(new SubscribeFragment());	
				}
			});*/
			//circleLinearLayout.removeAllViews();
			viewInsights.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					StaticVariables.BucketIdInsight=v.getId();
					StaticVariables.isInterestDriver=false;


					//if(StaticVariables.BucketIdInsight==1||modelSubscribe.getStatus().trim().equalsIgnoreCase("true"))
					{
						//here we have to start a fragment
						StaticVariables.fragmentIndexCurrentTabInsight=3;
						switchingFragments(new InsightDriversFragment());
					}
					/*else
					{
						StaticVariables.fragmentIndexCurrentTabInsight=4;
						StaticVariables.subscriptionTypeArray=modelSubscribe.getSubscriptionType().split(",");
						StaticVariables.subscriptionTextArray=modelSubscribe.getSubscriptionText().split(",");				
						StaticVariables.subscriptionCostArray=modelSubscribe.getSubscriptionCost().split(",");
						switchingFragments(new SubscribeFragment());
					}*/


				}
			});

			viewInsights.setBackgroundColor(Color.parseColor(backgroundColorInterestDrivers[i]));

			//getInterestTraits.clear();
			for(int j=0;j<getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().size();j++)
			{

				if((counter<=4 && i+1==Integer.parseInt(getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().get(j).getPatternID().trim())))
				{

					counter++;
					LayoutInflater layoutInflaterCircle = (LayoutInflater) getActivity()
							.getApplicationContext().getSystemService(
									Context.LAYOUT_INFLATER_SERVICE);
					View viewInsightsCircle = layoutInflaterCircle.inflate(R.layout.layoutcircleinterestpattern, null);

					ImageView arrowInsight=(ImageView) viewInsightsCircle.findViewById(R.id.imageInterestTrendsCircle);
					ImageView imageInterestImageId=(ImageView) viewInsightsCircle.findViewById(R.id.viewInterestTrendsCircle);
					GetInterestPatternByChildIDOnInsight model=getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().get(j);

					//getInterestTraits.add(model);

					imageInterestImageId.setImageResource(arrayInterestTrendsImages[Integer.parseInt(model.getInterestTraitID().trim())]);
					arrowInsight.setVisibility(View.GONE);

					/*	if(model.getStatus()==1)
					{
						arrowInsight.setImageResource(R.drawable.up_driver);

					}
					else if(model.getStatus()==0)
					{
						arrowInsight.setVisibility(View.GONE);

					}
					else if(model.getStatus()==2)
					{
						arrowInsight.setImageResource(R.drawable.down_driver);

					}
					 */
					circleLinearLayout.addView(viewInsightsCircle);

				}

			}


			texttypesinsightinterestdriversname.setText(arrayInterestPattern[i]);


			switch (i) {
			case 0:
				texttypesinsightinterestdriversname.setTextColor(getActivity().getResources().getColor(R.color.font_greenpiechart));
				break;
			case 1:
				texttypesinsightinterestdriversname.setTextColor(getActivity().getResources().getColor(R.color.font_red_color));
				break;
			case 2:
				texttypesinsightinterestdriversname.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
				break;
			default:
				break;
			}

			imagepattern.setImageResource(arrayInterestPatternImages[i]);

			if(i==3)
			{
				View viewline=(View)viewInsights.findViewById(R.id.lining_view);
				viewline.setVisibility(View.GONE);

			}

			//imageinterstdrivers.setVisibility(View.GONE);
			//if(i!=3)
			linearlayoutinsightinterestpattern.addView(viewInsights);





		}

	}




}
