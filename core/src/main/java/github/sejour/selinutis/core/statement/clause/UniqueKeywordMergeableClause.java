package github.sejour.selinutis.core.statement.clause;

public interface UniqueKeywordMergeableClause<T extends UniqueKeywordMergeableClause<?>> extends Clause {
    T combine(T other);
}
