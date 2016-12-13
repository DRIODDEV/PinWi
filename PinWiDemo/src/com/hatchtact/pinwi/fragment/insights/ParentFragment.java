package com.hatchtact.pinwi.fragment.insights;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ParentFragment extends Fragment 
{
	protected OnFragmentAttachedListener mListener;
	protected TypeFace typeFace=null;

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
	}

	protected void switchingFragments(Fragment toFragment)
	{
		System.out.println("In fragment: "+StaticVariables.fragmentIndexCurrentTabSchedular);
		getFragmentManager().beginTransaction().replace(R.id.tabcontent_frame_layout,
				toFragment).commit();  
		getFragmentManager().executePendingTransactions();      // <----- This is the key 
	}
}
