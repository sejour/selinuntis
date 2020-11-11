package github.sejour.selinutis.core;

import java.lang.reflect.Field;
import java.util.Map;

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
}
