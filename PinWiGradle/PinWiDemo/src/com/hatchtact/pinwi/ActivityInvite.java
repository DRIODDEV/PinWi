package com.hatchtact.pinwi;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.TutorialAdapter;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.TutorialItems;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class ActivityInvite extends MainActionBarActivity implements OnItemClickListener
{
	private ListView listData=null;

	private TutorialAdapter adapter=null;

	private ArrayList<TutorialItems> listDataValue=null;

	private int[] inviteImage={R.drawable.invite_mail,R.drawable.invite_sms,R.drawable.invite_whatsapp};

	private String[] inviteTitle={"Email","SMS","Whatsapp"};
	
	private ServiceMethod serviceMethod=null;
	
	private String parentId=null;
	
	private Gson gsonRegistration=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	ParentProfile parentCompleteInformation;
	private SharePreferenceClass sharePreferenceClass=null;
	
	private String messageText=null;
	protected CustomLoader customProgressLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		screenName="Invite";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		customProgressLoader=new CustomLoader(ActivityInvite.this);

		listData=(ListView) findViewById(R.id.list_invite); 

		listDataValue=new ArrayList<TutorialItems>();

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(ActivityInvite.this);
		serviceMethod=new ServiceMethod();
		gsonRegistration=new GsonBuilder().create();
		sharePreferenceClass=new SharePreferenceClass(ActivityInvite.this);
		
		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		parentId=""+parentCompleteInformation.getParentID();

   
		for(int i=0;i<3;i++)
		{   
			TutorialItems tutorialList=new TutorialItems();
			tutorialList.setImageTutorial(inviteImage[i]);
			tutorialList.setTextTutorial(inviteTitle[i]);     

			listDataValue.add(tutorialList);	
		}

		adapter=new TutorialAdapter(ActivityInvite.this, listDataValue);
		listData.setAdapter(adapter);
		listData.setOnItemClickListener(ActivityInvite.this);
		
		new GetInviteURL(parentId).execute();
	}
	
	//private ProgressDialog progressDialog=null;

	private class GetInviteURL extends AsyncTask<Void, Void, String>
	{

		private String parentId;

		public GetInviteURL(String parentId)
		{
			// TODO Auto-generated constructor stub 
			this.parentId = parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			/*progressDialog = ProgressDialog.show(ActivityInvite.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected String doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			String ErrorCode=null;

			if(checkNetwork.checkNetworkConnection(ActivityInvite.this))
			{
				ErrorCode=""+serviceMethod.inviteUrl(parentId);
				messageText=ErrorCode;
			}
			else 
			{
				ErrorCode="WrongValue";
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				customProgressLoader.removeCallbacksHandler();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result=="WrongValue")
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ActivityInvite.this))
					new GetInviteURL(parentId).execute();
			}
			else
			{
				
			}	
		}	
	}  

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		switch (position) 
		{
		case 0:
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, messageText);
			startActivity(emailIntent);
			
			break;
		case 1:
			Intent intentMessage = new Intent("android.intent.action.VIEW");	
			Uri data = Uri.parse("sms:");
			intentMessage.setData(data);
			intentMessage.putExtra("sms_body",messageText); 
			startActivity(intentMessage);	
			break;
			
		case 2:
			/*Intent intentWhatsapp = new Intent("android.intent.action.VIEW");	
			Uri dataUri = Uri.parse("sms:");
			intentWhatsapp.setData(dataUri);
			intentWhatsapp.putExtra("sms_body",messageText); 
			startActivity(intentWhatsapp);
			*/
			try{
			  boolean installed = appInstalledOrNot("com.whatsapp");  
		        if(installed)
		        {
		        	Intent sendIntent = new Intent();
					sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, messageText);
					sendIntent.setType("text/plain");

					// Do not forget to add this to open whatsApp App specifically
					sendIntent.setPackage("com.whatsapp");
					startActivity(sendIntent);
		            System.out.println("App is already installed on your phone");         
		        }
		        else
		        {
		            Toast.makeText(ActivityInvite.this, "App is not currently installed on your phone",Toast.LENGTH_SHORT).show();
		        }
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			break;
		}
		
	}
	
	 private boolean appInstalledOrNot(String uri) {
	        PackageManager pm = getPackageManager();
	        boolean app_installed;
	        try {
	            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	            app_installed = true;
	        }
	        catch (PackageManager.NameNotFoundException e) {
	            app_installed = false;
	        }
	        return app_installed;
	    }
}
