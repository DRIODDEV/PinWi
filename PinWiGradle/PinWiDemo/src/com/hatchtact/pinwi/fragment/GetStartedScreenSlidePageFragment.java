package com.hatchtact.pinwi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.TypeFace;


public class GetStartedScreenSlidePageFragment extends Fragment {

	public static final String ARG_PAGE = "page";


	private int mPageNumber;

	private TypeFace typeface;

	public static GetStartedScreenSlidePageFragment create(int pageNumber) {
		GetStartedScreenSlidePageFragment fragment = new GetStartedScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		System.out.println("in screen slide" +ARG_PAGE);
		return fragment;
	}

	public GetStartedScreenSlidePageFragment()
	{

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		if(typeface==null)
		{
			typeface=new TypeFace(getActivity());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		final ViewGroup rootView = (ViewGroup) inflater
				.inflate(R.layout.fragment_screen_slide_page_getstarted, container, false);


		TextView heading=(TextView)rootView.findViewById(R.id.text_started_Heading);
		TextView textDesc=(TextView)rootView.findViewById(R.id.text_started);
		typeface.setTypefaceLight(textDesc);
		typeface.setTypefaceBold(heading);
		heading.setTextColor(getActivity().getResources().getIntArray(R.array.getstartedheadingcolors)[mPageNumber]);
		heading.setText(getActivity().getResources().getStringArray(R.array.get_started_features_heading)[mPageNumber]);
		textDesc.setText(getActivity().getResources().getStringArray(R.array.get_started_features)[mPageNumber]);

		return rootView;
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}
}
