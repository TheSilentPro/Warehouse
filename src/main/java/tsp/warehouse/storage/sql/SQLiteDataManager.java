package tsp.warehouse.storage.sql;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.concurrent.Executor;

/**
 * SQL(.db) based storage.
 *
 * @param <T>
 */
public abstract class SQLiteDataManager<T> extends SQLDataManager<T> {

    public SQLiteDataManager(@Nonnull File file, @Nullable Executor executor) {
        super("jdbc:sqlite:" + file.getAbsolutePath(), executor);
    }

}