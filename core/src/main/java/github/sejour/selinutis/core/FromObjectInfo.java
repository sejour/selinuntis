package github.sejour.selinutis.core;

import static java.lang.String.join;

import org.apache.commons.lang3.StringUtils;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.Getter;

@Getter
public class FromObjectInfo implements ObjectInfo {
    private final String alias;
    private final TableInfo info;
    private final String clause;

    public FromObjectInfo(String alias, TableInfo info) {
        this.alias = alias;
        this.info = info;
        clause = join(" ", Keyword.FROM.getClause(), info.getTableName());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FromObjectInfo) {
            final var o = (FromObjectInfo) other;
            if (StringUtils.isEmpty(alias) && StringUtils.isEmpty(o.alias)) {
                return info.equals(o.info);
            }
            if (StringUtils.equals(alias, o.alias)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (StringUtils.isNotEmpty(alias)) {
            return alias.hashCode();
        }
        return info.hashCode();
    }
}
