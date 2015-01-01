package com.ppt.Selenium;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
public class WorkerBlog2Cell implements Runnable{

    Task task;
    Vector<Task> taskVector;
    CountDownLatch latch;
    WorkerBlog2Cell(Task task, Vector<Task> taskVector, CountDownLatch latch) {
        this.task = task;
        this.taskVector = taskVector;
        this.latch = latch;
    }
    
    public void run() {
		System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		WebDriver driver = new FirefoxDriver();  
    	
    	Vector<Blog> blogVector = task.getBlogVector();
    	
    	System.out.println(blogVector.size());
    	
    	for(Blog blog : blogVector){
    		
    		Vector<Cell> cellVector = blog.getCellVector();
    		String blogUrl = blog.getUrl();
    		driver.get(blogUrl);

    		String pageUrlBase = blogUrl.substring(0, blogUrl.length()-5);
    		int pageNum = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"mainbody\"]/div[1]/div[1]/span")).getText());

    		for(int i = 1; i <= pageNum; i++){

    			driver.get(pageUrlBase + "_" + i + ".html");
        		List<WebElement> webElements = driver.findElements(By.className("stockcodec"));
        		
        		for(WebElement e : webElements){
        			String s = e.getText();
        			if(s.length() > 0){
        				Cell c = new Cell(s);
        				cellVector.addElement(c);
        			}
        		}
    		}
    	}
    	
    	taskVector.addElement(task);
    	latch.countDown();
    }
}
