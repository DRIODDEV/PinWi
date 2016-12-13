package com.hatchtact.pinwi.utility;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hatchtact.pinwi.classmodel.Error;

public class GetValidJson
{
	private Gson gson;
	
	public GetValidJson()
	{
		super();
		// TODO Auto-generated constructor stub	
		gson = new Gson();
	}

	public String getValidJsonArray(String stringRaw)
	{
		String validJsonArrayString = null;
		
		String[] splitBySquareBracket = stringRaw.split("]");
		
		if(splitBySquareBracket!=null && splitBySquareBracket.length==2)
		{
			String errorMessage = splitBySquareBracket[0].replace("[", "").trim();
			
			Error error = gson.fromJson(errorMessage, Error.class);
			
			if(error!=null && error.getErrorCode().trim().equals("0"))
			{
				validJsonArrayString = splitBySquareBracket[1]+"]";
			}
			else
			{
				return null;
			}
		}
		else
		{
			stringRaw = stringRaw.replace("[", "").replace("]", "");
			
			Error error = null;
			try {
				error = gson.fromJson(stringRaw, Error.class);
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(error!=null)
			{
				validJsonArrayString = error.getErrorCode();
			}
			else
			{
				return null;
			}
		}
		
		return validJsonArrayString;
	}
	
	public String getValidJsonObject(String stringRaw)
	{
		String validJsonArrayString = null;
		
		String[] splitBySquareBracket = stringRaw.split("]");
		
		if(splitBySquareBracket!=null && splitBySquareBracket.length==2)
		{
			String errorMessage = splitBySquareBracket[0].replace("[", "").trim();
			
			Error error = gson.fromJson(errorMessage, Error.class);
			
			if(error!=null && error.getErrorCode().trim().equals("0"))
			{
				validJsonArrayString = splitBySquareBracket[1].replace("[", "");
			}
			else
			{
				return null;
			}
		}
		else
		{
			stringRaw = stringRaw.replace("[", "").replace("]", "");
			Error error = null;
			try {
				error = gson.fromJson(stringRaw, Error.class);
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if(error!=null)
			{
				validJsonArrayString = error.getErrorCode();
			}
			else
			{
				return null;
			}
		}
		
		return validJsonArrayString;
	}
	
	public Error getError(String stringRaw) 
	{
		try {
			String errorMessage = stringRaw.replace("[", "").replace("]", "");
			Error error = gson.fromJson(errorMessage, Error.class);
			
			return error;
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
