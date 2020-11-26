package github.sejour.selinuntis.core.statement.clause;

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
