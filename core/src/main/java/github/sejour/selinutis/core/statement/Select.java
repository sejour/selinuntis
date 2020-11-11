package github.sejour.selinutis.core.statement;

import github.sejour.selinutis.core.statement.command.FromTableClass;

public class Select {
    public static <T> Query<T> from(Class<T> tableClass) {
        return from(tableClass, null);
    }

    public static <T> Query<T> from(Class<T> tableClass, String alias) {
        return QueryImpl
                .<T>builder()
                .from(new FromTableClass(tableClass, alias))
                .build();
    }
}
