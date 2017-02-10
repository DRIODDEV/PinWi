package com.hatchtact.pinwi.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Validation
{
	public  boolean isNotNullOrBlank(String s) 
	{
		return !(s == null || s.trim().equals(""));
	}

	public int noOfCharacter(String s)
	{
		if(s!=null)
			return s.length();
		else
			return 0;
	}

	// validate first name
	public  boolean validateFirstName( String firstName )
	{
		return firstName.matches( "[A-Z][a-zA-Z]*" );
	} // end method validateFirstName

	// validate last name
	public  boolean validateLastName( String lastName )
	{
		return lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
	} // end method validateLastName

	public boolean isValidEmail(String email)
	{
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"; 
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern); 
		java.util.regex.Matcher m = p.matcher(email); 
		return m.matches();
	}

	public  boolean isValidPhoneNo(CharSequence target) 
	{
		boolean isValid = false;

		/*String expression = "^(0|\\+91|\\+91-)?[789]\\d{9}$";
		CharSequence inputStr = target;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches())
		{
			isValid = true;
		}*/

		if(target.toString().trim().length()==10)
		{
			isValid = true;
		}
		return isValid;
	} 

	public boolean isAgeValid(int year, int month, int day,int validAge)
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

		if(ageInt>=validAge)
			return true;
		else
			return false;
	}

	public ArrayList<Integer> getWeekDays(int startYear, int startMonth, int startDay,int endYear, int endMonth, int endDay)
	{
		ArrayList<Integer> listDays = new ArrayList<Integer>();

		Calendar sartDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();

		sartDate.set(startYear, startMonth, startDay); 

		endDate.set(endYear, endMonth, endDay);

		int days = daysBetween(sartDate.getTime(), endDate.getTime());

		if(days==0)
		{
			int dayOfWeek = sartDate.get(Calendar.DAY_OF_WEEK);
			listDays.add(dayOfWeek);
		}
		else if(days>6)
		{
			for(int i=0;i<7;i++)
				listDays.add(i+1);
		}
		else
		{
			int dayOfWeek = sartDate.get(Calendar.DAY_OF_WEEK);

			for(int i=0;i<=days;i++)
			{
				listDays.add(dayOfWeek);

				if(dayOfWeek==7)
				{
					dayOfWeek = 1;
				}
				else
				{
					dayOfWeek++;
				}		
			}
		}	
		return listDays;	
	}

	public int daysBetween(Date d1, Date d2)
	{
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
}
