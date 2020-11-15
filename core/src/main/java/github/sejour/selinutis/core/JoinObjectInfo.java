package github.sejour.selinutis.core;

import github.sejour.selinutis.core.statement.clause.ObjectFieldJoin;

import lombok.Value;

@Value
public class JoinObjectInfo implements ObjectInfo {
    ObjectFieldJoin join;
    TableInfo info;
    JoinField field;

    @Override
    public String getAlias() {
        return join.getAlias();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JoinObjectInfo) {
            final var o = (JoinObjectInfo) other;
            return getAlias().equals(o.getAlias());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getAlias().hashCode();
    }
}
