/*
 * Added by Amit Sharma.
 */
package com.hatchtact.pinwi.fragment.network;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.StaticVariables;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


/**
 * The Class ZoomScreenActivity for opening up the thumbnail image on click of
 * the Image in Request Delievery Activity.
 */
public class ZoomScreenActivity extends Activity
{
	public static ZoomScreenActivity instance;
	private Bitmap bitmap = null;
	byte[] imageByteParent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		//if (getIntent().hasExtra("byteArray"))
		{
			try {
				ImageView imageView = new ImageView(ZoomScreenActivity.this);
				try {
					imageByteParent=Base64.decode(StaticVariables.byteArray,0);
				}
				catch (Exception e)
				{

				}
				if(StaticVariables.byteArray.trim().length()>10 && StaticVariables.byteArray.length()<100)
				{
					Bitmap imageProfile=null;
					try {
						imageProfile = new AppUtils(this).getImagefromSDCard(StaticVariables.byteArray);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if(imageProfile!=null)
					{
						imageView.setImageBitmap(imageProfile);
					}
					imageView.setBackgroundColor(Color.BLACK);
					imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					setContentView(imageView);
					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							finish();
							onBackPressed();
						}
					});
				}
				else if(imageByteParent!=null)
				{

					if((StaticVariables.byteArray.length()>100))
					{
						imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length));
					}
					else
					{
						imageView.setImageResource(R.drawable.parent_image);
					}
					imageView.setBackgroundColor(Color.BLACK);
					imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					setContentView(imageView);
					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							finish();
							onBackPressed();
						}
					});

				}
				else
				{

/*
					ImageView imageView = new ImageView(ZoomScreenActivity.this);
*/
					imageView.setImageResource(R.drawable.parent_image);
					imageView.setBackgroundColor(Color.BLACK);
					imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					setContentView(imageView);
					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							finish();
							onBackPressed();
						}
					});


				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();



				ImageView imageView = new ImageView(ZoomScreenActivity.this);
				imageView.setImageResource(R.drawable.parent_image);
				imageView.setBackgroundColor(Color.BLACK);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				setContentView(imageView);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
						onBackPressed();
					}
				});



			}

		}
	}

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	/** The coming from finish. */
	private boolean comingFromFinish = false;

	@Override
	public void finish() {
		comingFromFinish = true;
		super.finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dispose();
	}
	private void dispose()
	{
		instance=null;
		if(bitmap!=null)
		{
			bitmap.recycle();
			bitmap=null;
		}
		System.gc();
	}
}
