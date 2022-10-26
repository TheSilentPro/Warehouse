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
import java.util.Optional;

/**
 * Yaml based storage.
 *
 * @param <T> Type
 */
public class YamlFileDataManager<T extends Collection<T>> extends FileDataManager<T> {

    private final Class<T> type;
    private Yaml yaml;

    public YamlFileDataManager(@Nonnull File file, @Nullable Class<T> type, @Nullable DumperOptions dumperOptions) {
        super(file);
        this.type = type;
        this.yaml = dumperOptions != null ? new Yaml(dumperOptions) : new Yaml();
    }

    public YamlFileDataManager(@Nonnull File file, @Nullable Class<T> type) {
        this(file, type, null);
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
    public Optional<Collection<T>> load() {
        try {
            FileReader reader = new FileReader(getFile());
            return Optional.ofNullable(type != null ? yaml.loadAs(reader, type) : yaml.load(reader));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean save(Collection<T> t) {
        try (FileWriter writer = new FileWriter(getFile())) {
            yaml.dump(t, writer);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
