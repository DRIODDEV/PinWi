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
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedID;
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedIDList;
import com.hatchtact.pinwi.fragment.network.CustomAsyncTask;
import com.hatchtact.pinwi.fragment.network.NetworkConnectionsFragment;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.fragment.network.ZoomScreenActivity;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class NetworkConnectionsListAdapter extends BaseAdapter
{

	public GetFriendsListByLoggedIDList getFriendsListByloggedId;

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;
	private NetworkConnectionsFragment fragmentContext;

	public NetworkConnectionsListAdapter(Context context, GetFriendsListByLoggedIDList list, NetworkConnectionsFragment networkConnectionsFragment)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getFriendsListByloggedId = list;
		fragmentContext=networkConnectionsFragment;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() {
		if (getFriendsListByloggedId.getGetFriendsListByLoggedID() != null) 
		{
			return getFriendsListByloggedId.getGetFriendsListByLoggedID().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetFriendsListByLoggedID getItem(int position) {

		return getFriendsListByloggedId.getGetFriendsListByLoggedID().get(position);
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


		GetFriendsListByLoggedID model=getFriendsListByloggedId.getGetFriendsListByLoggedID().get(position);
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
				GetFriendsListByLoggedID model= (GetFriendsListByLoggedID) v.getTag();
				Intent intent=new Intent(context,ZoomScreenActivity.class);
				//intent.putExtra("byteArray", model.getProfileImage());
				StaticVariables.byteArray=model.getProfileImage();
				context.startActivity(intent);
				//((Activity) context).overridePendingTransition(R.drawable.zoom_in, R.drawable.zoom_out);

			}
		});

		holder.btn_networkconnectionitem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sending request.
				//accepting request
				//removing connection

				final GetFriendsListByLoggedID model= (GetFriendsListByLoggedID) v.getTag();

				if(model.getFStatus().equalsIgnoreCase("1"))
				{
					showMessage.showAlertNetwork(context,new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							// TODO Auto-generated method stub
							// <----- This is the key 
							new CustomAsyncTask(context, model.getFriendID(), "3", StaticVariables.currentParentId+"",1, new OnEventListener<String>() {

								@Override
								public void onSuccess(String object) 
								{
									// TODO Auto-generated method stub

									getFriendsListByloggedId.getGetFriendsListByLoggedID().remove(model);
									notifyDataSetChanged();
									if(getFriendsListByloggedId.getGetFriendsListByLoggedID()!=null && getFriendsListByloggedId.getGetFriendsListByLoggedID().size()==0)
									{
										fragmentContext.setInviteButton();
									}

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
				else if(model.getFStatus().equalsIgnoreCase("2"))				
				{

					new CustomAsyncTask(context, model.getFriendID(), "1", StaticVariables.currentParentId+"",1, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							//getFriendsListByloggedId.getGetFriendsListByLoggedID().remove(model);

							/*holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
							holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_red_color));
							holder.btn_networkconnectionitem.setText("Remove");*/
							model.setFStatus("1");
							getFriendsListByloggedId.getGetFriendsListByLoggedID().set(position, model);
							notifyDataSetChanged();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub
							//net not available
						}
					}).execute();


				}
				/*else if(holder.btn_networkconnectionitem.getText().toString().trim().equalsIgnoreCase("Add"))
				{


					new CustomAsyncTask(context, model.getFriendID(), "0", StaticVariables.currentParentId+"",0, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							getFriendsListByloggedId.getGetFriendsListByLoggedID().set(position, model);
							holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
							holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_red_color));
							holder.btn_networkconnectionitem.setText("Sent");
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub
							//net not available
						}
					});



				}

				 */			}
		});


		if(model.getFStatus().equalsIgnoreCase("1"))
		{
			holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
			holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_red_color));
			holder.btn_networkconnectionitem.setText("Remove");
		}
		else if(model.getFStatus().equalsIgnoreCase("2"))
		{
			holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_greencolornetwork);
			holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.network_green));
			holder.btn_networkconnectionitem.setText("Accept");

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
