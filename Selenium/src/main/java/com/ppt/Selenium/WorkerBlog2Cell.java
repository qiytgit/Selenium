package com.ppt.Selenium;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WorkerBlog2Cell implements Runnable{

        Task task;
        CountDownLatch latch;
        WorkerBlog2Cell(Task task, CountDownLatch latch) {
            this.task = task;
            this.latch = latch;
        }
        
        public void run() {
    		System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
    		WebDriver driver = new FirefoxDriver();  
        	
        	Vector<Blog> blogVector = task.getBlogVector();
        	
        	System.out.println(blogVector.size());
        	
        	for(Blog blog : blogVector){
        		
        		
        		String url = blog.getUrl();
        		driver.get(url);
        		
//        		WebElement element[] = driver.findElements(By.className("stockcodec"));
        		
        		List<WebElement> webElements = driver.findElements(By.className("stockcodec"));
        		
        		for(WebElement e : webElements){
        			String s = e.getText();
        			System.out.println(s);
        			if(s.length() > 0){
        				Cell c = new Cell(s);
        				blog.getCellVector().addElement(c);
        			}
        			
        		}
        		
        		
        		
        		
        		//一页30
        		
//        		webElements.get(0).getText();
//        		System.out.println(webElements.get(0).getText());
        		
        		
        		//正文stockcodec
        		
        		
        		
        		
        		
        		
        		
        		
        		//*[@id="zwconttbt"]
        		//*[@id="zwconbody"]/div
        		
        		
        		

        		
        		
        	}
        	
        	latch.countDown();
//			WebDriver driver = new HtmlUnitDriver();  
//			String id = task.getId();			
//
//			driver.get("http://guba.eastmoney.com/list," + id + ",99.html");
//			for(int i = 2; i < 82; i++){
//				WebElement element = driver.findElement(By.xpath("//*[@id=\"articlelistnew\"]/div[" + i + "]/span[3]/a"));
//				task.addBlogUrl(element.getAttribute("href"));
//			}
//			
//			String blogNumStr = driver.findElement(By.className("pager")).getText().split(" ")[1];
//			int blogNum = Integer.parseInt(blogNumStr)/80 + (Integer.parseInt(blogNumStr)%80 > 0 ? 1 : 0);			
//			for(int i = 2; i <= blogNum; i++){
//				driver.get("http://guba.eastmoney.com/list," + id + ",99_" + i + ".html");
//				for(int j = 2; j < 82; j++){
//					WebElement element = driver.findElement(By.xpath("//*[@id=\"articlelistnew\"]/div[" + j + "]/span[3]/a"));
//					task.addBlogUrl(element.getAttribute("href"));
//				}
//			}			
//			
//			task.next.addElement(task);
        }
}
