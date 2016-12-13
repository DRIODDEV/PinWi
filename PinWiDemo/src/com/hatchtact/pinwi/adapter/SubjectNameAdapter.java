package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.SubjectActivities;
import com.hatchtact.pinwi.fragment.AddSubjectFragment;
import com.hatchtact.pinwi.utility.TypeFace;


public class SubjectNameAdapter extends ArrayAdapter<SubjectActivities>  implements Filterable
{

	//public ArrayList<SubjectActivities>  list_schoolName = new ArrayList<SubjectActivities>();

	private List<SubjectActivities> getAlllist_schoolName=null;
	public List<SubjectActivities > filteredlist_schoolName = null;

	private LayoutInflater inflater;
	private TypeFace typeFace=null;

	public SubjectNameAdapter(Context context, ArrayList<SubjectActivities> list)
	{
		super(context, R.layout.list_item_subject, list);
		// TODO Auto-generated constructor stub

		typeFace=new TypeFace(context);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getAlllist_schoolName = list;
		this.filteredlist_schoolName=list;
	}

	@Override
	public int getCount() {
		if (getAlllist_schoolName != null) 
		{
			return getAlllist_schoolName.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public SubjectActivities getItem(int position) {

		return getAlllist_schoolName.get(position);
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
			view = inflater.inflate(R.layout.list_item_subject, null);
			holder = createViewHolder(view);
			view.setTag(holder);

		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

		/*if(getAlllist_schoolName!=null)
		{
			if(getAlllist_schoolName.size()>0)
				if (getAlllist_schoolName.get(position).getSubjectName().toLowerCase().contains(AddSubjectFragment.filterString.toLowerCase())) 
				{



					int startPos = getAlllist_schoolName.get(position).getSubjectName().toLowerCase().indexOf(AddSubjectFragment.filterString.toLowerCase());

					int endPos = startPos + AddSubjectFragment.filterString.length();



					Spannable spanText = Spannable.Factory.getInstance().newSpannable(getAlllist_schoolName.get(position).getSubjectName());

					spanText.setSpan(new ForegroundColorSpan(Color.BLACK),startPos, endPos ,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

					holder.subjectName_text.setText(spanText);	
				}



				else

				{

					holder.subjectName_text.setText(getAlllist_schoolName.get(position).getSubjectName());	
				}
		}*/
		holder.subjectName_text.setText(getAlllist_schoolName.get(position).getSubjectName());	



		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.subjectName_text = (TextView) view.findViewById(R.id.item);
		typeFace.setTypefaceRegular(holder.subjectName_text);
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView subjectName_text;
	}



	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) {

				getAlllist_schoolName = (ArrayList<SubjectActivities>) results.values; // has the filtered values
				notifyDataSetChanged();  // notifies the data with new filtered values
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
				ArrayList<SubjectActivities> FilteredArrList = new ArrayList<SubjectActivities>();

				if (filteredlist_schoolName == null) {
					filteredlist_schoolName = new ArrayList<SubjectActivities>(getAlllist_schoolName); // saves the original data in mOriginalValues
				}

				/********
				 * 
				 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
				 *  else does the Filtering and returns FilteredArrList(Filtered)  
				 *
				 ********/
				if (constraint == null || constraint.length() == 0) 
				{

					// set the Original result to return  
					results.count = filteredlist_schoolName.size();
					results.values = filteredlist_schoolName;
				} else {
					constraint = constraint.toString().toLowerCase();
					for (int i = 0; i < filteredlist_schoolName.size(); i++) {
						String subjectName=filteredlist_schoolName.get(i).getSubjectName();
						String data = "" ;
						if(subjectName!=null && subjectName.length()>0)
						{
							data=subjectName;	
						}

						if (data.toLowerCase().contains(constraint.toString())) 
						{
							//String num,String name,String lastmsg,String Date,int statusRead,long id,String image
							FilteredArrList.add(filteredlist_schoolName.get(i));
						}
					}
					// set the Filtered result to return
					results.count = FilteredArrList.size();
					results.values = FilteredArrList;
				}
				return results;
			}
		};
		return filter;

	}
}
