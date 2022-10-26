package tsp.warehouse;

import org.yaml.snakeyaml.DumperOptions;
import tsp.warehouse.storage.file.JsonFileDataManager;
import tsp.warehouse.storage.file.YamlFileDataManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * Warehouse - Storage Manager
 *
 * @author TheSilentPro (Silent)
 */
public class Warehouse {

    public static <T> JsonFileDataManager<T> json(@Nonnull File file, @Nonnull Class<T> type) {
        return new JsonFileDataManager<>(file, type);
    }

    public static <T> YamlFileDataManager<T> yaml(@Nonnull File file, @Nullable Class<T> type, @Nullable DumperOptions dumperOptions) {
        return new YamlFileDataManager<>(file, type, dumperOptions);
    }

    public static <T> YamlFileDataManager<T> yaml(@Nonnull File file, @Nullable Class<T> type) {
        return yaml(file, type, null);
    }

    public static <T> YamlFileDataManager<T> yaml(@Nonnull File file) {
        return yaml(file, null);
    }

}
