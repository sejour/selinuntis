package github.sejour.selinutis.core;

import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.Query;

public interface StatementBuilder {
    String buildSelect(Query<?> query) throws StatementBuildException;
    // TODO: buildUpdate
    // TODO: buildDelete
    // TODO: buildInsert
}
