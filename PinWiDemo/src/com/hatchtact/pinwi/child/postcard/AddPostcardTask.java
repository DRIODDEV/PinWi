package com.hatchtact.pinwi.child.postcard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.StaticVariables;

/**
 * Created by Ankur on 1/8/2016.
 */
public class AddPostcardTask extends AsyncTask<Void, Void, Integer>
{
	private ProgressDialog progressDialog;
	private String errorMessage;
	private Context mContext;
	private OnEventListener callback;
	private final int addPostcard=1;
	private int currentWebServiceTobeUsed=addPostcard;
	private ServiceMethod serviceMethod;
	private CheckNetwork checkNetwork;
	private int templateId,loggedId,actiontype;
	private String sendingText="";



	public AddPostcardTask(Context context,int TemplateID,int LoggedID,int actionType,String Text,int currentWebservice,OnEventListener listener)
	{
		mContext=context;
		currentWebServiceTobeUsed=currentWebservice;
		callback=listener;
		templateId=TemplateID;
		actiontype=actionType;
		loggedId=LoggedID;
		sendingText=Text;

	}



	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(mContext, "", StaticVariables.progressBarText, false);
		serviceMethod=new ServiceMethod();
		checkNetwork=new CheckNetwork();
		progressDialog.setCancelable(false);
	}

	String status;
	@Override
	protected Integer doInBackground(Void... params)
	{
		int ErrorCode=0;

		if (checkNetwork.checkNetworkConnection(mContext))
		{
			switch (currentWebServiceTobeUsed)
			{
			case addPostcard:
				try {
					status=serviceMethod.addPostcard(templateId, loggedId,actiontype,sendingText);
				} catch (Exception e) 
				{
					ErrorCode=-1;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;	
			}
		}
		else
		{
			ErrorCode=-1;

		}
		return ErrorCode;
	}





	@Override
	protected void onPostExecute(Integer result)
	{
		super.onPostExecute(result);
		if (progressDialog.isShowing())
			progressDialog.cancel();

		switch (currentWebServiceTobeUsed)
		{
		case addPostcard:
			if(result==-1)
			{
				showToastMessage("Please check your network connection");

			}
			else
			{
			}	

			setResultData(result);
			break;


		}
	}

	private void setResultData(Integer result)
	{
		if (result == 0)
		{
			if(status.contains("Your post has been"))
			{
				showToastMessage("Your post has been sent to your Playwall.");
				callback.onSuccess(status);
			}
			else
			{
				callback.onFailure(status);

			}
		} else
		{
			callback.onFailure(status);
		}
	}

	public void showToastMessage(String text)
	{
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

}
