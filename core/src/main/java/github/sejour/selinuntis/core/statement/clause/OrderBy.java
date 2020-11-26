package github.sejour.selinuntis.core.statement.clause;

import lombok.NonNull;
import lombok.Value;

@Value
public class OrderBy implements PostSelectClause, StringExpressionClause {
    String expression;

    public OrderBy(@NonNull String expression) {
        this.expression = expression;
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.ORDER_BY;
    }
}
