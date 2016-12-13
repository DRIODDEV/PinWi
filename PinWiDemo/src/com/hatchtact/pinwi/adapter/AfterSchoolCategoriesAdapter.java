package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AfterSchoolCategoriesByOwnerID;
import com.hatchtact.pinwi.utility.TypeFace;

public class AfterSchoolCategoriesAdapter extends ArrayAdapter<AfterSchoolCategoriesByOwnerID> implements Filterable
{

	//public ArrayList<AfterSchoolCategoriesByOwnerID>  list_categories = new ArrayList<AfterSchoolCategoriesByOwnerID>();

	private List<AfterSchoolCategoriesByOwnerID> getAlllist_categories=null;
	public List<AfterSchoolCategoriesByOwnerID > filteredlist_categories = null;
	
	private LayoutInflater inflater;
	
	private TypeFace typeFace=null;

	public AfterSchoolCategoriesAdapter(Context context, ArrayList<AfterSchoolCategoriesByOwnerID> list)
	{
		super(context, R.layout.list_item_subject, list);
		// TODO Auto-generated constructor stub

		typeFace=new TypeFace(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getAlllist_categories = list;
		this.filteredlist_categories=list;
	}  

	@Override
	public int getCount() {        
		if (getAlllist_categories != null) 
		{
			return getAlllist_categories.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public AfterSchoolCategoriesByOwnerID getItem(int position) {

		return getAlllist_categories.get(position);
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


		holder.categoryName_text.setText(getAlllist_categories.get(position).getName());	
		
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.categoryName_text = (TextView) view.findViewById(R.id.item);
		typeFace.setTypefaceRegular(holder.categoryName_text);
		
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView categoryName_text;
	}
	
	
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) {

				getAlllist_categories = (ArrayList<AfterSchoolCategoriesByOwnerID>) results.values; // has the filtered values
				notifyDataSetChanged();  // notifies the data with new filtered values
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
				ArrayList<AfterSchoolCategoriesByOwnerID> FilteredArrList = new ArrayList<AfterSchoolCategoriesByOwnerID>();

				if (filteredlist_categories == null) {
					filteredlist_categories = new ArrayList<AfterSchoolCategoriesByOwnerID>(getAlllist_categories); // saves the original data in mOriginalValues
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
					results.count = filteredlist_categories.size();
					results.values = filteredlist_categories;
				} else {
					constraint = constraint.toString().toLowerCase();
					for (int i = 0; i < filteredlist_categories.size(); i++) {
						String name=filteredlist_categories.get(i).getName();
						String data = "" ;
						if(name!=null && name.length()>0)
						{
							data=name;	
						}
						
						if (data.toLowerCase().contains(constraint.toString())) 
						{
							//String num,String name,String lastmsg,String Date,int statusRead,long id,String image
							FilteredArrList.add(filteredlist_categories.get(i));
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
