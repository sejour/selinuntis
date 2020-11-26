package github.sejour.selinuntis.mapper;

import github.sejour.selinuntis.core.statement.chain.Query;

public interface Mapper {
    <T> QueryExecution<T> select(Query<T> query);
    <T, R> QueryExecution<R> select(Query<T> query, Class<R> resultType);
}
