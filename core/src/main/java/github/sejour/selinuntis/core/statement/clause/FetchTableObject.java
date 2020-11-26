package github.sejour.selinuntis.core.statement.clause;

import java.util.List;

public interface FetchTableObject extends TableObject {
    List<String> getFetchColumns();
}
