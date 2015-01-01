package com.ppt.Selenium;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {  
    
//    /*去警告*/
//    static {
//    	java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.SEVERE);
//    }
    
	private static final int POOL_SIZE = 2;

    public static void main(String[] args) throws IOException, InterruptedException {  
 

    	System.out.println("begin");
    	
    	Vector<Task> id2BlogTaskVector = new Vector<Task>();
    	Vector<Task> blog2CellTaskVector = new Vector<Task>();

    	//配置
    	Conf conf = new Conf();
    	conf.load();   	
    	
    	//任务
		for (String id : conf.getIdVector()) {
			id2BlogTaskVector.addElement(new Task(id, blog2CellTaskVector));
		}
    	
    	//消费者
    	ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);

//    	CountDownLatch id2BlogLatch=new CountDownLatch(id2BlogTaskVector.size());    	
//        while(!id2BlogTaskVector.isEmpty()){
//        	service.execute(new WorkerId2Blog(id2BlogTaskVector.remove(0), id2BlogLatch));      
//        }        
//        id2BlogLatch.await();
    	
    	
    	
    	Task task = new Task("000050", blog2CellTaskVector);
    	Blog blog = new Blog("http://guba.eastmoney.com/news,000050,137842639_1.html");
    	task.blogVector.addElement(blog);
    	blog2CellTaskVector.addElement(task);
    	
    	
    	
    	
        
        CountDownLatch Blog2CellLatch=new CountDownLatch(blog2CellTaskVector.size());
        while(!blog2CellTaskVector.isEmpty()){
        	service.execute(new WorkerBlog2Cell(blog2CellTaskVector.remove(0), Blog2CellLatch));      
        }
        Blog2CellLatch.await();
        
        
        
        
        
        
        System.out.println("end");
    }
}