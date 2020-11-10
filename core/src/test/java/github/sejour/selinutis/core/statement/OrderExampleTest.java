package github.sejour.selinutis.core.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import github.sejour.selinutis.core.statement.expression.Order;

public class OrderExampleTest {
    @Test
    public void testOrderByAsc() {
        final Order order1 = Order.By
                .asc("aaa")
                .desc("bbb")
                .desc("ccc")
                .asc("ddd");
        assertThat(order1.getExpression()).isEqualTo("aaa ASC,bbb DESC,ccc DESC,ddd ASC");

        final Order order2 = Order.By
                .desc("aaa")
                .desc("bbb")
                .asc("ccc")
                .asc("ddd");
        assertThat(order2.getExpression()).isEqualTo("aaa DESC,bbb DESC,ccc ASC,ddd ASC");

        final Order order3 = Order
                .by(o -> o.asc(">aaa")
                          .desc(">bbb")
                          .order(oo -> oo.desc(">>aaa")
                                         .asc(">>bbb"))
                          .asc(">ccc"))
                .desc("bbb")
                .order(o -> o.asc("!aaa")
                             .order(oo -> oo.asc("!!aaa")
                                            .desc("!!bbb"))
                             .desc("!bbb"))
                .desc("ccc")
                .asc("ddd");
        assertThat(order3.getExpression())
                .isEqualTo(">aaa ASC,>bbb DESC,>>aaa DESC,>>bbb ASC,>ccc ASC,bbb DESC,!aaa ASC,!!aaa ASC,!!bbb DESC,!bbb DESC,ccc DESC,ddd ASC");

        final Order order4 = Order
                .by(o -> o);
        assertThat(order4.getExpression()).isNull();
    }
}
