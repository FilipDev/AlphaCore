/*
 *  Created by Filip P. on 3/17/15 10:55 AM.
 */

package me.pauzen.alphacore.utils.io.files;

import java.io.File;
import java.io.IOException;

public class FileBuilder {

    private File file;

    private boolean createIfNotExist = false;

    public FileBuilder(String name) {
        this.file = new File(name);
    }

    public FileBuilder(String name, boolean createIfNotExist) {
        this(name);
        this.createIfNotExist = createIfNotExist;
    }

    public FileBuilder gotoFolder(String name) throws IOException {
        file = new File(file, name);

        return createIfNotExist(file);
    }

    public FileBuilder createFile(String name) throws IOException {
        return createIfNotExist(new File(file, name));
    }

    public FileBuilder createIfNotExist(File file) throws IOException {

        if (!createIfNotExist) {
            return this;
        }

        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    throw new IOException("Cannot create directories");
                }
            }

            if (!file.createNewFile()) {
                throw new IOException("Cannot create file");
            }
        }

        return this;
    }

    public FileBuilder createIfNotExist() throws IOException {
        return createIfNotExist(file);
    }

    public File getFile() {
        return file;
    }

}
