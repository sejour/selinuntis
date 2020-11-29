package github.sejour.selinuntis.core.statement.clause;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromFetchTableClass extends FromTableClass implements FetchTableObject {
    private final Set<String> fetchColumns;

    public FromFetchTableClass(@NonNull Class<?> tableClass, @NonNull String alias,
                               @NonNull String... fetchColumns) {
        super(tableClass, alias);
        this.fetchColumns = ImmutableSet.copyOf(fetchColumns);
    }
}
