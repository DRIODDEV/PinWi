package com.hatchtact.pinwi.classmodel;

public class AddCustomActivity 
{
	private String wsid="";
	private String wspwd="";
	private int SubCategoryID;
	private String Name="";
	private int ParentID;
	
	public String getWsid() {
		return wsid;
	}
	public void setWsid(String wsid) {
		this.wsid = wsid;
	}
	public String getWspwd() {
		return wspwd;
	}
	public void setWspwd(String wspwd) {
		this.wspwd = wspwd;
	}
	public int getSubCategoryID() {
		return SubCategoryID;
	}
	public void setSubCategoryID(int subCategoryID) {
		SubCategoryID = subCategoryID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
}
