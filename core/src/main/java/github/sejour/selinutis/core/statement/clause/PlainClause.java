package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlainClause implements Clause {
    String expression;
    Keyword keyword;
}
