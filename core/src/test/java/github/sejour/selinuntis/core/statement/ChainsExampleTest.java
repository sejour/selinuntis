package github.sejour.selinuntis.core.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import github.sejour.selinuntis.core.statement.chain.Chains;
import github.sejour.selinuntis.core.statement.chain.OrderChain;

public class ChainsExampleTest {
    @Test
    public void testOrderByAsc() {
        final OrderChain order1 = Chains
                .asc("aaa")
                .desc("bbb")
                .desc("ccc")
                .asc("ddd");
        assertThat(order1.getExpression()).isEqualTo("aaa ASC,bbb DESC,ccc DESC,ddd ASC");

        final OrderChain order2 = Chains
                .desc("aaa")
                .desc("bbb")
                .asc("ccc")
                .asc("ddd");
        assertThat(order2.getExpression()).isEqualTo("aaa DESC,bbb DESC,ccc ASC,ddd ASC");

        final OrderChain order3 = Chains
                .order()
                .order(Chains.asc(">aaa")
                             .desc(">bbb")
                             .order(Chains.desc(">>aaa")
                                          .asc(">>bbb"))
                             .asc(">ccc"))
                .desc("bbb")
                .order(Chains.asc("!aaa")
                             .order(Chains.asc("!!aaa")
                                          .desc("!!bbb"))
                             .desc("!bbb"))
                .desc("ccc")
                .asc("ddd");
        assertThat(order3.getExpression())
                .isEqualTo(">aaa ASC,>bbb DESC,>>aaa DESC,>>bbb ASC,>ccc ASC,bbb DESC,!aaa ASC,!!aaa ASC,!!bbb DESC,!bbb DESC,ccc DESC,ddd ASC");

        final OrderChain order4 = Chains
                .order()
                .order(Chains.order().asc(null).desc(null).order(Chains.order()))
                .asc(null)
                .desc(null);
        assertThat(order4.getExpression()).isNull();
    }
}
