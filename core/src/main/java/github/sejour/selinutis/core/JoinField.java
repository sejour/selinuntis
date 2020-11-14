package github.sejour.selinutis.core;

import static java.lang.String.format;

import java.lang.reflect.Field;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class JoinField {
    private static final String STATEMENT_TEMPLATE = "{0} %sON {1}";

    private final String statementTemplate;
    private final Field field;
    private final Class<?> fieldClass;
    private final Class<?> tableClass;
    private boolean toMany;

    public String getJoinExpression(String parentAlias, String targetAlias) {
        final var parent = Optional
                .ofNullable(parentAlias)
                .map(alias -> format("%s.", alias))
                .orElse("");
        final var target = Optional
                .ofNullable(targetAlias)
                .map(alias -> format("%s.", alias))
                .orElse("");
        return format(statementTemplate, target, parent);
    }
}
