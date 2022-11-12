package tsp.warehouse.storage.file;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

public class SerializableFileDataManager<Serializable> extends FileDataManager<Serializable> {

    public SerializableFileDataManager(@Nonnull File file, @Nullable Executor executor) {
        super(file, executor);
    }

    @Override
    public CompletableFuture<Serializable> load() {
        return CompletableFuture.supplyAsync(() -> {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFile()))) {
                //noinspection unchecked
                return (Serializable) in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                throw new CompletionException(ex);
            }
        }, getExecutor());
    }

    @Override
    public CompletableFuture<Boolean> save(Serializable data) {
        return CompletableFuture.supplyAsync(() -> {
            if (!getFile().exists()) {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    getFile().createNewFile();
                } catch (IOException ex) {
                    throw new CompletionException(ex);
                }
            }

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getFile()))) {
                out.writeObject(data);
                return true;
            } catch (IOException ex) {
                throw new CompletionException(ex);
            }
        }, getExecutor());
    }

}
