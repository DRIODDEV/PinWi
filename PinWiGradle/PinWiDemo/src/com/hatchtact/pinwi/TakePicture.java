package com.hatchtact.pinwi;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("NewApi")
public class TakePicture extends Activity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback {

	private Camera mCamera;
	private SurfaceView mPreview;
	private  Bitmap  imageTaken=null;

	static boolean booleanTakePicture=false;
	private SharePreferenceClass sharePref;
	private  Bitmap  rotate_bitmap=null;
	private TypeFace typeFace;

	private Button btncancel;
	private Button btntake;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.takepicture);

		typeFace= new TypeFace(TakePicture.this); 

		booleanTakePicture=false;
		
		btncancel = (Button) findViewById(R.id.btncancel);
		btntake = (Button) findViewById(R.id.btntake);
		typeFace.setTypefaceRegular(btncancel);
		typeFace.setTypefaceRegular(btntake);

		mPreview = (SurfaceView)findViewById(R.id.preview);
		mPreview.getHolder().addCallback(this);
		mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mCamera = Camera.open();
		sharePref=new SharePreferenceClass(TakePicture.this);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		mCamera.stopPreview();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mCamera.release();
		if(mPreview!=null)
		{
			mPreview=null;
			System.gc();
		}
		if(sharePref!=null)
		{
			sharePref=null;
			System.gc();
		}
		if(imageTaken!=null)
		{
			imageTaken.recycle();
			imageTaken=null;
		}
		Log.d("CAMERA","Destroy");
	}

	public void onSnapClick(View v) {
		if(!booleanTakePicture)
		{
			booleanTakePicture=true;
			mCamera.takePicture(this, null, null, this);
		}
	}
	
	public void onCancelClick(View v)
	{
		finish();
	}

	@Override
	public void onShutter() {
		// Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
	}


	private String bitmapStoreInSDCard(Bitmap bmp,
			String nameOfImageAlongWithPath) throws Exception {
		System.out.println("path" +nameOfImageAlongWithPath);
		File sd = new File(nameOfImageAlongWithPath);

		if (!sd.exists()) {
			sd.getParentFile().mkdirs();

			if (sd.exists()) {
				sd.delete();
			}

		}

		if (sd.exists()) {
			sd.delete();
		}

		FileOutputStream fos = new FileOutputStream(sd);

		bmp.compress(Bitmap.CompressFormat.JPEG, 70 /* ignored for JPEG */,
				fos);

		fos.close();
		fos.flush();
		if(bmp!=null)
		{
			bmp.recycle();
			bmp=null;
		}

		return nameOfImageAlongWithPath;

	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		//Here, we chose internal storage

		if(imageTaken!=null)
		{
			imageTaken.recycle();
			imageTaken=null;
			System.gc();

		}

		if(rotate_bitmap!=null)
		{
			rotate_bitmap.recycle();
			rotate_bitmap=null;
			System.gc();
		}

		try
		{
			rotate_bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			imageTaken =decodeSampledBitmapFromResource(data,rotate_bitmap.getWidth(),rotate_bitmap.getHeight());
			if(rotate_bitmap!=null)
			{
				rotate_bitmap.recycle();
				rotate_bitmap=null;
				System.gc();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//imageTaken = getScaledBitMapForImageFit(imageTaken);

		Matrix matrix = new Matrix();
		matrix.postRotate(90);

		rotate_bitmap = Bitmap.createBitmap(imageTaken , 0, 0,imageTaken.getWidth(),imageTaken.getHeight(), matrix, true);

		if(imageTaken!=null)
		{
			imageTaken.recycle();
			imageTaken=null;
			System.gc();
		}
		imageTaken = Bitmap.createBitmap(rotate_bitmap, 0, rotate_bitmap.getHeight()/2 - rotate_bitmap.getWidth()/2, rotate_bitmap.getWidth(), rotate_bitmap.getWidth());

		if(rotate_bitmap!=null)
		{
			rotate_bitmap.recycle();
			rotate_bitmap=null;
			System.gc();
		}

		String path=null;
		try {
			path=bitmapStoreInSDCard(imageTaken, Environment.getExternalStorageDirectory()+ "/profilo_"+".jpeg");

			if(imageTaken!=null)
			{
				imageTaken.recycle();
				imageTaken=null;
				System.gc();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Intent returnIntent = new Intent();

		returnIntent.putExtra("IMAGE_TAKEN", true);

		returnIntent.putExtra("PATH", path);
		System.out.println("path" +path);

		setResult(RESULT_OK, returnIntent);


		finish();
	}

	/*	private Uri getImageUri() {
	    // Store image in dcim
	    File file = new File(Environment.getExternalStorageDirectory() + "/DCIM", CAPTURE_TITLE);
	    Uri imgUri = Uri.fromFile(file);

	    return imgUri;
	}
	 */
	public  Bitmap decodeSampledBitmapFromResource(byte[] data,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16*1024];

		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}


	public static Bitmap decodeSampledBitmapFromFile(String filePath,
			int reqWidth, int reqHeight) {

		Bitmap scaledBitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp =BitmapFactory.decodeFile(filePath, options);

		File file = new File(filePath);
		double length = file.length();
		double kb=length/1024;
		double mb=kb/1024;

		System.out.println("scaled Bitmap mb in 1 " +mb);


		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;
		float maxHeight = 816.0f;
		float maxWidth = 612.0f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;     

			}
		}

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16*1024];

		try{	
			bmp = BitmapFactory.decodeFile(filePath,options);
		}
		catch(OutOfMemoryError exception){
			exception.printStackTrace();

		}
		try{
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
		}
		catch(OutOfMemoryError exception){
			exception.printStackTrace();
		}



		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
			Log.d("EXIF", "Exif: " + orientation);
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 3) {
				matrix.postRotate(180);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 8) {
				matrix.postRotate(270);
				Log.d("EXIF", "Exif: " + orientation);
			}
			scaledBitmap = Bitmap.createBitmap(bmp, 0, 0,bmp.getWidth(), bmp.getHeight(), matrix, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream   out = null;

		out = new ByteArrayOutputStream ();
		scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

		byte[] bytearray=out.toByteArray();
		double kb1=bytearray.length/1024;
		double mb1=kb1/1024;

		System.out.println("scaled Bitmap mb  in 2" +mb1);
		return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length, options);
	}
	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	@SuppressWarnings("null")
	@SuppressLint("NewApi")
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		/*Camera.Parameters params = mCamera.getParameters();
		List<Camera.Size> sizes = params.getSupportedPreviewSizes();
		Camera.Size selected = sizes.get(sizes.size()-1);
		params.setPreviewSize(selected.width,selected.height);
		mCamera.setParameters(params);
		mCamera.setDisplayOrientation(90);

		mCamera.startPreview();*/

		Camera.Parameters params = mCamera.getParameters();
		List<Size> sizes =params.getSupportedPictureSizes();


		Size mSize = null;
		for (Size size : sizes) {

			if (size.width >= 1280 && size.height >= 720) {
				mSize = size;
				break;
			}
			else
			{
				size.width=1280;
				size.height=720;
				
				mSize = size;
				
				break;
			}

		}

		params.setPictureSize(mSize.width, mSize.height);
		mCamera.setParameters(params);
		mCamera.setDisplayOrientation(90);
		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(mPreview.getHolder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("PREVIEW","surfaceDestroyed");
	}
}