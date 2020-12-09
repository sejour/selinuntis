package github.sejour.selinuntis.core;

import java.lang.reflect.Field;
import java.util.Optional;

public interface ModelInfo {
    Optional<Field> getColumnField(String columnName);
    Optional<JoinField> getJoinField(String fieldName);
}
