package github.sejour.selinutis.core.statement.clause;

import static java.lang.String.join;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public class FromFetchTableClass extends FromTableClass implements FetchTableObject {
    private final List<String> fetchColumns;

    public FromFetchTableClass(Class<?> tableClass, String alias, String... columns) {
        super(tableClass, alias);

        fetchColumns = StringUtils.isEmpty(alias)
                       ? asList(columns)
                       : Arrays.stream(columns)
                               .map(column -> join(".", alias, column))
                               .collect(Collectors.toList());
    }
}
