package github.sejour.selinutis.core.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Keyword {
    NONE(null),
    SELECT("SELECT"),
    DISTINCT("DISTINCT"),
    FROM("FROM"),
    JOIN("JOIN"),
    WHERE("WHERE"),
    GROUP_BY("GROUP BY"),
    HAVING("HAVING"),
    ORDER_BY("ORDER BY"),
    LIMIT("LIMIT");

    @Getter
    final String clause;
}
