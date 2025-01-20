package org.tsaitou.AutoBroadcast.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.tsaitou.AutoBroadcast.AutoBroadcast;
import org.tsaitou.AutoBroadcast.command.subcommandsMain.SubCommandAdd;
import org.tsaitou.AutoBroadcast.command.subcommandsMain.SubCommandReload;
import org.tsaitou.AutoBroadcast.command.subcommandsMain.SubCommandRemove;
import org.tsaitou.AutoBroadcast.libs.SubCommand;

public final class CommandMain implements CommandExecutor {
    private final AutoBroadcast plugin;

    public CommandMain(AutoBroadcast plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!plugin.hasPermissions(sender)) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("AutoBroadcast by Tsaitou7361");
            return true;
        }

        return switch (args[0]) {
            case "reload" -> {
                SubCommand subReload = new SubCommandReload(sender, command, label, args, plugin);
                yield subReload.run();
            }
            case "add", "join", "append" -> {
                SubCommand subAdd = new SubCommandAdd(sender, command, label, args, plugin);
                yield subAdd.run();
            }
            case "remove", "delete", "pop" -> {
                SubCommand subRemove = new SubCommandRemove(sender, command, label, args, plugin);
                yield subRemove.run();
            }

            default -> {
                sender.sendMessage("AutoBroadcast by Tsaitou7361");
                yield true;
            }
        };
    }
}
