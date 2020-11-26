package github.sejour.selinuntis.core.statement.clause;

import static java.lang.String.join;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FromFetchTableClass extends FromTableClass implements FetchTableObject {
    private final List<String> fetchColumns;

    public FromFetchTableClass(@NonNull Class<?> tableClass, @NonNull String alias,
                               @NonNull String... columns) {
        super(tableClass, alias);

        fetchColumns = StringUtils.isEmpty(alias)
                       ? asList(columns)
                       : Arrays.stream(columns)
                               .map(column -> join(".", alias, column))
                               .collect(Collectors.toList());
    }
}
