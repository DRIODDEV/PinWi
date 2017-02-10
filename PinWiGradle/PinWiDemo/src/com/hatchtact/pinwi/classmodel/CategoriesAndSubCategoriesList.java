package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CategoriesAndSubCategoriesList implements Serializable
{
	private ArrayList<CategoriesAndSubCategories> categoriesAndSubCategories=new ArrayList<CategoriesAndSubCategories>();

	public ArrayList<CategoriesAndSubCategories> getCategoriesAndSubCategories() {
		return categoriesAndSubCategories;
	}

	public void setCategoriesAndSubCategories(
			ArrayList<CategoriesAndSubCategories> categoriesAndSubCategories) {
		this.categoriesAndSubCategories = categoriesAndSubCategories;
	}
	
}
