package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.Value;

@Value
public class Offset implements PostSelectClause, StringExpressionClause {
    String expression;

    public Offset(long offset) {
        expression = String.valueOf(offset);
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.OFFSET;
    }
}
