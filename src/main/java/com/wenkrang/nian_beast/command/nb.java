package com.wenkrang.nian_beast.command;

import com.wenkrang.nian_beast.Entity.entity;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class nb implements CommandExecutor {
    public static void gethelp (CommandSender sender) {
        sender.sendMessage("§7[!]  §4年兽 - nian_beast §7正在运行");
        sender.sendMessage(" §4| §7help  帮助列表");
        sender.sendMessage(" §4| §7spawnLO 生成普通年兽");

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0) {
            gethelp(commandSender);
        } else {
            if (strings[0].equalsIgnoreCase("help")) {
                gethelp(commandSender);
            }
            if (strings[0].equalsIgnoreCase("spawnLO")) {
                if (commandSender.isOp()) {
                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        entity.getEntityone(player.getLocation());
                        commandSender.sendMessage(SpigotConsoleColors.BLUE + "[-] " + SpigotConsoleColors.RESET + "普通年兽已生成");
                    } else {
                        commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "你必须在游戏内才可以使用此命令");
                    }
                } else {
                    commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "阿巴阿巴");
                }

            }
        }




        return true;
    }
}
