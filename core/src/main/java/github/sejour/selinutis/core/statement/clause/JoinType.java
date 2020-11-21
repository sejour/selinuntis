package github.sejour.selinutis.core.statement.clause;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JoinType {
    INNER("INNER"),
    LEFT_OUTER("LEFT OUTER"),
    RIGHT_OUTER("RIGHT OUTER");

    @Getter
    private final String prefix;
}
