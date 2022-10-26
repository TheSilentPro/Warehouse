package tsp.warehouse.storage.sql;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * SQL(.db) based storage.
 *
 * @param <T>
 */
public abstract class SQLiteDataManager<T extends Collection<T>> extends SQLDataManager<T> {

    public SQLiteDataManager(@Nonnull File file, @Nonnull Executor executor) {
        super("jdbc:sqlite:" + file.getAbsolutePath(), executor);
    }

    public CompletableFuture<Integer> createTable(String name) {
        return sendPreparedUpdate("CREATE TABLE IF NOT EXISTS " + name);
    }

}