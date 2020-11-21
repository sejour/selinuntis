package github.sejour.selinutis.core.statement.clause;

import static java.lang.String.join;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.NonNull;
import lombok.Value;

@Value
public class GroupBy implements PostSelectClause, StringExpressionClause,
                                UniqueKeywordMergeableClause<GroupBy> {
    String expression;

    public GroupBy(@NonNull String expression) {
        this.expression = expression;
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.GROUP_BY;
    }

    @Override
    public GroupBy combine(GroupBy other) {
        if (other == null) {
            return this;
        }
        return new GroupBy(join(",", expression, other.expression));
    }
}
