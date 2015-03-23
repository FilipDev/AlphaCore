/*
 *  Created by Filip P. on 3/19/15 6:28 PM.
 */

package me.pauzen.alphacore.utils.reflection.jar;

import me.pauzen.alphacore.utils.reflection.ReflectionFactory;

import java.io.InputStream;

public class JAREntryFile {
    
    private String path;
    
    private Class creatorClass;

    public JAREntryFile(String path) {
        this.path = path;
        this.creatorClass = ReflectionFactory.getCallerClasses()[0];
    }

    public InputStream stream() {
        return creatorClass.getClassLoader().getResourceAsStream(path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JAREntryFile that = (JAREntryFile) o;

        if (!path.equals(that.path)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
