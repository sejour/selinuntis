package github.sejour.selinuntis.core.statement.clause;

import java.util.Set;

public interface FetchTableObject extends TableObject {
    Set<String> getFetchColumns();
}
