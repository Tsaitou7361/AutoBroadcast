package org.tsaitou.autobroadcast;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

@SuppressWarnings("unused")
public class Commands implements CommandExecutor, TabCompleter {
    private static final AutoBroadcast plugin = AutoBroadcast.instance;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0] == null) {
            help(sender);
            return true;
        }

        switch (args[0]) {
            case "reload":
                if (!sender.hasPermission("abc.admin")) {
                    sender.sendMessage(Objects.requireNonNull(plugin.getCustomConfig().getString("message.denied")));
                } else {
                    plugin.createCustomConfig(true);
                    sender.sendMessage(ChatColor.GREEN + "已重新讀取設定檔！");
                    plugin.getLogger().info("已重新讀取設定檔！");

                    if (plugin.isNull()) {
                        plugin.getLogger().warning("Nothing to broadcast, please check the config file out.");
                    }
                }
                break;
            default:
                help(sender);
                break;
        }
        return true;
    }

    public void help(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage("\n" +
            ChatColor.STRIKETHROUGH + "-----------------------------------------\n" +
                            ChatColor.GOLD + "AutoBroadcast by AverageKevin (Tsaitou7361 Modified)\n" +
                            "\n" +
                            ChatColor.GREEN + "/abc\n" +
                            ChatColor.GREEN + "/abc help | 顯示幫助頁面\n" +
                            ChatColor.GREEN + "/abc reload | 重新讀取設定檔\n" +
                            "\n" +
                            ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-----------------------------------------"
            );
            return;
        }
        sender.sendMessage("\n" +
                """
                       --------------------------------------------------------------------------------------------------------------

                         █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ██████╗  ██████╗  █████╗ ██████╗  ██████╗ █████╗ ███████╗████████╗
                        ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔════╝╚══██╔══╝
                        ███████║██║   ██║   ██║   ██║   ██║██████╔╝██████╔╝██║   ██║███████║██║  ██║██║     ███████║███████╗   ██║  \s
                        ██╔══██║██║   ██║   ██║   ██║   ██║██╔══██╗██╔══██╗██║   ██║██╔══██║██║  ██║██║     ██╔══██║╚════██║   ██║  \s
                        ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██████╔╝██║  ██║╚██████╔╝██║  ██║██████╔╝╚██████╗██║  ██║███████║   ██║  \s
                        ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝  \s
                                                                                                                                    \s
                       --------------------------------------------------------------------------------------------------------------

                       """ +
                "\n\n" +
                ChatColor.GOLD + "AutoBroadcast by AverageKevin (Tsaitou7361 Modified)\n" +
                "\n" +
                ChatColor.GREEN + "/abc help | 顯示幫助頁面\n" +
                ChatColor.GREEN + "/abc reload | 重新讀取設定檔\n" +
                "\n" +
                ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-----------------------------------------");
    }
    @Override()
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = Arrays.asList("reload", "help");
        if (args.length == 0) {
            return commands;
        }

        for (String d: commands) {
            if (args[0].startsWith(String.valueOf(d.charAt(0))) || args[0].equalsIgnoreCase(d)) {
                return List.of(d);
            }
        }
        return commands;
    }
}
