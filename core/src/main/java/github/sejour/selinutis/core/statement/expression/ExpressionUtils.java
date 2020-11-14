package github.sejour.selinutis.core.statement.expression;

import static java.lang.String.format;
import static java.lang.String.join;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.NonNull;

public class ExpressionUtils {
    public static String factor(@NonNull String expression) {
        return format("(%s)", expression);
    }

    public static String in(@NonNull String... values) {
        return format("IN %s", factor(join(",", values)));
    }

    public static String between(@NonNull String start, @NonNull String end) {
        return format("BETWEEN %s AND %s", start, end);
    }

    public static String and(@NonNull String... conditions) {
        return Arrays.stream(conditions)
                     .filter(Objects::nonNull)
                     .map(cnd -> format("%s", factor(cnd)))
                     .collect(Collectors.joining(" AND "));
    }

    public static String or(@NonNull String... conditions) {
        return Arrays.stream(conditions)
                     .filter(Objects::nonNull)
                     .map(cnd -> format("%s", factor(cnd)))
                     .collect(Collectors.joining(" OR "));
    }
}
