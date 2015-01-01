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
    	Vector<Task> cell2DBTaskVector = new Vector<Task>();
    	ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
    	
    	//conf2Id
    	Conf conf = new Conf();
    	conf.load();
		for (String id : conf.getIdVector()) {
			id2BlogTaskVector.addElement(new Task(id));
		}
    	
    	//id2Blog
    	CountDownLatch id2BlogLatch=new CountDownLatch(id2BlogTaskVector.size());    	
        while(!id2BlogTaskVector.isEmpty()){
        	service.execute(new WorkerId2Blog(id2BlogTaskVector.remove(0), blog2CellTaskVector, id2BlogLatch));      
        }        
        id2BlogLatch.await();
    	
    	
    	
//    	Task task = new Task("000050", blog2CellTaskVector);
//    	Blog blog = new Blog("http://guba.eastmoney.com/news,000050,133430166.html");
//    	task.blogVector.addElement(blog);
//    	blog2CellTaskVector.addElement(task);
    	
    	
    	
    	
        //blog2Cell
        CountDownLatch Blog2CellLatch=new CountDownLatch(blog2CellTaskVector.size());
        while(!blog2CellTaskVector.isEmpty()){
        	service.execute(new WorkerBlog2Cell(blog2CellTaskVector.remove(0), cell2DBTaskVector, Blog2CellLatch));      
        }
        Blog2CellLatch.await();
        
        
        
        //cell2DB
        CountDownLatch cell2DBLatch=new CountDownLatch(cell2DBTaskVector.size());
        while(!cell2DBTaskVector.isEmpty()){
        	service.execute(new WorkerCell2DB(cell2DBTaskVector.remove(0), cell2DBLatch));      
        }
        cell2DBLatch.await();       
        
        
        
        System.out.println("end");
    }
}