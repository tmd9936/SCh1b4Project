package com.h1b4.www.vo;

public class Category {
	private String category;
	private String category_kr;
	
	public Category() {
		super();
	}
	
	public Category(String category, String category_kr) {
		super();
		this.category = category;
		this.category_kr = category_kr;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory_kr() {
		return category_kr;
	}

	public void setCategory_kr(String category_kr) {
		this.category_kr = category_kr;
	}

	@Override
	public String toString() {
		return "Category [category=" + category + ", category_kr=" + category_kr + "]";
	}
}
