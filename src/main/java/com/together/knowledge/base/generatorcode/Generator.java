package com.together.knowledge.base.generatorcode;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Generator {

    public static void main(String[] args) throws Exception{
        BufferedReader bf = new BufferedReader(new FileReader(args[1]));
        String content = "";
        String tableName = "";
        String dir = "";
        String basePackagerUrl = "";
        String driverClass = "";
        String connectionURL = "";
        String userId = "";
        String password = "";
        StringBuffer sb = new StringBuffer("");
        while(content != null){
            content = bf.readLine();
            if(content == null){
                break;
            }
            sb.append(content);
        }
        Document document = DocumentHelper.parseText(sb.toString());
        Element rootElement = document.getRootElement();
        String name = rootElement.getName();
        List<Element> el = rootElement.elements();
        for(Element e: el){
            System.out.println(e.getName());
            if("context".equals(e.getName())){
                List<Element> tempList = e.elements();
                for(Element temp:tempList){
                    if(temp.getName().equals("jdbcConnection")){
                        driverClass = temp.attribute("driverClass").getValue();
                        connectionURL = temp.attribute("connectionURL").getValue();
                        userId = temp.attribute("userId").getValue();
                        password = temp.attribute("password").getValue();
                    }else if(temp.getName().equals("javaModelGenerator")){
                        basePackagerUrl = temp.attribute("targetPackage").getValue();
                    }else if(temp.getName().equals("sqlMapGenerator")){
                        dir = temp.attribute("targetProject").getValue();
                        File file = new File(dir);
                        if(!file.exists()){
                            file.mkdirs();
                        }
                    }else if(temp.getName().equals("table")){
                        tableName = temp.attribute("tableName").getValue();
                    }
                }
            }
        }
        //创建Configuration对象
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Generator.class, "/module");
        ClassGenerator classGenerator = new ClassGenerator(driverClass,connectionURL,userId,password);
        Map<String, MyClass> mapClass = classGenerator.generateClass(tableName,basePackagerUrl);
        Iterator<Map.Entry<String, MyClass>> it = mapClass.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, MyClass> entry = it.next();
            String moudleName = entry.getKey();
            MyClass myClass = entry.getValue();
            //获取模板
            Template template = configuration.getTemplate(moudleName+".ftl");
            //设置数据并执行
            Map map = new HashMap();
            map.put("myClass", myClass);
            Writer writer = new OutputStreamWriter(new FileOutputStream(dir+"\\"+myClass.getClassName()+".java"));
            template.process(map, writer);
        }
        System.out.println("生成文件成功:【"+dir+"】");
        System.out.println("请按【ENTER】键继续生成mapper.xml文件");
        System.in.read();
    }
}