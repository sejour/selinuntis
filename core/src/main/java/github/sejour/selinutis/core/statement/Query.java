package github.sejour.selinutis.core.statement;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import github.sejour.selinutis.core.error.QueryBuildException;

public interface Query<T> {
    Query<T> where(String expression);
    Query<T> orderBy(String expression);
    Query<T> limit(Long limit);
    String build() throws QueryBuildException;

    default Query<T> query(Function<Query<T>, Query<T>> f) {
        return f.apply(this);
    }

    default Query<T> OrderBy(OrderField... fields) throws QueryBuildException {
        return orderBy(Arrays.stream(fields).map(OrderField::toString).collect(Collectors.joining(",")));
    }
}
