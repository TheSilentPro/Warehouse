package tsp.warehouse.storage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Base storage interface.
 *
 * @param <T> Type
 */
@SuppressWarnings("unused")
public interface DataManager<T> {

    CompletableFuture<T> load();

    CompletableFuture<Boolean> save(T data);

    default Executor getExecutor() {
        throw new UnsupportedOperationException("Executor has not been implemented!");
    }

}