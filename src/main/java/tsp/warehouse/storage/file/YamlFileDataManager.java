package tsp.warehouse.storage.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

/**
 * Yaml based storage.
 *
 * @param <T> Type
 */
@SuppressWarnings("unused")
public class YamlFileDataManager<T> extends FileDataManager<T> {

    private final Class<T> type;
    private Yaml yaml;

    public YamlFileDataManager(@Nonnull File file, @Nullable DumperOptions dumperOptions, @Nullable Executor executor) {
        super(file, executor);
        //noinspection unchecked
        this.type = (Class<T>) Class.class;
        this.yaml = dumperOptions != null ? new Yaml(dumperOptions) : new Yaml();
    }

    public YamlFileDataManager(@Nonnull File file) {
        this(file, null, null);
    }

    @Override
    public CompletableFuture<T> load() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                FileReader reader = new FileReader(getFile());
                return type != null ? yaml.loadAs(reader, type) : yaml.load(reader);
            } catch (FileNotFoundException ex) {
                throw new CompletionException(ex);
            }
        }, getExecutor());
    }

    @Override
    public CompletableFuture<Boolean> save(T t) {
        return CompletableFuture.supplyAsync(() -> {
            try (FileWriter writer = new FileWriter(getFile())) {
                yaml.dump(t, writer);
                return true;
            } catch (IOException ex) {
                throw new CompletionException(ex);
            }
        }, getExecutor());
    }

    public Yaml getYaml() {
        return yaml;
    }

    public void setYaml(Yaml yaml) {
        this.yaml = yaml;
    }

}
