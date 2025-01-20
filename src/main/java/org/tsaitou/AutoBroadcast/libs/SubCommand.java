// Credit: https://github.com/SleepySpeller/DynamicallyMotd

package org.tsaitou.AutoBroadcast.libs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.tsaitou.AutoBroadcast.AutoBroadcast;

public abstract class SubCommand {
    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;
    private final AutoBroadcast plugin;

    public SubCommand(CommandSender sender, Command command, String label, String[] args, AutoBroadcast plugin) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
        this.plugin = plugin;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Command getCommand() {
        return command;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }

    public AutoBroadcast getPlugin() {
        return plugin;
    }

    public abstract boolean run();
}
