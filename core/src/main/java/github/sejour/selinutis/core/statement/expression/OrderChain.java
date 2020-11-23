package github.sejour.selinutis.core.statement.expression;

import static java.lang.String.format;
import static java.lang.String.join;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderChain implements OrderExpression {
    private static final String ASC_FORMAT = "%s ASC";
    private static final String DESC_FORMAT = "%s DESC";

    @Getter
    final String expression;

    public static OrderChain create() {
        return new OrderChain(null);
    }

    public OrderChain order(String expression) {
        return Optional.ofNullable(expression)
                       .flatMap(exp -> Optional
                               .ofNullable(this.expression)
                               .map(self -> new OrderChain(join(",", self, exp))))
                       .orElse(this);
    }

    public OrderChain order(@NonNull Supplier<Boolean> predicate,
                            @NonNull Supplier<String> supplier) {
        if (predicate.get()) {
            return Optional.ofNullable(supplier.get())
                           .map(this::order)
                           .orElse(this);
        }
        return this;
    }

    public OrderChain asc(String field) {
        return Optional.ofNullable(field)
                       .map(f -> order(format(ASC_FORMAT, f)))
                       .orElse(this);
    }

    public OrderChain asc(@NonNull Supplier<Boolean> predicate,
                          @NonNull Supplier<String> fieldSupplier) {
        if (predicate.get()) {
            return Optional.ofNullable(fieldSupplier.get())
                           .map(this::asc)
                           .orElse(this);
        }
        return this;
    }

    public OrderChain desc(String field) {
        return Optional.ofNullable(field)
                       .map(f -> order(format(DESC_FORMAT, f)))
                       .orElse(this);
    }

    public OrderChain desc(@NonNull Supplier<Boolean> predicate,
                           @NonNull Supplier<String> fieldSupplier) {
        if (predicate.get()) {
            return Optional.ofNullable(fieldSupplier.get())
                           .map(this::desc)
                           .orElse(this);
        }
        return this;
    }

    public OrderChain order(Function<OrderChain, OrderChain> order) {
        return Optional.ofNullable(order)
                       .flatMap(o -> Optional.ofNullable(o.apply(this)))
                       .orElse(this);
    }

    public OrderChain order(@NonNull Supplier<Boolean> predicate,
                            Function<OrderChain, OrderChain> order) {
        if (predicate.get()) {
            return this.order(order);
        }
        return this;
    }
}
