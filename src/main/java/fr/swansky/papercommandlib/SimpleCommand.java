package fr.swansky.papercommandlib;

import fr.swansky.papercommandlib.core.CommandParameterImpl;
import fr.swansky.papercommandlib.exceptions.NotSupportedTypeException;
import fr.swansky.papercommandlib.exceptions.ValidatorException;
import fr.swansky.papercommandlib.utils.PermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public abstract class SimpleCommand<T extends CommandSender> implements CommandExecutor, TabCompleter {

    private final Class<T> senderClass;
    protected final CommandParameter<T> commandParameter;

    public SimpleCommand(Class<T> senderClass) {
        this.senderClass = senderClass;
        this.commandParameter = parameter(new CommandParameterImpl<T>());
    }

    protected abstract CommandParameter<T> parameter(CommandParameter<T> parameter);

    @Override
    @SuppressWarnings({"unchecked"})
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (senderClass.isAssignableFrom(sender.getClass())) {
            if (sender.isOp() || sender.hasPermission(commandParameter.getPermission())) {
                List<Object> valuesParse = new ArrayList<>();
                if (args.length == 0) {
                    if (commandParameter.getDefaultHandler().isPresent()) {
                        return commandParameter.getDefaultHandler().get().onCommand((T) sender, command, label);
                    } else {
                        sender.sendMessage("Invalid command");
                    }
                } else {
                    Optional<ArgParameter<T>> argOptional = commandParameter.findArgByName(args[0]);
                    if (argOptional.isPresent()) {
                        ArgParameter<T> argParameter = argOptional.get();

                        if (sender.isOp() || argParameter.getPermissions().length == 0 || PermissionUtils.SenderHasOneOfPermission(sender, argParameter.getPermissions())) {
                            if (argParameter.totalElementsRequired() <= args.length) {
                                for (int i = 1; i < argParameter.totalElementsRequired(); i++) {
                                    Variable<?> variable = argParameter.getVariable(i - 1);
                                    try {
                                        Object obj = variable.parseValueToVariable(args[i]);
                                        valuesParse.add(obj);
                                        for (Validator validator : variable.getValidors()) {
                                            validator.validate(obj, sender, variable.getName());
                                        }
                                    } catch (IllegalArgumentException e) {
                                        sender.sendMessage(String.format("invalid data for variable '%s'", variable.getName()));
                                        return true;
                                    } catch (ValidatorException e) {
                                        sender.sendMessage(e.getMessage());
                                        return true;
                                    } catch (NotSupportedTypeException e) {
                                        sender.sendMessage(Color.RED + "Impossible to execute this command.");
                                        e.printStackTrace();
                                        return true;
                                    }
                                }
                                return argParameter.getCommandHandler().onCommand((T) sender, command, label, valuesParse);
                            } else {
                                sender.sendMessage("invalid command format");
                            }
                        } else {
                            sender.sendMessage("you don't have permission to execute this command");
                        }

                    } else {
                        sender.sendMessage("Arg no exist");
                    }
                }
            } else {
                sender.sendMessage("You don't have the permission to execute this command");
            }

        } else {
            sender.sendMessage("You cannot execute this command in this place.");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> values = new ArrayList<>();
        if (args.length <= 1) {
            Collection<ArgParameter<T>> arguments = commandParameter.getArguments();
            for (ArgParameter<T> argument : arguments) {
                if (sender.isOp() || argument.getPermissions().length == 0 || PermissionUtils.SenderHasOneOfPermission(sender, argument.getPermissions()))
                    values.add(argument.getName());
            }
        } else {
            Optional<ArgParameter<T>> argumentValueAtPosition = commandParameter.findArgByName(args[0]);
            if (argumentValueAtPosition.isPresent()) {
                ArgParameter<T> argumentParam = argumentValueAtPosition.get();
                if (sender.isOp() || argumentParam.getPermissions().length == 0 || PermissionUtils.SenderHasOneOfPermission(sender, argumentParam.getPermissions())) {
                    List<Variable<?>> variables = argumentParam.getVariables();
                    int pos = args.length - 2;
                    if (variables.size() > pos) {
                        Variable<?> variableParam = variables.get(pos);
                        if (variableParam.getType().isAssignableFrom(Player.class)) {
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                values.add(onlinePlayer.getName());
                            }
                        } else if (variableParam.getType().isEnum()) {
                            for (Object enumConstant : variableParam.getType().getEnumConstants()) {
                                values.add(enumConstant.toString().toLowerCase());
                            }
                        } else {
                            values.add(variableParam.getName());
                        }
                    }
                }
            }
        }
        return values;
    }
}


