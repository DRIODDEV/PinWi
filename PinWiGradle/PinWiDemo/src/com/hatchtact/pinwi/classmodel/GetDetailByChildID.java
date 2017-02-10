package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetDetailByChildID implements Serializable
{
    private String Text;

    private String Time;

    private String ChildID;

    private String TemplateID;

    private String ProfileImage;

    private String TempleteText;

    private String ChildName;
    private String ActionType;

    private String EmoticID;
	private String EmoticCount;
    public String getText ()
    {
        return Text;
    }

    public void setText (String Text)
    {
        this.Text = Text;
    }

    public String getTime ()
    {
        return Time;
    }

    public void setTime (String Time)
    {
        this.Time = Time;
    }

    public String getChildID ()
    {
        return ChildID;
    }

    public void setChildID (String ChildID)
    {
        this.ChildID = ChildID;
    }

    public String getTemplateID ()
    {
        return TemplateID;
    }

    public void setTemplateID (String TemplateID)
    {
        this.TemplateID = TemplateID;
    }

    public String getProfileImage ()
    {
        return ProfileImage;
    }

    public void setProfileImage (String ProfileImage)
    {
        this.ProfileImage = ProfileImage;
    }

    public String getTempleteText ()
    {
        return TempleteText;
    }

    public void setTempleteText (String TempleteText)
    {
        this.TempleteText = TempleteText;
    }

    public String getChildName ()
    {
        return ChildName;
    }

    public void setChildName (String ChildName)
    {
        this.ChildName = ChildName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Text = "+Text+", Time = "+Time+", ChildID = "+ChildID+", TemplateID = "+TemplateID+", ProfileImage = "+ProfileImage+", TempleteText = "+TempleteText+", ChildName = "+ChildName+"]";
    }

	public String getActionType() {
		return ActionType;
	}

	public void setActionType(String actionType) {
		ActionType = actionType;
	}

	public String getEmoticID() {
		return EmoticID;
	}

	public void setEmoticID(String emoticID) {
		EmoticID = emoticID;
	}

	public String getEmoticCount() {
		return EmoticCount;
	}

	public void setEmoticCount(String emoticCount) {
		EmoticCount = emoticCount;
	}

		
}
			
