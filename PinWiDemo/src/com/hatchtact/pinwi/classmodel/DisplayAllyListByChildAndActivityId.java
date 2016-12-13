package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DisplayAllyListByChildAndActivityId implements Serializable
{
	private ArrayList<DisplayAllyByChildAndActivityId> displayAllyByChildAndActivityId=new ArrayList<DisplayAllyByChildAndActivityId>();

	public ArrayList<DisplayAllyByChildAndActivityId> getDisplayAllyByChildAndActivityId() {
		return displayAllyByChildAndActivityId;
	}

	public void setDisplayAllyByChildAndActivityId(
			ArrayList<DisplayAllyByChildAndActivityId> displayAllyByChildAndActivityId) {
		this.displayAllyByChildAndActivityId = displayAllyByChildAndActivityId;
	}
}
