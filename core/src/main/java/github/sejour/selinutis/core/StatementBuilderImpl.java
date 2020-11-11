package github.sejour.selinutis.core;

import static java.lang.String.format;

import java.util.Map;

import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.Query;
import github.sejour.selinutis.core.statement.QueryImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatementBuilderImpl implements StatementBuilder {
    private final Map<Class<?>, TableInfo> tableInfoMap;

    @Override
    public String buildSelect(@NonNull Query<?> query) throws StatementBuildException {
        if (!(query instanceof QueryImpl<?>)) {
            throw new StatementBuildException(format("not supported query implementation: %s",
                                                     query.getClass().getName()));
        }

        final var queryImpl = (QueryImpl<?>) query;

        // TODO: impl
        return "";
    }
}
