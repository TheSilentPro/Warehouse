package tsp.warehouse.storage.file;

import tsp.warehouse.storage.DataManager;
import tsp.warehouse.storage.util.WHValidate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Storage contained locally within a file.
 *
 * @param <T> Type
 */
public abstract class FileDataManager<T> implements DataManager<T> {

    private final File file;
    private final Executor executor;

    public FileDataManager(@Nonnull File file, @Nullable Executor executor) {
        WHValidate.notNull(file, "File can not be null!");
        this.file = file;
        this.executor = executor != null ? executor : Executors.newFixedThreadPool(1);
    }

    public File getFile() {
        return file;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }
}