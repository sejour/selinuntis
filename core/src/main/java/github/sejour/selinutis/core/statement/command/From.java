package github.sejour.selinutis.core.statement.command;

public abstract class From implements Command {
    public CommandType getCommandType() {
        return CommandType.FROM;
    }
}
