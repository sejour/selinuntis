package github.sejour.selinutis.core.error;

public class QueryBuildException extends Exception {
    public QueryBuildException(String message) {
        super(message);
    }

    public QueryBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
