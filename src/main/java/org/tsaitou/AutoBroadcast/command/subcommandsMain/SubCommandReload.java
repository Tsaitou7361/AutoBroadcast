package org.tsaitou.AutoBroadcast.command.subcommandsMain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.tsaitou.AutoBroadcast.AutoBroadcast;
import org.tsaitou.AutoBroadcast.libs.Configuration;
import org.tsaitou.AutoBroadcast.libs.SubCommand;
import org.tsaitou.AutoBroadcast.utils.Broadcaster;

public class SubCommandReload extends SubCommand {
    public SubCommandReload(CommandSender sender, Command command, String label, String[] args, AutoBroadcast plugin) {
        super(sender, command, label, args, plugin);
    }

    @Override
    public boolean run() {
        final AutoBroadcast plugin = getPlugin();
        final CommandSender sender = getSender();
        final Configuration config = new Configuration(plugin);
        plugin.getLogger().info("Config reloading...");
        plugin.reloadConfig();
        plugin.getLogger().info("Config reloaded.");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3[AutoBroadcast]&r Config reloaded!"));

        final int interval = config.getInterval();
        final Broadcaster broadcaster = new Broadcaster(plugin);
        try {
            plugin.task.cancel();
        } catch (Exception e) {
            plugin.getLogger().warning(e.toString());
        }
        plugin.task = Bukkit.getScheduler().runTaskTimer(plugin, broadcaster::Broadcast, 0L, interval * 20L);
        return true;
    }
}
