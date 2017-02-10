package com.hatchtact.pinwi.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TypeFace 
{
	private Typeface ttfRegular = null,ttfLight=null,ttfBold=null;
	
	private Typeface ttfGotham = null, ttfGothamMedium = null, ttfGothamExtraLight = null, ttfGothamLight = null, ttfGothamThin = null;
	
	
	public TypeFace(Context con) {
		// TODO Auto-generated constructor stub
		ttfRegular = Typeface.createFromAsset(con.getAssets(), "Roboto-Regular.ttf");
		ttfLight = Typeface.createFromAsset(con.getAssets(), "Roboto-Light.ttf");
		ttfBold = Typeface.createFromAsset(con.getAssets(), "Roboto-Bold.ttf");
		
		ttfGotham = Typeface.createFromAsset(con.getAssets(), "gotham.ttf");
		ttfGothamMedium = Typeface.createFromAsset(con.getAssets(), "Gotham Medium.ttf");	
		ttfGothamExtraLight = Typeface.createFromAsset(con.getAssets(), "gothamExtralight.ttf");
		ttfGothamLight = Typeface.createFromAsset(con.getAssets(), "gothamLight.ttf");
		ttfGothamThin = Typeface.createFromAsset(con.getAssets(), "gothamThin.ttf");
	}
	
	//------------ TypeFace for Child Interface --------------
	public void  setTypefaceGotham(TextView txt)
	{
		txt.setTypeface(ttfGotham);
	}
	public void  setTypefaceGothamMedium(TextView txt)
	{
		txt.setTypeface(ttfGothamMedium);
	}
	public void  setTypefaceGothamExtraLight(TextView txt)
	{
		txt.setTypeface(ttfGothamExtraLight);
	}
	public void  setTypefaceGothamLight(TextView txt)
	{
		txt.setTypeface(ttfGothamLight);
	}	
	public void  setTypefaceGothamThin(TextView txt)
	{
		txt.setTypeface(ttfGothamThin);
	}
	//------------ TypeFace for Child Interface --------------
	
	public void  setTypefaceLight(TextView txt)
	{
		txt.setTypeface(ttfLight);
	}

	public void  setTypefaceRegular(EditText txt)
	{
		txt.setTypeface(ttfRegular);
	}
	
	public void  setTypefaceLight(EditText txt)
	{
		txt.setTypeface(ttfLight);
	}
	
	public void  setTypefaceBold(TextView txt)
	{
		txt.setTypeface(ttfBold);
	}

	public void  setTypefaceBold(EditText txt)
	{
		txt.setTypeface(ttfBold);
	}
	
	public void  setTypefaceRegular(Button button)
	{
		button.setTypeface(ttfRegular);
	}
	
	public void  setTypefaceLight(Button button)
	{
		button.setTypeface(ttfLight);
	}

	public void  setTypefaceBold(Button button)
	{
		button.setTypeface(ttfBold);
	}

	public void setTypefaceGotham(MenuItem findItem) {
		// TODO Auto-generated method stub
		((TextView) findItem).setTypeface(ttfGotham);
	}

	public void setTypefaceRegular(TextView txtBenefits) {
		// TODO Auto-generated method stub
		txtBenefits.setTypeface(ttfRegular);
	}
	//------------ TypeFace for Child Interface --------------
	public void  setTypefaceGothamBold(TextView txt)
	{
		txt.setTypeface(ttfGotham,Typeface.BOLD);
	}
}
