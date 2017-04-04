package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.ChildListActivity;
import com.hatchtact.pinwi.ChildListFreeActivity;
import com.hatchtact.pinwi.ChildRegistrationActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.classmodel.AccessProfile;
import com.hatchtact.pinwi.classmodel.GetListofChildsByParentID;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildFreeListAdapter extends ArrayAdapter<AccessProfile>{
	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ChildListFreeActivity childListActivity;
	public ArrayList<AccessProfile>  list_accessProfile = new ArrayList<AccessProfile>();


	public ChildFreeListAdapter(Context context, ArrayList<AccessProfile> list,ChildListFreeActivity childListActivity)
	{
		super(context, R.layout.item_free_child_list, list);
		// TODO Auto-generated constructor stub

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_accessProfile = list;
		this.context=context;
		this.childListActivity=childListActivity;
		typeFace= new TypeFace(context);
	}

	@Override
	public int getCount() {
		int count = 0;
		if (list_accessProfile != null && list_accessProfile.size()>0)
		{
			count = list_accessProfile.size();
		}
		return count;
	}

	@Override
	public AccessProfile getItem(int position) {

		return list_accessProfile.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	//	ViewHolder holder;

/*	@Override
	public View getView(final int position, View view, ViewGroup parent) 
	{
		final ViewHolder holder;
		if (view == null)
		{
			view = inflater.inflate(R.layout.item_ally_list, null);
			holder = createViewHolder(view);
			view.setTag(holder);

		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}


		holder.childName_text.setText(list_childName.get(position).getNickName());	


		return view;
	}*/

	private ViewHolder createViewHolder(View view)
	{
		ViewHolder holder = new ViewHolder();

		holder.name_parentchild = (TextView) view.findViewById(R.id.name_parentchild);
		typeFace.setTypefaceRegular(holder.name_parentchild);
		holder.access_profile_image = (ImageView) view.findViewById(R.id.access_profile_image);
		holder.next_parentImage = (ImageView) view.findViewById(R.id.next_parentImage);
		holder.emptyLine = (View) view.findViewById(R.id.emptyLine);


		return holder;
	}

	private  class ViewHolder
	{
		private TextView name_parentchild;
		private ImageView access_profile_image;
		private ImageView next_parentImage;
		private View  emptyLine;

	}
	ViewHolder holder;

	@Override
	public View getView(final int position, View view, ViewGroup parent)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.item_free_child_list, null);
			holder = createViewHolder(view);
			view.setTag(holder);

		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}

		//For Parent
	/*	if(list_accessProfile.get(position).getProfileType()==1 && position==0)
		{
			if(list_accessProfile.get(position).getProfileImage()!=null && list_accessProfile.get(position).getProfileImage().trim().length()>100)
			{
				byte[] imageByteParent=Base64.decode(list_accessProfile.get(position).getProfileImage(), 0);
				if(imageByteParent!=null)
				{
					holder.accessParentProfileImage.setBackgroundResource(0);

					holder.accessParentProfileImage.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length)));
				}
			}
			else
			{
				holder.accessParentProfileImage.setBackgroundResource(R.drawable.parent_image);
				holder.accessParentProfileImage.setImageBitmap(null);
			}

			holder.name_text.setText(list_accessProfile.get(position).getFirstName());
			holder.access_bottom_layer.setVisibility(View.GONE);

			holder.earned_text.setVisibility(View.INVISIBLE);
			holder.pending_text.setVisibility(View.INVISIBLE);
			holder.earned_image.setVisibility(View.INVISIBLE);
			holder.pending_image.setVisibility(View.INVISIBLE);
		}*/

		//For Child
		/*else */if(list_accessProfile.get(position).getProfileType()==2)
	{

		if(list_accessProfile.get(position).getProfileImage()!=null && list_accessProfile.get(position).getProfileImage().trim().length()>100)
		{
			byte[] imageByte= Base64.decode(list_accessProfile.get(position).getProfileImage(), 0);

			if(imageByte!=null)
			{
				holder.access_profile_image.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length)));
			}
		}
		else
		{
			holder.access_profile_image.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.child_image)));
		}
		holder.name_parentchild.setText(list_accessProfile.get(position).getFirstName());
		if(position==list_accessProfile.size()-1)
		{
			holder.emptyLine.setVisibility(View.GONE);
		}
		else
		{
			holder.emptyLine.setVisibility(View.VISIBLE);
		}
	}
		return view;
	}



	private Bitmap getRoundedRectBitmap(Bitmap bitmap) {

		int pixels;

		if(SplashActivity.ScreenWidth >= 2000)
		{
			pixels = 280;

		}

		else if(SplashActivity.ScreenWidth >= 1000)
		{
			pixels = 227;

		}
		else if(SplashActivity.ScreenWidth >= 700)
		{
			pixels = 170;

		}
		else if(SplashActivity.ScreenWidth >= 590)
		{
			pixels = 128;
		}
		else
		{
			pixels = 100;
		}

		Bitmap result = null;
		try {
			bitmap = Bitmap.createScaledBitmap(bitmap, pixels, pixels, false);

			result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Bitmap.Config.ARGB_8888);

			Canvas canvas = new Canvas(result);

			int color = 0xFF424242;
			Paint paint = new Paint();

			Rect rect = new Rect(0, 0, pixels, pixels);
			RectF rectF = new RectF(rect);
			int roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (NullPointerException e) {
			// return bitmap;
		} catch (OutOfMemoryError o) {
		}
		return result;
	}
}
