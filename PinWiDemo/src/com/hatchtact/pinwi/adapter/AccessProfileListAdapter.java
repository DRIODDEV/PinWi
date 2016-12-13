package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.classmodel.AccessProfile;
import com.hatchtact.pinwi.utility.TypeFace;


public class AccessProfileListAdapter extends ArrayAdapter<AccessProfile>{

	public ArrayList<AccessProfile>  list_accessProfile = new ArrayList<AccessProfile>();

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	public AccessProfileListAdapter(Context context, ArrayList<AccessProfile> list)
	{
		super(context, R.layout.access_list_item, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_accessProfile = list;
		typeFace=new TypeFace(context);

	}

	@Override
	public int getCount() {
		if (list_accessProfile != null) 
		{
			return list_accessProfile.size();
		} 
		else 
		{
			return 0;
		}          
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

	ViewHolder holder;

	@Override
	public View getView(final int position, View view, ViewGroup parent) 
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.access_list_item, null);
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
			holder.access_bottom_layer.setVisibility(View.VISIBLE);

			if(list_accessProfile.get(position).getProfileImage()!=null && list_accessProfile.get(position).getProfileImage().trim().length()>100)
			{
				byte[] imageByte=Base64.decode(list_accessProfile.get(position).getProfileImage(), 0);

				if(imageByte!=null)
				{
					holder.accessParentProfileImage.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length)));	
				}
			}
			else
			{
				holder.accessParentProfileImage.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.child_image)));	

				//holder.accessParentProfileImage.setBackgroundResource(R.drawable.child_image);	
				//holder.accessParentProfileImage.setImageBitmap(null);

			}
			holder.name_text.setText(list_accessProfile.get(position).getFirstName());	
			holder.earned_text.setText(list_accessProfile.get(position).getEarnedPoints()+"");
			holder.pending_text.setText(list_accessProfile.get(position).getPendingPoints()+"");

			//StaticVariables.childInfo1.addAll(list_accessProfile.get(position));
		}	
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.accessParentProfileImage = (ImageView) view.findViewById(R.id.access_profile_image);
		holder.name_text = (TextView) view.findViewById(R.id.name_parentchild);
		typeFace.setTypefaceRegular(holder.name_text);

		holder.earned_text = (TextView) view.findViewById(R.id.earned_Text);
		typeFace.setTypefaceLight(holder.earned_text);

		holder.pending_text = (TextView) view.findViewById(R.id.pending_Text);
		typeFace.setTypefaceLight(holder.pending_text);

		holder.earned_image=(ImageView) view.findViewById(R.id.earnedcoin_image);
		holder.pending_image=(ImageView) view.findViewById(R.id.pendingcoin_image);		
		holder.next_image=(ImageView) view.findViewById(R.id.next_parentImage);

		holder.access_bottom_layer=(RelativeLayout) view.findViewById(R.id.access_bottom_layer);

		return holder;
	}

	private  class ViewHolder 
	{
		private ImageView accessParentProfileImage;
		private TextView name_text;
		private TextView earned_text;
		private TextView pending_text;
		private ImageView next_image;
		private ImageView earned_image;
		private ImageView pending_image;
		private RelativeLayout access_bottom_layer;
	}

	
/*	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		
		int targetWidth;
		if(SplashActivity.ScreenWidth >= 700)
		{
			 targetWidth = 170;
			
		}
		else
		{
			targetWidth = 100;
		}
		
		int targetHeight = targetWidth;
		
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
				targetHeight,Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), 
						((float) targetHeight)) / 2),
						Path.Direction.CCW);

		canvas.clipPath(path); 

		 Paint p = new Paint();
		p.setAntiAlias(true);
		//p.setFilterBitmap(true);
		 		
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, 
				new Rect(0, 0, sourceBitmap.getWidth(),
						sourceBitmap.getHeight()), 
						new Rect(0, 0, targetWidth, targetHeight), null);
		return targetBitmap;
	}
	*/
	
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

		       paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		       canvas.drawBitmap(bitmap, rect, rect, paint);
		   } catch (NullPointerException e) {
		       // return bitmap;
		   } catch (OutOfMemoryError o) {
		   }
		   return result;
		}
}
