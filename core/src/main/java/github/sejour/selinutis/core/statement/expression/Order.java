package github.sejour.selinutis.core.statement.expression;

import static java.lang.String.format;
import static java.lang.String.join;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Order implements Expression {
    private static final String ASC_FORMAT = "%s ASC";
    private static final String DESC_FORMAT = "%s DESC";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class By {
        public static Order asc(String field) {
            return new Order(format(ASC_FORMAT, field));
        }

        public static Order desc(String field) {
            return new Order(format(DESC_FORMAT, field));
        }
    }

    @Getter
    final String expression;

    public Order asc(String field) {
        if (expression == null) {
            return new Order(format(ASC_FORMAT, field));
        }
        return new Order(join(",", expression, format(ASC_FORMAT, field)));
    }

    public Order desc(String field) {
        if (expression == null) {
            return new Order(format(DESC_FORMAT, field));
        }
        return new Order(join(",", expression, format(DESC_FORMAT, field)));
    }

    public Order order(Function<Order, Order> f) {
        return f.apply(this);
    }

    public static Order by(Function<Order, Order> f) {
        return f.apply(new Order(null));
    }
}
