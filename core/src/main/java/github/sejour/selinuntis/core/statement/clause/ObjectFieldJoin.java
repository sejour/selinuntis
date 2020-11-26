package github.sejour.selinuntis.core.statement.clause;

import static java.lang.String.format;
import static java.lang.String.join;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ObjectFieldJoin extends Join {
    private final JoinType type;
    private final String parentObjectAlias;
    private final String fieldName;
    private final String alias;

    public ObjectFieldJoin(@NonNull JoinType type, @NonNull String objectField, String alias) {
        if (StringUtils.isBlank(objectField)) {
            throw new IllegalArgumentException("objectField must not be blank");
        }
        if (StringUtils.isBlank(alias)) {
            throw new IllegalArgumentException("alias must not be blank");
        }

        this.type = type;
        this.alias = alias;

        final var parsed = StringUtils.split(objectField, ".");
        if (parsed == null) {
            throw new IllegalArgumentException(format("invalid format of object field: %s", objectField));
        }

        switch (parsed.length) {
            case 1 -> {
                parentObjectAlias = "";
                fieldName = objectField;
            }
            case 2 -> {
                parentObjectAlias = parsed[0];
                fieldName = parsed[1];
            }
            default -> throw new IllegalArgumentException(format("invalid format of object field: %s",
                                                                 objectField));
        }
    }

    public String getCompletedKeyword() {
        return join(" ", type.getPrefix(), getKeyword().getClause());
    }
}
