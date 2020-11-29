package github.sejour.selinuntis.core;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class JoinField {
    private static final String TARGET_TABLE = "{0}";
    private static final String TARGET_TABLE_ALIAS = "{1}";
    private static final String PARENT_TABLE_ALIAS = "{2}";

    // FIXME: MessageFormat is not thread safe
    private final MessageFormat expressionTemplate;
    private final Field field;
    private final Class<?> fieldClass;
    private final Class<?> tableClass;
    private final boolean toMany;

    public String formatJoinExpression(String targetTable,
                                       String targetTableAlias,
                                       String parentTableAlias) {
        return expressionTemplate.format(new Object[] {
                targetTable,
                targetTableAlias,
                parentTableAlias,
        });
    }
}
