package github.sejour.selinuntis.mapper;

import static github.sejour.selinuntis.core.statement.chain.Chains.asc;
import static github.sejour.selinuntis.core.statement.chain.Chains.condition;
import static github.sejour.selinuntis.core.statement.chain.Select.from;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import github.sejour.selinuntis.core.annotation.Column;
import github.sejour.selinuntis.core.annotation.Id;
import github.sejour.selinuntis.core.annotation.ResultType;
import github.sejour.selinuntis.core.annotation.SelectField;
import github.sejour.selinuntis.core.annotation.Table;
import github.sejour.selinuntis.mapper.annotation.Param;
import github.sejour.selinuntis.mapper.annotation.QueryCache;

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

        @SelectField
        Integer count;
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

    @ResultType
    public class TestResultType {
    }

    @Test
    public void testtest() {
        final var scanner = new Reflections(new ConfigurationBuilder()
                                                    .setUrls(ClasspathHelper.forPackage("github.sejour.selinuntis.mapper"))
                                                    .setScanners(new SubTypesScanner(),
                                                                 new TypeAnnotationsScanner()));
        scanner.getTypesAnnotatedWith(ResultType.class)
               .forEach(c -> System.out.println(c.getName()));
    }

}
