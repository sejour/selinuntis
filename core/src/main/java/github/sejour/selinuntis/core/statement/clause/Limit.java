package github.sejour.selinuntis.core.statement.clause;

import lombok.Value;

@Value
public class Limit implements PostSelectClause, StringExpressionClause {
    String expression;

    public Limit(long limit) {
        expression = String.valueOf(limit);
    }

    @Override
    public Keyword getKeyword() {
        return Keyword.LIMIT;
    }
}
