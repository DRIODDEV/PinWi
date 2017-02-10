package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TutorialItems implements Serializable
{
	private int imageTutorial;
	private String textTutorial=null;

	public int getImageTutorial() {
		return imageTutorial;
	}
	public void setImageTutorial(int imageTutorial) {
		this.imageTutorial = imageTutorial;
	}
	public String getTextTutorial() {
		return textTutorial;
	}
	public void setTextTutorial(String textTutorial) {
		this.textTutorial = textTutorial;
	}
}
