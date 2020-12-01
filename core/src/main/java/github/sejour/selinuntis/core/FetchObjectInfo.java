package github.sejour.selinuntis.core;

import static github.sejour.selinuntis.core.utils.CollectionUtils.isEmpty;
import static java.lang.String.format;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import github.sejour.selinuntis.core.statement.clause.TableObject;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

public interface FetchObjectInfo extends ObjectInfo {
    String getSelectFieldsString();
    Map<String, Field> getFetchColumnFieldMap();
}

@UtilityClass
class FetchObjectInfoSupport {
    @Value
    static class FetchColumn {
        String label;
        String definition;
        Field field;

        static FetchColumn fromColumnName(String name, Field field) {
            return new FetchColumn(name, name, field);
        }

        static FetchColumn fromTableAliasAndColumnName(String alias, String name, Field field) {
            final var label = String.join(TableObject.FIELD_LABEL_SEPARATOR, alias, name);
            return new FetchColumn(label,
                                   String.join(" AS ",
                                               String.join(".", alias, name), label),
                                   field);
        }
    }

    public static List<FetchColumn> createFetchColumns(@NonNull String alias,
                                                       @NonNull Set<String> fetchColumns,
                                                       @NonNull TableInfo info) {
        final Function<Map.Entry<String, Field>, FetchColumn> mapper
                = StringUtils.isEmpty(alias)
                  ? column -> FetchColumn.fromColumnName(column.getKey(), column.getValue())
                  : column -> FetchColumn.fromTableAliasAndColumnName(alias,
                                                                      column.getKey(),
                                                                      column.getValue());

        return (isEmpty(fetchColumns)
                ? info.getColumns()
                      .entrySet()
                      .stream()
                : fetchColumns.stream()
                              .map(columnName -> Map.entry(
                                      columnName,
                                      info.getColumnField(columnName)
                                          .orElseThrow(() -> new IllegalArgumentException(
                                                  format("fetch column that does not exist, table: %s, alias: %s, column: %s",
                                                         info.getTableName(), alias, columnName))))))
                .map(mapper)
                .collect(Collectors.toList());
    }
}
