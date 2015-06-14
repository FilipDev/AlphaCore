/*
 *  Created by Filip P. on 5/23/15 7:30 PM.
 */

package me.pauzen.alphacore.utils.misc.test;

public enum Test {

    OR((object, objects) -> {
        for (Object o : objects) {
            if (o.equals(object)) {
                return true;
            }
        }
        return false;
    }),
    AND((object, objects) -> {
        for (Object o : objects) {
            if (!o.equals(object)) {
                return false;
            }
        }
        return true;
    }),
    VALID((object, objects) -> object != null),;

    private Experiment experiment;

    Test(Experiment experiment) {
        this.experiment = experiment;
    }

    public static boolean args(Object[] array, int length) {
        return array.length >= length;
    }

    public boolean test(Object object, Object... objects) {
        return experiment.test(object, objects);
    }

    public void test(String falseMessage, Object object, Object... objects) {
        if (!experiment.test(object, objects)) {
            throw new IllegalStateException(falseMessage);
        }
    }
}
