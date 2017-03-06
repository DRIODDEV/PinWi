package com.hatchtact.pinwi.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import java.util.Random;

public class CustomLoader
{
	private final Random random;
	private ProgressDialog progressDialog=null;
	Handler handler = null;
	private Context mContext;
	private String[] messagesArray = {"While this loads, think about what makes you happy!!",
			"This is not us. Its the network, the wifi or what have you...",
			"This shouldn't take too long...",
			"Good things happen to those who wait!",
			"In progress…getting there any second…"};
	private static int previousIndex=0;

	public CustomLoader(Context context)
	{
		super();
		// TODO Auto-generated constructor stub	
		mContext=context;
		handler = new Handler();
		random = new Random();
		//previousIndex=0;
	}

	public void showProgressBar()
	{
		if(handler==null)
		{
			handler = new Handler();
		}
		removeCallbacksHandler();
		handler.postDelayed(new Runnable() {
			public void run() {
				startProgress();
			}}, 2000);
	}

	public void dismissProgressBar()
	{
		if(handler!=null)
			handler.removeCallbacksAndMessages(null);
		if(progressDialog!=null)
		{
			endProgress();
		}
	}



	public void startHandler()
	{
		if(handler==null)
		{
			handler = new Handler();
		}
		removeCallbacksHandler();
		handler.postDelayed(new Runnable() {
			public void run() {
				startProgress();
			}}, 2000);
	}

	public void removeCallbacksHandler()
	{
		if(handler!=null)
			handler.removeCallbacksAndMessages(null);
		if(progressDialog!=null)
		{
			endProgress();
		}
	}

	private void colorIndexSelected(){
		int randomNumber = random.nextInt(messagesArray.length - 1);
		if(randomNumber == previousIndex){
			colorIndexSelected();
		}else{
			previousIndex = randomNumber;
		}
	}



	private  void startProgress()
	{
		colorIndexSelected();
		if(mContext!=null) {
			try {
				progressDialog = ProgressDialog.show(mContext, "", messagesArray[previousIndex], false);
				progressDialog.setCancelable(false);
			}
			catch (Exception e)
			{
				removeCallbacksHandler();
			}
		}
	}

	private void endProgress()
	{
		if(progressDialog!=null) {
			if (progressDialog.isShowing())
			{
				progressDialog.cancel();
				progressDialog=null;
			}
		}
	}
}
