package github.sejour.selinuntis.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultTypeInfoMap {
    Map<Class<?>, ResultTypeInfo> map;

    ResultTypeInfoMap create(@NonNull List<Map<Class<?>, ResultTypeInfo>> mapList) {
        final Map<Class<?>, ResultTypeInfo> merged = mapList
                .stream()
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new ResultTypeInfoMap(merged);
    }

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
