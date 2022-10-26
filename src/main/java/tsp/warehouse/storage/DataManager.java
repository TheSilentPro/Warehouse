package tsp.warehouse.storage;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Base storage interface.
 *
 * @param <T> Type
 */
public interface DataManager<T> {

    CompletableFuture<Collection<T>> load();

    CompletableFuture<Boolean> save(Collection<T> t);

    default Executor getExecutor() {
        throw new NullPointerException("Executor has not been set!");
    }

}