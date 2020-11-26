package github.sejour.selinuntis.core.statement.clause;

public abstract class Join implements Clause, TableObject {
    public Keyword getKeyword() {
        return Keyword.JOIN;
    }
}
