package github.sejour.selinutis.core.statement.clause;

import static java.lang.String.join;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public class ObjectFieldJoinFetch extends ObjectFieldJoin implements FetchTableObject {
    private final List<String> fetchColumns;

    public ObjectFieldJoinFetch(JoinType type, String objectField, String alias, String... fetchColumns) {
        super(type, objectField, alias);

        this.fetchColumns = StringUtils.isEmpty(alias)
                       ? asList(fetchColumns)
                       : Arrays.stream(fetchColumns)
                               .map(column -> join(".", alias, column))
                               .collect(Collectors.toList());
    }
}
