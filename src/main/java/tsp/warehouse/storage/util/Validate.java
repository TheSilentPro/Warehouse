package tsp.warehouse.storage.util;

public class Validate {

    public static <T> void notNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

}
