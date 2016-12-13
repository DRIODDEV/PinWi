package com.hatchtact.pinwi.classmodel;

public class GetNewNotificationCountOnCI
{
    private String Count;

    public String getCount ()
    {
        return Count;
    }

    public void setCount (String Count)
    {
        this.Count = Count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Count = "+Count+"]";
    }
}
	