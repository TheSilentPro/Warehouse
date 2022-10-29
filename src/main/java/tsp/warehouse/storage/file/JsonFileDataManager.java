package tsp.warehouse.storage.file;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

/**
 * Json based storage.
 *
 * @param <T> Type
 */
@SuppressWarnings("unused")
public class JsonFileDataManager<T> extends FileDataManager<T> {

    private final Type type;
    private Gson gson;

    public JsonFileDataManager(@Nonnull File file, @Nullable Executor executor) {
        super(file, executor);

        this.type =  new TypeToken<Collection<T>>(){}.getType();
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public JsonFileDataManager(@Nonnull File file) {
        this(file, null);
    }

    @Override
    public CompletableFuture<Collection<T>> load() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return gson.fromJson(new FileReader(getFile()), type);
            } catch (FileNotFoundException ex) {
                throw new CompletionException(ex);
            }
        }, getExecutor());
    }

    @Override
    public CompletableFuture<Boolean> save(Collection<T> t) {
        return CompletableFuture.supplyAsync(() -> {
            try (FileWriter writer = new FileWriter(getFile())) {
                gson.toJson(t, type, writer);
                return true;
            } catch (IOException ex) {
                throw new CompletionException(ex);
            }
        }, getExecutor());
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public Gson getGson() {
        return gson;
    }

}