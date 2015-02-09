/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils.misc;

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
