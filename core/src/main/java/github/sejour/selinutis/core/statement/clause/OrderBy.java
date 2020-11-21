package github.sejour.selinutis.core.statement.clause;

import static java.lang.String.join;

import github.sejour.selinutis.core.statement.Keyword;
import github.sejour.selinutis.core.statement.expression.OrderExpression;

import lombok.NonNull;
import lombok.Value;

@Value
public class OrderBy implements PostSelectClause, StringExpressionClause,
                                UniqueKeywordMergeableClause<OrderBy> {
    String expression;

    public OrderBy(@NonNull String expression) {
        this.expression = expression;
    }

    public OrderBy(@NonNull OrderExpression expression) {
        this.expression = expression.getExpression();
    }

    @Override
    public OrderBy combine(OrderBy other) {
        if (other == null) {
            return this;
        }
        return new OrderBy(join(",", expression, other.expression));
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.ORDER_BY;
    }
}
