package tsp.warehouse;

import org.yaml.snakeyaml.DumperOptions;
import tsp.warehouse.storage.file.JsonFileDataManager;
import tsp.warehouse.storage.file.YamlFileDataManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Collection;

/**
 * Warehouse - Storage Manager
 *
 * @author TheSilentPro (Silent)
 */
public class Warehouse {

    public static <T extends Collection<T>> JsonFileDataManager<T> json(@Nonnull File file, @Nonnull Class<T> type) {
        return new JsonFileDataManager<>(file, type);
    }

    public static <T extends Collection<T>> YamlFileDataManager<T> yaml(@Nonnull File file, @Nullable DumperOptions dumperOptions) {
        return new YamlFileDataManager<>(file, dumperOptions);
    }

    public static <T extends Collection<T>> YamlFileDataManager<T> yaml(@Nonnull File file) {
        return yaml(file, null);
    }

}
