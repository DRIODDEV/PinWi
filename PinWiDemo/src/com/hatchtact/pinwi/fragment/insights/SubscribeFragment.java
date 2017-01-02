package com.hatchtact.pinwi.fragment.insights;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.util.IabHelper;
import com.hatchtact.pinwi.util.IabResult;
import com.hatchtact.pinwi.util.Inventory;
import com.hatchtact.pinwi.util.Purchase;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class SubscribeFragment extends ParentFragment  
{

	private View view;
	public static SubscribeFragment subscribefragment;
	private View layoutYearly,layoutQuarterly;
	private TextView tvtxtone,tvtxttwo;
	private ImageView imgBenifits;
	private TextView yearlytext,yearlyprice,yearlytotalprice,quarterlyytext,quarterlyprice,quarterlytotalprice;
	private Typeface typeface;
	private LinearLayout layoutSubscribecardYearly;
	private LinearLayout layoutSubscribecardQuarterly;
	private int subtypesubscribe=0;


	static final String TAG = "PinWi";
	// SKU for our subscription (3month)
	static final String SKU_THREEMONTH = /*"Pinwi3MonthSubscription"*/"pinwi3months";
	static final String SKU_TWELVEMONTH = /*"Pinwi12MonthSubscription"*/"pinwi12months";

	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;
	// The helper object
	public static IabHelper mHelper;
	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;
	private boolean isSubscribeStarted=false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.fragment_subscription, container, false);
		mListener.onFragmentAttached(false,"  Unlock Insights");
		setHasOptionsMenu(true);
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());

		checkNetwork=new CheckNetwork();

		init();
		yearlytext.setText("Yearly"/*StaticVariables.subscriptionTextArray[0]*/);
		yearlyprice.setText(/*getActivity().getResources().getString(R.string.Rs)+" "+*/StaticVariables.subscriptionTextArray[0]/*+"/mo"*/);
		yearlytotalprice.setText("Total "+getActivity().getResources().getString(R.string.Rs)+" "+StaticVariables.subscriptionCostArray[0]+"/-");
		quarterlyytext.setText("Quarterly"/*StaticVariables.subscriptionTextArray[1]*/);
		quarterlyprice.setText(/*getActivity().getResources().getString(R.string.Rs)+" "+*/StaticVariables.subscriptionTextArray[1]/*+"/mo"*/);
		quarterlytotalprice.setText("Total "+getActivity().getResources().getString(R.string.Rs)+" "+StaticVariables.subscriptionCostArray[1]+"/-");
		//getActivity().invalidateOptionsMenu();

		layoutYearly.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isSubscribeStarted)
				{
					isSubscribeStarted=true;
					subscribeProcess(0);
				}
			}
		});

		layoutQuarterly.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isSubscribeStarted)
				{
					isSubscribeStarted=true;
					subscribeProcess(1);
				}
			}
		});
		return view;		
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		isSubscribeStarted=false;
	}
	private void subscribeProcess(int subtype)
	{
		this.subtypesubscribe=subtype;
		//have to change the key
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhayYPDKgktRz9yDpBpHX8Bywko6tRZcfntvkAlW/xAKBD1nVWb+rGbLf48FUnHgFuY+nniY+GD/n3QEJzUDZng/cUWZ56/SFZVbxGlBQXiOW/qEfdyDGy+jrNP0N+YMMKwzEc73U8+4S3qkSdxyCm9EgEuJPDrV9B+ziJMSX4cqMKiBtsLqvEwsiZF1XrUa4fmFOZIJHpxlWqhQEiIK0dDBpc7Txc5U63SxqwK7+9RaKYCznO4IeCGEBTn+4EhRoSKCtzrXaZRmJ/3tX8xex3SH7JVNQFjhVQgCX1oUX/2PHYUOPuerYUjC5fqeytfzS54RMcbJw06stXZEjP7zgvwIDAQAB";

		// Create the helper, passing it our context and the public key to verify signatures with
		Log.d(TAG, "Creating IAB helper.");
		mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
		// enable debug logging (for a production application, you should set this to false).
		mHelper.enableDebugLogging(false);

		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					complain("Problem setting up in-app billing: " + result);
					return;
				}

				// Have we been disposed of in the meantime? If so, quit.
				if (mHelper == null) return;

				// IAB is fully set up. Now, let's get an inventory of stuff we own.
				Log.d(TAG, "Setup successful. Querying inventory.");
				String payload = "";
				switch (subtypesubscribe) 
				{
				case 0:
					//alert("Consum,ed Twelve month");
					mHelper.launchPurchaseFlow(getActivity(),
							SKU_TWELVEMONTH, IabHelper.ITEM_TYPE_SUBS,
							RC_REQUEST, mPurchaseFinishedListener, payload);

					break;
				case 1:
					//alert("Consum,ed Three month");
					mHelper.launchPurchaseFlow(getActivity(),
							SKU_THREEMONTH, IabHelper.ITEM_TYPE_SUBS,
							RC_REQUEST, mPurchaseFinishedListener, payload);

					break;
				default:
					break;
				}
				//mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}


	// Listener that's called when we finish querying the items and subscriptions we own
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");

			// Have we been disposed of in the meantime? If so, quit.
			if (mHelper == null) return;

			// Is it a failure?
			if (result.isFailure()) {
				complain("Failed to query inventory: " + result);
				return;
			}

			Log.d(TAG, "Query inventory was successful.");

			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */
			// Do we have the infinite gas plan?
			Purchase threemonthpurchase = inventory.getPurchase(SKU_THREEMONTH);
			// Check for gas delivery -- if we own gas, we should fill up the tank immediately
			Purchase twelvemonthpurchase = inventory.getPurchase(SKU_TWELVEMONTH);
			switch (subtypesubscribe) {
			case 0:
				if (twelvemonthpurchase != null && verifyDeveloperPayload(twelvemonthpurchase)) {
					Log.d(TAG, "We have twelve month plan. Consuming it.");
					mHelper.consumeAsync(inventory.getPurchase(SKU_TWELVEMONTH), mConsumeFinishedListener);
					return;
				}
				break;
			case 1:
				if (threemonthpurchase != null && verifyDeveloperPayload(threemonthpurchase)) {
					Log.d(TAG, "We have three month plan. Consuming it.");
					mHelper.consumeAsync(inventory.getPurchase(SKU_THREEMONTH), mConsumeFinishedListener);
					return;
				}
				break;

			default:
				break;
			}



			//updateUi();
			//setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};


	/* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }*/

	/** Verifies the developer payload of a purchase. */
	boolean verifyDeveloperPayload(Purchase p) {
		String payload = p.getDeveloperPayload();

		/*
		 * TODO: verify that the developer payload of the purchase is correct. It will be
		 * the same one that you sent when initiating the purchase.
		 *
		 * WARNING: Locally generating a random string when starting a purchase and
		 * verifying it here might seem like a good approach, but this will fail in the
		 * case where the user purchases an item on one device and then uses your app on
		 * a different device, because on the other device you will not have access to the
		 * random string you originally generated.
		 *
		 * So a good developer payload has these characteristics:
		 *
		 * 1. If two different users purchase an item, the payload is different between them,
		 *    so that one user's purchase can't be replayed to another user.
		 *
		 * 2. The payload must be such that you can verify it even when the app wasn't the
		 *    one who initiated the purchase flow (so that items purchased by the user on
		 *    one device work on other devices owned by the user).
		 *
		 * Using your own server to store and verify developer payloads across app
		 * installations is recommended.
		 */

		return true;
	}

	// Callback for when a purchase is finished
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

			// if we were disposed of in the meantime, quit.
			if (mHelper == null) return;

			if (result.isFailure()) {
			//	complain("Error purchasing: " + result);
				//setWaitScreen(false);
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
			//	complain("Error purchasing. Authenticity verification failed.");
				//setWaitScreen(false);
				return;
			}

			Log.d(TAG, "Purchase successful.");

			if (purchase.getSku().equals(SKU_TWELVEMONTH)) {
				// bought 1/4 tank of gas. So consume it.
				//alert("Thank you for subscribing to  twelve month!");
				//mHelper.consumeAsync(purchase, mConsumeFinishedListener);
				social.Insights_SubscribedFacebookLog("Annual");
				social.Insights_SubscribedGoogleAnalyticsLog("Annual");
				new AddSubscriptionTask().execute();
			}
			else if (purchase.getSku().equals(SKU_THREEMONTH)) {
				// bought the infinite gas subscription
				//Log.d(TAG, "Infinite gas subscription purchased.");
				//alert("Thank you for subscribing to  three month!");
				//updateUi();
				//setWaitScreen(false);
				social.Insights_SubscribedFacebookLog("Quarter");
				social.Insights_SubscribedGoogleAnalyticsLog("Quarter");

				new AddSubscriptionTask().execute();
			}
		}
	};

	// Called when consumption is complete
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

			// if we were disposed of in the meantime, quit.
			if (mHelper == null) return;

			// We know this is the "gas" sku because it's the only one we consume,
			// so we don't check which sku was consumed. If you have more than one
			// sku, you probably should check...
			if (result.isSuccess()) {
				// successfully consumed, so we apply the effects of the item in our
				// game world's logic, which in our case means filling the gas tank a bit
				Log.d(TAG, "Consumption successful. Provisioning.");
				//saveData();
				String payload = "";
				switch (subtypesubscribe) {
				case 0:
					alert("Consum,ed Twelve month");
					mHelper.launchPurchaseFlow(getActivity(),
							SKU_TWELVEMONTH, IabHelper.ITEM_TYPE_SUBS,
							RC_REQUEST, mPurchaseFinishedListener, payload);

					break;
				case 1:
					alert("Consum,ed Three month");
					mHelper.launchPurchaseFlow(getActivity(),
							SKU_THREEMONTH, IabHelper.ITEM_TYPE_SUBS,
							RC_REQUEST, mPurchaseFinishedListener, payload);

					break;
				default:
					break;
				}

			}
			else {
				complain("Error while consuming: " + result);
			}
			// updateUi();
			// setWaitScreen(false);
			Log.d(TAG, "End consumption flow.");
		}
	};






	void complain(String message) {
		Log.e(TAG, "**** TrivialDrive Error: " + message);
		alert("Error: " + message);
	}

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}



	/**
	 * 
	 */
	private void init() {
		layoutYearly=view.findViewById(R.id.layoutYearly);
		layoutQuarterly=view.findViewById(R.id.layoutQuarterly);

		tvtxtone=(TextView) view.findViewById(R.id.tvtxtone);
		tvtxttwo=(TextView) view.findViewById(R.id.tvtxttwo);
		imgBenifits=(ImageView) view.findViewById(R.id.imgBenifits);

		yearlytext=(TextView) layoutYearly.findViewById(R.id.yearlytext);
		yearlyprice=(TextView) layoutYearly.findViewById(R.id.yearlyprice);
		yearlytotalprice=(TextView) layoutYearly.findViewById(R.id.totalprice);
		quarterlyytext=(TextView) layoutQuarterly.findViewById(R.id.yearlytext);
		quarterlyprice=(TextView) layoutQuarterly.findViewById(R.id.yearlyprice);
		quarterlytotalprice=(TextView) layoutQuarterly.findViewById(R.id.totalprice);
		layoutSubscribecardYearly=(LinearLayout) layoutYearly.findViewById(R.id.layoutSubscribecard);
		layoutSubscribecardQuarterly=(LinearLayout) layoutQuarterly.findViewById(R.id.layoutSubscribecard);

		layoutSubscribecardQuarterly.setBackgroundResource(0);
		quarterlyytext.setBackgroundColor(getActivity().getResources().getColor(R.color.dark_gray));

		typeFace=new TypeFace(getActivity());
		typeFace.setTypefaceRegular(tvtxtone);
		typeFace.setTypefaceRegular(tvtxttwo);
		typeFace.setTypefaceRegular(yearlytext);
		typeFace.setTypefaceRegular(yearlyprice);
		typeFace.setTypefaceRegular(yearlytotalprice);
		typeFace.setTypefaceRegular(quarterlyytext);
		typeFace.setTypefaceRegular(quarterlyprice);
		typeFace.setTypefaceRegular(quarterlytotalprice);
	}









	public static SubscribeFragment getInstance()
	{
		if(subscribefragment==null)
		{
			subscribefragment = new SubscribeFragment();
		}
		return subscribefragment;
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
				menu.findItem(R.id.action_childName).setTitle(/*StaticVariables.currentChild.getFirstName()*/StaticVariables.currentParentName);
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


	private ProgressDialog progressDialog=null;	
	private String  reportStatus;

	private class AddSubscriptionTask extends AsyncTask<Void, Void, Integer>
	{
		int parentId;
		int childId;


		public AddSubscriptionTask()
		{

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
				reportStatus =serviceMethod.addSubscription(StaticVariables.currentParentId,new SharePreferenceClass(getActivity()).getDeviceId(),1,StaticVariables.subscriptionTypeArray[subtypesubscribe],StaticVariables.subscriptionCostArray[subtypesubscribe]);

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
				//showMessage.showToastMessage("Please check your network connection");


				/*if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetInsightsReportStatusByParentAndChildId(parentId,childId).execute();*/

			}
			else
			{

				if(reportStatus.trim().equalsIgnoreCase("0"))
				{
					getActivity().onBackPressed();

				}
				else
				{
					showMessage.showToastMessage("Purchase Not Successful");
				}
			}

		}	
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}


}
