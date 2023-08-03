package org.liman.test;

import org.liman.test.annotations.Id;

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
