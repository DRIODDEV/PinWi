package com.hatchtact.pinwi.child.postcard;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

public class CompressImage {


	private Context mContext;

	public CompressImage(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public  Bitmap decodeSampledBitmapFromResource( int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(mContext.getResources(), resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(mContext.getResources(), resId, options);
	}

	public Bitmap compressImage(String filePath) {

		Bitmap scaledBitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();

		//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
		//      you try the use the bitmap here, you will get null.
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;

		//      max Height and width values of the compressed image is taken as 816x612

		float maxHeight = 800f;
		float maxWidth = 420f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		//      width and height values are set maintaining the aspect ratio of the image

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

		//      setting inSampleSize value allows to load a scaled down version of the original image
		options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

		//      inJustDecodeBounds set to false to load the actual bitmap
		options.inJustDecodeBounds = false;

		//      this options allow android to claim the bitmap memory if it runs low on memory
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

		try {
			//          load the bitmap from its path
			bmp = BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();

		}
		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

		//      check the rotation of the image and display it properly
		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 0);
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
			scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
					scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
					true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//SharedPreferenceUtil.getInstance(mContext).saveData(SharedPrefKeys.WALLPAPER_BYTE, encodedByteWallpaper);
		return scaledBitmap;
	}


	private String bitmapTobyte(byte[] bitmapLength)
	{
		return Base64.encodeToString(bitmapLength,
				Base64.DEFAULT);
	}

	public String getFilename() {
		File file = new File(Environment.getExternalStorageDirectory().getPath());
		if (!file.exists()) {
			file.mkdirs();
		}
		String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
		return uriSting;

	}

	private String getRealPathFromURI(String contentURI) {
		Uri contentUri = Uri.parse(contentURI);
		Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
		if (cursor == null) {
			return contentUri.getPath();
		} else {
			cursor.moveToFirst();
			int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(index);
		}
	}

	public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;
		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}

		return inSampleSize;
	}

}
