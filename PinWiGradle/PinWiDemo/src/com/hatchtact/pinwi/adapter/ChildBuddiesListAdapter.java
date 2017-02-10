package com.hatchtact.pinwi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.child.ChildBuddiesActivity;
import com.hatchtact.pinwi.child.ChildBuddiesDetailActivity;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.classmodel.GetListOfBuddiesByChildIDOnCI;
import com.hatchtact.pinwi.classmodel.GetListOfBuddiesByChildIDOnCIList;
import com.hatchtact.pinwi.fragment.network.CustomAsyncTask;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class ChildBuddiesListAdapter extends ArrayAdapter<GetListOfBuddiesByChildIDOnCI>
{
	public GetListOfBuddiesByChildIDOnCIList listBuddies = new GetListOfBuddiesByChildIDOnCIList();
	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private int[] arr={R.drawable.add_i,R.drawable.delete_i ,R.drawable.accept_i,R.drawable.add_i};
	private ShowMessages showMessage;
	private ChildBuddiesActivity actContext;
	public ChildBuddiesListAdapter(Context context, GetListOfBuddiesByChildIDOnCIList list, ChildBuddiesActivity childBuddiesActivity)
	{
		super(context, R.layout.buddies_list_item);
		// TODO Auto-generated constructor stub
		this.context = context;
		actContext=childBuddiesActivity;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listBuddies = list;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() 
	{
		if (listBuddies!= null && listBuddies.getBuddiesByChildIDOnCI()!=null)  
		{
			return listBuddies.getBuddiesByChildIDOnCI().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetListOfBuddiesByChildIDOnCI getItem(int position) {

		return listBuddies.getBuddiesByChildIDOnCI().get(position);
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
			view = inflater.inflate(R.layout.buddies_list_item, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

		if(listBuddies.getBuddiesByChildIDOnCI().get(position).getProfileImage()!=null && listBuddies.getBuddiesByChildIDOnCI().get(position).getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(listBuddies.getBuddiesByChildIDOnCI().get(position).getProfileImage(), 0);
			if(imageByteParent!=null)
			{
				Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
				holder.child_buddies_image.setImageBitmap(bitmap);
			}
		}
		else
		{
			/*holder.child_buddies_image.setBackgroundResource(R.drawable.child_image);	
			holder.child_buddies_image.setImageBitmap(null);*/
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.default_buddies), dp2px(80), dp2px(80), false);
			holder.child_buddies_image.setImageBitmap(bitmap);
		}

		if(listBuddies.getBuddiesByChildIDOnCI().get(position).getProfileImage()!=null && listBuddies.getBuddiesByChildIDOnCI().get(position).getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(listBuddies.getBuddiesByChildIDOnCI().get(position).getProfileImage(), 0);
			if(imageByteParent!=null)
			{
				Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
				holder.child_buddies.setImageBitmap(bitmap);
			}
		}
		else
		{
			/*holder.child_buddies.setBackgroundResource(R.drawable.child_image);	
			holder.child_buddies.setImageBitmap(null);*/
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.default_buddies), dp2px(80), dp2px(80), false);
			holder.child_buddies.setImageBitmap(bitmap);
		}

		holder.tick_buddies.setImageResource(arr[Integer.parseInt(listBuddies.getBuddiesByChildIDOnCI().get(position).getStatus())]);
		holder.txt_child_buddies_number.setText(listBuddies.getBuddiesByChildIDOnCI().get(position).getTotalFriend());	
		holder.buddies_name.setText(listBuddies.getBuddiesByChildIDOnCI().get(position).getChildName());
		holder.layout_buddies_connection.setTag(listBuddies.getBuddiesByChildIDOnCI().get(position));
		holder.tick_buddies.setTag(listBuddies.getBuddiesByChildIDOnCI().get(position));
		holder.layout_buddies_connection.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetListOfBuddiesByChildIDOnCI model= (GetListOfBuddiesByChildIDOnCI) v.getTag();
				StaticVariables.childIdBuddiesDetail=model.getFriendID();
				//pass intent to child detail
				context.startActivity(new Intent(context,ChildBuddiesDetailActivity.class));
				((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				((Activity) context).finish();

			}
		});
		/*holder.tick_buddies.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetListOfBuddiesByChildIDOnCI model= (GetListOfBuddiesByChildIDOnCI) v.getTag();
				StaticVariables.childIdBuddiesDetail=model.getFriendID();
				//now use loggedid and action flag

			}
		});*/

		holder.tick_buddies.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sending request.
				//accepting request
				//removing connection

				final GetListOfBuddiesByChildIDOnCI model= (GetListOfBuddiesByChildIDOnCI) v.getTag();

				if(model.getStatus().equalsIgnoreCase("1"))
				{
					showMessage.showAlertChild(context,new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							// TODO Auto-generated method stub
							// <----- This is the key 
							new CustomAsyncTask(context, model.getFriendID(), "3", StaticVariables.currentChild.getChildID()+"",2, new OnEventListener<String>() {

								@Override
								public void onSuccess(String object) 
								{
									// TODO Auto-generated method stub

									listBuddies.getBuddiesByChildIDOnCI().remove(model);
									notifyDataSetChanged();
									if(listBuddies.getBuddiesByChildIDOnCI()!=null && listBuddies.getBuddiesByChildIDOnCI().size()==0)
									{
										actContext.errorMessageNoFriend();
										//fragmentContext.setInviteButton();
										//Toast.makeText(context, "Buddy has been removed from list", Toast.LENGTH_SHORT).show();
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
				else if(model.getStatus().equalsIgnoreCase("2"))				
				{

					new CustomAsyncTask(context, model.getFriendID(), "1", StaticVariables.currentChild.getChildID()+"",2, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							//getFriendsListByloggedId.getGetFriendsListByLoggedID().remove(model);

							/*holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
							holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_red_color));
							holder.btn_networkconnectionitem.setText("Remove");*/
							model.setStatus("1");
							listBuddies.getBuddiesByChildIDOnCI().set(position, model);
							notifyDataSetChanged();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub
							//net not available
						}
					}).execute();


				}
				else if(model.getStatus().equalsIgnoreCase("3"))				
				{

					new CustomAsyncTask(context, model.getFriendID(), "0", StaticVariables.currentChild.getChildID()+"",3, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) 
						{
							// TODO Auto-generated method stub
							//getFriendsListByloggedId.getGetFriendsListByLoggedID().remove(model);

							/*holder.btn_networkconnectionitem.setBackgroundResource(R.drawable.rectangular_btn_redcolor);
							holder.btn_networkconnectionitem.setTextColor(context.getResources().getColor(R.color.font_red_color));
							holder.btn_networkconnectionitem.setText("Remove");*/
							model.setStatus("0");
							notifyDataSetChanged();
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub
							//net not available
						}
					}).execute();


				}	
				else if(model.getStatus().equalsIgnoreCase("0"))				
				{
					Toast.makeText(context, "This buddy request is already sent. ", Toast.LENGTH_LONG).show();

				}
			}
		});
		final GetListOfBuddiesByChildIDOnCI model= listBuddies.getBuddiesByChildIDOnCI().get(position);

		if(model.getStatus().equalsIgnoreCase("1"))
		{
			holder.tick_buddies.setImageResource(R.drawable.delete_i);
			holder.tick_buddies.setAlpha(1f);

		}
		else if(model.getStatus().equalsIgnoreCase("2"))
		{
			holder.tick_buddies.setImageResource(R.drawable.accept_i);
			holder.tick_buddies.setAlpha(1f);


		}
		else if(model.getStatus().equalsIgnoreCase("3"))
		{
			holder.tick_buddies.setImageResource(R.drawable.add_i);
			holder.tick_buddies.setAlpha(1f);
			holder.tick_buddies.setEnabled(true);
		}
		else if(model.getStatus().equalsIgnoreCase("0"))
		{
			holder.tick_buddies.setImageResource(R.drawable.add_i);
			holder.tick_buddies.setAlpha(.3f);
			holder.tick_buddies.setEnabled(true);
		}

		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.child_buddies_image = (HexagonImageView) view.findViewById(R.id.child_buddies_image);
		holder.child_buddies = (HexagonImageView)view.findViewById(R.id.child_buddies);
		holder.txt_child_buddies_number = (TextView) view.findViewById(R.id.txt_child_buddies_number);
		holder.buddies_name = (TextView) view.findViewById(R.id.buddies_name);
		typeFace.setTypefaceGothamBold(holder.buddies_name);
		typeFace.setTypefaceGotham(holder.txt_child_buddies_number);
		holder.txt_child_buddies_number.setAlpha(.6f);
		holder.child_buddies_small_image=(ImageView) view.findViewById(R.id.child_buddies_small_image);
		holder.tick_buddies=(ImageView) view.findViewById(R.id.tick_buddies);		
		holder.layout_buddies_connection=(RelativeLayout) view.findViewById(R.id.layout_buddies_connection);

		return holder;
	}

	private  class ViewHolder 
	{
		private HexagonImageView child_buddies_image;
		private HexagonImageView child_buddies;
		private ImageView child_buddies_small_image;
		private ImageView tick_buddies;
		private RelativeLayout layout_buddies_connection;
		private TextView txt_child_buddies_number;
		private TextView buddies_name;
	}


	private int dp2px(int dp) 
	{

		if(SplashActivity.ScreenWidth >= 2000)
		{
			dp = 60;

		}

		else if(SplashActivity.ScreenWidth >= 1000)
		{
			dp = 60;

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
