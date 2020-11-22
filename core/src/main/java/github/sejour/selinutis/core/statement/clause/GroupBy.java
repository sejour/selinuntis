package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.NonNull;
import lombok.Value;

@Value
public class GroupBy implements PostSelectClause, StringExpressionClause {
    String expression;

    public GroupBy(@NonNull String expression) {
        this.expression = expression;
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.GROUP_BY;
    }
}
