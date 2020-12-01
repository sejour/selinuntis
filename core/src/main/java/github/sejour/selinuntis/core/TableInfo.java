package github.sejour.selinuntis.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;

@Getter
public class TableInfo extends ResultTypeInfo {
    private final String tableName;
    private final Map<String, Field> columns; // key: column name of table

    public TableInfo(Class<?> clazz, Map<String, Field> selectFields,
                     Map<String, JoinField> joinFields,
                     String tableName,
                     Map<String, Field> columns) {
        super(clazz, selectFields, joinFields);
        this.tableName = tableName;
        this.columns = columns;
    }

    public Optional<Field> getColumnField(String columnName) {
        return Optional.ofNullable(columns.get(columnName));
    }

    public Set<String> getColumnNames() {
        return columns.keySet();
    }
}
