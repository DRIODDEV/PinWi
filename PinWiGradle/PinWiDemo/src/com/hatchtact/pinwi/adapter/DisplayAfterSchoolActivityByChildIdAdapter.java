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
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityByChildID;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.TypeFace;

public class DisplayAfterSchoolActivityByChildIdAdapter extends ArrayAdapter<AfterSchoolActivityByChildID>{

	public ArrayList<AfterSchoolActivityByChildID>  list_afterschooladded = new ArrayList<AfterSchoolActivityByChildID>();

	private LayoutInflater inflater;

	private String currentDay;

	private TypeFace typeFace=null;

	private Context context=null;

	public DisplayAfterSchoolActivityByChildIdAdapter(Context context, ArrayList<AfterSchoolActivityByChildID> list)
	{
		super(context, R.layout.display_afterschool_information, list);
		// TODO Auto-generated constructor stub
		typeFace=new TypeFace(context);
		this.context=context;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_afterschooladded = list;
		currentDay=AppUtils.getCurrentDay();
	}

	@Override
	public int getCount() {
		if (list_afterschooladded != null) 
		{
			return list_afterschooladded.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public AfterSchoolActivityByChildID getItem(int position) {

		return list_afterschooladded.get(position);
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
			view = inflater.inflate(R.layout.display_afterschool_information, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}



		try 
		{
			final String day=list_afterschooladded.get(position).getDaysValue();

			final String[] daySelected=day.split(",");

			holder.ActivityName_text.setText(list_afterschooladded.get(position).getActivityName());	
			holder.startTime_text.setText(list_afterschooladded.get(position).getStartTime());	
			holder.endTime_text.setText(list_afterschooladded.get(position).getEndTime());

			if(list_afterschooladded.get(position).isIsPrivate())
			{
				holder.private_image.setImageResource(R.drawable.private_afterschool);
				holder.private_image.setVisibility(View.VISIBLE);	
			}
			else
			{
				holder.private_image.setVisibility(View.INVISIBLE);
			}

			if(list_afterschooladded.get(position).isIsSpecial())
			{
				holder.special_image.setImageResource(R.drawable.special);
				//holder.special_image.setVisibility(View.VISIBLE);
				if(holder.private_image.getVisibility()==View.VISIBLE)
				{
					holder.special_image.setVisibility(View.GONE);
				}
				else
				{
					holder.special_image.setVisibility(View.INVISIBLE);
				}
			}
			else
			{
				if(holder.private_image.getVisibility()==View.VISIBLE)
				{
					holder.special_image.setVisibility(View.GONE);
				}
				else
				{
					holder.special_image.setVisibility(View.INVISIBLE);
				}
			}

			if(list_afterschooladded.get(position).isIsVerified())
			{
				holder.ver_nonverified_image.setImageResource(R.drawable.verified);
				holder.ver_nonverified_image.setVisibility(View.GONE);
			}
			else
			{
				holder.ver_nonverified_image.setImageResource(R.drawable.nonverified);
				holder.ver_nonverified_image.setVisibility(View.VISIBLE);
			}

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.ActivityName_text = (TextView) view.findViewById(R.id.subjectNameAfterSchool_textDisplay);
		holder.sun_text=(TextView) view.findViewById(R.id.afterSchool_sunday_text);
		holder.mon_text=(TextView) view.findViewById(R.id.afterSchool_monday_text);
		holder.tue_text=(TextView) view.findViewById(R.id.afterSchool_tuesday_text);
		holder.wed_text=(TextView) view.findViewById(R.id.afterSchool_wednesday_text);
		holder.thu_text=(TextView) view.findViewById(R.id.afterSchool_thursday_text);
		holder.fri_text=(TextView) view.findViewById(R.id.afterSchool_friday_text);
		holder.sat_text=(TextView) view.findViewById(R.id.afterSchool_saturday_text);
		holder.startTime_text=(TextView) view.findViewById(R.id.starttimeAfterSchool_textDisplay);
		holder.endTime_text=(TextView) view.findViewById(R.id.endtimeAfterSchool_textDisplay);
		holder.private_image=(ImageView) view.findViewById(R.id.afterSchool_private_image);
		holder.special_image=(ImageView) view.findViewById(R.id.afterSchool_special_image);
		holder.ver_nonverified_image=(ImageView) view.findViewById(R.id.afterSchool_verified_nonverified_image);

		typeFace.setTypefaceRegular(holder.ActivityName_text);
		typeFace.setTypefaceRegular(holder.sun_text);
		typeFace.setTypefaceRegular(holder.mon_text);
		typeFace.setTypefaceRegular(holder.tue_text);
		typeFace.setTypefaceRegular(holder.wed_text);
		typeFace.setTypefaceRegular(holder.thu_text);
		typeFace.setTypefaceRegular(holder.fri_text);
		typeFace.setTypefaceRegular(holder.sat_text);
		typeFace.setTypefaceRegular(holder.startTime_text);
		typeFace.setTypefaceRegular(holder.endTime_text);

		return holder;
	}

	private  class ViewHolder 
	{
		private TextView ActivityName_text;
		private TextView sun_text;
		private TextView mon_text;
		private TextView tue_text;
		private TextView wed_text;
		private TextView thu_text;
		private TextView fri_text;
		private TextView sat_text;
		private TextView startTime_text;
		private TextView endTime_text;
		private ImageView private_image;
		private ImageView special_image;
		private ImageView ver_nonverified_image;
	}
}
