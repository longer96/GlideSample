package com.longer.library.error;

public class GlideException extends Exception {

    public GlideException() {
    }

    public GlideException(String message) {
        super(message);
    }

    public GlideException(String message, Throwable cause) {
        super(message, cause);
    }
}
