package github.sejour.selinutis.core.statement;

import github.sejour.selinutis.core.statement.clause.FromTableClass;

import lombok.NonNull;

public class Select {
    public static <T> Query<T> from(@NonNull Class<T> tableClass) {
        return from(tableClass, "");
    }

    public static <T> Query<T> from(@NonNull Class<T> tableClass, @NonNull String alias) {
        return QueryImpl.from(new FromTableClass(tableClass, alias));
    }
}
