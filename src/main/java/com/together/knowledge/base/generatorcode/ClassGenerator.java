package com.together.knowledge.base.generatorcode;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassGenerator {

    private String driverClass;
    private String connectionURL;
    private String userId;
    private String password;


    public ClassGenerator(String driverClass,String connectionURL,String userId,String password){
        this.driverClass = driverClass;
        this.connectionURL = connectionURL;
        this.userId = userId;
        this.password = password;
    }

    public DatabaseMetaData init(){
        DatabaseMetaData databaseMetaData = null;
        try{
            Class.forName(driverClass);
            String url = connectionURL;
            String user = userId;
            Connection connection = DriverManager.getConnection(url, user, password);
            databaseMetaData = connection.getMetaData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaseMetaData;
    }

    //根据表名生产出类
    public Map<String, MyClass> generateClass(String tableName, String basePackagerUrl){
        Map<String, MyClass> map = new HashMap<String, MyClass>();

        MyClass myClass = new MyClass();
        List<Field> fieldList = new ArrayList<Field>();
        DatabaseMetaData databaseMetaData = init();
        try{
            ResultSet rs = databaseMetaData.getColumns(null, "%", tableName, "%");
            while(rs.next()){
                //列名
                String columnName = rs.getString("COLUMN_NAME");
                //类型
                String typeName = rs.getString("TYPE_NAME");
                System.out.println("typeName:" + typeName);
                //注释
                String remarks = rs.getString("REMARKS");
                Field field = new Field();
                field.setFieldName(columnName);
                field.setFieldType(columnTypeToFieldType(typeName));
                field.setFieldRemarks(remarks);
                field.setFieldNameUpperFirstLetter(upperFirstLetter(columnName));
                fieldList.add(field);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String className = tableNameToClassName(tableName);

        myClass.setClassName(className+"Entity");
        myClass.setFieldList(fieldList);
        myClass.setPackageUrl(basePackagerUrl+".po");
        map.put("entity",myClass);


        myClass = new MyClass();
        myClass.setClassName(className+"Service");
        myClass.setPackageUrl(basePackagerUrl+".service");
        map.put("service",myClass);

        myClass = new MyClass();
        myClass.setClassName(className+"ServiceImpl");
        myClass.setPackageUrl(basePackagerUrl+".service.impl");
        myClass.setBaseName(className);
        map.put("serviceImpl",myClass);

        myClass = new MyClass();
        myClass.setClassName(className+"TransactionService");
        myClass.setBaseName(className);
        myClass.setPackageUrl(basePackagerUrl+".tx");
        map.put("txService",myClass);

        myClass = new MyClass();
        myClass.setClassName(className+"TransactionServiceImpl");
        myClass.setBaseName(className);
        myClass.setComponentName(lowerFirstLetter(className)+"TransactionServiceImpl");
        myClass.setPackageUrl(basePackagerUrl+".tx.impl");
        map.put("txServiceImpl",myClass);

        myClass = new MyClass();
        myClass.setClassName(className+"Dao");
        myClass.setPackageUrl(basePackagerUrl+".dao");
        map.put("dao",myClass);



        return map;
    }

    //字段类型转换成属性类型
    public String columnTypeToFieldType(String columnType){
        String fieldType = null;
        switch (columnType) {
            case "INT":
                fieldType = "Integer";
                break;
            case "VARCHAR":
                fieldType = "String";
                break;
            case "CHAR":
                fieldType = "String";
                break;
            case "DATE":
                fieldType = "Date";
                break;
            case "BIGINT":
                fieldType = "Long";
                break;
            default:
                fieldType = "String";
                break;
        }
        return fieldType;
    }

    //首字母大写
    public String upperFirstLetter(String src){
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

    //表名转类名
    public String tableNameToClassName(String tableName){
        StringBuilder className = new StringBuilder();
        //aa_bb_cc  AaBbCc
        String[] split = tableName.split("_");
        for (String item : split) {
            className.append(upperFirstLetter(item));
        }
        return className.toString();
    }
}
