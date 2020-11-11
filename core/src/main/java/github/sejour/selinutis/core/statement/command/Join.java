package github.sejour.selinutis.core.statement.command;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Join implements Command {
    JoinType type;
    String fieldName;
    String alias;
    boolean fetch;
    List<String> fetchFields;

    @Override
    public CommandType getCommandType() {
        return CommandType.JOIN;
    }
}
