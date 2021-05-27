package com.lsatin.topclass.webspring.basic.dao.reflection.type;


import com.lsatin.topclass.webspring.basic.dao.PersistenceException;

public class TypeException extends PersistenceException {
    public TypeException() {
        super();
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }
}
