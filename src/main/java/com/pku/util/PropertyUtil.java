package com.pku.util;

import java.io.*;
import java.util.Properties;

/** 
 * PropertyUtil属性文件读写工具类 
 */  
public class PropertyUtil {  
	private static PropertyUtil pu = null;
	private static Properties props = null;
	public static final String PROPERTY_FILE =System.getProperty("user.dir")+"//resources//config//CONFIG.properties";  
	
	private PropertyUtil() {
		
	}
	
	static {
		props = new Properties();
		try {
			InputStreamReader fn = new InputStreamReader(new FileInputStream(PROPERTY_FILE),"UTF-8");
			props.load(fn);
			fn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PropertyUtil getInstance() {
		if(pu == null) {
			pu = new PropertyUtil();  
		}
		return pu;
 	}
			

  
    /** 
     * 根据Key 读取Value 
     *  
     * @param key 
     * @return 
     */  
    public  String readData(String key) {  
         String value = props.getProperty(key);  
         return value;
    }  
  
      
    /** 
     * 修改或添加键值对 如果key存在，修改 反之，添加。 
     *  
     * @param key 
     * @param value 
     */  
    public void writeData(String key, String value) {  
        try {  
            File file = new File(PROPERTY_FILE);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            props.load(fis);  
            fis.close();//一定要在修改值之前关闭fis  
            OutputStream fos = new FileOutputStream(PROPERTY_FILE);  
            props.setProperty(key, value);  
            props.store(fos, "Update '" + key + "' value");  
            fos.close();  
        } catch (IOException e) {  
            System.err.println("Visit " + PROPERTY_FILE + " for updating "  
                    + value + " value error");  
        }  
    }    
}  