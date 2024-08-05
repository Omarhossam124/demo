package com.bm.transfer.exceptions;

public class PasswordException extends RuntimeException{

    public PasswordException(){
        super();
    }
    public PasswordException(String msg){
        super(msg);
    }
}
