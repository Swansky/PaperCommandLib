package fr.swansky.papercommandlib;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface CommandParameter<T extends CommandSender> {

    CommandParameter<T> setDefaultHandler(DefaultCommandHandler<T> handler);

    Optional<DefaultCommandHandler<T>> getDefaultHandler();

    ArgParameter<T> addArg(@NotNull String name, @NotNull CommandHandler<T> ArgHandler);

    ArgParameter<T> addArg(@NotNull String name, @NotNull CommandHandler<T> ArgHandler, String... permission);

    Optional<ArgParameter<T>> findArgByName(@NotNull String arg);


    Collection<ArgParameter<T>> getArguments();

    @NotNull String getPermission();


    CommandParameter<T> setPermission(@NotNull String permission);
}
