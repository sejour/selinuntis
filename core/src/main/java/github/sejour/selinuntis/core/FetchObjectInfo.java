package github.sejour.selinuntis.core;

import static github.sejour.selinuntis.core.utils.CollectionUtils.isEmpty;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

public interface FetchObjectInfo extends ObjectInfo {
    String getSelectFieldsString();
    Map<String, String> getFetchColumnNameMap();
}

@UtilityClass
class FetchObjectInfoSupport {
    @Value
    static class FetchColumn {
        String name;
        String label;
        String definition;

        static FetchColumn fromName(String name) {
            return new FetchColumn(name, name, name);
        }

        static FetchColumn fromTableAndName(String table, String name) {
            final var field = String.join(".", table, name);
            return new FetchColumn(name, field, field);
        }

        static FetchColumn fromNameAndLabel(String name, String label) {
            return new FetchColumn(name, label, String.join(" AS ", name, label));
        }
    }

    public static List<FetchColumn> createFetchColumns(@NonNull String alias,
                                                       @NonNull TableInfo info,
                                                       @NonNull Set<String> fetchColumns,
                                                       String fetchColumnsAliasFormat) {
        final Function<String, FetchColumn> mapper
                = StringUtils.isEmpty(alias)
                  ? FetchColumn::fromName
                  : (fetchColumnsAliasFormat == null
                     ? column -> FetchColumn.fromTableAndName(alias, column)
                     : column -> FetchColumn.fromNameAndLabel(column, MessageFormat
                .format(fetchColumnsAliasFormat, alias, column)));

        return (isEmpty(fetchColumns) ? info.getColumnNames() : fetchColumns)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
