package me.pauzen.bukkitcommonpluginapi.utils;

public class Tuple<A, B> {
    
    private A a;
    private B b;

    public Tuple(B b, A a) {
        this.b = b;
        this.a = a;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
}
