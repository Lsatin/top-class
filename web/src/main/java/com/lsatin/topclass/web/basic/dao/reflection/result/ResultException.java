package com.lsatin.topclass.web.basic.dao.reflection.result;

import com.lsatin.topclass.web.basic.dao.PersistenceException;

public class ResultException extends PersistenceException {
    public ResultException() {
        super();
    }

    public ResultException(String message) {
        super(message);
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultException(Throwable cause) {
        super(cause);
    }
}
