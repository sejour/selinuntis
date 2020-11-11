package github.sejour.selinutis.core.statement;

import java.util.function.Function;

import github.sejour.selinutis.core.StatementBuilder;
import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.command.Command;
import github.sejour.selinutis.core.statement.expression.OrderExpression;
import github.sejour.selinutis.core.statement.expression.WhereExpression;

public interface Query<T> {
    Query<T> preSelect(String query);
    Query<T> preSelect(Command command);
    Query<T> postSelect(String query);
    Query<T> postSelect(Command command);
    Query<T> with(String expression);
    Query<T> distinct();
    Query<T> select(String... fields);
    Query<T> count();
    Query<T> innerJoin(String fieldName, String alias);
    Query<T> innerJoinFetch(String fieldName, String alias, String... fetchFields);
    Query<T> leftOuterJoin(String fieldName, String alias);
    Query<T> leftOuterJoinFetch(String fieldName, String alias, String... fetchFields);
    Query<T> rightOuterJoin(String fieldName, String alias);
    Query<T> rightOuterJoinFetch(String fieldName, String alias, String... fetchFields);
    Query<T> where(String expression);
    Query<T> where(WhereExpression expression);
    Query<T> groupBy(String expression);
    Query<T> having(String expression);
    Query<T> having(WhereExpression expression);
    Query<T> orderBy(String expression);
    Query<T> orderBy(OrderExpression expression);
    Query<T> limit(Long limit);

    default Query<T> query(Function<Query<T>, Query<T>> f) {
        return f.apply(this);
    }

    String build(StatementBuilder builder) throws StatementBuildException;
}
