package github.sejour.selinutis.core.utils;

import java.util.Collection;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

}
