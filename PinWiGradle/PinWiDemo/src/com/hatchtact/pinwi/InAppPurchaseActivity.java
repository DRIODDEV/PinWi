package com.hatchtact.pinwi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.fragment.insights.SubscribeFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.util.IabHelper;
import com.hatchtact.pinwi.util.IabResult;
import com.hatchtact.pinwi.util.Inventory;
import com.hatchtact.pinwi.util.Purchase;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

import static com.hatchtact.pinwi.R.id.tvtxtone;
import static com.hatchtact.pinwi.R.id.yearlyprice;
import static com.hatchtact.pinwi.R.id.yearlytext;

public class InAppPurchaseActivity extends MainActionBarActivity
{
	static final String TAG = "PinWi";
	// SKU for our subscription (3month)
	static final String INAPP_PAIDAPP ="pinwifullaccess";
	//static final String INAPP_PAIDAPP ="paidapp";

	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;
	// The helper object
	public static IabHelper mHelper;
	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;
	private View layoutPurchasePaid;
	private TextView paidtext,paidprice,paidtotalprice;
	private TypeFace typeFace=null;
	private SharePreferenceClass sharePreferenceClass;
	private TextView tvtxtone,tvtxttwo,tvtxtthree;
	private TextView txtParent,txtChild;
	private View layoutOne,layoutTwo,layoutThree,layoutFour,layoutFive;
	private View layoutChildOne,layoutChildTwo,layoutChildThree,layoutChildFour,layoutChildFive,layoutChildSix,layoutChildSeven;
	private TextView txtPointOneParent,txtPointTwoParent,txtPointThreeParent,txtPointFourParent,txtPointFiveParent;
	private TextView txtPointOneChild,txtPointTwoChild,txtPointThreeChild,txtPointFourChild,txtPointFiveChild,txtPointSixChild,txtPointSevenChild;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		screenName=" Full Version";

