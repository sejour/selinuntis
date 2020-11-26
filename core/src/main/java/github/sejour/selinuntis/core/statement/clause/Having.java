package github.sejour.selinuntis.core.statement.clause;

import lombok.NonNull;
import lombok.Value;

@Value
public class Having implements PostSelectClause, StringExpressionClause {
    String expression;

    public Having(@NonNull String expression) {
        this.expression = expression;
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.HAVING;
    }
}
