package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetAtCurrentSemesterByChildID implements Serializable
{
    private String StartDate;

    private String EndDate;


    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
}
