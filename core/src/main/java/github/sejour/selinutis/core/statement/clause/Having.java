package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;
import github.sejour.selinutis.core.statement.expression.ExpressionUtils;
import github.sejour.selinutis.core.statement.expression.WhereExpression;

import lombok.NonNull;
import lombok.Value;

@Value
public class Having implements PostSelectClause, StringExpressionClause,
                               UniqueKeywordMergeableClause<Having> {
    String expression;

    public Having(@NonNull String expression) {
        this.expression = expression;
    }

    public Having(@NonNull WhereExpression expression) {
        this.expression = expression.getExpression();
    }

    @Override
    public Having combine(Having other) {
        if (other == null) {
            return this;
        }
        return new Having(ExpressionUtils.and(expression, other.expression));
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.HAVING;
    }
}
