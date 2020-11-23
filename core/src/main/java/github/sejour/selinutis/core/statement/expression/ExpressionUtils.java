package github.sejour.selinutis.core.statement.expression;

import static java.lang.String.format;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

import github.sejour.selinutis.core.utils.CollectionUtils;

import lombok.NonNull;

public class ExpressionUtils {
    public static String factor(@NonNull String expression) {
        return format("(%s)", expression);
    }

    public static String option(@NonNull Supplier<Boolean> predicate,
                                Supplier<String> supplier) {
        if (predicate.get()) {
            return Optional.ofNullable(supplier)
                           .map(Supplier::get)
                           .orElse(null);
        }
        return null;
    }

    public static String values(@NonNull String... values) {
       return excludeNullValues(values)
               .map(stream -> stream.collect(Collectors.joining(",")))
               .orElse(null);
    }

    public static String in(@NonNull String target, @NonNull String... values) {
        return excludeNullValues(values)
                .map(stream -> format("%s IN %s",
                                      target,
                                      stream.collect(Collectors.joining(","))))
                .orElse(null);
    }

    public static String field(@NonNull String target, @NonNull String... values) {
        return excludeNullValues(values)
                .map(stream -> Streams.concat(Stream.of(target), stream)
                                      .collect(Collectors.joining(",")))
                .orElse(null);
    }

    public static String between(@NonNull String target, @NonNull String start, @NonNull String end) {
        return format("%s BETWEEN %s AND %s", target, start, end);
    }

    public static String and(@NonNull String... conditions) {
        return excludeNullValues(conditions)
                .map(stream -> stream
                        .map(cnd -> format("%s", factor(cnd)))
                        .collect(Collectors.joining(" AND ")))
                .orElse(null);
    }

    public static String or(@NonNull String... conditions) {
        return excludeNullValues(conditions)
                .map(stream -> stream
                        .map(cnd -> format("%s", factor(cnd)))
                        .collect(Collectors.joining(" OR ")))
                .orElse(null);
    }

    @SafeVarargs
    private static <T> Optional<Stream<T>> excludeNullValues(T... values) {
        final var excluded = CollectionUtils.excludeNullValues(values);
        if (excluded.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(excluded.stream());
    }
}
