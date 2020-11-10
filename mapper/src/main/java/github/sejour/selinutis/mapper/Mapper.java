package github.sejour.selinutis.mapper;

import github.sejour.selinutis.core.statement.Query;

public interface Mapper {
    <T> QueryExecution<T> select(Query<T> query);
}
