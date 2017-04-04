package com.hatchtact.pinwi.fragment.network;


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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.adapter.FriendDetailsAdapter;
import com.hatchtact.pinwi.classmodel.GetFriendDetailsByFriendID;
import com.hatchtact.pinwi.classmodel.GetFriendDetailsByFriendIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public  class FriendDetailFragment extends ParentFragment 
{
	private View view;
	private SharePreferenceClass sharePref;

	private ListView listchild_network;
	private TextView parentDetailheading,childDetailHeading;

	private ServiceMethod servicemethod;
	private CheckNetwork checkNetwork;
	private ShowMessages showMessage;
	private GetFriendDetailsByFriendIDList getFriendsDetailList;
	private FriendDetailsAdapter adapter;
	private TextView txtChildrenName;
	private Button btn_networkconnectionitem;
	private TextView txtParentName;
	private ImageView imgUser;
	private String parentStatus="";


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
			view=inflater.inflate(R.layout.frienddetails_network, container, false);
			setHasOptionsMenu(true);
			mListener.onFragmentAttached(false,"  Network");
			initialize();//initialize all view items in fragment
			checkNetwork=new CheckNetwork();
			showMessage=new ShowMessages(getActivity());
			servicemethod=new ServiceMethod();
			getFriendsDetailList=new GetFriendDetailsByFriendIDList();
			new fetchFriendsDetailList().execute();	
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
		listchild_network=(ListView) view.findViewById(R.id.listchildrenparentdetail_network);
		imgUser=(ImageView) view.findViewById(R.id.imgUser);

		parentDetailheading.setVisibility(View.GONE);
		childDetailHeading.setVisibility(View.GONE);
		txtParentName.setVisibility(View.GONE);
		btn_networkconnectionitem.setVisibility(View.GONE);
		txtChildrenName.setVisibility(View.GONE);
		imgUser.setVisibility(View.GONE);
		
		
		typeFace.setTypefaceRegular(parentDetailheading);
		typeFace.setTypefaceRegular(childDetailHeading);
		typeFace.setTypefaceRegular(txtParentName);
		typeFace.setTypefaceRegular(btn_networkconnectionitem);
		typeFace.setTypefaceRegular(txtChildrenName);
		
		if(StaticVariables.ClusterName.startsWith("Conn"))
		{
			
			StaticVariables.screenIndex=0;
		}
		else
		{
			if(StaticVariables.ClusterName.startsWith("Dis"))
			{
				StaticVariables.screenIndex=2;
			}
			else
			{
				StaticVariables.screenIndex=1;
			}

		}

		listchild_network.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(parentStatus.equalsIgnoreCase("1"))
				{
					//call children details screen here.
					final GetFriendDetailsByFriendID model= getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(position);
					StaticVariables.fragmentIndexCurrentTabNetwork=205;
					StaticVariables.NetworkChildId=model.getChildID();
					switchingFragments(new ChildDetailFragment());
				}
				else
				{
					showMessage.showAlert("Warning", "Add this parent to your Network to view their child's detail" );
				}
			}
		});
	}

	public static FriendDetailFragment fr;

	public static FriendDetailFragment getInstance()
	{
		if(fr==null)
		{
			fr = new FriendDetailFragment();
		}
		return fr;
	}


	// the create options menu with a MenuInflater to have the menu from your fragment
	/*@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		for(int i=0;i<menu.size();i++)
		{
			menu.getItem(i).setVisible(true);
		}
		menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());
		super.onCreateOptionsMenu(menu, inflater);
	}  


	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
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
	
	//private ProgressDialog progressDialog=null;

	private class fetchFriendsDetailList extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
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
				getFriendsDetailList =servicemethod.getFriendDetails(StaticVariables.currentParentId, StaticVariables.FriendId);
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
				customProgressLoader.dismissProgressBar();
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
					new fetchFriendsDetailList().execute();
			}
			else
			{
				if(getFriendsDetailList!=null && getFriendsDetailList.getGetFriendDetailsByFriendIDList().size()>0)
				{
					parentDetailheading.setVisibility(View.VISIBLE);
					childDetailHeading.setVisibility(View.VISIBLE);
					txtParentName.setVisibility(View.VISIBLE);
					btn_networkconnectionitem.setVisibility(View.VISIBLE);
					txtChildrenName.setVisibility(View.VISIBLE);
					imgUser.setVisibility(View.VISIBLE);
					setParentData();
					if(getFriendsDetailList.getGetFriendDetailsByFriendIDList().size()>1)
					{
						if(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(1)!=null /*&&getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(1).getChildID().trim().length()>0*/ )
						{
							adapter=new FriendDetailsAdapter(getActivity(),getFriendsDetailList);
							listchild_network.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}
					}
				}
				else
				{
					getError();
				}	
			}	
		}

		/**
		 * 
		 */
		private void setParentData()
		{
			final GetFriendDetailsByFriendID model= getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0);
			parentStatus=model.getFStatus();
			txtParentName.setText(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getFriendName());
			txtChildrenName.setText(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getCityName());

			if(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getProfileImage()!=null && getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getProfileImage().trim().length()>100)
			{
				byte[] imageByteParent=Base64.decode(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getProfileImage(), 0);
				if(imageByteParent!=null)
				{
					imgUser.setBackgroundResource(0);	
					imgUser.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length)));	
				}
			}
			else
			{
				imgUser.setBackgroundResource(R.drawable.parent_image);	
				imgUser.setImageBitmap(null);
			}
			if(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getFStatus().equalsIgnoreCase("1"))
			{
				btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
				btn_networkconnectionitem.setTextColor(getResources().getColor(R.color.font_red_color));
				btn_networkconnectionitem.setText("Remove");
				btn_networkconnectionitem.setEnabled(true);
				btn_networkconnectionitem.setAlpha(1f);
			}
			else if(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getFStatus().equalsIgnoreCase("5"))
			{
				btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_bluecolornetwork);
				btn_networkconnectionitem.setTextColor(getResources().getColor(R.color.font_blue_color));
				btn_networkconnectionitem.setText(" Add  ");
				btn_networkconnectionitem.setEnabled(true);
				btn_networkconnectionitem.setAlpha(1f);

			}
			else if(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getFStatus().equalsIgnoreCase("0"))//if needed more casses can be inserted.
			{
				btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_bluecolornetwork);
				btn_networkconnectionitem.setTextColor(getResources().getColor(R.color.font_blue_color));
				btn_networkconnectionitem.setText(" Sent  ");
				btn_networkconnectionitem.setEnabled(false);
				btn_networkconnectionitem.setAlpha(.5f);

			}
			else if(getFriendsDetailList.getGetFriendDetailsByFriendIDList().get(0).getFStatus().equalsIgnoreCase("2"))
			{
				btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_greencolornetwork);
				btn_networkconnectionitem.setTextColor(getResources().getColor(R.color.network_green));
				btn_networkconnectionitem.setText("Accept");
				btn_networkconnectionitem.setEnabled(true);
				btn_networkconnectionitem.setAlpha(1f);
			}

			imgUser.setFocusable(true);
			btn_networkconnectionitem.setFocusable(true);
			imgUser.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//zoom screen activity call	
					Intent intent=new Intent(getActivity(),ZoomScreenActivity.class);
					//intent.putExtra("byteArray", model.getProfileImage());
					StaticVariables.byteArray=model.getProfileImage();
					startActivity(intent);
				}
			});

			btn_networkconnectionitem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//sending request.
					//accepting request
					//removing connection
					if(model.getFStatus().equalsIgnoreCase("1"))
					{
						showMessage.showAlertNetwork(getActivity(),new OnEventListener<String>() {
							@Override
							public void onSuccess(String object) {
								// TODO Auto-generated method stub
								// <----- This is the key 
								new CustomAsyncTask(getActivity(), model.getFriendID(), "3", StaticVariables.currentParentId+"",1, new OnEventListener<String>() {

									@Override
									public void onSuccess(String object) 
									{
										// TODO Auto-generated method stub
										//getFriendsDetailList.getGetFriendDetailsByFriendIDList().remove(0);
										//notifyDataSetChanged();
										StaticVariables.fragmentIndexCurrentTabNetwork=201;
										switchingFragments(new NetworkConnectionsFragment());
									}

									@Override
									public void onFailure(String object) {
										// TODO Auto-generated method stub
										//net not available
									}
								}).execute();
							}

							@Override
							public void onFailure(String object) 
							{
								// TODO Auto-generated method stub
							}
						});

					}
					else if(model.getFStatus().equalsIgnoreCase("5"))
					{
						new CustomAsyncTask(getActivity(), model.getFriendID(), "0", StaticVariables.currentParentId+"",0, new OnEventListener<String>() {

							@Override
							public void onSuccess(String object) 
							{
								// TODO Auto-generated method stub
								model.setFStatus("0");
								btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_bluecolornetwork);
								btn_networkconnectionitem.setTextColor(getResources().getColor(R.color.font_blue_color));
								btn_networkconnectionitem.setText(" Sent  ");
								btn_networkconnectionitem.setEnabled(false);
								btn_networkconnectionitem.setAlpha(.5f);
							}

							@Override
							public void onFailure(String object) 
							{
								// TODO Auto-generated method stub
								//net not available
							}
						}).execute();
					}

					else if(model.getFStatus().equalsIgnoreCase("2") /*|| model.getFStatus().equalsIgnoreCase("2")*/)				
					{
						new CustomAsyncTask(getActivity(), model.getFriendID(), "1", StaticVariables.currentParentId+"",1, new OnEventListener<String>() {
							@Override
							public void onSuccess(String object) 
							{
								// TODO Auto-generated method stub
								model.setFStatus("1");
								btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
								btn_networkconnectionitem.setTextColor(getResources().getColor(R.color.font_red_color));
								btn_networkconnectionitem.setText("Remove");
								StaticVariables.fragmentIndexCurrentTabNetwork=202;
								switchingFragments(new NetworkRequestFragment());
							}

							@Override
							public void onFailure(String object) 
							{
								// TODO Auto-generated method stub
								//net not available
							}
						}).execute();
					}

				}
			});
		}	
	}

	private void getError()
	{
		com.hatchtact.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
	}

	private void hideKeyBoard() 
	{
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
			result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),Bitmap.Config.ARGB_8888);
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
