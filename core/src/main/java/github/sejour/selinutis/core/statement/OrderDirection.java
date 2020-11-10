package github.sejour.selinutis.core.statement;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderDirection {
    ASC("ASC"),
    DESC("DESC");

    public final String value;
}
