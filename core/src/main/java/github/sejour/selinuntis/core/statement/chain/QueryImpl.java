package github.sejour.selinuntis.core.statement.chain;

import static java.lang.String.format;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import github.sejour.selinuntis.core.error.StatementBuildException;
import github.sejour.selinuntis.core.statement.Statement;
import github.sejour.selinuntis.core.statement.StatementBuilder;
import github.sejour.selinuntis.core.statement.clause.Clause;
import github.sejour.selinuntis.core.statement.clause.From;
import github.sejour.selinuntis.core.statement.clause.GroupBy;
import github.sejour.selinuntis.core.statement.clause.Having;
import github.sejour.selinuntis.core.statement.clause.Join;
import github.sejour.selinuntis.core.statement.clause.JoinType;
import github.sejour.selinuntis.core.statement.clause.Limit;
import github.sejour.selinuntis.core.statement.clause.ObjectFieldJoin;
import github.sejour.selinuntis.core.statement.clause.ObjectFieldJoinFetch;
import github.sejour.selinuntis.core.statement.clause.Offset;
import github.sejour.selinuntis.core.statement.clause.OrderBy;
import github.sejour.selinuntis.core.statement.clause.PlainClause;
import github.sejour.selinuntis.core.statement.clause.PostSelectClause;
import github.sejour.selinuntis.core.statement.clause.PreSelectClause;
import github.sejour.selinuntis.core.statement.clause.TableObject;
import github.sejour.selinuntis.core.statement.clause.Where;
import github.sejour.selinuntis.core.utils.CollectionUtils;

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

    @Override
    public Query<T> preSelect(PreSelectClause clause) {
        return Optional.ofNullable(clause)
                       .map(one -> toBuilder()
                               .preClauses(ImmutableList.<Clause>builder()
                                                   .addAll(preClauses)
                                                   .add(one)
                                                   .build())
                               .build())
                       .orElse(this);
    }

    @Override
    public Query<T> postSelect(PostSelectClause clause) {
        return Optional.ofNullable(clause)
                       .map(one -> toBuilder()
                               .postClauses(ImmutableList.<Clause>builder()
                                                    .addAll(postClauses)
                                                    .add(one)
                                                    .build())
                               .build())
                       .orElse(this);
    }

    @Override
    public Query<T> join(Join join) {
        return Optional
                .ofNullable(join)
                .map(one -> {
                    if (tableObjectMap.containsKey(one.getAlias())) {
                        throw new IllegalArgumentException(format("duplicate alias of join field: %s",
                                                                  one.getAlias()));
                    }

                    return toBuilder()
                            .postClauses(ImmutableList.<Clause>builder()
                                                 .addAll(postClauses)
                                                 .add(one)
                                                 .build())
                            .tableObjectMap(ImmutableMap.<String, TableObject>builder()
                                                    .putAll(tableObjectMap)
                                                    .put(one.getAlias(), one)
                                                    .build())
                            .build();
                })
                .orElse(this);
    }

    @Override
    public Query<T> preSelect(String clause) {
        return Optional.ofNullable(clause)
                       .map(one -> preSelect(new PlainClause(one)))
                       .orElse(this);
    }

    @Override
    public Query<T> postSelect(String clause) {
        return Optional.ofNullable(clause)
                       .map(one -> postSelect(new PlainClause(one)))
                       .orElse(this);
    }

    @Override
    public Query<T> distinct() {
        return toBuilder()
                .distinct(true)
                .build();
    }

    @Override
    public Query<T> select(String... fields) {
        return toBuilder()
                .selectFields(ImmutableList.<String>builder()
                                      .addAll(selectFields)
                                      .addAll(CollectionUtils.excludeNullValues(fields))
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
        return Optional.ofNullable(expression)
                       .map(exp -> postSelect(new Where(expression)))
                       .orElse(this);
    }

    @Override
    public Query<T> where(ConditionChain chain) {
        return Optional.ofNullable(chain)
                       .flatMap(ch -> Optional.ofNullable(ch.getExpression()))
                       .map(exp -> postSelect(new Where(exp)))
                       .orElse(this);
    }

    @Override
    public Query<T> groupBy(String expression) {
        return Optional.ofNullable(expression)
                       .map(exp -> postSelect(new GroupBy(expression)))
                       .orElse(this);
    }

    @Override
    public Query<T> having(String expression) {
        return Optional.ofNullable(expression)
                       .map(exp -> postSelect(new Having(expression)))
                       .orElse(this);
    }

    @Override
    public Query<T> having(ConditionChain chain) {
        return Optional.ofNullable(chain)
                       .flatMap(ch -> Optional.ofNullable(ch.getExpression()))
                       .map(exp -> postSelect(new Having(exp)))
                       .orElse(this);
    }

    @Override
    public Query<T> orderBy(String expression) {
        return Optional.ofNullable(expression)
                       .map(exp -> postSelect(new OrderBy(expression)))
                       .orElse(this);
    }

    @Override
    public Query<T> orderBy(OrderChain chain) {
        return Optional.ofNullable(chain)
                       .flatMap(ch -> Optional.ofNullable(ch.getExpression()))
                       .map(exp -> postSelect(new OrderBy(exp)))
                       .orElse(this);
    }

    @Override
    public Query<T> limit(Long size) {
        return Optional.ofNullable(size)
                       .map(s -> postSelect(new Limit(size)))
                       .orElse(this);
    }

    @Override
    public Query<T> offset(Long offset) {
        return Optional.ofNullable(offset)
                       .map(o -> postSelect(new Offset(o)))
                       .orElse(this);
    }

    @Override
    public Statement build(StatementBuilder builder) throws StatementBuildException {
        return builder.buildSelect(this);
    }
}
