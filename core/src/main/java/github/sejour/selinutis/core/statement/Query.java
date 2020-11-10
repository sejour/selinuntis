package github.sejour.selinutis.core.statement;

import java.util.function.Function;

import github.sejour.selinutis.core.statement.expression.Order;

public interface Query<T> {
    // TODO: Query<T> where(Condition condition);
    Query<T> where(String expression);
    Query<T> orderBy(Order order);
    Query<T> orderBy(String expression);
    Query<T> limit(Long limit);

    default Query<T> query(Function<Query<T>, Query<T>> f) {
        return f.apply(this);
    }

    String build();
}
