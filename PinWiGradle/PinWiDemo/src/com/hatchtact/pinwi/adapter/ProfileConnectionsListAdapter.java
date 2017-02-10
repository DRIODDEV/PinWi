package com.hatchtact.pinwi.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.classmodel.GetListOfPinWiNetworksByLoggedID;
import com.hatchtact.pinwi.classmodel.GetListOfPinWiNetworksByLoggedIDList;
import com.hatchtact.pinwi.fragment.network.ZoomScreenActivity;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class ProfileConnectionsListAdapter extends BaseAdapter
{

	public GetListOfPinWiNetworksByLoggedIDList getListOfPinWiNetworksByLoggedIDList;

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;

	public ProfileConnectionsListAdapter(Context context, GetListOfPinWiNetworksByLoggedIDList list)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getListOfPinWiNetworksByLoggedIDList = list;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() {
		if (getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList() != null) 
		{
			return getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetListOfPinWiNetworksByLoggedID getItem(int position) {

		return getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().get(position);
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
			view = inflater.inflate(R.layout.list_network_connection_rowitem, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}


		GetListOfPinWiNetworksByLoggedID model=getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().get(position);
		if(model.getProfileImage()!=null && model.getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(model.getProfileImage(), 0);
			if(imageByteParent!=null)
			{
				holder.imgUser.setBackgroundResource(0);	

				holder.imgUser.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length)));	
			}
		}
		else
		{
			holder.imgUser.setBackgroundResource(R.drawable.parent_image);	
			holder.imgUser.setImageBitmap(null);
		}

		holder.txtParentName.setText(model.getChildName());
		holder.txtChildrenName.setText("Parent: "+model.getParentName());

		holder.btn_networkconnectionitem.setVisibility(View.GONE);
		holder.imgUser.setTag(model);

		holder.imgUser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//zoom screen activity call	
				GetListOfPinWiNetworksByLoggedID model= (GetListOfPinWiNetworksByLoggedID) v.getTag();
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

		holder.imgUser = (ImageView) view.findViewById(R.id.imgUser);
		holder.txtParentName = (TextView) view.findViewById(R.id.txtParentName);
		holder.txtChildrenName = (TextView) view.findViewById(R.id.txtChildrenName);
		holder.btn_networkconnectionitem = (Button) view.findViewById(R.id.btn_networkconnectionitem);

		typeFace.setTypefaceRegular(holder.txtParentName);
		typeFace.setTypefaceRegular(holder.txtChildrenName);
		typeFace.setTypefaceRegular(holder.btn_networkconnectionitem);
		return holder;
	}

	private  class ViewHolder 
	{
		private ImageView imgUser;
		private TextView txtParentName;
		private TextView txtChildrenName;
		private Button btn_networkconnectionitem;
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
}
