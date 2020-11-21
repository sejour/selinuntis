package github.sejour.selinutis.core;

import static java.lang.String.join;

import org.apache.commons.lang3.StringUtils;

import github.sejour.selinutis.core.statement.Keyword;

import lombok.Getter;

@Getter
public class FromTableObjectInfo implements FromObjectInfo {
    private final String alias;
    private final TableInfo info;
    private final String clause;

    public FromTableObjectInfo(String alias, TableInfo info) {
        this.alias = alias;
        this.info = info;
        clause = join(" ", Keyword.FROM.getClause(), info.getTableName());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FromTableObjectInfo) {
            final var o = (FromTableObjectInfo) other;
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
