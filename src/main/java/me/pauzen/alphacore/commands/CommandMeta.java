/*
 *  Created by Filip P. on 3/12/15 10:50 PM.
 */

package me.pauzen.alphacore.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMeta {

    String[] aliases() default {};

    String value();

    String description() default "%default%";

}
