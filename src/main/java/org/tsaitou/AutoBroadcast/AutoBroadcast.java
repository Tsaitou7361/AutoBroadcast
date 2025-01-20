package org.tsaitou.AutoBroadcast;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.tsaitou.AutoBroadcast.command.CommandMain;
import org.tsaitou.AutoBroadcast.libs.Configuration;
import org.tsaitou.AutoBroadcast.tabcompleter.TabCompleterMain;
import org.tsaitou.AutoBroadcast.utils.Broadcaster;

import java.util.Objects;

public final class AutoBroadcast extends JavaPlugin {
    public BukkitTask task;

    public boolean hasPermissions(CommandSender sender) {
        return (sender instanceof Player) ? sender.hasPermission("abc.admin") : sender instanceof ConsoleCommandSender;
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("AutoBroadcast")).setExecutor(new CommandMain(this));
        Objects.requireNonNull(getCommand("AutoBroadcast")).setTabCompleter(new TabCompleterMain(this));
        saveDefaultConfig();
        getLogger().info("Plugin has enabled.");
        try {
            reloadConfig();
            getLogger().info("Config loaded.");
        } catch (Exception e) {
            getLogger().warning("Config file is not loaded. Please use /abc reload to load config file.");
            getLogger().warning(e.toString());
        }
        final Configuration config = new Configuration(this);
        final Broadcaster broadcaster = new Broadcaster(this);
        task = Bukkit.getScheduler().runTaskTimer(this, broadcaster::Broadcast, 0L, config.getInterval() * 20L);
    }

    @Override
    public void onDisable() {
    getLogger().info("Plugin has disabled.");
    }
}
