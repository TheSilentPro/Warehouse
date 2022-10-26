package tsp.warehouse.storage;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Base storage interface.
 *
 * @param <T> Type
 */
public interface DataManager<T extends Collection<T>> {

    Optional<T> load();

    boolean save(T t);

    default Executor getExecutor() {
        throw new NullPointerException("Executor has not been set!");
    }

    default CompletableFuture<Optional<T>> loadAsync() {
        return CompletableFuture.supplyAsync(this::load, getExecutor());
    }

    default CompletableFuture<Boolean> saveAsync(T t) {
        return CompletableFuture.supplyAsync(() -> save(t), getExecutor());
    }

}