		super.onCreate(savedInstanceState);
		/*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.x =0;
		params.height = (int) (SplashActivity.ScreenHeight*.7f);
		params.width = SplashActivity.ScreenWidth ;
		params.y = SplashActivity.ScreenHeight/15;
		this.getWindow().setAttributes(params);*/
		setContentView(R.layout.activity_inapp);
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(this);
		sharePreferenceClass=new SharePreferenceClass(this);
		checkNetwork=new CheckNetwork();
		typeFace=new TypeFace(this);
		init();
	}

	/**
	 *
	 */
	private void init() {
		layoutPurchasePaid=findViewById(R.id.layoutYearly);
		tvtxtone=(TextView)findViewById(R.id.tvtxtone);
		tvtxttwo=(TextView) findViewById(R.id.tvtxttwo);
		tvtxtthree=(TextView) findViewById(R.id.tvtxtthree);
		txtParent=(TextView) findViewById(R.id.txtParent);
		txtChild=(TextView) findViewById(R.id.txtChild);

		layoutOne=findViewById(R.id.layoutOne);
		layoutTwo=findViewById(R.id.layoutTwo);
		layoutThree=findViewById(R.id.layoutThree);
		layoutFour=findViewById(R.id.layoutFour);
		layoutFive=findViewById(R.id.layoutFive);
		layoutChildOne=findViewById(R.id.layoutChildOne);
		layoutChildTwo=findViewById(R.id.layoutChildTwo);
		layoutChildThree=findViewById(R.id.layoutChildThree);
		layoutChildFour=findViewById(R.id.layoutChildFour);
		layoutChildFive=findViewById(R.id.layoutChildFive);
		layoutChildSix=findViewById(R.id.layoutChildSix);
		layoutChildSeven=findViewById(R.id.layoutChildSeven);

		txtPointOneParent= (TextView) layoutOne.findViewById(R.id.txtPoint);
		txtPointTwoParent= (TextView) layoutTwo.findViewById(R.id.txtPoint);
		txtPointThreeParent= (TextView) layoutThree.findViewById(R.id.txtPoint);
		txtPointFourParent= (TextView) layoutFour.findViewById(R.id.txtPoint);
		txtPointFiveParent= (TextView) layoutFive.findViewById(R.id.txtPoint);

		txtPointOneChild= (TextView) layoutChildOne.findViewById(R.id.txtPoint);
		txtPointTwoChild= (TextView) layoutChildTwo.findViewById(R.id.txtPoint);
		txtPointThreeChild= (TextView) layoutChildThree.findViewById(R.id.txtPoint);
		txtPointFourChild= (TextView) layoutChildFour.findViewById(R.id.txtPoint);
		txtPointFiveChild= (TextView) layoutChildFive.findViewById(R.id.txtPoint);
		txtPointSixChild= (TextView) layoutChildSix.findViewById(R.id.txtPoint);
		txtPointSevenChild= (TextView) layoutChildSeven.findViewById(R.id.txtPoint);


		paidtext=(TextView) layoutPurchasePaid.findViewById(yearlytext);
		paidprice=(TextView) layoutPurchasePaid.findViewById(yearlyprice);
		paidtotalprice=(TextView) layoutPurchasePaid.findViewById(R.id.totalprice);

		paidtext.setText("Upgrade");
		paidprice.setText(getResources().getString(R.string.Rs)+" 600 / Lifetime");

		paidtotalprice.setVisibility(View.INVISIBLE);
		typeFace.setTypefaceRegular(paidtext);
		typeFace.setTypefaceRegular(paidprice);
		typeFace.setTypefaceRegular(paidtotalprice);
		typeFace.setTypefaceRegular(tvtxtone);
		typeFace.setTypefaceRegular(tvtxttwo);
		typeFace.setTypefaceRegular(tvtxtthree);
		typeFace.setTypefaceRegular(txtParent);
		typeFace.setTypefaceRegular(txtChild);

		typeFace.setTypefaceRegular(txtPointOneParent);
		typeFace.setTypefaceRegular(txtPointTwoParent);
		typeFace.setTypefaceRegular(txtPointThreeParent);
		typeFace.setTypefaceRegular(txtPointFourParent);
		typeFace.setTypefaceRegular(txtPointFiveParent);
		typeFace.setTypefaceRegular(txtPointOneChild);
		typeFace.setTypefaceRegular(txtPointTwoChild);
		typeFace.setTypefaceRegular(txtPointThreeChild);
		typeFace.setTypefaceRegular(txtPointFourChild);
		typeFace.setTypefaceRegular(txtPointFiveChild);
		typeFace.setTypefaceRegular(txtPointSixChild);
		typeFace.setTypefaceRegular(txtPointSevenChild);

		txtPointOneParent.setText(getResources().getStringArray(R.array.parent_features_inapp)[0]);
		txtPointTwoParent.setText(getResources().getStringArray(R.array.parent_features_inapp)[1]);
		txtPointThreeParent.setText(getResources().getStringArray(R.array.parent_features_inapp)[2]);
		txtPointFourParent.setText(getResources().getStringArray(R.array.parent_features_inapp)[3]);
		txtPointFiveParent.setText(getResources().getStringArray(R.array.parent_features_inapp)[4]);

		txtPointOneChild.setText(getResources().getStringArray(R.array.child_features_inapp)[0]);
		txtPointTwoChild.setText(getResources().getStringArray(R.array.child_features_inapp)[1]);
		txtPointThreeChild.setText(getResources().getStringArray(R.array.child_features_inapp)[2]);
		txtPointFourChild.setText(getResources().getStringArray(R.array.child_features_inapp)[3]);
		txtPointFiveChild.setText(getResources().getStringArray(R.array.child_features_inapp)[4]);
		txtPointSixChild.setText(getResources().getStringArray(R.array.child_features_inapp)[5]);
		txtPointSevenChild.setText(getResources().getStringArray(R.array.child_features_inapp)[6]);

		layoutPurchasePaid.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				subscribeProcess();
			}
		});

		tvtxttwo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(InAppPurchaseActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/privacy_policy.html";
			}
		});
		tvtxtthree.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(InAppPurchaseActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/terms.html";
			}
		});
	}
	private void subscribeProcess()
	{
		//have to change the key
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhayYPDKgktRz9yDpBpHX8Bywko6tRZcfntvkAlW/xAKBD1nVWb+rGbLf48FUnHgFuY+nniY+GD/n3QEJzUDZng/cUWZ56/SFZVbxGlBQXiOW/qEfdyDGy+jrNP0N+YMMKwzEc73U8+4S3qkSdxyCm9EgEuJPDrV9B+ziJMSX4cqMKiBtsLqvEwsiZF1XrUa4fmFOZIJHpxlWqhQEiIK0dDBpc7Txc5U63SxqwK7+9RaKYCznO4IeCGEBTn+4EhRoSKCtzrXaZRmJ/3tX8xex3SH7JVNQFjhVQgCX1oUX/2PHYUOPuerYUjC5fqeytfzS54RMcbJw06stXZEjP7zgvwIDAQAB";

		// Create the helper, passing it our context and the public key to verify signatures with
		Log.d(TAG, "Creating IAB helper.");
		mHelper = new IabHelper(this, base64EncodedPublicKey);
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
				mHelper.launchPurchaseFlow(InAppPurchaseActivity.this,
						INAPP_PAIDAPP, IabHelper.ITEM_TYPE_INAPP,
						RC_REQUEST, mPurchaseFinishedListener, payload);

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
			Purchase inappPaidPurchase = inventory.getPurchase(INAPP_PAIDAPP);
			if (inappPaidPurchase != null && verifyDeveloperPayload(inappPaidPurchase)) {
				mHelper.consumeAsync(inventory.getPurchase(INAPP_PAIDAPP), mConsumeFinishedListener);
				return;
			}

			//updateUi();
			//setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};


	 @Override
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
        }
    }

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

			if (purchase.getSku().equals(INAPP_PAIDAPP)) {
				// bought 1/4 tank of gas. So consume it.
				//alert("Thank you for subscribing to  twelve month!");
				//mHelper.consumeAsync(purchase, mConsumeFinishedListener);
				/*social.Insights_SubscribedFacebookLog("Annual");
				social.Insights_SubscribedGoogleAnalyticsLog("Annual");*/
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

				mHelper.launchPurchaseFlow(InAppPurchaseActivity.this,
						INAPP_PAIDAPP, IabHelper.ITEM_TYPE_SUBS,
						RC_REQUEST, mPurchaseFinishedListener, payload);



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
		AlertDialog.Builder bld = new AlertDialog.Builder(InAppPurchaseActivity.this);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
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

			progressDialog = ProgressDialog.show(InAppPurchaseActivity.this,"", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(InAppPurchaseActivity.this))
			{
				reportStatus =serviceMethod.addInAppPurchase(StaticVariables.currentParentId,"1");
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

				//if(reportStatus.trim().equalsIgnoreCase("0"))
				{
					sharePreferenceClass.setIsLogin(true);
					Intent intent=new Intent(InAppPurchaseActivity.this, AccessProfileActivity.class);
					startActivity(intent);
					sharePreferenceClass.setCurrentScreen(4);
					finish();
				}
				/*else
				{
					showMessage.showToastMessage("Purchase Not Successful");
				}*/
			}

		}
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(StaticVariables.screenForPurchase==1)
		{
			finish();
			Intent intent=new Intent(InAppPurchaseActivity.this, GetStartedActivity.class);
			startActivity(intent);
		}
		else if(StaticVariables.screenForPurchase==2)
		{
			finish();
			Intent intent=new Intent(InAppPurchaseActivity.this, WebContainerFreeAppActivity.class);
			startActivity(intent);
		}
		else
			finish();
	}



}
