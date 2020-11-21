package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;
import github.sejour.selinutis.core.statement.expression.ExpressionUtils;
import github.sejour.selinutis.core.statement.expression.WhereExpression;

import lombok.NonNull;
import lombok.Value;

@Value
public class Where implements PostSelectClause, StringExpressionClause, UniqueKeywordMergeableClause<Where> {
    String expression;

    public Where(@NonNull String expression) {
        this.expression = expression;
    }

    public Where(@NonNull WhereExpression expression) {
        this.expression = expression.getExpression();
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.WHERE;
    }

    @Override
    public Where combine(Where other) {
        if (other == null) {
            return this;
        }
        return new Where(ExpressionUtils.and(expression, other.expression));
    }
}
