package fr.swansky.papercommandlib.core;

import fr.swansky.papercommandlib.ArgParameter;
import fr.swansky.papercommandlib.CommandHandler;
import fr.swansky.papercommandlib.Variable;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ArgParameterImpl<T extends CommandSender> implements ArgParameter<T> {

    private final List<Variable<?>> variables = new ArrayList<>();
    private final CommandHandler<T> argHandler;
    private final String name;
    private @NotNull String[] permission = {};

    public ArgParameterImpl(@NotNull String name, @NotNull CommandHandler<T> argHandler) {
        this.name = name;
        this.argHandler = argHandler;
    }

    public ArgParameterImpl(@NotNull String name, @NotNull CommandHandler<T> argHandler, @NotNull String... permission) {
        this(name, argHandler);
        this.permission = permission;
    }

    @Override
    public <D> Variable<D> addVariable(String name, Class<D> type) {
        VariableImpl<D> variable = new VariableImpl<>(name, type);
        variables.add(variable);
        return variable;
    }

    @Override
    public Variable<?> getVariable(int i) {

        return variables.get(i);
    }

    @Override
    public @NotNull CommandHandler<T> getCommandHandler() {
        return argHandler;
    }

    @Override
    public int totalElementsRequired() {
        return 1 + variables.size();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public @NotNull String[] getPermissions() {
        return this.permission;
    }

    @Override
    public List<Variable<?>> getVariables() {
        return variables;
    }
}
