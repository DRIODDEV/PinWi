package com.hatchtact.pinwi.classmodel;

public class SearchActivityByName
{
    private String IsWished;

    private String ActivityID;

    private String RowNumber;

    private String FriendsDoingThis;

    private String ActivityName;

    public String getIsWished ()
    {
        return IsWished;
    }

    public void setIsWished (String IsWished)
    {
        this.IsWished = IsWished;
    }

    public String getActivityID ()
    {
        return ActivityID;
    }

    public void setActivityID (String ActivityID)
    {
        this.ActivityID = ActivityID;
    }

    public String getRowNumber ()
    {
        return RowNumber;
    }

    public void setRowNumber (String RowNumber)
    {
        this.RowNumber = RowNumber;
    }

    public String getFriendsDoingThis ()
    {
        return FriendsDoingThis;
    }

    public void setFriendsDoingThis (String FriendsDoingThis)
    {
        this.FriendsDoingThis = FriendsDoingThis;
    }

    public String getActivityName ()
    {
        return ActivityName;
    }

    public void setActivityName (String ActivityName)
    {
        this.ActivityName = ActivityName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [IsWished = "+IsWished+", ActivityID = "+ActivityID+", RowNumber = "+RowNumber+", FriendsDoingThis = "+FriendsDoingThis+", ActivityName = "+ActivityName+"]";
    }
}