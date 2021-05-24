package com.lsatin.topclass.web.basic.dao.reflection.type;

import com.lsatin.topclass.web.basic.dao.reflection.ReflectionException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T> {

    private final Type rawType;

    protected TypeReference() {
        this.rawType = getSuperclassTypeParameter(getClass());
    }

    Type getSuperclassTypeParameter(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            if (TypeReference.class != genericSuperclass) {
                return getSuperclassTypeParameter(clazz.getSuperclass());
            }
            throw new ReflectionException(getClass() + "继承于TypeReference但是没有找到泛型参数");
        }

        Type rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }
        return rawType;
    }

    public final Type getRawType() {
        return rawType;
    }
}
