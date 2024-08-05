package com.bm.transfer.exceptions;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(){
        super();
    }
    public AccountNotFoundException(String msg){
        super(msg);
    }
}
