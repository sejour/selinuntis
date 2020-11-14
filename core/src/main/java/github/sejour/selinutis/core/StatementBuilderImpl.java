package github.sejour.selinutis.core;

import static github.sejour.selinutis.core.utils.CollectionUtils.isEmpty;
import static java.lang.String.format;
import static java.lang.String.join;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.Keyword;
import github.sejour.selinutis.core.statement.Query;
import github.sejour.selinutis.core.statement.QueryImpl;
import github.sejour.selinutis.core.statement.clause.ObjectFieldJoin;
import github.sejour.selinutis.core.statement.clause.TableObject;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class StatementBuilderImpl implements StatementBuilder {
    private final Map<Class<?>, TableInfo> tableInfoMap;

    @Override
    public String buildSelect(@NonNull Query<?> query) throws StatementBuildException {
        if (!(query instanceof QueryImpl<?>)) {
            throw new StatementBuildException(format("not supported query implementation: %s",
                                                     query.getClass().getName()));
        }

        final var queryImpl = (QueryImpl<?>) query;
        final var from = queryImpl.getFrom();
        final var fromTable = new FromTable(from.getAlias(), Optional
                .ofNullable(tableInfoMap.get(from.getTableClass()))
                .orElseThrow(() -> new StatementBuildException(
                        format("table class is not registered: %s",
                               queryImpl.getFrom().getTableClass().getName()))));

        final var objectNodeMap = queryImpl
                .getTableObjectMap()
                .values()
                .stream()
                .map(obj -> Map.entry(obj.getAlias(), new ObjectNode(obj)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        for (final var node: objectNodeMap.values()) {
            if (!(node.object instanceof ObjectFieldJoin)) {
                continue;
            }

            final var join = (ObjectFieldJoin) node.object;
            final var parent = objectNodeMap.get(join.getParentObjectAlias());
            if (parent == null) {
                throw new StatementBuildException(format("undefined object alias: %s",
                                                         join.getParentObjectAlias()));
            }

            parent.child.add(node);
        }

        final var rootNode = objectNodeMap.get(from.getAlias());

        return "";
    }

    private String buildSelectFrom(boolean distinct, List<String> selectFields,
                                   FromTable fromTable,
                                   Set<JoinTable> joinTables) {
        final var builder = new StringBuilder(Keyword.SELECT.getClause())
                .append(" ");

        if (distinct) {
            builder.append(Keyword.DISTINCT.getClause());
        }

        if (isEmpty(selectFields)) {
            // TODO
        } else {
            builder.append(join(",", selectFields));
        }

        return builder.append(Keyword.FROM.getClause())
                      .append(" ")
                      .append(fromTable.getInfo().getTableName())
                      .toString();
    }
}

class ObjectNode {
    final TableObject object;
    final Set<ObjectNode> child;

    public ObjectNode(TableObject object) {
        this.object = object;
        this.child = new HashSet<>();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ObjectNode) {
            return object.getAlias().equals(((ObjectNode) other).object.getAlias());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return object.getAlias().hashCode();
    }
}

interface Table {
    String getAlias();
    TableInfo getInfo();
}

@Value
class FromTable implements Table {
    String alias;
    TableInfo info;

    @Override
    public boolean equals(Object other) {
        if (other instanceof FromTable) {
            final var o = (FromTable) other;
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

@Value
class JoinTable implements Table {
    ObjectFieldJoin join;
    TableInfo info;
    JoinField field;

    @Override
    public String getAlias() {
        return join.getAlias();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JoinTable) {
            final var o = (JoinTable) other;
            return getAlias().equals(o.getAlias());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getAlias().hashCode();
    }
}
