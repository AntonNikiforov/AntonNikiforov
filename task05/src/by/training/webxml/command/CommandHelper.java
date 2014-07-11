package by.training.webxml.command;

import by.training.webxml.command.impl.NoSuchCommand;
import by.training.webxml.command.impl.ParseCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {

    private static final CommandHelper instance = new CommandHelper();

    private Map<CommandName, Command> commands = new HashMap<>();

    private CommandHelper() {
        commands.put(CommandName.PARSE, new ParseCommand());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
    }

    public static CommandHelper getInstance() {
        return instance;
    }

    public Command getCommand(CommandName command) {

        if (command == null) {
            return commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return commands.get(command);
    }
}
