package com.together.knowledge.base.invoke.designinvok;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class DefaultInvoction{

    public abstract Object doBefore();

    public abstract Object doAfter();

    public Object invoke(String methodName,Object object,Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        doBefore();
        Class[] parameterTypes = new Class[args.length];
        for(int i=0;i < args.length;i++){
            Object arg = args[i];
            parameterTypes[i] = arg.getClass();
        }
        Method method = object.getClass().getDeclaredMethod(methodName,parameterTypes);
        Object result = method.invoke(object,args);
        doAfter();
        return result;
    }
}
