package com.hatchtact.pinwi.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class AppUtils
{

	private Context mContext;
	public static final String PATHACCESSPROFILEIMAGES="/PinWi/AccessProfile/";
	public AppUtils(Context context)
	{
		super();
		// TODO Auto-generated constructor stub	
		mContext=context;
	}
	private int getAge(int year, int month, int day)
	{
		Calendar dob = Calendar.getInstance();
		Calendar today = Calendar.getInstance();

		dob.set(year, month, day); 

		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
			age--; 
		}

		Integer ageInt = new Integer(age);
		String ageS = ageInt.toString();

		return ageInt;  
	}

	public static String getCurrentDay()
	{	
		Calendar day=Calendar.getInstance();
		int day1 =day.get(Calendar.DAY_OF_WEEK);
		String dayValue=String.valueOf(day1);

		return dayValue;	
	}

	public String bitmapStoreInSDCard(Bitmap bmp,
			String nameOfImageAlongWithPath) throws Exception {
		System.out.println("path" +nameOfImageAlongWithPath);

		File sd = new File(Environment.getExternalStorageDirectory()+nameOfImageAlongWithPath);

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

	/**
	 * Gets the imagefrom sd card.
	 *
	 * @param name
	 *            the name
	 * @return the imagefrom sd card
	 * @throws Exception
	 *             the exception
	 */
	public Bitmap getImagefromSDCard(String name) throws Exception {

		Bitmap bmp = null;
		File imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +name);
		//File imageFile = new File(name);
		bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if(bmp!=null)
		{
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		}

		return bmp;
	}
	
	public  boolean deleteDirectory(File path) {
		   if( path.exists() ) {
		     File[] files = path.listFiles();
		     if (files == null) {
		         return true;
		     }
		     for(int i=0; i<files.length; i++) {
		        if(files[i].isDirectory()) {
		          deleteDirectory(files[i]);
		        }
		        else {
		          files[i].delete();
		        }
		     }
		   }
		   return( path.delete() );
		 }

}
