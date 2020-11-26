package github.sejour.selinuntis.core.statement.clause;

import lombok.NonNull;
import lombok.Value;

@Value
public class Where implements PostSelectClause, StringExpressionClause {
    String expression;

    public Where(@NonNull String expression) {
        this.expression = expression;
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.WHERE;
    }
}
