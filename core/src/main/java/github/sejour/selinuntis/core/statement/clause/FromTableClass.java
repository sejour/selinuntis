package github.sejour.selinuntis.core.statement.clause;

import static github.sejour.selinuntis.core.statement.clause.TableObjectSupport.validateAlias;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromTableClass extends From implements TableObject {
    private final Class<?> tableClass;
    private final String alias;

    public FromTableClass(@NonNull Class<?> tableClass, @NonNull String alias) {
        this.tableClass = tableClass;
        this.alias = validateAlias(alias);
    }
}
