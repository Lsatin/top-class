package com.lsatin.topclass.web.basic.dao.reflection;

import com.lsatin.topclass.web.basic.dao.PersistenceException;

public class ReflectionException extends PersistenceException {
    public ReflectionException() {
        super();
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
