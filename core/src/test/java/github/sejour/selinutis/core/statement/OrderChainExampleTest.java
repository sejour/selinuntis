package github.sejour.selinutis.core.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import github.sejour.selinutis.core.statement.expression.OrderChain;

public class OrderChainExampleTest {
    @Test
    public void testOrderByAsc() {
        final OrderChain order1 = OrderChain
                .create()
                .asc("aaa")
                .desc("bbb")
                .desc("ccc")
                .asc("ddd");
        assertThat(order1.getExpression()).isEqualTo("aaa ASC,bbb DESC,ccc DESC,ddd ASC");

        final OrderChain order2 = OrderChain
                .create()
                .desc("aaa")
                .desc("bbb")
                .asc("ccc")
                .asc("ddd");
        assertThat(order2.getExpression()).isEqualTo("aaa DESC,bbb DESC,ccc ASC,ddd ASC");

        final OrderChain order3 = OrderChain
                .create()
                .order(o -> o.asc(">aaa")
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

        final OrderChain order4 = OrderChain
                .create()
                .order(o -> o);
        assertThat(order4.getExpression()).isNull();
    }
}
