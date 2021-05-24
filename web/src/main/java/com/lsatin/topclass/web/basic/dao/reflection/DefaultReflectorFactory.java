package com.lsatin.topclass.web.basic.dao.reflection;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultReflectorFactory implements ReflectorFactory {
    private final ConcurrentHashMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();
    @Override
    public Reflector findForClass(Class<?> type) {
        Reflector cached = reflectorMap.get(type);
        if (cached == null) {
            cached = new Reflector(type);
            reflectorMap.put(type, cached);
        }
        return cached;
    }
}
