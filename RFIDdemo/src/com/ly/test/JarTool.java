package com.ly.test;

import java.io.File;  
/** 
 * ��ȡ�����jar��·����Ϣ 
 * @author Administrator 
 *  2011-01-16 13:53:12 
 */  
public class JarTool {  
    //��ȡjar����·��  
    public static String getJarPath(){  
        File file = getFile();  
        if(file==null)return null;  
         return file.getAbsolutePath();  
    }  
    //��ȡjarĿ¼  
    public static String getJarDir() {  
        File file = getFile();  
        if(file==null)return null;  
         return getFile().getParent();  
    }  
    //��ȡjar����  
    public static String getJarName() {  
        File file = getFile();  
        if(file==null)return null;  
        return getFile().getName();  
    }  
  
    private static File getFile() {  
        //�ؼ�������...  
        String path = JarTool.class.getProtectionDomain().getCodeSource()  
                .getLocation().getFile();  
        try{  
            path = java.net.URLDecoder.decode(path, "UTF-8");//ת���������ļ��ո�  
        }catch (java.io.UnsupportedEncodingException e){  
            return null;  
        }  
        return new File(path);  
    }  
      
}  