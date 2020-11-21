package github.sejour.selinutis.core;

import java.util.List;

public interface FetchObjectInfo extends ObjectInfo {
    List<String> getFetchColumns();
    String getFetchColumnsString();
}
