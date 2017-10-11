package de.odinoxin.aidware.aidcloud.utils;

import javax.ws.rs.WebApplicationException;

public class ConcurrentException extends WebApplicationException {

    public ConcurrentException(String msg) {
        super();
    }

    public ConcurrentException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
