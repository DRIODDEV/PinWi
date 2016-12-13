package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.child.playwall.AddEmoticByMapIDTask;
import com.hatchtact.pinwi.child.playwall.ChildEmoticonDetailActivity;
import com.hatchtact.pinwi.child.playwall.ChildPlayWallActivity;
import com.hatchtact.pinwi.classmodel.GetFriendsTempleteMessageListByChildID;
import com.hatchtact.pinwi.classmodel.GetFriendsTempleteMessageListByChildIDList;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class ChildPlaywallListAdapter extends ArrayAdapter<GetFriendsTempleteMessageListByChildID>
{
	public GetFriendsTempleteMessageListByChildIDList listPlaywall = new GetFriendsTempleteMessageListByChildIDList();
	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;
	private ChildPlayWallActivity mActivity;
	private String[] innerColor = {"#6a24ae","#f7941d","#fdb813","#64ad17","#acc138","#f2716c","#5b9ddd","#a3238e"};
	private Integer[] arrayImagesSmileys={R.drawable.wow_i,R.drawable.lol_i,R.drawable.inspired_i,R.drawable.welldone_i,R.drawable.cool_i,R.drawable.me_too_i,R.drawable.love_i,R.drawable.party_time_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i,R.drawable.wow_i};
	private Integer[] arrayImagesSmileysBig={R.drawable.wow_big_i,R.drawable.lol_big_i,R.drawable.inspired_big_i,R.drawable.welldone_big_i,R.drawable.cool_big_i,R.drawable.me_too_big_i,R.drawable.love_big_i,R.drawable.party_time_big_i,R.drawable.wow_big_i,R.drawable.wow_big_i,R.drawable.wow_big_i,R.drawable.wow_big_i,R.drawable.wow_big_i,R.drawable.wow_big_i};

	/*private int previousColorIndex = 0;
	private Random random;*/
	public ChildPlaywallListAdapter(Context context, GetFriendsTempleteMessageListByChildIDList list,ChildPlayWallActivity act)
	{
		super(context, R.layout.playwall_list_item);
		// TODO Auto-generated constructor stub
		this.context = context;
		mActivity=act;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listPlaywall = list;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);
		/*random = new Random();
		previousColorIndex = 0;*/


	}

	/*private void colorIndexSelected(){
		int randomNumber = random.nextInt(innerColor.length - 1);
		if(randomNumber == previousColorIndex){
			colorIndexSelected();
		}else{
			previousColorIndex = randomNumber;
		}
	}*/
	@Override
	public int getCount() 
	{
		if (listPlaywall!= null && listPlaywall.getFriendsTempleteMessageListByChildID()!=null)  
		{
			return listPlaywall.getFriendsTempleteMessageListByChildID().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetFriendsTempleteMessageListByChildID getItem(int position) {

		return listPlaywall.getFriendsTempleteMessageListByChildID().get(position);
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
			view = inflater.inflate(R.layout.playwall_list_item, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

		//colorIndexSelected();

		GetFriendsTempleteMessageListByChildID model=listPlaywall.getFriendsTempleteMessageListByChildID().get(position);
		setProfileImage(model);
		//model.setColorHeading(innerColor[previousColorIndex]);
		//holder.playwall_heading.setBackgroundColor(Color.parseColor(innerColor[previousColorIndex]));
		holder.playwall_heading.setBackgroundColor(Color.parseColor(model.getColorHeading()));

		holder.buddies_name.setText(model.getChildName());
		holder.buddies_time.setText(model.getTime());
		holder.playwall_heading.setText(model.getTempleteText());
		holder.layoutemojione.setTag(model);
		holder.layoutemojitwo.setTag(model);
		holder.layoutemojithree.setTag(model);
		holder.layoutemojifour.setTag(model);
		holder.layoutemojifive.setTag(model);
		//mActivity.emojione.setTag(model);
		//mActivity.emojitwo.setTag(model);
		//mActivity.emojithree.setTag(model);
		//mActivity.emojifour.setTag(model);
		//mActivity.emojifive.setTag(model);
		holder.playwall_smileyimage.setTag(model);

		holder.playwall_smileyimage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!mActivity.popupWindowAction.isShowing())
				{
					//notifyDataSetChanged();
					final GetFriendsTempleteMessageListByChildID modelEmoji=(GetFriendsTempleteMessageListByChildID) v.getTag();

					ArrayList<Integer> emoticIdArray = getEmoticIdArray(modelEmoji);

					mActivity.emojione.setImageResource(arrayImagesSmileysBig[emoticIdArray.get(0)]);
					mActivity.emojitwo.setImageResource(arrayImagesSmileysBig[emoticIdArray.get(1)]);
					mActivity.emojithree.setImageResource(arrayImagesSmileysBig[emoticIdArray.get(2)]);
					mActivity.emojifour.setImageResource(arrayImagesSmileysBig[emoticIdArray.get(3)]);
					try {
						mActivity.emojifive.setImageResource(arrayImagesSmileysBig[emoticIdArray.get(4)]);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						mActivity.emojifive.setImageResource(arrayImagesSmileysBig[emoticIdArray.get(0)]);

					}
					/*mActivity .popupWindowAction.showAtLocation(v, Gravity.BOTTOM, 0,
			                v.getBottom() - 60);*/
					//mActivity.popupWindowAction.showAsDropDown(v,0,v.getTop() -40);
					//mActivity .popupWindowAction.showAtLocation(v, Gravity.TOP, 0,0);
					if(SplashActivity.ScreenWidth >= 2000)
					{
						mActivity.popupWindowAction.showAsDropDown(v,0,v.getTop()+10 );

					}

					else if(SplashActivity.ScreenWidth >= 1000)
					{
						mActivity.popupWindowAction.showAsDropDown(v,0,v.getTop()+8 );

					}
					else if(SplashActivity.ScreenWidth >= 700)
					{
						mActivity.popupWindowAction.showAsDropDown(v,0,v.getTop()+8);

					}
					else if(SplashActivity.ScreenWidth >= 590)
					{
						mActivity.popupWindowAction.showAsDropDown(v,0,v.getTop() +10);
					}
					else
					{
						mActivity.popupWindowAction.showAsDropDown(v,0,v.getTop() +5);
					}
					mActivity.emojitwo.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ArrayList<Integer> emoticIdArray = getEmoticIdArray(modelEmoji);
							new AddEmoticByMapIDTask(context, Integer.parseInt(modelEmoji.getMapID()), StaticVariables.currentChild.getChildID(),emoticIdArray.get(1), 1, new OnEventListener<GetFriendsTempleteMessageListByChildID>() {

								@Override
								public void onSuccess(GetFriendsTempleteMessageListByChildID  model) {
									// TODO Auto-generated method stub
									ArrayList<String> emoticountArray = getEmoticCountArray(model);	
									setEmoticCountList(position, modelEmoji,emoticountArray);

								}

								@Override
								public void onFailure(GetFriendsTempleteMessageListByChildID object) {
									// TODO Auto-generated method stub

								}
							}).execute();
						}
					});

					mActivity.emojione.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							//need to call webservice
							//need to disable click of emoji image ifwebservice is already called

							//GetFriendsTempleteMessageListByChildID modelEmojiEmoji=(GetFriendsTempleteMessageListByChildID) v.getTag();


							ArrayList<Integer> emoticIdArray = getEmoticIdArray(modelEmoji);

							new AddEmoticByMapIDTask(context, Integer.parseInt(modelEmoji.getMapID()), StaticVariables.currentChild.getChildID(),emoticIdArray.get(0), 1, new OnEventListener<GetFriendsTempleteMessageListByChildID>() {

								@Override
								public void onSuccess(GetFriendsTempleteMessageListByChildID model) {
									// TODO Auto-generated method stub

									ArrayList<String> emoticountArray = getEmoticCountArray(model);	
									setEmoticCountList(position, modelEmoji,emoticountArray);

								}

								@Override
								public void onFailure(GetFriendsTempleteMessageListByChildID object) {
									// TODO Auto-generated method stub

								}
							}).execute();


						}
					});

					mActivity.emojithree.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							ArrayList<Integer> emoticIdArray = getEmoticIdArray(modelEmoji);
							new AddEmoticByMapIDTask(context, Integer.parseInt(modelEmoji.getMapID()), StaticVariables.currentChild.getChildID(),emoticIdArray.get(2), 1, new OnEventListener<GetFriendsTempleteMessageListByChildID>() {

								@Override
								public void onSuccess(GetFriendsTempleteMessageListByChildID model) {
									// TODO Auto-generated method stub
									ArrayList<String> emoticountArray = getEmoticCountArray(model);	
									setEmoticCountList(position, modelEmoji,emoticountArray);

								}

								@Override
								public void onFailure(GetFriendsTempleteMessageListByChildID object) {
									// TODO Auto-generated method stub

								}
							}).execute();
						}
					});
					mActivity.emojifour.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							ArrayList<Integer> emoticIdArray = getEmoticIdArray(modelEmoji);
							new AddEmoticByMapIDTask(context, Integer.parseInt(modelEmoji.getMapID()), StaticVariables.currentChild.getChildID(),emoticIdArray.get(3), 1, new OnEventListener<GetFriendsTempleteMessageListByChildID>() {

								@Override
								public void onSuccess(GetFriendsTempleteMessageListByChildID model) {
									// TODO Auto-generated method stub
									ArrayList<String> emoticountArray = getEmoticCountArray(model);	
									setEmoticCountList(position, modelEmoji,emoticountArray);

								}

								@Override
								public void onFailure(GetFriendsTempleteMessageListByChildID object) {
									// TODO Auto-generated method stub

								}
							}).execute();

						}
					});

					mActivity.emojifive.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ArrayList<Integer> emoticIdArray = getEmoticIdArray(modelEmoji);
							int emoticid;
							try {
								emoticid=emoticIdArray.get(4);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								emoticid=8;
							}

							new AddEmoticByMapIDTask(context, Integer.parseInt(modelEmoji.getMapID()), StaticVariables.currentChild.getChildID(),emoticid, 1, new OnEventListener<GetFriendsTempleteMessageListByChildID>() {

								@Override
								public void onSuccess(GetFriendsTempleteMessageListByChildID model) {
									// TODO Auto-generated method stub
									ArrayList<String> emoticountArray = getEmoticCountArray(model);	
									setEmoticCountList(position, modelEmoji,emoticountArray);

								}
								@Override
								public void onFailure(GetFriendsTempleteMessageListByChildID object) {
									// TODO Auto-generated method stub

								}
							}).execute();

						}
					});


				}

				mActivity.popupWindowAction.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub			
						try{
							/*	holder.actionbtn.setEnabled(true);
							mActivity.move.setEnabled(true);
							mActivity.issue.setEnabled(true);*/
							//selectedIndex = -1;
							notifyDataSetChanged();

						}
						catch(Exception e)
						{
							e.printStackTrace();
						}

					}
				});



				/*if(holder.layoutemoji.getVisibility()==View.GONE)
					holder.layoutemoji.setVisibility(View.VISIBLE);
				else
				{
					holder.layoutemoji.setVisibility(View.GONE);
				}*/
			}
		});



		holder.layoutemojione.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetFriendsTempleteMessageListByChildID model=(GetFriendsTempleteMessageListByChildID) v.getTag();
				ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

				Intent intent=new Intent(context,ChildEmoticonDetailActivity.class);
				intent.putExtra("screen",0);
				intent.putExtra("mapid",model.getMapID());
				intent.putExtra("emoticid",emoticIdArray.get(0)+"");
				intent.putExtra("arrayEmotic", emoticIdArray);
				StaticVariables.emooticionNo=1;
				context.startActivity(intent);
				((Activity) context).finish();
			}
		});
		holder.layoutemojitwo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetFriendsTempleteMessageListByChildID model=(GetFriendsTempleteMessageListByChildID) v.getTag();
				ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

				Intent intent=new Intent(context,ChildEmoticonDetailActivity.class);
				intent.putExtra("screen",0);

				intent.putExtra("mapid",model.getMapID());
				intent.putExtra("emoticid",emoticIdArray.get(1)+"");
				intent.putExtra("arrayEmotic", emoticIdArray);

				StaticVariables.emooticionNo=2;

				context.startActivity(intent);
				((Activity) context).finish();

			}
		});
		holder.layoutemojithree.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetFriendsTempleteMessageListByChildID model=(GetFriendsTempleteMessageListByChildID) v.getTag();
				ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

				Intent intent=new Intent(context,ChildEmoticonDetailActivity.class);
				intent.putExtra("screen",0);

				intent.putExtra("mapid",model.getMapID());
				intent.putExtra("emoticid",emoticIdArray.get(2)+"");
				intent.putExtra("arrayEmotic", emoticIdArray);
				StaticVariables.emooticionNo=3;

				context.startActivity(intent);
				((Activity) context).finish();

			}
		});
		holder.layoutemojifour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetFriendsTempleteMessageListByChildID model=(GetFriendsTempleteMessageListByChildID) v.getTag();
				ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

				Intent intent=new Intent(context,ChildEmoticonDetailActivity.class);
				intent.putExtra("screen",0);

				intent.putExtra("mapid",model.getMapID());
				intent.putExtra("emoticid",emoticIdArray.get(3)+"");
				intent.putExtra("arrayEmotic", emoticIdArray);
				StaticVariables.emooticionNo=4;

				context.startActivity(intent);
				((Activity) context).finish();

			}
		});

		holder.layoutemojifive.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetFriendsTempleteMessageListByChildID model=(GetFriendsTempleteMessageListByChildID) v.getTag();
				ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

				Intent intent=new Intent(context,ChildEmoticonDetailActivity.class);
				intent.putExtra("screen",0);

				intent.putExtra("mapid",model.getMapID());
				intent.putExtra("emoticid",emoticIdArray.get(4)+"");
				intent.putExtra("arrayEmotic", emoticIdArray);
				StaticVariables.emooticionNo=5;

				context.startActivity(intent);
				((Activity) context).finish();

			}
		});

		ArrayList<Integer> emoticIdArray = getEmoticIdArray(model);

		holder.emojilayoutimageone.setImageResource(arrayImagesSmileys[emoticIdArray.get(0)]);
		holder.emojilayoutimagetwo.setImageResource(arrayImagesSmileys[emoticIdArray.get(1)]);
		holder.emojilayoutimagethree.setImageResource(arrayImagesSmileys[emoticIdArray.get(2)]);
		holder.emojilayoutimagefour.setImageResource(arrayImagesSmileys[emoticIdArray.get(3)]);
		try {
			holder.emojilayoutimagefive.setImageResource(arrayImagesSmileys[emoticIdArray.get(4)]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			holder.emojilayoutimagefive.setImageResource(arrayImagesSmileys[emoticIdArray.get(0)]);

		}


		//private TextView txtemojione,txtemojitwo,txtemojithree,txtemojifour,txtemojifive;
		ArrayList<String> emoticountArray = getEmoticCountArray(model);

		holder.txtemojione.setText(emoticountArray.get(0));
		holder.txtemojitwo.setText(emoticountArray.get(1));
		holder.txtemojithree.setText(emoticountArray.get(2));
		holder.txtemojifour.setText(emoticountArray.get(3));

		try {
			holder.txtemojifive.setText(emoticountArray.get(4));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			holder.txtemojifive.setText("0");
		}

		if(emoticountArray.get(0).equalsIgnoreCase("0")||emoticountArray.get(0).equalsIgnoreCase(""))
		{
			holder.txtemojione.setVisibility(View.INVISIBLE);
			holder.emojilayoutimageone.setAlpha(.2f);
			holder.layoutemojione.setEnabled(false);
			holder.layoutemojione.setClickable(false);

		}
		else 
		{
			holder.txtemojione.setVisibility(View.VISIBLE);
			holder.emojilayoutimageone.setAlpha(1f);
			holder.layoutemojione.setEnabled(true);
			holder.layoutemojione.setClickable(true);
		}

		if(emoticountArray.get(1).equalsIgnoreCase("0")||emoticountArray.get(1).equalsIgnoreCase(""))
		{
			holder.txtemojitwo.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagetwo.setAlpha(.2f);
			holder.layoutemojitwo.setEnabled(false);
			holder.layoutemojitwo.setClickable(false);
		}
		else 
		{
			holder.txtemojitwo.setVisibility(View.VISIBLE);
			holder.emojilayoutimagetwo.setAlpha(1f);
			holder.layoutemojitwo.setEnabled(true);
			holder.layoutemojitwo.setClickable(true);
		}
		
		if(emoticountArray.get(2).equalsIgnoreCase("0")||emoticountArray.get(2).equalsIgnoreCase(""))
		{
			holder.txtemojithree.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagethree.setAlpha(.2f);
			holder.layoutemojithree.setEnabled(false);
			holder.layoutemojithree.setClickable(false);
		}
		else 
		{
			holder.txtemojithree.setVisibility(View.VISIBLE);
			holder.emojilayoutimagethree.setAlpha(1f);
			holder.layoutemojithree.setEnabled(true);
			holder.layoutemojithree.setClickable(true);
		}
		
		if(emoticountArray.get(3).equalsIgnoreCase("0")||emoticountArray.get(3).equalsIgnoreCase(""))
		{
			holder.txtemojifour.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagefour.setAlpha(.2f);
			holder.layoutemojifour.setEnabled(false);
			holder.layoutemojifour.setClickable(false);
		}
		else 
		{
			holder.txtemojifour.setVisibility(View.VISIBLE);
			holder.emojilayoutimagefour.setAlpha(1f);
			holder.layoutemojifour.setEnabled(true);
			holder.layoutemojifour.setClickable(true);
		}
		
		if(emoticountArray.get(4).equalsIgnoreCase("0")||emoticountArray.get(4).equalsIgnoreCase(""))
		{
			holder.txtemojifive.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagefive.setAlpha(.2f);
			holder.layoutemojifive.setEnabled(false);
			holder.layoutemojifive .setClickable(false);
		}
		else 
		{
			holder.txtemojifive.setVisibility(View.VISIBLE);
			holder.emojilayoutimagefive.setAlpha(1f);
			holder.layoutemojifive.setEnabled(true);
			holder.layoutemojifive .setClickable(true);
		}
		if(model.getActionType().equalsIgnoreCase("1"))
		{
			holder.playwall_image.setVisibility(View.GONE);
			holder.playwall_desc.setVisibility(View.VISIBLE);
			holder.playwall_audio.setVisibility(View.GONE);
			holder.playwall_desc.setText(model.getText());
			holder.playwall_desc.setBackgroundColor(context.getResources().getColor(R.color.notepad_edit));
		}
		else if(model.getActionType().equalsIgnoreCase("2"))
		{
			holder.playwall_image.setVisibility(View.VISIBLE);
			holder.playwall_desc.setVisibility(View.GONE);
			holder.playwall_audio.setVisibility(View.GONE);

			if(model.getText()!=null && model.getText().length()>100)
			{
				byte[] imageByteRefill=Base64.decode(model.getText(), 0);
				if(imageByteRefill!=null)
				{
					holder.playwall_image.setImageBitmap(BitmapFactory.decodeByteArray(imageByteRefill, 0, imageByteRefill.length));	
				}
			}
		}
		else if(model.getActionType().equalsIgnoreCase("3"))
		{
			holder.playwall_image.setVisibility(View.GONE);
			holder.playwall_desc.setVisibility(View.GONE);
			holder.playwall_audio.setVisibility(View.VISIBLE);

		}
		/*listPlaywall.getFriendsTempleteMessageListByChildID().set(position, model);
		notifyDataSetChanged();*/
		return view;
	}

	/**
	 * @param model
	 * @return
	 */
	private ArrayList<String> getEmoticCountArray(
			GetFriendsTempleteMessageListByChildID model) {
		ArrayList<String> emoticountArray=new ArrayList<String>();
		if(model.getEmoticCount().equalsIgnoreCase("")||model.getEmoticCount()==null)
		{
			emoticountArray.add("0");
			emoticountArray.add("0");
			emoticountArray.add("0");
			emoticountArray.add("0");
			emoticountArray.add("0");
		}
		else
		{
			String[] emoticount=model.getEmoticCount().split(",");
			for(int i=0;i<emoticount.length;i++)
			{
				if(emoticount[i].equalsIgnoreCase(""))
				{
					emoticountArray.add("0");
				}
				else
				{
					emoticountArray.add(emoticount[i]);
				}
			}
		}
		return emoticountArray;
	}

	/**
	 * @param model
	 */
	private void setProfileImage(GetFriendsTempleteMessageListByChildID model) {
		if(model.getProfileImage()!=null && model.getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(model.getProfileImage(), 0);
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
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.child_image), dp2px(80), dp2px(80), false);
			holder.child_buddies_image.setImageBitmap(bitmap);
		}

		if(model.getProfileImage()!=null && model.getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(model.getProfileImage(), 0);
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
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.child_image), dp2px(80), dp2px(80), false);
			holder.child_buddies.setImageBitmap(bitmap);
		}
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();


		holder.child_buddies_image = (HexagonImageView) view.findViewById(R.id.child_buddies_image);
		holder.child_buddies = (HexagonImageView)view.findViewById(R.id.child_buddies);
		holder.layout_playwall = (LinearLayout)view.findViewById(R.id.layout_playwall);
		holder.layoutemoji = (LinearLayout)view.findViewById(R.id.layoutemoji);
		holder.layoutemojione = (LinearLayout)view.findViewById(R.id.layoutemojione);
		holder.layoutemojitwo = (LinearLayout)view.findViewById(R.id.layoutemojitwo);
		holder.layoutemojithree = (LinearLayout)view.findViewById(R.id.layoutemojithree);
		holder.layoutemojifour = (LinearLayout)view.findViewById(R.id.layoutemojifour);
		holder.layoutemojifive = (LinearLayout)view.findViewById(R.id.layoutemojifive);
		//holder.layoutemojifive = (LinearLayout)view.findViewById(R.id.layoutemojifive);

		holder.playwall_heading = (TextView) view.findViewById(R.id.playwall_heading);
		holder.buddies_name = (TextView) view.findViewById(R.id.buddies_name);
		holder.buddies_time= (TextView) view.findViewById(R.id.buddies_time);
		holder.playwall_desc = (TextView) view.findViewById(R.id.playwall_desc);
		holder.songDuration = (TextView) view.findViewById(R.id.songDuration);

		holder.txtemojione = (TextView) view.findViewById(R.id.txtemojione);
		holder.txtemojitwo = (TextView) view.findViewById(R.id.txtemojitwo);
		holder.txtemojithree = (TextView) view.findViewById(R.id.txtemojithree);
		holder.txtemojifour = (TextView) view.findViewById(R.id.txtemojifour);
		holder.txtemojifive = (TextView) view.findViewById(R.id.txtemojifive);


		typeFace.setTypefaceGotham(holder.buddies_name);
		typeFace.setTypefaceGotham(holder.playwall_heading);
		typeFace.setTypefaceGotham(holder.playwall_desc);
		typeFace.setTypefaceGotham(holder.songDuration);
		typeFace.setTypefaceGotham(holder.txtemojione);
		typeFace.setTypefaceGotham(holder.txtemojithree);
		typeFace.setTypefaceGotham(holder.txtemojitwo);
		typeFace.setTypefaceGotham(holder.txtemojifour);
		typeFace.setTypefaceGotham(holder.txtemojifive);
		typeFace.setTypefaceGotham(holder.buddies_time);
		holder.buddies_time.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.buddies_time.setAlpha(.6f);
		holder.buddies_time.setVisibility(View.VISIBLE);


		holder.playwall_image=(ImageView) view.findViewById(R.id.playwall_image);	
		holder.playwall_smileyimage=(ImageView) view.findViewById(R.id.playwall_smileyimage);
		holder.playwall_smileyimage.setImageResource(R.drawable.launch_menu_smile_i);

		holder.emojilayoutimageone=(ImageView) view.findViewById(R.id.emojilayoutimageone);	
		holder.emojilayoutimagetwo=(ImageView) view.findViewById(R.id.emojilayoutimagetwo);
		holder.emojilayoutimagethree=(ImageView) view.findViewById(R.id.emojilayoutimagethree);	
		holder.emojilayoutimagefour=(ImageView) view.findViewById(R.id.emojilayoutimagefour);
		holder.emojilayoutimagefive=(ImageView) view.findViewById(R.id.emojilayoutimagefive);	

		holder.playwall_audio=view.findViewById(R.id.playwall_audio);
		holder.playwall_smiley_layout=view.findViewById(R.id.playwall_smiley_layout);
		holder.seekBar=(SeekBar) view.findViewById(R.id.seekBar);
		//holder.seekBar.setEnabled(false);
		holder.seekBar.setClickable(false);
		holder.seekBar.setEnabled(false);
		holder.seekBar.setFocusableInTouchMode(false);
		holder.seekBar.setFocusable(false);


		holder.playwall_audio.setClickable(false);
		holder.playwall_audio.setEnabled(false);
		holder.playwall_audio.setFocusableInTouchMode(false);
		holder.playwall_audio.setFocusable(false);		


		holder.buddies_name.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.playwall_heading.setTextColor(context.getResources().getColor(R.color.black_color));
		holder.playwall_desc.setTextColor(context.getResources().getColor(R.color.font_blackcoloralphanew));
		holder.txtemojione.setAlpha(.7f);
		holder.txtemojitwo.setAlpha(.7f);
		holder.txtemojithree.setAlpha(.7f);
		holder.txtemojifour.setAlpha(.7f);
		holder.txtemojifive.setAlpha(.7f);

		holder.mp3Image=(ImageView) view.findViewById(R.id.mp3Image);	
		/*		holder.container=(LinearLayout) view.findViewById(R.id.container);	
		 */		holder.mp3Image.setClickable(false);
		 holder.mp3Image.setEnabled(false);
		 holder.mp3Image.setFocusableInTouchMode(false);
		 holder.mp3Image.setFocusable(false);
		 /*	holder.container.setClickable(false);
		holder.container.setEnabled(false);
		holder.container.setFocusableInTouchMode(false);
		holder.container.setFocusable(false);*/

		 return holder;
	}

	private  class ViewHolder 
	{
		private LinearLayout layout_playwall,layoutemoji,layoutemojione,layoutemojitwo,layoutemojithree,layoutemojifour,layoutemojifive;
		private HexagonImageView child_buddies_image;
		private HexagonImageView child_buddies;
		private TextView buddies_name,playwall_heading,playwall_desc,songDuration,txtemojione,txtemojitwo,txtemojithree,txtemojifour,txtemojifive,buddies_time;
		private ImageView playwall_image,playwall_smileyimage;
		private ImageView  emojilayoutimageone,emojilayoutimagetwo,emojilayoutimagethree,emojilayoutimagefour,emojilayoutimagefive;

		private View playwall_audio,playwall_smiley_layout;
		private SeekBar seekBar;
		private ImageView mp3Image;
		/*		private LinearLayout container;
		 */
	}


	private int dp2px(int dp) 
	{

		if(SplashActivity.ScreenWidth >= 2000)
		{
			dp = 60;

		}

		else if(SplashActivity.ScreenWidth >= 1000)
		{
			dp = 55;

		}
		else if(SplashActivity.ScreenWidth >= 700)
		{
			dp = 50;

		}
		else if(SplashActivity.ScreenWidth >= 590)
		{
			dp = 80;
		}
		else
		{
			dp = 50;
		}
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * @param model
	 * @return
	 */
	private ArrayList<Integer> getEmoticIdArray(
			GetFriendsTempleteMessageListByChildID model) {
		ArrayList<Integer> emoticIdArray=new ArrayList<Integer>();
		if(model.getEmoticID().equalsIgnoreCase("")||model.getEmoticID()==null)
		{
			emoticIdArray.add(0);
			emoticIdArray.add(1);
			emoticIdArray.add(2);
			emoticIdArray.add(3);
			emoticIdArray.add(4);
		}
		else
		{
			String[] emoticId=model.getEmoticID().split(",");
			for(int i=0;i<emoticId.length;i++)
			{
				if(emoticId[i].equalsIgnoreCase(""))
				{
					emoticIdArray.add(0);
				}
				else
				{
					emoticIdArray.add(Integer.parseInt(emoticId[i]));
				}
			}
		}
		return emoticIdArray;
	}

	/**
	 * @param position
	 * @param modelEmoji
	 * @param emoticountArray
	 */
	private void setEmoticCountList(final int position,
			final GetFriendsTempleteMessageListByChildID modelEmoji,
			ArrayList<String> emoticountArray) {
		holder.txtemojione.setText(emoticountArray.get(0));
		holder.txtemojitwo.setText(emoticountArray.get(1));
		holder.txtemojithree.setText(emoticountArray.get(2));
		holder.txtemojifour.setText(emoticountArray.get(3));
		holder.txtemojifive.setText(emoticountArray.get(4));
		/*if(emoticountArray.get(0).equalsIgnoreCase("0")||emoticountArray.get(0).equalsIgnoreCase(""))
		{
			holder.txtemojione.setVisibility(View.INVISIBLE);
			holder.emojilayoutimageone.setAlpha(.5f);
		}
		else 
		{
			holder.txtemojione.setVisibility(View.VISIBLE);
			holder.emojilayoutimageone.setAlpha(1f);
		}

		if(emoticountArray.get(1).equalsIgnoreCase("0")||emoticountArray.get(1).equalsIgnoreCase(""))
		{
			holder.txtemojitwo.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagetwo.setAlpha(.5f);
		}
		else 
		{
			holder.txtemojitwo.setVisibility(View.VISIBLE);
			holder.emojilayoutimagetwo.setAlpha(1f);
		}
		
		if(emoticountArray.get(2).equalsIgnoreCase("0")||emoticountArray.get(2).equalsIgnoreCase(""))
		{
			holder.txtemojithree.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagethree.setAlpha(.5f);
		}
		else 
		{
			holder.txtemojithree.setVisibility(View.VISIBLE);
			holder.emojilayoutimagethree.setAlpha(1f);
		}
		
		if(emoticountArray.get(3).equalsIgnoreCase("0")||emoticountArray.get(3).equalsIgnoreCase(""))
		{
			holder.txtemojifour.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagefour.setAlpha(.5f);
		}
		else 
		{
			holder.txtemojifour.setVisibility(View.VISIBLE);
			holder.emojilayoutimagefour.setAlpha(1f);
		}
		
		if(emoticountArray.get(4).equalsIgnoreCase("0")||emoticountArray.get(4).equalsIgnoreCase(""))
		{
			holder.txtemojifive.setVisibility(View.INVISIBLE);
			holder.emojilayoutimagefive.setAlpha(.5f);
		}
		else 
		{
			holder.txtemojifive.setVisibility(View.VISIBLE);
			holder.emojilayoutimagefive.setAlpha(1f);
		}*/
		modelEmoji.setEmoticCount(emoticountArray.get(0)+","+emoticountArray.get(1)+","+emoticountArray.get(2)+","+emoticountArray.get(3)+","+emoticountArray.get(4));
		listPlaywall.getFriendsTempleteMessageListByChildID().set(position, modelEmoji);
		mActivity.popupWindowAction.dismiss();
	}


}
