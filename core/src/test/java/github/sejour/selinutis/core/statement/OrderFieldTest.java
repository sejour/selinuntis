package github.sejour.selinutis.core.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OrderFieldTest {
    @Test
    public void testToString() {
        final String actual = OrderField.of("test_field", OrderDirection.ASC).toString();
        assertThat(actual).isEqualTo("test_field ASC");
    }
}
