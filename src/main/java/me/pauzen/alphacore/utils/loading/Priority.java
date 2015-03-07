/*
 *  Created by Filip P. on 2/23/15 10:20 PM.
 */

package me.pauzen.alphacore.utils.loading;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {

    LoadPriority value() default LoadPriority.NORMAL;

}
