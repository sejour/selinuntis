package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.NonNull;
import lombok.Value;

@Value
public class PlainClause implements PreSelectClause, PostSelectClause, StringExpressionClause {
    String expression;
    Keyword keyword;

    public PlainClause(@NonNull String expression) {
        this.expression = expression;
        keyword = Keyword.NONE;
    }

    public PlainClause(@NonNull Keyword keyword, @NonNull String expression) {
        this.expression = expression;
        this.keyword = keyword;
    }
}
