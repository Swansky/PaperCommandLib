package fr.swansky.papercommandlib;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ArgParameter<T extends CommandSender> {

    <D> Variable<D> addVariable(String name, Class<D> type);


    Variable<?> getVariable(int i);

    @NotNull CommandHandler<T> getCommandHandler();

    int totalElementsRequired();

    String getName();

    @NotNull String[] getPermissions();

    List<Variable<?>> getVariables();
}
