package com.bm.transfer.exceptions;

public class FavoriteNotFoundException extends RuntimeException{

    public FavoriteNotFoundException(){
        super();
    }

    public FavoriteNotFoundException(String msg){
        super(msg);
    }
}
