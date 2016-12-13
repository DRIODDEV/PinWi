package com.hatchtact.pinwi.fragment.whattodo;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class CustomDialogClass extends Dialog implements View.OnClickListener {

	private ActivityListFragment contextActivityList;
	private WishListFragment contextWishList;
	private TextView txtwhoisdoingthis, txtSchedulethisAct,txtfindaseller,txtViewWishlist,txtCancel;
	private View emptylinewishlist,viewemptyscheduleit;
	private TypeFace typeface;

	public CustomDialogClass(ActivityListFragment context) {
		super(context.getActivity());
		// TODO Auto-generated constructor stub
		this.contextActivityList = context;
		typeface=new TypeFace(context.getActivity());

	}

	public CustomDialogClass(WishListFragment context) {
		super(context.getActivity());
		// TODO Auto-generated constructor stub
		this.contextWishList = context;
		typeface=new TypeFace(context.getActivity());

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog_whattodo);


		txtwhoisdoingthis = (TextView) findViewById(R.id.txtwhoisdoingthis);
		txtSchedulethisAct = (TextView) findViewById(R.id.txtSchedulethisAct);
		txtfindaseller = (TextView) findViewById(R.id.txtfindaseller);
		txtViewWishlist = (TextView) findViewById(R.id.txtViewWishlist);
		txtCancel = (TextView) findViewById(R.id.txtCancel);
		emptylinewishlist=(View) findViewById(R.id.emptylinewishlist);
		viewemptyscheduleit=(View) findViewById(R.id.viewemptyscheduleit);
		txtwhoisdoingthis.setOnClickListener(this);
		txtSchedulethisAct.setOnClickListener(this);
		txtfindaseller.setOnClickListener(this);
		txtViewWishlist.setOnClickListener(this);
		txtCancel.setOnClickListener(this);


		typeface.setTypefaceRegular(txtwhoisdoingthis);
		typeface.setTypefaceRegular(txtSchedulethisAct);
		typeface.setTypefaceRegular(txtfindaseller);
		typeface.setTypefaceRegular(txtViewWishlist);
		typeface.setTypefaceRegular(txtCancel);
		if(contextWishList!=null)
		{
			txtViewWishlist.setVisibility(View.GONE);
			emptylinewishlist.setVisibility(View.GONE);
		}
		else
		{
			txtViewWishlist.setVisibility(View.VISIBLE);
			emptylinewishlist.setVisibility(View.VISIBLE);
		}

		setCancelable(true);


	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.txtwhoisdoingthis:

			if(contextActivityList==null) 
			{
				if(StaticVariables.fragmentIndexCurrentTabWhatToDo==303)
					StaticVariables.fragmentIndexCurrentTabWhatToDo=306;
				else
					StaticVariables.fragmentIndexCurrentTabWhatToDo=307;

				contextWishList.switchScreen(new WhoDoingThisFragment());
			}
			else
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=305;
				contextActivityList.switchScreen(new WhoDoingThisFragment());
			}

			dismiss();
 
			break;
		case R.id.txtSchedulethisAct:

			StaticVariables.addAfterSchoolActivities=null;//if needed can be removed
			if(contextActivityList==null)
			{
				if(StaticVariables.fragmentIndexCurrentTabWhatToDo==303)
					StaticVariables.fragmentIndexCurrentTabWhatToDo=318;
				else
					StaticVariables.fragmentIndexCurrentTabWhatToDo=322;

				contextWishList.switchScreen(new AddAfterSchoolFragment());
			}
			else
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=314;
				contextActivityList.switchScreen(new AddAfterSchoolFragment());
			}
			dismiss();

			break;

		case R.id.txtfindaseller:

			if(contextActivityList==null) 
			{

			}
			else
			{

			}

			dismiss();

			break;
		case R.id.txtViewWishlist:

			if(contextActivityList==null)
			{

			}
			else
			{

				StaticVariables.fragmentIndexCurrentTabWhatToDo=304;
				contextActivityList.switchScreen(new WishListFragment());
			}
			dismiss();

			break;

		case R.id.txtCancel:



			dismiss();

			break;


		}


	}
	
	
	public void isActivityScheduled(int value)
	{
		if(value==1)
		{
			viewemptyscheduleit.setVisibility(View.GONE);
			txtSchedulethisAct.setVisibility(View.GONE);
		}
		else
		{
			viewemptyscheduleit.setVisibility(View.VISIBLE);
			txtSchedulethisAct.setVisibility(View.VISIBLE);	
		}
	}









}