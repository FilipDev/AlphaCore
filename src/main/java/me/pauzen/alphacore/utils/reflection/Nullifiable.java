/*
 *  Created by Filip P. on 2/9/15 6:59 PM.
 */

package me.pauzen.alphacore.utils.reflection;

public interface Nullifiable {

    public default void nullify() {
        Reflection thisReflection = new Reflection(this);
        ReflectionFactory.getFields(getClass()).stream().filter(field -> field.isAnnotationPresent(Nullify.class)).forEach(field -> thisReflection.setValue(field, null));
    }

}
