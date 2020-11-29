package github.sejour.selinuntis.core;

public interface FetchField {
    String getDefinition();
}

class FetchExpression implements FetchField {
    String expression;
    String alias;

    @Override
    public String getDefinition() {
        return String.join(" AS ", expression, alias);
    }
}

class FetchColumn implements FetchField {
    String object;
    String name;
    String alias;

    @Override
    public String getDefinition() {
        return String.join("");
    }
}
