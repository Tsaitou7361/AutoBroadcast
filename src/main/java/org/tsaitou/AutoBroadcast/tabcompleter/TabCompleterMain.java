package org.tsaitou.AutoBroadcast.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tsaitou.AutoBroadcast.AutoBroadcast;
import org.tsaitou.AutoBroadcast.libs.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleterMain implements TabCompleter {
    private final AutoBroadcast plugin;
    private final static List<String> commands = Arrays.asList("add", "append", "delete", "join", "pop", "reload", "remove");
    private final static List<String> popping = Arrays.asList("delete", "pop", "remove");

    public TabCompleterMain(AutoBroadcast plugin) {
        this.plugin = plugin;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> {
                List<String> suggestions = new ArrayList<>();
                for (String d : commands) {
                    if (d.startsWith(args[0].toLowerCase()) || args[0].equalsIgnoreCase(d)) {
                        suggestions.add(d);
                    }
                }
                yield suggestions;
            }
            case 2 -> {
                if (popping.contains(args[0])) {
                    final Configuration config = new Configuration(plugin);
                    List<String> suggestions = new ArrayList<>();
                    List<String> messages = config.getMessage();

                    for (String d : messages) {
                        if (d.startsWith(args[1].toLowerCase()) || args[1].equalsIgnoreCase(d)) {
                            suggestions.add(d);
                        }
                    }
                    yield suggestions;
                }
                yield List.of();
            }

            default -> List.of();
        };
    }
}
