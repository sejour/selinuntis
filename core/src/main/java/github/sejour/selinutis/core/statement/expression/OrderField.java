package github.sejour.selinutis.core.statement.expression;

import static java.lang.String.format;
import static java.lang.String.join;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderField implements OrderExpression {
    private static final String ASC_FORMAT = "%s ASC";
    private static final String DESC_FORMAT = "%s DESC";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class By {
        public static OrderField asc(String field) {
            return new OrderField(format(ASC_FORMAT, field));
        }

        public static OrderField desc(String field) {
            return new OrderField(format(DESC_FORMAT, field));
        }
    }

    @Getter
    final String expression;

    public OrderField asc(String field) {
        if (expression == null) {
            return new OrderField(format(ASC_FORMAT, field));
        }
        return new OrderField(join(",", expression, format(ASC_FORMAT, field)));
    }

    public OrderField desc(String field) {
        if (expression == null) {
            return new OrderField(format(DESC_FORMAT, field));
        }
        return new OrderField(join(",", expression, format(DESC_FORMAT, field)));
    }

    public OrderField order(Function<OrderField, OrderField> f) {
        return f.apply(this);
    }

    public static OrderField by(Function<OrderField, OrderField> f) {
        return f.apply(new OrderField(null));
    }
}
