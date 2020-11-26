package github.sejour.selinuntis.core;

import static java.lang.String.join;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromFetchObjectInfo extends FromObjectInfo implements FetchObjectInfo {
    private final List<String> fetchColumns;
    private final String fetchColumnsString;

    public FromFetchObjectInfo(@NonNull String alias, @NonNull TableInfo info,
                               @NonNull List<String> fetchColumns) {
        super(alias, info);
        this.fetchColumns = fetchColumns;
        fetchColumnsString = join(",", fetchColumns);
    }
}
