package github.sejour.selinuntis.core.error;

public class StatementBuildException extends Exception {
    public StatementBuildException(String message) {
        super(message);
    }

    public StatementBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
