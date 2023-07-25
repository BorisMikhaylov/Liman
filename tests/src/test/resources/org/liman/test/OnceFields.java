package org.liman.test;

public abstract class OnceFields {
    /*ERROR*/@Id
    public int f1;

    /*ERROR*/@Id
    public int f2;

}
