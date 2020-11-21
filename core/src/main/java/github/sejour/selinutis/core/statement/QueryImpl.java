package github.sejour.selinutis.core.statement;

import static java.lang.String.format;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import github.sejour.selinutis.core.StatementBuilder;
import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.clause.Clause;
import github.sejour.selinutis.core.statement.clause.From;
import github.sejour.selinutis.core.statement.clause.GroupBy;
import github.sejour.selinutis.core.statement.clause.Having;
import github.sejour.selinutis.core.statement.clause.Join;
import github.sejour.selinutis.core.statement.clause.JoinType;
import github.sejour.selinutis.core.statement.clause.Limit;
import github.sejour.selinutis.core.statement.clause.ObjectFieldJoin;
import github.sejour.selinutis.core.statement.clause.ObjectFieldJoinFetch;
import github.sejour.selinutis.core.statement.clause.OrderBy;
import github.sejour.selinutis.core.statement.clause.PlainClause;
import github.sejour.selinutis.core.statement.clause.PostSelectClause;
import github.sejour.selinutis.core.statement.clause.PreSelectClause;
import github.sejour.selinutis.core.statement.clause.TableObject;
import github.sejour.selinutis.core.statement.clause.Where;
import github.sejour.selinutis.core.statement.expression.OrderExpression;
import github.sejour.selinutis.core.statement.expression.WhereExpression;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class QueryImpl<T> implements Query<T> {
    Map<String, TableObject> tableObjectMap;
    String fromObjectAlias;
    List<Clause> preClauses;
    boolean distinct;
    List<String> selectFields;
    List<Clause> postClauses;

    public static <T> QueryImpl<T> from(@NonNull From tableClass) {
        return QueryImpl
                .<T>builder()
                .tableObjectMap(ImmutableMap.<String, TableObject>builder()
                                        .put(tableClass.getAlias(), tableClass)
                                        .build())
                .fromObjectAlias(tableClass.getAlias())
                .preClauses(Collections.emptyList())
                .distinct(false)
                .selectFields(Collections.emptyList())
                .postClauses(ImmutableList.<Clause>builder()
                                     .add(tableClass)
                                     .build())
                .build();
    }

    public Query<T> preSelect(PreSelectClause clause) {
        if (clause == null) {
            return this;
        }

        return toBuilder()
                .preClauses(ImmutableList.<Clause>builder()
                                    .addAll(preClauses)
                                    .add(clause)
                                    .build())
                .build();
    }

    public Query<T> postSelect(PostSelectClause clause) {
        if (clause == null) {
            return this;
        }

        return toBuilder()
                .postClauses(ImmutableList.<Clause>builder()
                                     .addAll(postClauses)
                                     .add(clause)
                                     .build())
                .build();
    }

    public Query<T> join(Join join) {
        if (join == null) {
            return this;
        }
        if (tableObjectMap.containsKey(join.getAlias())) {
            throw new IllegalArgumentException(format("duplicate alias of join field: %s",
                                                      join.getAlias()));
        }

        return toBuilder()
                .postClauses(ImmutableList.<Clause>builder()
                                     .addAll(postClauses)
                                     .add(join)
                                     .build())
                .tableObjectMap(ImmutableMap.<String, TableObject>builder()
                                        .putAll(tableObjectMap)
                                        .put(join.getAlias(), join)
                                        .build())
                .build();
    }

    @Override
    public Query<T> preSelect(String clause) {
        if (clause == null) {
            return this;
        }

        return preSelect(new PlainClause(clause));
    }

    @Override
    public Query<T> postSelect(String clause) {
        if (clause == null) {
            return this;
        }

        return postSelect(new PlainClause(clause));
    }

    @Override
    public Query<T> distinct() {
        return toBuilder()
                .distinct(true)
                .build();
    }

    @Override
    public Query<T> select(String... fields) {
        if (fields == null) {
            return this;
        }

        return toBuilder()
                .selectFields(ImmutableList.<String>builder()
                                      .addAll(selectFields)
                                      .add(fields)
                                      .build())
                .build();
    }

    @Override
    public Query<T> count() {
        return select("COUNT(*)");
    }

    @Override
    public Query<T> innerJoin(@NonNull String objectField, @NonNull String alias) {
        return join(new ObjectFieldJoin(JoinType.INNER, objectField, alias));
    }

    @Override
    public Query<T> innerJoinFetch(@NonNull String objectField, @NonNull String alias,
                                   @NonNull String... fetchColumns) {
        return join(new ObjectFieldJoinFetch(JoinType.INNER, objectField, alias, fetchColumns));
    }

    @Override
    public Query<T> leftOuterJoin(@NonNull String objectField, @NonNull String alias) {
        return join(new ObjectFieldJoin(JoinType.LEFT_OUTER, objectField, alias));
    }

    @Override
    public Query<T> leftOuterJoinFetch(@NonNull String objectField, @NonNull String alias,
                                       @NonNull String... fetchColumns) {
        return join(new ObjectFieldJoinFetch(JoinType.LEFT_OUTER, objectField, alias, fetchColumns));
    }

    @Override
    public Query<T> rightOuterJoin(@NonNull String objectField, @NonNull String alias) {
        return join(new ObjectFieldJoin(JoinType.RIGHT_OUTER, objectField, alias));
    }

    @Override
    public Query<T> rightOuterJoinFetch(@NonNull String objectField, @NonNull String alias,
                                        @NonNull String... fetchColumns) {
        return join(new ObjectFieldJoinFetch(JoinType.RIGHT_OUTER, objectField, alias, fetchColumns));
    }

    @Override
    public Query<T> where(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(new Where(expression));
    }

    @Override
    public Query<T> where(WhereExpression expression) {
        if (expression == null || expression.getExpression() == null) {
            return this;
        }

        return postSelect(new Where(expression));
    }

    @Override
    public Query<T> groupBy(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(new GroupBy(expression));
    }

    @Override
    public Query<T> having(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(new Having(expression));
    }

    @Override
    public Query<T> having(WhereExpression expression) {
        if (expression == null || expression.getExpression() == null) {
            return this;
        }

        return postSelect(new Having(expression));
    }

    @Override
    public Query<T> orderBy(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(new OrderBy(expression));
    }

    @Override
    public Query<T> orderBy(OrderExpression expression) {
        if (expression == null || expression.getExpression() == null) {
            return this;
        }

        return postSelect(new OrderBy(expression));
    }

    @Override
    public Query<T> limit(Long size) {
        if (size == null) {
            return this;
        }

        return postSelect(new Limit(size));
    }

    @Override
    public Statement build(StatementBuilder builder) throws StatementBuildException {
        return builder.buildSelect(this);
    }
}
