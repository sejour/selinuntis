package github.sejour.selinutis.core.statement;

import static github.sejour.selinutis.core.statement.expression.ExpressionUtils.clause;
import static java.lang.String.format;
import static java.lang.String.join;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import github.sejour.selinutis.core.statement.expression.Order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor(access = AccessLevel.MODULE)
@Builder(toBuilder = true)
class QueryImpl<T> implements Query<T> {
    private final String statement;
    private final String whereExpression;
    private final String orderByExpression;
    private final Long limit;

    QueryImpl(String statement) {
        this.statement = statement;
        whereExpression = null;
        orderByExpression = null;
        limit = null;
    }

    @Override
    public Query<T> where(String expression) {
        if (expression == null) {
            return this;
        }

        final var claused = clause(expression);
        return toBuilder()
                .whereExpression(Optional.ofNullable(whereExpression)
                                         .map(current -> join(" AND ", current, claused))
                                         .orElse(claused))
                .build();
    }

    @Override
    public Query<T> orderBy(Order order) {
        if (order == null || order.getExpression() == null) {
            return this;
        }

        final var expression = order.getExpression();
        return toBuilder()
                .orderByExpression(Optional.ofNullable(orderByExpression)
                                           .map(current -> join(",", current, expression))
                                           .orElse(expression))
                .build();
    }

    @Override
    public Query<T> orderBy(String expression) {
        if (expression == null) {
            return this;
        }

        return toBuilder()
                .orderByExpression(Optional.ofNullable(orderByExpression)
                                           .map(current -> join(",", current, expression))
                                           .orElse(expression))
                .build();
    }

    @Override
    public Query<T> limit(Long size) {
        return toBuilder()
                .limit(size)
                .build();
    }

    @Override
    public String build() {
        return Stream.of(Optional.ofNullable(statement),
                         Optional.ofNullable(whereExpression)
                                 .map(where -> format("WHERE %s", where)),
                         Optional.ofNullable(orderByExpression)
                                 .map(order -> format("ORDER BY %s", order)),
                         Optional.ofNullable(limit)
                                 .filter(lim -> lim > 0L)
                                 .map(lim -> format("LIMIT %s", lim)))
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .collect(Collectors.joining(" "));
    }
}
