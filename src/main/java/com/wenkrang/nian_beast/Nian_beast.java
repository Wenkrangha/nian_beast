package com.wenkrang.nian_beast;

import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nian_beast extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConsoleCommandSender consoleSender = getServer().getConsoleSender();
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "正在加载Nian_beast, 版本 -=> 24a01a");
        consoleSender.sendMessage("    _   ___                  __                    __ ");
        consoleSender.sendMessage("   / | / (_)___ _____       / /_  ___  ____ ______/ /_");
        consoleSender.sendMessage("  /  |/ / / __ `/ __ \\     / __ \\/ _ \\/ __ `/ ___/ __/");
        consoleSender.sendMessage(" / /|  / / /_/ / / / /    / /_/ /  __/ /_/ (__  ) /_  ");
        consoleSender.sendMessage("/_/ |_/_/\\__,_/_/ /_/____/_.___/\\___/\\__,_/____/\\__/  ");
        consoleSender.sendMessage("                   /_____/                            ");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
