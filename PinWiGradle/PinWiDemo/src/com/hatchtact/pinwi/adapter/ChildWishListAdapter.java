package com.hatchtact.pinwi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.child.ChildWishListDetailActivity;
import com.hatchtact.pinwi.classmodel.GetListOfWishListsByChildID;
import com.hatchtact.pinwi.classmodel.GetListOfWishListsByChildIDList;
import com.hatchtact.pinwi.fragment.network.CustomAsyncTask;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class ChildWishListAdapter extends ArrayAdapter<GetListOfWishListsByChildID>
{
	public GetListOfWishListsByChildIDList listWishlists = new GetListOfWishListsByChildIDList();
	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private int[] arr={R.drawable.add_i,R.drawable.delete_i ,R.drawable.accept_i,R.drawable.add_i};
	private ShowMessages showMessage;

	public ChildWishListAdapter(Context context, GetListOfWishListsByChildIDList list)
	{
		super(context, R.layout.wishlist_list_item);
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listWishlists = list;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() 
	{
		if (listWishlists!= null && listWishlists.getListOfWishListsByChildID()!=null)  
		{
			return listWishlists.getListOfWishListsByChildID().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetListOfWishListsByChildID getItem(int position) {

		return listWishlists.getListOfWishListsByChildID().get(position);
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
			view = inflater.inflate(R.layout.wishlist_list_item, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}
		
		holder.wishlist_name.setText(listWishlists.getListOfWishListsByChildID().get(position).getActivityName());	
		holder.txt_child_wishlist_number.setText(listWishlists.getListOfWishListsByChildID().get(position).getFriendsDoingThis());
		holder.layoutConnections.setTag(listWishlists.getListOfWishListsByChildID().get(position));
		holder.wishlist_image.setTag(listWishlists.getListOfWishListsByChildID().get(position));
		holder.layoutConnections.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetListOfWishListsByChildID model= (GetListOfWishListsByChildID) v.getTag();
				StaticVariables.childIdBuddiesDetail=model.getActivityID();
				StaticVariables.childWishlistName=model.getActivityName();
				//pass intent to child detail
				context.startActivity(new Intent(context,ChildWishListDetailActivity.class));
				((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				((Activity) context).finish();

			}
		});
	
		
		holder.wishlist_image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sending request.
				//accepting request
				//removing connection

				final GetListOfWishListsByChildID model= (GetListOfWishListsByChildID) v.getTag();

				if(model.getIsWished().equalsIgnoreCase("false"))
				{
					// TODO Auto-generated method stub
					// <----- This is the key 
					new CustomAsyncTask(context, model.getActivityID(), "4", StaticVariables.currentChild.getChildID()+"",4, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							model.setIsWished("true");
							listWishlists.getListOfWishListsByChildID().set(position, model);
							notifyDataSetChanged();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub
							//net not available
						}
					}).execute();





				}
				else 				
				{

					// TODO Auto-generated method stub
					// <----- This is the key 
					new CustomAsyncTask(context, model.getActivityID(), "5", StaticVariables.currentChild.getChildID()+"",5, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							model.setIsWished("false");
							listWishlists.getListOfWishListsByChildID().set(position, model);
							notifyDataSetChanged();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub
							//net not available
						}
					}).execute();





				
				}
					}
		});
		final GetListOfWishListsByChildID model= listWishlists.getListOfWishListsByChildID().get(position);

		if(model.getIsWished().equalsIgnoreCase("true"))
		{
			holder.wishlist_image.setImageResource(R.drawable.wishlist_active);
		}
		else
		{
			holder.wishlist_image.setImageResource(R.drawable.wishlist_child);

		}
		

		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.wishlist_image = (ImageView) view.findViewById(R.id.wishlist_image);
		holder.txt_child_wishlist_number = (TextView) view.findViewById(R.id.txt_child_wishlist_number);
		holder.wishlist_name = (TextView) view.findViewById(R.id.wishlist_name);
		typeFace.setTypefaceGothamBold(holder.wishlist_name);
		typeFace.setTypefaceGotham(holder.txt_child_wishlist_number);
		holder.txt_child_wishlist_number.setAlpha(.7f);
		holder.layoutConnections=(RelativeLayout) view.findViewById(R.id.layoutConnections);

		return holder;
	}

	private  class ViewHolder 
	{
		private ImageView wishlist_image;
		private RelativeLayout layoutConnections;
		private TextView wishlist_name;
		private TextView txt_child_wishlist_number;
	}


	private int dp2px(int dp) 
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
}
