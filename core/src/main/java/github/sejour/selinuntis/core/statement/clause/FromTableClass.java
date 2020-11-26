package github.sejour.selinuntis.core.statement.clause;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FromTableClass extends From implements TableObject {
    private final Class<?> tableClass;
    private final String alias;
}
