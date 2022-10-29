package tsp.warehouse.storage.sql;

import tsp.warehouse.storage.DataManager;
import tsp.warehouse.storage.util.WHValidate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * SQL based storage.
 *
 * @param <T> Type
 */
public abstract class SQLDataManager<T> implements DataManager<T> {

    private final String url;
    private final Executor executor;

    public SQLDataManager(@Nonnull String url, @Nullable Executor executor) {
        WHValidate.notNull(url, "URL can not be null!");

        this.url = url;
        this.executor = executor != null ? executor : Executors.newFixedThreadPool(1);
    }

    public CompletableFuture<Integer> sendPreparedUpdate(String statement) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getConnection().prepareStatement(statement).executeUpdate();
            } catch (SQLException ex) {
                throw new CompletionException(ex);
            }
        }, executor);
    }

    public CompletableFuture<ResultSet> sendPreparedQuery(String statement) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getConnection().prepareStatement(statement).executeQuery();
            } catch (SQLException ex) {
                throw new CompletionException(ex);
            }
        }, executor);
    }

    public CompletableFuture<Boolean> sendPrepared(String statement) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getConnection().prepareStatement(statement).execute();
            } catch (SQLException ex) {
                throw new CompletionException(ex);
            }
        }, executor);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public String getUrl() {
        return url;
    }

}