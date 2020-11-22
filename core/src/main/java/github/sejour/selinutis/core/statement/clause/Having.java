package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;
import github.sejour.selinutis.core.statement.expression.WhereExpression;

import lombok.NonNull;
import lombok.Value;

@Value
public class Having implements PostSelectClause, StringExpressionClause {
    String expression;

    public Having(@NonNull String expression) {
        this.expression = expression;
    }

    public Having(@NonNull WhereExpression expression) {
        this.expression = expression.getExpression();
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.HAVING;
    }
}
