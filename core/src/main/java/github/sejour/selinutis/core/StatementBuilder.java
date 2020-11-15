package github.sejour.selinutis.core;

import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.Query;
import github.sejour.selinutis.core.statement.Statement;

public interface StatementBuilder {
    Statement buildSelect(Query<?> query) throws StatementBuildException;
    // TODO: buildUpdate
    // TODO: buildDelete
    // TODO: buildInsert
}
