package tsp.warehouse.storage.util;

public class WHValidate {

    public static <T> void notNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

}
