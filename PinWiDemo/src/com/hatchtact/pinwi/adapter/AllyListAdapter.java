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
import com.hatchtact.pinwi.classmodel.ListOfAllys;
import com.hatchtact.pinwi.utility.TypeFace;

public class AllyListAdapter extends ArrayAdapter<ListOfAllys> implements Filterable
{

	//public ArrayList<ListOfAllys>  list_allyName = new ArrayList<ListOfAllys>();
	private List<ListOfAllys> getAlllist_allyName=null;
	public List<ListOfAllys > filteredlist_allyName = null;

	private LayoutInflater inflater;
	
	private TypeFace typeFace=null;

	public AllyListAdapter(Context context, ArrayList<ListOfAllys> list)
	{
		super(context, R.layout.list_item_subject, list);
		// TODO Auto-generated constructor stub
		typeFace=new TypeFace(context);
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getAlllist_allyName = list;
		this.filteredlist_allyName = list;
	}

	@Override
	public int getCount() {
		if (getAlllist_allyName != null) 
		{
			return getAlllist_allyName.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public ListOfAllys getItem(int position) {

		return getAlllist_allyName.get(position);
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


		holder.allyName_text.setText(getAlllist_allyName.get(position).getAllyName());	
		
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.allyName_text = (TextView) view.findViewById(R.id.item);
		typeFace.setTypefaceRegular(holder.allyName_text);
		
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView allyName_text;
	}
	
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) {

				getAlllist_allyName = (ArrayList<ListOfAllys>) results.values; // has the filtered values
				notifyDataSetChanged();  // notifies the data with new filtered values
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
				ArrayList<ListOfAllys> FilteredArrList = new ArrayList<ListOfAllys>();

				if (filteredlist_allyName == null) {
					filteredlist_allyName = new ArrayList<ListOfAllys>(filteredlist_allyName); // saves the original data in mOriginalValues
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
					results.count = filteredlist_allyName.size();
					results.values = filteredlist_allyName;
				} else {
					constraint = constraint.toString().toLowerCase();
					for (int i = 0; i < filteredlist_allyName.size(); i++) {
						String name=filteredlist_allyName.get(i).getAllyName();
						String data = "" ;
						if(name!=null && name.length()>0)
						{
							data=name;	
						}
						
						if (data.toLowerCase().contains(constraint.toString())) 
						{
							//String num,String name,String lastmsg,String Date,int statusRead,long id,String image
							FilteredArrList.add(filteredlist_allyName.get(i));
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
