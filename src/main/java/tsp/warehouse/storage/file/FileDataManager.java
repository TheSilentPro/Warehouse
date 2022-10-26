package tsp.warehouse.storage.file;

import tsp.warehouse.storage.DataManager;
import tsp.warehouse.storage.util.Validate;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * Storage contained locally within a file.
 *
 * @param <T> Type
 */
public abstract class FileDataManager<T> implements DataManager<T> {

    private final File file;

    public FileDataManager(@Nonnull File file) {
        Validate.notNull(file, "File can not be null!");
        this.file = file;
    }

    public File getFile() {
        return file;
    }

}