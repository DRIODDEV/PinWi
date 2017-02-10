package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.SubjectActivitiesByChildID;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.TypeFace;

public class DisplaySubjectActivityByChildIdAdapter extends ArrayAdapter<SubjectActivitiesByChildID>{

	public ArrayList<SubjectActivitiesByChildID>  list_subjectadded = new ArrayList<SubjectActivitiesByChildID>();

	private LayoutInflater inflater;

	String currentDay;

	private TypeFace typeFace=null;

	private Context context=null;

	public DisplaySubjectActivityByChildIdAdapter(Context context, ArrayList<SubjectActivitiesByChildID> list)
	{
		super(context, R.layout.display_childsubject_information, list);
		// TODO Auto-generated constructor stub
		typeFace=new TypeFace(context);
		this.context=context;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_subjectadded = list;
		currentDay = AppUtils.getCurrentDay();
	}

	@Override
	public int getCount() {
		if (list_subjectadded != null) 
		{
			return list_subjectadded.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public SubjectActivitiesByChildID getItem(int position) {

		return list_subjectadded.get(position);
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
			view = inflater.inflate(R.layout.display_childsubject_information, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

		final String day=list_subjectadded.get(position).getDayid();

		final String[] daySelected=day.split(",");




		holder.subjectName_text.setText(list_subjectadded.get(position).getName());	

		holder.sun_text.setBackgroundResource(R.drawable.dot_gray);	
		holder.mon_text.setBackgroundResource(R.drawable.dot_gray);	
		holder.tue_text.setBackgroundResource(R.drawable.dot_gray);		
		holder.wed_text.setBackgroundResource(R.drawable.dot_gray);		
		holder.thu_text.setBackgroundResource(R.drawable.dot_gray);		
		holder.fri_text.setBackgroundResource(R.drawable.dot_gray);		
		holder.sat_text.setBackgroundResource(R.drawable.dot_gray);	
		
		holder.sun_text.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.mon_text.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.tue_text.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.wed_text.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.thu_text.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.fri_text.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.sat_text.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.School_verified_nonverified_image.setVisibility(View.GONE);
		/*if(list_subjectadded.get(position).isIsVerified())
		{
			holder.School_verified_nonverified_image.setImageResource(R.drawable.verified);
			holder.School_verified_nonverified_image.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.School_verified_nonverified_image.setImageResource(R.drawable.nonverified);
			holder.School_verified_nonverified_image.setVisibility(View.VISIBLE);
		}*/

		for(int i=0;i<daySelected.length;i++)
		{
			if(daySelected[i].equalsIgnoreCase("1"))
			{
				if(daySelected[i].equalsIgnoreCase(currentDay))
				{
					holder.sun_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.sun_text.setTextColor(context.getResources().getColor(R.color.font_white_color));
				}
				else
				{
					holder.sun_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.sun_text.setTextColor(context.getResources().getColor(R.color.font_white_color));
				}
			}

			else if(daySelected[i].equalsIgnoreCase("2"))
			{
				if(daySelected[i].equalsIgnoreCase(currentDay))	
				{
					holder.mon_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.mon_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
				else
				{
					holder.mon_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.mon_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
			}

			else if(daySelected[i].equalsIgnoreCase("3"))
			{
				if(daySelected[i].equalsIgnoreCase(currentDay))	
				{
					holder.tue_text.setBackgroundResource(R.drawable.dot_darkblue);	
					holder.tue_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
				else
				{
					holder.tue_text.setBackgroundResource(R.drawable.dot_darkblue);	
					holder.tue_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
			}

			else if(daySelected[i].equalsIgnoreCase("4"))
			{
				if(daySelected[i].equalsIgnoreCase(currentDay))	
				{
					holder.wed_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.wed_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
				else
				{
					holder.wed_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.wed_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
			}

			else if(daySelected[i].equalsIgnoreCase("5"))
			{
				if(daySelected[i].equalsIgnoreCase(currentDay))	
				{
					holder.thu_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.thu_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
				else
				{
					holder.thu_text.setBackgroundResource(R.drawable.dot_darkblue);	
					holder.thu_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
			}

			else if(daySelected[i].equalsIgnoreCase("6"))
			{
				if(daySelected[i].equalsIgnoreCase(currentDay))	
				{
					holder.fri_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.fri_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
				else
				{
					holder.fri_text.setBackgroundResource(R.drawable.dot_darkblue);	
					holder.fri_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
			}

			else if(daySelected[i].equalsIgnoreCase("7"))
			{
				if(daySelected[i].equalsIgnoreCase(currentDay))	
				{
					holder.sat_text.setBackgroundResource(R.drawable.dot_darkblue);
					holder.sat_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
				else
				{
					holder.sat_text.setBackgroundResource(R.drawable.dot_darkblue);	
					holder.sat_text.setTextColor(context.getResources().getColor(R.color.font_white_color));

				}
			}
		}
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.subjectName_text = (TextView) view.findViewById(R.id.subjectName_textDisplay);
		holder.sun_text=(TextView) view.findViewById(R.id.subject_sunday_text);
		holder.mon_text=(TextView) view.findViewById(R.id.subject_monday_text);
		holder.tue_text=(TextView) view.findViewById(R.id.subject_tuesday_text);
		holder.wed_text=(TextView) view.findViewById(R.id.subject_wednesday_text);
		holder.thu_text=(TextView) view.findViewById(R.id.subject_thursday_text);
		holder.fri_text=(TextView) view.findViewById(R.id.subject_friday_text);
		holder.sat_text=(TextView) view.findViewById(R.id.subject_saturday_text);
		holder.School_verified_nonverified_image=(ImageView) view.findViewById(R.id.School_verified_nonverified_image);

		typeFace.setTypefaceRegular(holder.subjectName_text);
		typeFace.setTypefaceRegular(holder.sun_text);
		typeFace.setTypefaceRegular(holder.mon_text);
		typeFace.setTypefaceRegular(holder.tue_text);
		typeFace.setTypefaceRegular(holder.wed_text);
		typeFace.setTypefaceRegular(holder.thu_text);
		typeFace.setTypefaceRegular(holder.fri_text);
		typeFace.setTypefaceRegular(holder.sat_text);

		return holder;
	}

	private  class ViewHolder 
	{
		private TextView subjectName_text;
		private TextView sun_text;
		private TextView mon_text;
		private TextView tue_text;
		private TextView wed_text;
		private TextView thu_text;
		private TextView fri_text;
		private TextView sat_text;
		private ImageView School_verified_nonverified_image;
	}
}
