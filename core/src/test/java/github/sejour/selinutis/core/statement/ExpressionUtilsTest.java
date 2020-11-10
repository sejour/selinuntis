package github.sejour.selinutis.core.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import github.sejour.selinutis.core.statement.expression.ExpressionUtils;

public class ExpressionUtilsTest {
    @Test
    public void testClause() {
        assertThat(ExpressionUtils.clause("test-expression")).isEqualTo("(test-expression)");
    }


}
