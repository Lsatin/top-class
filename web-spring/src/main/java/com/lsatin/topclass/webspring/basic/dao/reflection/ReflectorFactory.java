package com.lsatin.topclass.webspring.basic.dao.reflection;

public interface ReflectorFactory {
    Reflector findForClass(Class<?> type);
}
