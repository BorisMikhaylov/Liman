package org.liman.test;

public abstract class TestClass {
    @Id
    public int f1;

    @Id
    public int f2;

    abstract void foo(
            int p1,
            int p2
    );
}
