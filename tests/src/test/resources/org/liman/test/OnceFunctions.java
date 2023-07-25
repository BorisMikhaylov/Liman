package org.liman.test;

public abstract class OnceFunctions {
    /*ERROR*/@Id
    public int f1(){
        return 1;
    }

    /*ERROR*/@Id
    public int f2(){
        return 2;
    };
}
