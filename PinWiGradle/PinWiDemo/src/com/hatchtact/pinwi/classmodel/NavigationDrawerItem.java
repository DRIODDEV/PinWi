package com.hatchtact.pinwi.classmodel;

public class NavigationDrawerItem 
{
	private String title;
	private int icon;
	
	public NavigationDrawerItem(){}

	public NavigationDrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
}
