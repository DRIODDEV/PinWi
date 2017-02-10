package com.hatchtact.pinwi.fragment.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;

/**
 * Created by Ankur on 1/8/2016.
 */
public class CustomAsyncTask extends AsyncTask<Void, Void, Integer>
{
	private ProgressDialog progressDialog;
	private String errorMessage;
	private Context mContext;
	private OnEventListener callback;
	private final int sendFriendRequest=0;
	private final int updateStatusonAction=1;
	private final int sendBuddiesRequest=3;
	private final int updateStatusonActionBuddies=2;
	private final int addToWishList=4;
	private final int removeFromWishList=5;


	private int currentWebServiceTobeUsed=sendFriendRequest;
	private ServiceMethod serviceMethod;
	private CheckNetwork checkNetwork;
	private String friendId,actionflag,loggedId;
	private SocialConstants social;
	



	public CustomAsyncTask(Context context,String FriendId,String Actionflag,String LoggedId,int currentWebservice,OnEventListener listener)
	{
		mContext=context;
		currentWebServiceTobeUsed=currentWebservice;
		if(social==null)
			social=new SocialConstants(context);
		callback=listener;
		friendId=FriendId;
		actionflag=Actionflag;
		loggedId=LoggedId;
		
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
			case sendFriendRequest:
				status=serviceMethod.sendFriendRequest(friendId, loggedId);

				break;
			case updateStatusonAction:
				status=serviceMethod.updateStatusOnAction(friendId, loggedId, actionflag);
				break;
			case updateStatusonActionBuddies:
				status=serviceMethod.actionOnPendingRequestsOnCI(friendId, loggedId, actionflag);
				break;
			case sendBuddiesRequest:
				status=serviceMethod.sendFriendRequestOnCI(friendId, loggedId);
				break;
			case addToWishList:
				status=serviceMethod.addWishlistByChildID(loggedId,friendId);
				break;
			case removeFromWishList:
				status=serviceMethod.addWishlistByChildID(loggedId,friendId);
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
		case sendFriendRequest:
			if(result==-1)
			{
				showToastMessage("Please check your network connection");

			}
			else
			{
				//if(status.contains("Action Taken"))
				{
					if(actionflag.equalsIgnoreCase("3"))
					{
					Toast.makeText(mContext, "Friend Removed", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("1"))
					{
						social.Network_Connection_AcceptedFacebookLog();
						social.Network_Connection_AcceptedGoogleAnalyticsLog();
						Toast.makeText(mContext, "Friend Added", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("0"))
					{
						social.Network_Connection_AddedFacebookLog();
						social.Network_Connection_AddedGoogleAnalyticsLog();
						Toast.makeText(mContext, "Friend Request Sent", Toast.LENGTH_LONG).show();
					}

						
				}
				/*else
				{
					
				}*/
			}	
			
			setResultData(result);
			break;
		case updateStatusonAction:
			if(result==-1)
			{
				showToastMessage("Please check your network connection");

			}
			else
			{
				//if(status.contains("Action Taken"))
				{
					if(actionflag.equalsIgnoreCase("3"))
					{
					Toast.makeText(mContext, "Friend Removed", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("1"))
					{
						social.Network_Connection_AcceptedFacebookLog();
						social.Network_Connection_AcceptedGoogleAnalyticsLog();
						Toast.makeText(mContext, "Friend Added", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("0"))
					{
						social.Network_Connection_AddedFacebookLog();
						social.Network_Connection_AddedGoogleAnalyticsLog();
						Toast.makeText(mContext, "Friend Request Sent", Toast.LENGTH_LONG).show();
					}

						
				}
				/*else
				{
					
				}*/
			}	
			
			setResultData(result);
			break;
		case sendBuddiesRequest:
			if(result==-1)
			{
				showToastMessage("Please check your network connection");

			}
			else
			{
				//if(status.contains("Action Taken"))
				{
					if(actionflag.equalsIgnoreCase("3"))
					{
					Toast.makeText(mContext, "Friend Removed", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("1"))
					{
						Toast.makeText(mContext, "Friend Added", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("0"))
					{
						social.buddyRequestSentFacebookLog();
						social.buddyRequestSentGoogleAnalyticsLog();
						Toast.makeText(mContext, "Yay! Your request is on its way.", Toast.LENGTH_LONG).show();
					}

						
				}
				/*else
				{
					
				}*/
			}	
			
			setResultData(result);
			break;
		case updateStatusonActionBuddies:
			if(result==-1)
			{
				showToastMessage("Please check your network connection");

			}
			else
			{
				//if(status.contains("Action Taken"))
				{
					if(actionflag.equalsIgnoreCase("3"))
					{
					Toast.makeText(mContext, "Buddy Removed", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("1"))
					{
						social.addBuddyFacebookLog();
						social.addBuddyGoogleAnalyticsLog();
						Toast.makeText(mContext, "Buddy Added", Toast.LENGTH_LONG).show();
					}
					else if(actionflag.equalsIgnoreCase("0"))
					{
						social.buddyRequestSentFacebookLog();
						social.buddyRequestSentGoogleAnalyticsLog();
						Toast.makeText(mContext, "Buddy Request Sent", Toast.LENGTH_LONG).show();
					}

						
				}
				/*else
				{
					
				}*/
			}	
			
			setResultData(result);
			break;
		case addToWishList:
			if(result==-1)
			{
				showToastMessage("Please check your network connection");

			}
			else
			{
				//if(status.contains("Action Taken"))
				{
					if(actionflag.equalsIgnoreCase("4"))
					{
						social.Add_to_WishlistFacebookLog();
						social.Add_to_WishlistGoogleAnalyticsLog();
					//Toast.makeText(mContext, "You have wished for this. Good choice.", Toast.LENGTH_LONG).show();
					}
					

						
				}
				/*else
				{
					
				}*/
			}	
			
			setResultData(result);
			break;
			
		case removeFromWishList:
			if(result==-1)
			{
				showToastMessage("Please check your network connection");

			}
			else
			{
				//if(status.contains("Action Taken"))
				{
					if(actionflag.equalsIgnoreCase("5"))
					{
					//Toast.makeText(mContext, "You no longer wish to do this activity.", Toast.LENGTH_LONG).show();
					}
					

						
				}
				/*else
				{
					
				}*/
			}	
			
			setResultData(result);
			break;
			
		}
	}

	private void setResultData(Integer result)
	{
		if (result == 0)
		{
			callback.onSuccess(status);
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
