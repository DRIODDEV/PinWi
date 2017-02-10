package com.hatchtact.pinwi.fragment.insights;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetDelightTraitsByActivityList;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class DelightTrendsByActivityFragment extends ParentFragment
{

	private View view;
	public static DelightTrendsByActivityFragment delightTrendsByActivityFragment;
	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;


	public  GetDelightTraitsByActivityList getDelightTraitsByActivityList;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.activity_barchart, container, false);
		mListener.onFragmentAttached(false,"  Insights");
		setHasOptionsMenu(true);
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();
		getDelightTraitsByActivityList=new GetDelightTraitsByActivityList();


		/*....................Delight Trends.........................*/

		new AsyncTaskDelightTrendsByActivity().execute();

		/*....................Delight Trends.........................*/



		return view;		
	}


	public static DelightTrendsByActivityFragment getInstance()
	{
		if(delightTrendsByActivityFragment==null)
		{
			delightTrendsByActivityFragment = new DelightTrendsByActivityFragment();
		}
		return delightTrendsByActivityFragment;
	}



	private ProgressDialog progressDialogDelightTrendsByActivity=null;	

	private class AsyncTaskDelightTrendsByActivity extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogDelightTrendsByActivity = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogDelightTrendsByActivity.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getDelightTraitsByActivityList =serviceMethod.getDelightTraitsByActivityList(StaticVariables.currentChild.getChildID(),StaticVariables.ActivityIdInsight);
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
				if (progressDialogDelightTrendsByActivity.isShowing())
					progressDialogDelightTrendsByActivity.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new AsyncTaskDelightTrendsByActivity().execute();

			}
			else
			{
				if(getDelightTraitsByActivityList!=null && getDelightTraitsByActivityList.getGetDelightTraitsByActivity().size()>0)
				{
					//setDelightTrendsFrameData();
					setDelightFrameByActivityData();
				}
				else
				{	
					getError();

				}	



			}	
		}

		private void getError()
		{
			Error err = serviceMethod.getError();	
			showMessage.showAlert("Alert", "Insufficient Data");
		}
	}


	protected LineChart mChart;
	public void setDelightFrameByActivityData()
	{
		initializeChart();
	}


	private void initializeChart() {
		// TODO Auto-generated method stub
		mChart = (LineChart) view.findViewById(R.id.chart2);

		//mChart.setDrawBarShadow(false);
		//mChart.setDrawValueAboveBar(true);

		mChart.setDescription("");

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		mChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(true);

		// draw shadows for each bar that show the maximum value
		// mChart.setDrawBarShadow(true);

		// mChart.setDrawXLabels(false);

		mChart.setDrawGridBackground(false);
		// mChart.setDrawYLabels(false);
		mChart.setBackgroundColor(getResources().getColor(R.color.chartbackgroundcolor));
		mChart.zoomIn();
		//		mChart.zoomIn();

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);
		xAxis.setSpaceBetweenLabels(2);
		xAxis.setGridColor(Color.WHITE);
		//xAxis.setLabelsToSkip(0);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setLabelCount(8, false);
		leftAxis.setGridColor(Color.WHITE);
		leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
		leftAxis.setSpaceTop(15f);


		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setForm(LegendForm.SQUARE);
		l.setFormSize(9f);
		l.setTextSize(11f);
		l.setXEntrySpace(4f);

		// l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });
		// l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });

		setData();

		// setting data


		// mChart.setDrawLegend(false);
	}


	private void setData() {

		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		for (int i = 0; i < getDelightTraitsByActivityList.getGetDelightTraitsByActivity().size(); i++) 
		{
			xVals.add(getDelightTraitsByActivityList.getGetDelightTraitsByActivity().get(i).getActivityDate());
			yVals1.add(new BarEntry(getDelightTraitsByActivityList.getGetDelightTraitsByActivity().get(i).getRating(), i)); 
		}





		LineDataSet set1 = new LineDataSet(yVals1, "Rating");
		//set1.setBarSpacePercent(35f);
		set1.setCircleSize(10f);
		set1.setColor(Color.WHITE);
		set1.setCircleColorHole(Color.WHITE);
		set1.setHighLightColor(Color.WHITE);


		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1);


		LineData data = new LineData(xVals, dataSets);
		// data.setValueFormatter(new MyValueFormatter());
		data.setValueTextSize(10f);



		for (LineDataSet iSet : dataSets) {

			LineDataSet set = (LineDataSet) iSet;
			set.setFillColor(getResources().getColor(R.color.datasetchartcolor));
			if (set.isDrawFilledEnabled())
				set.setDrawFilled(false);
			else
				set.setDrawFilled(true);
		}

		((LineDataSet) data.getDataSetByIndex(0)).setCircleColor(Color.WHITE);
		mChart.invalidate();
		// data.setValueTypeface(mTf);
		mChart.getAxisRight().setEnabled(false);
		mChart.setData(data);
		mChart.animateX(3000);

	}



	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(false);
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
		return super.onOptionsItemSelected(item);
	}

}
