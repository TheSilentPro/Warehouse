package tsp.warehouse;

import org.yaml.snakeyaml.DumperOptions;
import tsp.warehouse.storage.file.JsonFileDataManager;
import tsp.warehouse.storage.file.YamlFileDataManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.concurrent.Executor;

/**
 * Warehouse - Storage Manager
 *
 * @author TheSilentPro (Silent)
 */
@SuppressWarnings("unused")
public final class Warehouse {

    private Warehouse() {}

    public static <T> JsonFileDataManager<T> json(@Nonnull File file, @Nullable Executor executor) {
        return new JsonFileDataManager<>(file, executor);
    }

    public static <T> JsonFileDataManager<T> json(@Nonnull File file) {
        return json(file, null);
    }

    public static <T> YamlFileDataManager<T> yaml(@Nonnull File file, @Nullable DumperOptions dumperOptions, @Nullable Executor executor) {
        return new YamlFileDataManager<>(file, dumperOptions, null);
    }

    public static <T> YamlFileDataManager<T> yaml(@Nonnull File file, @Nullable Executor executor) {
        return yaml(file, null, executor);
    }

    public static <T> YamlFileDataManager<T> yaml(@Nonnull File file) {
        return yaml(file, null);
    }

}
