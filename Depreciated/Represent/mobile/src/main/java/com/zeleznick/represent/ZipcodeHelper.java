package com.zeleznick.represent;

/**
 * Created by zeleznick on 3/3/16.
 */
public class ZipcodeHelper {
    public Zipcode[] storedZipcodes;

    public ZipcodeHelper(){
       this.storedZipcodes = new Zipcode[] {
               new Zipcode(94702),
               new Zipcode(94704),
               new Zipcode(94709)
       };
    }
}

class Zipcode {
    public int value;

    public Zipcode(){
        super();
    }
    public Zipcode(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}