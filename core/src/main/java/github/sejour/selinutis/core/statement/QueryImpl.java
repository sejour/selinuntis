package github.sejour.selinutis.core.statement;

import static java.util.Arrays.asList;

import java.util.List;

import github.sejour.selinutis.core.StatementBuilder;
import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.command.Command;
import github.sejour.selinutis.core.statement.command.CommandType;
import github.sejour.selinutis.core.statement.command.From;
import github.sejour.selinutis.core.statement.command.GeneralCommand;
import github.sejour.selinutis.core.statement.command.Join;
import github.sejour.selinutis.core.statement.command.JoinType;
import github.sejour.selinutis.core.statement.expression.OrderExpression;
import github.sejour.selinutis.core.statement.expression.WhereExpression;
import github.sejour.selinutis.core.utils.ListUtils;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class QueryImpl<T> implements Query<T> {
    List<Command> preCommands;
    boolean distinct;
    List<String> selectFields;
    From from;
    List<Command> postCommands;
    Long limit;

    @Override
    public Query<T> preSelect(String query) {
        return preSelect(GeneralCommand.builder()
                                       .commandType(CommandType.PLAIN_TEXT)
                                       .expression(query)
                                       .build());
    }

    @Override
    public Query<T> preSelect(Command command) {
        return toBuilder()
                .preCommands(ListUtils.safeCopyAsImmutableList(preCommands, command))
                .build();
    }

    @Override
    public Query<T> postSelect(String query) {
        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.PLAIN_TEXT)
                                        .expression(query)
                                        .build());
    }

    @Override
    public Query<T> postSelect(Command command) {
        return toBuilder()
                .postCommands(ListUtils.safeCopyAsImmutableList(preCommands, command))
                .build();
    }

    @Override
    public Query<T> with(String expression) {
        return preSelect(GeneralCommand.builder()
                                       .commandType(CommandType.WITH)
                                       .expression(expression)
                                       .build());
    }

    @Override
    public Query<T> distinct() {
        return toBuilder()
                .distinct(true)
                .build();
    }

    @Override
    public Query<T> select(@NonNull String... fields) {
        return toBuilder()
                .selectFields(ListUtils.safeCopyAsImmutableList(selectFields, fields))
                .build();
    }

    @Override
    public Query<T> count() {
        return select("COUNT(*)");
    }

    @Override
    public Query<T> innerJoin(@NonNull String fieldName, @NonNull String alias) {
        return postSelect(Join.builder()
                              .type(JoinType.INNER)
                              .fieldName(fieldName)
                              .alias(alias)
                              .fetch(false)
                              .build());
    }

    @Override
    public Query<T> innerJoinFetch(@NonNull String fieldName, @NonNull String alias,
                                   @NonNull String... fetchFields) {
        return postSelect(Join.builder()
                              .type(JoinType.INNER)
                              .fieldName(fieldName)
                              .alias(alias)
                              .fetch(true)
                              .fetchFields(asList(fetchFields))
                              .build());
    }

    @Override
    public Query<T> leftOuterJoin(@NonNull String fieldName, @NonNull String alias) {
        return postSelect(Join.builder()
                              .type(JoinType.LEFT_OUTER)
                              .fieldName(fieldName)
                              .alias(alias)
                              .fetch(false)
                              .build());
    }

    @Override
    public Query<T> leftOuterJoinFetch(@NonNull String fieldName, @NonNull String alias,
                                       @NonNull String... fetchFields) {
        return postSelect(Join.builder()
                              .type(JoinType.LEFT_OUTER)
                              .fieldName(fieldName)
                              .alias(alias)
                              .fetch(true)
                              .fetchFields(asList(fetchFields))
                              .build());
    }

    @Override
    public Query<T> rightOuterJoin(@NonNull String fieldName, @NonNull String alias) {
        return postSelect(Join.builder()
                              .type(JoinType.RIGHT_OUTER)
                              .fieldName(fieldName)
                              .alias(alias)
                              .fetch(false)
                              .build());
    }

    @Override
    public Query<T> rightOuterJoinFetch(@NonNull String fieldName, @NonNull String alias,
                                        @NonNull String... fetchFields) {
        return postSelect(Join.builder()
                              .type(JoinType.RIGHT_OUTER)
                              .fieldName(fieldName)
                              .alias(alias)
                              .fetch(true)
                              .fetchFields(asList(fetchFields))
                              .build());
    }

    @Override
    public Query<T> where(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.WHERE)
                                        .expression(expression)
                                        .build());
    }

    @Override
    public Query<T> where(WhereExpression expression) {
        if (expression == null || expression.getExpression() == null) {
            return this;
        }

        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.WHERE)
                                        .expression(expression.getExpression())
                                        .build());
    }

    @Override
    public Query<T> groupBy(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.ORDER_BY)
                                        .expression(expression)
                                        .build());
    }

    @Override
    public Query<T> having(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.HAVING)
                                        .expression(expression)
                                        .build());
    }

    @Override
    public Query<T> having(WhereExpression expression) {
        if (expression == null || expression.getExpression() == null) {
            return this;
        }

        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.HAVING)
                                        .expression(expression.getExpression())
                                        .build());
    }

    @Override
    public Query<T> orderBy(String expression) {
        if (expression == null) {
            return this;
        }

        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.ORDER_BY)
                                        .expression(expression)
                                        .build());
    }

    @Override
    public Query<T> orderBy(OrderExpression expression) {
        if (expression == null || expression.getExpression() == null) {
            return this;
        }

        return postSelect(GeneralCommand.builder()
                                        .commandType(CommandType.WHERE)
                                        .expression(expression.getExpression())
                                        .build());
    }

    @Override
    public Query<T> limit(Long size) {
        return toBuilder()
                .limit(size)
                .build();
    }

    @Override
    public String build(StatementBuilder builder) throws StatementBuildException {
        return builder.buildSelect(this);
    }
}
