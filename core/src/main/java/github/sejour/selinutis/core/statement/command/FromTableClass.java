package github.sejour.selinutis.core.statement.command;

import lombok.Value;

@Value
public class FromTableClass extends From {
    Class<?> tableClass;
    String alias;
}
