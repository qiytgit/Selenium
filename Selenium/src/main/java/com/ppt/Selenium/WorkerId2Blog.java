package com.ppt.Selenium;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;



//try {
//	this.log = new BufferedWriter(new FileWriter("log/" + Thread.currentThread().getName() + ".txt"));
//} catch (IOException e1) {
//	// TODO Auto-generated catch block
//	e1.printStackTrace();
//}

//String url = "http://guba.eastmoney.com/list," + task.getId() + ",99_" + i + ".html";
//try {
//	log.write(url);
//	log.newLine();
//	log.flush();
//} catch (IOException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//driver.get(url);

//url = element.getAttribute("href");
//try {
//	log.write(url);
//	log.newLine();
//	log.flush();
//} catch (IOException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//task.addBlog(new Blog(url));


public class WorkerId2Blog implements Runnable{

        Task task;
        CountDownLatch latch;
        BufferedWriter log;
        
        WorkerId2Blog(Task task, CountDownLatch latch) throws IOException {
            this.task = task;
            this.latch = latch;
        }
        
        public void run() {
    		System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
    		WebDriver driver = new FirefoxDriver();  
        	
 			driver.get("http://guba.eastmoney.com/list," + task.getId() + ",99.html");
			for(int i = 2; i < 82; i++){
				WebElement element = driver.findElement(By.xpath("//*[@id=\"articlelistnew\"]/div[" + i + "]/span[3]/a"));
				task.addBlog(new Blog(element.getAttribute("href")));
			}
			
			String blogNumStr = driver.findElement(By.className("pager")).getText().split(" ")[1];
			int remainder = Integer.parseInt(blogNumStr)%80;
			int pageNum = Integer.parseInt(blogNumStr)/80 + (remainder > 0 ? 1 : 0);			
			for(int i = 2; i < pageNum; i++){
				driver.get("http://guba.eastmoney.com/list," + task.getId() + ",99_" + i + ".html");
				for(int j = 2; j < 82; j++){
					WebElement element = driver.findElement(By.xpath("//*[@id=\"articlelistnew\"]/div[" + j + "]/span[3]/a"));
					task.addBlog(new Blog(element.getAttribute("href")));
				}
			}	
			
			driver.get("http://guba.eastmoney.com/list," + task.getId() + ",99_" + pageNum + ".html");
			for(int j = 2; j < (remainder > 0 ? remainder + 2 : 82); j++){
				WebElement element = driver.findElement(By.xpath("//*[@id=\"articlelistnew\"]/div[" + j + "]/span[3]/a"));
				task.addBlog(new Blog(element.getAttribute("href")));
			}
			
			
			task.next.addElement(task);
			latch.countDown();
			
			System.out.println("over");
        }
}
