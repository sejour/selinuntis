package github.sejour.selinutis.core.statement.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GeneralCommand implements Command {
    String expression;
    CommandType commandType;
}
