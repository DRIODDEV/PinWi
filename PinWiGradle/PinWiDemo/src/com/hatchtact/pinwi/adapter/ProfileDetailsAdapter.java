package com.hatchtact.pinwi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.classmodel.GetProfileDetailsByLoggedID;
import com.hatchtact.pinwi.classmodel.GetProfileDetailsByLoggedIDList;
import com.hatchtact.pinwi.fragment.network.MyProfileScreenFragment;
import com.hatchtact.pinwi.fragment.network.ZoomScreenActivity;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class ProfileDetailsAdapter extends BaseAdapter
{

	private GetProfileDetailsByLoggedIDList getProfileDetailsByLoggedIDList;

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private MyProfileScreenFragment fragment;

	public ProfileDetailsAdapter(Context context, GetProfileDetailsByLoggedIDList list,MyProfileScreenFragment frag)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		fragment=frag;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list.getGetProfileDetailsByLoggedIDList().remove(0);
		this.getProfileDetailsByLoggedIDList = list;
		typeFace=new TypeFace(context);

	}

	@Override
	public int getCount() {
		if (getProfileDetailsByLoggedIDList.getGetProfileDetailsByLoggedIDList() != null) 
		{
			return getProfileDetailsByLoggedIDList.getGetProfileDetailsByLoggedIDList().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetProfileDetailsByLoggedID getItem(int position) {

		return getProfileDetailsByLoggedIDList.getGetProfileDetailsByLoggedIDList().get(position);
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
			view = inflater.inflate(R.layout.layout_child_itemprofile, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}


		GetProfileDetailsByLoggedID model=getProfileDetailsByLoggedIDList.getGetProfileDetailsByLoggedIDList().get(position);

		if(model.getProfileImage()!=null && model.getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(model.getProfileImage(), 0);
			if(imageByteParent!=null)
			{
				holder.imgUserChild.setBackgroundResource(0);	

				holder.imgUserChild.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length)));	
			}
		}
		else
		{
			holder.imgUserChild.setBackgroundResource(R.drawable.child_image);	
			holder.imgUserChild.setImageBitmap(null);
		}

		//setUnreadBackgroundDrawable(holder.textViewnotification_image);

		holder.txtChildName.setText(model.getChildName());
		holder.txtdobprofile.setText(model.getAge() +" Years Old, Born On: "+model.getDateOfBirth());
		holder.txtschoolprofilechild.setText("School: "+model.getSchoolName1());
		holder.txtdobprofile.setText(model.getAge() +" Years Old, Born On: "+model.getDateOfBirth());
		holder.textViewnotification_image.setText(model.getPinWiConnection());

		holder.imgUserChild.setTag(model);
		holder.layoutconnbtnprofile.setTag(model);
		holder.btn_networkconnectionitemprofile.setTag(model);
		
		holder.btn_networkconnectionitemprofile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//zoom screen activity call	
				GetProfileDetailsByLoggedID model= (GetProfileDetailsByLoggedID) v.getTag();
				StaticVariables.fragmentIndexCurrentTabNetwork=208;
				StaticVariables.NetworkChildIdProfileConnections=model.getChildID();
				StaticVariables.NetworkExhilaratorChildNameProfileConnections=model.getChildName();
				fragment.switchFrag();

			}
		});
		
		holder.layoutconnbtnprofile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//zoom screen activity call	
				GetProfileDetailsByLoggedID model= (GetProfileDetailsByLoggedID) v.getTag();
				StaticVariables.fragmentIndexCurrentTabNetwork=208;
				StaticVariables.NetworkChildId=model.getChildID();
				StaticVariables.NetworkExhilaratorChildName=model.getChildName();
				fragment.switchFrag();

			}
		});

		holder.imgUserChild.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//zoom screen activity call	
				GetProfileDetailsByLoggedID model= (GetProfileDetailsByLoggedID) v.getTag();
				Intent intent=new Intent(context,ZoomScreenActivity.class);
				//intent.putExtra("byteArray", model.getProfileImage());
				StaticVariables.byteArray=model.getProfileImage();
				context.startActivity(intent);

			}
		});



		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.imgUserChild = (ImageView) view.findViewById(R.id.imgUserChild);
		holder.txtChildName = (TextView) view.findViewById(R.id.txtChildName);
		holder.txtdobprofile = (TextView) view.findViewById(R.id.txtdobprofile);
		holder.txtschoolprofilechild = (TextView) view.findViewById(R.id.txtschoolprofilechild);
		holder.txtSibling = (TextView) view.findViewById(R.id.txtSibling);
		holder.btn_networkconnectionitemprofile = (Button) view.findViewById(R.id.btn_networkconnectionitemprofile);
		holder.textViewnotification_image=(TextView) view.findViewById(R.id.textViewnotification_image);
		holder.layoutconnbtnprofile=(RelativeLayout) view.findViewById(R.id.layoutconnbtnprofile);

		typeFace.setTypefaceRegular(holder.txtChildName);
		typeFace.setTypefaceRegular(holder.txtdobprofile);
		typeFace.setTypefaceRegular(holder.txtschoolprofilechild);
		typeFace.setTypefaceRegular(holder.txtSibling);
		typeFace.setTypefaceRegular(holder.textViewnotification_image);
		typeFace.setTypefaceRegular(holder.btn_networkconnectionitemprofile);
		return holder;
	}

	private  class ViewHolder 
	{
		private ImageView imgUserChild;
		private TextView txtChildName;
		private TextView txtdobprofile;
		private TextView txtschoolprofilechild;
		private TextView txtSibling;
		private TextView textViewnotification_image;
		private Button btn_networkconnectionitemprofile;
		private RelativeLayout layoutconnbtnprofile;
	}



	private Bitmap getRoundedRectBitmap(Bitmap bitmap) 
	{

		int pixels;

		if(SplashActivity.ScreenWidth >= 1000)
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

	@SuppressLint("NewApi")
	public void setUnreadBackgroundDrawable(View view)
	{
		// edited to support big numbers bigger than 0x80000000
		//int color = Color.parseColor(myColorString);

		ShapeDrawable biggerCircle= new ShapeDrawable( new OvalShape());
		biggerCircle.setIntrinsicHeight(view.getHeight());
		biggerCircle.setIntrinsicWidth( view.getWidth());
		biggerCircle.setBounds(new Rect(0, 0, 120, 60));
		int newColor = Color.argb(156,49, 157, 197);

		biggerCircle.getPaint().setColor(newColor);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) 
		{
			view.setBackgroundDrawable(biggerCircle);

		} else {
			view.setBackground(biggerCircle);

		}

	}
}
