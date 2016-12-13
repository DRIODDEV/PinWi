package com.hatchtact.pinwi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.classmodel.GetDetailByMapEmoticID;
import com.hatchtact.pinwi.classmodel.GetDetailByMapEmoticIDList;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.TypeFace;


public class ChildEmoticonDetailAdapter extends ArrayAdapter<GetDetailByMapEmoticID>
{
	public GetDetailByMapEmoticIDList listChildrens = new GetDetailByMapEmoticIDList();
	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;

	public ChildEmoticonDetailAdapter(Context context, GetDetailByMapEmoticIDList list)
	{
		super(context, R.layout.wishlistdetail_list_item);
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listChildrens = list;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() 
	{
		if (listChildrens!= null && listChildrens.getDetailByMapEmoticID()!=null)  
		{
			return listChildrens.getDetailByMapEmoticID().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetDetailByMapEmoticID getItem(int position) {

		return listChildrens.getDetailByMapEmoticID().get(position);
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
			view = inflater.inflate(R.layout.wishlistdetail_list_item, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}
		holder.child_wishlist.setVisibility(View.VISIBLE);
		if(listChildrens.getDetailByMapEmoticID().get(position).getProfileImage()!=null && listChildrens.getDetailByMapEmoticID().get(position).getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(listChildrens.getDetailByMapEmoticID().get(position).getProfileImage(), 0);
			if(imageByteParent!=null)
			{
				Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
				holder.child_wishlist.setImageBitmap(bitmap);
			}
		}
		else
		{
			/*holder.child_buddies_image.setBackgroundResource(R.drawable.child_image);	
			holder.child_buddies_image.setImageBitmap(null);*/
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.default_buddies), dp2px(80), dp2px(80), false);
			holder.child_wishlist.setImageBitmap(bitmap);
		}



		holder.child_wishlist_name.setText(listChildrens.getDetailByMapEmoticID().get(position).getChildName());	


		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.child_wishlist = (HexagonImageView) view.findViewById(R.id.child_wishlist);
		holder.child_wishlist_name = (TextView) view.findViewById(R.id.child_wishlist_name);
		typeFace.setTypefaceGotham(holder.child_wishlist_name);


		return holder;
	}

	private  class ViewHolder 
	{
		private HexagonImageView child_wishlist;
		private TextView child_wishlist_name;
	}


	private int dp2px(int dp) 
	{
		
		if(SplashActivity.ScreenWidth >= 2000)
		{
			dp = 70;

		}

		else if(SplashActivity.ScreenWidth >= 1000)
		{
			dp = 70;

		}
		else if(SplashActivity.ScreenWidth >= 700)
		{
			dp = 60;
			
		}
		else if(SplashActivity.ScreenWidth >= 590)
		{
			dp = 80;
		}
		else
		{
			dp = 60;
		}
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
}
