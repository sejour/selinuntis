package github.sejour.selinuntis.core.statement;

import static java.lang.String.format;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;

import github.sejour.selinuntis.core.FetchObjectInfo;
import github.sejour.selinuntis.core.FromFetchObjectInfo;
import github.sejour.selinuntis.core.FromObjectInfo;
import github.sejour.selinuntis.core.JoinFetchObjectInfo;
import github.sejour.selinuntis.core.JoinObjectInfo;
import github.sejour.selinuntis.core.ObjectInfo;
import github.sejour.selinuntis.core.TableInfo;
import github.sejour.selinuntis.core.error.StatementBuildException;
import github.sejour.selinuntis.core.statement.chain.Query;
import github.sejour.selinuntis.core.statement.chain.QueryImpl;
import github.sejour.selinuntis.core.statement.clause.Clause;
import github.sejour.selinuntis.core.statement.clause.FetchTableObject;
import github.sejour.selinuntis.core.statement.clause.From;
import github.sejour.selinuntis.core.statement.clause.FromTableClass;
import github.sejour.selinuntis.core.statement.clause.Keyword;
import github.sejour.selinuntis.core.statement.clause.ObjectFieldJoin;
import github.sejour.selinuntis.core.statement.clause.StringExpressionClause;
import github.sejour.selinuntis.core.statement.clause.TableObject;
import github.sejour.selinuntis.core.utils.CollectionUtils;

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
        final var fetchObjects = objectInfoMap
                .values()
                .stream()
                .filter(info -> info instanceof FetchObjectInfo)
                .map(info -> (FetchObjectInfo) info)
                .collect(Collectors.toSet());

        final var builder = new StringBuilder();

        if (CollectionUtils.isNotEmpty(queryImpl.getPreClauses())) {
            builder.append(buildClause(queryImpl.getPreClauses(), objectInfoMap));
        }

        builder.append(" ")
               .append(buildSelect(queryImpl.isDistinct(), queryImpl.getSelectFields(), fetchObjects))
               .append(" ")
               .append(buildClause(queryImpl.getPostClauses(), objectInfoMap));

        return new Statement(builder.toString(), objectInfoMap, fromObjectAlias);
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
            if (from instanceof FetchTableObject) {
                objectInfo = new FromFetchObjectInfo(from.getAlias(), tableInfo,
                                                     ((FetchTableObject) from).getFetchColumns());
            } else {
                objectInfo = new FromObjectInfo(from.getAlias(), tableInfo);
            }
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
            if (join instanceof FetchTableObject) {
                objectInfo = new JoinFetchObjectInfo(join, tableInfo, joinField,
                                                     ((FetchTableObject) join).getFetchColumns());
            } else {
                objectInfo = new JoinObjectInfo(join, tableInfo, joinField);
            }
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

    private static String buildSelect(boolean distinct,
                                      Set<String> selectFields,
                                      Set<FetchObjectInfo> fetchObjects) throws StatementBuildException {
        final var builder = new StringBuilder(Keyword.SELECT.getClause());

        if (distinct) {
            builder.append(" ")
                   .append(Keyword.DISTINCT.getClause());
        }

        final var fieldsString = Stream
                .concat(selectFields.stream(),
                        fetchObjects.stream()
                                    .map(FetchObjectInfo::getSelectFieldsString))
                .collect(Collectors.joining(","));
        if (StringUtils.isEmpty(fieldsString)) {
            throw new StatementBuildException("select fields not exist");
        }

        return builder.append(" ")
                      .append(fieldsString)
                      .toString();
    }

    private static String buildClause(List<Clause> sequence,
                                      Map<String, ObjectInfo> objectInfoMap) throws StatementBuildException {
        final var builder = new StringBuilder();

        for (final var clause: sequence) {
            String str = null;
            if (clause instanceof TableObject) {
                final var tableObject = (TableObject) clause;
                str = Optional.ofNullable(objectInfoMap.get(tableObject.getAlias()))
                              .map(ObjectInfo::getClause)
                              .orElseThrow(() -> new StatementBuildException(
                                      format("object info not found, alias: %s",
                                             tableObject.getAlias())));
            } else if (clause instanceof StringExpressionClause) {
                str = ((StringExpressionClause) clause).getExpression();
            }

            if (str == null) {
                throw new StatementBuildException(format("not supported clause: %s, keyword: %s",
                                                         clause.getClass().getName(),
                                                         clause.getKeyword().getClause()));
            }

            builder.append(' ')
                   .append(str);
        }

        return builder.toString();
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

