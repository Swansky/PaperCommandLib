package fr.swansky.papercommandlib;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DefaultCommandHandler<T extends CommandSender> {

    boolean onCommand(@NotNull T entity, @NotNull Command command, @NotNull String label);

}
