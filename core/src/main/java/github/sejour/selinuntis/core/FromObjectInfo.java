package github.sejour.selinuntis.core;

import github.sejour.selinuntis.core.statement.clause.Keyword;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromObjectInfo extends AbstractObjectInfo {
    private final String alias;
    private final ResultTypeInfo info;
    private final String clause;

    public FromObjectInfo(@NonNull String alias,
                          @NonNull TableInfo info) {
        this.alias = alias;
        this.info = info;
        clause = String.join(" ", Keyword.FROM.getClause(), info.getTableName());
    }
}
