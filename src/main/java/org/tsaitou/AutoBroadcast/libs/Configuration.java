package org.tsaitou.AutoBroadcast.libs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tsaitou.AutoBroadcast.AutoBroadcast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public final class Configuration {
    private final AutoBroadcast plugin;

    public Configuration(@NotNull AutoBroadcast plugin) {
        this.plugin = plugin;

        // Migration
        final FileConfiguration config = plugin.getConfig();
        List<String> old = config.getStringList("message.messages");
        if (!(old.isEmpty())) {
            config.set("messages", old);
            config.set("message.messages", null);
            plugin.saveConfig();
        }
        final InputStream template_file = plugin.getResource("config.yml");
        if (template_file != null) {
            final FileConfiguration defaultConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(template_file));
            removeKeysNotInTemplate(config, defaultConfiguration, "");
        }
    }

    private void removeKeysNotInTemplate(FileConfiguration config, FileConfiguration template, String currentPath) {
        if (config == null) return;

        Set<String> configKeys = config.getKeys(false);

        for (String key : configKeys) {
            String fullKey = currentPath.isEmpty() ? key : currentPath + "." + key;

            if (config.isConfigurationSection(fullKey)) {
                removeKeysNotInTemplate((FileConfiguration) config.getConfigurationSection(fullKey), template, fullKey);
            } else if (!template.contains(fullKey)) {
                config.set(fullKey, null);
                plugin.getLogger().info("Removed key: " + fullKey);
            }
        }
    }

    public @NotNull List<String> getMessage() {
        return plugin.getConfig().getStringList("messages");
    }

    public void setMessages(List<String> newList) {
        plugin.getConfig().set("messages", newList);
        plugin.saveConfig();
    }

    public @Nullable String getMessage(int index) {
        try {
            return Objects.requireNonNull(plugin.getConfig().getStringList("messages")).get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getMessageRandomly() {
        List<String> messages = getMessage();
        Random random = new Random();
        int int_random = random.nextInt(messages.size());

        if (int_random >= messages.size()) {
            int_random -= 1;
        }
        return getMessage(int_random);
    }

    public void addMessage(@NotNull String newMessage) {
        List<String> messages = getMessage();
        messages.add(newMessage);
        setMessages(messages);
    }

    public void removeMessage(@NotNull String itemToRemove) {
        List<String> messages = getMessage();
        if (messages.remove(itemToRemove)) {
            setMessages(messages);
        }
    }

    public int getInterval() {
        return plugin.getConfig().getInt("interval");
    }

    public boolean getSound() {
        return plugin.getConfig().getBoolean("sound");
    }
}
