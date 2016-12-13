package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetDetailByMapEmoticID implements Serializable
{
    private String Text;

    private String ChildID;

    private String ActionType;

    private String MapID;

    private String TemplateID;

    private String ProfileImage;

    private String TempleteText;

    private String EmoticID;

    private String ChildName;

    private String EmoticCount;
    public String getText ()
    {
        return Text;
    }

    public void setText (String Text)
    {
        this.Text = Text;
    }

    public String getChildID ()
    {
        return ChildID;
    }

    public void setChildID (String ChildID)
    {
        this.ChildID = ChildID;
    }

    public String getActionType ()
    {
        return ActionType;
    }

    public void setActionType (String ActionType)
    {
        this.ActionType = ActionType;
    }

    public String getMapID ()
    {
        return MapID;
    }

    public void setMapID (String MapID)
    {
        this.MapID = MapID;
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

    public String getEmoticID ()
    {
        return EmoticID;
    }

    public void setEmoticID (String EmoticID)
    {
        this.EmoticID = EmoticID;
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
        return "ClassPojo [Text = "+Text+", ChildID = "+ChildID+", ActionType = "+ActionType+", MapID = "+MapID+", TemplateID = "+TemplateID+", ProfileImage = "+ProfileImage+", TempleteText = "+TempleteText+", EmoticID = "+EmoticID+", ChildName = "+ChildName+"]";
    }

	public String getEmoticCount() {
		return EmoticCount;
	}

	public void setEmoticCount(String emoticCount) {
		EmoticCount = emoticCount;
	}
}
			
