package github.sejour.selinutis.mapper;

import static github.sejour.selinutis.core.statement.Select.from;
import static github.sejour.selinutis.core.statement.expression.Chains.asc;
import static github.sejour.selinutis.core.statement.expression.Chains.condition;

import org.junit.Before;
import org.junit.Test;

import github.sejour.selinutis.core.annotation.Column;
import github.sejour.selinutis.core.annotation.Id;
import github.sejour.selinutis.core.annotation.Table;
import github.sejour.selinutis.mapper.annotation.Param;
import github.sejour.selinutis.mapper.annotation.QueryCache;

import lombok.AllArgsConstructor;
import lombok.Value;

public class ExampleTest {

    @Value
    @Table("example")
    public static class Example {
        @Id
        String id;

        @Column
        String name;
    }

    @AllArgsConstructor
    public static class ExampleRepository {
        final Mapper mapper;

        @QueryCache
        public QueryExecution<Example> find(@Param String url, @Param String name, @Param String title) {
            return mapper.select(
                    from(Example.class)
                            .where(condition("url = #{url}")
                                           .or(condition(name != null, "name = #{name}")
                                                       .and(title != null, "title = #{title*")))
                            .orderBy(asc("id")));
        }
    }

    private ExampleRepository exampleRepository;

    @Before
    public void setup() {
        exampleRepository = new ExampleRepository(null);
    }

    @Test
    public void test() {
        final Example result = exampleRepository.find("", "", "").execute();
        // TODO
    }

}
