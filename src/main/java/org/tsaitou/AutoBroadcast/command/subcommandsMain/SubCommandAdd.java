package org.tsaitou.AutoBroadcast.command.subcommandsMain;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.tsaitou.AutoBroadcast.AutoBroadcast;
import org.tsaitou.AutoBroadcast.libs.Configuration;
import org.tsaitou.AutoBroadcast.libs.SubCommand;

import java.util.Arrays;

public final class SubCommandAdd extends SubCommand {
    public SubCommandAdd(CommandSender sender, Command command, String label, String[] args, AutoBroadcast plugin){
        super(sender, command, label, args, plugin);
    }

    @Override
    public boolean run() {
        final CommandSender sender = getSender();
        final Configuration config = new Configuration(getPlugin());
        final AutoBroadcast plugin = getPlugin();

        if (getArgs().length <= 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3[AutoBroadcast]&r You specified an empty new message!"));
            return true;
        }

        String[] newArgs = Arrays.copyOfRange(getArgs(), 1, getArgs().length);
        StringBuilder newMessage = new StringBuilder();

        for (String item : newArgs) {
            newMessage.append(newArgs[newArgs.length - 1].equals(item) ? item : item + " ");
        }

        config.addMessage(newMessage.toString());

        plugin.getLogger().info("Messages was updated");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3[AutoBroadcast]&r Updated messages list successfully!"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a+ &7") + newMessage);
        new SubCommandReload(getSender(), getCommand(), getLabel(), getArgs(), getPlugin()).run();
        return true;
    }
}
