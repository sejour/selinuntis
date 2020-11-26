package github.sejour.selinuntis.core.statement.chain;

import java.util.Optional;

import github.sejour.selinuntis.core.statement.Expressions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConditionChain {
    private static final String AND = " AND ";
    private static final String OR = " OR ";

    String expression;

    private ConditionChain combine(String expression, String delimiter) {
        return Optional.ofNullable(expression)
                       .map(Expressions::factor)
                       .map(exp -> Optional
                               .ofNullable(this.expression)
                               .map(self -> new ConditionChain(String.join(delimiter, self, exp)))
                               .orElseGet(() -> new ConditionChain(exp)))
                       .orElse(this);
    }

    private ConditionChain combine(boolean condition, String expression, String delimiter) {
        if (condition) {
            return combine(expression, delimiter);
        }
        return this;
    }

    private ConditionChain combine(ConditionChain chain, String delimiter) {
        return Optional.ofNullable(chain)
                       .flatMap(ch -> Optional.ofNullable(chain.expression))
                       .map(exp -> Optional
                               .ofNullable(this.expression)
                               .map(self -> new ConditionChain(String.join(delimiter, self, exp)))
                               .orElseGet(() -> new ConditionChain(exp)))
                       .orElse(this);
    }

    private ConditionChain combine(boolean condition, ConditionChain chain, String delimiter) {
        if (condition) {
            return combine(chain, delimiter);
        }
        return this;
    }

    public ConditionChain and(String expression) {
        return combine(expression, AND);
    }

    public ConditionChain and(boolean condition, String expression) {
        return combine(condition, expression, AND);
    }

    public ConditionChain and(ConditionChain chain) {
        return combine(chain, AND);
    }

    public ConditionChain and(boolean condition,
                              ConditionChain chain) {
        return combine(condition, chain, AND);
    }

    public ConditionChain or(String expression) {
        return combine(expression, OR);
    }

    public ConditionChain or(boolean condition, String expression) {
        return combine(condition, expression, OR);
    }

    public ConditionChain or(ConditionChain chain) {
        return combine(chain, OR);
    }

    public ConditionChain or(boolean condition, ConditionChain chain) {
        return combine(condition, chain, OR);
    }
}
