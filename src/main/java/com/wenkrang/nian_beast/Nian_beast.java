package com.wenkrang.nian_beast;

import com.wenkrang.nian_beast.Entity.*;
import com.wenkrang.nian_beast.command.nb;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public final class Nian_beast extends JavaPlugin {
    public static boolean isShutdown = false;

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
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "正在注册命令");

        //Load Command
        getServer().getPluginCommand("nb").setExecutor(new nb());
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "正在注册监听器");
        getServer().getPluginManager().registerEvents(new Spawner(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByBlock(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new safdasfs(), this); //没啥事别开
        World world = getServer().getWorld("world");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConsoleCommandSender consoleSender = getServer().getConsoleSender();
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "Nian_beast 正在关闭");
        isShutdown = true;
    }
}
