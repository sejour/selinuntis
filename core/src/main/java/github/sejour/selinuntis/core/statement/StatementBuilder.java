package github.sejour.selinuntis.core.statement;

import github.sejour.selinuntis.core.error.StatementBuildException;
import github.sejour.selinuntis.core.statement.chain.Query;

public interface StatementBuilder {
    Statement buildSelect(Query<?> query) throws StatementBuildException;
    // TODO: buildUpdate
    // TODO: buildDelete
    // TODO: buildInsert
}
