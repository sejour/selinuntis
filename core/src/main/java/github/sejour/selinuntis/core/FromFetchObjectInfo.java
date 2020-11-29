package github.sejour.selinuntis.core;

import static github.sejour.selinuntis.core.FetchObjectInfoSupport.createFetchColumns;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromFetchObjectInfo extends FromObjectInfo implements FetchObjectInfo {
    private final String selectFieldsString;
    private final Map<String, String> fetchColumnNameMap;

    public FromFetchObjectInfo(@NonNull String alias, @NonNull TableInfo info,
                               @NonNull Set<String> fetchColumns,
                               String fetchColumnsAliasFormat) {
        super(alias, info);

        final var columns = createFetchColumns(
                alias, info, fetchColumns, fetchColumnsAliasFormat);
        selectFieldsString = columns
                .stream()
                .map(FetchObjectInfoSupport.FetchColumn::getDefinition)
                .collect(Collectors.joining(","));
        fetchColumnNameMap = columns
                .stream()
                .collect(Collectors.toMap(FetchObjectInfoSupport.FetchColumn::getLabel,
                                          FetchObjectInfoSupport.FetchColumn::getName));
    }
}
