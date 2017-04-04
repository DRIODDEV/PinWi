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
import com.hatchtact.pinwi.classmodel.CategoriesAndSubCategories;
import com.hatchtact.pinwi.utility.TypeFace;

public class AddAfterSchoolSubCategoriesAdapter extends ArrayAdapter<CategoriesAndSubCategories> implements Filterable
{

	//public ArrayList<CategoriesAndSubCategories>  list_subCategories = new ArrayList<CategoriesAndSubCategories>();

	private List<CategoriesAndSubCategories> getAlllist_subCategories=null;
	public List<CategoriesAndSubCategories > filteredlist_subCategories = null;
	
	private LayoutInflater inflater;
	
	private TypeFace typeFace=null;

	public AddAfterSchoolSubCategoriesAdapter(Context context, ArrayList<CategoriesAndSubCategories> list)
	{
		super(context, R.layout.list_item_subject, list);
		// TODO Auto-generated constructor stub
		typeFace=new TypeFace(context);
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getAlllist_subCategories = list;
		this.filteredlist_subCategories = list;

	}

	@Override
	public int getCount() {
		if (getAlllist_subCategories != null) 
		{
			return getAlllist_subCategories.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public CategoriesAndSubCategories getItem(int position) {

		return getAlllist_subCategories.get(position);
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


		holder.subcategoriesName_text.setText(getAlllist_subCategories.get(position).getCategoryName());	
		typeFace.setTypefaceRegular(holder.subcategoriesName_text);
		
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.subcategoriesName_text = (TextView) view.findViewById(R.id.item);

		return holder;
	}

	private  class ViewHolder 
	{
		private TextView subcategoriesName_text;
	}
	
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) {

				getAlllist_subCategories = (ArrayList<CategoriesAndSubCategories>) results.values; // has the filtered values
				notifyDataSetChanged();  // notifies the data with new filtered values
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
				ArrayList<CategoriesAndSubCategories> FilteredArrList = new ArrayList<CategoriesAndSubCategories>();

				if (filteredlist_subCategories == null) {
					filteredlist_subCategories = new ArrayList<CategoriesAndSubCategories>(getAlllist_subCategories); // saves the original data in mOriginalValues
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
					results.count = filteredlist_subCategories.size();
					results.values = filteredlist_subCategories;
				} else {
					constraint = constraint.toString().toLowerCase();
					for (int i = 0; i < filteredlist_subCategories.size(); i++) {
						String name=filteredlist_subCategories.get(i).getCategoryName();
						String data = "" ;
						if(name!=null && name.length()>0)
						{
							data=name;	
						}
						
						if (data.toLowerCase().contains(constraint.toString())) 
						{
							//String num,String name,String lastmsg,String Date,int statusRead,long id,String image
							FilteredArrList.add(filteredlist_subCategories.get(i));
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
