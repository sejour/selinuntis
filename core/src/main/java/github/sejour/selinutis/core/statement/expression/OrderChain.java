package github.sejour.selinutis.core.statement.expression;

import static java.lang.String.join;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OrderChain implements OrderExpression {
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    @Getter
    final String expression;

    public OrderChain order(String expression) {
        return Optional.ofNullable(expression)
                       .map(exp -> Optional
                               .ofNullable(this.expression)
                               .map(self -> new OrderChain(join(",", self, exp)))
                               .orElseGet(() -> new OrderChain(exp)))
                       .orElse(this);
    }

    public OrderChain order(boolean condition, String expression) {
        if (condition) {
            return order(expression);
        }
        return this;
    }

    public OrderChain order(OrderChain chain) {
        return Optional.ofNullable(chain)
                       .map(ch -> order(ch.expression))
                       .orElse(this);
    }

    public OrderChain order(boolean condition, OrderChain chain) {
        if (condition) {
            return order(chain);
        }
        return this;
    }

    private OrderChain orderField(String field, String direction) {
        return Optional.ofNullable(field)
                       .map(f -> order(String.join(" ", f, direction)))
                       .orElse(this);
    }

    private OrderChain orderField(boolean condition, String field, String direction) {
        if (condition) {
            return orderField(field, direction);
        }
        return this;
    }

    public OrderChain asc(String field) {
        return orderField(field, ASC);
    }

    public OrderChain asc(boolean condition, String field) {
        return orderField(condition, field, ASC);
    }

    public OrderChain desc(String field) {
        return orderField(field, DESC);
    }

    public OrderChain desc(boolean condition, String field) {
        return orderField(condition, field, DESC);
    }
}
