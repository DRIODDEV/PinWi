package com.hatchtact.pinwi.utility;

import java.util.Calendar;

public class AppUtils
{
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
 
}
