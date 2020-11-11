package github.sejour.selinutis.core.statement.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandType {
    PLAIN_TEXT(null),
    WITH("WITH"),
    FROM("FROM"),
    SELECT("SELECT"),
    JOIN("JOIN"),
    WHERE("WHERE"),
    GROUP_BY("GROUP BY"),
    HAVING("HAVING"),
    ORDER_BY("ORDER BY");

    final String command;
}
