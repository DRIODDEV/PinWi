package com.hatchtact.pinwi.classmodel;

public class GetListofChildrensByChildActID
{
    private String ActivityID;

    private String ProfileImage;

    private String FriendsDoingThis;

    private String RowNumbers;

    private String ChildName;

    public String getActivityID ()
    {
        return ActivityID;
    }

    public void setActivityID (String ActivityID)
    {
        this.ActivityID = ActivityID;
    }

    public String getProfileImage ()
    {
        return ProfileImage;
    }

    public void setProfileImage (String ProfileImage)
    {
        this.ProfileImage = ProfileImage;
    }

    public String getFriendsDoingThis ()
    {
        return FriendsDoingThis;
    }

    public void setFriendsDoingThis (String FriendsDoingThis)
    {
        this.FriendsDoingThis = FriendsDoingThis;
    }

    public String getRowNumbers ()
    {
        return RowNumbers;
    }

    public void setRowNumbers (String RowNumbers)
    {
        this.RowNumbers = RowNumbers;
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
        return "ClassPojo [ActivityID = "+ActivityID+", ProfileImage = "+ProfileImage+", FriendsDoingThis = "+FriendsDoingThis+", RowNumbers = "+RowNumbers+", ChildName = "+ChildName+"]";
    }
}
		
