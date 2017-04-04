package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetFriendsListByLoggedIDOnCI implements Serializable 
{

    private String ChildID;

    private String RowNumber;

    private String ProfileImage;

    private String totalFriendsCount;

    private String ChildName;

    public String getChildID ()
    {
        return ChildID;
    }

    public void setChildID (String ChildID)
    {
        this.ChildID = ChildID;
    }

    public String getRowNumber ()
    {
        return RowNumber;
    }

    public void setRowNumber (String RowNumber)
    {
        this.RowNumber = RowNumber;
    }

    public String getProfileImage ()
    {
        return ProfileImage;
    }

    public void setProfileImage (String ProfileImage)
    {
        this.ProfileImage = ProfileImage;
    }

    public String getTotalFriendsCount ()
    {
        return totalFriendsCount;
    }

    public void setTotalFriendsCount (String totalFriendsCount)
    {
        this.totalFriendsCount = totalFriendsCount;
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
        return "ClassPojo [ChildID = "+ChildID+", RowNumber = "+RowNumber+", ProfileImage = "+ProfileImage+", totalFriendsCount = "+totalFriendsCount+", ChildName = "+ChildName+"]";
    }
}
