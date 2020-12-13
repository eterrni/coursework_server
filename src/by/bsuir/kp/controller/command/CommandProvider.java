package by.bsuir.kp.controller.command;

import by.bsuir.kp.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private static final CommandProvider instance = new CommandProvider();
    private Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.LOGIN, new LoginCommand());
        commands.put(ParameterName.REGISTRATION, new RegistrationCommand());
        commands.put(ParameterName.INIT_DATA, new InitDataCommand());
        commands.put(ParameterName.FIND_PRODUCT, new FindProductCommand());
        commands.put(ParameterName.ADD_NEW_PRODUCER, new AddNewProducerCommand());
        commands.put(ParameterName.ADD_NEW_PROVIDER, new AddNewProviderCommand());

        commands.put(ParameterName.INIT_USERS, new InitUsersCommand());
        commands.put(ParameterName.INIT_CHOICE_PRODUCER, new InitChoiceProducerCommand());
        commands.put(ParameterName.INIT_CHOICE_PROVIDER, new InitChoiceProviderCommand());
        commands.put(ParameterName.DELETE_PRODUCT, new DeleteProductCommand());
        commands.put(ParameterName.APPOINT_AN_ADMINISTRATOR, new AppointAnAdministratorCommand());
        commands.put(ParameterName.APPOINT_AN_USER, new AppointAnUserCommand());
        commands.put(ParameterName.DELETE_USER, new DeleteUserCommand());
        commands.put(ParameterName.ADD_NEW_PRODUCT, new AddNewProductCommand());

    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command getCommand(String commandName) {
        Command command;
        ParameterName valueName;

        commandName = commandName.toUpperCase();
        valueName = ParameterName.valueOf(commandName);

        command = commands.get(valueName);
        return command;
    }
}
