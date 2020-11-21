package github.sejour.selinutis.core;

import static java.lang.String.join;

import java.util.List;

import github.sejour.selinutis.core.statement.clause.ObjectFieldJoin;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class JoinFetchObjectInfo extends JoinObjectInfo implements FetchObjectInfo {
    private final List<String> fetchColumns;
    private final String fetchColumnsString;

    public JoinFetchObjectInfo(@NonNull ObjectFieldJoin join,
                               @NonNull TableInfo info, @NonNull JoinField field,
                               @NonNull List<String> fetchColumns) {
        super(join, info, field);
        this.fetchColumns = fetchColumns;
        fetchColumnsString = join(",", fetchColumns);
    }
}
