package com.lsatin.topclass.web.dao.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class工具类
 */
public class ClassUtil {

    public static <T> T instance(Class<T> clazz, ResultSet resultSet) throws SQLException {
        Object result = null;
        try {
            result = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            while (resultSet.next()) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(result, resultSet.getString(field.getName()));
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) result;
    }

    public static List<?> instanceList(Class<?> clazz, ResultSet resultSet) throws SQLException {
        List<Object> result = new ArrayList<>();
        Object item;
        try {
            while (resultSet.next()) {
                item = clazz.newInstance();
                Class clazzs = clazz.getSuperclass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(item, resultSet.getString(field.getName()));
                }
                result.add(item);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

}
