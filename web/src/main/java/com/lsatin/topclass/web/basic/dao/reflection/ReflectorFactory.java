package com.lsatin.topclass.web.basic.dao.reflection;

public interface ReflectorFactory {
    Reflector findForClass(Class<?> type);
}
