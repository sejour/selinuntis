package github.sejour.selinuntis.core.statement.chain;

import github.sejour.selinuntis.core.statement.clause.FromTableClass;
import github.sejour.selinuntis.core.statement.clause.FromFetchTableClass;
import github.sejour.selinuntis.core.statement.clause.FromTableName;

import lombok.NonNull;

public class Select {
    public static <T> Query<T> from(@NonNull Class<T> tableClass) {
        return from(tableClass, "");
    }

    public static <T> Query<T> from(@NonNull Class<T> tableClass, @NonNull String alias) {
        return QueryImpl.from(new FromTableClass(tableClass, alias));
    }

    public static <T> Query<T> fromFetch(@NonNull Class<T> tableClass, @NonNull String... fetchColumns) {
        return fromFetchWithAlias(tableClass, "", fetchColumns);
    }

    public static <T> Query<T> fromFetchWithAlias(@NonNull Class<T> tableClass, @NonNull String alias,
                                                  @NonNull String... fetchColumns) {
        return QueryImpl.from(new FromFetchTableClass(tableClass, alias, fetchColumns));
    }

    public static <T> Query<T> from(@NonNull String tableName,
                                    @NonNull Class<T> resultType) {
        return from(tableName, resultType, "");
    }

    public static <T> Query<T> from(@NonNull String tableName,
                                    @NonNull Class<T> resultType,
                                    @NonNull String alias) {
        return QueryImpl.from(new FromTableName(tableName, resultType, alias));
    }
}
