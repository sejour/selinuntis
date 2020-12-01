package github.sejour.selinuntis.core.statement.clause;

import static java.lang.String.format;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

public interface TableObject {
    String FIELD_LABEL_SEPARATOR = "_";
    String getAlias();
}

@UtilityClass
class TableObjectSupport {
    public static String validateAlias(@NonNull String alias) {
        if (alias.contains(TableObject.FIELD_LABEL_SEPARATOR)) {
            throw new IllegalArgumentException(format("'%s' cannot be included in the alias",
                                                      TableObject.FIELD_LABEL_SEPARATOR));
        }
        return alias;
    }
}
