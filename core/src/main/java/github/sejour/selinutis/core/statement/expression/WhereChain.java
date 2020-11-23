package github.sejour.selinutis.core.statement.expression;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WhereChain {
    String expression;

    public static WhereChain create() {
        return new WhereChain(null);
    }

    public WhereChain where(Function<WhereChain, WhereChain> where) {
        return Optional.ofNullable(where)
                       .flatMap(ch -> Optional.ofNullable(ch.apply(this)))
                       .orElse(this);
    }

    private WhereChain combine(String expression, String delimiter) {
        return Optional.ofNullable(expression)
                       .map(ExpressionUtils::factor)
                       .map(exp -> Optional
                               .ofNullable(this.expression)
                               .map(self -> new WhereChain(String.join(delimiter, self, exp)))
                               .orElseGet(() -> new WhereChain(exp)))
                       .orElse(this);
    }

    public WhereChain and(String expression) {
        return combine(expression, " AND ");
    }

    public WhereChain and(@NonNull Supplier<Boolean> predicate,
                          @NonNull Supplier<String> supplier) {
        if (predicate.get()) {
            return Optional.ofNullable(supplier.get())
                           .map(this::and)
                           .orElse(null);
        }
        return this;
    }

    public WhereChain and(Function<WhereChain, WhereChain> where) {
        return Optional.ofNullable(where)
                       .map(wh -> and(Optional.ofNullable(wh.apply(create()))
                                              .map(WhereChain::getExpression)
                                              .orElse(null)))
                       .orElse(this);
    }

    public WhereChain and(@NonNull Supplier<Boolean> predicate,
                          Function<WhereChain, WhereChain> where) {
        if (predicate.get()) {
            return and(where);
        }
        return this;
    }

    public WhereChain or(String expression) {
        return combine(expression, " OR ");
    }

    public WhereChain or(@NonNull Supplier<Boolean> predicate,
                          @NonNull Supplier<String> supplier) {
        if (predicate.get()) {
            return Optional.ofNullable(supplier.get())
                           .map(this::or)
                           .orElse(null);
        }
        return this;
    }

    public WhereChain or(Function<WhereChain, WhereChain> where) {
        return Optional.ofNullable(where)
                       .map(wh -> or(Optional.ofNullable(wh.apply(create()))
                                              .map(WhereChain::getExpression)
                                              .orElse(null)))
                       .orElse(this);
    }

    public WhereChain or(@NonNull Supplier<Boolean> predicate,
                          Function<WhereChain, WhereChain> where) {
        if (predicate.get()) {
            return or(where);
        }
        return this;
    }
}
