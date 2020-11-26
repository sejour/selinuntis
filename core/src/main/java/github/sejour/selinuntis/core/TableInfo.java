package github.sejour.selinuntis.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class TableInfo {
    private final String tableName;
    private final Map<String, Field> columns; // key: column name of table
    private final Map<String, JoinField> joinFields; // key: field name of java object

    public Optional<Field> getColumnField(String columnName) {
        return Optional.ofNullable(columns.get(columnName));
    }

    public Optional<JoinField> getJoinField(String fieldName) {
        return Optional.ofNullable(joinFields.get(fieldName));
    }
}
