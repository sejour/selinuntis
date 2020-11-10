package github.sejour.selinutis.core.statement;

import org.apache.commons.lang3.NotImplementedException;

public class Select {
    public static <T> Query<T> from(Class<T> resultType, String... columns) {
        // TODO: implement
        throw new NotImplementedException();
    }

    public static <T, S> Query<T> from(Query<S> subQuery, Class<T> resultType, String... columns) {
        // TODO: implement
        throw new NotImplementedException();
    }

    public static <T> Query<Long> count(Class<T> tableClass) {
        // TODO: implement
        throw new NotImplementedException();
    }

    public static <T> Query<Boolean> exist(Class<T> tableClass) {
        // TODO: implement
        throw new NotImplementedException();
    }
}
