package github.sejour.selinuntis.core;

import java.util.Map;
import java.util.Optional;

import lombok.NonNull;
import lombok.Value;

@Value
public class ResultTypeInfoMap {
    Map<Class<?>, ResultTypeInfo> map;

    public Optional<ResultTypeInfo> getAsResultTypeInfo(@NonNull Class<?> resultType) {
        return Optional.ofNullable(map.get(resultType));
    }

    public Optional<TableInfo> getAsTableInfo(@NonNull Class<?> tableClass) {
        return Optional.ofNullable(map.get(tableClass))
                       .flatMap(info -> {
                           if (info instanceof TableInfo) {
                               return Optional.of((TableInfo) info);
                           }
                           return Optional.empty();
                       });
    }
}
