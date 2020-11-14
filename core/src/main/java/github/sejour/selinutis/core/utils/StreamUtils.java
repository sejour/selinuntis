package github.sejour.selinutis.core.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamUtils {
    public static <T> Stream<T> safeStream(Collection<T> collection) {
        return Optional.ofNullable(collection)
                       .orElse(Collections.emptyList())
                       .stream();
    }
}
