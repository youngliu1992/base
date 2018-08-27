package com.together.knowledge.base.drools.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Client {

    public static String upperFirstLetter(String src){
        try {
            String firstLetter = src.substring(0, 1).toUpperCase();
            String otherLetters = src.substring(1);
            return firstLetter + otherLetters;
        }catch (Exception e){
            System.out.println("");
            return src;
        }
    }

    public String lowerFirstLetter(String src){
        String firstLetter = src.substring(0, 1).toLowerCase();
        String otherLetters = src.substring(1);
        return firstLetter + otherLetters;
    }

    public static void main(String[] args){

        List<Map<String,String>> list = ExcelUtils.readExcel("d:\\1.xlsx");
        String ruleEntity = "pointDomain";
        String ruleName = "$"+ruleEntity;
        for(int i=0;i < list.size();i++){
            //一个map 是一行数据  一行数据就是一个rule
            Map<String,String> map = list.get(i);
            StringBuffer rule = new StringBuffer("");
            rule.append("rule buyNumsPoint \r\n");
            rule.append("salience "+i+" \r\n");
            rule.append("lock-on-active true \r\n");
            rule.append("when \r\n "+ruleName+" : "+upperFirstLetter(ruleEntity)+"(");
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            Iterator<Map.Entry<String, String>> it = entrySet.iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                if(key.indexOf("C:")>-1) {
                    //TODO 判断属性是否存在
                    rule.append(key.replaceAll("C:","") + " = " + entry.getValue() + " && ");
                }
            }
            rule = new StringBuffer(rule.substring(0,rule.length()-4));
            rule.append(") \r\n");
            rule.append("then ");
            it = entrySet.iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                if(key.indexOf("R:")>-1) {
                    //TODO 判断属性是否存在  首字母大写
                    rule.append(ruleName+".set"+upperFirstLetter(key.replaceAll("R:",""))+"("+entry.getValue()+") \r\n");
                }
            }
            rule.append("end \r\n");


            System.out.println(rule.toString());


        }


    }
}
