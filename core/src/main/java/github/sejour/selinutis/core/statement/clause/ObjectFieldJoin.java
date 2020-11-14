package github.sejour.selinutis.core.statement.clause;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.Value;

@Value
public class ObjectFieldJoin implements Join, TableObject {
    JoinType type;
    String parentObjectAlias;
    String fieldName;
    String alias;
    boolean fetch;
    List<String> fetchFields;

    public ObjectFieldJoin(JoinType type, String objectField, String alias, boolean fetch, String... fetchFields) {
        if (StringUtils.isBlank(objectField)) {
            throw new IllegalArgumentException("objectField must not be blank");
        }
        if (StringUtils.isBlank(alias)) {
            throw new IllegalArgumentException("alias must not be blank");
        }

        this.type = type;
        this.alias = alias;
        this.fetch = fetch;
        this.fetchFields = asList(fetchFields);

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

    @Override
    public Keyword getKeyword() {
        return Keyword.JOIN;
    }
}
