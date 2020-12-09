package github.sejour.selinuntis.core;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import github.sejour.selinuntis.core.annotation.ResultType;

public abstract class TableClassScanner {
    public static ResultTypeInfoMap doScan(String... targetPackages) {
        final var scanner = createScanner(targetPackages);
        final Set<Class<?>> classes = scanner.getTypesAnnotatedWith(ResultType.class);
        return null;
    }

    private static Reflections createScanner(String... targetPackages) {
        return new Reflections(new ConfigurationBuilder()
                                       .setUrls(Arrays.stream(targetPackages)
                                                      .flatMap(pkg -> ClasspathHelper
                                                              .forPackage(pkg)
                                                              .stream())
                                                      .collect(Collectors.toList()))
                                       .setScanners(new SubTypesScanner(),
                                                    new TypeAnnotationsScanner()));
    }

    private static Set<ModelInfo> resultType() {

    }
}
