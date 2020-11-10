package github.sejour.selinutis.mapper;

import static github.sejour.selinutis.core.statement.Select.from;

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
        public QueryExecution<Example> findById(@Param Long id) {
            return mapper.select(
                    from(Example.class)
                            .where("id = #{id}"));
        }
    }

    private ExampleRepository exampleRepository;

    @Before
    public void setup() {
        exampleRepository = new ExampleRepository(null);
    }

    @Test
    public void test() {
        final Example result = exampleRepository.findById(123L).execute();
        // TODO
    }

}
