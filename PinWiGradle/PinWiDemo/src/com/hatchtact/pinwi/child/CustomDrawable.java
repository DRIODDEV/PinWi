package com.hatchtact.pinwi.child;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class CustomDrawable extends GradientDrawable 
{
	 public CustomDrawable(int[] colors, int cornerRadius, boolean isLeft) 
	    {
	        super(GradientDrawable.Orientation.TOP_BOTTOM, colors);

	        try {
	            this.setShape(GradientDrawable.RECTANGLE);
	            this.setGradientType(GradientDrawable.LINEAR_GRADIENT);
	            //top-left     top-right    bottom-right   bottom-left
	            if(isLeft)
	            {
	            	float[] m_arrfTopHalfOuterRadii = new float[] {cornerRadius, cornerRadius, 0, 0, 0, 0,cornerRadius, cornerRadius};
	            	this.setCornerRadii(m_arrfTopHalfOuterRadii);
	            }
	            else
	            {
	            	float[] m_arrfTopHalfOuterRadii = new float[] { 0, 0, cornerRadius, cornerRadius,cornerRadius, cornerRadius,0, 0};
	            	this.setCornerRadii(m_arrfTopHalfOuterRadii);
	            }
	            
	        } catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
	 
	 public CustomDrawable(int[] colors,String strokeColor) 
	    {
	        super(GradientDrawable.Orientation.TOP_BOTTOM, colors);

	        try {
	            this.setShape(GradientDrawable.RECTANGLE);
	            this.setGradientType(GradientDrawable.LINEAR_GRADIENT);
	            this.setStroke(1, Color.parseColor(strokeColor));
	            
	        } catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
 
	 public CustomDrawable(int[] colors, int cornerRadius, String strokeColor, int strokeWidth, boolean isTopOnly) 
	    {
	        super(GradientDrawable.Orientation.TOP_BOTTOM, colors);

	        try {
	            this.setShape(GradientDrawable.RECTANGLE);
	            this.setGradientType(GradientDrawable.LINEAR_GRADIENT);
	            this.setStroke(strokeWidth, Color.parseColor(strokeColor));
	            if(isTopOnly){
	            	float[] m_arrfTopHalfOuterRadii = new float[] {cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0, 0, 0};
	            	this.setCornerRadii(m_arrfTopHalfOuterRadii);
	            }else{
		            this.setCornerRadius(cornerRadius);
	            }
	        } catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
}