package com.hatchtact.pinwi.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {
	
	public  boolean haveNetworkConnection(Context context) 
	{
		boolean	haveConnectedWifi = false;
		boolean	haveConnectedMobile = false;    

		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] netInfo = cm.getAllNetworkInfo();

		for (NetworkInfo ni : netInfo)    
		{
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))                  
			{
				if (ni.isConnected())                      
				{
					haveConnectedWifi = true;
				}
			}

			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
			{
				if (ni.isConnected())
				{
					haveConnectedMobile = true;
				}
			}
		}
		return haveConnectedWifi || haveConnectedMobile;
	}


	public boolean checkNetworkConnection(Context context)
	{
		boolean pinged = false; 

		if(haveNetworkConnection(context))
		{
			try {
				pinged=true;
			} catch(Exception e)
			{
				e.printStackTrace();
			}		
		}
		return pinged;
	}
}
