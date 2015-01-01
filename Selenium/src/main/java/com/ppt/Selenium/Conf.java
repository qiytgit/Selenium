package com.ppt.Selenium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

public class Conf {
	
	Vector<String> idVector;
	Properties basic;

	Conf() {
		idVector = new Vector<String> ();
    }
	
    void loadBasic() throws IOException{
        basic = new Properties();   
        FileInputStream fis = new FileInputStream("conf/" + "basic.properties");
        basic.load(fis);
        fis.close();
    }
    
    void loadIdList() throws IOException{
        FileReader file = new FileReader(new File("conf/" + "idList.txt"));  
        BufferedReader br = new BufferedReader(file); 
        
        while (br.ready()) {  
        	idVector.addElement(br.readLine());
        }  
        br.close();  
        file.close();
    } 	
	
	void load() throws IOException{
		loadBasic();
		loadIdList();
	}
	
	Vector<String> getIdVector(){
		return idVector;
	}
	
	Properties getBasic(){
		return basic;
	}
	
	void idVectorToString(){
		System.out.println("[idVector begin]");
		for(String str : idVector){
			System.out.println(str);
		}
		System.out.println("[idVector end]");
	}
	
}
