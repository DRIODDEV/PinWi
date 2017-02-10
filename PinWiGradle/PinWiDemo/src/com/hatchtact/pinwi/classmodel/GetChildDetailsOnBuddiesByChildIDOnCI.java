package com.hatchtact.pinwi.classmodel;
public class GetChildDetailsOnBuddiesByChildIDOnCI
{
    private String ParentName;

    private String DateOfBirth;

    private String ChildID;

    private String ChidName;

    private String SchoolName;

    private String ProfileImage;

    private String Siblings;

    private String totalFriend;

    private String CityName;
    
    public String getParentName ()
    {
        return ParentName;
    }

    public void setParentName (String ParentName)
    {
        this.ParentName = ParentName;
    }

    public String getDateOfBirth ()
    {
        return DateOfBirth;
    }

    public void setDateOfBirth (String DateOfBirth)
    {
        this.DateOfBirth = DateOfBirth;
    }

    public String getChildID ()
    {
        return ChildID;
    }

    public void setChildID (String ChildID)
    {
        this.ChildID = ChildID;
    }

    public String getChidName ()
    {
        return ChidName;
    }

    public void setChidName (String ChidName)
    {
        this.ChidName = ChidName;
    }

    public String getSchoolName ()
    {
        return SchoolName;
    }

    public void setSchoolName (String SchoolName)
    {
        this.SchoolName = SchoolName;
    }

    public String getProfileImage ()
    {
        return ProfileImage;
    }

    public void setProfileImage (String ProfileImage)
    {
        this.ProfileImage = ProfileImage;
    }

    public String getSiblings ()
    {
        return Siblings;
    }

    public void setSiblings (String Siblings)
    {
        this.Siblings = Siblings;
    }

    public String getTotalFriend ()
    {
        return totalFriend;
    }

    public void setTotalFriend (String totalFriend)
    {
        this.totalFriend = totalFriend;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ParentName = "+ParentName+", DateOfBirth = "+DateOfBirth+", ChildID = "+ChildID+", ChidName = "+ChidName+", SchoolName = "+SchoolName+", ProfileImage = "+ProfileImage+", Siblings = "+Siblings+", totalFriend = "+totalFriend+"]";
    }

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}
}
		