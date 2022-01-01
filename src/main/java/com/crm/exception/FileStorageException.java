package com.crm.exception;

public class FileStorageException extends RuntimeException {

    //private static final int serialVersionUID = 1L;
    private String msg;

    public FileStorageException(String msg) {
        this.msg = msg;

    }

    public String getMsg() {
        return msg;
    }
}
