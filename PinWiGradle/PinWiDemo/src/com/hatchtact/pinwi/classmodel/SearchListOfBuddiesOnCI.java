package com.hatchtact.pinwi.classmodel;


public class SearchListOfBuddiesOnCI
{
private String Status;

private String ProfileImage;

private String FriendID;

private String totalFriend;

private String ChildName;

public String getStatus ()
{
return Status;
}

public void setStatus (String Status)
{
this.Status = Status;
}

public String getProfileImage ()
{
return ProfileImage;
}

public void setProfileImage (String ProfileImage)
{
this.ProfileImage = ProfileImage;
}

public String getFriendID ()
{
return FriendID;
}

public void setFriendID (String FriendID)
{
this.FriendID = FriendID;
}

public String getTotalFriend ()
{
return totalFriend;
}

public void setTotalFriend (String totalFriend)
{
this.totalFriend = totalFriend;
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
return "ClassPojo [Status = "+Status+", ProfileImage = "+ProfileImage+", FriendID = "+FriendID+", totalFriend = "+totalFriend+", ChildName = "+ChildName+"]";
}
}