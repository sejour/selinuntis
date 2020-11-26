package github.sejour.selinuntis.core.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ExpressionsTest {
    @Test
    public void testClause() {
        assertThat(Expressions.factor("test-expression")).isEqualTo("(test-expression)");
    }
}
