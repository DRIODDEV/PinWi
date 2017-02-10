package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.ActivityAboutUS;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.TutorialItems;
import com.hatchtact.pinwi.utility.TypeFace;


public class TutorialAdapter extends ArrayAdapter<TutorialItems>{

	public ArrayList<TutorialItems>  list_tutorialValue = new ArrayList<TutorialItems>();

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	public TutorialAdapter(Context context, ArrayList<TutorialItems> list)
	{
		super(context, R.layout.list_item_tutorials, list);
		// TODO Auto-generated constructor stub
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_tutorialValue = list;
		typeFace= new TypeFace(context); 

	}

	@Override
	public int getCount() {
		if (list_tutorialValue != null) 
		{
			return list_tutorialValue.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public TutorialItems getItem(int position) {

		return list_tutorialValue.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	ViewHolder holder;

	@Override
	public View getView(final int position, View view, ViewGroup parent) 
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.list_item_tutorials, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

		holder.tutorial_image.setImageResource(list_tutorialValue.get(position).getImageTutorial());	
		holder.tutorial_text.setText(list_tutorialValue.get(position).getTextTutorial());	
		
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.tutorial_image = (ImageView) view.findViewById(R.id.image_tutorial);
		holder.tutorial_text=(TextView) view.findViewById(R.id.text_tutorial);
		typeFace.setTypefaceRegular(holder.tutorial_text);
		return holder;
	}

	private  class ViewHolder 
	{
		private ImageView tutorial_image=null;
		private TextView tutorial_text;
	}
}
