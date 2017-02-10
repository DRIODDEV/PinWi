package com.hatchtact.pinwi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.StaticVariables;

public class ScreenSlidePageFragment extends Fragment {

	public static final String ARG_PAGE = "page";


	private int mPageNumber;



	private Integer[] arrayImagesTutorial1={R.drawable.howpinwiworks1,R.drawable.howpinwiworks2,R.drawable.howpinwiworks3,R.drawable.howpinwiworks4};
	private Integer[] arrayImagesTutorial2={R.drawable.scheduler1,R.drawable.scheduler2,R.drawable.scheduler3,R.drawable.scheduler4,R.drawable.scheduler5,R.drawable.scheduler6,R.drawable.scheduler7,R.drawable.scheduler8};
	private Integer[] arrayImagesTutorial3={R.drawable.atschool1,R.drawable.atschool2,R.drawable.atschool3,R.drawable.atschool4,R.drawable.atschool5,R.drawable.atschool6};
	private Integer[] arrayImagesTutorial4={R.drawable.afterschool1,R.drawable.afterschool2,R.drawable.afterschool3,R.drawable.afterschool4,R.drawable.afterschool5,R.drawable.afterschool6,R.drawable.afterschool7};
	private Integer[] arrayImagesTutorial5={R.drawable.insights1,R.drawable.insights2,R.drawable.insights3,R.drawable.insights4,R.drawable.insights5,R.drawable.insights6};
	private Integer[] arrayImagesTutorial6={R.drawable.child_slide1,R.drawable.child_slide2,R.drawable.child_slide3,
			R.drawable.child_slide4,R.drawable.child_slide5,R.drawable.child_slide6,R.drawable.child_slide7,R.drawable.child_slide8};




	private TextView btnNext;
	private int sizeArray;

	public static ScreenSlidePageFragment create(int pageNumber) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		System.out.println("in screen slide" +ARG_PAGE);
		return fragment;
	}

	public ScreenSlidePageFragment()
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
				getActivity().finish();
			}
		});

		switch (StaticVariables.currentTutorialValue) 
		{
		case 0:
			image.setImageResource(arrayImagesTutorial1[mPageNumber]);
            sizeArray=arrayImagesTutorial1.length;
			break;

		case 1:
			image.setImageResource(arrayImagesTutorial2[mPageNumber]);
            sizeArray=arrayImagesTutorial2.length;

			break;

		case 2:
			image.setImageResource(arrayImagesTutorial3[mPageNumber]);
            sizeArray=arrayImagesTutorial3.length;

			break;

		case 3:
			image.setImageResource(arrayImagesTutorial4[mPageNumber]);
            sizeArray=arrayImagesTutorial4.length;

			break;

		case 4:
			image.setImageResource(arrayImagesTutorial5[mPageNumber]);
            sizeArray=arrayImagesTutorial5.length;

			break;

		case 5:
			image.setImageResource(arrayImagesTutorial6[mPageNumber]);
            sizeArray=arrayImagesTutorial6.length;

			break;


		}
         
		
		if(mPageNumber==sizeArray-1)
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
}
