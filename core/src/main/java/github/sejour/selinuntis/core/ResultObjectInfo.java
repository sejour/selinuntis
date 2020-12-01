package github.sejour.selinuntis.core;

import github.sejour.selinuntis.core.statement.clause.Keyword;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ResultObjectInfo extends AbstractObjectInfo {
    private final String alias;
    private final ResultTypeInfo info;
    private final Class<?> resultType;
    private final String clause;

    public static ResultObjectInfo from(@NonNull ResultTypeInfoMap resultTypeInfoMap,
                                        @NonNull Class<?> resultType,
                                        @NonNull String tableName,
                                        @NonNull String alias) {
        return new ResultObjectInfo(
                alias,
                resultTypeInfoMap.getAsResultTypeInfo(resultType).orElse(null),
                resultType,
                String.join(" ", Keyword.FROM.getClause(), tableName));
    }
}
