/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils.misc;

public class Tuple<A, B> {

    private A a;
    private B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
}
