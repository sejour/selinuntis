package github.sejour.selinutis.core.statement.clause;

import github.sejour.selinutis.core.statement.Keyword;

public abstract class Join implements Clause, TableObject {
    public Keyword getKeyword() {
        return Keyword.JOIN;
    }
}
