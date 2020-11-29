package github.sejour.selinuntis.core.statement.clause;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ObjectFieldJoinFetch extends ObjectFieldJoin implements FetchTableObject {
    private final Set<String> fetchColumns;

    public ObjectFieldJoinFetch(@NonNull JoinType type, @NonNull String objectField,
                                @NonNull String alias, @NonNull String... fetchColumns) {
        super(type, objectField, alias);
        this.fetchColumns = ImmutableSet.copyOf(fetchColumns);
    }
}
