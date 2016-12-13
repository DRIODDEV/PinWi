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
import com.hatchtact.pinwi.classmodel.GetPeopleYouMayKnowListByLoggedID;
import com.hatchtact.pinwi.classmodel.GetPeopleYouMayKnowListByLoggedIDList;
import com.hatchtact.pinwi.fragment.network.CustomAsyncTask;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.fragment.network.ZoomScreenActivity;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class NetworkDiscoverListAdapter extends BaseAdapter
{

	public GetPeopleYouMayKnowListByLoggedIDList getPeopleYouMayKnowListByLoggedIDList;

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;

	public NetworkDiscoverListAdapter(Context context, GetPeopleYouMayKnowListByLoggedIDList list)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getPeopleYouMayKnowListByLoggedIDList = list;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() {
		if (getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID() != null) 
		{
			return getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetPeopleYouMayKnowListByLoggedID getItem(int position) {

		return getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().get(position);
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


		GetPeopleYouMayKnowListByLoggedID model=getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().get(position);
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
			holder.imgUser.setBackgroundResource(0);
			holder.imgUser.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.parent_image)));	

			/*holder.imgUser.setBackgroundResource(R.drawable.parent_image);	
			holder.imgUser.setImageBitmap(null);*/
		}

		holder.txtParentName.setText(model.getFriendName());
		holder.txtChildrenName.setText("Children: "+model.getChildName());

		holder.btn_networkconnectionitem.setTag(model);
		holder.imgUser.setTag(model);

		holder.imgUser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//zoom screen activity call	
				GetPeopleYouMayKnowListByLoggedID model= (GetPeopleYouMayKnowListByLoggedID) v.getTag();
				Intent intent=new Intent(context,ZoomScreenActivity.class);
				//intent.putExtra("byteArray", model.getProfileImage());
				StaticVariables.byteArray=model.getProfileImage();
				context.startActivity(intent);

			}
		});

		holder.btn_networkconnectionitem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sending request.
				//accepting request
				//removing connection
				final GetPeopleYouMayKnowListByLoggedID model= (GetPeopleYouMayKnowListByLoggedID) v.getTag();
				if(model.getFStatus().equalsIgnoreCase("1"))
				{
					showMessage.showAlertNetwork(context,new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							// TODO Auto-generated method stub
							// <----- This is the key 
							new CustomAsyncTask(context, model.getFriendID()+"", "3", StaticVariables.currentParentId+"",1, new OnEventListener<String>() {

								@Override
								public void onSuccess(String object) 
								{
									// TODO Auto-generated method stub

									getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().remove(model);
									notifyDataSetChanged();

								}

								@Override
								public void onFailure(String object) {
									// TODO Auto-generated method stub
									//net not available
								}
							}).execute();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub

						}
					});


				}
				
				
				else if(model.getFStatus().equalsIgnoreCase("2") /*|| model.getFStatus().equalsIgnoreCase("2")*/)				
				{

					new CustomAsyncTask(context, model.getFriendID()+"", "1", StaticVariables.currentParentId+"",1, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							//getFriendsListByloggedId.getGetFriendsListByLoggedID().remove(model);

							/*holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
							holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_red_color));
							holder.btn_networkconnectionitem.setText("Remove");*/
							model.setFStatus("1");
							getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().set(position, model);
							notifyDataSetChanged();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub
							//net not available
						}
					}).execute();


				}
				else if(model.getFStatus().equalsIgnoreCase("5"))
				{


					new CustomAsyncTask(context, model.getFriendID()+"", "0", StaticVariables.currentParentId+"",0, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							model.setFStatus("0");
							getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().set(position, model);
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


		if(model.getFStatus().equalsIgnoreCase("1"))
		{
			holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
			holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_red_color));
			holder.btn_networkconnectionitem.setText("Remove");
			holder.btn_networkconnectionitem.setEnabled(true);
			holder.btn_networkconnectionitem.setAlpha(1f);
		}
		else if(model.getFStatus().equalsIgnoreCase("0"))//if needed more casses can be inserted.
		{
			holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_bluecolornetwork);
			holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_blue_color));
			holder.btn_networkconnectionitem.setText(" Sent  ");
			holder.btn_networkconnectionitem.setEnabled(false);
			holder.btn_networkconnectionitem.setAlpha(.5f);
		}
		else if(/*model.getFStatus().equalsIgnoreCase("0") ||*/ model.getFStatus().equalsIgnoreCase("2"))
		{
			holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_greencolornetwork);
			holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.network_green));
			holder.btn_networkconnectionitem.setText("Accept");
			holder.btn_networkconnectionitem.setEnabled(true);
			holder.btn_networkconnectionitem.setAlpha(1f);

		}
		else if(model.getFStatus().equalsIgnoreCase("5"))
		{
			holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_bluecolornetwork);
			holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_blue_color));
			holder.btn_networkconnectionitem.setText(" Add  ");
			holder.btn_networkconnectionitem.setEnabled(true);
			holder.btn_networkconnectionitem.setAlpha(1f);
		}



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
