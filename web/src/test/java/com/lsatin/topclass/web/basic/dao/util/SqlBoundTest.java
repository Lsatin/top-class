package com.lsatin.topclass.web.basic.dao.util;

import com.lsatin.topclass.web.basic.dao.annotation.Table;
import com.lsatin.topclass.web.basic.dao.builder.SqlBound;
import com.lsatin.topclass.web.pojo.School;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

public class SqlBoundTest {

    @Test
    public void select() {
        final SqlBound.Builder builder = new SqlBound.Builder();
        builder.from("t_account_details")
                .read("t_account_details", "id", "username", "address", "email")
                .leftJoin("t_account", "t_account_details", "f_details_id", "id")
                .addLike("t_account_details", "username", "张三")
                .limit(0, 50);
        System.out.println(builder.build().count());
        System.out.println(builder.build().select());
        System.out.println(builder.build().insert());
        System.out.println(builder.build().update());
        System.out.println(builder.build().delete());
    }

    @Test
    public void get() {
        final School school = new School();
        System.out.println(getAnnotationValue(school.getName().getClass().getAnnotation(Table.class), "value"));
    }

    public static Object getAnnotationValue(Annotation annotation, String property) {
        Object result = null;
        if (annotation != null) {
            InvocationHandler invo = Proxy.getInvocationHandler(annotation); //获取被代理的对象
            Map map = (Map) getFieldValue(invo, "memberValues");
            if (map != null) {
                result = map.get(property);
            }
        }
        return result;
    }

    public static <T> Object getFieldValue(T object, String property) {
        if (object != null && property != null) {
            Class<T> currClass = (Class<T>) object.getClass();

            try {
                Field field = currClass.getDeclaredField(property);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(currClass + " has no property: " + property);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
