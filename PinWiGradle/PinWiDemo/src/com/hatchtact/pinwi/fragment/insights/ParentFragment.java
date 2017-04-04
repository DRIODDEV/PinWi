package com.hatchtact.pinwi.fragment.insights;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hatchtact.pinwi.AccessProfileActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ParentFragment extends Fragment 
{
	protected OnFragmentAttachedListener mListener;
	protected TypeFace typeFace=null;
	protected SocialConstants social;
	protected CustomLoader customProgressLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		typeFace=new TypeFace(getActivity());
		social=new SocialConstants(getActivity());
		customProgressLoader=new CustomLoader(getActivity());

		try {	
			mListener = (OnFragmentAttachedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentAttachedListener");
		}
	}


	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		mListener=null;
		typeFace=null;
		customProgressLoader.removeCallbacksHandler();
	}

	protected void switchingFragments(Fragment toFragment)
	{
		System.out.println("In fragment: "+StaticVariables.fragmentIndexCurrentTabSchedular);
		getFragmentManager().beginTransaction().replace(R.id.tabcontent_frame_layout,
				toFragment).commit();
		getFragmentManager().popBackStack();
		getFragmentManager().executePendingTransactions();      // <----- This is the key 
	}
}
