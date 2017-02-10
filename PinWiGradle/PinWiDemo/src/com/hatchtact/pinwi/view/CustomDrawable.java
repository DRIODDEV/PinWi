package com.hatchtact.pinwi.view;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class CustomDrawable extends GradientDrawable 
{
	String hexColor = String.format("#%06X", (0xFFFFFF & Color.rgb(49, 157, 197)));
		
	public CustomDrawable(int[] colors) 
	{
		super(GradientDrawable.Orientation.TOP_BOTTOM, colors);

		try {
			this.setShape(GradientDrawable.OVAL);
			this.setGradientType(GradientDrawable.LINEAR_GRADIENT);
			this.setStroke(2, Color.parseColor(hexColor));

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
