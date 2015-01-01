package com.ppt.Selenium;

import java.util.Vector;

public class Blog {
	
	String url;
	Vector<Cell> cellVector;
	
	public Vector<Cell> getCellVector() {
		return cellVector;
	}

	Blog(String url) {
		this.url = url;
		this.cellVector = new Vector<Cell>();
    }
	
	String getUrl(){
		return url;
	}
	
	
	
	

}
