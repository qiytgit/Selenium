package com.ppt.Selenium;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Hello world!
 *
 */
public class App 
{
		
	
    public static void main( String[] args ) throws ClientProtocolException, IOException
    {
        System.out.println( "begin" );
        String runIdStr = null;
        String downloadPath = null;
        File downloadDir = null;
        
        /*建立目录，用于存文件*/
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
            runIdStr = df.format(new Date()); 	
            
            downloadPath = String.format("download/%s", runIdStr);
            downloadDir = new File(downloadPath);
			if(!downloadDir.exists())
			{
				downloadDir.mkdir();
			}
        }catch(Exception e){
        	e.printStackTrace();
        }

        /*访问，输入000612，确定*/
		System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		WebDriver driver1 = new FirefoxDriver();  
	
		driver1.get("http://www.szse.cn");      
		
		WebElement element = driver1.findElement(By.id("idZQDM11"));  
		element.clear();
		element.sendKeys("000612"); 
		  
		WebElement button = driver1.findElement(By.id("imgGGCX11"));  
		button.click();
      
		/*切到新窗口*/
		WebDriver driver2 = null;
		//得到当前窗口的句柄  
		String currentWindow = driver1.getWindowHandle();  
		//得到所有窗口的句柄  
		Set<String> handles = driver1.getWindowHandles();  
		Iterator<String> it = handles.iterator();  
		while(it.hasNext()){  
			String handle = it.next();  
			if(currentWindow.equals(handle)) continue;  
			driver2 = driver1.switchTo().window(handle);  
			System.out.println("title,url = "+driver2.getTitle()+","+driver2.getCurrentUrl());  
		} 
		
		/*输入代码和日期，确定*/
		element = driver2.findElement(By.id("stockCode")); 
		element.clear();
		element.sendKeys("000612"); 		

		element = driver2.findElement(By.id("startTime")); 
		element.clear();
		element.sendKeys("2001-01-01");
		
		button = driver2.findElement(By.name("imageField"));
		button.click();

			
		int seq = 1;
		String pdfUrl, title, dateStr, fileNameStr;
		String strs[];
		
		//通过chrome浏览器获得
		element = driver2.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/table/tbody/tr/td[2]/span[2]")); 
		String str1111 = element.getText();
		

        List<WebElement> elements = driver2.findElements(By.cssSelector("a[target='new']"));  
        for (WebElement e : elements){  
        	pdfUrl = e.getAttribute("href");
            System.out.println(pdfUrl);
            
            title = e.getText();
            System.out.println(title);
            
            int i = title.indexOf("：");
            
            title = title.substring(i+1);
            System.out.println(title);
            
            strs = pdfUrl.split("/");
            dateStr = strs[5];
            
            // 生成一个httpclient对象
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(pdfUrl);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            fileNameStr = String.format("download/%s/%04d_%s_%s.pdf", runIdStr, seq++, dateStr, title);
            File file = new File(fileNameStr);
            try{
                FileOutputStream fout =new FileOutputStream(file);
                int l = -1;
                byte[] tmp =new byte[1024*1024*20];
                while((l = in.read(tmp)) != -1) {
                    fout.write(tmp,0, l);
                }
                fout.flush();
                fout.close();
            }finally{
                // 关闭低层流。
                in.close();
            }
            httpclient.close();

        }  
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//      element.submit();
		try {  
		    Thread.sleep(3000);  
		} catch (InterruptedException e) {  
		    e.printStackTrace();  
		}  
//		System.out.println("Page title is: " + driver.getTitle());       
      
      
      //      WebElement element = driver.findElement(By.id("su"));  
//      element.sendKeys("hello Selenium!");  
//      element.submit();  
//      try {  
//          Thread.sleep(3000);  
//      } catch (InterruptedException e) {  
//          e.printStackTrace();  
//      }  
//      System.out.println("Page title is: " + driver.getTitle());  

//		WebElement textField = driver.findElement(By.id("kw"));       //by id
  //WebElement textField = driver.findElement(By.name("wd"));    by name
//  WebElement textField = driver.findElement(By.xpath("//input[@id='id']"));   // by xPath
//  textField.sendKeys("webdriver");    
        
        
        driver1.close();
        
        
        
        System.out.println( "end" );
        
        
        
//		By.className(className))    
//		By.cssSelector(selector)       
//		By.id(id)                     
//		By.linkText(linkText)          
//		By.name(name)             
//		By.partialLinkText(linkText)
//		By.tagName(name)       
//		By.xpath(xpathExpression) 
        
//        WebDriver webdriver = new FirefoxDriver();    
//        webdriver.get("https://github.com"); 
//        List<WebElement> webElements = webdriver.findElements(By 
//                       .xpath("//ul[@class='nav logged_out']/li")); 
////        Assert.assertEquals(5, webElements.size()); 
//
//           // Retrieve the anchors 
//           WebElement anchor1 = webElements.get(0).findElement(By.tagName("a")); 
//           WebElement anchor2 = webElements.get(1).findElement(By.tagName("a")); 
//           WebElement anchor3 = webElements.get(2).findElement(By.tagName("a")); 
//           WebElement anchor4 = webElements.get(3).findElement(By.tagName("a")); 
//           WebElement anchor5 = webElements.get(4).findElement(By.tagName("a")); 
//           
//        // Assertions    
///*           Assert.assertEquals("Signup and Pricing", anchor1.getText()); 
//           Assert.assertEquals("Explore GitHub", anchor2.getText()); 
//           Assert.assertEquals("Features", anchor3.getText()); 
//           Assert.assertEquals("Blog", anchor4.getText()); 
//           Assert.assertEquals("Login", anchor5.getText()); */
//               
//           webdriver.quit(); 
        
        
        
        
 
          
           
           
           
           
           
           
//	        String title = driver.getTitle();  
//	        
//		    //得到当前页面url  
//		    String currentUrl = driver.getCurrentUrl();  
//		      
//		    //输出title和currenturl  
//		    System.out.println(title+"\n"+currentUrl); 
           
           
           
           
           
//        WebDriver driver = new FirefoxDriver();  
//        driver.get("http://www.51.com");  
////        String js = "var user_input = document.getElementById(\"passport_51_user\").title;return user_input;";  
////          
////        String title = (String)((JavascriptExecutor)driver).executeScript(js);
////        System.out.println(title); 
//        ((JavascriptExecutor)driver).executeScript("alert(\"hello,this is a alert!\")");
           
           
           
           
           
           
           
           

//           driver.quit(); 
           
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
}
