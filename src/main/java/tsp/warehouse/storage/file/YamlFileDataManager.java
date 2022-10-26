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
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Yaml based storage.
 *
 * @param <T> Type
 */
public class YamlFileDataManager<T> extends FileDataManager<T> {

    private final Class<Collection<T>> type;
    private Yaml yaml;

    public YamlFileDataManager(@Nonnull File file, @Nullable DumperOptions dumperOptions) {
        super(file);
        //noinspection unchecked
        this.type = (Class<Collection<T>>) (Class<T>) Collection.class;
        this.yaml = dumperOptions != null ? new Yaml(dumperOptions) : new Yaml();
    }
    public YamlFileDataManager(@Nonnull File file) {
        this(file, null);
    }

    public Yaml getYaml() {
        return yaml;
    }

    public void setYaml(Yaml yaml) {
        this.yaml = yaml;
    }

    @Override
    public CompletableFuture<Collection<T>> load() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                FileReader reader = new FileReader(getFile());
                return type != null ? yaml.loadAs(reader, type) : yaml.load(reader);
            } catch (FileNotFoundException ex) {
                throw new CompletionException(ex);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> save(Collection<T> t) {
        try (FileWriter writer = new FileWriter(getFile())) {
            yaml.dump(t, writer);
            return CompletableFuture.completedFuture(true);
        } catch (IOException ex) {
            throw new CompletionException(ex);
        }
    }

}
