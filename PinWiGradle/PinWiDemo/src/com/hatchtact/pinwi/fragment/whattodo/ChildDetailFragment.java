package com.hatchtact.pinwi.fragment.whattodo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.classmodel.GetChildDetailsByChildID;
import com.hatchtact.pinwi.classmodel.GetChildDetailsByChildIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.fragment.network.NetworkDriversFragment;
import com.hatchtact.pinwi.fragment.network.ZoomScreenActivity;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public  class ChildDetailFragment extends ParentFragment 
{
	private View view;
	private SharePreferenceClass sharePref;

	private ServiceMethod servicemethod;
	private CheckNetwork checkNetwork;
	private ShowMessages showMessage;
	private GetChildDetailsByChildIDList getChildDetailsByChildIDList;

	private TextView txtChildName,txtParentNameChild,txtdob,txtSibling,txtfamilyconnections;
	private TextView parentDetailheading,childDetailHeading;
	private ImageView imgUserChild,imgUser;
	private Button btn_networkconnectionitem;
	private TextView txtParentName,txtChildrenName;
	private LinearLayout linearlayoutnetworkinterestdrivers;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//StaticVariables.fragmentIndexCurrentTabSchedular=201;
		sharePref=new SharePreferenceClass(getActivity());

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if(savedInstanceState==null)
		{
			view=inflater.inflate(R.layout.friendchild_network, container, false);
			setHasOptionsMenu(true);
			mListener.onFragmentAttached(false,"  Network");
			initialize();//initialize all view items in fragment
			checkNetwork=new CheckNetwork();
			showMessage=new ShowMessages(getActivity());
			servicemethod=new ServiceMethod();
			getChildDetailsByChildIDList=new GetChildDetailsByChildIDList();
			new fetchChildDetailList().execute();	


		}
		return view;		
	}

	private void initialize() 
	{
		// TODO Auto-generated method stub
		parentDetailheading=(TextView) view.findViewById(R.id.parentDetailheading);
		childDetailHeading=(TextView) view.findViewById(R.id.childDetailheading);
		txtParentName=(TextView) view.findViewById(R.id.txtParentName);
		btn_networkconnectionitem=(Button) view.findViewById(R.id.btn_networkconnectionitem);
		txtChildrenName=(TextView) view.findViewById(R.id.txtChildrenName);
		imgUser=(ImageView) view.findViewById(R.id.imgUser);
		txtChildName=(TextView) view.findViewById(R.id.txtChildName);
		txtParentNameChild=(TextView) view.findViewById(R.id.txtParentNameChild);
		txtdob=(TextView) view.findViewById(R.id.txtdob);
		txtSibling=(TextView) view.findViewById(R.id.txtSibling);
		txtfamilyconnections=(TextView) view.findViewById(R.id.txtfamilyconnections);
		imgUserChild=(ImageView) view.findViewById(R.id.imgUserChild);
		linearlayoutnetworkinterestdrivers=(LinearLayout) view.findViewById(R.id.linearlayoutnetworkinterestdrivers);

		btn_networkconnectionitem.setVisibility(View.GONE);
		txtParentName.setTextColor(getActivity().getResources().getColor(R.color.black_color));
		txtParentName.setText("Errr...!");
		txtChildrenName.setText("There are no Exhilarators   to display for this child at the moment.");
		txtChildrenName.setSingleLine(false);
		txtChildrenName.setMaxLines(3);
		
		typeFace.setTypefaceRegular(parentDetailheading);
		typeFace.setTypefaceRegular(childDetailHeading);
		typeFace.setTypefaceRegular(txtParentName);
		typeFace.setTypefaceRegular(btn_networkconnectionitem);
		typeFace.setTypefaceRegular(txtChildrenName);
		typeFace.setTypefaceRegular(txtChildName);
		typeFace.setTypefaceRegular(txtParentNameChild);
		typeFace.setTypefaceRegular(txtdob);
		typeFace.setTypefaceRegular(txtSibling);
		typeFace.setTypefaceRegular(txtfamilyconnections);



	}

	public static ChildDetailFragment fr;

	public static ChildDetailFragment getInstance()
	{
		if(fr==null)
		{
			fr = new ChildDetailFragment();
		}
		return fr;
	}




	// the create options menu with a MenuInflater to have the menu from your fragment
	/*@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			menu.getItem(i).setVisible(true);
		}
		menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());

		super.onCreateOptionsMenu(menu, inflater);
	}  


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if(item.getItemId()==android.R.id.home)
		{

			getActivity().onBackPressed();
		}

		else  if(item.getItemId()!=R.id.action_childName)
		{
			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();

			//here we have to refresh data according to child

		}


		return super.onOptionsItemSelected(item);
	}*/

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
		
	//private ProgressDialog progressDialog=null;

	private class fetchChildDetailList extends AsyncTask<Void, Void, Integer>
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
				getChildDetailsByChildIDList =servicemethod.getChildDetailsByChildId(StaticVariables.currentParentId, Integer.parseInt(StaticVariables.NetworkChildId));
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
				hideKeyBoard();
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
					new fetchChildDetailList().execute();

			}
			else
			{
				try {
					if(getChildDetailsByChildIDList!=null && getChildDetailsByChildIDList.getChildDetailsByChildIDList().size()>0)
					{
						setChildData();

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

		/**
		 * 
		 */
		private void setChildData() 
		{
			final GetChildDetailsByChildID model= getChildDetailsByChildIDList.getChildDetailsByChildIDList().get(0);

			imgUser=(ImageView) view.findViewById(R.id.imgUser);
			txtChildName=(TextView) view.findViewById(R.id.txtChildName);
			txtParentNameChild=(TextView) view.findViewById(R.id.txtParentNameChild);
			txtdob=(TextView) view.findViewById(R.id.txtdob);
			txtSibling=(TextView) view.findViewById(R.id.txtSibling);
			txtfamilyconnections=(TextView) view.findViewById(R.id.txtfamilyconnections);
			imgUserChild=(ImageView) view.findViewById(R.id.imgUserChild);

			StaticVariables.NetworkExhilaratorChildName=model.getChildName();

			txtChildName.setText(model.getChildName());
			txtParentNameChild.setText("Parent Name: "+model.getParentName());
			txtdob.setText(model.getAge()+" Years Old, Born On: "+model.getDateOfBirth());
			txtSibling.setText("Sibling: "+model.getSiblings());
			txtfamilyconnections.setText("Family Connections: "+model.getFamilyConnection());



			if(model.getChildProfileImage()!=null && model.getChildProfileImage().trim().length()>100)
			{
				byte[] imageByteParent=Base64.decode(model.getChildProfileImage(), 0);
				if(imageByteParent!=null)
				{
					imgUserChild.setBackgroundResource(0);	
					imgUserChild.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length)));	
				}
			}
			else
			{
				imgUserChild.setBackgroundResource(R.drawable.child_image);	
				imgUserChild.setImageBitmap(null);
			}

			imgUser.setFocusable(true);
			imgUserChild.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//zoom screen activity call	

					Intent intent=new Intent(getActivity(),ZoomScreenActivity.class);
					//intent.putExtra("byteArray", model.getProfileImage());
					StaticVariables.byteArray=model.getChildProfileImage();
					startActivity(intent);

				}
			});

			setExhilarotorsData(model);

		}	
	}

	private void getError()
	{
		com.hatchtact.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
	}




	public void setExhilarotorsData(GetChildDetailsByChildID model) 
	{
		// TODO Auto-generated method stub
		//model.setInterestID("1,2,6,10,15,17,5,3");
		if(model.getInterestID().equalsIgnoreCase("0"))
		{
			imgUser.setVisibility(View.VISIBLE);
			txtParentName.setVisibility(View.VISIBLE);
			txtChildrenName.setVisibility(View.VISIBLE);
			txtParentName.setText("Errr...!");
			txtChildrenName.setText("There are no Exhilarators  to display for this child at the moment.");
			txtChildrenName.setSingleLine(false);
			
			txtParentName.setAlpha(.5f);
			txtChildrenName.setAlpha(.5f);
			linearlayoutnetworkinterestdrivers.setVisibility(View.GONE);
		}
		else
		{
			imgUser.setVisibility(View.GONE);
			txtParentName.setVisibility(View.GONE);
			txtChildrenName.setVisibility(View.GONE);
			linearlayoutnetworkinterestdrivers.setVisibility(View.VISIBLE);
			String[] interestIdArray=model.getInterestID().split(",");

			setInterestDriversFrameData(interestIdArray);

		}
	}


	public void setInterestDriversFrameData(String[] interestIdArray) 
	{
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		//String[] backgroundColorInterestDrivers={"#6a18b6","#ff6c00","#ffae00","#90b316","#7f7f7f"};
		ArrayList<String> interestIdArrayList=new ArrayList<String>();
		for(int i=0;i<interestIdArray.length;i++)
		{
			interestIdArrayList.add(interestIdArray[i]);
		}

		Integer[] arrayInterestTrendsImages={R.drawable.ic_launcher,
				R.drawable.one_big,
				R.drawable.two_big,
				R.drawable.three_big,
				R.drawable.four_big,
				R.drawable.five_big,
				R.drawable.six_big,
				R.drawable.seven_big,
				R.drawable.eight_big,
				R.drawable.nine_big,
				R.drawable.ten_big,
				R.drawable.eleven_big,
				R.drawable.twelve_big,
				R.drawable.thirteen_big,
				R.drawable.fourteen_big,
				R.drawable.fifteen_big,
				R.drawable.sixteen_big,
				R.drawable.seventeen_big,
				R.drawable.eighteen_big

		};
		/*SortDataTableByInterestTraitID sortTable = new SortDataTableByInterestTraitID();
		Collections.sort(getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight(), sortTable);*/

		//Arrays.sort(interestIdArray);

		Collections.sort(interestIdArrayList, new Comparator<String>() {
			@Override
			public int compare(String entry1, String entry2) {
				Integer time1 = Integer.valueOf(entry1);
				Integer time2 = Integer.valueOf(entry2);
				return time1.compareTo(time2);
			}
		});





		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		View viewInsights = layoutInflater.inflate(R.layout.layout_interestdriverexhilarotornetwork, null);
		LinearLayout circleLinearLayout=(LinearLayout)viewInsights.findViewById(R.id.drawCircleInterestdriversNetwork);


		ImageView imageinterstdrivers=(ImageView)viewInsights.findViewById(R.id.imageinterstdrivers);
		//circleLinearLayout.removeAllViews();
		viewInsights.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(StaticVariables.fragmentIndexCurrentTabWhatToDo==308)
				StaticVariables.fragmentIndexCurrentTabWhatToDo=309;
				else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==310)
					StaticVariables.fragmentIndexCurrentTabWhatToDo=311;
				else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==312)
					StaticVariables.fragmentIndexCurrentTabWhatToDo=313;

				switchingFragments(new NetworkDriversFragment());
			}
		});

		//viewInsights.setBackgroundColor(Color.parseColor(backgroundColorInterestDrivers[0]));

		int counter=0;
		//getInterestTraits.clear();
		for(int j=0;j<interestIdArrayList.size();j++)
		{

			if(counter<=4)
			{

				counter++;
				LayoutInflater layoutInflaterCircle = (LayoutInflater) getActivity()
						.getApplicationContext().getSystemService(
								Context.LAYOUT_INFLATER_SERVICE);
				View viewInsightsCircle = layoutInflaterCircle.inflate(R.layout.layout_circlenetwork, null);

				//ImageView arrowInsight=(ImageView) viewInsightsCircle.findViewById(R.id.imageInterestTrendsCircle);
				ImageView imageInterestImageId=(ImageView) viewInsightsCircle.findViewById(R.id.viewInterestTrendsCircle);
				imageInterestImageId.setImageResource(arrayInterestTrendsImages[Integer.parseInt(interestIdArrayList.get(j))]);
				circleLinearLayout.addView(viewInsightsCircle);

			}

		}

		linearlayoutnetworkinterestdrivers.addView(viewInsights);

	}




	private void hideKeyBoard() {



		try {
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(), 0);
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}

	}


	private Bitmap getRoundedRectBitmap(Bitmap bitmap) 
	{

		int pixels;

		if(SplashActivity.ScreenWidth >= 1000)
		{
			pixels = 227;

		}
		else if(SplashActivity.ScreenWidth >= 700)
		{
			pixels = 170;

		}
		else if(SplashActivity.ScreenWidth >= 590)
		{
			pixels = 128;
		}
		else
		{
			pixels = 100;
		}

		Bitmap result = null;
		try {
			bitmap = Bitmap.createScaledBitmap(bitmap, pixels, pixels, false);

			result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Bitmap.Config.ARGB_8888);

			Canvas canvas = new Canvas(result);

			int color = 0xFF424242;
			Paint paint = new Paint();

			Rect rect = new Rect(0, 0, pixels, pixels);
			RectF rectF = new RectF(rect);
			int roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (NullPointerException e) {
			// return bitmap;
		} catch (OutOfMemoryError o) {
		}
		return result;
	}







}
