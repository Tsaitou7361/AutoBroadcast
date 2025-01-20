package org.tsaitou.AutoBroadcast.command.subcommandsMain;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.tsaitou.AutoBroadcast.AutoBroadcast;
import org.tsaitou.AutoBroadcast.libs.Configuration;
import org.tsaitou.AutoBroadcast.libs.SubCommand;

import java.util.Arrays;

public class SubCommandRemove extends SubCommand {
    public SubCommandRemove(CommandSender sender, Command command, String label, String[] args, AutoBroadcast plugin) {
        super(sender, command, label, args, plugin);
    }

    @Override
    public boolean run() {
        final AutoBroadcast plugin = getPlugin();
        final Configuration config = new Configuration(plugin);
        final CommandSender sender = getSender();

        if (getArgs().length <= 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3[AutoBroadcast]&r you specified an empty message!"));
            return true;
        }

        String[] newArgs = Arrays.copyOfRange(getArgs(), 1, getArgs().length);
        StringBuilder message = new StringBuilder();

        for (String item : newArgs) {
            message.append(newArgs[newArgs.length - 1].equals(item) ? item : item + " ");
        }

        config.removeMessage(message.toString());

        plugin.getLogger().info("Messages was updated");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3[AutoBroadcast]&r Updated messages successfully!"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c- &7") + message);
        new SubCommandReload(getSender(), getCommand(), getLabel(), getArgs(), getPlugin()).run();
        return true;
    }
}
