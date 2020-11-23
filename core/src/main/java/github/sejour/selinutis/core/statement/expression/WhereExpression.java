package github.sejour.selinutis.core.statement.expression;

// TODO: rename Condition
public interface WhereExpression extends Expression {
}

enum Operator {
    AND,
    OR
}

interface ExpressionTree {
}

interface OperatorNode extends ExpressionTree {
    Operator operator();
    ExpressionTree leftOperand();
    ExpressionTree rightOperand();
}

interface Operand extends ExpressionTree {
    String expression();
}
