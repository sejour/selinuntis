package github.sejour.selinutis.core.statement.clause;

import lombok.Value;

@Value
public class FromTableClass extends From implements TableObject {
    Class<?> tableClass;
    String alias;
}
