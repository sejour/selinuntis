package github.sejour.selinuntis.core;

import static github.sejour.selinuntis.core.FetchObjectInfoSupport.createFetchColumns;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import github.sejour.selinuntis.core.error.StatementBuildException;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromFetchObjectInfo extends FromObjectInfo implements FetchObjectInfo {
    private final String selectFieldsString;
    private final Map<String, Field> fetchColumnFieldMap;

    public FromFetchObjectInfo(@NonNull String alias, @NonNull TableInfo info,
                               @NonNull Set<String> fetchColumns) throws StatementBuildException {
        super(alias, info);

        final var columns = createFetchColumns(
                alias, fetchColumns, info);
        selectFieldsString = columns
                .stream()
                .map(FetchObjectInfoSupport.FetchColumn::getDefinition)
                .collect(Collectors.joining(","));
        fetchColumnFieldMap = columns
                .stream()
                .collect(Collectors.toMap(FetchObjectInfoSupport.FetchColumn::getLabel,
                                          FetchObjectInfoSupport.FetchColumn::getField));
    }
}
