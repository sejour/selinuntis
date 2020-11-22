package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;
import github.sejour.selinutis.core.statement.expression.OrderExpression;

import lombok.NonNull;
import lombok.Value;

@Value
public class OrderBy implements PostSelectClause, StringExpressionClause {
    String expression;

    public OrderBy(@NonNull String expression) {
        this.expression = expression;
    }

    public OrderBy(@NonNull OrderExpression expression) {
        this.expression = expression.getExpression();
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.ORDER_BY;
    }
}
