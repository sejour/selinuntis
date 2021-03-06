package github.sejour.selinuntis.core;

import static github.sejour.selinuntis.core.FetchObjectInfoSupport.createFetchColumns;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import github.sejour.selinuntis.core.statement.clause.ObjectFieldJoin;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class JoinFetchObjectInfo extends JoinObjectInfo implements FetchObjectInfo {
    private final String selectFieldsString;
    private final Map<String, Field> fetchColumnFieldMap;

    public JoinFetchObjectInfo(@NonNull ObjectFieldJoin join,
                               @NonNull TableInfo info, @NonNull JoinField field,
                               @NonNull Set<String> fetchColumns) {
        super(join, info, field);

        final var columns = createFetchColumns(
                join.getAlias(), fetchColumns, info);
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
