package github.sejour.selinuntis.core;

import static java.lang.String.join;

import github.sejour.selinuntis.core.statement.clause.ObjectFieldJoin;

import lombok.Getter;

@Getter
public class JoinObjectInfo extends AbstractObjectInfo {
    private final ObjectFieldJoin join;
    private final ResultTypeInfo info;
    private final JoinField field;
    private final String clause;

    public JoinObjectInfo(ObjectFieldJoin join, TableInfo info, JoinField field) {
        this.join = join;
        this.info = info;
        this.field = field;
        clause = join(" ",
                      join.getCompletedKeyword(),
                      field.formatJoinExpression(info.getTableName(),
                                                 join.getAlias(),
                                                 join.getParentObjectAlias()));
    }

    @Override
    public String getAlias() {
        return join.getAlias();
    }
}
