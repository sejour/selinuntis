package github.sejour.selinuntis.core.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    @SafeVarargs
    public static <T> List<T> excludeNullValues(@NonNull T... values) {
        return Arrays.stream(values)
                     .filter(Objects::nonNull)
                     .collect(Collectors.toList());
    }

}
