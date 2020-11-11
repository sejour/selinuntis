package github.sejour.selinutis.core.statement.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JoinType {
    INNER("INNER"),
    LEFT_OUTER("LEFT OUTER"),
    RIGHT_OUTER("RIGHT OUTER");

    final String prefix;
}
