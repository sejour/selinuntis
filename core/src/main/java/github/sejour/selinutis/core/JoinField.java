package github.sejour.selinutis.core;

import java.lang.reflect.Field;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class JoinField {
    private final String statementTemplate;
    private final Field field;
}
