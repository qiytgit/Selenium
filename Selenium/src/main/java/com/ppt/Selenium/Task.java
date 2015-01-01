package com.ppt.Selenium;

import java.util.Vector;

public class Task {

	String id;
	Vector<Blog> blogVector;

	Vector<Task> next;
	
	Task(String id, Vector<Task> next) {
		this.id = id;
		this.blogVector = new Vector<Blog>();
		
		this.next = next;
    }
	
	String getId(){
		return id;
	}
	
	void addBlog(Blog blog){
		blogVector.addElement(blog);
	}
	
	Vector<Blog> getBlogVector(){
		return blogVector;
	}
}
