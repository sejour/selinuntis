package github.sejour.selinutis.core;

import org.apache.commons.lang3.StringUtils;

import lombok.Value;

@Value
public class FromTableObjectInfo implements FromObjectInfo {
    String alias;
    TableInfo info;

    @Override
    public String getClause() {
        return info.getTableName();
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
