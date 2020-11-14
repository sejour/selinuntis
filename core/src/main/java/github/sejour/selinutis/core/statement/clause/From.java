package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;

public abstract class From implements Clause {
    public Keyword getKeyword() {
        return Keyword.FROM;
    }
}
