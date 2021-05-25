package main.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static final Map<String, Command> commandsMap = new TreeMap<>();

    static {
        commandsMap.put("login", new CommandLogin());
        commandsMap.put("logout",new CommandLogout());
        commandsMap.put("catalog",new CommandCatalog());
        commandsMap.put("cart",new CommandCart());
        commandsMap.put("signup",new CommandSingUp());
    }

    public static Command getCommand(String commandName) {
        log.trace("Get command from Controller: "+commandName);
        if (commandsMap == null || !commandsMap.containsKey(commandName)) {
            log.trace("Command: " + commandName + " not found");
            return commandsMap.get("noCommand");
        }
        return commandsMap.get(commandName);
    }
}
