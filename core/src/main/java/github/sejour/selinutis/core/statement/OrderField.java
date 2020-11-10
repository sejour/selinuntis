package github.sejour.selinutis.core.statement;

import static java.lang.String.join;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrderField {
    String name;
    OrderDirection direction;

    @Override
    public String toString() {
        return join(" ", name, direction.value);
    }
}
