package github.sejour.selinuntis.core.statement.clause;

public abstract class From implements Clause, TableObject {
    public Keyword getKeyword() {
        return Keyword.FROM;
    }
}
