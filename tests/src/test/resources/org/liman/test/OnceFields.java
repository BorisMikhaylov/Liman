package org.liman.test;

import org.liman.test.annotations.Id;

public abstract class OnceFields {
    /*ERROR*/@Id
    public int f1;

    /*ERROR*/@Id
    public int f2;

}
