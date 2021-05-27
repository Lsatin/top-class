package com.lsatin.topclass.webspring.basic.dao.reflection.invoker;


import com.lsatin.topclass.webspring.basic.dao.reflection.ReflectionException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Map;

public class AnnotationInvoker implements Invoker {
    private final Class<? extends Annotation> annotationType;
    private final Class<?> clazz;
    private final Method method;
    private final Field field;

    public AnnotationInvoker(Class<? extends Annotation> annotationType, Class<?> clazz) {
        this.annotationType = annotationType;
        this.clazz = clazz;
        this.field = null;
        this.method = null;
    }

    public AnnotationInvoker(Class<? extends Annotation> annotationType, Method method) {
        this.annotationType = annotationType;
        this.clazz = method.getDeclaringClass();
        this.field = null;
        this.method = method;
    }

    public AnnotationInvoker(Class<? extends Annotation> annotationType, Field field) {
        this.annotationType = annotationType;
        this.clazz = field.getDeclaringClass();
        this.field = field;
        this.method = null;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        if (target == null) {
            throw new ReflectionException("target is null");
        }
        Class<?> targetClass = target.getClass();
        Annotation annotation = null;
        try {
            if (clazz.isAssignableFrom(targetClass)) {
                if (targetClass == clazz) {
                    annotation = targetClass.getAnnotation(annotationType);
                }
                if (field != null) {
                    annotation = field.getAnnotation(annotationType);
                }
                if (method != null) {
                    annotation = method.getAnnotation(annotationType);
                }
            }
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
            declaredField.setAccessible(true);
            Map<?, ?> map = (Map<?, ?>) declaredField.get(invocationHandler);
            return map.get(args[0]);
        } catch (NoSuchFieldException e) {
            throw new ReflectionException("target '" + target + "' not found field '" + field + "'");
        }
    }

    @Override
    public Class<?> getType() {
        return annotationType;
    }
}
