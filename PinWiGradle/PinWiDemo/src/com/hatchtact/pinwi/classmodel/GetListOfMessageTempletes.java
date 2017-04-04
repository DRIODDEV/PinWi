package com.hatchtact.pinwi.classmodel;

public class GetListOfMessageTempletes
{
    private String Message;

    private String TemplateID;

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    public String getTemplateID ()
    {
        return TemplateID;
    }

    public void setTemplateID (String TemplateID)
    {
        this.TemplateID = TemplateID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Message = "+Message+", TemplateID = "+TemplateID+"]";
    }
}
