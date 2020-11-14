package github.sejour.selinutis.core.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Keyword {
    NONE(null),
    WITH("WITH"),
    SELECT("SELECT"),
    DISTINCT("DISTINCT"),
    FROM("FROM"),
    JOIN("JOIN"),
    WHERE("WHERE"),
    GROUP_BY("GROUP BY"),
    HAVING("HAVING"),
    ORDER_BY("ORDER BY");

    @Getter
    final String clause;
}
