package github.sejour.selinuntis.core.statement;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import github.sejour.selinuntis.core.ObjectInfo;

import lombok.Value;

@Value
public class Statement {
    String statement;
    Map<String, ObjectInfo> objectInfoMap;
    String fromObjectAlias;

    @Override
    public String toString() {
        return statement;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Statement) {
            return StringUtils.equals(statement, ((Statement) other).statement);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return statement.hashCode();
    }
}
