package tsp.warehouse.storage.file;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import tsp.warehouse.storage.util.Validate;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

/**
 * Json based storage.
 *
 * @param <T> Type
 */
public class JsonFileDataManager<T extends Collection<T>> extends FileDataManager<T> {

    private final Type type;
    private Gson gson;

    public JsonFileDataManager(@Nonnull File file, @Nonnull Class<T> type) {
        super(file);
        Validate.notNull(type, "Type can not be null!");

        this.type = TypeToken.get(type).getType();
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    @Override
    public Optional<Collection<T>> load() {
        try {
            return gson.fromJson(new FileReader(getFile()), type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean save(Collection<T> t) {
        try {
            gson.toJson(t, type, new JsonWriter(new FileWriter(getFile())));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public Gson getGson() {
        return gson;
    }

}