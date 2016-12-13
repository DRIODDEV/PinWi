package com.hatchtact.pinwi.child;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;

public class ChildTutorialScreenSlidePageFragment  extends Fragment 
{

	public static final String ARG_PAGE = "page";


	private int mPageNumber;

	private Integer[] arrayImagesTutorial={R.drawable.child_slide1,R.drawable.child_slide2,R.drawable.child_slide3,
			R.drawable.child_slide4,R.drawable.child_slide5,R.drawable.child_slide6,R.drawable.child_slide7,R.drawable.child_slide8};

	private TextView btnNext;
	

	public static ChildTutorialScreenSlidePageFragment create(int pageNumber) {
		ChildTutorialScreenSlidePageFragment fragment = new ChildTutorialScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		System.out.println("in screen slide" +pageNumber);

		return fragment;
	}

	public ChildTutorialScreenSlidePageFragment()
	{

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		final ViewGroup rootView = (ViewGroup) inflater
				.inflate(R.layout.fragment_screen_slide_page, container, false);

		ImageView image=(ImageView)rootView.findViewById(R.id.image1);

		btnNext=(TextView)rootView.findViewById(R.id.imageNext);

		btnNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().onBackPressed();
			}
		});

		image.setImageResource(arrayImagesTutorial[mPageNumber]);
		
		if(mPageNumber==arrayImagesTutorial.length-1)
		{
			//btnNext.setImageResource(R.drawable.next);
			btnNext.setText("Done");
			btnNext.setVisibility(View.VISIBLE);
		}
		else
		{
			//btnNext.setImageResource(R.drawable.ic_launcher);
			btnNext.setText("Skip");
			btnNext.setVisibility(View.VISIBLE);
		
		}

		return rootView;
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}
}
