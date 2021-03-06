package com.together.knowledge.base.util;

import com.together.knowledge.base.drools.DroolsEngineEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

 /**
    * @author liuy
    * @date 2018/8/27 17:39
    * @param
    * @return
    * @description:扫描包中的注解
   */
public final class AnnoManageUtil
{
    /**
     * 获取当前包路径下指定的Controller注解类型的文件
     * @param packageName 包名
     * @param annotation 注解类型
     * @return 文件
     */
    public static  List<Class<?>> getPackageController(String packageName, Class<? extends Annotation> annotation)
    {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = null;
        //获取当前目录下面的所有的子目录的url
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            //如果当前类型是文件类型
            if ("file".equals(protocol)) {
                String filePath = null;
                try {
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                filePath = filePath.substring(1);
                getFilePathClasses(packageName,filePath,classList,annotation);
            }
        }
        return classList;
    }

    /**
     * 从指定的包下面找到文件名
     * @param packageName
     * @param filePath
     * @param classList
     * @param annotation 注解类型
     */
    private static void getFilePathClasses(String packageName,String filePath,List<Class<?>> classList,
                                           Class<? extends Annotation> annotation) {
        Path dir = Paths.get(filePath);
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream(dir);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for(Path path : stream) {
            String fileName = String.valueOf(path.getFileName());
            String className = fileName.substring(0, fileName.length() - 6);
            Class<?> classes = null;
            try {
                classes = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //判断该注解类型是不是所需要的类型
            if (null != classes && null != classes.getAnnotation(annotation)) {
                //把这个文件加入classlist中
                classList.add(classes);
            }
        }
    }


    public static void main(String[] args){
        List<Class<?>> list = getPackageController("com.liuy.knowledge.base.drools",DroolsEngineEntity.class);
        System.out.println("123");
    }


}
