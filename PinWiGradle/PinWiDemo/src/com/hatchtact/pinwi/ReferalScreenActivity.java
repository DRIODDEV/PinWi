package com.hatchtact.pinwi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetPaymentStatusCheck;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ReferalScreenActivity extends FragmentActivity
{

	private TextView referralHeading;
	private TextView referalDesc;
	private EditText editReferralCode;
	private TextView btnSubmit;
	private TextView btnNoThanks;
	private ImageView imageCross;
	private TypeFace typeFace;
	private CustomLoader customProgressLoader;
	private ServiceMethod serviceMethod;
	private int parentId=0;
	private String ReferCode="";
	private int referStatus=0;
	private String msgDialog="",parentEmail="";
	private SharePreferenceClass sharePreferenceclass=null;
	private LinearLayout layoutMainReferal;
	private int keyBoardHeight = 0;
	private SocialConstants social;
	private String IsOptForDontShowReferalCode="0";
	private boolean onTouchSubmitButton=false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = (int)(SplashActivity.ScreenWidth* .9f) ;
		this.getWindow().setAttributes(params);

		setContentView(R.layout.dialog_referral_enter);
		social=new SocialConstants(this);
		IsOptForDontShowReferalCode="0";
		parentId=StaticVariables.currentParentId;
		sharePreferenceclass=new SharePreferenceClass(ReferalScreenActivity.this);
		onTouchSubmitButton=false;
		try {
			referStatus = getIntent().getExtras().getInt("ReferStatus");
			msgDialog = getIntent().getExtras().getString("Message");
			parentEmail = getIntent().getExtras().getString("Email");
		}
		catch (Exception e)
		{
			msgDialog="";
		}

		customProgressLoader=new CustomLoader(ReferalScreenActivity.this);
		serviceMethod=new ServiceMethod();
		layoutMainReferal= (LinearLayout) findViewById(R.id.layoutMainReferal);
		if(referStatus==0) {
			layoutMainReferal.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {

					if (isKeyBoardShown()) { // 99% of the time the height diff will be due to a keyboard.
						btnSubmit.setEnabled(true);
						btnSubmit.setAlpha(1f);
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

						params.gravity = Gravity.NO_GRAVITY;
						params.topMargin=50;

						layoutMainReferal.setLayoutParams(params);
						btnNoThanks.setVisibility(View.GONE);
					} else {
						if (editReferralCode.getText().toString().trim().length() > 0) {
							btnSubmit.setEnabled(true);
							btnSubmit.setAlpha(1f);
						} else {
							btnSubmit.setEnabled(false);
							btnSubmit.setAlpha(.4f);
						}
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

						params.gravity = Gravity.CENTER;
						params.topMargin=0;

						layoutMainReferal.setLayoutParams(params);
						btnNoThanks.setVisibility(View.VISIBLE);
					}
					/*Rect r = new Rect();
				layoutMainReferal.getWindowVisibleDisplayFrame(r);

				int screenHeight = layoutMainReferal.getRootView().getHeight();
				int heightDifference = screenHeight - (r.bottom - r.top);

				if (keyBoardHeight < 100)
					keyBoardHeight = heightDifference - 40;*/

				}
			});
		}
		ReferalScreenActivity.this.overridePendingTransition(R.anim.popup_show, R.anim.activity_close_scale);

		typeFace=new TypeFace(this);
		referralHeading=(TextView)findViewById(R.id.referralHeading);
		referalDesc=(TextView)findViewById(R.id.referalDesc);
		btnSubmit=(TextView)findViewById(R.id.btnSubmit);
		btnNoThanks=(TextView)findViewById(R.id.btnNoThanks);
		editReferralCode=(EditText)findViewById(R.id.editReferralCode);
		imageCross=(ImageView)findViewById(R.id.imageCross);
		typeFace.setTypefaceBold(referralHeading);
		typeFace.setTypefaceRegular(btnSubmit);
		typeFace.setTypefaceRegular(btnNoThanks);
		typeFace.setTypefaceLight(referalDesc);
		if(referStatus==0) {
			btnSubmit.setEnabled(false);
			btnSubmit.setAlpha(.4f);
		}

		if(referStatus==1)
		{
			referralHeading.setText("Hi "+StaticVariables.currentParentName+"!");
			referalDesc.setText(msgDialog);
			btnNoThanks.setVisibility(View.GONE);
			editReferralCode.setVisibility(View.GONE);
			btnSubmit.setText("Get Full Access");
		}
		else
		{
			btnNoThanks.setVisibility(View.VISIBLE);
			editReferralCode.setVisibility(View.VISIBLE);
		}

		imageCross.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				ReferalScreenActivity.this.overridePendingTransition(R.anim.activity_open_scale, R.anim.disappear);
			}
		});
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				if(referStatus==1)
				{
					if(!onTouchSubmitButton) {
						onTouchSubmitButton = true;
						new GetPaymentStatusCheckAsyncTask().execute();
					}

				}
				else
				{
					if(editReferralCode.getText().toString().trim().length()>0)
					{
						//api call
						ReferCode=editReferralCode.getText().toString().trim();
						IsOptForDontShowReferalCode="0";

						if(!onTouchSubmitButton)
						{
							onTouchSubmitButton = true;
							new RedeemReferralCodeTask().execute();
						}
					}
					else
					{
						new ShowMessages(ReferalScreenActivity.this).showToast("Please enter code.");
					}
				}
			}
		});
		btnNoThanks.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sharePreferenceclass.setPopUpReferalFlag(true,parentId+"");
				ReferCode="";
				IsOptForDontShowReferalCode="1";
				new RedeemReferralCodeTask().execute();
				/*finish();
				ReferalScreenActivity.this.overridePendingTransition(R.anim.activity_open_scale, R.anim.disappear);*/

			}
		});

		editReferralCode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnSubmit.setEnabled(true);
				btnSubmit.setAlpha(1f);
			}
		});

		editReferralCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_NEXT)
				{
					hideKeyBoard();
					return true;
				} else if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					hideKeyBoard();
					return true;
				}

				else
				{
					hideKeyBoard();
					return true;
				}

			}
		});
		hideKeyBoard();
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		ReferalScreenActivity.this.overridePendingTransition(R.anim.activity_open_scale, R.anim.disappear);
		//super.onBackPressed();
	}
	private void hideKeyBoard()
	{
		try {
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}

	}



	private class RedeemReferralCodeTask extends AsyncTask<Void, Void, Integer>
	{


		public RedeemReferralCodeTask()
		{
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}

		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(new CheckNetwork().checkNetworkConnection(ReferalScreenActivity.this))
			{
				String error =serviceMethod.redeemReferralCode(parentId,ReferCode,IsOptForDontShowReferalCode);
				if(error!=null)
				{
					ErrorCode=Integer.parseInt(error);
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
			onTouchSubmitButton = false;

			try {
				customProgressLoader.removeCallbacksHandler();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{


			}
			else
			{
				if(result==0)
				{
					try
					{
						if(IsOptForDontShowReferalCode.trim().equalsIgnoreCase("0"))
						{
							social.userProfileClevertap("Refered", "Yes", 1, null, null, 0, 0);
						}
						else
						{
							social.userProfileClevertap("Refered", "No", 1, null, null, 0, 0);
						}
					}
					catch (Exception e)
					{

					}
					finish();
					ReferalScreenActivity.this.overridePendingTransition(R.anim.activity_open_scale, R.anim.disappear);
				}
				else
				{
					getError();
				}
			}

		}
	}

	private void getError()
	{
		Error err = serviceMethod.getError();
		//new ShowMessages(ReferalScreenActivity.this).showAlert("Warning", err.getErrorDesc());
		new ShowMessages(ReferalScreenActivity.this).showToast(err.getErrorDesc());

	}

	private GetPaymentStatusCheck modelStatus;
	private class GetPaymentStatusCheckAsyncTask extends AsyncTask<Void, Void, Integer>
	{


		public GetPaymentStatusCheckAsyncTask()
		{
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(new CheckNetwork().checkNetworkConnection(ReferalScreenActivity.this))
			{
				modelStatus =serviceMethod.getPaymentStatusCheck(parentId,parentEmail);
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

			onTouchSubmitButton = false;
			try {
				customProgressLoader.removeCallbacksHandler();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{


			}
			else
			{
				if(modelStatus!=null)
				{
					if(modelStatus.getPaymentStatus()==1)
					{
						sharePreferenceclass.setIsLogin(true);
						Intent intent=new Intent(ReferalScreenActivity.this, AccessProfileActivity.class);
						startActivity(intent);
						sharePreferenceclass.setCurrentScreen(4);
						ReferalScreenActivity.this.finish();
					}
					else
					{
						//Toast.makeText(GetStartedActivity.this,"Not Purchased ",Toast.LENGTH_LONG).show();
						StaticVariables.screenForPurchase=1;
						finish();
						Intent intent=new Intent(ReferalScreenActivity.this, InAppPurchaseActivity.class);
						startActivity(intent);
					}
				}
				else
				{
					//Toast.makeText(GetStartedActivity.this,"Not Purchased ",Toast.LENGTH_LONG).show();
					StaticVariables.screenForPurchase=1;
					finish();
					Intent intent=new Intent(ReferalScreenActivity.this, InAppPurchaseActivity.class);
					startActivity(intent);
				}
			}

		}
	}
	private boolean isKeyBoardShown() {
		Rect r = new Rect();
		layoutMainReferal.getWindowVisibleDisplayFrame(r);

		int screenHeight = layoutMainReferal.getRootView().getHeight();
		int heightDifference = screenHeight - (r.bottom - r.top);
		//  Log.d("Keyboard Size", "Size: " + heightDifference);

		if (heightDifference < 100) {
			return false;
		} else
			return true;
	}


}
