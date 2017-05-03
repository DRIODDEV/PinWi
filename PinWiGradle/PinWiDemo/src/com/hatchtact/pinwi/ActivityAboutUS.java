package com.hatchtact.pinwi;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.hatchtact.pinwi.utility.StaticVariables;

public class ActivityAboutUS extends MainActionBarActivity
{
	/*private TextView text_heading1;
	private TextView text_aboutHeading1Text;
	private TextView text_heading2;
	private TextView text_aboutHeading2Text;
	private Button button_viewTermsAndConditions;
	private TextView text_version;
	private TextView text_copyRight;

	private TypeFace typeFace=null;*/
	WebView webView;
	private TextView txtVersionNo;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		if(StaticVariables.webUrl.contains("aboutus"))
		{
			screenName="About Us";
		}
		else if(StaticVariables.webUrl.contains("blog"))
		{
			screenName="Insights";
		}
		else if(StaticVariables.webUrl.contains("contactus"))
		{
			screenName="Contact Us";
		}

		else if(StaticVariables.webUrl.contains("terms"))
		{
			screenName="Terms Of Use";
		}
		else if(StaticVariables.webUrl.contains("privacy_policy"))
		{
			screenName="Privacy Policy";
		}
		else if(StaticVariables.webUrl.contains("Insight_Report"))
		{
			screenName="Insights Sample Report";
		}
		else if(StaticVariables.webUrl.contains("interst-driver.aspx"))
		{
			screenName="Instant Demo Report";
		}
		else if(StaticVariables.webUrl.equalsIgnoreCase("getstarted"))
		{
			screenName="Welcome to PiNWi";
			StaticVariables.webUrl="http://www.pinwi.in/blog/";
		}
		else
		{
			screenName="FAQ";
		}


		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_aboutus);

		/*typeFace= new TypeFace(ActivityAboutUS.this); 

		text_heading1 = (TextView) findViewById(R.id.text_heading1);
		text_aboutHeading1Text = (TextView) findViewById(R.id.text_aboutHeading1Text);
		text_heading2 = (TextView) findViewById(R.id.text_heading2);
		text_aboutHeading2Text = (TextView) findViewById(R.id.text_aboutHeading2Text);
		button_viewTermsAndConditions = (Button) findViewById(R.id.button_viewTermsAndConditions);
		text_version = (TextView) findViewById(R.id.text_version);
		text_copyRight = (TextView) findViewById(R.id.text_copyRight);

		typeFace.setTypefaceRegular(text_heading1);
		typeFace.setTypefaceRegular(button_viewTermsAndConditions);
		typeFace.setTypefaceRegular(text_heading2);
		typeFace.setTypefaceLight(text_aboutHeading2Text);
		typeFace.setTypefaceLight(text_aboutHeading1Text);
		typeFace.setTypefaceLight(text_version);
		typeFace.setTypefaceLight(text_copyRight);*/
		setContentView(R.layout.activity_about);


		//The "Back" button declaration and listener


		//Declaring the WebView
		webView = (WebView) findViewById(R.id.webView1);
		webView.setWebChromeClient(new WebChromeClient(){});
		webView.setWebViewClient(new myWebViewClient());
		txtVersionNo=(TextView) findViewById(R.id.txtVersionNo);

		PackageManager manager = getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo( getPackageName(), 0);
			//"Version 3.0"
			txtVersionNo.setText("Version "+info.versionName);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if(StaticVariables.webUrl.contains("aboutus"))
		{
			txtVersionNo.setVisibility(View.VISIBLE);
		}
		else
		{
			txtVersionNo.setVisibility(View.GONE);
		}
		webView.getSettings().setJavaScriptEnabled(true);
		// webView.getSettings().setPluginState(PluginState.ON);
		//Setting the settings for the webview
		webView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		/* webView.setOnKeyListener(new View.OnKeyListener()
	        {
	            @Override
	            public boolean onKey(View v, int keyCode, KeyEvent event)
	            {
	                if(event.getAction() == KeyEvent.ACTION_DOWN)
	                {
	                    WebView webView = (WebView) v;

	                    switch(keyCode)
	                    {
	                        case KeyEvent.KEYCODE_BACK:
	                            if(webView.canGoBack())
	                            {
	                                webView.goBack();
	                                return true;
	                            }
	                            break;
	                    }
	                }

	                return false;
	            }
	        });*/


		//webview loading URL 
		if(StaticVariables.webUrl.contains("Insight_Report"))
		{
			StaticVariables.webUrl = "http://docs.google.com/gview?embedded=true&url=" + StaticVariables.webUrl;

		}
		//StaticVariables.webUrl="http://www.pinwi.in/blog/";
		webView.loadUrl(StaticVariables.webUrl);
	}
	class myWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
		}
		boolean error;
		@Override
		public void onReceivedError( WebView view, int errorCode, String description, String failingUrl)  {

			error = true;
			System.out.println("description error" + description);
			view.setVisibility( View.GONE );
			Toast.makeText(ActivityAboutUS.this, "This feature is unavailable at the moment, please try again later.", Toast.LENGTH_LONG).show();
			finish();
		}

	}
}
