package github.sejour.selinutis.core.statement.expression;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Chains {
    public static ConditionChain condition() {
        return condition(null);
    }

    public static ConditionChain condition(String expression) {
        return new ConditionChain(expression);
    }

    public static ConditionChain condition(boolean condition, String expression) {
        return condition(condition ? expression : null);
    }

    public static OrderChain order() {
        return order(null);
    }

    public static OrderChain order(String expression) {
        return new OrderChain(expression);
    }

    public static OrderChain order(boolean condition, String expression) {
        return order(condition ? expression : null);
    }

    public static OrderChain asc(String field) {
        return order().asc(field);
    }

    public static OrderChain asc(boolean condition, String field) {
        return order().asc(condition, field);
    }

    public static OrderChain desc(String field) {
        return order().desc(field);
    }

    public static OrderChain desc(boolean condition, String field) {
        return order().desc(condition, field);
    }
}
