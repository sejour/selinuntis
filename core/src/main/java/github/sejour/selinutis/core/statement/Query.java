package github.sejour.selinutis.core.statement;

import java.util.function.Function;

import github.sejour.selinutis.core.StatementBuilder;
import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.clause.Join;
import github.sejour.selinutis.core.statement.clause.PostSelectClause;
import github.sejour.selinutis.core.statement.clause.PreSelectClause;
import github.sejour.selinutis.core.statement.expression.OrderExpression;
import github.sejour.selinutis.core.statement.expression.WhereExpression;

public interface Query<T> {
    Query<T> preSelect(PreSelectClause clause);
    Query<T> preSelect(String clause);
    Query<T> postSelect(PostSelectClause clause);
    Query<T> postSelect(String clause);
    Query<T> distinct();
    Query<T> select(String... fields);
    Query<T> count();
    Query<T> join(Join join);
    Query<T> innerJoin(String objectField, String alias);
    Query<T> innerJoinFetch(String objectField, String alias, String... fetchColumns);
    Query<T> leftOuterJoin(String objectField, String alias);
    Query<T> leftOuterJoinFetch(String objectField, String alias, String... fetchColumns);
    Query<T> rightOuterJoin(String objectField, String alias);
    Query<T> rightOuterJoinFetch(String objectField, String alias, String... fetchColumns);
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

    Statement build(StatementBuilder builder) throws StatementBuildException;
}
