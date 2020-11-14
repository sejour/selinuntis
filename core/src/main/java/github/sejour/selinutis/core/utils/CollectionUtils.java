package github.sejour.selinutis.core.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> List<T> safeCopyAsImmutableList(@Nullable List<T> source, T... additionalData) {
        return ImmutableList.<T>builder()
                .addAll(Optional.ofNullable(source).orElse(Collections.emptyList()))
                .add(additionalData)
                .build();
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

}
