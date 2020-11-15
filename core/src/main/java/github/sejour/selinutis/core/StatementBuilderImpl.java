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

import com.google.common.collect.ImmutableMap;

import github.sejour.selinutis.core.error.StatementBuildException;
import github.sejour.selinutis.core.statement.Keyword;
import github.sejour.selinutis.core.statement.Query;
import github.sejour.selinutis.core.statement.QueryImpl;
import github.sejour.selinutis.core.statement.Statement;
import github.sejour.selinutis.core.statement.clause.From;
import github.sejour.selinutis.core.statement.clause.FromTableClass;
import github.sejour.selinutis.core.statement.clause.ObjectFieldJoin;
import github.sejour.selinutis.core.statement.clause.TableObject;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatementBuilderImpl implements StatementBuilder {
    private final Map<Class<?>, TableInfo> tableInfoMap;

    @Override
    public Statement buildSelect(@NonNull Query<?> query) throws StatementBuildException {
        if (!(query instanceof QueryImpl<?>)) {
            throw new StatementBuildException(format("not supported query implementation: %s",
                                                     query.getClass().getName()));
        }

        final var queryImpl = (QueryImpl<?>) query;
        final var fromObjectAlias = queryImpl.getFromObjectAlias();
        final var objectInfoMap = createObjectInfoMap(
                fromObjectAlias, queryImpl.getTableObjectMap());

        return new Statement("TODO", objectInfoMap, fromObjectAlias);
    }

    private Map<String, ObjectInfo> createObjectInfoMap(String fromObjectAlias,
                                                        Map<String, TableObject> tableObjectMap)
            throws StatementBuildException {
        final var objectNodeMap = tableObjectMap
                .values()
                .stream()
                .map(obj -> Map.entry(obj.getAlias(), new ObjectNode(obj)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        int fromCount = 0;
        for (final var node : objectNodeMap.values()) {
            if (node.object instanceof From) {
                ++fromCount;
            } else if (node.object instanceof ObjectFieldJoin) {
                final var join = (ObjectFieldJoin) node.object;
                final var parent = objectNodeMap.get(join.getParentObjectAlias());
                if (parent == null) {
                    throw new StatementBuildException(format("undefined object alias: %s",
                                                             join.getParentObjectAlias()));
                }
                parent.child.add(node);
            } else {
                throw new StatementBuildException(format("not supported table object: %s",
                                                         node.object.getClass().getName()));
            }
        }
        if (fromCount != 1) {
            throw new StatementBuildException("multiple from clauses found");
        }

        final var builder = ImmutableMap
                .<String, ObjectInfo>builder();
        final var rootNode = objectNodeMap.get(fromObjectAlias);

        buildObjectInfoMap(rootNode, null, builder);

        return builder.build();
    }

    private void buildObjectInfoMap(ObjectNode node, ObjectInfo parent,
                                    ImmutableMap.Builder<String, ObjectInfo> builder)
            throws StatementBuildException {
        ObjectInfo objectInfo = null;

        if (node.object instanceof FromTableClass) {
            final var from = (FromTableClass) node.object;
            final var tableInfo = Optional
                    .ofNullable(tableInfoMap.get(from.getTableClass()))
                    .orElseThrow(() -> new StatementBuildException(
                            format("table class used in from is not registered: %s",
                                   from.getTableClass().getName())));
            objectInfo = new FromTableObjectInfo(from.getAlias(), tableInfo);
        } else if (node.object instanceof ObjectFieldJoin) {
            final var join = (ObjectFieldJoin) node.object;
            final var joinField = parent
                    .getInfo()
                    .getJoinField(join.getFieldName())
                    .orElseThrow(() -> new StatementBuildException(
                            format("join object field not found: %s",
                                   join.getFieldName())));
            final var tableInfo = Optional
                    .ofNullable(tableInfoMap.get(joinField.getTableClass()))
                    .orElseThrow(() -> new StatementBuildException(
                            format("table class used in join is not supported: %s",
                                   joinField.getTableClass())));
            objectInfo = new JoinObjectInfo(join, tableInfo, joinField);
        }

        if (objectInfo == null) {
            throw new StatementBuildException(format("not supported table object: %s",
                                                     node.object.getClass().getName()));
        }

        builder.put(node.object.getAlias(), objectInfo);

        for (final var childNode: node.child) {
            buildObjectInfoMap(childNode, objectInfo, builder);
        }
    }

    private static String buildSelectFrom(boolean distinct,
                                          List<String> selectFields,
                                          FromObjectInfo fromObjectInfo,
                                          Set<ObjectInfo> fetchObjects) {
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
                      .append(fromObjectInfo.getClause())
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

