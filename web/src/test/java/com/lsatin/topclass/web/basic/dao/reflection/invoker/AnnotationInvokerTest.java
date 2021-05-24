package com.lsatin.topclass.web.basic.dao.reflection.invoker;

import com.lsatin.topclass.web.basic.dao.annotation.Column;
import com.lsatin.topclass.web.pojo.School;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationInvokerTest {

    private AnnotationInvoker annotationInvoker;

    @Before
    public void before() {
        Class<?> clazz = null;
        Method method = null;
        try {
            clazz = Class.forName(School.class.getName());
            method = clazz.getDeclaredMethod("setName", String.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        annotationInvoker = new AnnotationInvoker(Column.class, method);
    }

    @Test
    public void invoke() {
        try {
            System.out.println(annotationInvoker.invoke(new School(), new Object[]{"value"}));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getType() {
        System.out.println(annotationInvoker.getType());
    }
}
