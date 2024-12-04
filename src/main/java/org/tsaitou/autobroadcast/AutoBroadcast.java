package org.tsaitou.autobroadcast;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("unused")
public class AutoBroadcast extends JavaPlugin {

    public static AutoBroadcast instance;
    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        instance = this;
        createCustomConfig(false);
        Objects.requireNonNull(getCommand("AutoBroadcast")).setExecutor(new Commands());
        ABC();
        getLogger().info(
                """
                        AutoBroadcast 插件已啟用！
                        -------------------------------------------------------------------------------------------------------------

                         █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ██████╗  ██████╗  █████╗ ██████╗  ██████╗ █████╗ ███████╗████████╗
                        ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔════╝╚══██╔══╝
                        ███████║██║   ██║   ██║   ██║   ██║██████╔╝██████╔╝██║   ██║███████║██║  ██║██║     ███████║███████╗   ██║  \s
                        ██╔══██║██║   ██║   ██║   ██║   ██║██╔══██╗██╔══██╗██║   ██║██╔══██║██║  ██║██║     ██╔══██║╚════██║   ██║  \s
                        ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██████╔╝██║  ██║╚██████╔╝██║  ██║██████╔╝╚██████╗██║  ██║███████║   ██║  \s
                        ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝  \s
                                                                                                                                    \s
                        -------------------------------------------------------------------------------------------------------------

                        """
        );
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public void createCustomConfig(boolean reload) {
        if (!reload) {
            customConfigFile = new File(getDataFolder(), "config.yml");
            if (!customConfigFile.exists()) {
                customConfigFile.getParentFile().mkdirs();
                saveResource("config.yml", false);
            }
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().warning(e.toString());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("AutoBroadcast 插件已停用！");
    }

    public boolean isNull() {
        @SuppressWarnings("rawtypes")
        List messages = getCustomConfig().getStringList("message.messages");
        return messages.isEmpty();
    }

    public void ABC() {

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            @SuppressWarnings("rawtypes")
            List messages = instance.getCustomConfig().getStringList("message.messages");
            Random random = new Random();
            if (!(isNull())) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        (String) messages.get(random.nextInt(messages.size()))
                ));
                if (instance.getCustomConfig().getBoolean("sound")) {
                    for (Player p: Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10.0F, 1.0F);
                    }
                }
            } else {
                getLogger().warning("Nothing to broadcast. Please check the config file.");
            }
        }, 0L, instance.getCustomConfig().getInt("interval") * 20L);
    }
}
