package com.escel.tool;

import com.escel.init.CreateExcel;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ab1324ab on 2017/5/6.
 */
public class PropertiesTool {

    public  static final  String SGMTA_SPLIT="\\&";
    public  static final  String READ_SGMTA_SPLIT="&";
    public  static final  String SGMTA_SPLIT_LATER="\\|";
    public  static final  String READ_SPLIT_LATER="|";
    private static Properties properties=new Properties();

    /**
     * 读取配置文件
     * @param fileName
     * @return
     */
    public static Map<String,String> redConfigFile(String fileName){
        Map<String,String> contentMap=new HashMap<String, String>();
        try {
            properties.load(new FileInputStream(new File(System.getProperty("user.dir")+"/"+fileName)));
            Iterator it=properties.entrySet().iterator();
                    while(it.hasNext()){
                        Map.Entry entry=(Map.Entry)it.next();
                        String key = (String)entry.getKey();
                        String value =(String)entry.getValue();
                        contentMap.put(key,value);
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
                return contentMap;
    }

    /**
     * 写入配置文件
     * @param filename
     * @param key
     * @param value
     */
    public static void writeSet(String filename,String key,String value){
        try {
            Properties writeProperties=new Properties();
            String pathDir=System.getProperty("user.dir")+"\\config.properties";
            writeProperties.load(new FileInputStream(new File(pathDir)));
            writeProperties.setProperty(key,value);
            writeProperties.store(new FileOutputStream(new File(pathDir)),key);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 自动恢复设置
     */
    public static void initialization(){
        InputStream inputStream=PropertiesTool.class.getClass().getResourceAsStream("/config.properties");
        System.out.println("自动："+PropertiesTool.class.getClass().getResourceAsStream("/config.properties"));
        byte[] bytes=new byte[1024];
        try {
            String configFileUrl=new File(System.getProperty("user.dir"))+"/config.properties";
            if(!new File(configFileUrl).exists()){
                new File(configFileUrl).createNewFile();
            }
            FileOutputStream fileOutputStream=new FileOutputStream(new File(configFileUrl));
            int bytesWritten = 0;
            while (bytesWritten !=-1){
                fileOutputStream.write(bytes,0,bytesWritten);
                bytesWritten =inputStream.read(bytes);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
