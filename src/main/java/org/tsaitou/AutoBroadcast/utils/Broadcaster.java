package org.tsaitou.AutoBroadcast.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.tsaitou.AutoBroadcast.AutoBroadcast;
import org.tsaitou.AutoBroadcast.libs.Configuration;

public class Broadcaster {
    final private AutoBroadcast plugin;
    final private Configuration config;

    public Broadcaster(AutoBroadcast plugin) {
        this.plugin = plugin;
        this.config = new Configuration(plugin);
    }

    public void Broadcast() {
        final String message = config.getMessageRandomly();
        final boolean PlaySound = config.getSound();

        if (!(message.isEmpty())) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
            if (PlaySound) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10.0F, 1.0F);
                }
            }
            plugin.getLogger().fine("AutoBroadcast was triggered.");
        } else {
            plugin.getLogger().warning("Nothing to broadcast. Please check the config file.");
        }
    }
}
