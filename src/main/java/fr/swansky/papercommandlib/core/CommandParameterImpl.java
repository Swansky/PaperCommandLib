package fr.swansky.papercommandlib.core;


import fr.swansky.papercommandlib.ArgParameter;
import fr.swansky.papercommandlib.CommandHandler;
import fr.swansky.papercommandlib.CommandParameter;
import fr.swansky.papercommandlib.DefaultCommandHandler;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandParameterImpl<T extends CommandSender> implements CommandParameter<T> {
    private String permission = "";
    private final Map<String, ArgParameter<T>> args = new HashMap<>();

    private DefaultCommandHandler<T> defaultHandler;

    @Override
    public CommandParameter<T> setDefaultHandler(DefaultCommandHandler<T> handler) {
        this.defaultHandler = handler;
        return this;
    }


    public Optional<DefaultCommandHandler<T>> getDefaultHandler() {
        return Optional.ofNullable(defaultHandler);
    }

    @Override
    public ArgParameter<T> addArg(@NotNull String name, @NotNull CommandHandler<T> argHandler) {
        return addArg(name, argHandler, "");
    }

    @Override
    public ArgParameter<T> addArg(@NotNull String name, @NotNull CommandHandler<T> argHandler, String... permission) {
        ArgParameter<T> argParameter = new ArgParameterImpl<>(name, argHandler, permission);
        args.put(name, argParameter);
        return argParameter;
    }


    @Override
    public Optional<ArgParameter<T>> findArgByName(@NotNull String arg) {
        return Optional.ofNullable(args.get(arg));
    }

    @Override
    public Collection<ArgParameter<T>> getArguments() {
        return args.values();
    }

    @Override
    public @NotNull String getPermission() {
        return permission;
    }

    @Override
    public CommandParameter<T> setPermission(@NotNull String permission) {
        this.permission = permission;
        return this;
    }
}
