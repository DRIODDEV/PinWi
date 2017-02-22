package com.hatchtact.pinwi.fragment.insights;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.hatchtact.pinwi.ActivityAboutUS;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.StaticVariables;

public class InsightsErrorFragment extends ParentFragment implements OnClickListener
{

	private View view;
	public static InsightsErrorFragment insightfragment;
	private Button btnDashboard;
	private TextView notenoughdata,headerText,texttypesinsightbadge;
	private TextView texterrorinsightfragment,textsmall;
	private PieChart mChart;
	private TextView currentDate;
	private ServiceMethod serviceMethod;
	private LinearLayout layoutText;
	private String textBadge="Gain Insights into your child's interests. Know how you can unlock this report faster.";
	private ImageView imgArrow;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		view=inflater.inflate(R.layout.insights_error_fragment, container, false);
		mListener.onFragmentAttached(false,"  Insights");
		setHasOptionsMenu(true);
		serviceMethod=new ServiceMethod();
		btnDashboard=(Button)view.findViewById(R.id.btndashboard);
		btnDashboard.setText("View Sample Report");
		btnDashboard.setOnClickListener(this);
		typeFace.setTypefaceRegular(btnDashboard);
		layoutText=(LinearLayout) view.findViewById(R.id.layoutText);
		headerText=(TextView) view.findViewById(R.id.headerBadge);
		texttypesinsightbadge=(TextView) view.findViewById(R.id.texttypesinsightbadge);
		imgArrow=(ImageView) view.findViewById(R.id.imgArrow);
		layoutText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexErrorDetailPage=2000;
				switchingFragments(new InsightsErrorDetailFragment());

			}
		});
		imgArrow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexErrorDetailPage=2000;
				switchingFragments(new InsightsErrorDetailFragment());

			}
		});
		notenoughdata=(TextView) view.findViewById(R.id.notenoughdata);
		texterrorinsightfragment=(TextView) view.findViewById(R.id.texterrorinsightfragment);
		textsmall=(TextView) view.findViewById(R.id.textsmall);

		typeFace.setTypefaceRegular(texterrorinsightfragment);
		typeFace.setTypefaceRegular(textsmall);
		typeFace.setTypefaceBold(notenoughdata);
		typeFace.setTypefaceBold(headerText);
		texterrorinsightfragment.setOnClickListener(this);
		currentDate=(TextView) view.findViewById(R.id.texttypesinsightdate);
		setDate();//Set current date

		try {
			new GetPercentageCount(StaticVariables.currentChild.getChildID()).execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return view;
	}



	public static InsightsErrorFragment getInstance()
	{
		if(insightfragment==null)
		{
			insightfragment = new InsightsErrorFragment();
		}
		return insightfragment;
	}


	@Override
	public void onClick(View v)
	{

		switch (v.getId()) {
			case R.id.texterrorinsightfragment:
				StaticVariables.webUrl="http://www.pinwi.in/blog/";
				Intent intentAboutUs =new Intent(getActivity(), ActivityAboutUS.class);
				startActivity(intentAboutUs);
				break;

			case R.id.btndashboard:
				StaticVariables.webUrl="http://demo.pinwi.in/Insight_Report.pdf";
				Intent intentAboutUsPdf =new Intent(getActivity(), ActivityAboutUS.class);
				startActivity(intentAboutUsPdf);
				break;
		}
		
		/*if (getFragmentManager().getBackStackEntryCount() > 0) {
			getFragmentManager().popBackStack();
		} else {
			getActivity().finish();
		}*/
	}



	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(true);
			else
			{
				menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());
				menu.getItem(i).setVisible(true);

			}
		}
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
			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();
			switchingFragments(new TypesInsightsFragment());
		}
		return super.onOptionsItemSelected(item);
	}

	private void initializePieChart()
	{
		mChart = (PieChart) view.findViewById(R.id.chart1);
		mChart.setUsePercentValues(true);
		mChart.setDescription("");
		mChart.setX(-20);


		mChart.setDragDecelerationFrictionCoef(0.95f);


		mChart.setDrawHoleEnabled(true);
		mChart.setHoleColorTransparent(true);

		mChart.setTransparentCircleColor(getResources().getColor(R.color.font_yellowpiechart));
		mChart.setTransparentCircleAlpha(255);

		mChart.setHoleRadius(30f);
		mChart.setTransparentCircleRadius(33f);

		mChart.setDrawCenterText(true);
		mChart.setCenterText("  "+modelPercentage.getGetPercentageCountOnCI());

		mChart.setRotationAngle(0);
		mChart.animateY(2000);
		// enable rotation of the chart by touch
		mChart.setRotationEnabled(true);

		// mChart.setUnit(" €");
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

		/*int earnedPts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getEarnedPoints();
		int pendingpts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getPendingPoints();
		int lostpts=getPointsInfoByChildIDOnInsightsList.getGetPointsInfoByChildIDOnInsights().get(0).getLostPoints();*/
		int totalPoints=Integer.parseInt(modelPercentage.getGetPercentageCountOnCI().replace("%",""));
		int percentageLeft=30;
		int percentageGained=70;

		if(totalPoints<=100)
		{
			percentageGained=totalPoints;
			percentageLeft=100-totalPoints;
		}

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
		if(percentageLeft==100 )
		{
			colors.add(getResources().getColor(R.color.gray));

			percentageLeft=100;
			percentageGained=0;
		}
		else
		{
			colors.add(getResources().getColor(R.color.gray));
			colors.add(getResources().getColor(R.color.font_greenpiechart));
			//colors.add(getResources().getColor(R.color.font_redpiechart));
		}

		//earnedPts=2500;
		//pendingpts=1100;
		//lostpts=100;	
		yVals1.add(new Entry(percentageLeft, 0));
		yVals1.add(new Entry(percentageGained, 1));
		//yVals1.add(new Entry(lostpts, 2));
		ArrayList<String> xVals = new ArrayList<String>();
		xVals.add("");
		xVals.add("");
		xVals.add("");

		/*for (int i = 0; i < count + 1; i++)
	            xVals.add(mParties[i % mParties.length]);*/

		PieDataSet dataSet = new PieDataSet(yVals1, "");
		//dataSet.setSliceSpace(3f);
		//dataSet.setSelectionShift(5f);

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

	//private ProgressDialog progressDialog=null;

	private com.hatchtact.pinwi.classmodel.GetPercentageCount modelPercentage;
	private class GetPercentageCount extends AsyncTask<Void, Void, Integer>
	{
		int childId;


		public GetPercentageCount(int ChildID)
		{
			// TODO Auto-generated constructor stub
			childId=ChildID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			/*progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(new CheckNetwork().checkNetworkConnection(getActivity()))
			{
				modelPercentage =serviceMethod.getPercentageCountOnCI(childId);
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
				customProgressLoader.removeCallbacksHandler();
			/*	if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{


			}
			else
			{
				headerText.setText(modelPercentage.getGetPercentageCountOnCI()+" Unlocked");
				texttypesinsightbadge.setText(textBadge);
				initializePieChart();
			}

		}
	}


}
