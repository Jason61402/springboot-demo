package com.demoshop.shop.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApiUtil {

    public static  <T>Map<String,Object> getRequestUriVariables(T source) throws
            NoSuchMethodException,SecurityException,IllegalAccessException,IllegalArgumentException,
            InvocationTargetException{

        Map<String,Object> returnMap = new HashMap<>();
        Field[] fields = source.getClass().getDeclaredFields();
        for (int i = 0; i<fields.length; i++){
            String filedName = fields[i].getName();
            String firstLetter = filedName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + filedName.substring(1);
            Method method = source.getClass().getMethod(getter, new Class[]{});

            Object value = method.invoke(source , new Object[]{});
            returnMap.put(filedName, value);

        }
        return returnMap;
    }


}
