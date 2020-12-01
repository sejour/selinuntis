package github.sejour.selinuntis.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResultTypeInfo {
    private final Class<?> clazz;
    private final Map<String, Field> selectFields; // key: label
    private final Map<String, JoinField> joinFields; // key: field name of java object

    public Optional<JoinField> getJoinField(String fieldName) {
        return Optional.ofNullable(joinFields.get(fieldName));
    }
}
