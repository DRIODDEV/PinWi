package com.hatchtact.pinwi.fragment.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetExhilaratorsListByChildIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;


public class NetworkDriversFragment extends ParentFragment
{
	private ListView gridView_InterestDrivers;
	private GetExhilaratorsListByChildIDList getExhilaratorsListByChildIDList;
	private NetworkInterestDriversAdapterGridView adapter;

	private Context mContext;

	private TextView interestDriverSpecificName;
	private TextView interestDriverSpecificDetail;
	private TextView childName;

	private final String[] arrayInterestDrivers={"Exhilarators   ",
			"Amusers        ",
			"Unexciting     ",
			"Non-Influencers",
	"Unexplored     "};

	private final String[] arrayInterestDriverDescription={"Exhilarators are key Interest Drivers common across activities that make your child most happy. These are the primary building blocks of activities your child consistently rates higher on the scale.",
			"Amusers are key Interest Drivers common across activities that keep your child amused and occupied. These are the primary building blocks of activities your child consistently rates medium on the scale.", 
			"Unexciting are key Interest Drivers common across activities that are not in your child's good books. These are the primary building blocks of activities your child consistently rates lower on the scale.", 
			"Non-influencers are Interest Drivers that are essential but not influential in defining your child's interests. These are secondary building blocks recurring across all the activities that your child does.",
	"Unexplored are Interest Drivers or building blocks present in categories of activities your child has never been exposed to. "};

	private RelativeLayout relativeParent;

	String[] backgroundColorInterestDrivers={"#6a18b6","#ff6c00","#ffae00","#90b316","#7f7f7f"};

	private CheckNetwork checkNetwork;
	private ShowMessages showMessage;
	private ServiceMethod serviceMethod;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.interestdrivers_network, container, false);
		mListener.onFragmentAttached(false," Network");
		setHasOptionsMenu(true);
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(getActivity());
		serviceMethod=new ServiceMethod();
		init(view);
		new fetchExhilaratorsList().execute();	

		return view;
	}


	//private ProgressDialog progressDialog;

	private class fetchExhilaratorsList extends AsyncTask<Void, Void, Integer>
	{


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

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getExhilaratorsListByChildIDList =serviceMethod.getExhilaratorsListByChildID(StaticVariables.currentParentId, Integer.parseInt(StaticVariables.NetworkChildId));
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
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new fetchExhilaratorsList().execute();

			}
			else
			{
				try {
					if(getExhilaratorsListByChildIDList!=null && getExhilaratorsListByChildIDList.getExhilaratorsList().size()>0)
					{
						adapter=new NetworkInterestDriversAdapterGridView(mContext,getExhilaratorsListByChildIDList);
						gridView_InterestDrivers.setAdapter(adapter);
						gridView_InterestDrivers.setHorizontalScrollBarEnabled(false);
					}
					else
					{
						getError();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}	
		}
	}

	private void getError()
	{

		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());

	}


	public void init(View view)
	{
		mContext=getActivity();


		gridView_InterestDrivers=(ListView)view.findViewById(R.id.live_gridview);

		interestDriverSpecificName = (TextView) view.findViewById(R.id.interestDriverSpecificName);
		interestDriverSpecificDetail = (TextView) view.findViewById(R.id.interestDriverSpecificDetail);
		childName=(TextView) view.findViewById(R.id.childNameinterestDriver);
		typeFace.setTypefaceLight(interestDriverSpecificName);
		typeFace.setTypefaceRegular(childName);
		typeFace.setTypefaceRegular(interestDriverSpecificDetail);

		interestDriverSpecificName.setText(arrayInterestDrivers[0]);
		childName.setText(StaticVariables.NetworkExhilaratorChildName);

		relativeParent=(RelativeLayout) view.findViewById(R.id.parent_driverslayout);
		relativeParent.setBackgroundColor(Color.parseColor(backgroundColorInterestDrivers[0]));
		interestDriverSpecificDetail.setText(arrayInterestDriverDescription[0]);


		getExhilaratorsListByChildIDList=new GetExhilaratorsListByChildIDList();

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
				menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentParentName);
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
