package github.sejour.selinuntis.core.statement.clause;

import static github.sejour.selinuntis.core.statement.clause.TableObjectSupport.validateAlias;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromTableName extends From {
    private final String tableName;
    private final Class<?> resultType;
    private final String alias;

    public FromTableName(@NonNull String tableName,
                         @NonNull Class<?> resultType,
                         @NonNull String alias) {
        this.tableName = tableName;
        this.resultType = resultType;
        this.alias = validateAlias(alias);
    }
}
