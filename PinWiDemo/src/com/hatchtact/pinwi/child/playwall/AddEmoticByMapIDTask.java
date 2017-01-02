package com.hatchtact.pinwi.child.playwall;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hatchtact.pinwi.classmodel.GetFriendsTempleteMessageListByChildIDList;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;

/**
 * Created by Ankur on 1/8/2016.
 */
public class AddEmoticByMapIDTask extends AsyncTask<Void, Void, Integer>
{
	private ProgressDialog progressDialog;
	private String errorMessage;
	private Context mContext;
	private OnEventListener callback;
	private final int addEmoticon=1;
	private int currentWebServiceTobeUsed=addEmoticon;
	private ServiceMethod serviceMethod;
	private CheckNetwork checkNetwork;
	private int EmoticID,loggedId,MapID;
	private String sendingText="";
	private GetFriendsTempleteMessageListByChildIDList getFriendsTempleteMessageListByChildIDList;
	private SocialConstants social;



	public AddEmoticByMapIDTask(Context context,int MapID,int LoggedID,int EmoticID,int currentWebservice,OnEventListener listener)
	{
		mContext=context;
		currentWebServiceTobeUsed=currentWebservice;
		callback=listener;
		this.MapID=MapID;
		this.EmoticID=EmoticID;
		loggedId=LoggedID;
		if(social==null)
		social=new SocialConstants(context);
		getFriendsTempleteMessageListByChildIDList=new GetFriendsTempleteMessageListByChildIDList();
		getFriendsTempleteMessageListByChildIDList.getFriendsTempleteMessageListByChildID().clear();

	}



	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(mContext, "", StaticVariables.progressBarText, false);
		serviceMethod=new ServiceMethod();
		checkNetwork=new CheckNetwork();
		progressDialog.setCancelable(false);
	}

	//String status;
	@Override
	protected Integer doInBackground(Void... params)
	{
		int ErrorCode=0;

		if (checkNetwork.checkNetworkConnection(mContext))
		{
			switch (currentWebServiceTobeUsed)
			{
			case addEmoticon:
				try {
					getFriendsTempleteMessageListByChildIDList=serviceMethod.addEmoticByMapID(MapID, loggedId, EmoticID);
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
		case addEmoticon:
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
			if(getFriendsTempleteMessageListByChildIDList!=null && getFriendsTempleteMessageListByChildIDList.getFriendsTempleteMessageListByChildID().size()>0)
			{
				showToastMessage("Emoticon Added");
				social.reactedToPostFacebookLog();
				social.reactedToPostGoogleAnalyticsLog();
				callback.onSuccess(getFriendsTempleteMessageListByChildIDList.getFriendsTempleteMessageListByChildID().get(0));
			}
			else
			{
				callback.onFailure("");

			}
		} else
		{
			callback.onFailure("");
		}
	}

	public void showToastMessage(String text)
	{
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

}
